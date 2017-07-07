package serviciosWeb;

import dominio.Libro;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import org.json.JSONArray;
import org.json.JSONObject;
import persistencia.GestorAutor;
import persistencia.GestorCategoria;
import persistencia.GestorLibro;

/**
 * REST Web Service
 *
 * @author alber
 */
@Path("libros")
public class LibrosRest {

	

	/**
	 * ********************************************************************
	 ******** LIBROS	******
	*********************************************************************
	 */
	
	
	@GET
	@Path("libro/{id}")
	@Produces(MediaType.TEXT_PLAIN)
	public String getLibro(@PathParam("id") int id) throws ClassNotFoundException, SQLException {
		return getLibro(GestorLibro.selectLibroByID(id)).toString();
	
	}
	
	/**
	 * Retrieves representation of an instance of servicioweb.ServicioWeb
	 *
	 * @return an instance of java.lang.String
	 */
	@GET
	@Path("")
	@Produces(MediaType.TEXT_PLAIN)
	public String getLibros() throws SQLException, ClassNotFoundException {
	
		return getLibros(GestorLibro.selectAll());
	}

	/**
	 * Obtiene un libro a partir de su isbn, si el libro esta ya registrado
	 * en el sistema lo obtiene de ahi; en caso de no ser asi su informacion
	 * es obtenida de la api de google.
	 *
	 * @param isbn es el isbn a partir del cual se va a obtener el libro
	 * puede ser de formato 10 numeros o de 13 numeros.
	 * @return un String en formato JSON con la informacion del libro.
	 * @throws java.lang.ClassNotFoundException al ocurrir algun problema
	 * con la base de datos.
	 * @throws java.sql.SQLException al ocurrir algun problema con la base
	 * de datos.
	 */
	@GET
	@Path("{isbn}")
	@Produces(MediaType.TEXT_PLAIN)
	public String getLibro(@PathParam("isbn") String isbn) throws ClassNotFoundException, SQLException {
		JSONArray autores2=new JSONArray();
		boolean internet = false;//almacena si la informacion es sacada de la api de google.	
		Libro libro = GestorLibro.selectLibroByISBN(isbn);
		if (libro == null) { //Obtiene la informacion de la api de Google
			Client client = ClientBuilder.newClient();
			WebTarget webTarget = client.target("https://www.googleapis.com/books/v1/volumes?q=isbn:" + isbn);
			String libroString = webTarget.request().get(String.class);
			int posicion = libroString.indexOf("[");
			libroString = libroString.substring(posicion, libroString.length());
			JSONArray libro2 = new JSONArray(libroString);
			String titulo = libro2.getJSONObject(0).getJSONObject("volumeInfo").getString("title");
			String urlFoto;
			try{
			 urlFoto = libro2.getJSONObject(0).getJSONObject("volumeInfo").getJSONObject("imageLinks").getString("smallThumbnail");
			}catch(org.json.JSONException e){
				urlFoto="imagenes/not_found.jpg";
			}
			String isbn10 = "";
			String isbn13 = "";

			JSONArray isbns = libro2.getJSONObject(0).getJSONObject("volumeInfo").getJSONArray("industryIdentifiers");
			for (int i = 0; i < isbns.length(); i++) { //Obtiene ISBNS del libro.
				try{
				if (isbns.getJSONObject(i).get("type").equals("ISBN_10")) {
					isbn10 = isbns.getJSONObject(i).getString("identifier");
				} else {
					if (isbns.getJSONObject(i).get("type").equals("ISBN_13")) {
						isbn13 = isbns.getJSONObject(i).getString("identifier");
					}
				}
				}catch(org.json.JSONException e){
					
				}
			}
			
			JSONArray autores=new JSONArray();
			try{
			autores = libro2.getJSONObject(0).getJSONObject("volumeInfo").getJSONArray("authors");
			}catch(org.json.JSONException e){
				
			}
			
			for(int i=0;i<autores.length();i++){
				autores2.put(autores.get(i));
			}
			
			
			
			libro = new Libro(1,isbn10, isbn13, titulo, urlFoto, new ArrayList<String>(), new ArrayList<String>());
			GestorLibro.selectID();
			internet = true;

		}
		JSONObject libroJson = new JSONObject();
		libroJson.put("isbn10", libro.getISBN10());
		libroJson.put("isbn13", libro.getISBN13());
		libroJson.put("titulo", libro.getTitulo());
		libroJson.put("urlfoto", libro.getUrlFoto());
		libroJson.put("autores", autores2);
		libroJson.put("internet", internet);

		return libroJson.toString();
	}

