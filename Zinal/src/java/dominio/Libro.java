/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dominio;

import java.util.ArrayList;


/**
 *
 * @author alberto
 */
public class Libro {
	private final int id;
	private final String isbn10;
	private final String isbn13;
	private final String titulo;
	private final String urlFoto;
	private final ArrayList <String> autores;
	private final ArrayList <String> categorias;

	public Libro(int id, String isbn10,String isbn13, String titulo, String urlFoto, ArrayList<String> autores, ArrayList<String> categorias) {
		this.id=id;
		this.isbn10=isbn10;
		this.isbn13=isbn13;
		this.titulo=titulo;
		this.urlFoto=urlFoto;
		this.autores=autores;
		this.categorias=categorias;
	}

	public int getId() {
		return id;
	}
	
	public String getISBN10() {
		return isbn10;
	}
	
	public String getISBN13() {
		return isbn13;
	}
	
	public String getTitulo(){
		return titulo;
	}

	public String getUrlFoto() {
		return urlFoto;
	}

	public ArrayList<String> getAutores() {
		return autores;
	}

	public ArrayList<String> getCategorias() {
		return categorias;
	}

}