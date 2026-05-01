package view;

import model.Camion;
import model.Motocicleta;
import model.Sucursal;
import model.Auto;
import model.Vehiculo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class AgregarVehiculoDialog extends JDialog {
    private JComboBox<String> tipoCombo;
    private JTextField marcaField;
    private JTextField modeloField;
    private JSpinner añoSpinner;
    private JSpinner precioSpinner;
    private JComboBox<String> sucursalCombo;
    private JPanel detallesPanel;
    private CardLayout detallesLayout;

    private JSpinner numeroPuertasSpinner;
    private JComboBox<String> tipoCombustibleCombo;
    private JCheckBox automaticoCheck;

    private JSpinner cilindradaSpinner;
    private JComboBox<String> tipoManejoCombo;

    private JSpinner capacidadCargaSpinner;
    private JSpinner numeroEjesSpinner;

    private List<Sucursal> sucursales;
    private Vehiculo vehiculoExistente;
    private Vehiculo vehiculoCreado;

    public AgregarVehiculoDialog(Frame owner, List<Sucursal> sucursales) {
        this(owner, sucursales, null);
    }

    public AgregarVehiculoDialog(Frame owner, List<Sucursal> sucursales, Vehiculo vehiculoExistente) {
        super(owner, vehiculoExistente == null ? "Agregar Vehículo" : "Editar Vehículo", true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(520, 420);
        setLocationRelativeTo(owner);
        this.sucursales = sucursales;
        this.vehiculoExistente = vehiculoExistente;

        construirUI(sucursales);
    }

    private void construirUI(List<Sucursal> sucursales) {
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));

        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 6, 6, 6);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        tipoCombo = new JComboBox<>(new String[]{"Auto", "Motocicleta", "Camion"});
        tipoCombo.addActionListener(e -> actualizarPanelDetalles());
        marcaField = new JTextField(15);
        modeloField = new JTextField(15);
        añoSpinner = new JSpinner(new SpinnerNumberModel(2024, 1900, 2050, 1));
        precioSpinner = new JSpinner(new SpinnerNumberModel(1000.0, 0.0, 1000000.0, 100.0));
        sucursalCombo = new JComboBox<>();
        sucursalCombo.addItem("Seleccione una sucursal");
        for (Sucursal sucursal : sucursales) {
            sucursalCombo.addItem(sucursal.getNombre());
        }

        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(new JLabel("Tipo de vehículo:"), gbc);
        gbc.gridx = 1;
        formPanel.add(tipoCombo, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(new JLabel("Marca:"), gbc);
        gbc.gridx = 1;
        formPanel.add(marcaField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        formPanel.add(new JLabel("Modelo:"), gbc);
        gbc.gridx = 1;
        formPanel.add(modeloField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        formPanel.add(new JLabel("Año:"), gbc);
        gbc.gridx = 1;
        formPanel.add(añoSpinner, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        formPanel.add(new JLabel("Precio:"), gbc);
        gbc.gridx = 1;
        formPanel.add(precioSpinner, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        formPanel.add(new JLabel("Sucursal:"), gbc);
        gbc.gridx = 1;
        formPanel.add(sucursalCombo, gbc);

        detallesLayout = new CardLayout();
        detallesPanel = new JPanel(detallesLayout);
        detallesPanel.setBorder(BorderFactory.createTitledBorder("Detalles por tipo"));

        detallesPanel.add(construirPanelAuto(), "Auto");
        detallesPanel.add(construirPanelMotocicleta(), "Motocicleta");
        detallesPanel.add(construirPanelCamion(), "Camion");

        actualizarPanelDetalles();
        prefillDatos();

        mainPanel.add(formPanel, BorderLayout.NORTH);
        mainPanel.add(detallesPanel, BorderLayout.CENTER);
        mainPanel.add(construirBotones(), BorderLayout.SOUTH);

        setContentPane(mainPanel);
    }

    private JPanel construirPanelAuto() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(4, 4, 4, 4);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        numeroPuertasSpinner = new JSpinner(new SpinnerNumberModel(4, 2, 6, 1));
        tipoCombustibleCombo = new JComboBox<>(new String[]{"Gasolina", "Diesel", "Eléctrico", "Híbrido"});
        automaticoCheck = new JCheckBox("Automático");

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Número de puertas:"), gbc);
        gbc.gridx = 1;
        panel.add(numeroPuertasSpinner, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(new JLabel("Tipo de combustible:"), gbc);
        gbc.gridx = 1;
        panel.add(tipoCombustibleCombo, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(new JLabel(""), gbc);
        gbc.gridx = 1;
        panel.add(automaticoCheck, gbc);

        return panel;
    }

    private JPanel construirPanelMotocicleta() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(4, 4, 4, 4);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        cilindradaSpinner = new JSpinner(new SpinnerNumberModel(150, 50, 2500, 50));
        tipoManejoCombo = new JComboBox<>(new String[]{"Urbana", "Deportiva", "Enduro", "Ruta"});

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Cilindrada (cc):"), gbc);
        gbc.gridx = 1;
        panel.add(cilindradaSpinner, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(new JLabel("Tipo de manejo:"), gbc);
        gbc.gridx = 1;
        panel.add(tipoManejoCombo, gbc);

        return panel;
    }

    private JPanel construirPanelCamion() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(4, 4, 4, 4);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        capacidadCargaSpinner = new JSpinner(new SpinnerNumberModel(1.0, 0.5, 100.0, 0.5));
        numeroEjesSpinner = new JSpinner(new SpinnerNumberModel(2, 2, 8, 1));

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Capacidad de carga (ton):"), gbc);
        gbc.gridx = 1;
        panel.add(capacidadCargaSpinner, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(new JLabel("Número de ejes:"), gbc);
        gbc.gridx = 1;
        panel.add(numeroEjesSpinner, gbc);

        return panel;
    }

    private JPanel construirBotones() {
        JPanel botonesPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton guardarButton = new JButton("Guardar");
        JButton cancelarButton = new JButton("Cancelar");

        guardarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onGuardar();
            }
        });

        cancelarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                vehiculoCreado = null;
                dispose();
            }
        });

        botonesPanel.add(guardarButton);
        botonesPanel.add(cancelarButton);
        return botonesPanel;
    }

    private void actualizarPanelDetalles() {
        String tipo = (String) tipoCombo.getSelectedItem();
        detallesLayout.show(detallesPanel, tipo);
    }

    private void onGuardar() {
        try {
            String marca = marcaField.getText().trim();
            String modelo = modeloField.getText().trim();
            int año = (Integer) añoSpinner.getValue();
            double precio = (Double) precioSpinner.getValue();
            String sucursalSeleccionada = (String) sucursalCombo.getSelectedItem();

            if (marca.isEmpty() || modelo.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Marca y modelo son obligatorios.", "Validación", JOptionPane.WARNING_MESSAGE);
                return;
            }

            if (sucursalSeleccionada == null || sucursalSeleccionada.equals("Seleccione una sucursal")) {
                JOptionPane.showMessageDialog(this, "Seleccione una sucursal válida.", "Validación", JOptionPane.WARNING_MESSAGE);
                return;
            }

            int idSucursal = -1;
            for (Sucursal sucursal : sucursales) {
                if (sucursal.getNombre().equals(sucursalSeleccionada)) {
                    idSucursal = sucursal.getId();
                    break;
                }
            }

            if (idSucursal == -1) {
                JOptionPane.showMessageDialog(this, "Seleccione una sucursal válida.", "Validación", JOptionPane.WARNING_MESSAGE);
                return;
            }

            String tipo = (String) tipoCombo.getSelectedItem();
            if (tipo == null) {
                JOptionPane.showMessageDialog(this, "Seleccione el tipo de vehículo.", "Validación", JOptionPane.WARNING_MESSAGE);
                return;
            }

            if (precio < 0) {
                JOptionPane.showMessageDialog(this, "El precio no puede ser negativo.", "Validación", JOptionPane.WARNING_MESSAGE);
                return;
            }

            if (tipo.equals("Auto")) {
                int puertas = (Integer) numeroPuertasSpinner.getValue();
                String combustible = (String) tipoCombustibleCombo.getSelectedItem();
                boolean automatico = automaticoCheck.isSelected();
                if (vehiculoExistente != null && vehiculoExistente instanceof Auto) {
                    Auto auto = (Auto) vehiculoExistente;
                    auto.setMarca(marca);
                    auto.setModelo(modelo);
                    auto.setAño(año);
                    auto.setPrecio(precio);
                    auto.setIdSucursal(idSucursal);
                    auto.setActivo(true);
                    auto.setNumeroPuertas(puertas);
                    auto.setTipoCombustible(combustible);
                    auto.setEsAutomatico(automatico);
                    vehiculoCreado = auto;
                } else {
                    vehiculoCreado = new Auto(marca, modelo, año, precio, idSucursal, true, puertas, combustible, automatico);
                }
            } else if (tipo.equals("Motocicleta")) {
                int cilindrada = (Integer) cilindradaSpinner.getValue();
                String tipoManejo = (String) tipoManejoCombo.getSelectedItem();
                if (vehiculoExistente != null && vehiculoExistente instanceof Motocicleta) {
                    Motocicleta moto = (Motocicleta) vehiculoExistente;
                    moto.setMarca(marca);
                    moto.setModelo(modelo);
                    moto.setAño(año);
                    moto.setPrecio(precio);
                    moto.setIdSucursal(idSucursal);
                    moto.setActivo(true);
                    moto.setCilindrada(cilindrada);
                    moto.setTipoManejo(tipoManejo);
                    vehiculoCreado = moto;
                } else {
                    vehiculoCreado = new Motocicleta(marca, modelo, año, precio, idSucursal, true, cilindrada, tipoManejo);
                }
            } else if (tipo.equals("Camion")) {
                double capacidad = (Double) capacidadCargaSpinner.getValue();
                int ejes = (Integer) numeroEjesSpinner.getValue();
                if (vehiculoExistente != null && vehiculoExistente instanceof Camion) {
                    Camion camion = (Camion) vehiculoExistente;
                    camion.setMarca(marca);
                    camion.setModelo(modelo);
                    camion.setAño(año);
                    camion.setPrecio(precio);
                    camion.setIdSucursal(idSucursal);
                    camion.setActivo(true);
                    camion.setCapacidadCarga(capacidad);
                    camion.setNumeroEjes(ejes);
                    vehiculoCreado = camion;
                } else {
                    vehiculoCreado = new Camion(marca, modelo, año, precio, idSucursal, true, capacidad, ejes);
                }
            }

            if (vehiculoCreado == null) {
                JOptionPane.showMessageDialog(this, "No se pudo crear el vehículo.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            dispose();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al crear el vehículo: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void prefillDatos() {
        if (vehiculoExistente == null) {
            return;
        }

        tipoCombo.setSelectedItem(vehiculoExistente.obtenerTipo());
        actualizarPanelDetalles();
        marcaField.setText(vehiculoExistente.getMarca());
        modeloField.setText(vehiculoExistente.getModelo());
        añoSpinner.setValue(vehiculoExistente.getAño());
        precioSpinner.setValue(vehiculoExistente.getPrecio());

        for (int i = 0; i < sucursalCombo.getItemCount(); i++) {
            String nombre = sucursalCombo.getItemAt(i);
            if (nombre.equals("Seleccione una sucursal")) {
                continue;
            }
            for (Sucursal sucursal : sucursales) {
                if (sucursal.getNombre().equals(nombre) && sucursal.getId() == vehiculoExistente.getIdSucursal()) {
                    sucursalCombo.setSelectedItem(nombre);
                    break;
                }
            }
        }

        if (vehiculoExistente instanceof Auto) {
            Auto auto = (Auto) vehiculoExistente;
            numeroPuertasSpinner.setValue(auto.getNumeroPuertas());
            tipoCombustibleCombo.setSelectedItem(auto.getTipoCombustible());
            automaticoCheck.setSelected(auto.isEsAutomatico());
        } else if (vehiculoExistente instanceof Motocicleta) {
            Motocicleta moto = (Motocicleta) vehiculoExistente;
            cilindradaSpinner.setValue(moto.getCilindrada());
            tipoManejoCombo.setSelectedItem(moto.getTipoManejo());
        } else if (vehiculoExistente instanceof Camion) {
            Camion camion = (Camion) vehiculoExistente;
            capacidadCargaSpinner.setValue(camion.getCapacidadCarga());
            numeroEjesSpinner.setValue(camion.getNumeroEjes());
        }
    }

    public Vehiculo mostrarDialogo() {
        setVisible(true);
        return vehiculoCreado;
    }
}
