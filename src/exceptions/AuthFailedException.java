package exceptions;

/**
 * Excepción personalizada para errores de autenticación
 */
public class AuthFailedException extends Exception {
    public AuthFailedException(String message) {
        super(message);
    }

    public AuthFailedException(String message, Throwable cause) {
        super(message, cause);
    }
}
