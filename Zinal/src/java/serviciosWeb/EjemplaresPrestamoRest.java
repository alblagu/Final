/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serviciosWeb;

import dominio.Ejemplar;
import dominio.Libro;
import dominio.Prestamo;
import dominio.Usuario;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import org.json.JSONArray;
import org.json.JSONObject;
import persistencia.GestorAutor;
import persistencia.GestorEjemplar;
import persistencia.GestorLibro;
import persistencia.GestorPrestamo;
import persistencia.GestorUsuario;

/**
 * REST Web Service
 *
 * @author alber
 */
@Path("ejemplares")
public class EjemplaresPrestamoRest {

	
	/**
	 * ********************************************************************
	 ******** EJEMPLARES ******
	*********************************************************************
	 */
	/**
	 * Obtiene los ejemplares que el sistema tiene asociados al id pasado.
	 *
	 * @param isbn es el isbn a partir del cual se va a obtener los
	 * ejemplares.
	 * @return un array JSON en formato String con los ejemplares.
	 * @throws java.lang.ClassNotFoundException al ocurrir algun problema
	 * con la base de datos.
	 * @throws java.sql.SQLException al ocurrir algun problema con la base
	 * de datos.
	 */
	@GET
	@Path("libro/{id}")
	@Produces(MediaType.TEXT_PLAIN)
	public String getEjemplaresByLibro(@PathParam("id") int id) throws ClassNotFoundException, SQLException {
		ArrayList<Ejemplar> ejemplares = GestorEjemplar.selectEjemplaresByID(id);
		JSONArray ejemplares2 = new JSONArray();

		for (Ejemplar ejemplar:ejemplares ) {
			JSONObject ejemplar2 = new JSONObject();
			ejemplar2.put("codigo", ejemplar.getCodigo());
			ejemplar2.put("isbn10", ejemplar.getLibro().getISBN10());
			ejemplar2.put("disponible", ejemplar.getDisponible());
			ejemplar2.put("localizacion", ejemplar.getLocalizacion());
			ejemplares2.put(ejemplar2);
		}
		return ejemplares2.toString();
	}

	@GET
	@Path("{codigo}")
	@Produces(MediaType.TEXT_PLAIN)
	public String getEjemplaresByCodigo(@PathParam("codigo") String codigo) throws ClassNotFoundException, SQLException {

		Ejemplar ejemplar = GestorEjemplar.selectEjemplarByCodigo(codigo);
		if (ejemplar == null) {
			throw new IllegalArgumentException("No se ha encontrado el ejemplar");
		}
		JSONObject ejemplar2 = new JSONObject();
		ejemplar2.put("codigo", ejemplar.getCodigo());
		ejemplar2.put("titulo", ejemplar.getLibro().getTitulo());
		ejemplar2.put("urlfoto", ejemplar.getLibro().getUrlFoto());
		ejemplar2.put("disponible", ejemplar.getDisponible());

		
		return ejemplar2.toString();
	}

