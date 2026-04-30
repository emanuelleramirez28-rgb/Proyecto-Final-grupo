package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * Vista para la gestión del inventario de vehículos
 */
public class InventarioView extends JPanel {
    private JTextField searchField;
    private JComboBox<String> sucursalCombo;
    private JComboBox<String> tipoVehiculoCombo;
    private JSpinner precioMinSpinner;
    private JSpinner precioMaxSpinner;
    private JButton searchButton;
    private JButton agregarButton;
    private JButton editarButton;
    private JButton eliminarButton;
    private JButton galeriaButton;
    private JTable vehiculosTable;
    private DefaultTableModel tableModel;

    public InventarioView() {
        setLayout(new BorderLayout());

        // Panel de filtros
        JPanel filterPanel = new JPanel(new GridBagLayout());
        filterPanel.setBorder(BorderFactory.createTitledBorder("Filtros de Búsqueda"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Buscar por marca/modelo
        JLabel searchLabel = new JLabel("Marca/Modelo:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        filterPanel.add(searchLabel, gbc);

        searchField = new JTextField(15);
        gbc.gridx = 1;
        filterPanel.add(searchField, gbc);

        // Sucursal
        JLabel sucursalLabel = new JLabel("Sucursal:");
        gbc.gridx = 2;
        filterPanel.add(sucursalLabel, gbc);

        sucursalCombo = new JComboBox<>();
        sucursalCombo.addItem("Todas");
        gbc.gridx = 3;
        filterPanel.add(sucursalCombo, gbc);

        // Tipo de vehículo
        JLabel tipoLabel = new JLabel("Tipo:");
        gbc.gridx = 4;
        filterPanel.add(tipoLabel, gbc);

        tipoVehiculoCombo = new JComboBox<>();
        tipoVehiculoCombo.addItem("Todos");
        tipoVehiculoCombo.addItem("Auto");
        tipoVehiculoCombo.addItem("Motocicleta");
        tipoVehiculoCombo.addItem("Camion");
        gbc.gridx = 5;
        filterPanel.add(tipoVehiculoCombo, gbc);

        // Rango de precios
        JLabel precioLabel = new JLabel("Precio:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        filterPanel.add(precioLabel, gbc);

        precioMinSpinner = new JSpinner(new SpinnerNumberModel(0.0, 0.0, 1000000.0, 1000.0));
        gbc.gridx = 1;
        filterPanel.add(precioMinSpinner, gbc);

        JLabel precioALabel = new JLabel("a");
        gbc.gridx = 2;
        filterPanel.add(precioALabel, gbc);

        precioMaxSpinner = new JSpinner(new SpinnerNumberModel(1000000.0, 0.0, 1000000.0, 1000.0));
        gbc.gridx = 3;
        filterPanel.add(precioMaxSpinner, gbc);

        // Botón de búsqueda
        searchButton = new JButton("Buscar");
        searchButton.setBackground(new Color(0, 153, 0));
        searchButton.setForeground(Color.WHITE);
        gbc.gridx = 5;
        gbc.gridy = 1;
        filterPanel.add(searchButton, gbc);

        // Panel de botones de acción
        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        agregarButton = new JButton("+ Agregar");
        agregarButton.setBackground(new Color(0, 102, 204));
        agregarButton.setForeground(Color.WHITE);
        actionPanel.add(agregarButton);

        editarButton = new JButton("✎ Editar");
        editarButton.setBackground(new Color(255, 153, 0));
        editarButton.setForeground(Color.WHITE);
        actionPanel.add(editarButton);

        eliminarButton = new JButton("✕ Eliminar");
        eliminarButton.setBackground(new Color(204, 0, 0));
        eliminarButton.setForeground(Color.WHITE);
        actionPanel.add(eliminarButton);

        galeriaButton = new JButton("📷 Galería");
        galeriaButton.setBackground(new Color(102, 102, 204));
        galeriaButton.setForeground(Color.WHITE);
        actionPanel.add(galeriaButton);

        // Tabla de vehículos
        String[] columnNames = {"ID", "Marca", "Modelo", "Año", "Precio", "Tipo", "Sucursal"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        vehiculosTable = new JTable(tableModel);
        vehiculosTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        vehiculosTable.setRowHeight(25);
        JScrollPane scrollPane = new JScrollPane(vehiculosTable);

        // Agregar componentes
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(filterPanel, BorderLayout.NORTH);
        topPanel.add(actionPanel, BorderLayout.SOUTH);

        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }

    // Getters para campos
    public String getSearchText() {
        return searchField.getText();
    }

    public String getSelectedSucursal() {
        return (String) sucursalCombo.getSelectedItem();
    }

    public String getSelectedTipoVehiculo() {
        return (String) tipoVehiculoCombo.getSelectedItem();
    }

    public double getPrecioMin() {
        return (Double) precioMinSpinner.getValue();
    }

    public double getPrecioMax() {
        return (Double) precioMaxSpinner.getValue();
    }

    public int getSelectedVehiculoId() {
        int selectedRow = vehiculosTable.getSelectedRow();
        if (selectedRow != -1) {
            return (Integer) tableModel.getValueAt(selectedRow, 0);
        }
        return -1;
    }

    public void loadSucursales(java.util.List<String> sucursales) {
        sucursalCombo.removeAllItems();
        sucursalCombo.addItem("Todas");
        for (String sucursal : sucursales) {
            sucursalCombo.addItem(sucursal);
        }
    }

    public void loadVehiculos(java.util.List<Object[]> vehiculos) {
        tableModel.setRowCount(0);
        for (Object[] fila : vehiculos) {
            tableModel.addRow(fila);
        }
    }

    public void addSearchButtonListener(ActionListener listener) {
        searchButton.addActionListener(listener);
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

    public void addGaleriaButtonListener(ActionListener listener) {
        galeriaButton.addActionListener(listener);
    }

    public void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public void mostrarExito(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Éxito", JOptionPane.INFORMATION_MESSAGE);
    }
}
