package org.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuPrincipal extends JFrame {
    private JPanel panelCredenciales, panelPrincipal;

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

        panelCredenciales = new JPanel();
        panelCredenciales.setLayout(new GridLayout(3, 1));
        add(panelCredenciales);
        panelCredencialesUsuario = new JTextField("u1");
        panelCredencialesContraseña = new JTextField("Contraseña");
        botonLogin = new JButton("Iniciar sesión");
        botonLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                // Falta verificar los datos
                username = panelCredencialesUsuario.getText();
                remove(panelCredenciales);
                add(panelPrincipal);
                validate();
            }
        });
        panelCredenciales.add(panelCredencialesUsuario);
        panelCredenciales.add(panelCredencialesContraseña);
        panelCredenciales.add(botonLogin);
    }
}
