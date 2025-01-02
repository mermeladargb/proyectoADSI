package org.gui;

import org.example.VideoClub;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuVerReseñas extends JFrame {
    private String username;
    private int idPelicula;
    private JPanel panelReseñas;
    private JScrollPane scrollPane;

    public MenuVerReseñas(int idPelicula, String username) {
        this.username = username;
        this.idPelicula = idPelicula;

        //Configuracion de la ventana
        setTitle("Reseñas de la Película");
        setSize(600, 400);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        //creacion del panel
        panelReseñas = new JPanel();
        panelReseñas.setLayout(new BoxLayout(panelReseñas, BoxLayout.Y_AXIS));

        //Permitir desplazarse entre reseñas
        scrollPane = new JScrollPane(panelReseñas);
        add(scrollPane);

        cargarReseñas();
    }

    private void cargarReseñas() {
        VideoClub.getUnVideoClub().puntuarPelicula("u1", 1, "Buena", 5);
        VideoClub.getUnVideoClub().puntuarPelicula("u2", 1, "Bien", 3);
        VideoClub.getUnVideoClub().puntuarPelicula("u3", 1, "Mal,Mal,Mal,Mal,Mal,Mal,Mal,Mal,Mal,Mal,Mal,Mal,Mal,Mal,Mal,Mal,Mal,Mal,Mal,Mal,Mal,Mal,Mal,Mal,Mal,Mal,Mal,Mal,Mal,Mal,Mal,Mal,Mal,Mal,Mal,Mal,Mal,Mal,Mal,Mal,Mal,Mal,Mal,Mal,Mal,Mal,Mal,Mal,Mal,Mal,Mal,Mal,Mal,Mal,Mal,Mal,Mal,Mal,Mal,Mal,Mal,Mal,Mal,Mal,Mal,Mal,Mal,Mal,Mal,Mal,Mal,Mal,Mal,Mal,Mal,Mal,Mal,Mal,Mal,Mal,Mal,Mal,Mal,Mal,Mal,Mal,Mal,Mal,Mal,Mal,Mal,Mal,Mal,Mal,Mal,Mal,Mal,Mal,Mal,Mal,Mal,Mal,Mal,Mal,Mal,Mal,Mal,Mal,Mal,Mal,Mal,Mal,Mal,Mal,Mal,Mal,Mal,Mal,Mal,Mal,Mal,Mal,Mal,Mal,Mal,Mal,Mal,Mal,Mal,Mal,Mal,Mal,Mal,Mal,Mal,Mal,Mal,Mal,Mal,Mal,Mal,Mal,Mal,Mal,Mal,Mal,Mal,Mal,Mal,Mal,Mal,Mal,Mal,Mal,Mal,Mal,Mal,Mal,Mal,Mal,Mal,Mal,Mal,Mal,Mal,Mal,Mal,Mal, ", 4);
        VideoClub.getUnVideoClub().puntuarPelicula("u4", 1, "Me he dormido", 4);
        VideoClub.getUnVideoClub().puntuarPelicula("u5", 1, "Increible", 4);
        VideoClub.getUnVideoClub().puntuarPelicula("u6", 1, "Me encanto", 4);

        panelReseñas.removeAll();

        //Lamar al metodo que nos da el JSON con las reseñas y puntuaciones de cada usuario
        JSONObject reseñasJSON = VideoClub.getUnVideoClub().mostrarReseñas(username, idPelicula);
        JSONArray valoraciones = reseñasJSON.getJSONArray("valoraciones");

        if (valoraciones.length() == 0) {
            panelReseñas.add(new JLabel("No hay reseñas disponibles para esta pelicula."));
        } else {
            //Iterar por las reseñas y obtener los datos
            for (int i = 0; i < valoraciones.length(); i++) {
                JSONObject reseña = valoraciones.getJSONObject(i);
                String usuario = reseña.getString("username");
                String textoReseña = reseña.getString("reseña");
                float puntuacion = reseña.getFloat("puntuacion");

                //creacion del panel para mostrar la reseña
                JPanel panelIndividual = new JPanel(new BorderLayout());
                //TODO se puede quitar "Usuario: " porque se puede intuir
                JLabel labelUsuario = new JLabel("Usuario: " + usuario);
                JLabel labelPuntuacion = new JLabel("Puntuación: " + puntuacion);
                JTextArea areaReseña = new JTextArea(textoReseña);
                areaReseña.setEditable(false);
                areaReseña.setLineWrap(true);
                areaReseña.setWrapStyleWord(true);

                //añadir los componentes al panel
                panelIndividual.add(labelUsuario, BorderLayout.NORTH);
                panelIndividual.add(new JScrollPane(areaReseña), BorderLayout.CENTER);
                panelIndividual.add(labelPuntuacion, BorderLayout.SOUTH);
                panelIndividual.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

                panelReseñas.add(panelIndividual);
            }
        }

        JButton botonVolver = new JButton("Volver");
        botonVolver.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        panelReseñas.add(botonVolver);

        panelReseñas.revalidate();
        panelReseñas.repaint();
    }
}
