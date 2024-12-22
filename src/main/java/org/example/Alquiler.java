package org.example;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Alquiler {
	private Date fecha;
	private Pelicula pelicula;
	
	
	

	public Alquiler(Pelicula pPelicula) {

		fecha=new Date();
		pelicula=pPelicula;
	}

	public JSONObject mostrarAlquiler(){
		JSONObject unJSON= new JSONObject();
		Calendar calendar= Calendar.getInstance();
		calendar.setTime(fecha);
		calendar.add(Calendar.DAY_OF_MONTH,15);
		Date fechaFin = calendar.getTime();
		unJSON.put("peliID",pelicula.getID());
		unJSON.put("titulo",pelicula.getNombre());
		unJSON.put("fechaInic", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(fecha));
		unJSON.put("fechaFin",  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(fechaFin));
		return unJSON;
	}

	public Pelicula getPelicula() {
		return pelicula;
	}
	
	
	
}
