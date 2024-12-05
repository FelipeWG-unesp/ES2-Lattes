package Model;

public class Curriculo {
    
    
    private String usuarioCpf; // Parte da chave composta
    private String titulo; // Parte da chave composta
    private String dadosPessoais;
    private String formacaoAcademica;
    private String producoesAcademicas;
    private String premios;

    public Curriculo(String usuarioCpf, String titulo, String dadosPessoais, String formacaoAcademica, String producoesAcademicas, String premios) {
        this.usuarioCpf = usuarioCpf;
        this.titulo = titulo;
        this.dadosPessoais = dadosPessoais;
        this.formacaoAcademica = formacaoAcademica;
        this.producoesAcademicas = producoesAcademicas;
        this.premios = premios;
    }

    public String getUsuarioCpf() {
        return usuarioCpf;
    }

    public void setUsuarioCpf(String usuarioCpf) {
        this.usuarioCpf = usuarioCpf;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDadosPessoais() {
        return dadosPessoais;
    }

    public void setDadosPessoais(String dadosPessoais) {
        this.dadosPessoais = dadosPessoais;
    }

    public String getFormacaoAcademica() {
        return formacaoAcademica;
    }

    public void setFormacaoAcademica(String formacaoAcademica) {
        this.formacaoAcademica = formacaoAcademica;
    }

    public String getProducoesAcademicas() {
        return producoesAcademicas;
    }

    public void setProducoesAcademicas(String producoesAcademicas) {
        this.producoesAcademicas = producoesAcademicas;
    }

    public String getPremios() {
        return premios;
    }

    public void setPremios(String premios) {
        this.premios = premios;
    }

    
    
}
