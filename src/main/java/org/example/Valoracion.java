package org.example;

import javax.sql.RowSet;

public class Valoracion{

    private float nota;
    private String reseña;
    private Usuario user;

    public Valoracion(float pNota, String pReseña, Usuario pUser)
    {
        nota=pNota;
        reseña=pReseña;
        user=pUser;
    }

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