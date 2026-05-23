package Conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
/**
 *
 * @author arigo
 */
public class Conexion {

    // Este método sirve para conectar el sistema con la base de datos
    public Connection getConexion() {

        // Datos necesarios para realizar la conexión
        String connectionUrl =
                "jdbc:sqlserver://NUWO;"
                + "database=Papeleria;"
                + "user=sa;"
                + "password=1234;"
                + "timeout=30;"
                + "encrypt=true;trustServerCertificate=true;";

        try {

            // Se intenta realizar la conexión
            Connection con = DriverManager.getConnection(connectionUrl);

            // Si todo sale bien, retorna la conexión
            return con;

        } catch (SQLException ex) {

            // Si ocurre un error, se muestra en pantalla
            System.out.println("Error: " + ex.getMessage());

            return null;
        }
    }

    public static void main(String[] args) {

        // Se crea un objeto de la clase Conexion
        Conexion cn = new Conexion();

        // Se llama el método para conectar
        Connection con = cn.getConexion();

        // Verifica si la conexión fue correcta
        if (con != null) {
            System.out.println("Conectado correctamente");
        } else {
            System.out.println("Error al conectar");
        }
    }
}