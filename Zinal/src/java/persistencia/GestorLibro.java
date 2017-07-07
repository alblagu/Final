/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistencia;

import dominio.Libro;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author alberto
 */
public class GestorLibro {
	
	
	/**
	 * Obtiene un libro a partir de su id
	 * @param id es el id a partir del cual encuentras el libro.
	 * @return el libro encontrado o null si no lo encuentra.
	 * @throws java.lang.ClassNotFoundException al ocurrir algun problema con la base de datos.
	 * @throws java.sql.SQLException al ocurrir algun problema con la base de datos.
	 */
	public static Libro selectLibroByID(int id) throws ClassNotFoundException, SQLException {
        	String laQuery = ("select * from LIBRO where id="+id);
       		ResultSet rs = ConexionBD.getInstancia().select(laQuery);
		while(rs.next())
			return new Libro(rs.getInt("id"),rs.getString("isbn10"), rs.getString("isbn13"), rs.getString("titulo"),rs.getString("urlfoto"),GestorAutorLibro.selectAutoresLibro(rs.getInt("id")),GestorCategoriaLibro.selectCategoriasLibro(rs.getInt("id")));
		return null;
	}
	
	/**
	 *  
	 * @return
	 * @throws java.lang.ClassNotFoundException al ocurrir algun problema con la base de datos.
	 * @throws java.sql.SQLException al ocurrir algun problema con la base de datos.
	 */
	public static ArrayList<Libro> selectAll() throws SQLException, ClassNotFoundException {
        	ArrayList<Libro> libros= new ArrayList<>();
        	String laQuery = ("select * from LIBRO");
        	ResultSet rs = ConexionBD.getInstancia().select(laQuery);
        	while (rs.next()) {
			libros.add(new Libro(rs.getInt("id"),rs.getString("isbn10"),rs.getString("isbn13"),rs.getString("titulo"),rs.getString("urlfoto"),GestorAutorLibro.selectAutoresLibro(rs.getInt("id")),GestorCategoriaLibro.selectCategoriasLibro(rs.getInt("id"))));
        	}
        	return libros;
    	}
	/**
	 * Obtiene un libro a partir de su isbn. 
	 * @param isbn es el isbn a partir del cual encuentras el libro.
	 * @return el libro encontrado o null si no lo encuentra.
	 * @throws java.lang.ClassNotFoundException al ocurrir algun problema con la base de datos.
	 * @throws java.sql.SQLException al ocurrir algun problema con la base de datos.
	 */
	public static Libro selectLibroByISBN(String isbn) throws ClassNotFoundException, SQLException {
        	String laQuery = ("select * from LIBRO where isbn10='"+isbn+"' OR isbn13='"+isbn+"'");
       		ResultSet rs = ConexionBD.getInstancia().select(laQuery);
		while(rs.next())
			return new Libro(rs.getInt("id"),rs.getString("isbn10"), rs.getString("isbn13"), rs.getString("titulo"),rs.getString("urlfoto"),GestorAutorLibro.selectAutoresLibro(rs.getInt("id")),GestorCategoriaLibro.selectCategoriasLibro(rs.getInt("id")));
		return null;
	}
	
	
	/**
	 * Obtiene un libro a partir de sus dos isbn. 
	 * @param isbn10 es el isbn10 del libro.
	 * @param isbn10 es el isbn13 del libro.
	 * @return el libro encontrado o null si no lo encuentra.
	 * @throws java.lang.ClassNotFoundException al ocurrir algun problema con la base de datos.
	 * @throws java.sql.SQLException al ocurrir algun problema con la base de datos.
	 */
	public static Libro selectLibroByISBNs(String isbn10, String isbn13) throws ClassNotFoundException, SQLException {
        	String laQuery = ("select * from LIBRO where isbn10='"+isbn10+"' AND isbn13='"+isbn13+"'");
       		ResultSet rs = ConexionBD.getInstancia().select(laQuery);
		while(rs.next())
			return new Libro(rs.getInt("id"),rs.getString("isbn10"), rs.getString("isbn13"), rs.getString("titulo"),rs.getString("urlfoto"),GestorAutorLibro.selectAutoresLibro(rs.getInt("id")),GestorCategoriaLibro.selectCategoriasLibro(rs.getInt("id")));
		return null;
	}
	
	
	
	
	/**
	 * Añade un nuevo libro al sistema.
	 * @param libro es el libro que va a ser añadido.
	 * @throws java.lang.ClassNotFoundException al ocurrir algun problema con la base de datos.
	 * @throws java.sql.SQLException al ocurrir algun problema con la base de datos.
	 */
	public static void create(Libro libro) throws ClassNotFoundException, SQLException {
		String laQuery=("insert into LIBRO(id,ISBN10,ISBN13, TITULO,URLFOTO) values("+libro.getId()+",'"+libro.getISBN10()+"','" + libro.getISBN13()+ "','" + libro.getTitulo()+ "','"+libro.getUrlFoto()+"')");
		ConexionBD.getInstancia().update(laQuery);
		GestorAutorLibro.create(libro.getId(), libro.getAutores());
		GestorCategoriaLibro.create(libro.getId(), libro.getCategorias());
	}

