package model;

/**
 * Subclase de Vehiculo que representa una motocicleta
 */
public class Motocicleta extends Vehiculo {
    private int cilindrada;
    private String tipoManejo;

    // Constructor con ID
    public Motocicleta(int id, String marca, String modelo, int año, double precio, int idSucursal, boolean activo,
                       int cilindrada, String tipoManejo) {
        super(id, marca, modelo, año, precio, idSucursal, activo);
        this.cilindrada = cilindrada;
        this.tipoManejo = tipoManejo;
    }

    // Constructor sin ID
    public Motocicleta(String marca, String modelo, int año, double precio, int idSucursal, boolean activo,
                       int cilindrada, String tipoManejo) {
        super(marca, modelo, año, precio, idSucursal, activo);
        this.cilindrada = cilindrada;
        this.tipoManejo = tipoManejo;
    }

    @Override
    public double calcularImpuesto() {
        // Impuesto: 5% del precio base para motocicletas
        return getPrecio() * 0.05;
    }

    @Override
    public String obtenerTipo() {
        return "Motocicleta";
    }

    @Override
    public String mostrarDetalles() {
        return "Motocicleta: " + getMarca() + " " + getModelo() + " (" + getAño() + ")\n" +
               "Cilindrada: " + cilindrada + "cc | Manejo: " + tipoManejo + "\n" +
               "Precio: $" + String.format("%.2f", getPrecio());
    }

    // Getters y Setters
    public int getCilindrada() {
        return cilindrada;
    }

    public void setCilindrada(int cilindrada) {
        this.cilindrada = cilindrada;
    }

    public String getTipoManejo() {
        return tipoManejo;
    }

    public void setTipoManejo(String tipoManejo) {
        this.tipoManejo = tipoManejo;
    }

    @Override
    public String toString() {
        return "Motocicleta{" +
                "marca='" + getMarca() + '\'' +
                ", modelo='" + getModelo() + '\'' +
                ", año=" + getAño() +
                ", precio=" + getPrecio() +
                ", cilindrada=" + cilindrada +
                ", tipoManejo='" + tipoManejo + '\'' +
                '}';
    }
}
