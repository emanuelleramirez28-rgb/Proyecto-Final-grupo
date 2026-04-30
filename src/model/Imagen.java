package model;

/**
 * Clase que representa una imagen asociada a un vehículo
 */
public class Imagen {
    private int id;
    private int idVehiculo;
    private String ruta;
    private boolean esPrincipal;

    // Constructor con ID
    public Imagen(int id, int idVehiculo, String ruta, boolean esPrincipal) {
        this.id = id;
        this.idVehiculo = idVehiculo;
        this.ruta = ruta;
        this.esPrincipal = esPrincipal;
    }

    // Constructor sin ID
    public Imagen(int idVehiculo, String ruta, boolean esPrincipal) {
        this.idVehiculo = idVehiculo;
        this.ruta = ruta;
        this.esPrincipal = esPrincipal;
    }

    // Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdVehiculo() {
        return idVehiculo;
    }

    public void setIdVehiculo(int idVehiculo) {
        this.idVehiculo = idVehiculo;
    }

    public String getRuta() {
        return ruta;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }

    public boolean isEsPrincipal() {
        return esPrincipal;
    }

    public void setEsPrincipal(boolean esPrincipal) {
        this.esPrincipal = esPrincipal;
    }

    @Override
    public String toString() {
        return "Imagen{" +
                "id=" + id +
                ", idVehiculo=" + idVehiculo +
                ", ruta='" + ruta + '\'' +
                ", esPrincipal=" + esPrincipal +
                '}';
    }
}
