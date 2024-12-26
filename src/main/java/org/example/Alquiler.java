package org.example;

import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Alquiler {
	private String fecha;
	private Pelicula pelicula;
	
	
	

	public Alquiler(String pFecha,Pelicula pPelicula) {

		fecha= pFecha;
		pelicula=pPelicula;
	}

	public JSONObject mostrarAlquiler(){

		JSONObject unJSON= new JSONObject();
		SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try{
			Date fechaInicio = formatoFecha.parse(fecha);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(fechaInicio);
			calendar.add(Calendar.DAY_OF_MONTH, 15);
			Date fechaFin = calendar.getTime();
			unJSON.put("peliID",pelicula.getID());
			unJSON.put("titulo",pelicula.getTitulo());
			unJSON.put("fechaInic", formatoFecha.format(fechaInicio));
			unJSON.put("fechaFin",  formatoFecha.format(fechaFin));
		}catch (ParseException e)
		{
			unJSON=null;
		}


		return unJSON;
	}

	public Pelicula getPelicula() {
		return pelicula;
	}
	
	
	
}
