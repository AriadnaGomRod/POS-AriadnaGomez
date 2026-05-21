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
        String sql = "INSERT INTO [Detalle_Venta] ([Id_Venta], [ID_Producto], Cantidad, "
                + "[Precio_Unitario], SubTotal) VALUES (?,?,?,?,?)";
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
        String sql = "SELECT * FROM [Detalle_Venta] WHERE [Id_Venta] = ?";
        try {
            con = conectar.getConexion();
            ps = con.prepareStatement(sql);
            ps.setInt(1, idVenta);
            rs = ps.executeQuery();
            while (rs.next()) {
                DetalleVenta dv = new DetalleVenta();
                dv.setIdVenta(rs.getInt("Id_Venta"));
                dv.setIdProducto(rs.getInt("ID_Producto"));
                dv.setCantidad(rs.getInt("Cantidad"));
                dv.setPrecioUnitario(rs.getDouble("Precio_Unitario"));
                dv.setSubtotal(rs.getDouble("SubTotal"));
                lista.add(dv);
            }
        } catch (SQLException e) {
            System.out.println("Error al listar detalles: " + e.toString());
        }
        // Devuelve los productos relacionados con esa venta
        return lista;
    }
    public List<Object[]> listarDetallesPorFecha(String fechaInicio, String fechaFin) {
    List<Object[]> lista = new ArrayList<>();

    String sql = "SELECT v.Id_Venta, p.Nom_Prod, dv.Cantidad, dv.SubTotal " +
                 "FROM Venta v " +
                 "INNER JOIN Detalle_Venta dv ON v.Id_Venta = dv.Id_Venta " +
                 "INNER JOIN Producto p ON dv.ID_Producto = p.ID_Producto " +
                 "WHERE CONVERT(date, v.Fecha) BETWEEN ? AND ? " +
                 "ORDER BY v.Id_Venta DESC";

    try {
        con = conectar.getConexion();
        ps = con.prepareStatement(sql);
        ps.setString(1, fechaInicio);
        ps.setString(2, fechaFin);

        rs = ps.executeQuery();

        while (rs.next()) {
            Object[] fila = new Object[4];

            fila[0] = rs.getInt("Id_Venta");
            fila[1] = rs.getString("Nom_Prod");
            fila[2] = rs.getInt("Cantidad");
            fila[3] = rs.getDouble("SubTotal");

            lista.add(fila);
        }

    } catch (SQLException e) {
        System.out.println("Error al listar detalles por fecha: " + e.toString());
    }

    return lista;
}
}