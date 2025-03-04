package org.gui;

import org.example.VideoClub;
import org.json.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MenuPrincipal extends JFrame {
    private JPanel panelInicioSesion, panelRegistro, panelPrincipal;
    private JPanel cardPanel;
    private CardLayout cardLayout;

    private JTextField panelCredencialesUsuario;
    private JPasswordField panelCredencialesContraseña;
    private JButton botonLogin;

    private JTextField registroUsuario;
    private JPasswordField registroContraseña;
    private JTextField registroNombre;
    private JTextField registroApellido;
    private JTextField registroCorreo;
    private JButton botonRegistrar;
    private JLabel labelErrorRegistro;

    private String username;
    private boolean esAdmin;

    public MenuPrincipal() {
        setSize(800, 800);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

        // Agregar un WindowListener para interceptar el evento de cierre
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                cardLayout.show(cardPanel, "iniciosesion");
            }
        });

        // Inicializar cardLayout y cardPanel
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        // Panel principal con funcionalidades
        panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new GridLayout(10, 1));

        // Panel de inicio de sesión
        panelInicioSesion = new JPanel();
        panelInicioSesion.setLayout(new GridLayout(6, 1));
        panelCredencialesUsuario = new JTextField();
        panelCredencialesContraseña = new JPasswordField();
        botonLogin = new JButton("Iniciar sesión");
        botonLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String user = panelCredencialesUsuario.getText();
                String password = new String(panelCredencialesContraseña.getPassword());
                JSONObject respuesta = VideoClub.getUnVideoClub().verificarInicioDeSesion(user, password);
                if (respuesta.getString("estado").equals("exitoso")) {
                    username = user;
                    esAdmin = respuesta.getBoolean("esAdmin");
                    crearMenuPrincipal();
                    cardLayout.show(cardPanel, "principal");
                } else {
                    JOptionPane.showMessageDialog(null, respuesta.getString("mensaje"));
                }
            }
        });

        // Inicialización de botón de registro
        JButton botonRegistro = new JButton("Registrarse");
        botonRegistro.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                cardLayout.show(cardPanel, "registro");
            }
        });

        // Añadir componentes al panel de inicio de sesión
        panelInicioSesion.add(new JLabel("Username:"));
        panelInicioSesion.add(panelCredencialesUsuario);
        panelInicioSesion.add(new JLabel("Contraseña:"));
        panelInicioSesion.add(panelCredencialesContraseña);
        panelInicioSesion.add(botonLogin);
        panelInicioSesion.add(botonRegistro);

        // Configuración del panel de registro
        panelRegistro = new JPanel();
        panelRegistro.setLayout(new BoxLayout(panelRegistro, BoxLayout.Y_AXIS));

        registroUsuario = new JTextField(20);
        registroContraseña = new JPasswordField(20);
        registroNombre = new JTextField(20);
        registroApellido = new JTextField(20);
        registroCorreo = new JTextField(20);
        labelErrorRegistro = new JLabel();
        labelErrorRegistro.setForeground(Color.RED);
        labelErrorRegistro.setPreferredSize(new Dimension(400, 30)); // Ajusta el tamaño de la etiqueta para asegurar que el mensaje se muestre completo

        botonRegistrar = new JButton("Registrar");
        botonRegistrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String user = registroUsuario.getText();
                String password = new String(registroContraseña.getPassword());
                String nombre = registroNombre.getText();
                String apellido = registroApellido.getText();
                String correo = registroCorreo.getText();

                JSONObject respuesta = VideoClub.getUnVideoClub().verificarRegistro(nombre, apellido, user, password, correo);
                if (respuesta.getString("estado").equals("exitoso")) {
                    JOptionPane.showMessageDialog(null, "Registro exitoso. Esperando aprobación del administrador.");
                    cardLayout.show(cardPanel, "iniciosesion"); // Volver a la pantalla de inicio de sesión
                } else {
                    labelErrorRegistro.setText("<html>El nombre del usuario debe ser único. Además, la contraseña debe tener al menos 8 caracteres, un carácter especial y una dirección de correo válida.</html>");
                }
            }
        });

        // Añadir componentes al panel de registro
        JButton botonRegistroAtras = new JButton("Atrás");
        botonRegistroAtras.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                cardLayout.show(cardPanel, "iniciosesion");
            }
        });

        panelRegistro.add(new JLabel("Nuevo Usuario:"));
        panelRegistro.add(registroUsuario);
        panelRegistro.add(new JLabel("Contraseña:"));
        panelRegistro.add(registroContraseña);
        panelRegistro.add(new JLabel("Nombre:"));
        panelRegistro.add(registroNombre);
        panelRegistro.add(new JLabel("Apellido:"));
        panelRegistro.add(registroApellido);
        panelRegistro.add(new JLabel("Correo:"));
        panelRegistro.add(registroCorreo);
        panelRegistro.add(botonRegistrar);
        panelRegistro.add(botonRegistroAtras);
        panelRegistro.add(labelErrorRegistro); // Añadir el mensaje de error al panel de registro

        // Inicialización del cardLayout y cardPanel
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        // Configuración de paneles
        panelPrincipal = new JPanel();
        cardPanel.add(panelInicioSesion, "iniciosesion");
        cardPanel.add(panelRegistro, "registro");
        cardPanel.add(panelPrincipal, "principal");

        // Añadir cardPanel al JFrame
        add(cardPanel);
        cardLayout.show(cardPanel, "iniciosesion");
    }



    private void crearMenuPrincipal() {
        panelPrincipal.removeAll();
    
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
    
        JButton botonBuscarListas = new JButton("Buscar listas");
        botonBuscarListas.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                MenuBuscarListas menuBuscarListas = new MenuBuscarListas();
                menuBuscarListas.setVisible(true);
            }
        });
        panelPrincipal.add(botonBuscarListas);
    
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
    
        JButton botonPedirPeliculas = new JButton("Pedir Peliculas");
        botonPedirPeliculas.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                PedirPeliculas pedirPeliculas = new PedirPeliculas(username);
                pedirPeliculas.setVisible(true);
            }
        });
        panelPrincipal.add(botonPedirPeliculas);
    

    
        if (esAdmin) {
            // Funcionalidades adicionales para administrador
        
            JButton botonMostrarSolicitudes = new JButton("Mostrar Solicitudes");
            panelPrincipal.add(botonMostrarSolicitudes);
        
            JButton botonEliminarCuentas = new JButton("Eliminar Cuentas");
            panelPrincipal.add(botonEliminarCuentas);
        
            JButton botonModificarCuentas = new JButton("Modificar Cuentas");
            panelPrincipal.add(botonModificarCuentas);
        
            JButton botonAceptarPeticiones = new JButton("Aceptar Peticiones");
            panelPrincipal.add(botonAceptarPeticiones);
        
            // Acción para el botón Mostrar Solicitudes
            botonMostrarSolicitudes.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    new VerSolicitudes(username);
                }
            });
        
            // Acción para el botón Eliminar Cuentas
            botonEliminarCuentas.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    new EliminarCuentas(username);
                }
            });
        
            // Acción para el botón Modificar Cuentas
            botonModificarCuentas.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    new ModificarCuentas(username);
                }
            });
        
            // Acción para el botón Aceptar Peticiones
            botonAceptarPeticiones.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    new AceptarPeticiones(username);
                }
            });
        }
        
        panelPrincipal.revalidate();
        panelPrincipal.repaint();
    }
}
