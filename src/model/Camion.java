package model;

/**
 * Subclase de Vehiculo que representa un camión
 */
public class Camion extends Vehiculo {
    private double capacidadCarga;
    private int numeroEjes;

    // Constructor con ID
    public Camion(int id, String marca, String modelo, int año, double precio, int idSucursal, boolean activo,
                  double capacidadCarga, int numeroEjes) {
        super(id, marca, modelo, año, precio, idSucursal, activo);
        this.capacidadCarga = capacidadCarga;
        this.numeroEjes = numeroEjes;
    }

    // Constructor sin ID
    public Camion(String marca, String modelo, int año, double precio, int idSucursal, boolean activo,
                  double capacidadCarga, int numeroEjes) {
        super(marca, modelo, año, precio, idSucursal, activo);
        this.capacidadCarga = capacidadCarga;
        this.numeroEjes = numeroEjes;
    }

    @Override
    public double calcularImpuesto() {
        // Impuesto: 10% del precio base para camiones (mayor impuesto por ser vehículo comercial)
        return getPrecio() * 0.10;
    }

    @Override
    public String obtenerTipo() {
        return "Camion";
    }

    @Override
    public String mostrarDetalles() {
        return "Camión: " + getMarca() + " " + getModelo() + " (" + getAño() + ")\n" +
               "Capacidad de Carga: " + capacidadCarga + " ton | Ejes: " + numeroEjes + "\n" +
               "Precio: $" + String.format("%.2f", getPrecio());
    }

    // Getters y Setters
    public double getCapacidadCarga() {
        return capacidadCarga;
    }

    public void setCapacidadCarga(double capacidadCarga) {
        this.capacidadCarga = capacidadCarga;
    }

    public int getNumeroEjes() {
        return numeroEjes;
    }

    public void setNumeroEjes(int numeroEjes) {
        this.numeroEjes = numeroEjes;
    }

    @Override
    public String toString() {
        return "Camion{" +
                "marca='" + getMarca() + '\'' +
                ", modelo='" + getModelo() + '\'' +
                ", año=" + getAño() +
                ", precio=" + getPrecio() +
                ", capacidadCarga=" + capacidadCarga +
                ", numeroEjes=" + numeroEjes +
                '}';
    }
}
