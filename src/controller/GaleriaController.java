package controller;

import view.GaleriaView;
import dao.ImagenDAO;
import model.Imagen;
import exceptions.DatabaseException;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;

/**
 * Controlador para la galería de imágenes
 */
public class GaleriaController {
    private GaleriaView galeriaView;
    private ImagenDAO imagenDAO;
    private int idVehiculo;
    private List<Imagen> imagenes;
    private int indiceActual = 0;

    public GaleriaController(GaleriaView galeriaView, int idVehiculo, ImagenDAO imagenDAO) throws Exception {
        this.galeriaView = galeriaView;
        this.imagenDAO = imagenDAO;
        this.idVehiculo = idVehiculo;
        this.imagenes = imagenDAO.obtenerImagenesVehiculo(idVehiculo);

        // Configurar listeners
        galeriaView.addAnteriorButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarAnterior();
            }
        });

        galeriaView.addSiguienteButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarSiguiente();
            }
        });

        galeriaView.addAgregarButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                agregarImagen();
            }
        });

        galeriaView.addEliminarButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                eliminarImagen();
            }
        });

        galeriaView.addEstablecerPrincipalButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                establecerImagenPrincipal();
            }
        });

        // Mostrar primera imagen
        mostrarImagenActual();
    }

    /**
     * Muestra la imagen anterior
     */
    private void mostrarAnterior() {
        if (indiceActual > 0) {
            indiceActual--;
            mostrarImagenActual();
        }
    }

    /**
     * Muestra la siguiente imagen
     */
    private void mostrarSiguiente() {
        if (indiceActual < imagenes.size() - 1) {
            indiceActual++;
            mostrarImagenActual();
        }
    }

    /**
     * Muestra la imagen actual
     */
    private void mostrarImagenActual() {
        if (imagenes.isEmpty()) {
            galeriaView.mostrarImagen(null);
            galeriaView.setInfoLabel("Sin imágenes");
        } else {
            Imagen imagen = imagenes.get(indiceActual);
            try {
                File file = new File(imagen.getRuta());
                if (file.exists()) {
                    BufferedImage bufferedImage = ImageIO.read(file);
                    ImageIcon icon = new ImageIcon(bufferedImage);
                    galeriaView.mostrarImagen(icon);
                } else {
                    galeriaView.mostrarImagen(null);
                }
            } catch (Exception e) {
                galeriaView.mostrarImagen(null);
            }
            galeriaView.setInfoLabel("Imagen " + (indiceActual + 1) + " de " + imagenes.size());
        }
    }

    /**
     * Agrega una nueva imagen
     */
    private void agregarImagen() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setMultiSelectionEnabled(true);
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter(
            "Imágenes", "jpg", "jpeg", "png"));

        int resultado = fileChooser.showOpenDialog(galeriaView);
        if (resultado == JFileChooser.APPROVE_OPTION) {
            File[] archivos = fileChooser.getSelectedFiles();
            try {
                for (File archivo : archivos) {
                    copiarYGuardarImagen(archivo);
                }
                imagenes = imagenDAO.obtenerImagenesVehiculo(idVehiculo);
                mostrarImagenActual();
                galeriaView.mostrarExito("Imágenes agregadas correctamente");
            } catch (Exception e) {
                galeriaView.mostrarError("Error al agregar imágenes: " + e.getMessage());
            }
        }
    }

    /**
     * Copia la imagen a la carpeta de recursos y la guarda en BD
     */
    private void copiarYGuardarImagen(File archivo) throws Exception {
        String nombre = System.currentTimeMillis() + "_" + archivo.getName();
        File destino = new File("resources/images/" + nombre);
        
        // Copiar archivo
        Files.copy(archivo.toPath(), destino.toPath(), 
            java.nio.file.StandardCopyOption.REPLACE_EXISTING);

        // Guardar en BD
        boolean esPrincipal = imagenes.isEmpty(); // La primera es principal
        Imagen imagen = new Imagen(idVehiculo, destino.getAbsolutePath(), esPrincipal);
        imagenDAO.insertarImagen(imagen);
    }

    /**
     * Elimina la imagen actual
     */
    private void eliminarImagen() {
        if (imagenes.isEmpty()) {
            galeriaView.mostrarError("No hay imágenes para eliminar");
            return;
        }

        int opcion = JOptionPane.showConfirmDialog(galeriaView,
            "¿Está seguro de que desea eliminar esta imagen?",
            "Confirmar eliminación", JOptionPane.YES_NO_OPTION);

        if (opcion == JOptionPane.YES_OPTION) {
            try {
                Imagen imagen = imagenes.get(indiceActual);
                imagenDAO.eliminarImagen(imagen.getId());
                
                // Eliminar archivo del sistema
                File file = new File(imagen.getRuta());
                if (file.exists()) {
                    file.delete();
                }

                imagenes = imagenDAO.obtenerImagenesVehiculo(idVehiculo);
                if (indiceActual >= imagenes.size()) {
                    indiceActual = Math.max(0, imagenes.size() - 1);
                }
                mostrarImagenActual();
                galeriaView.mostrarExito("Imagen eliminada correctamente");
            } catch (DatabaseException e) {
                galeriaView.mostrarError("Error al eliminar imagen: " + e.getMessage());
            }
        }
    }

    /**
     * Establece la imagen actual como principal
     */
    private void establecerImagenPrincipal() {
        if (imagenes.isEmpty()) {
            galeriaView.mostrarError("No hay imágenes");
            return;
        }

        try {
            Imagen imagen = imagenes.get(indiceActual);
            imagenDAO.establecerImagenPrincipal(imagen.getId());
            galeriaView.mostrarExito("Imagen establecida como principal");
            imagenes = imagenDAO.obtenerImagenesVehiculo(idVehiculo);
            mostrarImagenActual();
        } catch (DatabaseException e) {
            galeriaView.mostrarError("Error: " + e.getMessage());
        }
    }
}

// Importaciones necesarias que faltaban
class Files {
    public static void copy(java.nio.file.Path source, java.nio.file.Path target, 
                          java.nio.file.CopyOption... options) throws java.io.IOException {
        java.nio.file.Files.copy(source, target, options);
    }
}
