package Controller;

import DAO.RelatorioDAO;
import Model.Relatorio;
import java.util.List;
import java.util.ArrayList;

public class RelatorioController {

    private RelatorioDAO relatorioDAO;
    private List<Relatorio> relatorios;

    public RelatorioController() {
        this.relatorioDAO = new RelatorioDAO();
        this.relatorios = new ArrayList<>();
    }

    // Método para gerar um relatório
    public void gerarRelatorio(String sistemaNome, String tipo, String periodo, String conteudo) {
        Relatorio relatorio = new Relatorio(sistemaNome, tipo, periodo, conteudo);
        // Adiciona o relatório à lista
        relatorios.add(relatorio);
        // Persiste no banco de dados
        if (relatorioDAO.gerarRelatorio(relatorio)) {
            System.out.println("Relatório gerado com sucesso!");
        } else {
            System.out.println("Erro ao gerar o relatório.");
        }
    }

    // Método para listar todos os relatórios
    public void listarRelatorios() {
        // Verifica se a lista local está vazia, caso contrário, busca no banco de dados
        if (relatorios.isEmpty()) {
            relatorios = relatorioDAO.listarRelatorios();
        }

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
        // Filtra a lista localmente
        List<Relatorio> relatoriosFiltrados = new ArrayList<>();
        for (Relatorio relatorio : relatorios) {
            if (relatorio.getSistemaNome().equals(sistemaNome)) {
                relatoriosFiltrados.add(relatorio);
            }
        }

        // Se não encontrar, busca no banco de dados
        if (relatoriosFiltrados.isEmpty()) {
            relatoriosFiltrados = relatorioDAO.listarRelatoriosPorSistema(sistemaNome);
            relatorios.addAll(relatoriosFiltrados); // Atualiza a lista local
        }

        if (relatoriosFiltrados.isEmpty()) {
            System.out.println("Nenhum relatório encontrado para o sistema: " + sistemaNome);
        } else {
            System.out.println("Relatórios do sistema " + sistemaNome + ":");
            for (Relatorio relatorio : relatoriosFiltrados) {
                System.out.println("- Tipo: " + relatorio.getTipo() +
                        ", Período: " + relatorio.getPeriodo());
                System.out.println("  Conteúdo: " + relatorio.getConteudo());
            }
        }
    }

    // Método para excluir um relatório
    public void excluirRelatorio(String sistemaNome, String tipo, String periodo) {
        // Procura o relatório na lista e remove
        Relatorio relatorioRemovido = null;
        for (Relatorio relatorio : relatorios) {
            if (relatorio.getSistemaNome().equals(sistemaNome) &&
                    relatorio.getTipo().equals(tipo) &&
                    relatorio.getPeriodo().equals(periodo)) {
                relatorioRemovido = relatorio;
                break;
            }
        }

        if (relatorioRemovido != null) {
            relatorios.remove(relatorioRemovido); // Remove da lista
        }

        // Exclui do banco de dados
        if (relatorioDAO.excluirRelatorio(sistemaNome, tipo, periodo)) {
            System.out.println("Relatório excluído com sucesso.");
        } else {
            System.out.println("Erro ao excluir o relatório.");
        }
    }
}
