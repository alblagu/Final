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
}
