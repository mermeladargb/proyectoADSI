package org.gui;

import org.example.VideoClub;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VerSolicitudes extends JFrame {
    private JPanel panelSolicitudes;

    public VerSolicitudes(String adminUsername) {
        panelSolicitudes = new JPanel();
        panelSolicitudes.setLayout(new BoxLayout(panelSolicitudes, BoxLayout.Y_AXIS)); // Añadir un layout para organizar los componentes
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

            JPanel solicitudPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            solicitudPanel.add(new JLabel(username));
            JButton aceptarButton = new JButton("Aceptar");
            JButton rechazarButton = new JButton("Rechazar");

            aceptarButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    VideoClub.getUnVideoClub().aceptarSolicitud(adminUsername, username); // Asegúrate de pasar el nombre de usuario del administrador
                    JOptionPane.showMessageDialog(null, "Solicitud aceptada");
                    dispose();
                    new VerSolicitudes(adminUsername); // Refrescar la ventana
                }
            });

            rechazarButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    VideoClub.getUnVideoClub().rechazarSolicitud(adminUsername, username); // Asegúrate de pasar el nombre de usuario del administrador
                    JOptionPane.showMessageDialog(null, "Solicitud rechazada");
                    dispose();
                    new VerSolicitudes(adminUsername); // Refrescar la ventana
                }
            });

            solicitudPanel.add(aceptarButton);
            solicitudPanel.add(rechazarButton);
            panelSolicitudes.add(solicitudPanel);
        }

        setVisible(true);
    }
}