	/**
	 * Añade un nuevo ejemplar al sistema; y en caso de que el libro al que
	 * corresponde no estuviera añadido; tambien lo añade.
	 *
	 * @param json es el libro en formato JSON al que corresponde el
	 * ejemplar.
	 * @param codigo es el codigo del ejemplar.
	 * @throws java.lang.ClassNotFoundException al ocurrir algun problema
	 * con la base de datos.
	 * @throws java.sql.SQLException al ocurrir algun problema con la base
	 * de datos.
	 */
	@POST
	@Path("{codigo}")
	@Consumes(MediaType.APPLICATION_JSON)
	public void addEjemplarToLibro(String json, @PathParam("codigo") String codigo) throws ClassNotFoundException, SQLException {
		if(GestorEjemplar.selectEjemplarByCodigo(codigo)!=null)throw new IllegalArgumentException("Ese codigo ya esta asignado a un libro");
		
		JSONObject libro = new JSONObject(json);
		if (libro.getBoolean("internet")) {
			
			JSONArray autores=libro.getJSONArray("autores");
			JSONArray categorias=libro.getJSONArray("categorias");
			ArrayList<String> autores2= new ArrayList<String>();
			ArrayList<String> categorias2= new ArrayList<String>();
			
			for(int i=0; i<autores.length();i++){
				autores2.add(autores.get(i).toString());
				if(GestorAutor.selectAutor(autores.get(i).toString())==null)GestorAutor.createAutor(autores.get(i).toString());
			}
			for(int i=0; i<categorias.length();i++){
				categorias2.add(categorias.get(i).toString());
			}
			
	
			Libro nuevo = new Libro(GestorLibro.selectID(), libro.getString("isbn10"), libro.getString("isbn13"), libro.getString("titulo"), libro.getString("urlfoto"),autores2,categorias2);
			GestorLibro.create(nuevo);
			GestorEjemplar.create(new Ejemplar(codigo, nuevo, true,libro.getString("localizacion")));
		} else {
			String isbn=libro.getString("isbn10");
			if("".equals(isbn))
				isbn=libro.getString("isbn13");	
			GestorEjemplar.create(new Ejemplar(codigo, GestorLibro.selectLibroByISBN(isbn), true,libro.getString("localizacion")));
			}
	

	}

	@POST
	@Path("")
	@Consumes(MediaType.APPLICATION_JSON)
	public void addEjemplarToLibro(String json) throws ClassNotFoundException, SQLException{
		JSONObject libro = new JSONObject(json);
		
		if(GestorEjemplar.selectEjemplarByCodigo(libro.getString("codigo"))!=null)throw new IllegalArgumentException("Ese codigo ya esta asignado a un libro");
		
		
		if(null==GestorLibro.selectLibroByISBNs(libro.getString("isbn10"), libro.getString("isbn13"))){  //Crear Libro
			if(!"".equals(libro.getString("isbn10"))&&null!=GestorLibro.selectLibroByISBN(libro.getString("isbn10")))throw new IllegalArgumentException("Ese isbn10 ya esta asociado a un libro");
				if(!"".equals(libro.getString("isbn13"))&&null!=GestorLibro.selectLibroByISBN(libro.getString("isbn13")))throw new IllegalArgumentException("El isbn13 ya esta asociado a un libro");
				
				JSONArray autores=libro.getJSONArray("autores");
			JSONArray categorias=libro.getJSONArray("categorias");
			ArrayList<String> autores2= new ArrayList<String>();
			ArrayList<String> categorias2= new ArrayList<String>();
			
			for(int i=0; i<autores.length();i++){
				autores2.add(autores.get(i).toString());
				if(GestorAutor.selectAutor(autores.get(i).toString())==null)GestorAutor.createAutor(autores.get(i).toString());
			}
			for(int i=0; i<categorias.length();i++){
				categorias2.add(categorias.get(i).toString());
			}
			
			
			
			Libro nuevo = new Libro(GestorLibro.selectID(), libro.getString("isbn10"), libro.getString("isbn13"), libro.getString("titulo"), libro.getString("urlfoto"),autores2,categorias2);
			GestorLibro.create(nuevo);
			GestorEjemplar.create(new Ejemplar(libro.getString("codigo"), nuevo, true,libro.getString("localizacion")));
				
				
		}
		else{
			String isbn=libro.getString("isbn10");
			if("".equals(isbn))
				isbn=libro.getString("isbn13");	
			GestorEjemplar.create(new Ejemplar(libro.getString("codigo"), GestorLibro.selectLibroByISBN(isbn), true,libro.getString("localizacion")));
		}
		
	}

//	@DELETE
//	@Path("ejemplares/{codigo}")
//	@Produces
//	public void deleteEjemplar(@PathParam("codigo")String codigo){
//		try {
//			GestorEjemplar.delete(codigo);
//		} catch (ClassNotFoundException | SQLException ex) {
//			Logger.getLogger(ServicioWeb.class.getName()).log(Level.SEVERE, null, ex);
//		}
//	}
	
	
	
	
	
