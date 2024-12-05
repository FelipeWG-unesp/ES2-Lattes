/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author gusan
 */
public class Relatorio {
    private String sistemaNome; // Parte da chave composta
    private String tipo; // Parte da chave composta
    private String periodo; // Parte da chave composta
    private String conteudo;

    public Relatorio(String sistemaNome, String tipo, String periodo, String conteudo) {
        this.sistemaNome = sistemaNome;
        this.tipo = tipo;
        this.periodo = periodo;
        this.conteudo = conteudo;
    }

    public String getSistemaNome() {
        return sistemaNome;
    }

    public void setSistemaNome(String sistemaNome) {
        this.sistemaNome = sistemaNome;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getPeriodo() {
        return periodo;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }

    public String getConteudo() {
        return conteudo;
    }

    public void setConteudo(String conteudo) {
        this.conteudo = conteudo;
    }
}