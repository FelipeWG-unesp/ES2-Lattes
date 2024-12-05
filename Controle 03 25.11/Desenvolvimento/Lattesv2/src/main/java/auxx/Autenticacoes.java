/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package auxx;

/**
 *
 * @author gusan
 */
public class Autenticacoes {
    
    public static boolean validarCPF(String cpf) {
        // Verifica se o CPF é nulo, possui exatamente 11 caracteres e se é numérico
        return cpf != null && cpf.matches("\\d{11}");
    }
    
    public static boolean validarEmail(String email) {
        if (email == null || email.isEmpty()) {
            return false; // Verifica se o e-mail não está vazio ou nulo
        }

        // Expressão regular para validar o formato do e-mail
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@" +
                            "(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

        return email.matches(emailRegex); // Retorna true se o formato do e-mail for válido
    }
}
