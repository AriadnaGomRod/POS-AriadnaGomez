package Dao;

import Conexion.Conexion;
import Modelo.Producto;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 * Clase encargada de administrar los productos en la base de datos.
 */
public class ProductoDAO {
  // Conexión a la base de datos
    Conexion conectar = new Conexion();

    // Método auxiliar para convertir registros en objetos Producto
    private Producto obtenerProducto(ResultSet rs) throws SQLException {
        Producto p = new Producto();

        p.setIdProducto(rs.getInt("ID_Producto"));
        p.setCodigoBarras(rs.getString("Codigo_Barras"));
        p.setNomProd(rs.getString("Nom_Prod"));
        p.setPrecio(rs.getDouble("Precio"));
        p.setStock(rs.getInt("Stock"));
        p.setStockMin(rs.getInt("Stock_Min"));
        p.setProveedor(rs.getString("Proveedor"));
p.setCategoria(rs.getString("Categoria"));
p.setEstado(rs.getString("Estado"));

        return p;
        // Asigna los datos consultados al objeto
    }
// Lista todos los productos registrados
    public List<Producto> listar() {
        List<Producto> lista = new ArrayList<>();

    String sql = "SELECT p.ID_Producto, p.Nom_Prod, " +
"c.Nombre_Cat AS Categoria, " +
"pr.Nom_Prov AS Proveedor, " +
"p.Codigo_Barras, " +
"p.Precio, p.Stock, p.Stock_Min, " +
"p.Estado " +   
"FROM Producto p " +
"INNER JOIN Categoria c ON p.Id_Categoria = c.Id_Categoria " +
"INNER JOIN Proveedor pr ON p.ID_Proveedor = pr.ID_Proveedor";

        try (
            Connection con = conectar.getConexion();
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()
        ) {
            while (rs.next()) {
                lista.add(obtenerProducto(rs));
            }

        } catch (SQLException e) {
            System.out.println("Error al listar: " + e);
        }

        return lista;
        // Consulta general de productos
    }

    // Lista productos con stock bajo
    public List<Producto> listarStockBajo() {
        List<Producto> lista = new ArrayList<>();

        String sql = "SELECT p.ID_Producto, p.Nom_Prod, " +
             "c.Nombre_Cat AS Categoria, " +
             "pr.Nom_Prov AS Proveedor, " +
             "p.Codigo_Barras, " +   
             "p.Precio, p.Stock, p.Stock_Min " +
             "FROM Producto p " +
             "INNER JOIN Categoria c ON p.Id_Categoria = c.Id_Categoria " +
             "INNER JOIN Proveedor pr ON p.ID_Proveedor = pr.ID_Proveedor " +
             "WHERE p.Stock <= p.Stock_Min";

        try (
            Connection con = conectar.getConexion();
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()
        ) {
            while (rs.next()) {
                lista.add(obtenerProducto(rs));
            }

        } catch (SQLException e) {
            System.out.println("Error stock bajo: " + e);
        }

        return lista;
    } // Muestra productos con stock menor o igual al mínimo

    
    // Registra un nuevo producto
    public boolean registrar(Producto p) {

        String sql = "INSERT INTO Producto "
                + "([Codigo_Barras],[Nom_Prod],Precio,Stock,[Stock_Min],[Id_Categoria],[ID_Proveedor]) "
                + "VALUES (?,?,?,?,?,?,?)";

        try (
            Connection con = conectar.getConexion();
            PreparedStatement ps = con.prepareStatement(sql)
        ) {

            ps.setString(1, p.getCodigoBarras());
            ps.setString(2, p.getNomProd());
            ps.setDouble(3, p.getPrecio());
            ps.setInt(4, p.getStock());
            ps.setInt(5, p.getStockMin());
            ps.setInt(6, p.getIdCategoria());
            ps.setInt(7, p.getIdProveedor());

            ps.executeUpdate();
            return true;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al registrar: " + e);
            return false;
        }
           // Inserta datos en la tabla Producto
    }

    // Elimina un producto por id
    public boolean eliminar(int id) {

        String sql = "DELETE FROM Producto WHERE [ID_Producto] = ?";

        try (
            Connection con = conectar.getConexion();
            PreparedStatement ps = con.prepareStatement(sql)
        ) {

            ps.setInt(1, id);
            ps.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.out.println("Error al eliminar: " + e);
            return false;
        }
         // Borra el registro seleccionado
    }
    
    // Busca producto por código de barras
    public Producto buscarPorCodigo(String codigo) {

        String sql = "SELECT * FROM Producto WHERE [Codigo_Barras] = ?";

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
            System.out.println("Error buscar código: " + e);
        }

        return null;
        // Devuelve el producto encontrado
    }
    // Busca producto por nombre
    public Producto buscarPorNombre(String nombre) {

        String sql = "SELECT p.ID_Producto, p.Nom_Prod, " +
                 "c.Nombre_Cat AS Categoria, " +
                 "pr.Nom_Prov AS Proveedor, " +
                 "p.Precio, p.Stock, p.Stock_Min, p.Codigo_Barras " +
                 "FROM Producto p " +
                 "INNER JOIN Categoria c ON p.Id_Categoria = c.Id_Categoria " +
                 "INNER JOIN Proveedor pr ON p.ID_Proveedor = pr.ID_Proveedor " +
                 "WHERE p.Nom_Prod = ?";

          try {
        Connection con = conectar.getConexion();
        PreparedStatement ps = con.prepareStatement(sql);

        ps.setString(1, nombre);

        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            return obtenerProducto(rs);
        }

    } catch (SQLException e) {
        System.out.println("Error buscar nombre: " + e);
    }

    return null;
        // Consulta por nombre exacto
    }
    
