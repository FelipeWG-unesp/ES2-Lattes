package Controller;

import DAO.UsuarioDAO;
import Model.Usuario;
import java.util.List;
import java.util.ArrayList;

public class UsuarioController {

    private UsuarioDAO usuarioDAO;
    private List<Usuario> usuarios;

    public UsuarioController() {
        this.usuarioDAO = new UsuarioDAO();
        this.usuarios = new ArrayList<>();
        // Carregar usuários do banco de dados na inicialização (opcional)
        usuarios.addAll(usuarioDAO.buscarUsuarios());
    }

    // Método para cadastrar um usuário
    public boolean cadastrarUsuario(Usuario usuario, String sistemaNome) {
        // Adiciona o usuário na lista interna
        usuarios.add(usuario);
        // Agora, realiza a inserção no banco de dados
        return usuarioDAO.cadastrarUsuario(usuario, sistemaNome);
    }

    // Método para realizar login
    public Usuario fazerLogin(String email, String senha) {
        for (Usuario usuario : usuarios) {
            if (usuario.getEmail().equals(email) && usuario.getSenha().equals(senha)) {
                System.out.println("Login realizado com sucesso!");
                return usuario;
            }
        }
        System.out.println("Falha no login. Verifique suas credenciais.");
        return null;
    }

    // Método para editar um usuário
    public boolean editarUsuario(String cpf, String nome, String email, String senha) {
        // Atualiza o usuário na lista interna
        for (Usuario usuario : usuarios) {
            if (usuario.getCpf().equals(cpf)) {
                usuario.setNome(nome);
                usuario.setEmail(email);
                usuario.setSenha(senha);
                // Atualiza no banco de dados
                return usuarioDAO.editarUsuario(usuario);
            }
        }
        System.out.println("Usuário não encontrado.");
        return false;
    }

    // Método para buscar usuários por administrador
    public List<Usuario> buscarUsuariosPorADM(String administradorLogin, String sistemaNome) {
        return usuarioDAO.buscarUsuariosPorADM(administradorLogin, sistemaNome);
    }

    public String buscarSistemaDoAdministrador(String administradorLogin) {
        return usuarioDAO.buscarSistemaPorAdministrador(administradorLogin);
    }

    public List<Usuario> buscarUsuariosPorSistema(String sistemaNome) {
        return usuarioDAO.buscarUsuariosPorSistema(sistemaNome);
    }

    public String obterSistemaPorAdm(String administradorLogin) {
        return usuarioDAO.obterSistemaPorAdm(administradorLogin);
    }

    public Usuario buscarUsuarioPorCpf(String cpf) {
        for (Usuario usuario : usuarios) {
            if (usuario.getCpf().equals(cpf)) {
                return usuario;
            }
        }
        return null;
    }
}
