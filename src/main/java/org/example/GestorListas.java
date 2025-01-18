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
		if (getListaUsuario(usuario.getUsername(), nombreLista) == null) {
			Lista l = new Lista(usuario, nombreLista);
			listas.add(l);
			DBGestor.getDBGestor().updateSQL(
				"INSERT INTO listas(nombre, visible, username)"
				+ "VALUES('" + nombreLista + "', false, '" + usuario.getUsername() + "')"
			);
		}
	}
	
	public List<String> getListasUsuario(String username) {
		return listas.stream()
				.filter(l -> l.getNombreUsuario().equals(username))
				.map(l -> l.getNombre())
				.collect(Collectors.toList());
	}
	
	public Lista getListaUsuario(String username, String nombreLista) {
		List<Lista> lista = listas.stream()
				.filter(l -> l.getNombreUsuario().equals(username))
				.filter(l -> l.getNombre().equals(nombreLista))
				.collect(Collectors.toList());
		if (!lista.isEmpty())
			return lista.get(0);
		else
			return null;
	}
	
	public void añadirPeliculaALista(String username, String nombreLista, Pelicula pelicula) {
		List<Lista> lista = listas.stream()
				.filter(l -> l.getNombreUsuario().equals(username))
				.filter(l -> l.getNombre().equals(nombreLista))
				.collect(Collectors.toList());
		if (lista.isEmpty())
			return;
		lista.get(0).añadirPelicula(pelicula);

		int idLista = DBGestor.getDBGestor().getIdLista(username, nombreLista);
		DBGestor.getDBGestor().updateSQL(
				"INSERT INTO pertenece_a VALUES(" + idLista + "," + pelicula.getID() + ")"
		);
	}
	
	public List<Lista> buscarLista(String nombreLista) {
		return listas.stream()
				.filter(l -> l.esVisible())
				.filter(l -> l.getNombre().toLowerCase().contains(nombreLista))
				.collect(Collectors.toList());
	}
	
	public void cambiarVisibilidadLista(String username, String nombreLista) {
		List<Lista> lista = listas.stream()
				.filter(l -> l.getNombreUsuario().equals(username))
				.filter(l -> l.getNombre().equals(nombreLista))
				.collect(Collectors.toList());
		if (lista.isEmpty())
			return;
		lista.get(0).cambiarVisibilidad();
		int idLista = DBGestor.getDBGestor().getIdLista(username, nombreLista);
		DBGestor.getDBGestor().updateSQL(
			"UPDATE listas SET visible = NOT visible WHERE id = " + idLista
		);
	}

	// para las junits
	public void reset() {
		this.listas = new ArrayList<>();
	}
}
