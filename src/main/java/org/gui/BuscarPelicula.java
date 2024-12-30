package org.gui;

import org.example.Pelicula;
import org.example.VideoClub;
import org.json.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BuscarPelicula extends JFrame {
    private String username;
    private CardLayout cardLayout;
    private JPanel cardPanel;
    private JPanel panelBusqueda;
    private JPanel panelResultados;
    private JPanel panelDetalles;
    private JSONObject peliculas;
    private JList<String> listaResultados; // Lista de títulos
    private DefaultListModel<String> listModel; // Modelo para títulos
    private Map<String, JSONObject> peliculaMap; // Mapeo de títulos a JSONObject
    private JTextArea detallesArea;
    private int id;

    public BuscarPelicula(String username) {
        this.username = username;
        this.peliculas=new JSONObject();

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
        panelResultados = new JPanel();
        panelResultados.setLayout(new BorderLayout());

        // Título del panel de resultados
        JLabel resultadosLabel = new JLabel("Resultados de la búsqueda:", SwingConstants.CENTER);
        panelResultados.add(resultadosLabel, BorderLayout.NORTH);

        // Inicializar modelo y lista de resultados
        listModel = new DefaultListModel<>();
        listaResultados = new JList<>(listModel);
        listaResultados.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Mapeo para buscar detalles a partir del título
        peliculaMap = new HashMap<>();

        // Detectar selección en la lista
        listaResultados.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                String tituloSeleccionado = listaResultados.getSelectedValue();
                if (tituloSeleccionado != null) {
                    JSONObject peliculaSeleccionada = peliculaMap.get(tituloSeleccionado);
                    if (peliculaSeleccionada != null) {
                        mostrarDetalles((int) peliculaSeleccionada.get("id"));
                        cardLayout.show(cardPanel, "Detalles");
                    }
                }
            }
        });

        JScrollPane scrollPane = new JScrollPane(listaResultados);
        panelResultados.add(scrollPane, BorderLayout.CENTER);

        // Botón para regresar al panel de búsqueda
        JButton backButton = new JButton("Volver");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel, "Busqueda");
            }
        });
        panelResultados.add(backButton, BorderLayout.SOUTH);
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
                VideoClub.getUnVideoClub().alquilarPeli(username,id);
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
        // Limpiar modelo y mapeo
        listModel.clear();
        peliculaMap.clear();

        // Agregar películas al modelo y mapeo
        for (Object obj : json.getJSONArray("peliculas")) {
            JSONObject pelicula = (JSONObject) obj;
            String titulo = pelicula.getString("titulo");
            String texto= titulo + " (Media: " + pelicula.get("media") + ")";
            listModel.addElement(texto); // Mostrar solo el título
            peliculaMap.put(texto, pelicula); // Guardar mapeo para detalles
        }

        // Actualizar la lista visible
        listaResultados.setModel(listModel);
    }

    private void mostrarDetalles(int idPelicula) {
        // Obtener los componentes del panel de detalles
        id = idPelicula;
        JSONObject jsonPeli= VideoClub.getUnVideoClub().seleccionarPelicula(idPelicula);
        System.out.println(idPelicula);
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
