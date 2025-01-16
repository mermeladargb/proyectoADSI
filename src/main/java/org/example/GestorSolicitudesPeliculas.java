package org.example;

import java.util.ArrayList;
import java.util.Iterator;

import javax.sound.sampled.UnsupportedAudioFileException;

public class GestorSolicitudesPeliculas {
    private static GestorSolicitudesPeliculas mGestorSolicitudesPeliculas=new GestorSolicitudesPeliculas();
    private ArrayList<SolicitudPelicula> solicitudes;

    private GestorSolicitudesPeliculas(){
        solicitudes=new ArrayList<SolicitudPelicula>();
    }

    public static GestorSolicitudesPeliculas getmGestorSolicutudesPeliculas()
    {
        return mGestorSolicitudesPeliculas;
    }

    private Iterator<SolicitudPelicula> iterator()
	{
		return solicitudes.iterator();
	}

    public ArrayList<SolicitudPelicula> getSolicitudes() {
        return null;
    }

    public boolean esta(String pTitulo) {
        for (SolicitudPelicula solicitud : solicitudes) {
			if (solicitud.getTitulo() == pTitulo) {
				return true;
			}
		}
		return false;
    }

    // VOID TEMPORAL
    public void validarPelicula(String pTitulo, String pUser) {

        //LLAMADA A LA API PARA TITULO Y DESCRIPCION

        //TODO

        //Obtencion usuario que solicita la pelicula
        Usuario solicitadaPor = null;
        for (SolicitudPelicula solicitud : solicitudes) {
			if (solicitud.getTitulo() == pTitulo) {
				solicitadaPor = solicitud.solicitadaPor();
			}
		}

        //Obtencion administrador que acepta la pelicula
        GestorUsuarios gestorUsuarios = GestorUsuarios.getGestorUsuarios();
        Usuario aceptadaPor = gestorUsuarios.getUsuario(pUser);

        //Creacion y adicion de la pelicula al gestor de peliculas
        //TODO
        Pelicula peliNueva = new Pelicula(80085,"Up","Pelicula del siglo en la que un señor mayor decide atar globos a su casa para ver unas cataratas en Venezuela", solicitadaPor, aceptadaPor);
        GestorPeliculas gestorPeliculas = GestorPeliculas.getGestorPeliculas();
        gestorPeliculas.addPelicula(peliNueva);

    }
}
