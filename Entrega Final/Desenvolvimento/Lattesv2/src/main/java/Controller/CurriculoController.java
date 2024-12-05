package Controller;

import DAO.CurriculoDAO;
import Model.Curriculo;
import java.util.List;
import java.util.ArrayList;

public class CurriculoController {

    private CurriculoDAO curriculoDAO;
    private List<Curriculo> curriculos;

    public CurriculoController() {
        this.curriculoDAO = new CurriculoDAO();
        this.curriculos = new ArrayList<>();
    }

    // Método para criar um novo currículo
    public void criarCurriculo(String usuarioCpf, String titulo, String dadosPessoais, String formacaoAcademica,
                               String producoesAcademicas, String premios) {
        Curriculo curriculo = new Curriculo(usuarioCpf, titulo, dadosPessoais, formacaoAcademica,
                producoesAcademicas, premios);
        // Adiciona o currículo à lista
        curriculos.add(curriculo);
        // Também persiste no banco de dados
        curriculoDAO.cadastrarCurriculo(curriculo);
    }

    // Editar uma seção de um currículo específico
    public boolean editarSecao(String usuarioCpf, String titulo, String secao, String dados) {
        for (Curriculo curriculo : curriculos) {
            if (curriculo.getUsuarioCpf().equals(usuarioCpf) && curriculo.getTitulo().equals(titulo)) {
                // Atualiza os dados na lista
                if (secao.equals("dadosPessoais")) {
                    curriculo.setDadosPessoais(dados);
                } else if (secao.equals("formacaoAcademica")) {
                    curriculo.setFormacaoAcademica(dados);
                } else if (secao.equals("producoesAcademicas")) {
                    curriculo.setProducoesAcademicas(dados);
                } else if (secao.equals("premios")) {
                    curriculo.setPremios(dados);
                }
                // Atualiza no banco de dados
                return curriculoDAO.editarSecao(usuarioCpf, titulo, secao, dados);
            }
        }
        return false;
    }

    // Função para buscar currículos de um usuário
    public List<Curriculo> buscarCurriculosPorUsuario(String usuarioCpf) {
        // Primeiro verifica se os currículos estão na lista
        for (Curriculo curriculo : curriculos) {
            if (curriculo.getUsuarioCpf().equals(usuarioCpf)) {
                return curriculos;
            }
        }
        // Se não estiverem na lista, busca do banco e adiciona à lista
        List<Curriculo> curriculosBanco = curriculoDAO.buscarCurriculosPorUsuario(usuarioCpf);
        curriculos.addAll(curriculosBanco);
        return curriculosBanco;
    }

    // Exibir o conteúdo de um currículo específico
    public Curriculo exibirConteudo(String usuarioCpf, String titulo) {
        // Primeiro tenta encontrar na lista
        for (Curriculo curriculo : curriculos) {
            if (curriculo.getUsuarioCpf().equals(usuarioCpf) && curriculo.getTitulo().equals(titulo)) {
                return curriculo;
            }
        }
        // Se não encontrado na lista, busca no banco e adiciona à lista
        Curriculo curriculo = curriculoDAO.buscarCurriculo(usuarioCpf, titulo);
        if (curriculo != null) {
            curriculos.add(curriculo);
        }
        return curriculo;
    }
}
