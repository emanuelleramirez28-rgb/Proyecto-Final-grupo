package dao;

import model.Sucursal;
import exceptions.DatabaseException;
import java.sql.*;
import java.util.*;

/**
 * DAO para gestionar operaciones CRUD de sucursales
 */
public class SucursalDAO {
    private Connection conexion;

    public SucursalDAO() throws Exception {
        this.conexion = ConexionDB.getInstance().getConexion();
    }

    /**
     * Inserta una sucursal en la base de datos
     */
    public int insertarSucursal(Sucursal sucursal) throws DatabaseException {
        String sql = "INSERT INTO sucursales (nombre, ciudad, direccion) VALUES (?, ?, ?)";
        try {
            PreparedStatement pstmt = conexion.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, sucursal.getNombre());
            pstmt.setString(2, sucursal.getCiudad());
            pstmt.setString(3, sucursal.getDireccion());
            pstmt.executeUpdate();

            ResultSet rs = pstmt.getGeneratedKeys();
            int idGenerado = 0;
            if (rs.next()) {
                idGenerado = rs.getInt(1);
            }
            pstmt.close();
            return idGenerado;
        } catch (SQLException e) {
            throw new DatabaseException("Error al insertar sucursal: " + e.getMessage(), e);
        }
    }

    /**
     * Obtiene una sucursal por su ID
     */
    public Sucursal obtenerSucursal(int id) throws DatabaseException {
        String sql = "SELECT * FROM sucursales WHERE id = ?";
        try {
            PreparedStatement pstmt = conexion.prepareStatement(sql);
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                Sucursal sucursal = new Sucursal(rs.getInt("id"), rs.getString("nombre"),
                        rs.getString("ciudad"), rs.getString("direccion"));
                pstmt.close();
                return sucursal;
            }
            pstmt.close();
            return null;
        } catch (SQLException e) {
            throw new DatabaseException("Error al obtener sucursal: " + e.getMessage(), e);
        }
    }

    /**
     * Obtiene todas las sucursales
     */
    public List<Sucursal> obtenerTodas() throws DatabaseException {
        List<Sucursal> sucursales = new ArrayList<>();
        String sql = "SELECT * FROM sucursales ORDER BY nombre";
        try {
            Statement stmt = conexion.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                sucursales.add(new Sucursal(rs.getInt("id"), rs.getString("nombre"),
                        rs.getString("ciudad"), rs.getString("direccion")));
            }
            stmt.close();
        } catch (SQLException e) {
            throw new DatabaseException("Error al obtener sucursales: " + e.getMessage(), e);
        }
        return sucursales;
    }

    /**
     * Obtiene sucursales por ciudad
     */
    public List<Sucursal> obtenerPorCiudad(String ciudad) throws DatabaseException {
        List<Sucursal> sucursales = new ArrayList<>();
        String sql = "SELECT * FROM sucursales WHERE ciudad = ? ORDER BY nombre";
        try {
            PreparedStatement pstmt = conexion.prepareStatement(sql);
            pstmt.setString(1, ciudad);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                sucursales.add(new Sucursal(rs.getInt("id"), rs.getString("nombre"),
                        rs.getString("ciudad"), rs.getString("direccion")));
            }
            pstmt.close();
        } catch (SQLException e) {
            throw new DatabaseException("Error al obtener sucursales por ciudad: " + e.getMessage(), e);
        }
        return sucursales;
    }

    /**
     * Actualiza una sucursal
     */
    public void actualizarSucursal(Sucursal sucursal) throws DatabaseException {
        String sql = "UPDATE sucursales SET nombre = ?, ciudad = ?, direccion = ? WHERE id = ?";
        try {
            PreparedStatement pstmt = conexion.prepareStatement(sql);
            pstmt.setString(1, sucursal.getNombre());
            pstmt.setString(2, sucursal.getCiudad());
            pstmt.setString(3, sucursal.getDireccion());
            pstmt.setInt(4, sucursal.getId());
            pstmt.executeUpdate();
            pstmt.close();
        } catch (SQLException e) {
            throw new DatabaseException("Error al actualizar sucursal: " + e.getMessage(), e);
        }
    }

    /**
     * Elimina una sucursal
     */
    public void eliminarSucursal(int id) throws DatabaseException {
        String sql = "DELETE FROM sucursales WHERE id = ?";
        try {
            PreparedStatement pstmt = conexion.prepareStatement(sql);
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            pstmt.close();
        } catch (SQLException e) {
            throw new DatabaseException("Error al eliminar sucursal: " + e.getMessage(), e);
        }
    }

    /**
     * Obtiene el número de vehículos en una sucursal
     */
    public int contarVehiculos(int idSucursal) throws DatabaseException {
        String sql = "SELECT COUNT(*) FROM vehiculos WHERE id_sucursal = ? AND activo = true";
        try {
            PreparedStatement pstmt = conexion.prepareStatement(sql);
            pstmt.setInt(1, idSucursal);
            ResultSet rs = pstmt.executeQuery();
            int cantidad = 0;
            if (rs.next()) {
                cantidad = rs.getInt(1);
            }
            pstmt.close();
            return cantidad;
        } catch (SQLException e) {
            throw new DatabaseException("Error al contar vehículos: " + e.getMessage(), e);
        }
    }
}
