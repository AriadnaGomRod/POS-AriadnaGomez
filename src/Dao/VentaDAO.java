package Dao;
import Conexion.Conexion;
import Modelo.Producto;
import Modelo.Venta;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author arigo
 */
/**
 * Clase encargada de las consultas de ventas en la base de datos.
 */
public class VentaDAO {
    // Variables para conexión y consultas SQL
    Conexion conectar = new Conexion();
    Connection con;
    PreparedStatement ps;
    ResultSet rs;

 // Obtiene la suma total de ventas del día actual
    public double totalVentasDia() {
        double total = 0.0;
        String sql = "SELECT SUM(Total) AS VentasHoy FROM Venta WHERE Fecha = CAST(GETDATE() AS DATE)";
        try {
            con = conectar.getConexion();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            if (rs.next()) {
                total = rs.getDouble("VentasHoy");
            }
        } catch (SQLException e) {
            System.out.println("Error al sumar ventas del día: " + e.toString());
        }
        return total;
    }

    // Lista las 10 ventas más recientes
    public List<Venta> listarVentasRecientes() {
        List<Venta> lista = new ArrayList<>();
        String sql = "SELECT TOP 10 [Id Venta], Hora, Total FROM Venta ORDER BY [Id Venta] DESC";
        try {
            con = conectar.getConexion();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Venta v = new Venta();
                v.setIdVenta(rs.getInt("Id Venta"));
                v.setHora(rs.getString("Hora"));
                v.setTotal(rs.getDouble("Total"));
                lista.add(v);
            }
        } catch (SQLException e) {
            System.out.println("Error en ventas recientes: " + e.toString());
        }
        return lista;
    }

     // Guarda una venta nueva y devuelve su id generado
    public int guardarVenta(Venta v) {
        int idVenta = 0;
        String sql = "INSERT INTO Venta (Fecha, Hora, [Tipo Comprob], Total, [Id Empleado]) VALUES (CAST(GETDATE() AS DATE), CAST(GETDATE() AS TIME), ?, ?, ?)";
        try {
            con = conectar.getConexion();
       
            ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, v.getTipoComprob());
            ps.setDouble(2, v.getTotal());
            ps.setInt(3, v.getIdEmpleado());
            ps.executeUpdate();
            
            rs = ps.getGeneratedKeys();
            if (rs.next()) {
                idVenta = rs.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println("Error al guardar venta: " + e.toString());
        }
        return idVenta;
    }
    // Filtra ventas entre dos fechas
public List<Venta> filtrarVentasPorFecha(String inicio, String fin) {
    List<Venta> lista = new ArrayList<>();
    String sql = "SELECT [Id Venta], Fecha, Hora, Total FROM Venta WHERE Fecha BETWEEN ? AND ?";
    try {
        con = conectar.getConexion();
        ps = con.prepareStatement(sql);
        ps.setString(1, inicio);
        ps.setString(2, fin);
        rs = ps.executeQuery();
        while (rs.next()) {
            Venta v = new Venta();
            v.setIdVenta(rs.getInt("Id Venta"));
            v.setFecha(rs.getString("Fecha"));
            v.setHora(rs.getString("Hora"));
            v.setTotal(rs.getDouble("Total"));
            lista.add(v);
        }
    } catch (SQLException e) {
        System.out.println("Error al filtrar ventas: " + e.toString());
    }
     // Devuelve ventas dentro del rango indicado
    return lista;
}
 // Obtiene cantidad de ventas y total vendido
public double[] obtenerResumenVentas(String inicio, String fin) {
    double[] resumen = new double[2];
    String sql = "SELECT COUNT([Id Venta]), SUM(Total) FROM Venta WHERE Fecha BETWEEN ? AND ?";
    try {
        con = conectar.getConexion();
        ps = con.prepareStatement(sql);
        ps.setString(1, inicio);
        ps.setString(2, fin);
        rs = ps.executeQuery();
        if (rs.next()) {
            resumen[0] = rs.getDouble(1); 
            resumen[1] = rs.getDouble(2);
            // resumen[0] = número de ventas
            // resumen[1] = suma total
        }
    } catch (SQLException e) {
        System.out.println("Error en resumen: " + e.toString());
    }
    return resumen;
}
}
