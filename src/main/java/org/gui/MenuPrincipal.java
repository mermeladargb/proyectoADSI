package org.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuPrincipal extends JFrame {
    private JPanel panelInicioSesion, panelRegistro, panelPrincipal;
    private JPanel cardPanel;
    private CardLayout cardLayout;

    private JTextField panelCredencialesUsuario;
    private JTextField panelCredencialesContraseña;
    private JButton botonLogin;

    private String username;

    public MenuPrincipal() {
        setSize(800, 800);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new GridLayout(5, 1));
        JButton botonDatosUsuario = new JButton("Ver mis datos");
        botonDatosUsuario.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                MenuDatosUsuario menuDatosUsuario = new MenuDatosUsuario(username);
                menuDatosUsuario.setVisible(true);
            }
        });
        panelPrincipal.add(botonDatosUsuario);

        JButton botonVerMisListas = new JButton("Ver mis listas");
        botonVerMisListas.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                MenuListas menuListas = new MenuListas(username);
                menuListas.setVisible(true);
            }
        });
        panelPrincipal.add(botonVerMisListas);

        JButton botonVerHistorial = new JButton("Ver mi historial alquileres");
        botonVerHistorial.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                MenuHistorialAlquileres menuHistorial = new MenuHistorialAlquileres(username);
                menuHistorial.setVisible(true);
            }
        });
        panelPrincipal.add(botonVerHistorial);

        JButton botonBuscarPelicula = new JButton("Buscar Pelicula");
        botonBuscarPelicula.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                BuscarPelicula buscaPelicula = new BuscarPelicula(username);
                buscaPelicula.setVisible(true);
            }
        });
        panelPrincipal.add(botonBuscarPelicula);

        panelInicioSesion = new JPanel();
        panelInicioSesion.setLayout(new GridLayout(4, 1));
        add(panelInicioSesion);
        panelCredencialesUsuario = new JTextField("u1");
        panelCredencialesContraseña = new JTextField("Contraseña");
        botonLogin = new JButton("Iniciar sesión");
        botonLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                // Falta verificar los datos
                username = panelCredencialesUsuario.getText();
                cardLayout.show(cardPanel, "principal");
            }
        });
        JButton botonRegistro = new JButton("Registrarse");
        botonRegistro.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                cardLayout.show(cardPanel, "registro");
            }
        });
        panelInicioSesion.add(panelCredencialesUsuario);
        panelInicioSesion.add(panelCredencialesContraseña);
        panelInicioSesion.add(botonLogin);
        panelInicioSesion.add(botonRegistro);

        panelRegistro = new JPanel();
        JLabel l = new JLabel("registro");
        panelRegistro.add(l);
        JButton botonRegistroAtras = new JButton("Atrás");
        botonRegistroAtras.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                cardLayout.show(cardPanel, "iniciosesion");
            }
        });
        panelRegistro.add(botonRegistroAtras);

        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);
        cardPanel.add(panelRegistro, "registro");
        cardPanel.add(panelInicioSesion, "iniciosesion");
        cardPanel.add(panelPrincipal, "principal");

        add(cardPanel);
        cardLayout.show(cardPanel, "iniciosesion");
    }
}
