package org.example;

public class SolicitudPelicula {
    
    private String titulo;
    private Usuario user;

    public SolicitudPelicula(String pTitulo, Usuario pUser)
    {
        titulo=pTitulo;
        user=pUser;
    }

    public String getTitulo(){
        return (titulo);
    }

    public Usuario solicitadaPor(){
        return (user);
    }
}