	/**
	 * ********************************************************************
	 ******** PRESTAMOS	******
	*********************************************************************
	 */
	/**
	 *
	 * @param ejemplar
	 * @return
	 * @throws java.lang.ClassNotFoundException al ocurrir algun problema
	 * con la base de datos.
	 * @throws java.sql.SQLException al ocurrir algun problema con la base
	 * de datos.
	 */
	@GET
	@Path("prestamo/{codigo}/{dni}")
	@Produces(MediaType.TEXT_PLAIN)
	public String getPrestamosByEjemplar(@PathParam("codigo") String codigo, @PathParam("dni") String dni) throws ClassNotFoundException, SQLException {
		Prestamo prestamo=(GestorPrestamo.selectPrestamosByEjemplar(codigo));
		
		if(prestamo==null || !prestamo.getUsuario().getDNI().equals(dni)){
			throw new IllegalArgumentException("No hay un prestamo en activo para los datos introducidos");
		}
		else{
			JSONObject nuevo = new JSONObject();
			nuevo.put("titulo", prestamo.getEjemplar().getLibro().getTitulo());
			nuevo.put("urlfoto",prestamo.getEjemplar().getLibro().getUrlFoto());
			nuevo.put("fecha_inicio", prestamo.getFechaIni());
			nuevo.put("fecha_fin", prestamo.getFechaFin());
			return nuevo.toString();
		}
			
	}



	
	
	

	
	
	@POST
	@Path("prestamos/{dni}/{codigo}")
	@Consumes(MediaType.APPLICATION_JSON)
	public void addNuevoPrestamo(@PathParam("dni") String dni, @PathParam("codigo") String codigo,String fecha) throws ClassNotFoundException, SQLException {
		JSONObject fechaJson=new JSONObject(fecha);
		Date inicio=new Date();
		GregorianCalendar fin=new GregorianCalendar(fechaJson.getInt("anio"), fechaJson.getInt ("mes")-1, fechaJson.getInt("dia"));
		Date fin2=fin.getTime(); 
		Usuario usuario=GestorUsuario.selectUsuarioByDNI(dni);
		if("PROFESOR".equals(usuario.getTipoUsuario())){
			fin2=null;
		}
		GestorPrestamo.createNuevoPrestamo(new Prestamo(GestorPrestamo.selectID(),GestorEjemplar.selectEjemplarByCodigo(codigo),usuario,true,inicio,fin2));	
		
	}
	
	@POST
	@Path("prestamos/{codigo}")
	@Consumes(MediaType.APPLICATION_JSON)
	public void finalizarPrestamo( @PathParam("codigo") String codigo ) throws ClassNotFoundException, SQLException {
		GestorEjemplar.updateEjemplarCambiaDisponible(GestorEjemplar.selectEjemplarByCodigo(codigo));
		GestorPrestamo.updatePrestamoFin(codigo);
		
	}
	
	
	@GET
	@Path("prestamosUsu/{usuario}")
	@Produces(MediaType.TEXT_PLAIN)
	public String getPrestamosByUsuario(@PathParam("usuario") String usuario) throws ClassNotFoundException, SQLException {
		return prestamosToString(GestorPrestamo.selectPrestamosByUsuario(usuario));
	}
	@GET
	@Path("prestamosUsu")
	@Produces(MediaType.TEXT_PLAIN)
	public String getPrestamosByAdmin() throws ClassNotFoundException, SQLException {
		return prestamosToString(GestorPrestamo.selectPrestamosByAdmin());
	}
	
	
	private String prestamosToString(ArrayList<Prestamo> prestamos) {
		JSONArray prestamos2 = new JSONArray();

		for (int i = 0; i < prestamos.size(); i++) {
			JSONObject nuevo = new JSONObject();
			nuevo.put("libro", prestamos.get(i).getEjemplar().getLibro().getTitulo());
			nuevo.put("codigo",prestamos.get(i).getEjemplar().getCodigo());
			nuevo.put("fecha_fin", prestamos.get(i).getFechaFin());
			prestamos2.put(nuevo);
		}

		return prestamos2.toString();
	}
	
}
