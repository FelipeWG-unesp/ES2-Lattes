package DAO;

import Model.Curriculo;
import auxx.Conexao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CurriculoDAO {

    // Método para cadastrar um novo currículo
    public boolean cadastrarCurriculo(Curriculo curriculo) {
        System.out.println("Dados recebidos para cadastro: " + curriculo.getDadosPessoais() + ", " + curriculo.getFormacaoAcademica() + ", " + curriculo.getProducoesAcademicas() + ", " + curriculo.getPremios() + ", " + curriculo.getUsuarioCpf() + ", " + curriculo.getTitulo());

        String sql = "INSERT INTO Curriculo (dadosPessoais, formacaoAcademica, producoesAcademicas, premios, usuario_cpf, titulo) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = Conexao.getConexao().prepareStatement(sql)) {
            ps.setString(1, curriculo.getDadosPessoais());
            ps.setString(2, curriculo.getFormacaoAcademica());
            ps.setString(3, curriculo.getProducoesAcademicas());
            ps.setString(4, curriculo.getPremios());
            ps.setString(5, curriculo.getUsuarioCpf());
            ps.setString(6, curriculo.getTitulo());
            ps.executeUpdate();
            return true; // Retorna true se o currículo foi cadastrado com sucesso
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Retorna false em caso de erro
        }
    }

    
    // Método para editar uma seção do currículo
    public boolean editarSecao(String usuarioCpf, String titulo, String secao, String dados) {
        String sql = "UPDATE Curriculo SET " + secao + " = ? WHERE usuario_cpf = ? AND titulo = ?";
        try (Connection conn = Conexao.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, dados);
            ps.setString(2, usuarioCpf);
            ps.setString(3, titulo);

            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Método para buscar currículos de um usuário
    public List<Curriculo> buscarCurriculosPorUsuario(String usuarioCpf) {
        String sql = "SELECT * FROM Curriculo WHERE usuario_cpf = ?";
        List<Curriculo> curriculos = new ArrayList<>();
        try (Connection conn = Conexao.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, usuarioCpf);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Curriculo curriculo = new Curriculo(
                        rs.getString("usuario_cpf"),
                        rs.getString("titulo"),
                        rs.getString("dadosPessoais"),
                        rs.getString("formacaoAcademica"),
                        rs.getString("producoesAcademicas"),
                        rs.getString("premios")
                );
                curriculos.add(curriculo);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return curriculos;
    }

    // Método para buscar um currículo específico
    public Curriculo buscarCurriculo(String usuarioCpf, String titulo) {
        String sql = "SELECT * FROM Curriculo WHERE usuario_cpf = ? AND titulo = ?";
        try (Connection conn = Conexao.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, usuarioCpf);
            ps.setString(2, titulo);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new Curriculo(
                        rs.getString("usuario_cpf"),
                        rs.getString("titulo"),
                        rs.getString("dadosPessoais"),
                        rs.getString("formacaoAcademica"),
                        rs.getString("producoesAcademicas"),
                        rs.getString("premios")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Método para excluir um currículo
    public boolean excluirCurriculo(String usuarioCpf, String titulo) {
        String sql = "DELETE FROM Curriculo WHERE usuario_cpf = ? AND titulo = ?";
        try (Connection conn = Conexao.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, usuarioCpf);
            ps.setString(2, titulo);

            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public List<Curriculo> getCurriculosByCpf(String cpf) {
        List<Curriculo> curriculos = new ArrayList<>();
        String sql = "SELECT * FROM Curriculo WHERE cpf_usuario = ?"; // Assumindo que a tabela tem uma coluna 'cpf_usuario'

        try (PreparedStatement stmt = Conexao.getConexao().prepareStatement(sql)) {
            stmt.setString(1, cpf);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                // Criar o objeto Curriculo com os dados da consulta
                Curriculo curriculo = new Curriculo(rs.getString("cpf_usuario"),rs.getString("titulo"),
                        "",rs.getString("formacaoAcademica"),rs.getString("producaoAcademica"),rs.getString("premios"));

                // Adicionar à lista de currículos
                curriculos.add(curriculo);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return curriculos;
    }
}