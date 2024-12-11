package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GestorListas {
	private static GestorListas unGestorListas = new GestorListas();
	private List<Lista> listas;
	
	
	private GestorListas() {
		this.listas = new ArrayList<>();
	}
	
	public static GestorListas getGestorListas() {
		return unGestorListas;
	}
	
	public void crearLista(Usuario usuario, String nombreLista) {
		Lista l = new Lista(usuario, nombreLista);
		listas.add(l);
	}
	
	public List<String> getListasUsuario(String username) {
		return listas.stream()
				.filter(l -> l.getNombreUsuario() == username)
				.map(l -> l.getNombre())
				.collect(Collectors.toList());
	}
	
	public List<String> getListaUsuario(String username, String nombreLista) {
		List<Lista> lista = listas.stream()
				.filter(l -> l.getNombreUsuario() == username)
				.filter(l -> l.getNombre() == nombreLista)
				.collect(Collectors.toList());
		return lista.get(0).getPeliculas();
	}
	
	public void añadirPeliculaALista(String username, String nombreLista, Pelicula pelicula) {
		List<Lista> lista = listas.stream()
				.filter(l -> l.getNombreUsuario() == username)
				.filter(l -> l.getNombre() == nombreLista)
				.collect(Collectors.toList());
		lista.get(0).añadirPelícula(pelicula);
	}
	
	public List<String> buscarLista(String nombreLista) {
		return null;
	}
	
	public void cambiarVisibilidadLista(String username, String nombreLista) {
		List<Lista> lista = listas.stream()
				.filter(l -> l.getNombreUsuario() == username)
				.filter(l -> l.getNombre() == nombreLista)
				.collect(Collectors.toList());
		lista.get(0).cambiarVisibilidad();
	}
}
