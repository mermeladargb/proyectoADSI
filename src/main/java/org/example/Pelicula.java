package org.example;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;
public class Pelicula {
	private int ID;
	private String titulo;
	private String descripcion;
	private boolean aceptada;
	private ArrayList<Valoracion> listaValoraciones;
	private Usuario solicitadaPor;
	private Usuario aceptadaPor;

	public Pelicula(int pID, String pTitulo, String pDescrip, Usuario pSolicitadaPor, Usuario pAceptadaPor )
	{
		ID=pID;
		titulo=pTitulo;
		descripcion=pDescrip;
		aceptada=false;
		listaValoraciones= new ArrayList<Valoracion>();
		solicitadaPor=pSolicitadaPor;
		aceptadaPor=pAceptadaPor;
	}

	public Pelicula() {

	}


	//Tienen nombre parecido si nombrePeli es una subcadena del titulo o si tiene determinada cantidad igual de caracteres
	public JSONObject tieneNombreParecido(String nombrePeli){
		JSONObject unJSON= new JSONObject();

		if (titulo.toLowerCase().contains(nombrePeli.toLowerCase())){
			unJSON.put("id",ID);
			unJSON.put("titulo",titulo);
			unJSON.put("media", String.format("%.2f", getMediaValoracion()));
		}
		else {
			int cont = 0;
			ArrayList<Integer> indicesUsados= new ArrayList<Integer>();
			for (int i = 0; i < nombrePeli.length(); i++) {
				char c = nombrePeli.toLowerCase().charAt(i);

				// Buscar el carácter en 'base', evitando usar posiciones ya utilizadas
				for (int j = 0; j < titulo.length(); j++) {
					if (titulo.charAt(j) == c && !indicesUsados.contains(j)) {
						cont++;
						indicesUsados.add(j); // Marcar la posición como utilizada
						break; // Salir del bucle interno para evitar contar más de una vez
					}
				}
			}

			if (cont >= titulo.length() / 2  && cont >= nombrePeli.length() / 2) {
				unJSON.put("id", ID);
				unJSON.put("titulo", titulo);
				unJSON.put("media", String.format("%.2f", getMediaValoracion()));
			} else {
				unJSON = null;
			}
		}
		return unJSON;

	}

	public int getID() {
		return ID;
	}


	public Valoracion getValoracion(Usuario pUsuario)
	{
		if (listaValoraciones != null) {
			for (Valoracion valoracion : listaValoraciones) {
				if (valoracion.getUser().equals(pUsuario.getUsername())) {
					return valoracion;
				}
			}
		}
		return null;
	}
	public void guardarValoracion(Usuario user, String reseña, int nota) {
		if (listaValoraciones == null) {
			listaValoraciones = new ArrayList<>(); // Inicializar la lista si es null
		}
		//Verificacion si la pelicula esta valorada
		for (Valoracion valoracion : listaValoraciones) {
			if (valoracion.getUser().equals(user.getUsername())) {
				valoracion.setReseña(reseña);
				valoracion.setNota(nota);
				return;
			}
		}
		//Si el user no ha hecho ninguna valoracion a la pelicula
		Valoracion nuevaValoracion = new Valoracion(nota, reseña, user);
		listaValoraciones.add(nuevaValoracion);

	}

	public ArrayList<Valoracion>verValoraciones(String username) {

		ArrayList<Valoracion> valoracionesUsuario = new ArrayList<>();
		ArrayList<Valoracion> otrasValoraciones = new ArrayList<>();

		//La valoracion del usuario que solicita ver las valoraciones se va a "valoracionesUsuario" y las demas a "otrasValoraciones"
		for (Valoracion valoracion : listaValoraciones) {
			if (valoracion.getUser().equals(username)) {
				valoracionesUsuario.add(valoracion);
			} else {
				otrasValoraciones.add(valoracion);
			}
		}

		//Se combinan las dos listas
		valoracionesUsuario.addAll(otrasValoraciones);

		return valoracionesUsuario;
	}

	public void setID(int id) {
	}

	public void setTitulo(String titulo) {
	}

	public void setDescripcion(String descripcion) {
	}

	public void setAceptada(boolean aceptada) {
	}

	public String getTitulo() {
		return titulo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public boolean estaAceptada() {
		return aceptada;
	}

	public Usuario getSolicitadaPor() {
		return solicitadaPor;
	}

	public Usuario getAceptadaPor() {
		return aceptadaPor;
	}
	//Se obtiene la media de las valoraciones de la Pelicula
	public double getMediaValoracion(){
		return this.listaValoraciones.stream().mapToDouble(Valoracion::getPuntuacion).sum() / this.listaValoraciones.size();
	}
	public void addValoracion(Valoracion pValoracion){
		listaValoraciones.add(pValoracion);

	}

	public void setSolicitadaPor(Usuario solicitadaPor) {
		this.solicitadaPor = solicitadaPor;
	}

	public void setAceptadaPor(Usuario aceptadaPor) {
		this.aceptadaPor = aceptadaPor;
	}
}
