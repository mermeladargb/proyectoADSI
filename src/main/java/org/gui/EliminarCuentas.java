package org.gui;

import org.example.VideoClub;
import org.json.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class EliminarCuentas extends JFrame {
    private JPanel panelEliminarCuentas;

    public EliminarCuentas(String adminUsername) {
        panelEliminarCuentas = new JPanel();
        setContentPane(panelEliminarCuentas);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);

        ArrayList<JSONObject> usuarios = VideoClub.getUnVideoClub().getUsuariosJson(); 

        for (JSONObject usuario : usuarios) {
            if (!usuario.getBoolean("esAdmin")) {
                JPanel usuarioPanel = new JPanel();
                usuarioPanel.add(new JLabel(usuario.getString("username")));
                JButton eliminarButton = new JButton("Eliminar");

                eliminarButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent actionEvent) {
                        String username = usuario.getString("username");
                        VideoClub.getUnVideoClub().eliminarCuentaSeleccionada(username);
                        JOptionPane.showMessageDialog(null, "Usuario eliminado");
                        dispose();
                        new EliminarCuentas(adminUsername);
                    }
                });

                usuarioPanel.add(eliminarButton);
                panelEliminarCuentas.add(usuarioPanel);
            }
        }

        setVisible(true);
    }
}
