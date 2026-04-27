package Dao;
import Conexion.Conexion;
import Modelo.DetalleVenta;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author arigo
 */
/**
 * Clase encargada de manejar los detalles de venta en la base de datos.
 */
public class DetalleVentaDAO {

    // Variables para conexión y consultas SQL
    Conexion conectar = new Conexion();
    Connection con;
    PreparedStatement ps;
    ResultSet rs;

  // Registra los productos vendidos en una venta
    public boolean registrarDetalle(DetalleVenta dv) {
        String sql = "INSERT INTO [Detalle Venta] ([Id Venta], [ID Producte], Cantidad, "
                + "[Precio Unitario], SubTotal) VALUES (?,?,?,?,?)";
         // Inserta producto, cantidad, precio y subtotal
        try {
            con = conectar.getConexion();
            ps = con.prepareStatement(sql);
            ps.setInt(1, dv.getIdVenta());
            ps.setInt(2, dv.getIdProducto());
            ps.setInt(3, dv.getCantidad());
            ps.setDouble(4, dv.getPrecioUnitario());
            ps.setDouble(5, dv.getSubtotal());
            ps.execute();
            return true;
        } catch (SQLException e) {
            System.out.println("Error al registrar detalle: " + e.toString());
            return false;
        }
    }

       // Lista los detalles según el id de venta
    public List<DetalleVenta> listarDetallesPorVenta(int idVenta) {
        List<DetalleVenta> lista = new ArrayList<>();
        String sql = "SELECT * FROM [Detalle Venta] WHERE [Id Venta] = ?";
        try {
            con = conectar.getConexion();
            ps = con.prepareStatement(sql);
            ps.setInt(1, idVenta);
            rs = ps.executeQuery();
            while (rs.next()) {
                DetalleVenta dv = new DetalleVenta();
                dv.setIdVenta(rs.getInt("Id Venta"));
                dv.setIdProducto(rs.getInt("ID Producte"));
                dv.setCantidad(rs.getInt("Cantidad"));
                dv.setPrecioUnitario(rs.getDouble("Precio Unitario"));
                dv.setSubtotal(rs.getDouble("SubTotal"));
                lista.add(dv);
            }
        } catch (SQLException e) {
            System.out.println("Error al listar detalles: " + e.toString());
        }
        // Devuelve los productos relacionados con esa venta
        return lista;
    }
}