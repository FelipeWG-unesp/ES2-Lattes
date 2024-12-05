/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package View;

/**
 *
 * @author gusan
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VisualizarRelatoriosView extends JFrame {

    public VisualizarRelatoriosView() {
        setTitle("Visualizar Relatórios");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Mensagem central
        JLabel mensagemLabel = new JLabel("A ser implementado", JLabel.CENTER);
        mensagemLabel.setFont(new Font("Arial", Font.BOLD, 16));
        add(mensagemLabel, BorderLayout.CENTER);

        // Botão de voltar
        JButton voltarButton = new JButton("Voltar");
        voltarButton.addActionListener(e -> dispose()); // Fecha a janela
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(voltarButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new VisualizarRelatoriosView().setVisible(true));
    }
}
