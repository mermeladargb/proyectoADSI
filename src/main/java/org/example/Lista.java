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
	
	public boolean esDeUsuario(String username) {
		return this.user.getUsername().equals(username);
	}
	
	public String getNombre() {
		return this.nombre;
	}
	
	public void cambiarVisibilidad() {
		this.visible = !this.visible;
	}
	
	public void añadirPelicula(Pelicula pelicula) {
		this.listaPeliculas.add(pelicula);
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
