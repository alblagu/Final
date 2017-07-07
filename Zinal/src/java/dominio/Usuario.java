/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dominio;

/**
 *
 * @author alberto
 */
public class Usuario {
	
	public static final String ADMIN="ADMIN";
	public static final String PROFESOR="PROFESOR";
	public static final String ALUMNO="ALUMNO";
	
	private final String dni;
	private final String password;
	private final String nombre;
	private final String tipoUsuario;
	private final String telefono;
	private final String email;

	public Usuario(String dni, String password, String nombre, String tipoUsuario, String telefono, String email) {
		this.dni = dni;
		this.password = password;
		this.nombre = nombre;
		this.tipoUsuario=tipoUsuario;
		this.telefono = telefono;
		this.email=email;
	}
	
	public String getDNI() {
		return dni;
	}

	public String getPassword() {
		return password;
	}

	public String getNombre() {
		return nombre;
	}

	public String getTipoUsuario(){
		return tipoUsuario;
	}

	public String getTelefono() {
		return telefono;
	}	
	public String getEmail(){
		return email;
	}
}