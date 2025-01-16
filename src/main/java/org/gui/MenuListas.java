package org.gui;

import org.example.VideoClub;
import org.json.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Map;

public class MenuListas extends JFrame {
    private String username;
    private CardLayout cardLayout;
    private JPanel cardPanel;
    private JPanel panelMisListas;
    private JPanel panelLista;
    private JScrollPane scrollMisListas;
    private JScrollPane scrollLista;
    private JPanel panelCrearLista;
    private JTextField fieldNombreLista;
    private JButton botonMenuCrearLista;

    private JPanel panelAñadirPeli;
    private JButton botonBuscarPeli;
    private JTextField fieldNombrePeli;


    private JPanel panelListaPelis;
    private JScrollPane scrollListaPelis;


    public MenuListas(String username) {
        this.username = username;

        setSize(400, 400);

        panelMisListas = new JPanel();
        scrollMisListas = new JScrollPane(panelMisListas);
        panelMisListas.setLayout(new BoxLayout(panelMisListas, BoxLayout.Y_AXIS));

        panelLista = new JPanel();
        scrollLista = new JScrollPane(panelLista);

        panelCrearLista = new JPanel();

        panelAñadirPeli = new JPanel();

        panelListaPelis = new JPanel();
        scrollListaPelis = new JScrollPane(panelListaPelis);

        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);
        cardPanel.add(scrollMisListas, "mislistas");
        cardPanel.add(scrollLista, "verlista");
        cardPanel.add(panelCrearLista, "crearlista");
        cardPanel.add(panelAñadirPeli, "añadirpeli");
        cardPanel.add(scrollListaPelis, "listapelis");

        panelCrearLista.setLayout(new BoxLayout(panelCrearLista, BoxLayout.Y_AXIS));
        panelCrearLista.add(new JLabel("Nombre de la lista"));
        fieldNombreLista = new JTextField();
        panelCrearLista.add(fieldNombreLista);
        JButton botonCrearLista = new JButton("Crear lista");
        botonCrearLista.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (fieldNombreLista.getText().isEmpty())
                    return;
                VideoClub.getUnVideoClub().crearLista(username, fieldNombreLista.getText());
                actualizarMisListas();
                cardLayout.show(cardPanel, "mislistas");
            }
        });
        panelCrearLista.add(botonCrearLista);

        actualizarMisListas();

        add(cardPanel);
        cardLayout.show(cardPanel, "mislistas");
    }

    private void verLista(String nombreLista) {
        panelLista.removeAll();
        panelLista.setLayout(new BoxLayout(panelLista, BoxLayout.Y_AXIS));

        JSONObject json = VideoClub.getUnVideoClub().getListaUsuario(username, nombreLista);
        boolean visible = (boolean) json.get("visible");
        List<Object> peliculas = json.getJSONArray("peliculas").toList();

        JLabel label = new JLabel("Lista: " + nombreLista + ", visible: " + visible);
        panelLista.add(label);

        JButton botonCambiarVisibilidad = new JButton("Cambiar visibilidad");
        botonCambiarVisibilidad.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                VideoClub.getUnVideoClub().cambiarVisibilidadLista(username, nombreLista);
                verLista(nombreLista);
                cardLayout.show(cardPanel, "verlista");
                revalidate();
                repaint();
            }
        });
        panelLista.add(botonCambiarVisibilidad);

        JButton botonAñadirPeli = new JButton("Añadir película");
        botonAñadirPeli.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                actualizarAñadirPeli(nombreLista);
                cardLayout.show(cardPanel, "añadirpeli");
            }
        });
        panelLista.add(botonAñadirPeli);

        if (peliculas.isEmpty()) {
            panelLista.add(new JLabel("Esta lista no tiene películas"));
        }
        else {
            for (Object o : peliculas)
                panelLista.add(new JLabel((String) o));
        }
        JButton botonAtras = new JButton("Volver");
        panelLista.add(botonAtras);
        botonAtras.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                cardLayout.show(cardPanel, "mislistas");
            }
        });
    }

    private void actualizarMisListas() {
        JSONObject json = VideoClub.getUnVideoClub().getListasUsuario(username);
        List<Object> listas = json.getJSONArray("listas").toList();

        panelMisListas.removeAll();

        JLabel label = new JLabel("Listas de " + username);
        panelMisListas.add(label);

        if (listas.isEmpty()) {
            panelMisListas.add(new JLabel("No has creado ninguna lista"));
        }
        else {
            for (Object o : listas) {
                JButton boton = new JButton((String) o);
                boton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent actionEvent) {
                        verLista(boton.getText());
                        cardLayout.show(cardPanel, "verlista");
                    }
                });
                panelMisListas.add(boton);
            }
        }

        botonMenuCrearLista = new JButton("Crear lista");
        botonMenuCrearLista.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                fieldNombreLista.setText("");
                cardLayout.show(cardPanel, "crearlista");
            }
        });
        panelMisListas.add(botonMenuCrearLista);
    }

    private void actualizarAñadirPeli(String nombreLista) {
        panelAñadirPeli.removeAll();
        panelAñadirPeli.setLayout(new BoxLayout(panelAñadirPeli, BoxLayout.Y_AXIS));

        fieldNombrePeli = new JTextField("Nombre de la película");
        botonBuscarPeli = new JButton("Buscar");
        botonBuscarPeli.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (fieldNombrePeli.getText().isEmpty())
                    return;
                mostrarPelis(nombreLista, fieldNombrePeli.getText());
                cardLayout.show(cardPanel, "listapelis");
            }
        });

        JButton botonAtras = new JButton("Volver");
        botonAtras.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                verLista(nombreLista);
                cardLayout.show(cardPanel, "verlista");
            }
        });

        panelAñadirPeli.add(fieldNombrePeli);
        panelAñadirPeli.add(botonBuscarPeli);
        panelAñadirPeli.add(botonAtras);
    }

    private void mostrarPelis(String nombreLista, String busqueda) {
        panelListaPelis.removeAll();

        JSONObject json = VideoClub.getUnVideoClub().mostrarPeliculasSimilares(busqueda);
        List<Object> listaPelis = json.getJSONArray("peliculas").toList();

        JButton botonAtras = new JButton("Volver");
        botonAtras.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                cardLayout.show(cardPanel, "añadirpeli");
            }
        });

        if (listaPelis.isEmpty()) {
            panelListaPelis.add(new JLabel("No se ha encontrado ninguna película"));
            panelListaPelis.add(botonAtras);
        }

        else {
            panelListaPelis.setLayout(new GridLayout(0, 2));
            panelListaPelis.add(new JLabel("Resultados"));
            panelListaPelis.add(botonAtras);

            for (Object o : json.getJSONArray("peliculas").toList()) {
                Map<String, Object> jsonObject = (Map<String, Object>) o;
                String nombrePeli = (String) jsonObject.get("titulo");
                int idPeli = (int) jsonObject.get("id");
                System.out.println("Peli: " + nombrePeli + ", id: " + idPeli);
                JLabel labelNombrePeli = new JLabel(nombrePeli);
                JButton botonAñadir = new JButton("Añadir");
                botonAñadir.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent actionEvent) {
                        VideoClub.getUnVideoClub().añadirPeliculaALista(username, nombreLista, idPeli);
                    }
                });
                panelListaPelis.add(labelNombrePeli);
                panelListaPelis.add(botonAñadir);
            }
        }
    }
}