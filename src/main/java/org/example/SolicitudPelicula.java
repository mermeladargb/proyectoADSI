package org.example;

public class SolicitudPelicula {
    
    private String titulo;
    private Usuario user;

    public SolicitudPelicula(String pTitulo, Usuario pUser)
    {
        titulo=pTitulo;
        user=pUser;
    }

    public boolean esPelicula(String pTitulo){
        return (titulo==pTitulo);
    }

    public Usuario solicitadaPor(){
        return (user);
    }
}
