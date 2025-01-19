package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Lista {
	private boolean visible;
	private String nombre;
	private Usuario user;
	private List<Pelicula> listaPeliculas;
	
	
	public Lista(Usuario usuario, String nombreLista) {
		this.user = usuario;
		this.nombre = nombreLista;
		this.listaPeliculas = new ArrayList<>();
		this.visible = false;
	}

	public Lista(Usuario usuario, String nombreLista, boolean visible) {
		this.user = usuario;
		this.nombre = nombreLista;
		this.listaPeliculas = new ArrayList<>();
		this.visible = visible;
	}


	public String getNombre() {
		return this.nombre;
	}
	
	public void cambiarVisibilidad() {
		this.visible = !this.visible;
	}
	
	public void añadirPelicula(Pelicula pelicula) {
		System.out.println("a añadir: " + pelicula.getTitulo() + pelicula.getID());
		for (Pelicula p : listaPeliculas) {
			if (p.getID() == pelicula.getID())
				return;
		}
		this.listaPeliculas.add(pelicula);
		System.out.println("añadida " + pelicula.getID());
		for (Pelicula l : listaPeliculas)
			System.out.println("peli " + l.getID());
	}
	
	public List<String> getPeliculas() {
		return this.listaPeliculas.stream().map(p -> p.getTitulo()).collect(Collectors.toList());
	}
	
	public String getNombreUsuario() {
		return this.user.getUsername();
	}
	
	public boolean esVisible() {
		return this.visible;
	}
}
