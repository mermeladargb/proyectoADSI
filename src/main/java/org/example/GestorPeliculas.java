package org.example;

import java.util.ArrayList;
import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONObject;
public class GestorPeliculas {
	
	private static GestorPeliculas unGestorpelis=new GestorPeliculas();
	private ArrayList<Pelicula> pelis;
	private DBGestor dbGestor;
	private GestorPeliculas() {
		pelis=new ArrayList<Pelicula>();
		dbGestor = new DBGestor();
		//cargarPeliculasDesdeBD();
	}

	private void cargarPeliculasDesdeBD() {
		pelis = dbGestor.cargarPeliculas();
		System.out.println("Peliculas cargadas");
	}
/*
	public void agregarPelicula(Pelicula nuevaPeli) {
		pelis.add(nuevaPeli);
		dbGestor.guardarPelicula(nuevaPeli);
		System.out.println("Pelicula guardada");
	}*/
	public static GestorPeliculas getGestorPeliculas() {
		return unGestorpelis;
	}
	private Iterator<Pelicula> iterator()
	{
		return pelis.iterator();
	}
	public JSONArray mostrarPeliculas(String nombrePeli) {
			Iterator<Pelicula>itr=iterator();
			JSONArray JSON1= new JSONArray();
			if (itr.hasNext())
			{
				if(itr.next().tieneNombreParecido(nombrePeli)!="") {
				
				}
			}
			return JSON1;
	}
	
	public Pelicula buscarPeliSeleccionada(int idPeli) {
		return null;
	}


	
}
