package Dao;

import Conexion.Conexion;
import Modelo.Producto;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class ProductoDAO {

    private final Conexion conectar = new Conexion();

    private static final String SELECT_BASE =
            "SELECT p.ID_Producto, p.Codigo_Barras, p.Nom_Prod, p.Precio, " +
            "p.Precio_Mayoreo, p.Stock, p.Stock_Min, p.Estado, " +
            "p.Id_Categoria, p.ID_Proveedor, " +
            "c.Nombre_Cat AS Categoria, " +
            "pr.Nom_Prov AS Proveedor " +
            "FROM Producto p " +
            "INNER JOIN Categoria c ON p.Id_Categoria = c.Id_Categoria " +
            "INNER JOIN Proveedor pr ON p.ID_Proveedor = pr.ID_Proveedor ";

    private Producto obtenerProducto(ResultSet rs) throws SQLException {
        Producto p = new Producto();

        p.setIdProducto(rs.getInt("ID_Producto"));
        p.setCodigoBarras(rs.getString("Codigo_Barras"));
        p.setNomProd(rs.getString("Nom_Prod"));
        p.setPrecio(rs.getDouble("Precio"));
        p.setPrecioMayoreo(rs.getDouble("Precio_Mayoreo"));
        p.setStock(rs.getInt("Stock"));
        p.setStockMin(rs.getInt("Stock_Min"));
        p.setEstado(rs.getString("Estado"));
        p.setIdCategoria(rs.getInt("Id_Categoria"));
        p.setIdProveedor(rs.getInt("ID_Proveedor"));
        p.setCategoria(rs.getString("Categoria"));
        p.setProveedor(rs.getString("Proveedor"));

        return p;
    }

    public List<Producto> listar() {
        List<Producto> lista = new ArrayList<>();
        String sql = SELECT_BASE + "ORDER BY p.ID_Producto DESC";

        try (
            Connection con = conectar.getConexion();
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()
        ) {
            while (rs.next()) {
                lista.add(obtenerProducto(rs));
            }
        } catch (SQLException e) {
            System.out.println("Error al listar productos: " + e.getMessage());
        }

        return lista;
    }

    public List<Producto> listarStockBajo() {
        List<Producto> lista = new ArrayList<>();
        String sql = SELECT_BASE + "WHERE p.Stock <= p.Stock_Min";

        try (
            Connection con = conectar.getConexion();
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()
        ) {
            while (rs.next()) {
                lista.add(obtenerProducto(rs));
            }
        } catch (SQLException e) {
            System.out.println("Error stock bajo: " + e.getMessage());
        }

        return lista;
    }

    public boolean registrar(Producto p) {
        String sql = "INSERT INTO Producto " +
                "(Codigo_Barras, Nom_Prod, Precio, Precio_Mayoreo, Stock, Stock_Min, Id_Categoria, ID_Proveedor, Estado) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (
            Connection con = conectar.getConexion();
            PreparedStatement ps = con.prepareStatement(sql)
        ) {
            ps.setString(1, p.getCodigoBarras());
            ps.setString(2, p.getNomProd());
            ps.setDouble(3, p.getPrecio());
            ps.setDouble(4, p.getPrecioMayoreo());
            ps.setInt(5, p.getStock());
            ps.setInt(6, p.getStockMin());
            ps.setInt(7, p.getIdCategoria());
            ps.setInt(8, p.getIdProveedor());
            ps.setString(9, p.getEstado() == null ? "Activo" : p.getEstado());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al registrar producto: " + e.getMessage());
            return false;
        }
    }

    public boolean editar(Producto p) {
        String sql = "UPDATE Producto SET " +
                "Codigo_Barras = ?, " +
                "Nom_Prod = ?, " +
                "Precio = ?, " +
                "Precio_Mayoreo = ?, " +
                "Stock = ?, " +
                "Stock_Min = ?, " +
                "Id_Categoria = ?, " +
                "ID_Proveedor = ?, " +
                "Estado = ? " +
                "WHERE ID_Producto = ?";

        try (
            Connection con = conectar.getConexion();
            PreparedStatement ps = con.prepareStatement(sql)
        ) {
            ps.setString(1, p.getCodigoBarras());
            ps.setString(2, p.getNomProd());
            ps.setDouble(3, p.getPrecio());
            ps.setDouble(4, p.getPrecioMayoreo());
            ps.setInt(5, p.getStock());
            ps.setInt(6, p.getStockMin());
            ps.setInt(7, p.getIdCategoria());
            ps.setInt(8, p.getIdProveedor());
            ps.setString(9, p.getEstado());
            ps.setInt(10, p.getIdProducto());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al editar producto: " + e.getMessage());
            return false;
        }
    }

    public boolean eliminar(int id) {
        String sql = "DELETE FROM Producto WHERE ID_Producto = ?";

        try (
            Connection con = conectar.getConexion();
            PreparedStatement ps = con.prepareStatement(sql)
        ) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al eliminar producto: " + e.getMessage());
            return false;
        }
    }

    public Producto buscarPorCodigo(String codigo) {
        String sql = SELECT_BASE + "WHERE p.Codigo_Barras = ?";

        try (
            Connection con = conectar.getConexion();
            PreparedStatement ps = con.prepareStatement(sql)
        ) {
            ps.setString(1, codigo);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return obtenerProducto(rs);
                }
            }

        } catch (SQLException e) {
            System.out.println("Error buscar por código: " + e.getMessage());
        }

        return null;
    }

    public Producto buscarPorNombre(String nombre) {
        String sql = SELECT_BASE + "WHERE p.Nom_Prod = ?";

        try (
            Connection con = conectar.getConexion();
            PreparedStatement ps = con.prepareStatement(sql)
        ) {
            ps.setString(1, nombre);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return obtenerProducto(rs);
                }
            }

        } catch (SQLException e) {
            System.out.println("Error buscar nombre: " + e.getMessage());
        }

        return null;
    }

    public boolean descontarStock(int cantidad, int idProducto) {
        String sql = "UPDATE Producto SET " +
                "Stock = Stock - ?, " +
                "Estado = CASE WHEN Stock - ? <= 0 THEN 'Inactivo' ELSE 'Activo' END " +
                "WHERE ID_Producto = ? AND Stock >= ?";

        try (
            Connection con = conectar.getConexion();
            PreparedStatement ps = con.prepareStatement(sql)
        ) {
            ps.setInt(1, cantidad);
            ps.setInt(2, cantidad);
            ps.setInt(3, idProducto);
            ps.setInt(4, cantidad);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error descontar stock: " + e.getMessage());
            return false;
        }
    }

    public boolean actualizarStockYEstado(int stock, int idProducto) {
        String sql = "UPDATE Producto SET " +
                "Stock = ?, " +
                "Estado = CASE WHEN ? <= 0 THEN 'Inactivo' ELSE 'Activo' END " +
                "WHERE ID_Producto = ?";

        try (
            Connection con = conectar.getConexion();
            PreparedStatement ps = con.prepareStatement(sql)
        ) {
            ps.setInt(1, stock);
            ps.setInt(2, stock);
            ps.setInt(3, idProducto);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error actualizar stock: " + e.getMessage());
            return false;
        }
    }

    public boolean editarCampos(String columna, Object valor, int idProducto) {
        if (!columna.equals("Stock") &&
            !columna.equals("Precio") &&
            !columna.equals("Precio_Mayoreo") &&
            !columna.equals("Stock_Min") &&
            !columna.equals("Estado")) {

            JOptionPane.showMessageDialog(null, "Columna no permitida: " + columna);
            return false;
        }

        String sql = "UPDATE Producto SET " + columna + " = ? WHERE ID_Producto = ?";

        try (
            Connection con = conectar.getConexion();
            PreparedStatement ps = con.prepareStatement(sql)
        ) {
            ps.setObject(1, valor);
            ps.setInt(2, idProducto);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error actualizar campo: " + e.getMessage());
            return false;
        }
    }

    public List<Producto> buscarPorFiltros(String proveedor, String categoria, String estado) {
        List<Producto> lista = new ArrayList<>();
        StringBuilder sql = new StringBuilder(SELECT_BASE + "WHERE 1=1 ");

        if (proveedor != null && !proveedor.equals("Seleccionar Proveedor")) {
            sql.append("AND pr.Nom_Prov = ? ");
        }

        if (categoria != null && !categoria.equals("Seleccionar Categoría")) {
            sql.append("AND c.Nombre_Cat = ? ");
        }

        if (estado != null && !estado.equals("Seleccionar Estado")) {
            sql.append("AND p.Estado = ? ");
        }

        try (
            Connection con = conectar.getConexion();
            PreparedStatement ps = con.prepareStatement(sql.toString())
        ) {
            int index = 1;

            if (proveedor != null && !proveedor.equals("Seleccionar Proveedor")) {
                ps.setString(index++, proveedor);
            }

            if (categoria != null && !categoria.equals("Seleccionar Categoría")) {
                ps.setString(index++, categoria);
            }

            if (estado != null && !estado.equals("Seleccionar Estado")) {
                ps.setString(index++, estado);
            }

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(obtenerProducto(rs));
                }
            }

        } catch (SQLException e) {
            System.out.println("Error filtros: " + e.getMessage());
        }

        return lista;
    }
}