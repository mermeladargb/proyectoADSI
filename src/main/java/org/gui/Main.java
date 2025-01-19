package org.gui;

import javax.swing.SwingUtilities;

import org.example.VideoClub;

public class Main {

    public static void main(String[] args) {
        VideoClub.getUnVideoClub().cargarDatos();

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                MenuPrincipal menuPrincipal = new MenuPrincipal();
                menuPrincipal.setVisible(true);
            }
        });
    }
}
