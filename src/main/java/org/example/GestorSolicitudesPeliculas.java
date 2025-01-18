package org.example;

import java.util.ArrayList;
import java.util.Iterator;

import javax.sound.sampled.UnsupportedAudioFileException;

import org.json.JSONObject;

public class GestorSolicitudesPeliculas {
    private static GestorSolicitudesPeliculas mGestorSolicitudesPeliculas = new GestorSolicitudesPeliculas();
    private ArrayList<SolicitudPelicula> solicitudes;

    private GestorSolicitudesPeliculas() {
        solicitudes = new ArrayList<SolicitudPelicula>();
    }

    public static GestorSolicitudesPeliculas getmGestorSolicutudesPeliculas() {
        return mGestorSolicitudesPeliculas;
    }

    private Iterator<SolicitudPelicula> iterator() {
        return solicitudes.iterator();
    }

    public ArrayList<SolicitudPelicula> getSolicitudes() {
        return solicitudes; // Corregido para devolver la lista de solicitudes
    }

    public SolicitudPelicula getSolicitud(String pTitulo) {
        for (SolicitudPelicula solicitud : solicitudes){
            if (solicitud.getTitulo().equals(pTitulo)) { // Usar equals() para comparar strings
                return solicitud;
            }
        }
        return null;
    }

    public boolean esta(String pTitulo) {
        for (SolicitudPelicula solicitud : solicitudes) {
            if (solicitud.getTitulo().equals(pTitulo)) { // Usar equals() para comparar strings
                return true;
            }
        }
        return false;
    }

    public void addSolicitud(String pTitulo, String pUser) {
        if (!this.esta(pTitulo)) {
            Usuario user = GestorUsuarios.getGestorUsuarios().getUsuario(pUser);
            SolicitudPelicula solicitud = new SolicitudPelicula(pTitulo, user);
            solicitudes.add(solicitud);
        }
        else{System.out.println("Esta Pelicula ya ha sido solicitada");}
    }

    public void aceptarSolicitudPelicula(String pTitulo, String pUser) {
        //Llamada a la api para obtencion de descripcion e id
        JSONObject pelicula = API.getDatosPelicula(pTitulo);

        String pIDString = pelicula.getString("imdbID");
        int pID = Integer.parseInt(pIDString.substring(2));
        String trama = pelicula.getString("Plot");

        //Obtencion usuario que solicita la pelicula
        Usuario solicitadaPor = null;
        for (SolicitudPelicula solicitud : solicitudes) {
            if (solicitud.getTitulo().equals(pTitulo)) { // Usar equals() para comparar strings
                solicitadaPor = solicitud.solicitadaPor();
                solicitudes.remove(solicitud);

            }
        }

        //Obtencion administrador que acepta la pelicula
        GestorUsuarios gestorUsuarios = GestorUsuarios.getGestorUsuarios();
        Usuario aceptadaPor = gestorUsuarios.getUsuario(pUser);

        //Creacion y adicion de la pelicula al gestor de peliculas
        Pelicula peliNueva = new Pelicula(pID, pTitulo, trama, solicitadaPor, aceptadaPor);
        GestorPeliculas.getGestorPeliculas().addPelicula(peliNueva);
    }

    public void rechazarSolicitudPelicula(String titulo) {
        SolicitudPelicula solicitudPelicula = this.getSolicitud(titulo);
        solicitudes.remove(solicitudPelicula);
    }
}