	@Path("libros/aleatorios")
	@Produces(MediaType.TEXT_PLAIN)
	public String getLibrosNovedades(@PathParam("isbn") String isbn) throws ClassNotFoundException, SQLException {
		return getLibros(GestorLibro.selectAll());
		
	}
	
	
	/**
	 * Realiza una busqueda en el sistema
	 *
	
	 * @return un String en formato array JSON con los libros que tienen esa
	 * cadena de numeros en su ISBNS
	 * @throws java.lang.ClassNotFoundException al ocurrir algun problema
	 * con la base de datos.
	 * @throws java.sql.SQLException al ocurrir algun problema con la base
	 * de datos.
	 */
	@GET
	@Path("busqueda/{filtros}")
	@Produces(MediaType.APPLICATION_JSON)
	public String getLibrosBusquedaISBN(  @PathParam("filtros") String filtros) throws ClassNotFoundException, SQLException {
		
		JSONObject filtros2=new JSONObject(filtros);
		
		
		ArrayList<Libro> libros;
		if(!filtros2.getBoolean("filtros")){
			return getLibros(GestorLibro.selectLibrosByTitulo(filtros2.getString("cadena")));
		}
		else{
			JSONArray categorias=filtros2.getJSONArray("categorias");
			ArrayList<String> categorias2=new ArrayList<String>();
			for(int i=0;i<categorias.length();i++){
				categorias2.add(categorias.get(i).toString());
				
			}
			return getLibros(libros=GestorLibro.selectLibrosBusqueda(filtros2.getString("cadena"), filtros2.getString("tipoBusqueda"), categorias2));
			
		}	
		
	}


	/**
	 * Convierte un arrayList de Libro en uno de JSON
	 *
	 * @param libros el arrayList de libros que se va a convertir.
	 * @return en formato String el array de JSON.
	 */
	private String getLibros(ArrayList<Libro> libros) {
		JSONArray libros2=new JSONArray();
		for (Libro libro :libros){
			
			libros2.put(getLibro(libro));
			
		}
		return libros2.toString();
	}
	
	
	private JSONObject getLibro(Libro libro){
		JSONObject nuevo = new JSONObject();
			nuevo.put("id", libro.getId());
			nuevo.put("isbn10", libro.getISBN10());
			nuevo.put("isbn13", libro.getISBN13());
			nuevo.put("titulo", libro.getTitulo());
			nuevo.put("urlfoto", libro.getUrlFoto());
			JSONArray categorias=new JSONArray();
			for(String categoria:libro.getCategorias()){
				categorias.put(categoria);
			}
			JSONArray autores=new JSONArray();
			for(String autor:libro.getAutores()){
				autores.put(autor);
			}
			nuevo.put("autores", autores);
			nuevo.put("categorias", categorias);
			return nuevo;
	}

//	@DELETE
//	@Path("{isbn}")
//	@Produces
//	public void deleteLibro(@PathParam("isbn")String isbn){
//		try {
//			GestorLibro.deleteByISBN(isbn);
//		} catch (ClassNotFoundException | SQLException ex) {
//			Logger.getLogger(ServicioWeb.class.getName()).log(Level.SEVERE, null, ex);
//		}
//	}
	
	
	
	
	
	
	
	/**
	 * ********************************************************************
	 ********                 Categorias				******
	*********************************************************************
	 */
	@GET
	@Path("categorias")
	@Produces(MediaType.TEXT_PLAIN)
	public String getAllCategorias( ) throws ClassNotFoundException, SQLException{
		JSONArray categorias=new JSONArray();
		ArrayList<String> categorias2=GestorCategoria.selectAll();
		for(String categoria:categorias2){
			categorias.put(categoria);
		}
		return categorias.toString();
	}
	
	
	@POST
	@Path("categorias/{nombre}")
	@Consumes(MediaType.TEXT_PLAIN)
	public void nuevaCategorias(@PathParam("nombre") String nombre ) throws ClassNotFoundException, SQLException{
		GestorCategoria.createCategoria(nombre);
	}
	
	@PUT
	@Path("categorias/{nombreAntiguo}/{nombreNuevo}")
	@Consumes(MediaType.TEXT_PLAIN)
	public void eliminarCategorias(@PathParam("nombreAntiguo") String nombreAntiguo, @PathParam("nombreNuevo") String nombreNuevo  ) throws ClassNotFoundException, SQLException{
		GestorCategoria.updateCategoria(nombreAntiguo,nombreNuevo);
	}
	
	
	
	@DELETE
	@Path("categorias/{nombre}")
	@Consumes(MediaType.TEXT_PLAIN)
	public void eliminarCategorias(@PathParam("nombre") String nombre ) throws ClassNotFoundException, SQLException{
		GestorCategoria.deleteCategoria(nombre);
	}
	
	/**
	 * ********************************************************************
	 ********                 Autores				******
	*********************************************************************
	 */
	@GET
	@Path("autores")
	@Produces(MediaType.TEXT_PLAIN)
	public String getAllAutores( ) throws ClassNotFoundException, SQLException{
		JSONArray autores=new JSONArray();
		ArrayList<String> autores2=GestorAutor.selectAll();
		for(String autor:autores2){
			autores.put(autor);
		}
		return autores.toString();
	}
}
