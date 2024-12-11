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
	private Admin aceptadaPor;
	
	public Pelicula(int pID, String pTitulo, String pDescrip, Usuario pSolicitadaPor, Admin pAceptadaPor )
	{
		ID=pID;
		titulo=pTitulo;
		descripcion=pDescrip;
		aceptada=false;
		listaValoraciones= new ArrayList<Valoracion>();
		solicitadaPor=pSolicitadaPor;
		aceptadaPor=pAceptadaPor;
	}
	
	public String tieneNombreParecido(String nombrePeli){
		String nombre="";
		if (nombrePeli.contains(titulo)){
			nombre=nombrePeli;
		}
		return nombre;
		
	}
	public int getID() {
		return ID;
	}
	public String getNombre() {
		return titulo;
	}
	
	public Valoracion getValoracion(Usuario pUsuario)
	{
		
	}
	public void guardarValoracion(Usuario user, String reseña, int nota) {
		
		
	}
	//TODO Creo que calcularPromedio debería de devolver un valor que fuera float
	private void calcularPromedio() {
		listaValoraciones.stream().mapToDouble(Valoracion::getNota).average();
	}
	
	public ArrayList<Valoracion>verValoraciones(String username) {
		
	}
	
}
