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

    // Método para listar todas as associações entre ADM e Sistema
    public List<ADMSistema> listarADMSistemas() {
        List<ADMSistema> admsSistemaList = new ArrayList<>();
        String sql = "SELECT adm_login, sistema_nome FROM ADM_Sistema";
        try (Connection conn = Conexao.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                String admLogin = rs.getString("adm_login");
                String sistemaNome = rs.getString("sistema_nome");
                ADMSistema admSistema = new ADMSistema(admLogin, sistemaNome);
                admsSistemaList.add(admSistema);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return admsSistemaList;
    }
}
