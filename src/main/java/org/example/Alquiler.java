package org.example;

public class Alquiler {
	private String fecha;
	private Pelicula pelicula;
	
	
	

	public Alquiler(String fecha, Pelicula unPelicula) {
		this.fecha=fecha;
		this.pelicula=unPelicula;
	}

	public String mostrarAlquiler() {
		String mensaje="Titulo: "+ this.pelicula.getTitulo() + " Fecha: " + this.fecha + "\n";
		return mensaje;
	}

	public Pelicula getPelicula() {
		return pelicula;
	}
	
	
	
}
