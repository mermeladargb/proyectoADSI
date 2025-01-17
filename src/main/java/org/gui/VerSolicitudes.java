package org.gui;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.example.VideoClub;
import org.json.JSONArray;
import org.json.JSONObject;

public class VerSolicitudes extends JFrame {
    private JPanel panelSolicitudes;

    public VerSolicitudes(String adminUsername) {
        panelSolicitudes = new JPanel();
        panelSolicitudes.setLayout(new BoxLayout(panelSolicitudes, BoxLayout.Y_AXIS));
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
                    JSONObject respuesta = VideoClub.getUnVideoClub().aceptarSolicitud(adminUsername, username);
                    if (respuesta.getString("estado").equals("exitoso")) {
                        JOptionPane.showMessageDialog(null, "Solicitud aceptada");
                    } else {
                        JOptionPane.showMessageDialog(null, "Error: " + respuesta.getString("mensaje"));
                    }
                    dispose();
                    new VerSolicitudes(adminUsername);
                }
            });

            rechazarButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    JSONObject respuesta = VideoClub.getUnVideoClub().rechazarSolicitud(adminUsername, username);
                    if (respuesta.getString("estado").equals("exitoso")) {
                        JOptionPane.showMessageDialog(null, "Solicitud rechazada");
                    } else {
                        JOptionPane.showMessageDialog(null, "Error: " + respuesta.getString("mensaje"));
                    }
                    dispose();
                    new VerSolicitudes(adminUsername);
                }
            });

            solicitudPanel.add(aceptarButton);
            solicitudPanel.add(rechazarButton);
            panelSolicitudes.add(solicitudPanel);
        }

        setVisible(true);
    }
}
