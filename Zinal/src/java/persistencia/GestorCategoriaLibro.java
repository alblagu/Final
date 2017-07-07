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
public class GestorCategoriaLibro {
	
	public static ArrayList<String> selectCategoriasLibro(int id) throws ClassNotFoundException, SQLException{
		String laQuery="select * from CATEGORIA_LIBRO where LIBRO="+id;
		ResultSet rs=ConexionBD.getInstancia().select(laQuery);
		ArrayList<String> categorias=new ArrayList<String>();
		while(rs.next()){
			categorias.add(rs.getString("categoria"));
		}
		return categorias;
	}

	public static void deleteCategoria(String nombre) throws ClassNotFoundException, SQLException{
		String laQuery="delete from Categoria_Libro where categoria='"+nombre+"'";
		ConexionBD.getInstancia().update(laQuery);
	}

	public static void updateCategoria(String nombreAntiguo, String nombreNuevo) throws ClassNotFoundException, SQLException{
	String laQuery="update Categoria_Libro set categoria='"+nombreNuevo+"' where categoria='"+nombreAntiguo+"'";
	ConexionBD.getInstancia().update(laQuery);
}
	
	public static void create(int id, ArrayList<String> categorias) throws ClassNotFoundException, SQLException{
		for(int i=0;i<categorias.size();i++){
		String laQuery="insert into CATEGORIA_LIBRO (libro,categoria) values("+id+",'"+categorias.get(i)+"')";
	ConexionBD.getInstancia().update(laQuery);
		}
	}
}