	public static void deleteByISBN(String isbn) throws ClassNotFoundException, SQLException {
        	String laQuery = ("delete * from LIBRO where isbn10='"+isbn+"' OR isbn13='"+isbn+"'");
		ConexionBD.getInstancia().update(laQuery);
	}

	/**
	 * Realiza una busqueda en el sistema de los libros que tienen la cadena
	 * pasada en su isbn ya sea en el de formato 10 numeros o el de 13.
	 * @param isbn es la cadena de numeros a partir de la cual se 
	 * va a realizar la busqueda.
	 * @return un String en formato array JSON con los libros que tienen esa
	 * cadena de numeros en su ISBNS
	 * @throws java.lang.ClassNotFoundException al ocurrir algun problema con la base de datos.
	 * @throws java.sql.SQLException al ocurrir algun problema con la base de datos.
	 */
	public static ArrayList<Libro> selectLibrosByISNB(String isbn) throws ClassNotFoundException, SQLException{
        	ArrayList<Libro> libros= new ArrayList<>();
        	String laQuery = ("select * from LIBRO where isbn10 LIKE '%"+isbn+"%' OR isbn13 LIKE '%"+isbn+"%'");
        	ResultSet rs = ConexionBD.getInstancia().select(laQuery);
        	while (rs.next()) {
			libros.add(new Libro(rs.getInt("id"),rs.getString("isbn10"),rs.getString("isbn13"),rs.getString("titulo"),rs.getString("urlfoto"),GestorAutorLibro.selectAutoresLibro(rs.getInt("id")),GestorCategoriaLibro.selectCategoriasLibro(rs.getInt("id"))));
        	}
        	return libros;
		
	}

	/**
	 * Realiza una busqueda en el sistema de los libros que tienen la cadena
	 * pasada en su titulo
	 * @param titulo es la cadena partir de la cual se 
	 * va a realizar la busqueda.
	 * @return un String en formato array JSON con los libros que tienen esa
	 * cadena en su titulo.
	 * @throws java.lang.ClassNotFoundException al ocurrir algun problema con la base de datos.
	 * @throws java.sql.SQLException al ocurrir algun problema con la base de datos.
	 */
	public static ArrayList<Libro> selectLibrosByTitulo(String titulo) throws ClassNotFoundException, SQLException{
        	String laQuery = ("select * from LIBRO where titulo ILIKE '%"+titulo+"%'");
        	ResultSet rs = ConexionBD.getInstancia().select(laQuery);		
        	ArrayList<Libro> libros= new ArrayList<Libro>();
        	while (rs.next()) {
			libros.add(new Libro(rs.getInt("id"),rs.getString("isbn10"),rs.getString("isbn13"),rs.getString("titulo"),rs.getString("urlfoto"),GestorAutorLibro.selectAutoresLibro(rs.getInt("id")),GestorCategoriaLibro.selectCategoriasLibro(rs.getInt("id"))));
        	}
        	return libros;
		
	}
	
	
	public static ArrayList<Libro> selectLibrosBusqueda(String cadena,String tipoBusqueda, ArrayList<String> categorias)throws ClassNotFoundException, SQLException{
		String laQuery="select * from LIBRO l, CATEGORIA_LIBRO c ";
		if("titulo".equals(tipoBusqueda)){
			laQuery+= "where l.titulo ilike '%"+cadena+"%' ";
		}
		else if("isbn".equals(tipoBusqueda)){
			laQuery+= "where (l.isbn10 ilike '%"+cadena+"%' OR l.isbn13 '%"+cadena+"%') ";
		}
		else{
			laQuery+= ",AUTOR_LIBRO a where l.id=a.libro AND a.autor ilike '%"+cadena+"%'";
		}
		if (!categorias.isEmpty()){
		laQuery+="AND (l.id=c.categoria AND (";
		for(int i=0;i<categorias.size();i++){
			laQuery+= "c.categoria='"+categorias.get(i)+"' ";
			if(!(categorias.size()-1==i)){
				laQuery+=" OR ";
			}
		}
		laQuery+=" ))";
		}
		System.out.println(laQuery);
		
		ResultSet rs=ConexionBD.getInstancia().select(laQuery);
		
		ArrayList<Libro> libros= new ArrayList<Libro>();
        	while (rs.next()) {
			libros.add(new Libro(rs.getInt("id"),rs.getString("isbn10"),rs.getString("isbn13"),rs.getString("titulo"),rs.getString("urlfoto"),GestorAutorLibro.selectAutoresLibro(rs.getInt("id")),GestorCategoriaLibro.selectCategoriasLibro(rs.getInt("id"))));
        	}
		System.out.println(libros.size());
        	return libros;	
	}
	
	

	
	
	public static int selectID() throws ClassNotFoundException, SQLException{
		String laQuery = ("select max(id)  from LIBRO");
		ResultSet rs =ConexionBD.getInstancia().select(laQuery);
		
		rs.next();
		return rs.getInt("max")+1;
		
	}
}
