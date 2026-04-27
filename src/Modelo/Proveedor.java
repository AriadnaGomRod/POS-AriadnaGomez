package Modelo;

/**
 *
 * @author arigo
 */
/**
 * 
 * Clase para manejar a los proveedores
 */
public class Proveedor {
    //Creación de las variables
    private int idProveedor;
    private String nomProv;
    private String telefono;
    private String descripcion;

    //Constructor vacío
    public Proveedor() {}


    //Getters y setters
    public int getIdProveedor() {
        return idProveedor;
    }

    public void setIdProveedor(int idProveedor) {
        this.idProveedor = idProveedor;
    }

    public String getNomProv() {
        return nomProv;
    }

    public void setNomProv(String nomProv) {
        this.nomProv = nomProv;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
}