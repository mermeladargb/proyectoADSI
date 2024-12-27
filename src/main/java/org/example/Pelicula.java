package org.example;

import java.util.ArrayList;
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

	public JSONObject tieneNombreParecido(String nombrePeli){
		JSONObject unJSON= new JSONObject();

		if (titulo.toLowerCase().contains(nombrePeli.toLowerCase())){
			unJSON.put("id",ID);
			unJSON.put("titulo",titulo);
		}
		else{
			unJSON=null;
		}
		return unJSON;
		
	}
	public int getID() {
		return ID;
	}
	
	public Valoracion getValoracion(Usuario pUsuario)
	{
		return null;
	}
	public void guardarValoracion(Usuario user, String reseña, int nota) {

	}
	//TODO Creo que calcularPromedio debería de devolver un valor que fuera float
	private void calcularPromedio() {
		listaValoraciones.stream().mapToDouble(Valoracion::getPuntuacion).average();
	}
	
	public ArrayList<Valoracion>verValoraciones(String username) {
		return null;
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
	public double getMediaValoracion(){
		return  this.listaValoraciones.stream().mapToDouble(Valoracion::getPuntuacion).sum() / this.listaValoraciones.size();
	}
}
