package dao;

import model.*;
import exceptions.*;
import java.sql.*;
import java.util.*;

/**
 * DAO para gestionar operaciones CRUD de vehículos
 */
public class VehiculoDAO {
    private Connection conexion;

    public VehiculoDAO() throws Exception {
        this.conexion = ConexionDB.getInstance().getConexion();
    }

    /**
     * Inserta un vehículo en la base de datos
     */
    public int insertarVehiculo(Vehiculo vehiculo) throws DatabaseException, InvalidPriceException {
        if (vehiculo.getPrecio() < 0) {
            throw new InvalidPriceException("El precio del vehículo no puede ser negativo");
        }

        String tipo = vehiculo.obtenerTipo();
        String sql = "INSERT INTO vehiculos (marca, modelo, año, precio, id_sucursal, activo, tipo";

        if (vehiculo instanceof Auto) {
            Auto auto = (Auto) vehiculo;
            sql += ", numero_puertas, tipo_combustible, es_automatico) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        } else if (vehiculo instanceof Motocicleta) {
            Motocicleta moto = (Motocicleta) vehiculo;
            sql += ", cilindrada, tipo_manejo) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        } else if (vehiculo instanceof Camion) {
            Camion camion = (Camion) vehiculo;
            sql += ", capacidad_carga, numero_ejes) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        } else {
            throw new DatabaseException("Tipo de vehículo desconocido");
        }

        try {
            PreparedStatement pstmt = conexion.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, vehiculo.getMarca());
            pstmt.setString(2, vehiculo.getModelo());
            pstmt.setInt(3, vehiculo.getAño());
            pstmt.setDouble(4, vehiculo.getPrecio());
            pstmt.setInt(5, vehiculo.getIdSucursal());
            pstmt.setBoolean(6, vehiculo.isActivo());
            pstmt.setString(7, tipo);

            if (vehiculo instanceof Auto) {
                Auto auto = (Auto) vehiculo;
                pstmt.setInt(8, auto.getNumeroPuertas());
                pstmt.setString(9, auto.getTipoCombustible());
                pstmt.setBoolean(10, auto.isEsAutomatico());
            } else if (vehiculo instanceof Motocicleta) {
                Motocicleta moto = (Motocicleta) vehiculo;
                pstmt.setInt(8, moto.getCilindrada());
                pstmt.setString(9, moto.getTipoManejo());
            } else if (vehiculo instanceof Camion) {
                Camion camion = (Camion) vehiculo;
                pstmt.setDouble(8, camion.getCapacidadCarga());
                pstmt.setInt(9, camion.getNumeroEjes());
            }

            pstmt.executeUpdate();
            ResultSet rs = pstmt.getGeneratedKeys();
            int idGenerado = 0;
            if (rs.next()) {
                idGenerado = rs.getInt(1);
            }
            pstmt.close();
            return idGenerado;
        } catch (SQLException e) {
            throw new DatabaseException("Error al insertar vehículo: " + e.getMessage(), e);
        }
    }

    /**
     * Obtiene un vehículo por su ID
     */
    public Vehiculo obtenerVehiculo(int id) throws DatabaseException {
        String sql = "SELECT * FROM vehiculos WHERE id = ?";
        try {
            PreparedStatement pstmt = conexion.prepareStatement(sql);
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return construirVehiculo(rs);
            }
            pstmt.close();
            return null;
        } catch (SQLException e) {
            throw new DatabaseException("Error al obtener vehículo: " + e.getMessage(), e);
        }
    }

    /**
     * Obtiene todos los vehículos activos
     */
    public List<Vehiculo> obtenerTodos() throws DatabaseException {
        List<Vehiculo> vehiculos = new ArrayList<>();
        String sql = "SELECT * FROM vehiculos WHERE activo = true ORDER BY marca, modelo";
        try {
            Statement stmt = conexion.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                vehiculos.add(construirVehiculo(rs));
            }
            stmt.close();
        } catch (SQLException e) {
            throw new DatabaseException("Error al obtener vehículos: " + e.getMessage(), e);
        }
        return vehiculos;
    }

    /**
     * Obtiene vehículos por sucursal
     */
    public List<Vehiculo> obtenerPorSucursal(int idSucursal) throws DatabaseException {
        List<Vehiculo> vehiculos = new ArrayList<>();
        String sql = "SELECT * FROM vehiculos WHERE id_sucursal = ? AND activo = true ORDER BY marca, modelo";
        try {
            PreparedStatement pstmt = conexion.prepareStatement(sql);
            pstmt.setInt(1, idSucursal);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                vehiculos.add(construirVehiculo(rs));
            }
            pstmt.close();
        } catch (SQLException e) {
            throw new DatabaseException("Error al obtener vehículos por sucursal: " + e.getMessage(), e);
        }
        return vehiculos;
    }

    /**
     * Obtiene vehículos por tipo
     */
    public List<Vehiculo> obtenerPorTipo(String tipo) throws DatabaseException {
        List<Vehiculo> vehiculos = new ArrayList<>();
        String sql = "SELECT * FROM vehiculos WHERE tipo = ? AND activo = true ORDER BY marca, modelo";
        try {
            PreparedStatement pstmt = conexion.prepareStatement(sql);
            pstmt.setString(1, tipo);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                vehiculos.add(construirVehiculo(rs));
            }
            pstmt.close();
        } catch (SQLException e) {
            throw new DatabaseException("Error al obtener vehículos por tipo: " + e.getMessage(), e);
        }
        return vehiculos;
    }

    /**
     * Busca vehículos por marca o modelo
     */
    public List<Vehiculo> buscar(String termino) throws DatabaseException {
        List<Vehiculo> vehiculos = new ArrayList<>();
        String sql = "SELECT * FROM vehiculos WHERE activo = true AND (marca LIKE ? OR modelo LIKE ?) ORDER BY marca, modelo";
        try {
            PreparedStatement pstmt = conexion.prepareStatement(sql);
            String terminiConWildcard = "%" + termino + "%";
            pstmt.setString(1, terminiConWildcard);
            pstmt.setString(2, terminiConWildcard);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                vehiculos.add(construirVehiculo(rs));
            }
            pstmt.close();
        } catch (SQLException e) {
            throw new DatabaseException("Error al buscar vehículos: " + e.getMessage(), e);
        }
        return vehiculos;
    }

    /**
     * Obtiene vehículos dentro de un rango de precios
     */
    public List<Vehiculo> obtenerPorRangoPrecios(double precioMin, double precioMax) throws DatabaseException {
        List<Vehiculo> vehiculos = new ArrayList<>();
        String sql = "SELECT * FROM vehiculos WHERE activo = true AND precio >= ? AND precio <= ? ORDER BY precio";
        try {
            PreparedStatement pstmt = conexion.prepareStatement(sql);
            pstmt.setDouble(1, precioMin);
            pstmt.setDouble(2, precioMax);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                vehiculos.add(construirVehiculo(rs));
            }
            pstmt.close();
        } catch (SQLException e) {
            throw new DatabaseException("Error al obtener vehículos por rango de precios: " + e.getMessage(), e);
        }
        return vehiculos;
    }

    /**
     * Actualiza un vehículo
     */
    public void actualizarVehiculo(Vehiculo vehiculo) throws DatabaseException, InvalidPriceException {
        if (vehiculo.getPrecio() < 0) {
            throw new InvalidPriceException("El precio del vehículo no puede ser negativo");
        }

        String sql = "UPDATE vehiculos SET marca = ?, modelo = ?, año = ?, precio = ?, id_sucursal = ?, activo = ?";
        
        if (vehiculo instanceof Auto) {
            sql += ", numero_puertas = ?, tipo_combustible = ?, es_automatico = ?";
        } else if (vehiculo instanceof Motocicleta) {
            sql += ", cilindrada = ?, tipo_manejo = ?";
        } else if (vehiculo instanceof Camion) {
            sql += ", capacidad_carga = ?, numero_ejes = ?";
        }
        
        sql += " WHERE id = ?";

        try {
            PreparedStatement pstmt = conexion.prepareStatement(sql);
            pstmt.setString(1, vehiculo.getMarca());
            pstmt.setString(2, vehiculo.getModelo());
            pstmt.setInt(3, vehiculo.getAño());
            pstmt.setDouble(4, vehiculo.getPrecio());
            pstmt.setInt(5, vehiculo.getIdSucursal());
            pstmt.setBoolean(6, vehiculo.isActivo());

            int paramIndex = 7;
            if (vehiculo instanceof Auto) {
                Auto auto = (Auto) vehiculo;
                pstmt.setInt(paramIndex++, auto.getNumeroPuertas());
                pstmt.setString(paramIndex++, auto.getTipoCombustible());
                pstmt.setBoolean(paramIndex++, auto.isEsAutomatico());
            } else if (vehiculo instanceof Motocicleta) {
                Motocicleta moto = (Motocicleta) vehiculo;
                pstmt.setInt(paramIndex++, moto.getCilindrada());
                pstmt.setString(paramIndex++, moto.getTipoManejo());
            } else if (vehiculo instanceof Camion) {
                Camion camion = (Camion) vehiculo;
                pstmt.setDouble(paramIndex++, camion.getCapacidadCarga());
                pstmt.setInt(paramIndex++, camion.getNumeroEjes());
            }
            
            pstmt.setInt(paramIndex, vehiculo.getId());
            pstmt.executeUpdate();
            pstmt.close();
        } catch (SQLException e) {
            throw new DatabaseException("Error al actualizar vehículo: " + e.getMessage(), e);
        }
    }

    /**
     * Realiza una baja lógica de un vehículo
     */
    public void darDeBaja(int id) throws DatabaseException {
        String sql = "UPDATE vehiculos SET activo = false WHERE id = ?";
        try {
            PreparedStatement pstmt = conexion.prepareStatement(sql);
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            pstmt.close();
        } catch (SQLException e) {
            throw new DatabaseException("Error al dar de baja el vehículo: " + e.getMessage(), e);
        }
    }

    /**
     * Construye un objeto Vehiculo a partir de un ResultSet
     */
    private Vehiculo construirVehiculo(ResultSet rs) throws SQLException {
        String tipo = rs.getString("tipo");
        int id = rs.getInt("id");
        String marca = rs.getString("marca");
        String modelo = rs.getString("modelo");
        int año = rs.getInt("año");
        double precio = rs.getDouble("precio");
        int idSucursal = rs.getInt("id_sucursal");
        boolean activo = rs.getBoolean("activo");

        switch (tipo) {
            case "Auto":
                int numeroPuertas = rs.getInt("numero_puertas");
                String tipoCombustible = rs.getString("tipo_combustible");
                boolean esAutomatico = rs.getBoolean("es_automatico");
                return new Auto(id, marca, modelo, año, precio, idSucursal, activo, numeroPuertas, tipoCombustible, esAutomatico);

            case "Motocicleta":
                int cilindrada = rs.getInt("cilindrada");
                String tipoManejo = rs.getString("tipo_manejo");
                return new Motocicleta(id, marca, modelo, año, precio, idSucursal, activo, cilindrada, tipoManejo);

            case "Camion":
                double capacidadCarga = rs.getDouble("capacidad_carga");
                int numeroEjes = rs.getInt("numero_ejes");
                return new Camion(id, marca, modelo, año, precio, idSucursal, activo, capacidadCarga, numeroEjes);

            default:
                throw new SQLException("Tipo de vehículo desconocido: " + tipo);
        }
    }
}
