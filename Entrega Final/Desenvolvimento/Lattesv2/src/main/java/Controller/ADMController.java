/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import DAO.ADMDAO;
import Model.ADM;

import java.util.List;

public class ADMController {

    private ADMDAO admDAO;

    public ADMController() {
        this.admDAO = new ADMDAO();
    }

    // Método para cadastrar um novo ADM
    public void cadastrarADM(String login, String senha) {
        ADM adm = new ADM(login, senha);
        if (admDAO.cadastrarADM(adm)) {
            System.out.println("ADM cadastrado com sucesso!");
        } else {
            System.out.println("Erro ao cadastrar o ADM.");
        }
    }

    // Método para editar um ADM
    public void editarADM(String login, String novaSenha) {
        ADM adm = new ADM(login, novaSenha);
        boolean sucesso = admDAO.editarADM(adm);
        if (sucesso) {
            System.out.println("ADM editado com sucesso!");
        } else {
            System.out.println("Falha ao editar o ADM.");
        }
    }

    // Método para validar credenciais de um ADM
    public boolean validarCredenciaisADM(String login, String senha) {
        ADM adm = admDAO.validarCredenciaisADM(login, senha);
        if (adm != null) {
            System.out.println("Credenciais válidas para o ADM: " + adm.getLogin());
            return true;
        } else {
            System.out.println("Credenciais inválidas.");
            return false;
        }
    }

    // Método para listar todos os ADMs
    public void listarADMs() {
        List<ADM> adms = admDAO.listarADMs();
        if (adms.isEmpty()) {
            System.out.println("Nenhum ADM cadastrado.");
        } else {
            System.out.println("Lista de ADMs:");
            for (ADM adm : adms) {
                System.out.println("- Login: " + adm.getLogin());
            }
        }
    }

    // Método para associar um ADM a um sistema
    public void associarADMaoSistema(String admLogin, String sistemaNome) {
        if (admDAO.associarADMaoSistema(admLogin, sistemaNome)) {
            System.out.println("ADM associado ao sistema com sucesso!");
        } else {
            System.out.println("Erro ao associar o ADM ao sistema.");
        }
    }
}