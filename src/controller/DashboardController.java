package controller;

import view.DashboardView;
import dao.VehiculoDAO;
import dao.SucursalDAO;
import exceptions.DatabaseException;
import model.Vehiculo;
import model.Sucursal;
import model.Auto;
import model.Motocicleta;
import model.Camion;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Controlador para el Dashboard con estadísticas
 */
public class DashboardController {
    private DashboardView dashboardView;
    private VehiculoDAO vehiculoDAO;
    private SucursalDAO sucursalDAO;

    public DashboardController(DashboardView dashboardView) throws Exception {
        this.dashboardView = dashboardView;
        this.vehiculoDAO = new VehiculoDAO();
        this.sucursalDAO = new SucursalDAO();
    }

    /**
     * Actualiza todas las estadísticas del dashboard
     */
    public void actualizarEstadisticas() {
        try {
            List<Vehiculo> vehiculos = vehiculoDAO.obtenerTodos();

            // Calcular KPIs
            int totalStock = vehiculos.size();
            double totalValor = 0;
            Map<Integer, Integer> vehiculosPorSucursal = new HashMap<>();
            int autoCount = 0;
            int motoCount = 0;
            int camionCount = 0;

            for (Vehiculo v : vehiculos) {
                totalValor += v.getPrecio();
                vehiculosPorSucursal.put(v.getIdSucursal(), 
                    vehiculosPorSucursal.getOrDefault(v.getIdSucursal(), 0) + 1);

                if (v instanceof Auto) autoCount++;
                else if (v instanceof Motocicleta) motoCount++;
                else if (v instanceof Camion) camionCount++;
            }

            // Encontrar sucursal con más vehículos
            String sucursalMax = "N/A";
            int maxVehiculos = 0;
            for (Map.Entry<Integer, Integer> entry : vehiculosPorSucursal.entrySet()) {
                if (entry.getValue() > maxVehiculos) {
                    maxVehiculos = entry.getValue();
                    Sucursal s = sucursalDAO.obtenerSucursal(entry.getKey());
                    sucursalMax = s.getNombre();
                }
            }

            // Actualizar vista
            dashboardView.actualizarKPIs(totalStock, totalValor, sucursalMax);
            dashboardView.actualizarGraficoStock(autoCount, motoCount, camionCount);

        } catch (DatabaseException e) {
            // Manejar error silenciosamente
            System.err.println("Error al actualizar estadísticas: " + e.getMessage());
        }
    }
}
