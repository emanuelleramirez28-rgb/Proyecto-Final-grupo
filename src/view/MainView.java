package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * Vista principal del sistema AutoElite
 */
public class MainView extends JFrame {
    private JTabbedPane tabbedPane;
    private JLabel usuarioLabel;
    private JButton logoutButton;
    private InventarioView inventarioView;
    private DashboardView dashboardView;
    private SucursalView sucursalView;

    public MainView(String nombreUsuario, String rol) {
        setTitle("AutoElite - Sistema de Concesionarios");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 700);
        setLocationRelativeTo(null);

        // Panel superior con info del usuario
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(new Color(0, 102, 204));

        JLabel welcomeLabel = new JLabel("Bienvenido, " + nombreUsuario);
        welcomeLabel.setForeground(Color.WHITE);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 14));
        welcomeLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        topPanel.add(welcomeLabel, BorderLayout.WEST);

        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        rightPanel.setBackground(new Color(0, 102, 204));

        usuarioLabel = new JLabel("Rol: " + rol);
        usuarioLabel.setForeground(Color.WHITE);
        usuarioLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        rightPanel.add(usuarioLabel);

        logoutButton = new JButton("Cerrar Sesión");
        logoutButton.setBackground(new Color(204, 0, 0));
        logoutButton.setForeground(Color.WHITE);
        topPanel.add(rightPanel, BorderLayout.EAST);

        // Panel con pestañas
        tabbedPane = new JTabbedPane();

        // Vistas para ADMIN y VENDEDOR
        if (rol.equals("ADMIN")) {
            inventarioView = new InventarioView();
            dashboardView = new DashboardView();
            sucursalView = new SucursalView();

            tabbedPane.addTab("Dashboard", dashboardView);
            tabbedPane.addTab("Inventario", inventarioView);
            tabbedPane.addTab("Sucursales", sucursalView);
        } else if (rol.equals("VENDEDOR")) {
            inventarioView = new InventarioView();
            dashboardView = new DashboardView();
            tabbedPane.addTab("Dashboard", dashboardView);
            tabbedPane.addTab("Inventario (Ver)", inventarioView);
        }

        add(topPanel, BorderLayout.NORTH);
        add(tabbedPane, BorderLayout.CENTER);
    }

    public InventarioView getInventarioView() {
        return inventarioView;
    }

    public DashboardView getDashboardView() {
        return dashboardView;
    }

    public SucursalView getSucursalView() {
        return sucursalView;
    }

    public void addLogoutButtonListener(ActionListener listener) {
        logoutButton.addActionListener(listener);
    }

    public void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public void mostrarExito(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Éxito", JOptionPane.INFORMATION_MESSAGE);
    }
}
