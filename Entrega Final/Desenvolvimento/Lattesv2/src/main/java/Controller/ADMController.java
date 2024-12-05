package Controller;

import DAO.ADMDAO;
import Model.ADM;

import java.util.ArrayList;
import java.util.List;

public class ADMController {

    private ADMDAO admDAO;
    private List<ADM> adms;  // Lista interna para armazenar os dados dos ADMs

    public ADMController() {
        this.admDAO = new ADMDAO();
        this.adms = new ArrayList<>();
        carregarADMs();  // Carregar os ADMs do banco para a lista local
    }

    // Carregar os ADMs do banco para a lista interna
    private void carregarADMs() {
        List<ADM> admsBanco = admDAO.listarADMs();
        if (admsBanco != null && !admsBanco.isEmpty()) {
            adms.addAll(admsBanco);
        }
    }

    // Método para cadastrar um novo ADM na lista e no banco
    public void cadastrarADM(String login, String senha) {
        ADM adm = new ADM(login, senha);
        adms.add(adm);  // Adiciona na lista local

        // Agora, salva no banco
        if (admDAO.cadastrarADM(adm)) {
            System.out.println("ADM cadastrado com sucesso!");
        } else {
            System.out.println("Erro ao cadastrar o ADM.");
        }
    }

    // Método para editar um ADM na lista local e no banco
    public void editarADM(String login, String novaSenha) {
        // Busca na lista interna
        for (ADM adm : adms) {
            if (adm.getLogin().equals(login)) {
                adm.setSenha(novaSenha);  // Atualiza na lista interna

                // Agora, salva a alteração no banco
                boolean sucesso = admDAO.editarADM(adm);
                if (sucesso) {
                    System.out.println("ADM editado com sucesso!");
                } else {
                    System.out.println("Falha ao editar o ADM.");
                }
                return;
            }
        }
        System.out.println("ADM não encontrado.");
    }

    // Método para validar credenciais de um ADM na lista interna
    public boolean validarCredenciaisADM(String login, String senha) {
        for (ADM adm : adms) {
            if (adm.getLogin().equals(login) && adm.getSenha().equals(senha)) {
                System.out.println("Credenciais válidas para o ADM: " + adm.getLogin());
                return true;
            }
        }
        System.out.println("Credenciais inválidas.");
        return false;
    }

    // Método para listar todos os ADMs da lista interna
    public void listarADMs() {
        if (adms.isEmpty()) {
            System.out.println("Nenhum ADM cadastrado.");
        } else {
            System.out.println("Lista de ADMs:");
            for (ADM adm : adms) {
                System.out.println("- Login: " + adm.getLogin());
            }
        }
    }

    // Método para associar um ADM a um sistema na lista interna e no banco
    public void associarADMaoSistema(String admLogin, String sistemaNome) {
        for (ADM adm : adms) {
            if (adm.getLogin().equals(admLogin)) {
                if (admDAO.associarADMaoSistema(admLogin, sistemaNome)) {
                    System.out.println("ADM associado ao sistema com sucesso!");
                } else {
                    System.out.println("Erro ao associar o ADM ao sistema.");
                }
                return;
            }
        }
        System.out.println("ADM não encontrado.");
    }
}
