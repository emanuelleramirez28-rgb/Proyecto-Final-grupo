package controller;

import view.LoginView;
import model.Usuario;
import dao.UsuarioDAO;
import exceptions.AuthFailedException;
import exceptions.DatabaseException;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Controlador para la autenticación de usuarios
 */
public class LoginController {
    private LoginView loginView;
    private UsuarioDAO usuarioDAO;

    public LoginController(LoginView loginView) throws Exception {
        this.loginView = loginView;
        this.usuarioDAO = new UsuarioDAO();

        // Agregar listener al botón de login
        this.loginView.addLoginButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                autenticar();
            }
        });
    }

    /**
     * Realiza la autenticación del usuario
     */
    public void autenticar() {
        try {
            String username = loginView.getUsuario();
            String password = loginView.getPassword();

            // Validaciones
            if (username.isEmpty() || password.isEmpty()) {
                loginView.mostrarError("Por favor complete todos los campos");
                return;
            }

            // Intentar autenticar
            Usuario usuario = usuarioDAO.autenticar(username, password);

            if (usuario != null) {
                loginView.mostrarExito("¡Autenticación exitosa!");
                loginView.setVisible(false);
                // Aquí se abrirá la MainView (se maneja en Main.java)
            } else {
                loginView.setErrorMessage("Usuario o contraseña inválidos");
            }
        } catch (AuthFailedException e) {
            loginView.mostrarError(e.getMessage());
        } catch (DatabaseException e) {
            loginView.mostrarError("Error de base de datos: " + e.getMessage());
        } catch (Exception e) {
            loginView.mostrarError("Error: " + e.getMessage());
        }
    }

    /**
     * Obtiene la vista de login
     */
    public LoginView getLoginView() {
        return loginView;
    }
}
