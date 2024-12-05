package View;

import javax.swing.*;
import java.awt.*;

public class ADMView extends JFrame {

    public ADMView(String login) {
        setTitle("Sistema de Currículos - Painel do Administrador");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Painel principal
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Painel de boas-vindas
        JPanel welcomePanel = new JPanel();
        JLabel welcomeLabel = new JLabel("Painel do Administrador - " + login);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 24));
        welcomePanel.add(welcomeLabel);

        // Painel de botões
        JPanel buttonPanel = new JPanel(new GridLayout(4, 1, 10, 10));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));

        // Criando botões
        JButton gerenciarUsuariosButton = createStyledButton("Gerenciar Usuários");
        JButton logoutButton = createStyledButton("Sair");

        // Adicionando ações aos botões
        gerenciarUsuariosButton.addActionListener(e -> {
            new GerenciarUsuariosView(login).setVisible(true);  // Passando login do ADM
        });

        logoutButton.addActionListener(e -> {
            int option = JOptionPane.showConfirmDialog(this,
                "Deseja realmente sair?",
                "Confirmação",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);
            
            if (option == JOptionPane.YES_OPTION) {
                dispose();
                new LoginView(new Controller.SistemaController()).setVisible(true);
            }
        });

        // Adicionando botões ao painel
        buttonPanel.add(gerenciarUsuariosButton);
        buttonPanel.add(logoutButton);

        // Adicionando painéis ao painel principal
        mainPanel.add(welcomePanel);
        mainPanel.add(Box.createVerticalStrut(20));
        mainPanel.add(buttonPanel);

        // Adicionando painel principal à janela
        add(mainPanel);
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