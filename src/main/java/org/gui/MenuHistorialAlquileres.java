package org.gui;

import org.example.Pelicula;
import org.example.VideoClub;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.List;

public class MenuHistorialAlquileres extends JFrame {
        private String username;
        private CardLayout cardLayout;
        private JPanel cardPanel;
        private JPanel panelHistorialAlquileres;
        private JPanel panelLista;

        public MenuHistorialAlquileres(String username) {
            this.username = username;

            setSize(400, 400);

            panelHistorialAlquileres = new JPanel();
            //setContentPane(panelMisListas);


            JSONObject json = VideoClub.getUnVideoClub().verAlquileres(username);
            JSONArray alquileres=json.getJSONArray("alquileres");
            panelHistorialAlquileres.setLayout(new GridLayout(10, 1));
            //panelHistorialAlquileres.setLayout(new BoxLayout(panelHistorialAlquileres, BoxLayout.Y_AXIS));  // Cambiado a BoxLayout para permitir más flexibilidad

            panelLista = new JPanel();

            cardLayout = new CardLayout();
            cardPanel = new JPanel(cardLayout);
            cardPanel.add(panelHistorialAlquileres, "misAlquileres");
            cardPanel.add(panelLista, "verlista");



            JLabel label = new JLabel(" Peliculas alquiladas de " + username);
            panelHistorialAlquileres.add(label);

            if (alquileres.isEmpty() ) {
                panelHistorialAlquileres.add(new JLabel("No has alquilado ninguna Película"));
            }
            else {
                for (int i = alquileres.length()-1; i >=0  ; i--) {
                    JSONObject alquiler = alquileres.getJSONObject(i);
                    String titulo = alquiler.getString("titulo");
                    int peliculaId = alquiler.getInt("peliID");

                    // Crear un botón para cada alquiler
                    JButton boton = new JButton(titulo);
                    JButton botonPuntuar = new JButton("Puntuar");
                    boton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent actionEvent) {
                            verHistorial(alquiler);  // Llamar al método para mostrar detalles
                            cardLayout.show(cardPanel, "verlista");  // Cambiar la vista
                        }
                    });
                    botonPuntuar.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent actionEvent) {
                            new MenuPuntuarPelicula(peliculaId, username);
                        }
                    });

                    JPanel panelPelicula = new JPanel(new FlowLayout(FlowLayout.LEFT));
                    panelPelicula.add(boton);
                    panelPelicula.add(botonPuntuar);
                    panelHistorialAlquileres.add(panelPelicula);
                }
            }

            panelHistorialAlquileres.revalidate();
            panelHistorialAlquileres.repaint();


            add(cardPanel);
            cardLayout.show(cardPanel, ",misAlquileres");
        }

        private void verHistorial(JSONObject alquiler) {
            panelLista.removeAll();
            panelLista.setLayout(new GridLayout(12, 1));

            //Ver descripcion del alquiler realizado
            int idPeli = (int) alquiler.get("peliID");
            String titulo = (String) alquiler.get("titulo");
            String fechaInic = (String) alquiler.get("fechaInic");
            String fechaFin = (String) alquiler.get("fechaFin");
            panelLista.add(new JLabel("PeliculaID: " + idPeli));
            panelLista.add(new JLabel("Título: " + titulo));
            panelLista.add(new JLabel("Fecha de inicio: " + fechaInic));
            panelLista.add(new JLabel("Fecha de fin: " + fechaFin));

            JButton botonAtras = new JButton("Volver");
            panelLista.add(botonAtras);
            botonAtras.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    cardLayout.show(cardPanel, "misAlquileres");
                }
            });

        }
    }


