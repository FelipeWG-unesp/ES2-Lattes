/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author gusan
 */
public class Sistema {
    
    private String nome;


    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Sistema(String nome) {
        this.nome = nome;
    }
    
    
    
    public void cadastrarUsuario(String nome, String email, String cpf, String senha){
        Usuario usuario = new Usuario(nome,email,cpf,senha);
    }
    
    //public boolean fazerLogin()
}
