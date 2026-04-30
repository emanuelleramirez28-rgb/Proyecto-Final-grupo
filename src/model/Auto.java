package model;

/**
 * Subclase de Vehiculo que representa un automóvil
 */
public class Auto extends Vehiculo {
    private int numeroPuertas;
    private String tipoCombustible;
    private boolean esAutomatico;

    // Constructor con ID
    public Auto(int id, String marca, String modelo, int año, double precio, int idSucursal, boolean activo,
                int numeroPuertas, String tipoCombustible, boolean esAutomatico) {
        super(id, marca, modelo, año, precio, idSucursal, activo);
        this.numeroPuertas = numeroPuertas;
        this.tipoCombustible = tipoCombustible;
        this.esAutomatico = esAutomatico;
    }

    // Constructor sin ID
    public Auto(String marca, String modelo, int año, double precio, int idSucursal, boolean activo,
                int numeroPuertas, String tipoCombustible, boolean esAutomatico) {
        super(marca, modelo, año, precio, idSucursal, activo);
        this.numeroPuertas = numeroPuertas;
        this.tipoCombustible = tipoCombustible;
        this.esAutomatico = esAutomatico;
    }

    @Override
    public double calcularImpuesto() {
        // Impuesto: 8% del precio base para automóviles
        return getPrecio() * 0.08;
    }

    @Override
    public String obtenerTipo() {
        return "Auto";
    }

    @Override
    public String mostrarDetalles() {
        return "Auto: " + getMarca() + " " + getModelo() + " (" + getAño() + ")\n" +
               "Puertas: " + numeroPuertas + " | Combustible: " + tipoCombustible + " | " +
               (esAutomatico ? "Automático" : "Manual") + "\n" +
               "Precio: $" + String.format("%.2f", getPrecio());
    }

    // Getters y Setters
    public int getNumeroPuertas() {
        return numeroPuertas;
    }

    public void setNumeroPuertas(int numeroPuertas) {
        this.numeroPuertas = numeroPuertas;
    }

    public String getTipoCombustible() {
        return tipoCombustible;
    }

    public void setTipoCombustible(String tipoCombustible) {
        this.tipoCombustible = tipoCombustible;
    }

    public boolean isEsAutomatico() {
        return esAutomatico;
    }

    public void setEsAutomatico(boolean esAutomatico) {
        this.esAutomatico = esAutomatico;
    }

    @Override
    public String toString() {
        return "Auto{" +
                "marca='" + getMarca() + '\'' +
                ", modelo='" + getModelo() + '\'' +
                ", año=" + getAño() +
                ", precio=" + getPrecio() +
                ", numeroPuertas=" + numeroPuertas +
                ", tipoCombustible='" + tipoCombustible + '\'' +
                ", esAutomatico=" + esAutomatico +
                '}';
    }
}
