package org.gui;

import java.util.ArrayList;

import javax.swing.SwingUtilities;

import org.example.*;

public class Main {

    public static void main(String[] args) {
        DBGestor.getDBGestor().cargarDatos();

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                MenuPrincipal menuPrincipal = new MenuPrincipal();
                menuPrincipal.setVisible(true);
            }
        });
    }
}
