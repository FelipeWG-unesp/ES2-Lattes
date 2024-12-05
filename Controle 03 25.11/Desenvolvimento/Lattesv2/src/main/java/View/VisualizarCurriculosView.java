package View;

import Controller.*;
import Model.Curriculo;
import Model.Pesquisa;
import Model.Usuario;
import auxx.XML;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class VisualizarCurriculosView extends JFrame {

    private JTable tabelaCurriculos;
    private JButton voltarButton;
    private JButton detalhesButton;
    private JButton importarXmlButton;
    private JButton exportarXmlButton;
    private CurriculoController curriculoController;
    private UsuarioController usuarioController;
    private String usuarioCpf;

    public VisualizarCurriculosView(String usuarioCpf) {
        this.usuarioCpf = usuarioCpf;
        this.curriculoController = new CurriculoController();
        this.usuarioController = new UsuarioController();

        setTitle("Visualizar Currículos");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Painel principal
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Título
        JLabel titleLabel = new JLabel("Meus Currículos", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        // Tabela para exibir os currículos
        tabelaCurriculos = new JTable();
        tabelaCurriculos.setFont(new Font("Arial", Font.PLAIN, 12));
        tabelaCurriculos.setRowHeight(25);
        JScrollPane scrollPane = new JScrollPane(tabelaCurriculos);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Painel de botões (botões centralizados)
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10)); // Centralizando os botões

        // Botões
        importarXmlButton = createStyledButton("Importar XML");
        exportarXmlButton = createStyledButton("Exportar XML");
        detalhesButton = createStyledButton("Ver Detalhes");
        voltarButton = createStyledButton("Voltar");

        // Adicionando os botões ao painel
        buttonPanel.add(importarXmlButton);
        buttonPanel.add(exportarXmlButton);
        buttonPanel.add(detalhesButton);
        buttonPanel.add(voltarButton);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Adiciona o painel principal à janela
        add(mainPanel);

        // Configuração dos listeners
        detalhesButton.addActionListener(e -> exibirDetalhes());
        voltarButton.addActionListener(e -> dispose());
        
        // Lógica de exportação XML
        exportarXmlButton.addActionListener(e -> exportarXml());

        importarXmlButton.addActionListener(e -> {
            // Abrir o JFileChooser para selecionar o arquivo XML
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Selecione um arquivo XML");
            int result = fileChooser.showOpenDialog(null);

            if (result == JFileChooser.APPROVE_OPTION) {
                File xmlFile = fileChooser.getSelectedFile();

                try {
                    String[] dados = XML.importarXml(xmlFile, usuarioCpf);

                    curriculoController.criarCurriculo(dados[0],dados[1],dados[2],dados[3],dados[4],dados[5]);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Erro ao importar o arquivo XML: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
            dispose();
        });

        // Carregar os currículos
        carregarCurriculos();
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.PLAIN, 14));
        button.setPreferredSize(new Dimension(120, 35));
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return button;
    }

    private void carregarCurriculos() {
        // Recupera a lista de currículos para o CPF do usuário
        List<Curriculo> curriculos = curriculoController.buscarCurriculosPorUsuario(usuarioCpf);

        DefaultTableModel model = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        model.addColumn("Nome do Currículo");
        model.addColumn("Formação Acadêmica");
        model.addColumn("Produções Acadêmicas");
        model.addColumn("Prêmios");

        // Preenche a tabela com os currículos
        for (Curriculo curriculo : curriculos) {
            model.addRow(new Object[] {
                curriculo.getTitulo(),
                curriculo.getFormacaoAcademica(),
                curriculo.getProducoesAcademicas(),
                curriculo.getPremios()
            });
        }

        tabelaCurriculos.setModel(model);
    }

    private void exibirDetalhes() {
        int selectedRow = tabelaCurriculos.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, 
                "Por favor, selecione um currículo para visualizar os detalhes.", 
                "Seleção Necessária", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }

        String nomeCurriculo = (String) tabelaCurriculos.getValueAt(selectedRow, 0);
        List<Curriculo> curriculos = curriculoController.buscarCurriculosPorUsuario(usuarioCpf);
        
        for (Curriculo curriculo : curriculos) {
            if (curriculo.getTitulo().equals(nomeCurriculo)) {
                mostrarDetalhesCurriculo(curriculo);
                break;
            }
        }
    }

    private void mostrarDetalhesCurriculo(Curriculo curriculo) {
        JDialog detalhesDialog = new JDialog(this, "Detalhes do Currículo", true);
        detalhesDialog.setSize(600, 400);
        detalhesDialog.setLocationRelativeTo(this);

        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Criar o conteúdo
        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setFont(new Font("Arial", Font.PLAIN, 14));

        // Formatar o texto com os detalhes do currículo
        StringBuilder content = new StringBuilder();
        content.append("Nome do Currículo: ").append(curriculo.getTitulo()).append("\n\n");
        content.append("Dados Pessoais:\n").append(curriculo.getDadosPessoais()).append("\n\n");
        content.append("Formação Acadêmica:\n").append(curriculo.getFormacaoAcademica()).append("\n\n");
        content.append("Produções Acadêmicas:\n").append(curriculo.getProducoesAcademicas()).append("\n\n");
        content.append("Prêmios e Reconhecimentos:\n").append(curriculo.getPremios());

        textArea.setText(content.toString());

        JScrollPane scrollPane = new JScrollPane(textArea);
        panel.add(scrollPane, BorderLayout.CENTER);

        JButton fecharButton = createStyledButton("Fechar");
        fecharButton.addActionListener(e -> detalhesDialog.dispose());
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(fecharButton);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        detalhesDialog.add(panel);
        detalhesDialog.setVisible(true);
    }


 private void exportarXml() {
        int selectedRow = tabelaCurriculos.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, 
                "Por favor, selecione um currículo para exportar.", 
                "Seleção Necessária", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }

        String nomeCurriculo = (String) tabelaCurriculos.getValueAt(selectedRow, 0);
        List<Curriculo> curriculos = curriculoController.buscarCurriculosPorUsuario(usuarioCpf);
        Curriculo curriculoSelecionado = null;
        
        for (Curriculo curriculo : curriculos) {
            if (curriculo.getTitulo().equals(nomeCurriculo)) {
                curriculoSelecionado = curriculo;
                break;
            }
        }

        if (curriculoSelecionado == null) {
            JOptionPane.showMessageDialog(this, 
                "Currículo não encontrado.", 
                "Erro", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Exportar para XML
        XML.exportarXml(curriculoSelecionado, usuarioController.buscarUsuarioPorCpf(usuarioCpf));
    }
}
