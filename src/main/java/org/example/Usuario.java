package org.example;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Usuario {
	private String username;
	private String contraseña;
	private String nombre;
	private String apellido;
	private String correo;
	private Admin aceptado_Por;
	private ArrayList<Alquiler> alquileres;
	
	public Usuario(String username, String contraseña, String nombre, String apellido, String correo, Admin aceptado_Por, ArrayList<Alquiler> alquileres) {
		this.username=username;
		this.contraseña=contraseña;
		this.nombre=nombre;
		this.apellido=apellido;
		this.correo=correo;
		this.alquileres=alquileres;
		this.aceptado_Por=aceptado_Por;
	}
	
	public String getUsername() {
		return username;
	}
	
	public String añadirAlquiler(Pelicula unPelicula) {
		String fecha= new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
		Alquiler alquiler= new Alquiler(fecha, unPelicula);
		this.alquileres.add(alquiler);
		String mensaje = "Alquiler añadido correctamente";
		return mensaje;
	}
	
	public String mostrarAlquileres() {
		String mensaje="";
		for(int i = 0; i<alquileres.size(); i++) {
			String aux=alquileres.get(i).getPelicula().getTitulo();
			mensaje= mensaje  + aux + "\n";
		}
		return mensaje;
		
	}
	
	public Admin setAceptadoPor(Admin admin){
		this.aceptado_Por=admin;
		return this.aceptado_Por;
	}

	public String getContraseña() {
		return contraseña;
	}	
	
	public void actualizarCuenta(String username, String contraseña, String nombre, String apellido, String correo) {
		this.username=username;
		this.contraseña=contraseña;
		this.nombre=nombre;
		this.apellido=apellido;
		this.correo=correo;
	}
	
	public String enviarSolicitud(Pelicula pelicula) {
		ArrayList<Pelicula> solicitudes=GestorSolicitudesPeliculas.getmGestorSolicutudesPeliculas().getSolicitudes();
		solicitudes.add(pelicula);
		String mensaje="Enviado solicitud de la Pelicula al GestorSolicitudesPeliculas" + pelicula.getTitulo() + "\n";
		return mensaje;
	}

	public Admin getAceptado_Por() {
		return aceptado_Por;
	}
	


	public boolean validarDatos() {
	    boolean aceptado = true;

	    // Verificar longitud de la contraseña
	    if (this.contraseña.length() < 8) {
	        System.out.println("Error: La contraseña debe tener al menos 8 caracteres.");
	        aceptado = false;
	    }

	    // Verificar si la contraseña contiene al menos un carácter especial
	    Pattern specialCharPattern = Pattern.compile("[^a-zA-Z0-9]");
	    Matcher matcher = specialCharPattern.matcher(this.contraseña);
	    if (!matcher.find()) {
	        System.out.println("Error: La contraseña debe contener al menos un carácter especial.");
	        aceptado = false;
	    }

	    // Verificar si el correo tiene la estructura correcta
	    Pattern emailPattern = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");
	    matcher = emailPattern.matcher(this.correo);
	    if (!matcher.matches()) {
	        System.out.println("Error: El correo electrónico no tiene una estructura válida.");
	        aceptado = false;
	    }

	    return aceptado;
	}


	public ArrayList<Alquiler> getAlquileres() {
		return alquileres;
	}

	public String getCorreo() {
		return correo;
	}

	
	
}

