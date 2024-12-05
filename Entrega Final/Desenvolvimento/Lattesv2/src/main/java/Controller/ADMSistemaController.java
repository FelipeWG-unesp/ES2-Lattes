package Controller;

import DAO.ADMSistemaDAO;
import Model.ADMSistema;

import java.util.List;
import java.util.ArrayList;

public class ADMSistemaController {

    private ADMSistemaDAO admSistemaDAO;
    private List<ADMSistema> admsSistema;

    public ADMSistemaController() {
        this.admSistemaDAO = new ADMSistemaDAO();
        this.admsSistema = new ArrayList<>();
        carregarADMSistemas();  // Carregar as associações do banco para a lista local
    }

    // Carregar as associações entre ADM e Sistema do banco para a lista interna
    private void carregarADMSistemas() {
        List<ADMSistema> admsSistemasBanco = admSistemaDAO.listarADMSistemas();
        if (admsSistemasBanco != null && !admsSistemasBanco.isEmpty()) {
            admsSistema.addAll(admsSistemasBanco);
        }
    }

    // Método para cadastrar uma associação entre ADM e Sistema na lista e no banco
    public void cadastrarADMSistema(String admLogin, String sistemaNome) {
        ADMSistema admSistema = new ADMSistema(admLogin, sistemaNome);
        this.admsSistema.add(admSistema);  // Adiciona na lista local

        // Agora, salva a associação no banco
        admSistemaDAO.cadastrarADMSistema(admSistema);
    }

    // Método para remover a associação entre ADM e Sistema na lista e no banco
    public void removerADMSistema(String admLogin, String sistemaNome) {
        // Busca na lista interna
        for (ADMSistema admSistema : admsSistema) {
            if (admSistema.getAdmLogin().equals(admLogin) && admSistema.getSistemaNome().equals(sistemaNome)) {
                admsSistema.remove(admSistema);  // Remove da lista interna

                // Agora, remove a associação no banco
                admSistemaDAO.removerADMSistema(admLogin, sistemaNome);
                return;
            }
        }
    }
}
