package Modelo;

/**
 *
 * @author arigo
 */
/**
 * Clase para manejar los productos.
 */
public class Producto {
    //Creación de las variables
    private int idProducto;
    private String codigoBarras;
    private String nomProd;
    private double precio;
    private int stock;
    private int stockMin;
    private int idCategoria;
    private int idProveedor;
private String proveedor;
private String categoria;
private String estado;
private double precioMayoreo;

    //Constructor
    public Producto() {

        
    }

  public Producto(int idProducto, String codigoBarras, String nomProd, double precio,
                int stock, int stockMin, int idCat,
                String proveedor, String categoria, String estado, double precioMayoreo) {

    this.idProducto = idProducto;
    this.codigoBarras = codigoBarras;
    this.nomProd = nomProd;
    this.precio = precio;
    this.stock = stock;
    this.stockMin = stockMin;
    this.idCategoria = idCat;
    this.proveedor = proveedor;
    this.categoria = categoria;
    this.estado = estado;
    this.precioMayoreo = precioMayoreo;
}


    //Getters y setters
    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public String getCodigoBarras() {
        return codigoBarras;
    }

    public void setCodigoBarras(String codigoBarras) {
        this.codigoBarras = codigoBarras;
    }

    public String getNomProd() {
        return nomProd;
    }

    public void setNomProd(String nomProd) {
        this.nomProd = nomProd;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public int getStockMin() {
        return stockMin;
    }

    public void setStockMin(int stockMin) {
        this.stockMin = stockMin;
    }

    public int getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(int idCategoria) {
        this.idCategoria = idCategoria;
    }

    public int getIdProveedor() {
        return idProveedor;
    }

    public void setIdProveedor(int idProveedor) {
        this.idProveedor = idProveedor;
    }

    public String getProveedor() {
        return proveedor;
    }

    public void setProveedor(String proveedor) {
        this.proveedor = proveedor;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
    public double getPrecioMayoreo() {
    return precioMayoreo;
}

public void setPrecioMayoreo(double precioMayoreo) {
    this.precioMayoreo = precioMayoreo;
}
    
    @Override
public String toString() {
    return nomProd;
}
}