package org.gui;

import org.example.VideoClub;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class MenuBuscarListas extends JFrame {
    private JPanel panelBusqueda;
    private JTextField fieldNombreLista;

    private JPanel panelListas;
    private JScrollPane scrollListas;

    private JPanel panelLista;
    private JScrollPane scrollLista;

    private JPanel cardPanel;
    private CardLayout cardLayout;

    public MenuBuscarListas() {
        setSize(400, 400);

        panelBusqueda = new JPanel();
        panelBusqueda.setLayout(new BoxLayout(panelBusqueda, BoxLayout.Y_AXIS));
        fieldNombreLista = new JTextField("Nombre de la lista");
        panelBusqueda.add(fieldNombreLista);
        JButton botonBuscar = new JButton("Buscar");
        botonBuscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                verListas(fieldNombreLista.getText());
                cardLayout.show(cardPanel, "listas");
            }
        });
        panelBusqueda.add(botonBuscar);

        panelListas = new JPanel();
        scrollListas = new JScrollPane(panelListas);

        panelLista = new JPanel();
        scrollLista = new JScrollPane(panelLista);

        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);
        cardPanel.add(panelBusqueda, "busqueda");
        cardPanel.add(scrollListas, "listas");
        cardPanel.add(scrollLista, "lista");

        add(cardPanel);
        cardLayout.show(cardPanel, "busqueda");
    }

    private void verListas(String busqueda) {
        panelListas.removeAll();
        panelListas.setLayout(new BoxLayout(panelListas, BoxLayout.Y_AXIS));

        JSONObject json = VideoClub.getUnVideoClub().buscarLista(busqueda);
        JSONArray listas = json.getJSONArray("listas");
        if (listas.isEmpty()) {
            JLabel label = new JLabel("No se ha encontrado ninguna lista");
            JButton botonAtras = new JButton("Volver");
            botonAtras.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    cardLayout.show(cardPanel, "busqueda");
                }
            });
            panelListas.add(label);
            panelListas.add(botonAtras);
        }
        else {
            panelListas.add(new JLabel("Resultados para la búsqueda \"" + busqueda + "\":"));
            for (Object o : listas) {
                JSONObject lista = (JSONObject) o;
                String nombreLista = (String) lista.get("nombreLista");
                String username = (String) lista.get("username");
                JButton boton = new JButton(nombreLista + " (" + username + ")");
                boton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent actionEvent) {
                        verLista(username, nombreLista);
                        cardLayout.show(cardPanel, "lista");
                    }
                });
                panelListas.add(boton);
            }
            JButton botonAtras = new JButton("Volver");
            botonAtras.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    cardLayout.show(cardPanel, "busqueda");
                }
            });
            panelListas.add(botonAtras);
        }
    }

    private void verLista(String username, String nombreLista) {
        panelLista.removeAll();
        panelLista.setLayout(new BoxLayout(panelLista, BoxLayout.Y_AXIS));

        JSONObject json = VideoClub.getUnVideoClub().getListaUsuario(username, nombreLista);
        List<Object> peliculas = json.getJSONArray("peliculas").toList();

        JLabel label = new JLabel("Lista \"" + nombreLista + "\" de " + username);
        panelLista.add(label);

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
                cardLayout.show(cardPanel, "listas");
            }
        });
    }
}
