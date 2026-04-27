package Modelo;

/**
 *
 * @author arigo
 */
/**
 * 
 * Clase para manejar las ventas
 */
public class Venta {
    //Creación de variables
    private int idVenta;
    private String fecha;
    private String hora;
    private String tipoComprob;
    private double total;
    private int idEmpleado;
    //Constructor vacío
    public Venta() {}
    
    //Getters y setters
    public int getIdVenta() {
        return idVenta;
    }

    public void setIdVenta(int idVenta) {
        this.idVenta = idVenta;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getTipoComprob() {
        return tipoComprob;
    }

    public void setTipoComprob(String tipoComprob) {
        this.tipoComprob = tipoComprob;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public int getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(int idEmpleado) {
        this.idEmpleado = idEmpleado;
    }
    
}