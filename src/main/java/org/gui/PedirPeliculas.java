package org.gui;

import org.example.SolicitudPelicula;
import org.example.VideoClub;
import org.json.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class PedirPeliculas extends JFrame {
    private JPanel panelPedirPeliculas;

    public PedirPeliculas(String username) {
        panelPedirPeliculas = new JPanel();
        setContentPane(panelPedirPeliculas);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);

        ArrayList<JSONObject> peliculas = VideoClub.getUnVideoClub().obtenerCatalogoPeliculas();

        for (JSONObject pelicula : peliculas) {
            JPanel peliculaPanel = new JPanel();

            String titulo = pelicula.getString("Title");
            peliculaPanel.add(new JLabel(titulo));
            JButton modificarButton = new JButton("Pedir");

            modificarButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    VideoClub.getUnVideoClub().pedirPelicula(username, titulo);
                }
            });
        }
    }
}
