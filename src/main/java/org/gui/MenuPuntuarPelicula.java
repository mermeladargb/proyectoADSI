package org.gui;

import org.example.VideoClub;
import org.json.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuPuntuarPelicula extends JFrame {
    private int peliculaId;
    private String username;

    public MenuPuntuarPelicula(int peliculaId, String username) {
        this.peliculaId = peliculaId;
        this.username = username;

        setTitle("Puntuar Película");
        setSize(400, 300);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(6, 1));

        JSONObject valoracionAntigua = obtenerValoracionAntigua();

        JLabel labelTitulo = new JLabel("Título: " + obtenerTituloPelicula(peliculaId));
        panel.add(labelTitulo);

        JLabel labelPuntuacion = new JLabel("Puntuación (0-10):");
        panel.add(labelPuntuacion);

        int puntuacionDefecto = 0;
        if (valoracionAntigua != null && valoracionAntigua.has("puntuacion")) {
            puntuacionDefecto = valoracionAntigua.optInt("puntuacion", 5);
        }
        JSpinner spinnerPuntuacion = new JSpinner(new SpinnerNumberModel(puntuacionDefecto, 0, 10, 1));
        panel.add(spinnerPuntuacion);
        JLabel labelReseña = new JLabel("Reseña:");
        panel.add(labelReseña);

        String reseñaPorDefecto = "";
        if (valoracionAntigua != null && valoracionAntigua.has("descripcion")){
            reseñaPorDefecto = valoracionAntigua.optString("descripcion", "");
        }
        JTextArea textAreaReseña = new JTextArea(reseñaPorDefecto);
        JScrollPane scrollPane = new JScrollPane(textAreaReseña);
        panel.add(scrollPane);

        JButton botonEnviar = new JButton("Enviar");
        botonEnviar.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                int puntuacion = (int) spinnerPuntuacion.getValue();
                String reseña = textAreaReseña.getText();
                VideoClub.getUnVideoClub().puntuarPelicula(username, peliculaId, reseña, puntuacion);
                JOptionPane.showMessageDialog(MenuPuntuarPelicula.this,
                        "¡Puntuación guardada con éxito!",
                        "Éxito",
                        JOptionPane.INFORMATION_MESSAGE);
                dispose();
            }
        });
        panel.add(botonEnviar);

        add(panel);
        setVisible(true);
    }

    private String obtenerTituloPelicula(int peliculaId) {
        JSONObject pelicula = VideoClub.getUnVideoClub().seleccionarPelicula(peliculaId);
        return pelicula != null && pelicula.has("titulo") ? pelicula.getString("titulo") : "Titulo no disponible";
    }

    private JSONObject obtenerValoracionAntigua() {
        try {
            JSONObject valoracion = VideoClub.getUnVideoClub().mostrarValoracionesAntiguas(username, peliculaId);
            if (valoracion != null && valoracion.has("puntuacion") && valoracion.has("descripcion")) {
                return valoracion;
            }
        } catch (Exception e) {
            System.err.println("Error al obtener valoraciones antiguas: " + e.getMessage());
        }
        return new JSONObject();
    }
}
