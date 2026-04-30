package controller;

import view.MainView;
import dao.*;
import exceptions.DatabaseException;
import model.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * Controlador principal que coordina todas las vistas y controladores
 */
public class MainController {
    private MainView mainView;
    private InventarioController inventarioController;
    private DashboardController dashboardController;
    private SucursalController sucursalController;
    private Usuario usuarioActual;

    public MainController(MainView mainView, Usuario usuario) throws Exception {
        this.mainView = mainView;
        this.usuarioActual = usuario;

        // Inicializar controladores
        this.inventarioController = new InventarioController(mainView.getInventarioView(), usuario.getRol());
        this.dashboardController = new DashboardController(mainView.getDashboardView());

        if (usuario.getRol().equals("ADMIN")) {
            this.sucursalController = new SucursalController(mainView.getSucursalView());
        }

        // Agregar listener al botón de logout
        mainView.addLogoutButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                logout();
            }
        });

        // Cargar datos iniciales
        cargarDatosIniciales();
    }

    /**
     * Carga los datos iniciales en las vistas
     */
    private void cargarDatosIniciales() {
        try {
            // Cargar sucursales
            SucursalDAO sucursalDAO = new SucursalDAO();
            List<Sucursal> sucursales = sucursalDAO.obtenerTodas();
            java.util.List<String> nombresSucursales = new java.util.ArrayList<>();
            for (Sucursal s : sucursales) {
                nombresSucursales.add(s.getNombre());
            }
            inventarioController.getInventarioView().loadSucursales(nombresSucursales);

            // Cargar vehículos
            inventarioController.cargarVehiculos();

            // Cargar estadísticas del dashboard
            dashboardController.actualizarEstadisticas();

            // Cargar sucursales en la vista de sucursales (si es admin)
            if (usuarioActual.getRol().equals("ADMIN")) {
                sucursalController.cargarSucursales();
            }
        } catch (Exception e) {
            mainView.mostrarError("Error al cargar datos iniciales: " + e.getMessage());
        }
    }

    /**
     * Cierra la sesión del usuario
     */
    private void logout() {
        mainView.setVisible(false);
        mainView.dispose();
        // El Main.java se encargará de mostrar el LoginView nuevamente
    }

    // Getters
    public MainView getMainView() {
        return mainView;
    }

    public InventarioController getInventarioController() {
        return inventarioController;
    }

    public DashboardController getDashboardController() {
        return dashboardController;
    }

    public SucursalController getSucursalController() {
        return sucursalController;
    }

    public Usuario getUsuarioActual() {
        return usuarioActual;
    }
}
