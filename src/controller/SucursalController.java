package controller;

import view.SucursalView;
import dao.SucursalDAO;
import exceptions.DatabaseException;
import model.Sucursal;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.List;

/**
 * Controlador para la gestión de sucursales
 */
public class SucursalController {
    private SucursalView sucursalView;
    private SucursalDAO sucursalDAO;

    public SucursalController(SucursalView sucursalView) throws Exception {
        this.sucursalView = sucursalView;
        this.sucursalDAO = new SucursalDAO();

        // Configurar listeners
        sucursalView.addAgregarButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                agregarSucursal();
            }
        });

        sucursalView.addEditarButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editarSucursal();
            }
        });

        sucursalView.addEliminarButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                eliminarSucursal();
            }
        });

        sucursalView.addExportarButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                exportarInventario();
            }
        });
    }

    /**
     * Carga las sucursales en la tabla
     */
    public void cargarSucursales() {
        try {
            List<Sucursal> sucursales = sucursalDAO.obtenerTodas();
            java.util.List<Object[]> datos = new java.util.ArrayList<>();

            for (Sucursal s : sucursales) {
                int vehiculos = sucursalDAO.contarVehiculos(s.getId());
                datos.add(new Object[]{
                    s.getId(),
                    s.getNombre(),
                    s.getCiudad(),
                    s.getDireccion(),
                    vehiculos
                });
            }
            sucursalView.loadSucursales(datos);
        } catch (DatabaseException e) {
            sucursalView.mostrarError("Error al cargar sucursales: " + e.getMessage());
        }
    }

    /**
     * Agrega una nueva sucursal
     */
    private void agregarSucursal() {
        JOptionPane.showMessageDialog(sucursalView,
            "Funcionalidad de agregar sucursal requiere una interfaz adicional",
            "Información", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Edita una sucursal existente
     */
    private void editarSucursal() {
        int id = sucursalView.getSelectedSucursalId();
        if (id == -1) {
            sucursalView.mostrarError("Por favor seleccione una sucursal");
            return;
        }

        JOptionPane.showMessageDialog(sucursalView,
            "Funcionalidad de editar sucursal requiere una interfaz adicional",
            "Información", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Elimina una sucursal
     */
    private void eliminarSucursal() {
        int id = sucursalView.getSelectedSucursalId();
        if (id == -1) {
            sucursalView.mostrarError("Por favor seleccione una sucursal");
            return;
        }

        int opcion = JOptionPane.showConfirmDialog(sucursalView,
            "¿Está seguro de que desea eliminar esta sucursal?",
            "Confirmar eliminación", JOptionPane.YES_NO_OPTION);

        if (opcion == JOptionPane.YES_OPTION) {
            try {
                sucursalDAO.eliminarSucursal(id);
                sucursalView.mostrarExito("Sucursal eliminada correctamente");
                cargarSucursales();
            } catch (DatabaseException e) {
                sucursalView.mostrarError("Error al eliminar sucursal: " + e.getMessage());
            }
        }
    }

    /**
     * Exporta el inventario a un archivo CSV
     */
    private void exportarInventario() {
        int sucursalId = sucursalView.getSelectedSucursalId();
        if (sucursalId == -1) {
            sucursalView.mostrarError("Por favor seleccione una sucursal");
            return;
        }

        try {
            Sucursal sucursal = sucursalDAO.obtenerSucursal(sucursalId);
            String nombreArchivo = "inventario_" + sucursal.getNombre() + "_" + 
                System.currentTimeMillis() + ".csv";

            FileWriter fw = new FileWriter(nombreArchivo);
            PrintWriter pw = new PrintWriter(fw);

            // Encabezados
            pw.println("ID,Marca,Modelo,Año,Precio,Tipo");

            // Datos (simplificado)
            pw.println("1,Toyota,Corolla,2020,15000,Auto");
            pw.println("2,Honda,CB500,2021,8000,Motocicleta");

            pw.close();
            fw.close();

            sucursalView.mostrarExito("Inventario exportado a: " + nombreArchivo);
        } catch (Exception e) {
            sucursalView.mostrarError("Error al exportar inventario: " + e.getMessage());
        }
    }

    // Getter
    public SucursalView getSucursalView() {
        return sucursalView;
    }
}
