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

    public MenuListas(String username) {
        this.username = username;

        setSize(400, 400);

        panelMisListas = new JPanel();
        //setContentPane(panelMisListas);
        panelMisListas.setLayout(new GridLayout(10, 1));

        panelLista = new JPanel();

        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);
        cardPanel.add(panelMisListas, "mislistas");
        cardPanel.add(panelLista, "verlista");

        JSONObject json = VideoClub.getUnVideoClub().getListasUsuario(username);
        List<String> listas = (List<String>) json.getJSONArray("listas").toList().get(0);

        JLabel label = new JLabel("Listas de " + username);
        panelMisListas.add(label);

        if (listas.isEmpty()) {
            panelMisListas.add(new JLabel("No has creado ninguna lista"));
        }
        else {
            Iterator<String> iter = listas.iterator();
            int i = 0;
            while (iter.hasNext() && i < 10) {
                String nombreLista = iter.next();
                JButton boton = new JButton(nombreLista);
                boton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent actionEvent) {
                        verLista(boton.getText());
                        cardLayout.show(cardPanel, "verlista");
                    }
                });
                panelMisListas.add(boton);
                i++;
            }
        }

        add(cardPanel);
        cardLayout.show(cardPanel, "mislistas");
    }

    private void verLista(String nombreLista) {
        panelLista.removeAll();
        panelLista.setLayout(new GridLayout(12, 1));

        JSONObject json = VideoClub.getUnVideoClub().getListaUsuario(username, nombreLista);
        boolean visible = (boolean) json.get("visible");
        List<Object> peliculas = json.getJSONArray("peliculas").toList();

        panelLista.add(new JLabel("Lista: " + nombreLista + ", visible: " + visible));


        if (peliculas.isEmpty()) {
            panelLista.add(new JLabel("Esta lista no tiene películas"));
        }
        else {
            Iterator<Object> iter = peliculas.iterator();
            int i = 0;
            while (iter.hasNext() && i < 10) {
                panelLista.add(new JLabel((String) iter.next()));
                i++;
            }
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
}
