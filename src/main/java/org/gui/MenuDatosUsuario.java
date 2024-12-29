package org.gui;

import javax.swing.*;
import java.awt.*;

public class MenuDatosUsuario extends JFrame {

    public MenuDatosUsuario(String username) {
        setSize(800, 800);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 1));
        JLabel l1 = new JLabel("Usuario: " + username);
        JLabel l2 = new JLabel("Email: bar");
        panel.add(l1);
        panel.add(l2);
        setContentPane(panel);
    }
}
