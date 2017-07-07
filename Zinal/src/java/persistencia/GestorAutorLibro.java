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
public class GestorAutorLibro {
	
	public static ArrayList<String> selectAutoresLibro(int id) throws ClassNotFoundException, SQLException{
		String laQuery="select * from AUTOR_LIBRO where LIBRO="+id;
		ResultSet rs=ConexionBD.getInstancia().select(laQuery);
		ArrayList<String> autores=new ArrayList<String>();
		while(rs.next()){
			autores.add(rs.getString("autor"));
		}
		return autores;
	}
	
	public static void create(int id, ArrayList<String> autores) throws ClassNotFoundException, SQLException{
	for(int i=0;i<autores.size();i++){
		String laQuery="insert into AUTOR_LIBRO (libro,autor) values("+id+",'"+autores.get(i)+"')";
		ConexionBD.getInstancia().update(laQuery);
	}
	}
}
