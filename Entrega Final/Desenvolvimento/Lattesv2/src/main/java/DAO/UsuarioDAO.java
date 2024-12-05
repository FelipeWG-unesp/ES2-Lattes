package DAO;

import Model.Usuario;
import auxx.Conexao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {

    // Método para cadastrar um usuário
    public boolean cadastrarUsuario(Usuario usuario, String sistemaNome) {
        // Verificar se o sistema existe
        if (!verificarSistemaExistente(sistemaNome)) {
            System.out.println("Sistema não existe.");
            return false; // Sistema não existe, não podemos continuar
        }

        String sql = "INSERT INTO Usuario (cpf, nome, email, senha) VALUES (?, ?, ?, ?)";
        try (Connection conn = Conexao.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            // Ordem correta de acordo com o SQL
            ps.setString(1, usuario.getCpf());
            ps.setString(2, usuario.getNome());
            ps.setString(3, usuario.getEmail());
            ps.setString(4, usuario.getSenha());

            ps.executeUpdate();

            // Agora, insere o usuário na tabela UsuarioSistema
            String sqlUsuarioSistema = "INSERT INTO UsuarioSistema (usuario_cpf, sistema_nome) VALUES (?, ?)";
            try (PreparedStatement psUsuarioSistema = conn.prepareStatement(sqlUsuarioSistema)) {
                psUsuarioSistema.setString(1, usuario.getCpf());
                psUsuarioSistema.setString(2, sistemaNome); // Nome do sistema
                psUsuarioSistema.executeUpdate();
            }

            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean verificarSistemaExistente(String sistemaNome) {
        String sql = "SELECT COUNT(*) FROM Sistema WHERE nome = ?";
        try (Connection conn = Conexao.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            // Exibe o nome do sistema que está sendo verificado
            System.out.println("Verificando sistema: " + sistemaNome);

            ps.setString(1, sistemaNome);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                int count = rs.getInt(1);
                System.out.println("Número de sistemas encontrados: " + count);
                if (count > 0) {
                    return true; // Sistema existe
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Caso o sistema não seja encontrado
        System.out.println("Sistema não encontrado: " + sistemaNome);
        return false; // Sistema não existe
    }



    // Método para realizar o login de um usuário
    public Usuario loginUsuario(String email, String senha) {
        String sql = "SELECT * FROM Usuario WHERE email = ? AND senha = ?";
        try (Connection conn = Conexao.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, email);
            ps.setString(2, senha);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Usuario(
                        rs.getString("cpf"),
                        rs.getString("nome"),
                        rs.getString("email"),
                        rs.getString("senha")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Método para editar um usuário
    public boolean editarUsuario(Usuario usuario) {
        String sql = "UPDATE Usuario SET nome = ?, email = ?, senha = ? WHERE cpf = ?";
        try (Connection conn = Conexao.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, usuario.getNome());
            ps.setString(2, usuario.getEmail());
            ps.setString(3, usuario.getSenha());
            ps.setString(4, usuario.getCpf());

            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    // Método para buscar usuários associados a um sistema específico
    public List<Usuario> buscarUsuariosPorSistema(String sistemaNome) {
String sql = "SELECT DISTINCT u.* " +
             "FROM Usuario u " +
             "INNER JOIN UsuarioSistema us ON us.usuario_cpf = u.cpf " + // Relacionamento via UsuarioSistema
             "INNER JOIN Sistema s ON s.nome = us.sistema_nome " + // Junção com a tabela Sistema
             "WHERE s.nome = ? AND us.sistema_nome = ?"; // Condição para filtrar pelo sistema

        List<Usuario> usuarios = new ArrayList<>();

        try (Connection conn = Conexao.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, sistemaNome);
            ps.setString(2, sistemaNome);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Usuario usuario = new Usuario(
                        rs.getString("cpf"),
                        rs.getString("nome"),
                        rs.getString("email"),
                        rs.getString("senha")
                );
                usuarios.add(usuario);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return usuarios;
    }

    public List<Usuario> buscarUsuariosPorADM(String administradorLogin, String sistemaNome) {
        String sql = "SELECT u.* " +
                     "FROM Usuario u " +
                     "INNER JOIN UsuarioSistema us ON us.usuario_cpf = u.cpf " + // Junção correta com a tabela UsuarioSistema
                     "INNER JOIN ADM_Sistema asis ON asis.sistema_nome = us.sistema_nome " + // Junção com ADM_Sistema
                     "WHERE asis.adm_login = ? AND us.sistema_nome = ?"; // Filtro correto pelo administrador e sistema

        List<Usuario> usuarios = new ArrayList<>();

        try (Connection conn = Conexao.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, administradorLogin); // Define o login do administrador
            ps.setString(2, sistemaNome); // Define o nome do sistema

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Usuario usuario = new Usuario(
                    rs.getString("cpf"),
                    rs.getString("nome"),
                    rs.getString("email"),
                    rs.getString("senha")
                );
                usuarios.add(usuario);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return usuarios;
    }



    public String buscarSistemaPorAdministrador(String administradorLogin) {
        String sql = "SELECT sistema_nome FROM ADM_Sistema WHERE adm_login = ?";
        try (Connection conn = Conexao.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, administradorLogin);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString("sistema_nome"); // Retorna o nome do sistema associado
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Retorna null se não encontrar
    }

    public String obterSistemaPorAdm(String administradorLogin) {
        String sql = "SELECT sistema_nome FROM ADM_Sistema WHERE adm_login = ?";
        try (Connection conn = Conexao.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, administradorLogin);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getString("sistema_nome");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Retorna null se nenhum sistema for encontrado
    }

    public Usuario buscarUsuarioPorCpf(String cpf) {
        String sql = "SELECT * FROM Usuario WHERE cpf = ?";
        try (Connection conn = Conexao.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, cpf);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Usuario(
                        rs.getString("cpf"),
                        rs.getString("nome"),
                        rs.getString("email"),
                        rs.getString("senha")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Usuario> buscarUsuarios() {
        String sql = "SELECT * FROM Usuario";
        List<Usuario> usuarios = new ArrayList<>();

        try (Connection conn = Conexao.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Usuario usuario = new Usuario(
                        rs.getString("cpf"),
                        rs.getString("nome"),
                        rs.getString("email"),
                        rs.getString("senha")
                );
                usuarios.add(usuario);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return usuarios;
    }


}