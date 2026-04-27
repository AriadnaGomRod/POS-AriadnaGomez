package Dao;
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
public class ProveedorDAO {

    Conexion conectar = new Conexion();
    Connection con;
    PreparedStatement ps;
    ResultSet rs;


    public List<Proveedor> listar() {
        List<Proveedor> lista = new ArrayList<>();
        String sql = "SELECT * FROM Proveedor";
        try {
            con = conectar.getConexion();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Proveedor pr = new Proveedor();
                pr.setIdProveedor(rs.getInt("ID_Proveedor"));
                pr.setNomProv(rs.getString("Nom_Prov"));
                pr.setTelefono(rs.getString("Telefono"));
                pr.setDescripcion(rs.getString("Descripcion"));
                lista.add(pr);
            }
        } catch (SQLException e) {
            System.out.println("Error al listar proveedores: " + e.toString());
        }
        return lista;
    }


    public boolean registrar(Proveedor pr) {
        String sql = "INSERT INTO Proveedor ([Nom_Prov], Telefono, Descripcion) VALUES (?,?,?)";
        try {
            con = conectar.getConexion();
            ps = con.prepareStatement(sql);
            ps.setString(1, pr.getNomProv());
            ps.setString(2, pr.getTelefono());
            ps.setString(3, pr.getDescripcion());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al registrar proveedor: " + e.toString());
            return false;
        }
    }


    public boolean editar(Proveedor pr) {
        String sql = "UPDATE Proveedor SET [Nom_Prov]=?, Telefono=?, Descripcion=? WHERE [ID_Proveedor]=?";
        try {
            con = conectar.getConexion();
            ps = con.prepareStatement(sql);
            ps.setString(1, pr.getNomProv());
            ps.setString(2, pr.getTelefono());
            ps.setString(3, pr.getDescripcion());
            ps.setInt(4, pr.getIdProveedor());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Error al editar proveedor: " + e.toString());
            return false;
        }
    }


    public boolean eliminar(int id) {
        String sql = "DELETE FROM Proveedor WHERE [ID_Proveedor] = ?";
        try {
            con = conectar.getConexion();
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Error al eliminar proveedor: " + e.toString());
            return false;
        }
    }
    public int obtenerIdPorNombre(String nombre) {
    int id = 0;
    String sql = "SELECT Id_Proveedor FROM Proveedor WHERE Nom_Prov = ?"; 
    try {
        con = conectar.getConexion();
        ps = con.prepareStatement(sql);
        ps.setString(1, nombre);
        rs = ps.executeQuery();
        if (rs.next()) {
            id = rs.getInt("Id_Proveedor");
        }
    } catch (SQLException e) {
        System.out.println("Error al buscar ID: " + e.toString());
    }
    return id;
}
}
