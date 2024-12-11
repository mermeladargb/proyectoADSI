package org.example;

import java.util.ArrayList;
import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONObject;
public class GestorPeliculas {
	
	private static GestorPeliculas unGestorpelis=new GestorPeliculas();
	private ArrayList<Pelicula> pelis;
	private GestorPeliculas() {
		pelis=new ArrayList<Pelicula>();
	}
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
		
	}
	
}
