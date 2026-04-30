package controller;

import view.InventarioView;
import view.GaleriaView;
import dao.VehiculoDAO;
import dao.SucursalDAO;
import dao.ImagenDAO;
import exceptions.*;
import model.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;

/**
 * Controlador para la gestión del inventario de vehículos
 */
public class InventarioController {
    private InventarioView inventarioView;
    private VehiculoDAO vehiculoDAO;
    private SucursalDAO sucursalDAO;
    private ImagenDAO imagenDAO;
    private String rolUsuario;
    private List<Vehiculo> vehiculosActuales;

    public InventarioController(InventarioView inventarioView, String rol) throws Exception {
        this.inventarioView = inventarioView;
        this.rolUsuario = rol;
        this.vehiculoDAO = new VehiculoDAO();
        this.sucursalDAO = new SucursalDAO();
        this.imagenDAO = new ImagenDAO();
        this.vehiculosActuales = new ArrayList<>();

        // Configurar permisos según rol
        if (rol.equals("VENDEDOR")) {
            inventarioView.addAgregarButtonListener(e -> 
                inventarioView.mostrarError("Solo administradores pueden agregar vehículos"));
            inventarioView.addEditarButtonListener(e -> 
                inventarioView.mostrarError("Solo administradores pueden editar vehículos"));
            inventarioView.addEliminarButtonListener(e -> 
                inventarioView.mostrarError("Solo administradores pueden eliminar vehículos"));
        } else {
            inventarioView.addAgregarButtonListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    agregarVehiculo();
                }
            });

            inventarioView.addEditarButtonListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    editarVehiculo();
                }
            });

            inventarioView.addEliminarButtonListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    eliminarVehiculo();
                }
            });
        }

        // Listener para galería (disponible para todos)
        inventarioView.addGaleriaButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirGaleria();
            }
        });

        // Listener para búsqueda
        inventarioView.addSearchButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                realizarBusqueda();
            }
        });
    }

    /**
     * Carga todos los vehículos en la tabla
     */
    public void cargarVehiculos() {
        try {
            vehiculosActuales = vehiculoDAO.obtenerTodos();
            actualizarTabla(vehiculosActuales);
        } catch (DatabaseException e) {
            inventarioView.mostrarError("Error al cargar vehículos: " + e.getMessage());
        }
    }

    /**
     * Realiza búsqueda de vehículos según los filtros
     */
    private void realizarBusqueda() {
        try {
            String textoBusqueda = inventarioView.getSearchText();
            String sucursal = inventarioView.getSelectedSucursal();
            String tipo = inventarioView.getSelectedTipoVehiculo();
            double precioMin = inventarioView.getPrecioMin();
            double precioMax = inventarioView.getPrecioMax();

            List<Vehiculo> resultados = new ArrayList<>();

            if (!textoBusqueda.isEmpty()) {
                resultados = vehiculoDAO.buscar(textoBusqueda);
            } else {
                resultados = vehiculoDAO.obtenerTodos();
            }

            // Aplicar filtros
            resultados = aplicarFiltros(resultados, sucursal, tipo, precioMin, precioMax);
            vehiculosActuales = resultados;
            actualizarTabla(resultados);
        } catch (Exception e) {
            inventarioView.mostrarError("Error en búsqueda: " + e.getMessage());
        }
    }

    /**
     * Aplica filtros a una lista de vehículos
     */
    private List<Vehiculo> aplicarFiltros(List<Vehiculo> vehiculos, String sucursal, 
                                          String tipo, double precioMin, double precioMax) throws Exception {
        List<Vehiculo> filtrados = new ArrayList<>();

        for (Vehiculo v : vehiculos) {
            // Filtro por sucursal
            if (!sucursal.equals("Todas")) {
                Sucursal s = sucursalDAO.obtenerSucursal(v.getIdSucursal());
                if (!s.getNombre().equals(sucursal)) continue;
            }

            // Filtro por tipo
            if (!tipo.equals("Todos")) {
                if (!v.obtenerTipo().equals(tipo)) continue;
            }

            // Filtro por precio
            if (v.getPrecio() < precioMin || v.getPrecio() > precioMax) continue;

            filtrados.add(v);
        }

        return filtrados;
    }

    /**
     * Actualiza la tabla con los vehículos
     */
    private void actualizarTabla(List<Vehiculo> vehiculos) throws DatabaseException {
        List<Object[]> datos = new ArrayList<>();
        for (Vehiculo v : vehiculos) {
            Sucursal sucursal = sucursalDAO.obtenerSucursal(v.getIdSucursal());
            datos.add(new Object[]{
                v.getId(),
                v.getMarca(),
                v.getModelo(),
                v.getAño(),
                String.format("$%.2f", v.getPrecio()),
                v.obtenerTipo(),
                sucursal.getNombre()
            });
        }
        inventarioView.loadVehiculos(datos);
    }

    /**
     * Abre el diálogo para agregar un nuevo vehículo
     */
    private void agregarVehiculo() {
        // Esta es una versión simplificada. En una aplicación real, 
        // se abriría un diálogo con un formulario completo
        JOptionPane.showMessageDialog(inventarioView, 
            "Funcionalidad de agregar vehículo requiere una interfaz adicional",
            "Información", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Abre el diálogo para editar un vehículo
     */
    private void editarVehiculo() {
        int id = inventarioView.getSelectedVehiculoId();
        if (id == -1) {
            inventarioView.mostrarError("Por favor seleccione un vehículo");
            return;
        }

        JOptionPane.showMessageDialog(inventarioView, 
            "Funcionalidad de editar vehículo requiere una interfaz adicional",
            "Información", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Elimina un vehículo (baja lógica)
     */
    private void eliminarVehiculo() {
        int id = inventarioView.getSelectedVehiculoId();
        if (id == -1) {
            inventarioView.mostrarError("Por favor seleccione un vehículo");
            return;
        }

        int opcion = JOptionPane.showConfirmDialog(inventarioView, 
            "¿Está seguro de que desea eliminar este vehículo?",
            "Confirmar eliminación", JOptionPane.YES_NO_OPTION);

        if (opcion == JOptionPane.YES_OPTION) {
            try {
                vehiculoDAO.darDeBaja(id);
                inventarioView.mostrarExito("Vehículo eliminado correctamente");
                cargarVehiculos();
            } catch (DatabaseException e) {
                inventarioView.mostrarError("Error al eliminar vehículo: " + e.getMessage());
            }
        }
    }

    /**
     * Abre la galería de imágenes de un vehículo
     */
    private void abrirGaleria() {
        int id = inventarioView.getSelectedVehiculoId();
        if (id == -1) {
            inventarioView.mostrarError("Por favor seleccione un vehículo");
            return;
        }

        try {
            GaleriaView galeriaView = new GaleriaView(null, "Galería de Imágenes");
            GaleriaController galeriaController = new GaleriaController(galeriaView, id, imagenDAO);
            galeriaView.setVisible(true);
        } catch (Exception e) {
            inventarioView.mostrarError("Error al abrir galería: " + e.getMessage());
        }
    }

    // Getter
    public InventarioView getInventarioView() {
        return inventarioView;
    }
}
