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
        panelInicioSesion.setLayout(new GridLayout(4, 1));
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
        panelRegistro.setLayout(new GridLayout(6, 1));

        registroUsuario = new JTextField();
        registroContraseña = new JPasswordField();
        registroNombre = new JTextField();
        registroApellido = new JTextField();
        registroCorreo = new JTextField();

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
                    JOptionPane.showMessageDialog(null, respuesta.getString("mensaje"));
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
        panelPrincipal.add(botonPedirPeliculas);
        botonPedirPeliculas.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                PedirPeliculas pedirPeliculas = new PedirPeliculas(username);
                pedirPeliculas.setVisible(true);
            }
        });
    
        if (esAdmin) {
            // Funcionalidades adicionales para administrador
            JButton botonModificarCuenta = new JButton("Modificar Cuenta");
            panelPrincipal.add(botonModificarCuenta);
    
            JButton botonMostrarSolicitudes = new JButton("Mostrar Solicitudes");
            panelPrincipal.add(botonMostrarSolicitudes);
    
            JButton botonEliminarCuentas = new JButton("Eliminar Cuentas");
            panelPrincipal.add(botonEliminarCuentas);
    
            JButton botonModificarCuentas = new JButton("Modificar Cuentas");
            panelPrincipal.add(botonModificarCuentas);
    
            JButton botonAceptarPeticiones = new JButton("Aceptar Peticiones");
            panelPrincipal.add(botonAceptarPeticiones);
            
    
            botonModificarCuenta.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    JSONObject datosUsuario = VideoClub.getUnVideoClub().obtenerDatosUsuario(username);
    
                    JTextField usernameField = new JTextField(datosUsuario.getString("username"));
                    JTextField nombreField = new JTextField(datosUsuario.getString("nombre"));
                    JTextField apellidoField = new JTextField(datosUsuario.getString("apellido"));
                    JTextField correoField = new JTextField(datosUsuario.getString("correo"));
                    JPasswordField contraseñaField = new JPasswordField(datosUsuario.getString("contraseña"));
    
                    Object[] message = {
                        "Username:", usernameField,
                        "Nombre:", nombreField,
                        "Apellido:", apellidoField,
                        "Correo:", correoField,
                        "Contraseña:", contraseñaField
                    };
    
                    int option = JOptionPane.showConfirmDialog(null, message, "Modificar Cuenta", JOptionPane.OK_CANCEL_OPTION);
                    if (option == JOptionPane.OK_OPTION) {
                        VideoClub.getUnVideoClub().actualizarDatos(nombreField.getText(), apellidoField.getText(), usernameField.getText(), new String(contraseñaField.getPassword()), correoField.getText());
                        JOptionPane.showMessageDialog(null, "Cuenta modificada correctamente");
                    }
                }
            });
    
            botonMostrarSolicitudes.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    new VerSolicitudes(username);
                }
            });
    
            botonEliminarCuentas.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    new EliminarCuentas(username);
                }
            });
    
            botonModificarCuentas.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    new ModificarCuentas(username);
                }
            });

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
    


    public static void main(String[] args) {
        new MenuPrincipal();
    }
}
