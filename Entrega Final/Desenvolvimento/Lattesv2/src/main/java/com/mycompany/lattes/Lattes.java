/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.lattes;

import View.LoginView;
import Controller.SistemaController;
import javax.swing.SwingUtilities;


/**
 *
 * @author gusan
 */
public class Lattes{

    public static void main(String[] args) {
        SistemaController sistemaController = new SistemaController();
        SwingUtilities.invokeLater(() -> {
            new LoginView(sistemaController).setVisible(true);
        });
    }
}
