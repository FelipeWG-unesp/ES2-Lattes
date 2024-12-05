/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import DAO.*;
import Model.*;
import auxx.Conexao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author gusan
 */
public class UsuarioController {

    private UsuarioDAO usuarioDAO;

    public UsuarioController() {
        this.usuarioDAO = new UsuarioDAO();
    }

    /*
    // Método para cadastrar um usuário
    public boolean cadastrarUsuario(String nome, String email, String cpf, String senha) {
        Usuario usuario = new Usuario(cpf, nome, email, senha);
        boolean sucesso = usuarioDAO.cadastrarUsuario(usuario);
        if (sucesso) {
            // Cria um currículo em branco para o novo usuário
            Curriculo curriculo = new Curriculo("", "", "", "", cpf, "Currículo Inicial");
            CurriculoDAO curriculoDAO = new CurriculoDAO();
            curriculoDAO.cadastrarCurriculo(curriculo);
        }
        return sucesso;
    }
    */
    
    public boolean cadastrarUsuario(Usuario usuario, String sistemaNome) {
        
        return usuarioDAO.cadastrarUsuario(usuario, sistemaNome);
    }

    
    // Método para realizar login
    public Usuario fazerLogin(String email, String senha) {
        Usuario usuario = usuarioDAO.loginUsuario(email, senha);
        if (usuario != null) {
            System.out.println("Login realizado com sucesso!");
        } else {
            System.out.println("Falha no login. Verifique suas credenciais.");
        }
        return usuario;
    }

    // Método para editar um usuário
    public boolean editarUsuario(String cpf, String nome, String email, String senha) {
        Usuario usuario = new Usuario(cpf, nome, email, senha);
        boolean sucesso = usuarioDAO.editarUsuario(usuario);
        if (sucesso) {
            System.out.println("Usuário editado com sucesso!");
        } else {
            System.out.println("Falha ao editar o usuário.");
        }
        return sucesso;
    }

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
        return usuarioDAO.buscarUsuarioPorCpf(cpf);
    }


   
}