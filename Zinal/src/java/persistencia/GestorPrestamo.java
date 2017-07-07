
package persistencia;

import dominio.Prestamo;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 *
 * @author alberto
 */
public class GestorPrestamo {

	public static Prestamo selectPrestamosByEjemplar(String codigo) throws ClassNotFoundException, SQLException {
		String laQuery = ("select * from PRESTAMO where ejemplar='"+codigo+"' AND enCurso='true'");
		ResultSet rs =ConexionBD.getInstancia().select(laQuery);
		while(rs.next()){
			return new Prestamo(rs.getInt("id"),GestorEjemplar.selectEjemplarByCodigo(rs.getString("ejemplar")),GestorUsuario.selectUsuarioByDNI(rs.getString("usuario")),rs.getBoolean("enCurso"),rs.getDate("fecha_ini"),rs.getDate("fecha_fin"));
	}
		return null;	
        	
	}

	
	public static void createNuevoPrestamo(Prestamo prestamo) throws ClassNotFoundException, SQLException {
		String laQuery="";
		if(prestamo.getFechaFin()!=null){
	laQuery=("insert into PRESTAMO(ID,USUARIO,EJEMPLAR,ENCURSO,FECHA_INI,FECHA_FIN) values("+ prestamo.getId()+",'"+prestamo.getUsuario().getDNI()+"','"+prestamo.getEjemplar().getCodigo()+"','"+prestamo.getEnCurso()+"','"+prestamo.getFechaIni()+"','"+prestamo.getFechaFin()+"')");
		}
		else{
			 laQuery=("insert into PRESTAMO(ID,USUARIO,EJEMPLAR,ENCURSO,FECHA_INI) values("+ prestamo.getId()+",'"+prestamo.getUsuario().getDNI()+"','"+prestamo.getEjemplar().getCodigo()+"','"+prestamo.getEnCurso()+"','"+prestamo.getFechaIni()+"')");	
		}
	
	

	ConexionBD.getInstancia().update(laQuery);
	GestorEjemplar.updateEjemplarCambiaDisponible(prestamo.getEjemplar());
	}
	
	
	public static void updatePrestamoFin(String codigo) throws ClassNotFoundException, SQLException{
		String laQuery="update PRESTAMO set encurso='false' where ejemplar='"+codigo+"'";
		ConexionBD.getInstancia().update(laQuery);
	}

	public static int selectID() throws ClassNotFoundException, SQLException{
		String laQuery = ("select max(id) from PRESTAMO ");
		ResultSet rs =ConexionBD.getInstancia().select(laQuery);
		
		rs.next();
		return rs.getInt("max")+1;
	}


	
	
	
	
	
	
	public static ArrayList<Prestamo> selectPrestamosByUsuario(String usuario) throws ClassNotFoundException, SQLException {
		String laQuery = ("select * from PRESTAMO where usuario='"+usuario+"' AND enCurso=true");
		ResultSet rs =ConexionBD.getInstancia().select(laQuery);
		ArrayList<Prestamo> prestamos=new ArrayList<>(); 
		
		while(rs.next()){
			prestamos.add(new Prestamo(rs.getInt("id"),GestorEjemplar.selectEjemplarByCodigo(rs.getString("ejemplar")),GestorUsuario.selectUsuarioByDNI(rs.getString("usuario")),rs.getBoolean("enCurso"),rs.getDate("fecha_ini"),rs.getDate("fecha_fin")));
	}
		return prestamos;	
	}
	
		public static ArrayList<Prestamo> selectPrestamosByAdmin() throws ClassNotFoundException, SQLException {
		GregorianCalendar fin=new GregorianCalendar();
		Date fin2=fin.getTime(); 	
			
			
			
		String laQuery = ("select * from PRESTAMO where enCurso=true and fecha_fin > '"+fin2+"'");
		ResultSet rs =ConexionBD.getInstancia().select(laQuery);
		ArrayList<Prestamo> prestamos=new ArrayList<>(); 
		
		while(rs.next()){
			prestamos.add(new Prestamo(rs.getInt("id"),GestorEjemplar.selectEjemplarByCodigo(rs.getString("ejemplar")),GestorUsuario.selectUsuarioByDNI(rs.getString("usuario")),rs.getBoolean("enCurso"),rs.getDate("fecha_ini"),rs.getDate("fecha_fin")));
	}
		return prestamos;	
	}

		
		
		
	public static String selectDNIByEjemplar(String codigo) throws ClassNotFoundException, SQLException{
		String laQuery="select usuario as dni from prestamo where ejemplar='"+codigo+"' and enCurso='true'";
		System.out.println(laQuery);
		ResultSet rs =ConexionBD.getInstancia().select(laQuery);
		rs.next();
		return rs.getString("dni");
	}
}