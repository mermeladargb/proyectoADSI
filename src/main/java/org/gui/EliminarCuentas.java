package org.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.example.Usuario;
import org.example.VideoClub;


public class EliminarCuentas extends JFrame {
    private JPanel panelEliminarCuentas;

    public EliminarCuentas(Usuario admin) {
        panelEliminarCuentas = new JPanel();
        setContentPane(panelEliminarCuentas);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);

        ArrayList<Usuario> usuarios = VideoClub.getUnVideoClub().getUsuarios(); 

        for (Usuario usuario : usuarios) {
            if (!usuario.isEsAdmin()) {
                JPanel usuarioPanel = new JPanel();
                usuarioPanel.add(new JLabel(usuario.getUsername()));
                JButton eliminarButton = new JButton("Eliminar");

                eliminarButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent actionEvent) {
                        VideoClub.getUnVideoClub().eliminarCuentaSeleccionada(usuario);
                        JOptionPane.showMessageDialog(null, "Usuario eliminado");
                        dispose();
                        new EliminarCuentas(admin);
                    }
                });

                usuarioPanel.add(eliminarButton);
                panelEliminarCuentas.add(usuarioPanel);
            }
        }

        setVisible(true);
    }
}
