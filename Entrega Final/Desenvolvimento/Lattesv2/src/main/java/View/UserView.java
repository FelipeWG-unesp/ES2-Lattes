package View;

import DAO.CurriculoDAO;
import Model.Curriculo;
import Model.Usuario;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.List;

public class UserView extends JFrame {

    private String nomeUsuario;
    private String usuarioCpf;

    public UserView(String nomeUsuario, String usuarioCpf) {
        this.nomeUsuario = nomeUsuario;
        this.usuarioCpf = usuarioCpf;

        setTitle("Sistema de Currículos - Painel do Usuário");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel welcomePanel = new JPanel();
        JLabel welcomeLabel = new JLabel("Bem-vindo, " + nomeUsuario + "!");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 24));
        welcomePanel.add(welcomeLabel);

        JPanel buttonPanel = new JPanel(new GridLayout(5, 1, 10, 10));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));

        JButton visualizarCurriculosButton = createStyledButton("Visualizar Currículos");
        JButton criarEditarCurriculoButton = createStyledButton("Criar/Editar Currículo");
        JButton logoutButton = createStyledButton("Sair");

        visualizarCurriculosButton.addActionListener(e -> {
            new VisualizarCurriculosView(usuarioCpf).setVisible(true);
        });

        criarEditarCurriculoButton.addActionListener(e -> {
            new CurriculoView(usuarioCpf).setVisible(true);
        });

        logoutButton.addActionListener(e -> {
            int option = JOptionPane.showConfirmDialog(this,
                    "Deseja realmente sair?",
                    "Confirmação",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE);

            if (option == JOptionPane.YES_OPTION) {
                dispose();
                new LoginView(new controller.SistemaController()).setVisible(true);
            }
        });

        buttonPanel.add(visualizarCurriculosButton);
        buttonPanel.add(criarEditarCurriculoButton);
        buttonPanel.add(logoutButton);

        mainPanel.add(welcomePanel);
        mainPanel.add(Box.createVerticalStrut(20));
        mainPanel.add(buttonPanel);

        add(mainPanel);
    }

    private Curriculo selecionarCurriculo(String cpf) {
        // Utiliza a função getCurriculosByCpf da CurriculoDAO para obter a lista de currículos
        CurriculoDAO curriculoDAO = new CurriculoDAO();
        List<Curriculo> curriculos = curriculoDAO.getCurriculosByCpf(cpf);

        if (curriculos.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Não há currículos associados a esse CPF.",
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);
            return null;
        }

        JComboBox<Curriculo> comboBoxCurriculos = new JComboBox<>();
        for (Curriculo curriculo : curriculos) {
            comboBoxCurriculos.addItem(curriculo);
        }

        int option = JOptionPane.showOptionDialog(this,
                new Object[]{"Selecione o currículo:", comboBoxCurriculos},
                "Selecionar Currículo",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.PLAIN_MESSAGE,
                null,
                new Object[]{},
                null);

        if (option == JOptionPane.OK_OPTION) {
            return (Curriculo) comboBoxCurriculos.getSelectedItem();
        }

        return null;
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.PLAIN, 14));
        button.setPreferredSize(new Dimension(200, 40));
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return button;
    }
}
