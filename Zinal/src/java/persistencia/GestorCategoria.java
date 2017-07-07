/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistencia;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author alber
 */
public class GestorCategoria {
	
	public static ArrayList<String> selectAll() throws ClassNotFoundException, SQLException{
		String laQuery="select * from categoria";
		ResultSet rs=ConexionBD.getInstancia().select(laQuery);
		ArrayList<String> categorias=new ArrayList<String>();
		while(rs.next()){
			categorias.add(rs.getString("nombre"));
		}
		return categorias;
	}
	
	public static String selectCategoria(String nombre) throws ClassNotFoundException, SQLException{
		String laQuery="select * from categoria where nombre='"+nombre+"'";
		ResultSet rs=ConexionBD.getInstancia().select(laQuery);
		
		while(rs.next()){
			return rs.getString("nombre");
		}
		return null;
	}
	
	public static void createCategoria(String nombre) throws ClassNotFoundException, SQLException{
		String laQuery="insert into Categoria(nombre) Values('"+nombre+"')";
		ConexionBD.getInstancia().update(laQuery);
	}
	
	public static void deleteCategoria(String nombre) throws ClassNotFoundException, SQLException{
		GestorCategoriaLibro.deleteCategoria(nombre);
		String laQuery="delete from Categoria where nombre='"+nombre+"'";
		ConexionBD.getInstancia().update(laQuery);
	}
	
	public static void updateCategoria(String nombreAntiguo, String nombreNuevo) throws ClassNotFoundException, SQLException{
		createCategoria(nombreNuevo);
		GestorCategoriaLibro.updateCategoria(nombreAntiguo,nombreNuevo);
		deleteCategoria(nombreAntiguo);
	}
}
