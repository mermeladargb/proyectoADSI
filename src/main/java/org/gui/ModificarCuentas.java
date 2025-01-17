package org.gui;

import org.example.VideoClub;
import org.json.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

public class ModificarCuentas extends JFrame {
    private JPanel panelModificarCuentas;

    public ModificarCuentas(String adminUsername) {
        panelModificarCuentas = new JPanel(new GridLayout(0, 1));
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

                        JTextField nombreField = new JTextField(datosUsuario.getString("nombre"));
                        JTextField apellidoField = new JTextField(datosUsuario.getString("apellido"));
                        JTextField correoField = new JTextField(datosUsuario.getString("correo"));
                        JPasswordField contraseñaField = new JPasswordField(datosUsuario.getString("contraseña"));

                        Object[] message = {
                            "Nombre:", nombreField,
                            "Apellido:", apellidoField,
                            "Correo:", correoField,
                            "Contraseña:", contraseñaField
                        };

                        // Abre una nueva ventana para modificar los datos del usuario
                        JFrame modificarFrame = new JFrame("Modificar Cuenta");
                        modificarFrame.setSize(400, 300);
                        modificarFrame.setLayout(new GridLayout(0, 1));
                        modificarFrame.add(new JLabel("Nombre:"));
                        modificarFrame.add(nombreField);
                        modificarFrame.add(new JLabel("Apellido:"));
                        modificarFrame.add(apellidoField);
                        modificarFrame.add(new JLabel("Correo:"));
                        modificarFrame.add(correoField);
                        modificarFrame.add(new JLabel("Contraseña:"));
                        modificarFrame.add(contraseñaField);

                        JButton saveButton = new JButton("Guardar Cambios");
                        saveButton.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent actionEvent) {
                                JSONObject respuesta = VideoClub.getUnVideoClub().modificarCuenta(
                                    adminUsername, 
                                    nombreField.getText(), 
                                    apellidoField.getText(), 
                                    username, 
                                    new String(contraseñaField.getPassword()), 
                                    correoField.getText()
                                );

                                if (respuesta.getString("estado").equals("exitoso")) {
                                    JOptionPane.showMessageDialog(modificarFrame, "Cuenta modificada correctamente");
                                    modificarFrame.dispose();
                                    panelModificarCuentas.revalidate();
                                    panelModificarCuentas.repaint();
                                } else {
                                    JOptionPane.showMessageDialog(modificarFrame, "Error: " + respuesta.getString("mensaje"));
                                }
                            }
                        });

                        modificarFrame.add(saveButton);
                        modificarFrame.setVisible(true);

                        // Listener para manejar el cierre de la ventana de modificación
                        modificarFrame.addWindowListener(new WindowAdapter() {
                            @Override
                            public void windowClosing(WindowEvent e) {
                                int confirm = JOptionPane.showConfirmDialog(
                                    modificarFrame,
                                    "¿Estás seguro de que deseas cerrar la ventana? Los cambios no se guardarán.",
                                    "Confirmar Cierre",
                                    JOptionPane.YES_NO_OPTION,
                                    JOptionPane.QUESTION_MESSAGE);
                                if (confirm == JOptionPane.YES_OPTION) {
                                    modificarFrame.dispose();
                                }
                            }
                        });
                    }
                });

                usuarioPanel.add(modificarButton);
                panelModificarCuentas.add(usuarioPanel);
            }
        }

        setVisible(true);
    }
}
