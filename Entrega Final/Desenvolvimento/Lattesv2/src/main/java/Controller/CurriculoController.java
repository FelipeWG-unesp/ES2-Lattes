/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import DAO.CurriculoDAO;
import Model.Curriculo;
import java.util.List;

public class CurriculoController {

    private CurriculoDAO curriculoDAO;

    public CurriculoController() {
        this.curriculoDAO = new CurriculoDAO();
    }

    // Método para criar um novo currículo
    public void criarCurriculo(String usuarioCpf, String titulo, String dadosPessoais, String formacaoAcademica,
                               String producoesAcademicas, String premios) {
        Curriculo curriculo = new Curriculo(usuarioCpf, titulo, dadosPessoais, formacaoAcademica,
                                            producoesAcademicas, premios);
        if (curriculoDAO.cadastrarCurriculo(curriculo)) {
            System.out.println("Currículo criado com sucesso!");
        } else {
            System.out.println("Erro ao criar o currículo.");
        }
    }

    // Editar uma seção de um currículo específico
    public boolean editarSecao(String usuarioCpf, String titulo, String secao, String dados) {
        boolean sucesso = curriculoDAO.editarSecao(usuarioCpf, titulo, secao, dados);
        if (sucesso) {
            System.out.println("Seção editada com sucesso!");
        } else {
            System.out.println("Erro ao editar a seção do currículo.");
        }
        return sucesso;
    }

    // Função para buscar currículos de um usuário
    public List<Curriculo> buscarCurriculosPorUsuario(String usuarioCpf) {
        List<Curriculo> curriculos = curriculoDAO.buscarCurriculosPorUsuario(usuarioCpf);
        if (curriculos.isEmpty()) {
            System.out.println("Nenhum currículo encontrado para este usuário.");
        } else {
            System.out.println("Currículos encontrados:");
            for (Curriculo curriculo : curriculos) {
                System.out.println("- Título: " + curriculo.getTitulo() +
                                   ", Dados Pessoais: " + curriculo.getDadosPessoais());
            }
        }
        return curriculos;
    }

    // Exibir o conteúdo de um currículo específico
    public Curriculo exibirConteudo(String usuarioCpf, String titulo) {
        Curriculo curriculo = curriculoDAO.buscarCurriculo(usuarioCpf, titulo);
        if (curriculo != null) {
            System.out.println("Conteúdo do currículo encontrado:");
            System.out.println("- Título: " + curriculo.getTitulo());
            System.out.println("- Dados Pessoais: " + curriculo.getDadosPessoais());
            System.out.println("- Formação Acadêmica: " + curriculo.getFormacaoAcademica());
            System.out.println("- Produções Acadêmicas: " + curriculo.getProducoesAcademicas());
            System.out.println("- Prêmios: " + curriculo.getPremios());
        } else {
            System.out.println("Currículo não encontrado.");
        }
        return curriculo;
    }
}