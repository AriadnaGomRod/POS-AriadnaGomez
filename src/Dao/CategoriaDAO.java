package Dao;
import Conexion.Conexion;
import Modelo.Categoria;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author arigo
    */
   /**
    * Clase encargada de administrar categorías en la base de datos.
    */
public class CategoriaDAO {
    
    // Variables para conexión y consultas SQL
    Conexion conectar = new Conexion();
    Connection con;
    PreparedStatement ps;
    ResultSet rs;
    
     // Lista todas las categorías registradas
    public List<Categoria> listar() {
        List<Categoria> lista = new ArrayList<>();
        String sql = "SELECT * FROM Categoria";
        try {
            con = conectar.getConexion();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Categoria cat = new Categoria();
                cat.setIdCategoria(rs.getInt("Id_Categoria"));
                cat.setNombreCat(rs.getString("Nombre_Cat"));
                lista.add(cat);
            }
        } catch (SQLException e) {
            System.out.println("Error al listar categorías: " + e.toString());
        }
        return lista;
         // Consulta general de categorías
    }
    
        // Registra una nueva categoría
    public boolean registrar(Categoria cat) {
        String sql = "INSERT INTO Categoria ([Nombre_Cat]) VALUES (?)";
        try {
            con = conectar.getConexion();
            ps = con.prepareStatement(sql);
            ps.setString(1, cat.getNombreCat());
            ps.execute();
            return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al registrar categoría: " + e.toString());
            return false;
        }
         // Inserta el nombre de la categoría
    }

        // Edita una categoría existente
    public boolean editar(Categoria cat) {
        String sql = "UPDATE Categoria SET [Nombre_Cat]=? WHERE [Id_Categoria]=?";
        try {
            con = conectar.getConexion();
            ps = con.prepareStatement(sql);
            ps.setString(1, cat.getNombreCat());
            ps.setInt(2, cat.getIdCategoria());
            ps.execute();
            return true;
        } catch (SQLException e) {
            System.out.println("Error al editar categoría: " + e.toString());
            return false;
        }
        // Actualiza el nombre según su id

    }

      // Elimina una categoría por id
    public boolean eliminar(int id) {
        String sql = "DELETE FROM Categoria WHERE [Id_Categoria] = ?";
        try {
            con = conectar.getConexion();
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ps.execute();
            return true;
        } catch (SQLException e) {
            System.out.println("Error al eliminar categoría: " + e.toString());
            return false;
        }
    // Borra el registro seleccionado
    }
   public int obtenerIdPorNombre(String nombre) {
    int id = 0;
    String sql = "SELECT Id_Categoria FROM Categoria WHERE Nombre_Cat = ?";
    try {
        con = conectar.getConexion();
        ps = con.prepareStatement(sql);
        ps.setString(1, nombre);
        rs = ps.executeQuery();
        if (rs.next()) {
            id = rs.getInt("Id_Categoria");
        }
    } catch (SQLException e) {
        System.out.println("Error al buscar ID: " + e.toString());
    }
    return id;
}
}