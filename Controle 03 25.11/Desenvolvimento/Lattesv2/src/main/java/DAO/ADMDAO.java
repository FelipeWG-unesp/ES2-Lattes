/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Model.ADM;
import auxx.Conexao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ADMDAO {

    // Método para editar dados de um ADM
    public boolean editarADM(ADM adm) {
        String sql = "UPDATE ADM SET senha = ? WHERE login = ?";
        try (Connection conn = Conexao.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, adm.getSenha());
            ps.setString(2, adm.getLogin());

            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Método para validar as credenciais de um ADM
    public ADM validarCredenciaisADM(String login, String senha) {
        String sql = "SELECT * FROM ADM WHERE login = ? AND senha = ?";
        try (Connection conn = Conexao.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, login);
            ps.setString(2, senha);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new ADM(
                        rs.getString("login"),
                        rs.getString("senha")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Método para listar todos os ADMs
    public List<ADM> listarADMs() {
        String sql = "SELECT * FROM ADM";
        List<ADM> adms = new ArrayList<>();
        try (Connection conn = Conexao.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ADM adm = new ADM(
                        rs.getString("login"),
                        rs.getString("senha")
                );
                adms.add(adm);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return adms;
    }

    // Método para associar um ADM a um Sistema
public boolean associarADMaoSistema(String admLogin, String sistemaNome) {
    String sql = "INSERT INTO ADM_Sistema (adm_login, sistema_nome) VALUES (?, ?)";
    try (PreparedStatement ps = Conexao.getConexao().prepareStatement(sql)) {
        ps.setString(1, admLogin);
        ps.setString(2, sistemaNome);
        ps.executeUpdate();
        return true;
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return false;
}


    // Método para obter o nome do sistema associado a um ADM
    public String obterSistemaParaADM(String admLogin) {
        String sql = "SELECT sistema_nome FROM ADM_Sistema WHERE adm_login = ?";
        try (Connection conn = Conexao.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, admLogin);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getString("sistema_nome");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Retorna null se nenhum sistema for encontrado
    }

    // Método para cadastrar um novo ADM
    public boolean cadastrarADM(ADM adm) {
        String sql = "INSERT INTO ADM (login, senha) VALUES (?, ?)";
        try (Connection conn = Conexao.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, adm.getLogin());
            ps.setString(2, adm.getSenha());

            ps.executeUpdate();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}

