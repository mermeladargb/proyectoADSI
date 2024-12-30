package org.gui;

import org.example.VideoClub;
import org.json.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.List;

public class MenuListas extends JFrame {
    private String username;
    private CardLayout cardLayout;
    private JPanel cardPanel;
    private JPanel panelMisListas;
    private JPanel panelLista;
    private JScrollPane scrollMisListas;
    private JScrollPane scrollLista;
    private JPanel panelCrearLista;
    private JTextField nombreLista;
    private JButton botonMenuCrearLista;

    public MenuListas(String username) {
        this.username = username;

        setSize(400, 400);

        panelMisListas = new JPanel();
        scrollMisListas = new JScrollPane(panelMisListas);
        panelMisListas.setLayout(new BoxLayout(panelMisListas, BoxLayout.Y_AXIS));

        panelLista = new JPanel();
        scrollLista = new JScrollPane(panelLista);

        panelCrearLista = new JPanel();

        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);
        cardPanel.add(scrollMisListas, "mislistas");
        cardPanel.add(scrollLista, "verlista");
        cardPanel.add(panelCrearLista, "crearlista");

        panelCrearLista.setLayout(new BoxLayout(panelCrearLista, BoxLayout.Y_AXIS));
        panelCrearLista.add(new JLabel("Nombre de la lista"));
        nombreLista = new JTextField();
        panelCrearLista.add(nombreLista);
        JButton botonCrearLista = new JButton("Crear lista");
        botonCrearLista.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (nombreLista.getText().isEmpty())
                    return;
                VideoClub.getUnVideoClub().crearLista(username, nombreLista.getText());
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

        panelLista.add(new JLabel("Lista: " + nombreLista + ", visible: " + visible));

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
                nombreLista.setText("");
                cardLayout.show(cardPanel, "crearlista");
            }
        });
        panelMisListas.add(botonMenuCrearLista);
    }
}
