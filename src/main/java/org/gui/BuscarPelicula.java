package org.gui;

import org.example.Pelicula;
import org.example.VideoClub;
import org.json.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

public class BuscarPelicula extends JFrame {
    private String username;
    private CardLayout cardLayout;
    private JPanel cardPanel;
    private JPanel panelBusqueda;
    private JPanel panelResultados;
    private JPanel panelDetalles;
    private JSONObject peliculas;
    private DefaultListModel<String> listModel; // Modelo para títulos
    private Map<String, JSONObject> peliculaMap; // Mapeo de títulos a JSONObject
    private JTextArea detallesArea;
    private int id;

    public BuscarPelicula(String username) {
        this.username = username;
        this.peliculas = new JSONObject();

        // Configuración inicial del JFrame
        setTitle("Buscar Películas");
        setSize(600, 500);

        // Usar CardLayout para alternar entre paneles
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        // Inicializar paneles
        initPanelBusqueda();
        initPanelResultados();
        initPanelDetalles();

        // Agregar paneles al CardLayout
        cardPanel.add(panelBusqueda, "Busqueda");
        cardPanel.add(panelResultados, "Resultados");
        cardPanel.add(panelDetalles, "Detalles");

        // Agregar CardPanel al JFrame
        add(cardPanel);

        setVisible(true);
    }

    private void initPanelBusqueda() {
        panelBusqueda = new JPanel();
        panelBusqueda.setLayout(new GridLayout(3, 2, 10, 10));

        // Etiqueta y campo de texto para buscar título
        JLabel titleLabel = new JLabel("Título:");
        JTextField titleField = new JTextField();

        // Botón para buscar
        JButton searchButton = new JButton("Buscar");

        // Acción del botón de buscar
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nombrePeli = titleField.getText().toLowerCase();

                // Actualizar el panel de resultados con las películas encontradas
                peliculas = VideoClub.getUnVideoClub().mostrarPeliculasSimilares(nombrePeli);
                System.out.println(peliculas);
                mostrarResultados(peliculas);

                // Cambiar al panel de resultados
                cardLayout.show(cardPanel, "Resultados");
            }
        });

        // Agregar componentes al panel de búsqueda
        panelBusqueda.add(titleLabel);
        panelBusqueda.add(titleField);
        panelBusqueda.add(new JLabel()); // Espacio vacío
        panelBusqueda.add(searchButton);
    }

    private void initPanelResultados() {
        //Crear panel GridBagLayout para los resultados
        panelResultados = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        //Añadir titulo "Resultados de la búsqueda"
        JLabel resultadosLabel = new JLabel("Resultados de la búsqueda:", SwingConstants.LEFT);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 3;
        panelResultados.add(resultadosLabel, gbc);

        //Botón para regresar al panel de búsqueda
        JButton backButton = new JButton("Volver");
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        panelResultados.add(backButton, gbc);

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel, "Busqueda");
            }
        });
    }

    private void initPanelDetalles() {
        panelDetalles = new JPanel();
        panelDetalles.setLayout(new BorderLayout());

        // Etiqueta para mostrar detalles
        JLabel detallesLabel = new JLabel("Detalles de la película:", SwingConstants.CENTER);
        panelDetalles.add(detallesLabel, BorderLayout.NORTH);

        // Área de texto para mostrar información detallada
        detallesArea = new JTextArea();
        detallesArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(detallesArea);
        panelDetalles.add(scrollPane, BorderLayout.CENTER);

        // Panel para botones
        JPanel botonesPanel = new JPanel();
        botonesPanel.setLayout(new FlowLayout());

        // Botón para regresar al panel de resultados
        JButton backButton = new JButton("Volver");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel, "Resultados");
            }
        });
        botonesPanel.add(backButton);
        // Botón para alquilar película
        JButton alquilarButton = new JButton("Alquilar");
        alquilarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                VideoClub.getUnVideoClub().alquilarPeli(username, id);
                JOptionPane.showMessageDialog(panelDetalles,
                        "Película alquilada con éxito.",
                        "Alquiler",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        });
        botonesPanel.add(alquilarButton);

        panelDetalles.add(botonesPanel, BorderLayout.SOUTH);
    }

    private void mostrarResultados(JSONObject json) {
        panelResultados.removeAll();
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5); //Espacio entre los componentes de la pelicula
        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridx = 0;
        gbc.gridy = 0;

        JLabel resultadosLabel = new JLabel("Resultados de la búsqueda:", SwingConstants.CENTER);
        gbc.gridwidth = 3;
        panelResultados.add(resultadosLabel, gbc);
        gbc.gridy++;

        for (Object obj : json.getJSONArray("peliculas")) {
            JSONObject pelicula = (JSONObject) obj;
            String titulo = pelicula.getString("titulo");
            int idPelicula = pelicula.getInt("id");

            JPanel peliculaPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            JLabel tituloLabel = new JLabel(titulo);

            // Botón "Ver detalles"
            JButton verDetallesButton = new JButton("Ver detalles");
            verDetallesButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    mostrarDetalles(idPelicula);
                    cardLayout.show(cardPanel, "Detalles");
                }
            });

            // Botón "Ver reseñas"
            JButton verReseñasButton = new JButton("Ver reseñas");
            verReseñasButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    new MenuVerReseñas(idPelicula, username).setVisible(true);
                }
            });

            peliculaPanel.add(tituloLabel);
            peliculaPanel.add(verDetallesButton);
            peliculaPanel.add(verReseñasButton);

            gbc.gridx = 0;
            gbc.gridwidth = 3;
            panelResultados.add(peliculaPanel, gbc);
            gbc.gridy++;
        }

        panelResultados.revalidate();
        panelResultados.repaint();
    }

    private void mostrarDetalles(int idPelicula) {
        id = idPelicula;
        JSONObject jsonPeli = VideoClub.getUnVideoClub().seleccionarPelicula(idPelicula);
        detallesArea.setText("");

        // Mostrar información detallada de la película
        String detalles = "Título: " + jsonPeli.get("titulo") + "\n" +
                "Descripción: " + jsonPeli.get("descrip") + "\n" +
                "Media: " + jsonPeli.get("media") + "\n";

        detallesArea.setText(detalles);
        panelDetalles.revalidate();
        panelDetalles.repaint();
    }
}
