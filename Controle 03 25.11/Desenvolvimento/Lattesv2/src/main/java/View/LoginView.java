package View;

import Model.ADM;
import Model.Usuario;
import controller.SistemaController;

import javax.swing.*;
import java.awt.*;

public class LoginView extends JFrame {
    private final JTextField emailField;
    private final JPasswordField passwordField;
    private final JButton loginButton;
    private final JButton adicionarGerenteButton;

    public LoginView(SistemaController sistemaController) {
        // Configurações básicas da janela
        setTitle("Sistema de Currículos - Login");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Painel principal com margens
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Painel para o título
        JPanel titlePanel = new JPanel();
        JLabel titleLabel = new JLabel("Bem-vindo ao Sistema");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titlePanel.add(titleLabel);

        // Painel para os campos
        JPanel fieldsPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        fieldsPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));

        JLabel emailLabel = new JLabel("Email/Login:");
        emailField = new JTextField();
        emailField.setPreferredSize(new Dimension(200, 25));

        JLabel passwordLabel = new JLabel("Senha:");
        passwordField = new JPasswordField();
        passwordField.setPreferredSize(new Dimension(200, 25));

        fieldsPanel.add(emailLabel);
        fieldsPanel.add(emailField);
        fieldsPanel.add(passwordLabel);
        fieldsPanel.add(passwordField);

        // Painel para os botões
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        loginButton = new JButton("Entrar");
        adicionarGerenteButton = new JButton("Adicionar ADM");

        // Estilizando botões
        loginButton.setPreferredSize(new Dimension(120, 30));
        adicionarGerenteButton.setPreferredSize(new Dimension(120, 30));

        buttonPanel.add(loginButton);
        buttonPanel.add(adicionarGerenteButton);

        // Adicionando painéis ao painel principal
        mainPanel.add(titlePanel);
        mainPanel.add(Box.createVerticalStrut(10));
        mainPanel.add(fieldsPanel);
        mainPanel.add(Box.createVerticalStrut(10));
        mainPanel.add(buttonPanel);

        // Adicionando painel principal à janela
        add(mainPanel);

        // Configuração de eventos
        loginButton.addActionListener(e -> realizarLogin(sistemaController));
        adicionarGerenteButton.addActionListener(e -> abrirAdicionarGerenteView());

        // Tornar a janela visível
        setVisible(true);
    }

    private void realizarLogin(SistemaController sistemaController) {
        String emailOuLogin = emailField.getText();
        String senha = new String(passwordField.getPassword());

        if (emailOuLogin.isEmpty() || senha.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Por favor, preencha todos os campos",
                    "Campos Vazios",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        Object resultado = sistemaController.fazerLogin(emailOuLogin, senha);

        if (resultado instanceof Usuario) {
            Usuario usuario = (Usuario) resultado;
            JOptionPane.showMessageDialog(this,
                    "Bem-vindo, " + usuario.getNome(),
                    "Login Bem-sucedido",
                    JOptionPane.INFORMATION_MESSAGE);
            dispose();
            new UserView(usuario.getNome(),usuario.getCpf()).setVisible(true); // Passa CPF ao invés de ID
        } else if (resultado instanceof ADM) {
            ADM adm = (ADM) resultado;
            String sistemaNome = sistemaController.obterSistemaParaADM(adm.getLogin()); // Obter o nome do sistema
            if (sistemaNome != null) {
                JOptionPane.showMessageDialog(this,
                        "Bem-vindo, Administrador " + adm.getLogin(),
                        "Login Bem-sucedido",
                        JOptionPane.INFORMATION_MESSAGE);
                dispose();
                new ADMView(adm.getLogin()).setVisible(true); // Passa o nome do sistema
            } else {
                JOptionPane.showMessageDialog(this,
                        "Erro ao obter o sistema associado ao administrador.",
                        "Erro",
                        JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this,
                    "Credenciais inválidas. Tente novamente.",
                    "Erro de Login",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void abrirAdicionarGerenteView() {
        new AdicionarGerenteView().setVisible(true);
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        SwingUtilities.invokeLater(() -> new LoginView(new SistemaController()));
    }
}