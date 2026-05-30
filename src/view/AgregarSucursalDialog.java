package view;

import model.Sucursal;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Diálogo para agregar o editar una sucursal
 */
public class AgregarSucursalDialog extends JDialog {
    private JTextField nombreField;
    private JTextField ciudadField;
    private JTextField direccionField;
    private JButton aceptarButton;
    private JButton cancelarButton;
    private Sucursal sucursal;
    private boolean aceptado = false;

    public AgregarSucursalDialog(Frame owner) {
        this(owner, null);
    }

    public AgregarSucursalDialog(Frame owner, Sucursal sucursal) {
        super(owner, true);
        this.sucursal = sucursal;
        
        if (sucursal != null) {
            setTitle("Editar Sucursal");
        } else {
            setTitle("Agregar Sucursal");
        }
        
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setSize(400, 250);
        setLocationRelativeTo(owner);
        
        // Panel principal
        JPanel mainPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        // Nombre
        mainPanel.add(new JLabel("Nombre:"));
        nombreField = new JTextField();
        if (sucursal != null) {
            nombreField.setText(sucursal.getNombre());
        }
        mainPanel.add(nombreField);
        
        // Ciudad
        mainPanel.add(new JLabel("Ciudad:"));
        ciudadField = new JTextField();
        if (sucursal != null) {
            ciudadField.setText(sucursal.getCiudad());
        }
        mainPanel.add(ciudadField);
        
        // Dirección
        mainPanel.add(new JLabel("Dirección:"));
        direccionField = new JTextField();
        if (sucursal != null) {
            direccionField.setText(sucursal.getDireccion());
        }
        mainPanel.add(direccionField);
        
        // Botones
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        
        aceptarButton = new JButton("Aceptar");
        aceptarButton.setBackground(new Color(0, 174, 239));
        aceptarButton.setForeground(Color.WHITE);
        aceptarButton.setOpaque(true);
        aceptarButton.setBorderPainted(false);
        aceptarButton.setFont(new Font("Arial", Font.BOLD, 12));
        aceptarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (validarCampos()) {
                    aceptado = true;
                    dispose();
                }
            }
        });
        
        cancelarButton = new JButton("Cancelar");
        cancelarButton.setBackground(new Color(0, 174, 239));
        cancelarButton.setForeground(Color.WHITE);
        cancelarButton.setOpaque(true);
        cancelarButton.setBorderPainted(false);
        cancelarButton.setFont(new Font("Arial", Font.BOLD, 12));
        cancelarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                aceptado = false;
                dispose();
            }
        });
        
        buttonPanel.add(aceptarButton);
        buttonPanel.add(cancelarButton);
        
        add(mainPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private boolean validarCampos() {
        if (nombreField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "El nombre es requerido", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (ciudadField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "La ciudad es requerida", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (direccionField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "La dirección es requerida", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    public Sucursal mostrarDialogo() {
        setVisible(true);
        
        if (aceptado) {
            if (sucursal == null) {
                sucursal = new Sucursal(
                    nombreField.getText(),
                    ciudadField.getText(),
                    direccionField.getText()
                );
            } else {
                sucursal.setNombre(nombreField.getText());
                sucursal.setCiudad(ciudadField.getText());
                sucursal.setDireccion(direccionField.getText());
            }
            return sucursal;
        }
        
        return null;
    }
}
