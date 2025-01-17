package org.gui;

import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import org.example.VideoClub;
import org.json.JSONObject;

public class MenuDatosUsuario extends JFrame {
    private JPanel panelDatosUsuario;

    public MenuDatosUsuario(String username) {
        setSize(800, 800);
        panelDatosUsuario = new JPanel(new GridLayout(0, 1));
        setContentPane(panelDatosUsuario);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Obtener los datos del usuario
        JSONObject datosUsuario = VideoClub.getUnVideoClub().obtenerDatosUsuario(username);
        if (datosUsuario.has("estado") && datosUsuario.getString("estado").equals("error")) {
            JOptionPane.showMessageDialog(null, datosUsuario.getString("mensaje"));
            dispose();
            return;
        }

        // Mostrar los datos del usuario
        JLabel usernameLabel = new JLabel("Username: " + datosUsuario.getString("username"));
        JTextField nombreField = new JTextField(datosUsuario.getString("nombre"));
        JTextField apellidoField = new JTextField(datosUsuario.getString("apellido"));
        JTextField correoField = new JTextField(datosUsuario.getString("correo"));
        JPasswordField contraseñaField = new JPasswordField(datosUsuario.getString("contraseña"));

        panelDatosUsuario.add(usernameLabel);
        panelDatosUsuario.add(new JLabel("Nombre:"));
        panelDatosUsuario.add(nombreField);
        panelDatosUsuario.add(new JLabel("Apellido:"));
        panelDatosUsuario.add(apellidoField);
        panelDatosUsuario.add(new JLabel("Correo:"));
        panelDatosUsuario.add(correoField);
        panelDatosUsuario.add(new JLabel("Contraseña:"));
        panelDatosUsuario.add(contraseñaField);

        JButton botonActualizarDatos = new JButton("Actualizar Datos");
        botonActualizarDatos.addActionListener(e -> {
            try {
                JSONObject respuesta = VideoClub.getUnVideoClub().actualizarDatos(
                    nombreField.getText(),
                    apellidoField.getText(),
                    username,
                    new String(contraseñaField.getPassword()),
                    correoField.getText()
                );

                if (respuesta.getString("estado").equals("exitoso")) {
                    JOptionPane.showMessageDialog(null, "Datos actualizados correctamente");
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "Error: " + respuesta.getString("mensaje"));
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Ocurrió un error al actualizar los datos");
            }
        });
        panelDatosUsuario.add(botonActualizarDatos);

        setVisible(true);
    }
}
