package view;

import javax.swing.*;
import java.awt.*;

/**
 * Vista del Dashboard con estadísticas e indicadores KPI
 */
public class DashboardView extends JPanel {
    private JLabel totalStockLabel;
    private JLabel totalValueLabel;
    private JLabel maxSucursalLabel;
    private JProgressBar autoProgressBar;
    private JProgressBar motoProgressBar;
    private JProgressBar camionProgressBar;

    public DashboardView() {
        setLayout(new BorderLayout());
        setBackground(new Color(240, 240, 240));

        // Panel superior con KPIs
        JPanel kpiPanel = new JPanel(new GridLayout(1, 3, 10, 10));
        kpiPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        kpiPanel.setBackground(new Color(240, 240, 240));

        // KPI 1: Stock Total
        JPanel stockPanel = crearPanelKPI("Stock Total", "0", Color.BLUE);
        totalStockLabel = (JLabel) stockPanel.getComponent(1);
        kpiPanel.add(stockPanel);

        // KPI 2: Valor del Inventario
        JPanel valuePanel = crearPanelKPI("Valor Inventario", "$0.00", Color.GREEN);
        totalValueLabel = (JLabel) valuePanel.getComponent(1);
        kpiPanel.add(valuePanel);

        // KPI 3: Sucursal con más vehículos
        JPanel maxPanel = crearPanelKPI("Sucursal Principal", "N/A", Color.RED);
        maxSucursalLabel = (JLabel) maxPanel.getComponent(1);
        kpiPanel.add(maxPanel);

        // Panel con gráficos
        JPanel chartPanel = new JPanel(new GridLayout(3, 1, 10, 10));
        chartPanel.setBorder(BorderFactory.createTitledBorder("Porcentaje de Stock por Tipo"));
        chartPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 20));
        chartPanel.setBackground(new Color(240, 240, 240));

        // Autos
        JPanel autoPanel = new JPanel(new BorderLayout(10, 0));
        autoPanel.setBackground(new Color(240, 240, 240));
        autoPanel.add(new JLabel("Autos:"), BorderLayout.WEST);
        autoProgressBar = new JProgressBar(0, 100);
        autoProgressBar.setStringPainted(true);
        autoPanel.add(autoProgressBar, BorderLayout.CENTER);
        chartPanel.add(autoPanel);

        // Motocicletas
        JPanel motoPanel = new JPanel(new BorderLayout(10, 0));
        motoPanel.setBackground(new Color(240, 240, 240));
        motoPanel.add(new JLabel("Motocicletas:"), BorderLayout.WEST);
        motoProgressBar = new JProgressBar(0, 100);
        motoProgressBar.setStringPainted(true);
        motoPanel.add(motoProgressBar, BorderLayout.CENTER);
        chartPanel.add(motoPanel);

        // Camiones
        JPanel camionPanel = new JPanel(new BorderLayout(10, 0));
        camionPanel.setBackground(new Color(240, 240, 240));
        camionPanel.add(new JLabel("Camiones:"), BorderLayout.WEST);
        camionProgressBar = new JProgressBar(0, 100);
        camionProgressBar.setStringPainted(true);
        camionPanel.add(camionProgressBar, BorderLayout.CENTER);
        chartPanel.add(camionPanel);

        add(kpiPanel, BorderLayout.NORTH);
        add(chartPanel, BorderLayout.CENTER);
    }

    private JPanel crearPanelKPI(String titulo, String valor, Color color) {
        JPanel panel = new JPanel(new GridLayout(2, 1));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createLineBorder(color, 2));

        JLabel titleLabel = new JLabel(titulo);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 12));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel valueLabel = new JLabel(valor);
        valueLabel.setFont(new Font("Arial", Font.BOLD, 20));
        valueLabel.setForeground(color);
        valueLabel.setHorizontalAlignment(SwingConstants.CENTER);

        panel.add(titleLabel);
        panel.add(valueLabel);

        return panel;
    }

    public void actualizarKPIs(int totalStock, double totalValue, String sucursalMax) {
        totalStockLabel.setText(String.valueOf(totalStock));
        totalValueLabel.setText(String.format("$%.2f", totalValue));
        maxSucursalLabel.setText(sucursalMax != null ? sucursalMax : "N/A");
    }

    public void actualizarGraficoStock(int autoCount, int motoCount, int camionCount) {
        int total = autoCount + motoCount + camionCount;
        if (total == 0) total = 1;

        autoProgressBar.setValue((autoCount * 100) / total);
        autoProgressBar.setString(autoCount + " (" + ((autoCount * 100) / total) + "%)");

        motoProgressBar.setValue((motoCount * 100) / total);
        motoProgressBar.setString(motoCount + " (" + ((motoCount * 100) / total) + "%)");

        camionProgressBar.setValue((camionCount * 100) / total);
        camionProgressBar.setString(camionCount + " (" + ((camionCount * 100) / total) + "%)");
    }
}
