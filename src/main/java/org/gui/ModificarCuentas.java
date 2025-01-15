package org.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import org.example.Usuario;
import org.example.VideoClub;

public class ModificarCuentas extends JFrame {
    private JPanel panelModificarCuentas;

    public ModificarCuentas(Usuario admin) {
        panelModificarCuentas = new JPanel();
        setContentPane(panelModificarCuentas);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);

        ArrayList<Usuario> usuarios = VideoClub.getUnVideoClub().getUsuarios();

        for (Usuario usuario : usuarios) {
            if (!usuario.isEsAdmin()) {
                JPanel usuarioPanel = new JPanel();
                usuarioPanel.add(new JLabel(usuario.getUsername()));
                JButton modificarButton = new JButton("Modificar");

                modificarButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent actionEvent) {
                        JTextField usernameField = new JTextField(usuario.getUsername());
                        JTextField nombreField = new JTextField(usuario.getNombre());
                        JTextField apellidoField = new JTextField(usuario.getApellido());
                        JTextField correoField = new JTextField(usuario.getCorreo());
                        JPasswordField contraseñaField = new JPasswordField(usuario.getContraseña());

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
