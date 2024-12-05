package Controller;

import DAO.PesquisaDAO;
import Model.Pesquisa;
import java.util.List;
import java.util.ArrayList;

public class PesquisaController {

    private PesquisaDAO pesquisaDAO;
    private List<Pesquisa> pesquisas;

    public PesquisaController() {
        this.pesquisaDAO = new PesquisaDAO();
        this.pesquisas = new ArrayList<>();
    }

    // Método para adicionar uma pesquisa
    public boolean addPesquisa(String curriculoUsuarioCpf, String curriculoTitulo, String titulo, String descricao) {
        Pesquisa pesquisa = new Pesquisa(curriculoUsuarioCpf, curriculoTitulo, titulo, descricao);
        // Adiciona a pesquisa à lista
        pesquisas.add(pesquisa);
        // Também persiste no banco de dados
        return pesquisaDAO.addPesquisa(pesquisa);
    }

    // Método para editar uma pesquisa
    public boolean editarPesquisa(String curriculoUsuarioCpf, String curriculoTitulo, String titulo, String descricao) {
        for (Pesquisa pesquisa : pesquisas) {
            if (pesquisa.getCurriculoUsuarioCpf().equals(curriculoUsuarioCpf) &&
                    pesquisa.getCurriculoTitulo().equals(curriculoTitulo) &&
                    pesquisa.getTitulo().equals(titulo)) {
                // Atualiza os dados na lista
                pesquisa.setDescricao(descricao);
                // Atualiza no banco de dados
                return pesquisaDAO.editarPesquisa(pesquisa);
            }
        }
        return false;
    }

    // Método para validar se uma pesquisa existe
    public boolean validarPesquisa(String curriculoUsuarioCpf, String curriculoTitulo, String titulo) {
        for (Pesquisa pesquisa : pesquisas) {
            if (pesquisa.getCurriculoUsuarioCpf().equals(curriculoUsuarioCpf) &&
                    pesquisa.getCurriculoTitulo().equals(curriculoTitulo) &&
                    pesquisa.getTitulo().equals(titulo)) {
                return true;
            }
        }
        boolean existe = pesquisaDAO.validarPesquisa(curriculoUsuarioCpf, curriculoTitulo, titulo);
        return existe;
    }

    // Método para buscar pesquisas de um currículo específico
    public List<Pesquisa> buscarPesquisasPorCurriculo(String usuarioCpf, String curriculoTitulo) {
        // Primeiro verifica se as pesquisas estão na lista
        List<Pesquisa> pesquisasFiltradas = new ArrayList<>();
        for (Pesquisa pesquisa : pesquisas) {
            if (pesquisa.getCurriculoUsuarioCpf().equals(usuarioCpf) &&
                    pesquisa.getCurriculoTitulo().equals(curriculoTitulo)) {
                pesquisasFiltradas.add(pesquisa);
            }
        }
        // Se não encontrou na lista, busca no banco de dados e adiciona à lista
        if (pesquisasFiltradas.isEmpty()) {
            List<Pesquisa> pesquisasBanco = pesquisaDAO.buscarPesquisasPorCurriculo(usuarioCpf, curriculoTitulo);
            pesquisas.addAll(pesquisasBanco);
            return pesquisasBanco;
        }
        return pesquisasFiltradas;
    }
}
