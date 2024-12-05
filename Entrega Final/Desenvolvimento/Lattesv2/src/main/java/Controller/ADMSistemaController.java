/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import DAO.ADMSistemaDAO;
import Model.ADMSistema;

import java.util.List;

public class ADMSistemaController {

    private ADMSistemaDAO admSistemaDAO;

    public ADMSistemaController() {
        this.admSistemaDAO = new ADMSistemaDAO();
    }

    // Método para associar ADM a Sistema
    public void cadastrarADMSistema(String admLogin, String sistemaNome) {
        ADMSistema admSistema = new ADMSistema(admLogin, sistemaNome);
        if (admSistemaDAO.cadastrarADMSistema(admSistema)) {
            System.out.println("ADM " + admLogin + " associado ao Sistema " + sistemaNome + " com sucesso!");
        } else {
            System.out.println("Erro ao associar ADM ao Sistema.");
        }
    }

    // Método para remover associação entre ADM e Sistema
    public void removerADMSistema(String admLogin, String sistemaNome) {
        boolean sucesso = admSistemaDAO.removerADMSistema(admLogin, sistemaNome);
        if (sucesso) {
            System.out.println("Associação entre ADM " + admLogin + " e Sistema " + sistemaNome + " removida com sucesso!");
        } else {
            System.out.println("Falha ao remover a associação.");
        }
    }

}