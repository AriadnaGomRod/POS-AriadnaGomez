package dao;
import Conexion.Conexion;
import Modelo.Proveedor;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author arigo
 */
/**
 * Clase encargada de administrar proveedores en la base de datos.
 */
public class ProveedorDAO {
    // Variables para conexión y consultas SQL
    Conexion conectar = new Conexion();
    Connection con;
    PreparedStatement ps;
    ResultSet rs;


    // Lista todos los proveedores registrados
    public List<Proveedor> listar() {
        List<Proveedor> lista = new ArrayList<>();
        String sql = "SELECT * FROM Proveedor";
        try {
            con = conectar.getConexion();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Proveedor pr = new Proveedor();
                pr.setIdProveedor(rs.getInt("ID Proveedor"));
                pr.setNomProv(rs.getString("Nom Prov"));
                pr.setTelefono(rs.getString("Telefono"));
                pr.setDescripcion(rs.getString("Descripción"));
                lista.add(pr);
            }
        } catch (SQLException e) {
            System.out.println("Error al listar proveedores: " + e.toString());
        }
        return lista;
          // Consulta general de proveedores
    }

    // Registra un nuevo proveedor
    public boolean registrar(Proveedor pr) {
        String sql = "INSERT INTO Proveedor ([Nom Prov], Telefono, Descripción) VALUES (?,?,?)";
        try {
            con = conectar.getConexion();
            ps = con.prepareStatement(sql);
            ps.setString(1, pr.getNomProv());
            ps.setString(2, pr.getTelefono());
            ps.setString(3, pr.getDescripcion());
            ps.execute();
            return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al registrar proveedor: " + e.toString());
            return false;
        }
          // Inserta nombre, teléfono y descripción
    }

    // Edita los datos de un proveedor
    public boolean editar(Proveedor pr) {
        String sql = "UPDATE Proveedor SET [Nom Prov]=?, Telefono=?, Descripción=? WHERE [ID Proveedor]=?";
        try {
            con = conectar.getConexion();
            ps = con.prepareStatement(sql);
            ps.setString(1, pr.getNomProv());
            ps.setString(2, pr.getTelefono());
            ps.setString(3, pr.getDescripcion());
            ps.setInt(4, pr.getIdProveedor());
            ps.execute();
            return true;
        } catch (SQLException e) {
            System.out.println("Error al editar proveedor: " + e.toString());
            return false;
        }
                // Actualiza la información seleccionada
    }

    // Elimina un proveedor por id
    public boolean eliminar(int id) {
        String sql = "DELETE FROM Proveedor WHERE [ID Proveedor] = ?";
        try {
            con = conectar.getConexion();
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ps.execute();
            return true;
        } catch (SQLException e) {
            System.out.println("Error al eliminar proveedor: " + e.toString());
            return false;
        }
    }
      // Borra el registro indicado
}