// Edita todos los datos de un producto
    public boolean editar(Producto p) {

        String sql = "UPDATE Producto SET "
                + "[Codigo_Barras]=?, "
                + "[Nom_Prod]=?, "
                + "Precio=?, "
                + "Stock=?, "
                + "[Stock_Min]=?, "
                + "[Id_Categoria]=?, "
                + "[ID_Proveedor]=? "
                + "WHERE [ID_Producto]=?";

        try (
            Connection con = conectar.getConexion();
            PreparedStatement ps = con.prepareStatement(sql)
        ) {

            ps.setString(1, p.getCodigoBarras());
            ps.setString(2, p.getNomProd());
            ps.setDouble(3, p.getPrecio());
            ps.setInt(4, p.getStock());
            ps.setInt(5, p.getStockMin());
            ps.setInt(6, p.getIdCategoria());
            ps.setInt(7, p.getIdProveedor());
            ps.setInt(8, p.getIdProducto());
            

            ps.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.out.println("Error editar: " + e);
            return false;
        }
            // Actualiza la información completa
    }
    
    // Edita un solo campo del producto
    public boolean editarCampos(String columna, Object valor, int id) {
        // La columna se concatena directamente en el String sql
        String sql = "UPDATE Producto SET " + columna + " = ? WHERE [ID_Producto] = ?";

        try (
            Connection con = conectar.getConexion();
            PreparedStatement ps = con.prepareStatement(sql)
        ) {
            ps.setObject(1, valor);
            ps.setInt(2, id);

            ps.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.out.println("Error actualizar campo " + columna + ": " + e);
            return false;
        }
        // Actualiza una columna específica
    }

     // Descuenta stock después de una venta
    public boolean actualizarStockYEstado(int stock, int id) {
    String sql = "UPDATE Producto SET Stock = ?, " +
                 "Estado = CASE WHEN ? = 0 THEN 'Inactivo' ELSE 'Activo' END " +
                 "WHERE ID_Producto = ?";
    try {
        Connection con = conectar.getConexion();
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, stock);
        ps.setInt(2, stock);
        ps.setInt(3, id);
        return ps.executeUpdate() > 0;
    } catch (Exception e) {
        System.out.println("Error al actualizar stock: " + e);
        return false;
    }
}
    public boolean descontarStock(int cantidad, int id) {

        String sql = "UPDATE Producto SET Stock = Stock - ? WHERE [ID_Producto] = ?";

        try (
            Connection con = conectar.getConexion();
            PreparedStatement ps = con.prepareStatement(sql)
        ) {

            ps.setInt(1, cantidad);
            ps.setInt(2, id);

            ps.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.out.println("Error descontar stock: " + e);
            return false;
        }
         // Resta cantidad al stock actual
    }

    // Busca productos por proveedor y categoría
    public List<Producto> buscarPorFiltros(int idProv, int idCat) {

        List<Producto> lista = new ArrayList<>();

      String sql = "SELECT p.ID_Producto, p.Nom_Prod, " +
             "c.Nombre_Cat AS Categoria, " +
             "pr.Nom_Prov AS Proveedor, " +
             "p.Codigo_Barras, " +   
             "p.Precio, p.Stock, p.Stock_Min " +
             "FROM Producto p " +
             "INNER JOIN Categoria c ON p.Id_Categoria = c.Id_Categoria " +
             "INNER JOIN Proveedor pr ON p.ID_Proveedor = pr.ID_Proveedor";

        try (
            Connection con = conectar.getConexion();
            PreparedStatement ps = con.prepareStatement(sql)
        ) {

            ps.setInt(1, idProv);
            ps.setInt(2, idCat);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(obtenerProducto(rs));
                }
            }

        } catch (SQLException e) {
            System.out.println("Error filtros: " + e);
        }

        return lista;
    }
    // Filtra productos según ambos datos
    public List<Producto> buscarPorFiltros(String proveedor, String categoria, String estado) {

    List<Producto> lista = new ArrayList<>();

    String sql = "SELECT p.ID_Producto, p.Nom_Prod, " +
             "c.Nombre_Cat AS Categoria, " +
             "pr.Nom_Prov AS Proveedor, " +
             "p.Codigo_Barras, " +
             "p.Precio, p.Stock, p.Stock_Min, " +
             "p.Estado " +
             "FROM Producto p " +
             "INNER JOIN Categoria c ON p.Id_Categoria = c.Id_Categoria " +
             "INNER JOIN Proveedor pr ON p.ID_Proveedor = pr.ID_Proveedor " +
             "WHERE 1=1";

   if (!proveedor.equals("Seleccionar Proveedor")) {
    sql += " AND pr.Nom_Prov = ?";
}

if (!categoria.equals("Seleccionar Categoría")) {
    sql += " AND c.Nombre_Cat = ?";
}

if (!estado.equals("Seleccionar Estado")) {
    sql += " AND p.Estado = ?";
}

    try {
        Connection con = conectar.getConexion();
        PreparedStatement ps = con.prepareStatement(sql);

        int index = 1;

        if (!proveedor.equals("Seleccionar Proveedor")) {
            ps.setString(index++, proveedor);
        }

        if (!categoria.equals("Seleccionar Categoría")) {
            ps.setString(index++, categoria);
        }

        if (!estado.equals("Seleccionar Estado")) {
            ps.setString(index++, estado);
        }

        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            lista.add(obtenerProducto(rs));
        }

    } catch (Exception e) {
        System.out.println("Error filtros: " + e);
    }

    return lista; // 👈 ESTE TE FALTABA
}
    
}