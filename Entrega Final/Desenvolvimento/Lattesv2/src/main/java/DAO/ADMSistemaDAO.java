/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Model.ADMSistema;
import auxx.Conexao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ADMSistemaDAO {

    // Método para associar um ADM a um Sistema
    public boolean cadastrarADMSistema(ADMSistema admSistema) {
        String sql = "INSERT INTO ADM_Sistema (adm_login, sistema_nome) VALUES (?, ?)";
        try (Connection conn = Conexao.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, admSistema.getAdmLogin());
            ps.setString(2, admSistema.getSistemaNome());

            ps.executeUpdate();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Método para remover a associação entre ADM e Sistema
    public boolean removerADMSistema(String admLogin, String sistemaNome) {
        String sql = "DELETE FROM ADM_Sistema WHERE adm_login = ? AND sistema_nome = ?";
        try (Connection conn = Conexao.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, admLogin);
            ps.setString(2, sistemaNome);

            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

}