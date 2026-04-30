import view.LoginView;
import view.MainView;
import controller.LoginController;
import controller.MainController;
import model.Usuario;
import dao.UsuarioDAO;
import exceptions.AuthFailedException;
import exceptions.DatabaseException;

import javax.swing.*;

/**
 * Clase principal de la aplicación AutoElite
 * Punto de entrada: java Main
 */
public class Main {
    private static LoginView loginView;
    private static MainView mainView;
    private static Usuario usuarioActual;

    public static void main(String[] args) {
        // Configurar look and feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Mostrar pantalla de login
        mostrarLogin();
    }

    /**
     * Muestra la pantalla de login
     */
    private static void mostrarLogin() {
        loginView = new LoginView();
        try {
            LoginController loginController = new LoginController(loginView) {
                @Override
                public void autenticar() {
                    try {
                        String username = loginView.getUsuario();
                        String password = loginView.getPassword();

                        if (username.isEmpty() || password.isEmpty()) {
                            loginView.mostrarError("Por favor complete todos los campos");
                            return;
                        }

                        UsuarioDAO usuarioDAO = new UsuarioDAO();
                        usuarioActual = usuarioDAO.autenticar(username, password);

                        if (usuarioActual != null) {
                            loginView.mostrarExito("¡Autenticación exitosa!");
                            loginView.setVisible(false);
                            mostrarMainView();
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
            };
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, 
                "Error al inicializar: " + e.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
        }

        loginView.setVisible(true);
    }

    /**
     * Muestra la vista principal después de autenticación exitosa
     */
    private static void mostrarMainView() {
        try {
            mainView = new MainView(usuarioActual.getNombreCompleto(), usuarioActual.getRol());
            MainController mainController = new MainController(mainView, usuarioActual);

            mainView.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosed(java.awt.event.WindowEvent e) {
                    System.exit(0);
                }
            });

            mainView.setVisible(true);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,
                "Error al abrir aplicación principal: " + e.getMessage(),
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
