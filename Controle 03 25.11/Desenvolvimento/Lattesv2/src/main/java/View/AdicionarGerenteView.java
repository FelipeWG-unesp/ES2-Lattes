package View;

import javax.swing.*;
import java.awt.*;
import controller.SistemaController;

public class AdicionarGerenteView extends JFrame {
    private JTextField loginField;
    private JPasswordField senhaField;
    private JTextField sistemaNomeField;
    private JButton salvarButton;
    private JButton cancelarButton;
    private SistemaController sistemaController;

    public AdicionarGerenteView() {
        sistemaController = new SistemaController();

        setTitle("Adicionar Novo Administrador");
        setSize(450, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Painel principal
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Painel do título
        JPanel titlePanel = new JPanel();
        JLabel titleLabel = new JLabel("Cadastro de Administrador");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titlePanel.add(titleLabel);

        // Painel de campos
        JPanel fieldsPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        fieldsPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));

        // Login
        JLabel loginLabel = new JLabel("Login do Administrador:");
        loginField = new JTextField();
        loginField.setPreferredSize(new Dimension(200, 25));

        // Senha
        JLabel senhaLabel = new JLabel("Senha:");
        senhaField = new JPasswordField();
        senhaField.setPreferredSize(new Dimension(200, 25));

        // Nome do Sistema
        JLabel sistemaNomeLabel = new JLabel("Nome do Sistema:");
        sistemaNomeField = new JTextField();
        sistemaNomeField.setPreferredSize(new Dimension(200, 25));

        // Adiciona campos ao painel
        fieldsPanel.add(loginLabel);
        fieldsPanel.add(loginField);
        fieldsPanel.add(senhaLabel);
        fieldsPanel.add(senhaField);
        fieldsPanel.add(sistemaNomeLabel);
        fieldsPanel.add(sistemaNomeField);

        // Painel de botões
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        salvarButton = createStyledButton("Salvar");
        cancelarButton = createStyledButton("Cancelar");

        buttonPanel.add(salvarButton);
        buttonPanel.add(cancelarButton);

        // Adiciona painéis ao painel principal
        mainPanel.add(titlePanel);
        mainPanel.add(Box.createVerticalStrut(10));
        mainPanel.add(fieldsPanel);
        mainPanel.add(Box.createVerticalStrut(10));
        mainPanel.add(buttonPanel);

        // Adiciona painel principal à janela
        add(mainPanel);

        // Configuração dos listeners
        salvarButton.addActionListener(e -> salvarGerenteESistema());
        cancelarButton.addActionListener(e -> dispose());
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.PLAIN, 14));
        button.setPreferredSize(new Dimension(120, 35));
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return button;
    }

    private void salvarGerenteESistema() {
        String loginADM = loginField.getText();
        String senhaADM = new String(senhaField.getPassword());
        String nomeSistema = sistemaNomeField.getText();

        if (loginADM.isEmpty() || senhaADM.isEmpty() || nomeSistema.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Por favor, preencha todos os campos.", 
                "Campos Obrigatórios", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }

        boolean sucesso = sistemaController.criarGerenteESistema(loginADM, senhaADM, nomeSistema);

        if (sucesso) {
            JOptionPane.showMessageDialog(this, 
                "Administrador e Sistema criados com sucesso!", 
                "Cadastro Realizado", 
                JOptionPane.INFORMATION_MESSAGE);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, 
                "Erro ao criar administrador e sistema.", 
                "Erro", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
}
