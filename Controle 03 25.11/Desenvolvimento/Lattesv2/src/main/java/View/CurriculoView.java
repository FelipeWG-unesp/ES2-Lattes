package View;

import Controller.CurriculoController;
import Model.Curriculo;
import Model.Pesquisa;
import Controller.PesquisaController;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class CurriculoView extends JFrame {
    private final JTextField nomeCurriculoField;
    private final JTextArea dadosPessoaisField;
    private final JTextArea formacaoAcademicaField;
    private final JTextArea producoesAcademicasField;
    private final JTextArea premiosField;
    private final JButton salvarButton;
    private final JButton cancelarButton;
    private final JButton carregarButton;
    private final JButton adicionarPesquisaButton;
    private final JList<String> listaPesquisas;
    private final DefaultListModel<String> pesquisasModel;
    private final JComboBox<String> curriculosComboBox;
    private final String usuarioCpf; 
    private String curriculoTitulo = ""; 
    private final CurriculoController curriculoController;
    private final PesquisaController pesquisaController;

    public CurriculoView(String usuarioCpf) {
        this.usuarioCpf = usuarioCpf;
        this.curriculoController = new CurriculoController();
        this.pesquisaController = new PesquisaController();

        setTitle("Gerenciamento de Currículo");
        setSize(900, 800);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Painel principal
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Título
        JLabel titleLabel = new JLabel("Edição de Currículo", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        // Painel para nome do currículo e combobox
        JPanel topPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        topPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));

        JLabel nomeLabel = new JLabel("Nome do Currículo:");
        nomeCurriculoField = new JTextField();

        JLabel selecionarLabel = new JLabel("Selecionar Currículo:");
        curriculosComboBox = new JComboBox<>();
        atualizarComboBox();

        topPanel.add(nomeLabel);
        topPanel.add(nomeCurriculoField);
        topPanel.add(selecionarLabel);
        topPanel.add(curriculosComboBox);

        // Painel de campos
        JPanel fieldsPanel = new JPanel(new GridLayout(5, 1, 10, 10));

        dadosPessoaisField = createFieldWithLabel(fieldsPanel, "Dados Pessoais");
        formacaoAcademicaField = createFieldWithLabel(fieldsPanel, "Formação Acadêmica");
        producoesAcademicasField = createFieldWithLabel(fieldsPanel, "Produções Acadêmicas");
        premiosField = createFieldWithLabel(fieldsPanel, "Prêmios e Reconhecimentos");

        // Painel de pesquisas
        JPanel pesquisaPanel = new JPanel(new BorderLayout(5, 5));
        pesquisaPanel.setBorder(BorderFactory.createTitledBorder("Pesquisas Relacionadas"));

        pesquisasModel = new DefaultListModel<>();
        listaPesquisas = new JList<>(pesquisasModel);
        JScrollPane pesquisaScrollPane = new JScrollPane(listaPesquisas);

        adicionarPesquisaButton = createStyledButton("Adicionar Pesquisa");
        adicionarPesquisaButton.addActionListener(e -> adicionarPesquisa());

        pesquisaPanel.add(pesquisaScrollPane, BorderLayout.CENTER);
        pesquisaPanel.add(adicionarPesquisaButton, BorderLayout.SOUTH);

        fieldsPanel.add(pesquisaPanel);

        // Painel central que combina o topPanel e fieldsPanel
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.add(topPanel, BorderLayout.NORTH);
        centerPanel.add(fieldsPanel, BorderLayout.CENTER);
        mainPanel.add(centerPanel, BorderLayout.CENTER);

        // Painel de botões
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));

        carregarButton = createStyledButton("Carregar");
        salvarButton = createStyledButton("Salvar");
        cancelarButton = createStyledButton("Cancelar");

        buttonPanel.add(carregarButton);
        buttonPanel.add(salvarButton);
        buttonPanel.add(cancelarButton);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);

        // Listeners
        salvarButton.addActionListener(e -> salvarCurriculo());
        cancelarButton.addActionListener(e -> dispose());
        carregarButton.addActionListener(e -> carregarCurriculoSelecionado());
        curriculosComboBox.addActionListener(e -> preencherCamposComCurriculoSelecionado());
    }

    private JTextArea createFieldWithLabel(JPanel panel, String labelText) {
        JPanel fieldPanel = new JPanel(new BorderLayout(5, 5));
        fieldPanel.setBorder(BorderFactory.createTitledBorder(labelText));

        JTextArea textArea = new JTextArea(5, 40);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        JScrollPane scrollPane = new JScrollPane(textArea);
        fieldPanel.add(scrollPane, BorderLayout.CENTER);
        panel.add(fieldPanel);

        return textArea;
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.PLAIN, 14));
        button.setPreferredSize(new Dimension(150, 35));
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return button;
    }

    private void salvarCurriculo() {
        String nomeCurriculo = nomeCurriculoField.getText().trim();
        if (nomeCurriculo.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Por favor, insira um nome para o currículo.",
                    "Campo Obrigatório",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        String dadosPessoais = dadosPessoaisField.getText();
        String formacaoAcademica = formacaoAcademicaField.getText();
        String producoesAcademicas = producoesAcademicasField.getText();
        String premios = premiosField.getText();

        if (curriculoTitulo.isEmpty()) {
            curriculoController.criarCurriculo(usuarioCpf, nomeCurriculo, dadosPessoais, formacaoAcademica, producoesAcademicas, premios);
            JOptionPane.showMessageDialog(this, "Currículo salvo com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        } else {
            // Atualiza as seções
            boolean sucesso = curriculoController.editarSecao(usuarioCpf, curriculoTitulo, "dadosPessoais", dadosPessoais)
                    && curriculoController.editarSecao(usuarioCpf, curriculoTitulo, "formacaoAcademica", formacaoAcademica)
                    && curriculoController.editarSecao(usuarioCpf, curriculoTitulo, "producoesAcademicas", producoesAcademicas)
                    && curriculoController.editarSecao(usuarioCpf, curriculoTitulo, "premios", premios);

            if (sucesso) {
                JOptionPane.showMessageDialog(this, "Currículo atualizado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Erro ao atualizar o currículo.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
        atualizarComboBox();
        limparCampos();
    }

    private void atualizarPesquisas(String curriculoTitulo) {
        pesquisasModel.clear();
        List<Pesquisa> pesquisas = pesquisaController.buscarPesquisasPorCurriculo(usuarioCpf, curriculoTitulo);
        for (Pesquisa pesquisa : pesquisas) {
            pesquisasModel.addElement(pesquisa.getTitulo());
        }
    }

    private void adicionarPesquisa() {
        String tituloPesquisa = JOptionPane.showInputDialog(this, "Informe o título da pesquisa:");
        String descricao = JOptionPane.showInputDialog(this, "Informe a descricao da pesquisa:");
        
        if (tituloPesquisa != null && !tituloPesquisa.trim().isEmpty()) {
            boolean sucesso = pesquisaController.addPesquisa(usuarioCpf, curriculoTitulo, tituloPesquisa, descricao);
            if (sucesso) {
                JOptionPane.showMessageDialog(this, "Pesquisa adicionada com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                atualizarPesquisas(curriculoTitulo);
            } else {
                JOptionPane.showMessageDialog(this, "Erro ao adicionar a pesquisa.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void carregarCurriculoSelecionado() {
        String selectedCurriculo = (String) curriculosComboBox.getSelectedItem();
        if (selectedCurriculo == null || selectedCurriculo.equals("Selecione um currículo...")) {
            JOptionPane.showMessageDialog(this,
                    "Por favor, selecione um currículo para carregar.",
                    "Seleção Necessária",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }
        preencherCamposComCurriculoSelecionado();
    }

    private void preencherCamposComCurriculoSelecionado() {
        String selectedCurriculo = (String) curriculosComboBox.getSelectedItem();
        if (selectedCurriculo != null && !selectedCurriculo.equals("Selecione um currículo...")) {
            Curriculo curriculo = curriculoController.exibirConteudo(usuarioCpf, selectedCurriculo);
            if (curriculo != null) {
                nomeCurriculoField.setText(curriculo.getTitulo());
                dadosPessoaisField.setText(curriculo.getDadosPessoais());
                formacaoAcademicaField.setText(curriculo.getFormacaoAcademica());
                producoesAcademicasField.setText(curriculo.getProducoesAcademicas());
                premiosField.setText(curriculo.getPremios());
                curriculoTitulo = curriculo.getTitulo();
                atualizarPesquisas(curriculoTitulo);
            }
        }
    }

    private void limparCampos() {
        nomeCurriculoField.setText("");
        dadosPessoaisField.setText("");
        formacaoAcademicaField.setText("");
        producoesAcademicasField.setText("");
        premiosField.setText("");
        pesquisasModel.clear();
        curriculoTitulo = "";
        curriculosComboBox.setSelectedIndex(0);
    }

    private void atualizarComboBox() {
        curriculosComboBox.removeAllItems();
        curriculosComboBox.addItem("Selecione um currículo...");
        List<Curriculo> curriculos = curriculoController.buscarCurriculosPorUsuario(usuarioCpf);
        for (Curriculo curriculo : curriculos) {
            curriculosComboBox.addItem(curriculo.getTitulo());
        }
    }
}
