package model;

/**
 * Clase abstracta que representa un vehículo en el sistema AutoElite
 */
public abstract class Vehiculo {
    private int id;
    private String marca;
    private String modelo;
    private int año;
    private double precio;
    private int idSucursal;
    private boolean activo;

    // Constructor
    public Vehiculo(int id, String marca, String modelo, int año, double precio, int idSucursal, boolean activo) {
        this.id = id;
        this.marca = marca;
        this.modelo = modelo;
        this.año = año;
        this.precio = precio;
        this.idSucursal = idSucursal;
        this.activo = activo;
    }

    // Constructor sin ID (para inserciones nuevas)
    public Vehiculo(String marca, String modelo, int año, double precio, int idSucursal, boolean activo) {
        this.marca = marca;
        this.modelo = modelo;
        this.año = año;
        this.precio = precio;
        this.idSucursal = idSucursal;
        this.activo = activo;
    }

    // Métodos abstractos que deben implementar las subclases
    public abstract double calcularImpuesto();
    public abstract String obtenerTipo();
    public abstract String mostrarDetalles();

    // Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public int getAño() {
        return año;
    }

    public void setAño(int año) {
        this.año = año;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getIdSucursal() {
        return idSucursal;
    }

    public void setIdSucursal(int idSucursal) {
        this.idSucursal = idSucursal;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    @Override
    public String toString() {
        return "Vehiculo{" +
                "id=" + id +
                ", marca='" + marca + '\'' +
                ", modelo='" + modelo + '\'' +
                ", año=" + año +
                ", precio=" + precio +
                ", idSucursal=" + idSucursal +
                ", activo=" + activo +
                '}';
    }
}
