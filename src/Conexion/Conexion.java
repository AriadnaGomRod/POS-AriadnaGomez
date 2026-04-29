package Conexion;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;



/**
 *
 * @author arigo
 */
public class Conexion {
    public Connection getConexion() {

        String connectionUrl =
                "jdbc:sqlserver://DAISY\\DAISYSERVIDOR1;"
                + "database=Papeleria;"
                + "user=sa;"
                + "password=123;"
                + "timeout=30;"
                + "encrypt=true;trustServerCertificate=true;";

        try {
            Connection con = DriverManager.getConnection(connectionUrl);
            return con;
        } catch (SQLException ex) {
    System.out.println("Error: " + ex.getMessage());
    return null;
}
    }
    public static void main(String[] args) {
        Conexion cn = new Conexion();
        Connection con = cn.getConexion();

        if (con != null) {
            System.out.println("Conectado correctamente");
        } else {
            System.out.println("Error al conectar");
        }
    }
}

