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

    public MenuListas(String username) {
        this.username = username;

        setSize(400, 400);

        panelMisListas = new JPanel();
        scrollMisListas = new JScrollPane(panelMisListas);
        panelMisListas.setLayout(new BoxLayout(panelMisListas, BoxLayout.Y_AXIS));

        panelLista = new JPanel();
        scrollLista = new JScrollPane(panelLista);

        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);
        cardPanel.add(scrollMisListas, "mislistas");
        cardPanel.add(scrollLista, "verlista");

        JSONObject json = VideoClub.getUnVideoClub().getListasUsuario(username);
        List<Object> listas = json.getJSONArray("listas").toList();

        JLabel label = new JLabel("Listas de " + username);
        panelMisListas.add(label);

        if (listas.isEmpty()) {
            panelMisListas.add(new JLabel("No has creado ninguna lista"));
        }
        else {
            Iterator<Object> iter = listas.iterator();
            int i = 0;
            while (iter.hasNext() && i < 10) {
                String nombreLista = (String) iter.next();
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
}
