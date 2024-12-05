/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author gusan
 */
public class ADMSistema {
    private String admLogin; // Parte da chave composta
    private String sistemaNome; // Parte da chave composta

    public ADMSistema(String admLogin, String sistemaNome) {
        this.admLogin = admLogin;
        this.sistemaNome = sistemaNome;
    }

    public String getAdmLogin() {
        return admLogin;
    }

    public void setAdmLogin(String admLogin) {
        this.admLogin = admLogin;
    }

    public String getSistemaNome() {
        return sistemaNome;
    }

    public void setSistemaNome(String sistemaNome) {
        this.sistemaNome = sistemaNome;
    }
}