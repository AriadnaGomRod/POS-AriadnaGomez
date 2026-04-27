package Modelo;

/**
 *
 * @author arigo
 */
/**
 * Clase para manejar las categorías.
 */
public class Categoria {
    //Creacion de las variables
    private int idCategoria;
    private String nombreCat;

    //Constructor vacío
    public Categoria() {}

//Getters y setters
    public int getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(int idCategoria) {
        this.idCategoria = idCategoria;
    }

    public String getNombreCat() {
        return nombreCat;
    }

    public void setNombreCat(String nombreCat) {
        this.nombreCat = nombreCat;
    }
    
}
