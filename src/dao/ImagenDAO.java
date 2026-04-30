package dao;

import model.Imagen;
import exceptions.DatabaseException;
import java.sql.*;
import java.util.*;

/**
 * DAO para gestionar operaciones CRUD de imágenes
 */
public class ImagenDAO {
    private Connection conexion;

    public ImagenDAO() throws Exception {
        this.conexion = ConexionDB.getInstance().getConexion();
    }

    /**
     * Inserta una imagen en la base de datos
     */
    public int insertarImagen(Imagen imagen) throws DatabaseException {
        String sql = "INSERT INTO imagenes (id_vehiculo, ruta, es_principal) VALUES (?, ?, ?)";
        try {
            PreparedStatement pstmt = conexion.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstmt.setInt(1, imagen.getIdVehiculo());
            pstmt.setString(2, imagen.getRuta());
            pstmt.setBoolean(3, imagen.isEsPrincipal());
            pstmt.executeUpdate();

            ResultSet rs = pstmt.getGeneratedKeys();
            int idGenerado = 0;
            if (rs.next()) {
                idGenerado = rs.getInt(1);
            }
            pstmt.close();
            return idGenerado;
        } catch (SQLException e) {
            throw new DatabaseException("Error al insertar imagen: " + e.getMessage(), e);
        }
    }

    /**
     * Obtiene una imagen por su ID
     */
    public Imagen obtenerImagen(int id) throws DatabaseException {
        String sql = "SELECT * FROM imagenes WHERE id = ?";
        try {
            PreparedStatement pstmt = conexion.prepareStatement(sql);
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                Imagen imagen = new Imagen(rs.getInt("id"), rs.getInt("id_vehiculo"),
                        rs.getString("ruta"), rs.getBoolean("es_principal"));
                pstmt.close();
                return imagen;
            }
            pstmt.close();
            return null;
        } catch (SQLException e) {
            throw new DatabaseException("Error al obtener imagen: " + e.getMessage(), e);
        }
    }

    /**
     * Obtiene todas las imágenes de un vehículo
     */
    public List<Imagen> obtenerImagenesVehiculo(int idVehiculo) throws DatabaseException {
        List<Imagen> imagenes = new ArrayList<>();
        String sql = "SELECT * FROM imagenes WHERE id_vehiculo = ? ORDER BY es_principal DESC, id";
        try {
            PreparedStatement pstmt = conexion.prepareStatement(sql);
            pstmt.setInt(1, idVehiculo);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                imagenes.add(new Imagen(rs.getInt("id"), rs.getInt("id_vehiculo"),
                        rs.getString("ruta"), rs.getBoolean("es_principal")));
            }
            pstmt.close();
        } catch (SQLException e) {
            throw new DatabaseException("Error al obtener imágenes del vehículo: " + e.getMessage(), e);
        }
        return imagenes;
    }

    /**
     * Obtiene la imagen principal de un vehículo
     */
    public Imagen obtenerImagenPrincipal(int idVehiculo) throws DatabaseException {
        String sql = "SELECT * FROM imagenes WHERE id_vehiculo = ? AND es_principal = true LIMIT 1";
        try {
            PreparedStatement pstmt = conexion.prepareStatement(sql);
            pstmt.setInt(1, idVehiculo);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                Imagen imagen = new Imagen(rs.getInt("id"), rs.getInt("id_vehiculo"),
                        rs.getString("ruta"), rs.getBoolean("es_principal"));
                pstmt.close();
                return imagen;
            }
            pstmt.close();
            return null;
        } catch (SQLException e) {
            throw new DatabaseException("Error al obtener imagen principal: " + e.getMessage(), e);
        }
    }

    /**
     * Actualiza una imagen
     */
    public void actualizarImagen(Imagen imagen) throws DatabaseException {
        String sql = "UPDATE imagenes SET id_vehiculo = ?, ruta = ?, es_principal = ? WHERE id = ?";
        try {
            PreparedStatement pstmt = conexion.prepareStatement(sql);
            pstmt.setInt(1, imagen.getIdVehiculo());
            pstmt.setString(2, imagen.getRuta());
            pstmt.setBoolean(3, imagen.isEsPrincipal());
            pstmt.setInt(4, imagen.getId());
            pstmt.executeUpdate();
            pstmt.close();
        } catch (SQLException e) {
            throw new DatabaseException("Error al actualizar imagen: " + e.getMessage(), e);
        }
    }

    /**
     * Elimina una imagen
     */
    public void eliminarImagen(int id) throws DatabaseException {
        String sql = "DELETE FROM imagenes WHERE id = ?";
        try {
            PreparedStatement pstmt = conexion.prepareStatement(sql);
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            pstmt.close();
        } catch (SQLException e) {
            throw new DatabaseException("Error al eliminar imagen: " + e.getMessage(), e);
        }
    }

    /**
     * Elimina todas las imágenes de un vehículo
     */
    public void eliminarImagenesVehiculo(int idVehiculo) throws DatabaseException {
        String sql = "DELETE FROM imagenes WHERE id_vehiculo = ?";
        try {
            PreparedStatement pstmt = conexion.prepareStatement(sql);
            pstmt.setInt(1, idVehiculo);
            pstmt.executeUpdate();
            pstmt.close();
        } catch (SQLException e) {
            throw new DatabaseException("Error al eliminar imágenes del vehículo: " + e.getMessage(), e);
        }
    }

    /**
     * Marca una imagen como principal y desmarca las otras del mismo vehículo
     */
    public void establecerImagenPrincipal(int idImagen) throws DatabaseException {
        try {
            // Primero obtener la imagen para saber el vehículo
            Imagen imagen = obtenerImagen(idImagen);
            if (imagen == null) {
                throw new DatabaseException("Imagen no encontrada");
            }

            // Desmarcar todas las imágenes principales del vehículo
            String sqlDeselect = "UPDATE imagenes SET es_principal = false WHERE id_vehiculo = ?";
            PreparedStatement pstmtDeselect = conexion.prepareStatement(sqlDeselect);
            pstmtDeselect.setInt(1, imagen.getIdVehiculo());
            pstmtDeselect.executeUpdate();
            pstmtDeselect.close();

            // Marcar la imagen como principal
            String sqlSelect = "UPDATE imagenes SET es_principal = true WHERE id = ?";
            PreparedStatement pstmtSelect = conexion.prepareStatement(sqlSelect);
            pstmtSelect.setInt(1, idImagen);
            pstmtSelect.executeUpdate();
            pstmtSelect.close();
        } catch (SQLException e) {
            throw new DatabaseException("Error al establecer imagen principal: " + e.getMessage(), e);
        }
    }
}
