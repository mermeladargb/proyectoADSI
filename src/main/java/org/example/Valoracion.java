package org.example;

import javax.sql.RowSet;

public class Valoracion{

    private float nota;
    private String reseña;
    private Usuario user;


    public float getPuntuacion(){
        return nota;
    }

    public String getUser() {
        return user.getUsername();
    }

    public String getReseña() {
        return reseña;
    }

}