package Dao;
import Conexion.Conexion;
import Modelo.Empleado;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
/**
 *
 * @author arigo
 */
/**
 * Clase encargada de administrar empleados en la base de datos.
 */
public class EmpleadoDAO {
    // Variables para conexión y consultas SQL
    Conexion conectar = new Conexion();
    Connection con;
    PreparedStatement ps;
    ResultSet rs;

    // Verifica usuario y contraseña para iniciar sesión
    public Empleado login(String user, String pass) {
        Empleado emp = null;
        String sql = "SELECT * FROM Empleado WHERE Usuario = ? AND Contraseña = ?";
        try {
            con = conectar.getConexion();
            ps = con.prepareStatement(sql);
            ps.setString(1, user);
            ps.setString(2, pass);
            rs = ps.executeQuery();
            if (rs.next()) {
                emp = new Empleado();
                emp.setIdEmpleado(rs.getInt("Id Empleado"));
                emp.setNombre(rs.getString("Nombre"));
                emp.setRol(rs.getString("Rol"));
                emp.setUsuario(rs.getString("Usuario"));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error en Login: " + e.toString());
        }
        return emp;
        // Devuelve datos del empleado si existe
    }

    // Lista todos los empleados registrados
    public List<Empleado> listar() {
        List<Empleado> lista = new ArrayList<>();
        String sql = "SELECT * FROM Empleado";
        try {
            con = conectar.getConexion();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Empleado em = new Empleado();
                em.setIdEmpleado(rs.getInt("Id Empleado"));
                em.setNombre(rs.getString("Nombre"));
                em.setaPaterno(rs.getString("A Paterno"));
                em.setTelefono(rs.getString("Telefono"));
                em.setRol(rs.getString("Rol"));
                em.setUsuario(rs.getString("Usuario"));
                lista.add(em);
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return lista;
        // Consulta general de empleados
    }

    // Registra un nuevo empleado
    public boolean registrar(Empleado emp) {
        String sql = "INSERT INTO Empleado (Nombre, [A Paterno], [A Materno], Telefono, Usuario, Contraseña, Rol) VALUES (?,?,?,?,?,?,?)";
        try {
            con = conectar.getConexion();
            ps = con.prepareStatement(sql);
            ps.setString(1, emp.getNombre());
            ps.setString(2, emp.getaPaterno());
            ps.setString(3, emp.getaMaterno());
            ps.setString(4, emp.getTelefono());
            ps.setString(5, emp.getUsuario());
            ps.setString(6, emp.getContrasena());
            ps.setString(7, emp.getRol());
            ps.execute();
            return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al registrar: " + e.toString());
            return false;
        }
         // Inserta datos personales y acceso
    }

    // Elimina un empleado por id
    public boolean eliminar(int id) {
        String sql = "DELETE FROM Empleado WHERE [Id Empleado] = ?";
        try {
            con = conectar.getConexion();
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ps.execute();
            return true;
        } catch (SQLException e) {
            System.out.println(e.toString());
            return false;
        }
        // Borra el registro seleccionado
    }
    
}