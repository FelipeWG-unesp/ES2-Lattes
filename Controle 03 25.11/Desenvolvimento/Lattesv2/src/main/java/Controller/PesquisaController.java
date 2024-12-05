/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import DAO.PesquisaDAO;
import Model.Pesquisa;
import java.util.List;

public class PesquisaController {

    private PesquisaDAO pesquisaDAO;

    public PesquisaController() {
        this.pesquisaDAO = new PesquisaDAO();
    }

    // Método para adicionar uma pesquisa
    public boolean addPesquisa(String curriculoUsuarioCpf, String curriculoTitulo, String titulo, String descricao) {
        Pesquisa pesquisa = new Pesquisa(curriculoUsuarioCpf, curriculoTitulo, titulo, descricao);
        if (pesquisaDAO.addPesquisa(pesquisa)) {
            System.out.println("Pesquisa adicionada com sucesso!");
            return true;
        } else {
            System.out.println("Erro ao adicionar a pesquisa.");
            return false;
        }
    }

    // Método para editar uma pesquisa
    public boolean editarPesquisa(String curriculoUsuarioCpf, String curriculoTitulo, String titulo, String descricao) {
        Pesquisa pesquisa = new Pesquisa(curriculoUsuarioCpf, curriculoTitulo, titulo, descricao);
        boolean sucesso = pesquisaDAO.editarPesquisa(pesquisa);
        if (sucesso) {
            System.out.println("Pesquisa editada com sucesso!");
        } else {
            System.out.println("Falha ao editar a pesquisa.");
        }
        return sucesso;
    }

    // Método para validar se uma pesquisa existe
    public boolean validarPesquisa(String curriculoUsuarioCpf, String curriculoTitulo, String titulo) {
        boolean existe = pesquisaDAO.validarPesquisa(curriculoUsuarioCpf, curriculoTitulo, titulo);
        if (!existe) {
            System.out.println("Pesquisa não encontrada.");
        }
        return existe;
    }
    
    /*
    public boolean cadastrarPesquisa(String usuarioCpf, String titulo, String descricao) {
        Pesquisa pesquisa = new Pesquisa(usuarioCpf, titulo, descricao);
        return pesquisaDAO.cadastrarPesquisa(pesquisa);
    }
    */
    
    public List<Pesquisa> buscarPesquisasPorCurriculo(String usuarioCpf, String curriculoTitulo) {
        return pesquisaDAO.buscarPesquisasPorCurriculo(usuarioCpf, curriculoTitulo);
    }

}