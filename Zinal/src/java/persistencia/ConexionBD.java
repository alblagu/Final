/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package persistencia;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Se encarga de realizar la conexion a la base de datos. Y obtener los datos de
 * ella.
 *
 * @author Alberto Laguna Humayor .
 * ConexionBD es un Singleton
 */
public class ConexionBD {

    // Este es la variable "singleton"
    private static ConexionBD laConexion;

    private final String urlBD = "jdbc:postgresql://ec2-54-228-255-234.eu-west-1.compute.amazonaws.com:5432/dfkg1htvv6410n?sslmode=require";
    private final String userName = "voqicvtrfriddk";
    private final String password = "1289ecd151eb6edc1bd2bd072ce43625e4fd6028131d6d7e6cc9cbe8b9805ee8";

    private final String driverName = "org.postgresql.Driver";

    private final Connection conexion;

    // Garantiza que el cliente no cree objetos
    private ConexionBD() throws SQLException, ClassNotFoundException {
        Class.forName(driverName);
        conexion = DriverManager.getConnection(urlBD, userName, password);
    }

    /**
     * @return la instancia de la ConexionBD
     * @throws ClassNotFoundException cuando no se pueda realizar la conexion.
     */
    public static ConexionBD getInstancia() throws ClassNotFoundException, SQLException {
        if (laConexion == null) {
          //  try {
                laConexion = new ConexionBD();
//            } catch (SQLException a) {
//                throw new IllegalArgumentException(a.getMessage());
//            }
        }
        return laConexion;
    }

    /**
     * @param laQuery a partir de la cual se realiza la consulta.
     * @return el resultado de la consulta
     * @throws java.sql.SQLException
     * @throws IllegalArgumentException cuando no encuentre el dato que hay que
     * actualizar
     */
    public ResultSet select(String laQuery) throws SQLException {
        PreparedStatement pst = conexion.prepareStatement(laQuery);
        return pst.executeQuery();
    }

    public void update(String laQuery) throws SQLException {
        try {
            PreparedStatement pst = conexion.prepareStatement(laQuery);
            pst.execute();
        } catch (SQLException a) {
            throw new IllegalArgumentException(a.getMessage());
        }
    }
}