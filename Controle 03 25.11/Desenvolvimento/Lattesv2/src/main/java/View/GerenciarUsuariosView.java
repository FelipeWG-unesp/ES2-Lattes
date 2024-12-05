package View;

import Controller.UsuarioController;
import Model.Usuario;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class GerenciarUsuariosView extends JFrame {
    private JTable tabelaUsuarios;
    private JButton adicionarButton;
    private JButton voltarButton;
    private UsuarioController usuarioController;
    private String administradorLogin;
    private String sistemaNome;

    public GerenciarUsuariosView(String administradorLogin) {
        this.administradorLogin = administradorLogin;
        this.usuarioController = new UsuarioController();

        // Obtém o nome do sistema associado ao administrador
        sistemaNome = usuarioController.obterSistemaPorAdm(administradorLogin);

        setTitle("Gerenciar Usuários");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Painel principal
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Título
        JLabel titleLabel = new JLabel("Gerenciamento de Usuários", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        // Tabela de usuários
        tabelaUsuarios = new JTable();
        tabelaUsuarios.setFont(new Font("Arial", Font.PLAIN, 12));
        tabelaUsuarios.setRowHeight(25);
        JScrollPane scrollPane = new JScrollPane(tabelaUsuarios);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Adiciona listener de duplo clique na tabela
        adicionarDoubleClickListener();

        // Painel de botões
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        adicionarButton = createStyledButton("Adicionar Usuário");
        voltarButton = createStyledButton("Voltar");

        // Adicionar ação ao botão "Adicionar Usuário"
        adicionarButton.addActionListener(e -> adicionarUsuario());
        voltarButton.addActionListener(e -> dispose());

        buttonPanel.add(adicionarButton);
        buttonPanel.add(voltarButton);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Adiciona o painel principal à janela
        add(mainPanel);

        // Carrega os usuários associados ao sistema do administrador
        carregarUsuarios();
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.PLAIN, 14));
        button.setPreferredSize(new Dimension(150, 35));
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return button;
    }

    private void adicionarUsuario() {
        JPanel panel = new JPanel(new GridLayout(4, 2, 5, 5));

        JTextField nomeField = new JTextField();
        JTextField emailField = new JTextField();
        JTextField cpfField = new JTextField();
        JPasswordField senhaField = new JPasswordField();

        panel.add(new JLabel("Nome:"));
        panel.add(nomeField);
        panel.add(new JLabel("Email:"));
        panel.add(emailField);
        panel.add(new JLabel("CPF:"));
        panel.add(cpfField);
        panel.add(new JLabel("Senha:"));
        panel.add(senhaField);

        int result = JOptionPane.showConfirmDialog(this, panel,
                "Adicionar Novo Usuário",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            String nome = nomeField.getText();
            String email = emailField.getText();
            String cpf = cpfField.getText();
            String senha = new String(senhaField.getPassword());

            if (nome.isEmpty() || email.isEmpty() || cpf.isEmpty() || senha.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Todos os campos são obrigatórios.",
                        "Campos Incompletos",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }


            Usuario u = new Usuario(cpf,nome,email,senha);
            boolean sucesso = usuarioController.cadastrarUsuario(u, sistemaNome);

            if (sucesso) {
                JOptionPane.showMessageDialog(this,
                        "Usuário cadastrado com sucesso!",
                        "Sucesso",
                        JOptionPane.INFORMATION_MESSAGE);
                carregarUsuarios(); // Atualiza a tabela
            } else {
                JOptionPane.showMessageDialog(this,
                        "Erro ao cadastrar usuário.",
                        "Erro",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }


    private void carregarUsuarios() {
        // Chama o método no Controller passando o administradorLogin e sistemaNome
        List<Usuario> usuarios = usuarioController.buscarUsuariosPorADM(administradorLogin, sistemaNome);

        DefaultTableModel model = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        // Definindo as colunas da tabela
        model.addColumn("Nome");
        model.addColumn("Email");
        model.addColumn("CPF");

        // Adicionando os dados dos usuários à tabela
        for (Usuario usuario : usuarios) {
            model.addRow(new Object[]{
                    usuario.getNome(),
                    usuario.getEmail(),
                    usuario.getCpf()
            });
        }

        tabelaUsuarios.setModel(model);

        // Configurando o tamanho das colunas
        tabelaUsuarios.getColumnModel().getColumn(0).setPreferredWidth(200); // Nome
        tabelaUsuarios.getColumnModel().getColumn(1).setPreferredWidth(200); // Email
        tabelaUsuarios.getColumnModel().getColumn(2).setPreferredWidth(100); // CPF
    }


    private void adicionarDoubleClickListener() {
        tabelaUsuarios.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (evt.getClickCount() == 2) { // Duplo clique
                    int selectedRow = tabelaUsuarios.getSelectedRow();
                    if (selectedRow != -1) { // Verifica se há uma linha selecionada
                        String cpf = tabelaUsuarios.getValueAt(selectedRow, 2).toString(); // Obtém o CPF do usuário selecionado
                        editarUsuario(cpf);
                    }
                }
            }
        });
    }

    private void editarUsuario(String cpf) {
        Usuario usuario = usuarioController.buscarUsuarioPorCpf(cpf);
        if (usuario == null) {
            JOptionPane.showMessageDialog(this, "Usuário não encontrado.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        JTextField nomeField = new JTextField(usuario.getNome());
        JTextField emailField = new JTextField(usuario.getEmail());
        JPasswordField senhaField = new JPasswordField(usuario.getSenha());

        JPanel panel = new JPanel(new GridLayout(4, 2, 5, 5));
        panel.add(new JLabel("Nome:"));
        panel.add(nomeField);
        panel.add(new JLabel("Email:"));
        panel.add(emailField);
        panel.add(new JLabel("Senha:"));
        panel.add(senhaField);

        int result = JOptionPane.showConfirmDialog(this, panel, "Editar Usuário", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            String novoNome = nomeField.getText();
            String novoEmail = emailField.getText();
            String novaSenha = new String(senhaField.getPassword());

            if (novoNome.isEmpty() || novoEmail.isEmpty() || novaSenha.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Todos os campos são obrigatórios.", "Campos Incompletos", JOptionPane.WARNING_MESSAGE);
                return;
            }

            boolean sucesso = usuarioController.editarUsuario(cpf, novoNome, novoEmail, novaSenha);
            if (sucesso) {
                JOptionPane.showMessageDialog(this, "Usuário atualizado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                carregarUsuarios(); // Atualiza a tabela
            } else {
                JOptionPane.showMessageDialog(this, "Erro ao atualizar o usuário.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}