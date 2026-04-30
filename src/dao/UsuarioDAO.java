package dao;

import model.Usuario;
import exceptions.*;
import java.sql.*;
import java.util.*;

/**
 * DAO para gestionar operaciones CRUD de usuarios
 */
public class UsuarioDAO {
    private Connection conexion;

    public UsuarioDAO() throws Exception {
        this.conexion = ConexionDB.getInstance().getConexion();
    }

    /**
     * Inserta un usuario en la base de datos
     */
    public int insertarUsuario(Usuario usuario) throws DatabaseException {
        String sql = "INSERT INTO usuarios (username, password, rol, nombre_completo) VALUES (?, ?, ?, ?)";
        try {
            PreparedStatement pstmt = conexion.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, usuario.getUsername());
            pstmt.setString(2, usuario.getPassword());
            pstmt.setString(3, usuario.getRol());
            pstmt.setString(4, usuario.getNombreCompleto());
            pstmt.executeUpdate();

            ResultSet rs = pstmt.getGeneratedKeys();
            int idGenerado = 0;
            if (rs.next()) {
                idGenerado = rs.getInt(1);
            }
            pstmt.close();
            return idGenerado;
        } catch (SQLException e) {
            throw new DatabaseException("Error al insertar usuario: " + e.getMessage(), e);
        }
    }

    /**
     * Autentica un usuario (valida usuario y contraseña)
     */
    public Usuario autenticar(String username, String password) throws AuthFailedException, DatabaseException {
        String sql = "SELECT * FROM usuarios WHERE username = ? AND password = ?";
        try {
            PreparedStatement pstmt = conexion.prepareStatement(sql);
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                Usuario usuario = new Usuario(rs.getInt("id"), rs.getString("username"),
                        rs.getString("password"), rs.getString("rol"), rs.getString("nombre_completo"));
                pstmt.close();
                return usuario;
            }
            pstmt.close();
            throw new AuthFailedException("Usuario o contraseña inválidos");
        } catch (SQLException e) {
            throw new DatabaseException("Error al autenticar usuario: " + e.getMessage(), e);
        }
    }

    /**
     * Obtiene un usuario por su ID
     */
    public Usuario obtenerUsuario(int id) throws DatabaseException {
        String sql = "SELECT * FROM usuarios WHERE id = ?";
        try {
            PreparedStatement pstmt = conexion.prepareStatement(sql);
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                Usuario usuario = new Usuario(rs.getInt("id"), rs.getString("username"),
                        rs.getString("password"), rs.getString("rol"), rs.getString("nombre_completo"));
                pstmt.close();
                return usuario;
            }
            pstmt.close();
            return null;
        } catch (SQLException e) {
            throw new DatabaseException("Error al obtener usuario: " + e.getMessage(), e);
        }
    }

    /**
     * Obtiene un usuario por su nombre de usuario
     */
    public Usuario obtenerPorUsername(String username) throws DatabaseException {
        String sql = "SELECT * FROM usuarios WHERE username = ?";
        try {
            PreparedStatement pstmt = conexion.prepareStatement(sql);
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                Usuario usuario = new Usuario(rs.getInt("id"), rs.getString("username"),
                        rs.getString("password"), rs.getString("rol"), rs.getString("nombre_completo"));
                pstmt.close();
                return usuario;
            }
            pstmt.close();
            return null;
        } catch (SQLException e) {
            throw new DatabaseException("Error al obtener usuario por username: " + e.getMessage(), e);
        }
    }

    /**
     * Obtiene todos los usuarios
     */
    public List<Usuario> obtenerTodos() throws DatabaseException {
        List<Usuario> usuarios = new ArrayList<>();
        String sql = "SELECT * FROM usuarios ORDER BY nombre_completo";
        try {
            Statement stmt = conexion.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                usuarios.add(new Usuario(rs.getInt("id"), rs.getString("username"),
                        rs.getString("password"), rs.getString("rol"), rs.getString("nombre_completo")));
            }
            stmt.close();
        } catch (SQLException e) {
            throw new DatabaseException("Error al obtener usuarios: " + e.getMessage(), e);
        }
        return usuarios;
    }

    /**
     * Obtiene usuarios por rol
     */
    public List<Usuario> obtenerPorRol(String rol) throws DatabaseException {
        List<Usuario> usuarios = new ArrayList<>();
        String sql = "SELECT * FROM usuarios WHERE rol = ? ORDER BY nombre_completo";
        try {
            PreparedStatement pstmt = conexion.prepareStatement(sql);
            pstmt.setString(1, rol);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                usuarios.add(new Usuario(rs.getInt("id"), rs.getString("username"),
                        rs.getString("password"), rs.getString("rol"), rs.getString("nombre_completo")));
            }
            pstmt.close();
        } catch (SQLException e) {
            throw new DatabaseException("Error al obtener usuarios por rol: " + e.getMessage(), e);
        }
        return usuarios;
    }

    /**
     * Actualiza un usuario
     */
    public void actualizarUsuario(Usuario usuario) throws DatabaseException {
        String sql = "UPDATE usuarios SET username = ?, password = ?, rol = ?, nombre_completo = ? WHERE id = ?";
        try {
            PreparedStatement pstmt = conexion.prepareStatement(sql);
            pstmt.setString(1, usuario.getUsername());
            pstmt.setString(2, usuario.getPassword());
            pstmt.setString(3, usuario.getRol());
            pstmt.setString(4, usuario.getNombreCompleto());
            pstmt.setInt(5, usuario.getId());
            pstmt.executeUpdate();
            pstmt.close();
        } catch (SQLException e) {
            throw new DatabaseException("Error al actualizar usuario: " + e.getMessage(), e);
        }
    }

    /**
     * Elimina un usuario
     */
    public void eliminarUsuario(int id) throws DatabaseException {
        String sql = "DELETE FROM usuarios WHERE id = ?";
        try {
            PreparedStatement pstmt = conexion.prepareStatement(sql);
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            pstmt.close();
        } catch (SQLException e) {
            throw new DatabaseException("Error al eliminar usuario: " + e.getMessage(), e);
        }
    }

    /**
     * Verifica si existe un usuario con el username dado
     */
    public boolean existeUsername(String username) throws DatabaseException {
        String sql = "SELECT COUNT(*) FROM usuarios WHERE username = ?";
        try {
            PreparedStatement pstmt = conexion.prepareStatement(sql);
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            boolean existe = false;
            if (rs.next()) {
                existe = rs.getInt(1) > 0;
            }
            pstmt.close();
            return existe;
        } catch (SQLException e) {
            throw new DatabaseException("Error al verificar username: " + e.getMessage(), e);
        }
    }
}
