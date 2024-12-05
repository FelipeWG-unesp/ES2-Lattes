/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Model.Sistema;
import auxx.Conexao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author gusan
 */
public class SistemaDAO {

    // Método para cadastrar um sistema
    public boolean cadastrarSistema(Sistema sistema) {
        String sql = "INSERT INTO Sistema (nome) VALUES (?)";
        try (Connection conn = Conexao.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, sistema.getNome());
            ps.executeUpdate();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Método para buscar um sistema pelo nome
    public Sistema buscarSistema(String nome) {
        String sql = "SELECT * FROM Sistema WHERE nome = ?";
        try (Connection conn = Conexao.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, nome);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new Sistema(rs.getString("nome"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Retorna null se o sistema não for encontrado
    }

    // Método para excluir um sistema pelo nome
    public boolean excluirSistema(String nome) {
        String sql = "DELETE FROM Sistema WHERE nome = ?";
        try (Connection conn = Conexao.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, nome);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}