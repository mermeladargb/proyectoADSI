package org.example;

public class GestorUsuario {

	private static GestorUsuario unGestorUsuario = new GestorUsuario();

	public static GestorUsuario getGestorUsuario() {
		return unGestorUsuario;
	}

	public Usuario getUsuario(String username) {
		return null;
	}

}
