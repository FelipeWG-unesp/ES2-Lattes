/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import DAO.RelatorioDAO;
import Model.Relatorio;

import java.util.List;

public class RelatorioController {

    private RelatorioDAO relatorioDAO;

    public RelatorioController() {
        this.relatorioDAO = new RelatorioDAO();
    }

    // Método para gerar um relatório
    public void gerarRelatorio(String sistemaNome, String tipo, String periodo, String conteudo) {
        Relatorio relatorio = new Relatorio(sistemaNome, tipo, periodo, conteudo);
        if (relatorioDAO.gerarRelatorio(relatorio)) {
            System.out.println("Relatório gerado com sucesso!");
        } else {
            System.out.println("Erro ao gerar o relatório.");
        }
    }

    // Método para listar todos os relatórios
    public void listarRelatorios() {
        List<Relatorio> relatorios = relatorioDAO.listarRelatorios();
        if (relatorios.isEmpty()) {
            System.out.println("Nenhum relatório encontrado.");
        } else {
            System.out.println("Relatórios:");
            for (Relatorio relatorio : relatorios) {
                System.out.println("- Sistema: " + relatorio.getSistemaNome() +
                                   ", Tipo: " + relatorio.getTipo() +
                                   ", Período: " + relatorio.getPeriodo());
                System.out.println("  Conteúdo: " + relatorio.getConteudo());
            }
        }
    }

    // Método para listar relatórios de um sistema específico
    public void listarRelatoriosPorSistema(String sistemaNome) {
        List<Relatorio> relatorios = relatorioDAO.listarRelatoriosPorSistema(sistemaNome);
        if (relatorios.isEmpty()) {
            System.out.println("Nenhum relatório encontrado para o sistema: " + sistemaNome);
        } else {
            System.out.println("Relatórios do sistema " + sistemaNome + ":");
            for (Relatorio relatorio : relatorios) {
                System.out.println("- Tipo: " + relatorio.getTipo() +
                                   ", Período: " + relatorio.getPeriodo());
                System.out.println("  Conteúdo: " + relatorio.getConteudo());
            }
        }
    }

    // Método para excluir um relatório
    public void excluirRelatorio(String sistemaNome, String tipo, String periodo) {
        if (relatorioDAO.excluirRelatorio(sistemaNome, tipo, periodo)) {
            System.out.println("Relatório excluído com sucesso.");
        } else {
            System.out.println("Erro ao excluir o relatório.");
        }
    }
}