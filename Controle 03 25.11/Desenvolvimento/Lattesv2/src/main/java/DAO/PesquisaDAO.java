/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Model.Pesquisa;
import auxx.Conexao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PesquisaDAO {

    // Método para adicionar uma pesquisa
    public boolean addPesquisa(Pesquisa pesquisa) {
        String sql = "INSERT INTO Pesquisa (curriculo_usuario_cpf, curriculo_titulo, titulo, descricao) VALUES (?, ?, ?, ?)";
        try (Connection conn = Conexao.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, pesquisa.getCurriculoUsuarioCpf());
            ps.setString(2, pesquisa.getCurriculoTitulo());
            ps.setString(3, pesquisa.getTitulo());
            ps.setString(4, pesquisa.getDescricao());

            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Método para editar uma pesquisa
    public boolean editarPesquisa(Pesquisa pesquisa) {
        String sql = "UPDATE Pesquisa SET descricao = ? WHERE curriculo_usuario_cpf = ? AND curriculo_titulo = ? AND titulo = ?";
        try (Connection conn = Conexao.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, pesquisa.getDescricao());
            ps.setString(2, pesquisa.getCurriculoUsuarioCpf());
            ps.setString(3, pesquisa.getCurriculoTitulo());
            ps.setString(4, pesquisa.getTitulo());

            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Método para validar se uma pesquisa existe
    public boolean validarPesquisa(String curriculoUsuarioCpf, String curriculoTitulo, String titulo) {
        String sql = "SELECT COUNT(*) AS total FROM Pesquisa WHERE curriculo_usuario_cpf = ? AND curriculo_titulo = ? AND titulo = ?";
        try (Connection conn = Conexao.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, curriculoUsuarioCpf);
            ps.setString(2, curriculoTitulo);
            ps.setString(3, titulo);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("total") > 0;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public List<Pesquisa> buscarPesquisasPorCurriculo(String usuarioCpf, String curriculoTitulo) {
        String sql = "SELECT * FROM Pesquisa WHERE curriculo_usuario_cpf = ? AND curriculo_titulo = ?";
        List<Pesquisa> pesquisas = new ArrayList<>();
        try (Connection conn = Conexao.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, usuarioCpf);
            ps.setString(2, curriculoTitulo);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Pesquisa pesquisa = new Pesquisa(
                        rs.getString("curriculo_usuario_cpf"),
                        rs.getString("curriculo_titulo"),
                        rs.getString("titulo"),
                        rs.getString("descricao")
                );
                pesquisas.add(pesquisa);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pesquisas;
    }


    
    
}