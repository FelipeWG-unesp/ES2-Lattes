/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import DAO.*;
import Model.*;
import auxx.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.List;

public class SistemaController {

    private UsuarioDAO usuarioDAO;
    private CurriculoDAO curriculoDAO;
    private RelatorioDAO relatorioDAO;
    private LoginDAO loginDAO;
    private ADMDAO admDAO; 
    private SistemaDAO sistemaDAO;

    public SistemaController() {
        this.usuarioDAO = new UsuarioDAO();
        this.curriculoDAO = new CurriculoDAO();
        this.relatorioDAO = new RelatorioDAO();
        this.loginDAO = new LoginDAO();
        this.sistemaDAO = new SistemaDAO();
        this.admDAO = new ADMDAO();
    }

    // Método para fazer login
    public Object fazerLogin(String emailOuLogin, String senha) {
        // Verificar se é um usuário
        Usuario usuario = loginDAO.loginUsuario(emailOuLogin, senha);
        if (usuario != null) {
            return usuario; // Retorna objeto Usuario
        }

        // Verificar se é um administrador
        ADM adm = loginDAO.loginADM(emailOuLogin, senha);
        if (adm != null) {
            return adm; // Retorna objeto ADM
        }

        // Caso não encontre
        return null;
    }

    // Método para buscar currículos de um usuário
    public void buscarCurriculos(String usuarioCpf) {
        List<Curriculo> curriculos = curriculoDAO.buscarCurriculosPorUsuario(usuarioCpf);
        if (curriculos.isEmpty()) {
            System.out.println("Nenhum currículo encontrado para este usuário.");
        } else {
            System.out.println("Currículos encontrados:");
            for (Curriculo curriculo : curriculos) {
                System.out.println("- Título: " + curriculo.getTitulo() + ", Dados Pessoais: " + curriculo.getDadosPessoais());
            }
        }
    }

    // Método para validar dados
    public boolean validarDados(String email, String senha) {
        Usuario usuario = usuarioDAO.loginUsuario(email, senha);
        if (usuario != null) {
            System.out.println("Dados válidos para o usuário: " + usuario.getNome());
            return true;
        } else {
            System.out.println("Dados inválidos.");
            return false;
        }
    }

    // Método para inicializar o sistema
    public void iniciarSistema() {
        System.out.println("Inicializando o sistema...");
        System.out.println("Sistema iniciado com sucesso!");
    }

    // Método para obter o nome do sistema associado a um ADM
    public String obterSistemaParaADM(String admLogin) {
        return admDAO.obterSistemaParaADM(admLogin);
    }

    // Método para criar um gerente e associá-lo a um sistema
    public boolean criarGerenteESistema(String login, String senha, String nomeSistema) {
        try {
            // Cria o sistema no banco de dados
            Sistema sistema = new Sistema(nomeSistema);
            if (!sistemaDAO.cadastrarSistema(sistema)) {
                return false; // Erro ao criar o sistema
            }

            // Cria o administrador no banco de dados
            ADM adm = new ADM(login, senha);
            if (!admDAO.cadastrarADM(adm)) {
                return false; // Erro ao criar o ADM
            }

            // Associa o administrador ao sistema
            return admDAO.associarADMaoSistema(login, nomeSistema);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}