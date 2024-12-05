/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Model.Relatorio;
import auxx.Conexao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RelatorioDAO {

    // Método para gerar um relatório
    public boolean gerarRelatorio(Relatorio relatorio) {
        String sql = "INSERT INTO Relatorio (sistema_nome, tipo, periodo, conteudo) VALUES (?, ?, ?, ?)";
        try (Connection conn = Conexao.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, relatorio.getSistemaNome());
            ps.setString(2, relatorio.getTipo());
            ps.setString(3, relatorio.getPeriodo());
            ps.setString(4, relatorio.getConteudo());

            ps.executeUpdate();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Método para listar todos os relatórios
    public List<Relatorio> listarRelatorios() {
        String sql = "SELECT * FROM Relatorio";
        List<Relatorio> relatorios = new ArrayList<>();
        try (Connection conn = Conexao.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Relatorio relatorio = new Relatorio(
                        rs.getString("sistema_nome"),
                        rs.getString("tipo"),
                        rs.getString("periodo"),
                        rs.getString("conteudo")
                );
                relatorios.add(relatorio);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return relatorios;
    }

    // Método para buscar relatórios por sistema
    public List<Relatorio> listarRelatoriosPorSistema(String sistemaNome) {
        String sql = "SELECT * FROM Relatorio WHERE sistema_nome = ?";
        List<Relatorio> relatorios = new ArrayList<>();
        try (Connection conn = Conexao.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, sistemaNome);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Relatorio relatorio = new Relatorio(
                        rs.getString("sistema_nome"),
                        rs.getString("tipo"),
                        rs.getString("periodo"),
                        rs.getString("conteudo")
                );
                relatorios.add(relatorio);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return relatorios;
    }

    // Método para excluir um relatório
    public boolean excluirRelatorio(String sistemaNome, String tipo, String periodo) {
        String sql = "DELETE FROM Relatorio WHERE sistema_nome = ? AND tipo = ? AND periodo = ?";
        try (Connection conn = Conexao.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, sistemaNome);
            ps.setString(2, tipo);
            ps.setString(3, periodo);

            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}