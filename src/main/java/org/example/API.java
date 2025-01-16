package org.example;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class API {
    
    public static void main(String[] args){
        try {
            String apiKey = "da65ada"; //Clave para conexiones a la api OMDb
            String titulo = "a";
            String urlString = "http://www.omdapi.com/?apikey=" + apiKey + "&t=" + titulo.replace("","%20");


            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;
            StringBuffer respuesta = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                respuesta.append(inputLine);
            }
            in.close();

            System.out.println(respuesta.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}