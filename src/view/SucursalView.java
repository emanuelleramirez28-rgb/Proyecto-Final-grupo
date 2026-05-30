package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * Vista para la gestión de sucursales
 */
public class SucursalView extends JPanel {
    private JTable sucursalesTable;
    private DefaultTableModel tableModel;
    private JButton agregarButton;
    private JButton editarButton;
    private JButton eliminarButton;
    private JButton exportarButton;

    public SucursalView() {
        setLayout(new BorderLayout());

        // Panel de botones
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        agregarButton = new JButton("+ Agregar Sucursal");
        agregarButton.setBackground(new Color(0, 174, 239));
        agregarButton.setForeground(Color.WHITE);
        agregarButton.setOpaque(true);
        agregarButton.setBorderPainted(false);
        agregarButton.setFont(new Font("Arial", Font.BOLD, 12));
        buttonPanel.add(agregarButton);

        editarButton = new JButton("✎ Editar");
        editarButton.setBackground(new Color(0, 174, 239));
        editarButton.setForeground(Color.WHITE);
        editarButton.setOpaque(true);
        editarButton.setBorderPainted(false);
        editarButton.setFont(new Font("Arial", Font.BOLD, 12));
        buttonPanel.add(editarButton);

        eliminarButton = new JButton("✕ Eliminar");
        eliminarButton.setBackground(new Color(0, 174, 239));
        eliminarButton.setForeground(Color.WHITE);
        eliminarButton.setOpaque(true);
        eliminarButton.setBorderPainted(false);
        eliminarButton.setFont(new Font("Arial", Font.BOLD, 12));
        buttonPanel.add(eliminarButton);

        exportarButton = new JButton("⬇ Exportar Inventario");
        exportarButton.setBackground(new Color(0, 174, 239));
        exportarButton.setForeground(Color.WHITE);
        exportarButton.setOpaque(true);
        exportarButton.setBorderPainted(false);
        exportarButton.setFont(new Font("Arial", Font.BOLD, 12));
        buttonPanel.add(exportarButton);

        // Tabla de sucursales
        String[] columnNames = {"ID", "Nombre", "Ciudad", "Dirección", "Vehículos"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        sucursalesTable = new JTable(tableModel);
        sucursalesTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        sucursalesTable.setRowHeight(25);
        JScrollPane scrollPane = new JScrollPane(sucursalesTable);

        add(buttonPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }

    public int getSelectedSucursalId() {
        int selectedRow = sucursalesTable.getSelectedRow();
        if (selectedRow != -1) {
            return (Integer) tableModel.getValueAt(selectedRow, 0);
        }
        return -1;
    }

    public void loadSucursales(java.util.List<Object[]> sucursales) {
        tableModel.setRowCount(0);
        for (Object[] fila : sucursales) {
            tableModel.addRow(fila);
        }
    }

    public void addAgregarButtonListener(ActionListener listener) {
        agregarButton.addActionListener(listener);
    }

    public void addEditarButtonListener(ActionListener listener) {
        editarButton.addActionListener(listener);
    }

    public void addEliminarButtonListener(ActionListener listener) {
        eliminarButton.addActionListener(listener);
    }

    public void addExportarButtonListener(ActionListener listener) {
        exportarButton.addActionListener(listener);
    }

    public void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public void mostrarExito(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Éxito", JOptionPane.INFORMATION_MESSAGE);
    }
}
