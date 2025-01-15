package org.gui;

import org.example.VideoClub;
import org.example.Usuario;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VerSolicitudes extends JFrame {
    private JPanel panelSolicitudes;

    public VerSolicitudes(Usuario admin) {
        panelSolicitudes = new JPanel();
        setContentPane(panelSolicitudes);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);

        // Obtiene las solicitudes desde VideoClub
        JSONObject jsonSolicitudes = VideoClub.getUnVideoClub().mostrarSolicitudes();
        JSONArray solicitudes = jsonSolicitudes.getJSONArray("solicitudes");

        for (int i = 0; i < solicitudes.length(); i++) {
            JSONObject solicitudJSON = solicitudes.getJSONObject(i);
            String username = solicitudJSON.getString("username");
            Usuario solicitud = VideoClub.getUnVideoClub().getUsuario(username); 

            JPanel solicitudPanel = new JPanel();
            solicitudPanel.add(new JLabel(username));
            JButton aceptarButton = new JButton("Aceptar");
            JButton rechazarButton = new JButton("Rechazar");

            aceptarButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    admin.aceptarCuenta(solicitud);
                    JOptionPane.showMessageDialog(null, "Solicitud aceptada");
                    dispose();
                    new VerSolicitudes(admin);
                }
            });

            rechazarButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    JOptionPane.showMessageDialog(null, "Solicitud rechazada");
                    dispose();
                    new VerSolicitudes(admin);
                }
            });

            solicitudPanel.add(aceptarButton);
            solicitudPanel.add(rechazarButton);
            panelSolicitudes.add(solicitudPanel);
        }

        setVisible(true);
    }
}
