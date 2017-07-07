/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serviciosWeb;

import dominio.Usuario;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import org.json.JSONArray;
import org.json.JSONObject;
import persistencia.GestorPrestamo;
import persistencia.GestorUsuario;
import sha.Hash;

/**
 * REST Web Service
 *
 * @author alber
 */
@Path("usuarios")
public class UsuariosRest {

/**
	 * ********************************************************************
	 ******** 			USUARIOS			******
	*********************************************************************
	 */
	@GET
	@Path("usuario/{dni}/{password}/{inicio}")
	@Produces(MediaType.TEXT_PLAIN)
	public String getUsuarioByDNI(@PathParam("dni") String dni,@PathParam("password") String password,@PathParam("inicio") boolean inicio) throws ClassNotFoundException, SQLException {
		Usuario usuario=GestorUsuario.selectUsuarioByDNI(dni);
		if(usuario==null)throw new IllegalArgumentException("No se ha encontrado dni del usuario");
		
		if(inicio){
			if(!usuario.getPassword().equals(Hash.sha1(password)))throw new IllegalArgumentException("Contraseña incorrecta");
		}
		else{
			if(!usuario.getPassword().equals(password))throw new IllegalArgumentException("Contraseña incorrecta");	
		}
		
		return getUsuarioString(usuario);
	}
	/**
	 *Crea un nuevo usuario
	 * @param json es el usuario que se va a crear en formato json.
	 * @throws java.lang.ClassNotFoundException al ocurrir algun problema
	 * con la base de datos.
	 * @throws java.sql.SQLException al ocurrir algun problema con la base
	 * de datos.
	 */
	@POST
	@Path("")
	@Consumes(MediaType.APPLICATION_JSON)
	public void addNuevoUsuario(String json) throws ClassNotFoundException, SQLException {
		JSONObject usuario = new JSONObject(json);
		if (GestorUsuario.selectUsuarioByDNI(usuario.getString("dni")) != null) {
			throw new IllegalArgumentException("Ya hay un usuario con ese dni");
		}
		String tipoUsuario =Usuario.ALUMNO;
		if(usuario.getBoolean("tipoUsuario")){
			tipoUsuario= Usuario.PROFESOR;
				}
		GestorUsuario.createUsuario(new Usuario(usuario.getString("dni"), Hash.sha1(usuario.getString("password")), usuario.getString("nombre")+" "+ usuario.getString("apellidos"),tipoUsuario, usuario.getString("telefono"),usuario.getString("email")));
	}

	
	
	
	
	
	
	@GET
	@Path("{filtros}")
	@Produces(MediaType.TEXT_PLAIN)
	public String getAllUsuarios(@PathParam("filtros") String filtros ) throws ClassNotFoundException, SQLException{
		JSONObject usu = new JSONObject(filtros);
		ArrayList<Usuario> usuarios=GestorUsuario.selectAllUsuariosByFiltros(usu.getString("dni"),usu.getString("nombre"));

		JSONArray usuarios2 =new JSONArray();
		for(int i = 0 ; i<usuarios.size();i++){
			JSONObject nuevo = new JSONObject();
			nuevo.put("dni", usuarios.get(i).getDNI());
			nuevo.put("password", usuarios.get(i).getPassword());
			nuevo.put("nombre", usuarios.get(i).getNombre());
			nuevo.put("tipoUsuario", usuarios.get(i).getTipoUsuario());
			nuevo.put("telefono", usuarios.get(i).getTelefono());
			usuarios2.put(nuevo);
		}
		return usuarios2.toString();
	}

	@DELETE
	@Path("{dni}")
	@Consumes(MediaType.TEXT_PLAIN)
	public void deleteUsuarioByDNI(@PathParam("dni") String dni) throws ClassNotFoundException, SQLException {
		GestorUsuario.deleteUsuarioByDNI(dni);
	}
	
	@GET
	@Path("usuario/{dni}")
	@Produces(MediaType.TEXT_PLAIN)
	public String getUsuarioDNI(@PathParam("dni") String dni ) throws ClassNotFoundException, SQLException{
		Usuario usu=GestorUsuario.selectUsuarioByDNI(dni);
		if(usu==null)throw new IllegalArgumentException("No se encontro ningun usuario para el dni pasado");
		
			
		
		return getUsuarioString(usu);
	}
	
	
	@GET
	@Path("ejemplar/{codigo}")
	@Produces(MediaType.TEXT_PLAIN)
	public String getUsuarioEjemplar(@PathParam("codigo") String codigo ) throws ClassNotFoundException, SQLException{
	
		String dni=GestorPrestamo.selectDNIByEjemplar(codigo);
		return getUsuarioString(GestorUsuario.selectUsuarioByDNI(dni));
		
	}
	
	private String getUsuarioString(Usuario usu){
		JSONObject usuario = new JSONObject();
			usuario.put("dni", usu.getDNI());
			usuario.put("password", usu.getPassword());
			usuario.put("nombre", usu.getNombre());
			usuario.put("tipoUsuario", usu.getTipoUsuario());
			usuario.put("telefono", usu.getTelefono());
			usuario.put("email", usu.getEmail());
			return usuario.toString();
	}
	
	
}
