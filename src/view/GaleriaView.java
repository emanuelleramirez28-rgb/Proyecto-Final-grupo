package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * Vista para la galería de imágenes de vehículos
 */
public class GaleriaView extends JDialog {
    private JLabel imagenLabel;
    private JButton anteriorButton;
    private JButton siguienteButton;
    private JButton agregarButton;
    private JButton eliminarButton;
    private JButton establecerPrincipalButton;
    private JLabel infoLabel;

    public GaleriaView(JFrame parent, String titulo) {
        super(parent, titulo, true);
        setSize(600, 500);
        setLocationRelativeTo(parent);

        // Panel central con la imagen
        imagenLabel = new JLabel();
        imagenLabel.setHorizontalAlignment(SwingConstants.CENTER);
        imagenLabel.setBackground(Color.BLACK);
        imagenLabel.setOpaque(true);
        imagenLabel.setPreferredSize(new Dimension(500, 350));

        // Panel inferior con botones
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        anteriorButton = new JButton("← Anterior");
        anteriorButton.setBackground(new Color(0, 102, 204));
        anteriorButton.setForeground(Color.WHITE);
        buttonPanel.add(anteriorButton);

        siguienteButton = new JButton("Siguiente →");
        siguienteButton.setBackground(new Color(0, 102, 204));
        siguienteButton.setForeground(Color.WHITE);
        buttonPanel.add(siguienteButton);

        buttonPanel.add(new JSeparator(SwingConstants.VERTICAL));

        agregarButton = new JButton("+ Agregar Imagen");
        agregarButton.setBackground(new Color(0, 153, 0));
        agregarButton.setForeground(Color.WHITE);
        buttonPanel.add(agregarButton);

        establecerPrincipalButton = new JButton("✓ Principal");
        establecerPrincipalButton.setBackground(new Color(255, 153, 0));
        establecerPrincipalButton.setForeground(Color.WHITE);
        buttonPanel.add(establecerPrincipalButton);

        eliminarButton = new JButton("✕ Eliminar");
        eliminarButton.setBackground(new Color(204, 0, 0));
        eliminarButton.setForeground(Color.WHITE);
        buttonPanel.add(eliminarButton);

        // Panel de información
        infoLabel = new JLabel("Imagen 1 de 0");
        infoLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        infoLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        mainPanel.add(imagenLabel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        mainPanel.add(infoLabel, BorderLayout.NORTH);

        add(mainPanel);
    }

    public void mostrarImagen(ImageIcon imagen) {
        if (imagen != null) {
            Image scaledImage = imagen.getImage().getScaledInstance(500, 350, Image.SCALE_SMOOTH);
            imagenLabel.setIcon(new ImageIcon(scaledImage));
        } else {
            imagenLabel.setIcon(null);
            imagenLabel.setText("Sin imagen");
        }
    }

    public void setInfoLabel(String texto) {
        infoLabel.setText(texto);
    }

    public void addAnteriorButtonListener(ActionListener listener) {
        anteriorButton.addActionListener(listener);
    }

    public void addSiguienteButtonListener(ActionListener listener) {
        siguienteButton.addActionListener(listener);
    }

    public void addAgregarButtonListener(ActionListener listener) {
        agregarButton.addActionListener(listener);
    }

    public void addEliminarButtonListener(ActionListener listener) {
        eliminarButton.addActionListener(listener);
    }

    public void addEstablecerPrincipalButtonListener(ActionListener listener) {
        establecerPrincipalButton.addActionListener(listener);
    }

    public void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public void mostrarExito(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Éxito", JOptionPane.INFORMATION_MESSAGE);
    }
}
