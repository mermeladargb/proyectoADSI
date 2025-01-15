package org.gui;

import org.example.VideoClub;
import org.json.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ModificarCuentas extends JFrame {
    private JPanel panelModificarCuentas;

    public ModificarCuentas(String adminUsername) {
        panelModificarCuentas = new JPanel();
        setContentPane(panelModificarCuentas);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);

        ArrayList<JSONObject> usuarios = VideoClub.getUnVideoClub().getUsuariosJson();

        for (JSONObject usuario : usuarios) {
            if (!usuario.getBoolean("esAdmin")) {
                JPanel usuarioPanel = new JPanel();
                usuarioPanel.add(new JLabel(usuario.getString("username")));
                JButton modificarButton = new JButton("Modificar");

                modificarButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent actionEvent) {
                        String username = usuario.getString("username");
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

                usuarioPanel.add(modificarButton);
                panelModificarCuentas.add(usuarioPanel);
            }
        }

        setVisible(true);
    }
}
