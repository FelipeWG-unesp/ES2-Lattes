/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author gusan
 */
public class Pesquisa {
    private String curriculoUsuarioCpf; // Parte da chave composta
    private String curriculoTitulo; // Parte da chave composta
    private String titulo; // Parte da chave composta
    private String descricao;

    public Pesquisa(String curriculoUsuarioCpf, String curriculoTitulo, String titulo, String descricao) {
        this.curriculoUsuarioCpf = curriculoUsuarioCpf;
        this.curriculoTitulo = curriculoTitulo;
        this.titulo = titulo;
        this.descricao = descricao;
    }

    public String getCurriculoUsuarioCpf() {
        return curriculoUsuarioCpf;
    }

    public void setCurriculoUsuarioCpf(String curriculoUsuarioCpf) {
        this.curriculoUsuarioCpf = curriculoUsuarioCpf;
    }

    public String getCurriculoTitulo() {
        return curriculoTitulo;
    }

    public void setCurriculoTitulo(String curriculoTitulo) {
        this.curriculoTitulo = curriculoTitulo;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}