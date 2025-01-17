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
		//dbGestor = new DBGestor();
		//cargarPeliculasDesdeBD();
	}

	private void cargarPeliculasDesdeBD() {
		//pelis = dbGestor.cargarPeliculas();
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

	public Pelicula buscarPeliSeleccionada(int idPeli) {
		for (Pelicula pelicula : pelis) {
			if (pelicula.getID() == idPeli) {
				return pelicula;
			}
		}
		System.out.println("Película no encontrada con ID:");
		return null;
	}



	public JSONObject mostrarPeliculas(String nombrePeli) {
		JSONArray lJSON= new JSONArray();
		JSONObject resultado= new JSONObject();
		for (Pelicula unaPeli:pelis)
		{
			resultado= unaPeli.tieneNombreParecido(nombrePeli);
			if (resultado!=null)
			{
				lJSON.put(resultado);
			}

		}
		resultado= new JSONObject();
		return  resultado.put("peliculas",lJSON);
	}
	public void addPelicula (Pelicula pPelicula){
		pelis.add(pPelicula);
	}
	public void reset (){
		pelis=new ArrayList<Pelicula>();
	}

	public JSONObject verDetallesPelicula (int pIdPeli) {
		Pelicula unaPelicula= buscarPeliSeleccionada(pIdPeli);
		JSONObject JSON = new JSONObject();
		if (unaPelicula != null) {
			JSON.put("ID", unaPelicula.getID());
			JSON.put("titulo", unaPelicula.getTitulo());
			JSON.put("descrip", unaPelicula.getDescripcion());
			JSON.put("media", String.format("%.2f", unaPelicula.getMediaValoracion()));
		} else {
			JSON = null;
		}
		return JSON;
	}
}
