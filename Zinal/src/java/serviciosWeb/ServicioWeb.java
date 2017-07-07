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
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;
import org.json.JSONArray;
import org.json.JSONObject;
import persistencia.GestorCategoria;
import persistencia.GestorEjemplar;
import persistencia.GestorLibro;
import persistencia.GestorPrestamo;
import persistencia.GestorUsuario;
import sha.Hash;

/**
 * REST Web Service
 *
 * @author alber
 */
@Path("generic")
public class ServicioWeb {

	@Context
	private UriInfo context;

	/**
	 * Creates a new instance of ServicioWeb
	 */
	public ServicioWeb() {
	}

	/**
	 * Retrieves representation of an instance of servicioWeb.ServicioWeb
	 * @return an instance of java.lang.String
	 */
	@GET
        @Produces(MediaType.TEXT_PLAIN)
	public String getText() {
		//TODO return proper representation object
		return "jajdsj";
	}

	
	
	
}
