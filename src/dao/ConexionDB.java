package dao;

import java.sql.*;

/**
 * Clase singleton para gestionar la conexión a la base de datos H2
 */
public class ConexionDB {
    private static ConexionDB instancia;
    private Connection conexion;
    private static final String DRIVER = "org.h2.Driver";
    private static final String URL = "jdbc:h2:./data/autoelite";
    private static final String USUARIO = "sa";
    private static final String PASSWORD = "";

    private ConexionDB() throws Exception {
        try {
            Class.forName(DRIVER);
            this.conexion = DriverManager.getConnection(URL, USUARIO, PASSWORD);
            System.out.println("Conexión a H2 establecida correctamente");
            inicializarBaseDatos();
        } catch (ClassNotFoundException | SQLException e) {
            throw new Exception("Error al conectar a la base de datos: " + e.getMessage(), e);
        }
    }

    /**
     * Obtiene la instancia única de ConexionDB (patrón Singleton)
     */
    public static synchronized ConexionDB getInstance() throws Exception {
        if (instancia == null) {
            instancia = new ConexionDB();
        }
        return instancia;
    }

    /**
     * Obtiene la conexión activa
     */
    public Connection getConexion() {
        return conexion;
    }

    /**
     * Inicializa la base de datos ejecutando el script init.sql
     */
    private void inicializarBaseDatos() {
        try {
            String sqlScript = obtenerScriptSQL();
            String[] sentencias = sqlScript.split(";");
            
            for (String sentencia : sentencias) {
                sentencia = sentencia.trim();
                if (!sentencia.isEmpty()) {
                    Statement stmt = conexion.createStatement();
                    stmt.execute(sentencia);
                    stmt.close();
                }
            }
            System.out.println("Base de datos inicializada correctamente");
        } catch (SQLException e) {
            System.err.println("Error al inicializar la base de datos: " + e.getMessage());
        }
    }

    /**
     * Lee el script SQL desde el archivo init.sql
     */
    private String obtenerScriptSQL() {
        StringBuilder sql = new StringBuilder();
        
        // Script SQL para crear las tablas
        sql.append("CREATE TABLE IF NOT EXISTS sucursales (");
        sql.append("id INT PRIMARY KEY AUTO_INCREMENT,");
        sql.append("nombre VARCHAR(100) NOT NULL,");
        sql.append("ciudad VARCHAR(50) NOT NULL,");
        sql.append("direccion VARCHAR(200) NOT NULL");
        sql.append(");");

        sql.append("CREATE TABLE IF NOT EXISTS usuarios (");
        sql.append("id INT PRIMARY KEY AUTO_INCREMENT,");
        sql.append("username VARCHAR(50) UNIQUE NOT NULL,");
        sql.append("password VARCHAR(100) NOT NULL,");
        sql.append("rol VARCHAR(20) NOT NULL,");
        sql.append("nombre_completo VARCHAR(100) NOT NULL");
        sql.append(");");

        sql.append("CREATE TABLE IF NOT EXISTS vehiculos (");
        sql.append("id INT PRIMARY KEY AUTO_INCREMENT,");
        sql.append("marca VARCHAR(50) NOT NULL,");
        sql.append("modelo VARCHAR(100) NOT NULL,");
        sql.append("año INT NOT NULL,");
        sql.append("precio DOUBLE NOT NULL,");
        sql.append("id_sucursal INT NOT NULL,");
        sql.append("activo BOOLEAN DEFAULT true,");
        sql.append("tipo VARCHAR(20) NOT NULL,");
        sql.append("numero_puertas INT,");
        sql.append("tipo_combustible VARCHAR(20),");
        sql.append("es_automatico BOOLEAN,");
        sql.append("cilindrada INT,");
        sql.append("tipo_manejo VARCHAR(20),");
        sql.append("capacidad_carga DOUBLE,");
        sql.append("numero_ejes INT,");
        sql.append("FOREIGN KEY (id_sucursal) REFERENCES sucursales(id)");
        sql.append(");");

        sql.append("CREATE TABLE IF NOT EXISTS imagenes (");
        sql.append("id INT PRIMARY KEY AUTO_INCREMENT,");
        sql.append("id_vehiculo INT NOT NULL,");
        sql.append("ruta VARCHAR(200) NOT NULL,");
        sql.append("es_principal BOOLEAN DEFAULT false,");
        sql.append("FOREIGN KEY (id_vehiculo) REFERENCES vehiculos(id)");
        sql.append(");");

        return sql.toString();
    }

    /**
     * Cierra la conexión con la base de datos
     */
    public void cerrarConexion() {
        try {
            if (conexion != null && !conexion.isClosed()) {
                conexion.close();
                System.out.println("Conexión cerrada correctamente");
            }
        } catch (SQLException e) {
            System.err.println("Error al cerrar la conexión: " + e.getMessage());
        }
    }

    /**
     * Ejecuta un script SQL personalizado
     */
    public void ejecutarScript(String script) throws SQLException {
        String[] sentencias = script.split(";");
        for (String sentencia : sentencias) {
            sentencia = sentencia.trim();
            if (!sentencia.isEmpty()) {
                Statement stmt = conexion.createStatement();
                stmt.execute(sentencia);
                stmt.close();
            }
        }
    }
}
