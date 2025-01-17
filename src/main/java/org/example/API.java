package org.example;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import org.json.JSONObject;

public class API {

    public static JSONObject getDatosPelicula(String titulo){
        
        JSONObject output = new JSONObject();
        System.out.println("Se llega hasta getDatosPelicula en API.java (Linea 16)");
        try {
            String apiKey = "350aba2d"; //Clave para conexiones a la api OMDb
            String title = titulo;
            String urlString = "http://www.omdbapi.com/?apikey=" + apiKey + "&t=" + titulo.replace(" ","+");


            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;
            StringBuffer respuesta = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                respuesta.append(inputLine);
            }
            
            output = new JSONObject(respuesta.toString());
            
            in.close();

        } catch (Exception e) {
            System.out.println("Womp Womp");
            e.printStackTrace();
        }

        System.out.println(output);
        return(output);
    }
    
    public static ArrayList<JSONObject> main(){

        ArrayList<JSONObject> peliculasJson = new ArrayList<>();

        String[] candidatos = {
            "Cars", "Up", "Avatar", "Home Alone", "Sharknado", "Me Before You"
        };

        for (String candidato : candidatos){
            try {
                String apiKey = "350aba2d"; //Clave para conexiones a la api OMDb
                String titulo = candidato;
                String urlString = "http://www.omdbapi.com/?apikey=" + apiKey + "&t=" + titulo.replace(" ","+");
    
    
                URL url = new URL(urlString);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
    
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String inputLine;
                StringBuffer respuesta = new StringBuffer(); //PUEDE QUE SEA BUFFER O BUILDER, NO SE
                
                while ((inputLine = in.readLine()) != null) {
                    respuesta.append(inputLine);
                }
                
                JSONObject peliculaJson = new JSONObject(respuesta.toString());

                if (!GestorSolicitudesPeliculas.getmGestorSolicutudesPeliculas().esta(peliculaJson.getString("Title"))) {
                    peliculasJson.add(peliculaJson);
                }
                else  {System.out.println(peliculaJson.getString("Plot"));}
                
                in.close();
    
                //Prueba
                //System.out.println(respuesta.toString());

            } catch (Exception e) {
                System.out.println("Womp Womp");
                e.printStackTrace();
            }
        }
        
        
        return(peliculasJson);
    }

}