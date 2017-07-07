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
public class GestorAutor {
	
	public static ArrayList<String> selectAll() throws ClassNotFoundException, SQLException{
		String laQuery="select * from autor";
		ResultSet rs=ConexionBD.getInstancia().select(laQuery);
		ArrayList<String> autores=new ArrayList<String>();
		while(rs.next()){
			autores.add(rs.getString("nombre"));
		}
		return autores;
	}
	
	
	
	public static String selectAutor(String nombre) throws ClassNotFoundException, SQLException{
		String laQuery="select * from autor where nombre='"+nombre+"'";
		ResultSet rs=ConexionBD.getInstancia().select(laQuery);
		
		while(rs.next()){
			return rs.getString("nombre");
		}
		return null;
	}
	
	public static void createAutor(String nombre) throws ClassNotFoundException, SQLException{
		String laQuery="insert into autor(nombre) Values('"+nombre+"')";
		ConexionBD.getInstancia().update(laQuery);
	}
}
