package org.gui;

import org.example.SolicitudPelicula;
import org.example.Usuario;
import org.example.VideoClub;
import org.json.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class AceptarPeticiones extends JFrame {
    private JPanel panelAceptarPeticiones;

    public AceptarPeticiones(String username) {
        panelAceptarPeticiones = new JPanel();
        setContentPane(panelAceptarPeticiones);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);

        ArrayList<SolicitudPelicula> solicitudes = VideoClub.getUnVideoClub().pedirSolicitudesPeliculas();

        for (SolicitudPelicula solicitud : solicitudes) {
            JPanel peliculaPanel = new JPanel();

            String titulo = solicitud.getTitulo();
            peliculaPanel.add(new JLabel(titulo));
            System.out.println(titulo);
            Usuario user = solicitud.solicitadaPor();
            String nombreUsuario = user.getUsername();
            peliculaPanel.add(new JLabel(nombreUsuario));
            System.out.println(nombreUsuario);
            JButton aceptarButton = new JButton("Aceptar");
            JButton rechazarButton = new JButton("Rechazar");


            //Pruebas
            //System.out.println(titulo);
            //System.out.println(nombreUsuario);

            aceptarButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    VideoClub.getUnVideoClub().aceptarSolicitudPelicula(titulo, username);
                }
            });

            rechazarButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    VideoClub.getUnVideoClub().rechazarSolicitudPelicula(titulo);
                }
            });

            peliculaPanel.add(aceptarButton);
            peliculaPanel.add(rechazarButton);
            panelAceptarPeticiones.add(peliculaPanel);
        }

        setVisible(true);
    }
}