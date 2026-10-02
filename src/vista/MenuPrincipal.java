package vista;

import modelo.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;

public class MenuPrincipal extends JFrame {

    private JComboBox<String> cmbModo;
    private JComboBox<String> cmbDificultad;
    private JComboBox<String> cmbAutosJ1;
    private JComboBox<String> cmbAutosJ2;
    private JButton btnIniciar;
    private JButton btnManual;
    private JButton btnSonido;
    private JButton btnSalirApp;
    private JPanel panelJ2;

    public MenuPrincipal() {
        setTitle("Copa Loca Racing - Menú Principal");
        setSize(450, 560);
        setLocationRelativeTo(null);
        setUndecorated(true); // Estilo moderno sin bordes

        // Panel principal con el estilo limpio y unificado
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(new Color(30, 15, 60)); 
        mainPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(255, 200, 50), 3), // Borde dorado
                BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));

        // Título del Menú
        JLabel lblTitulo = new JLabel("Copa Loca Racing");
        lblTitulo.setForeground(new Color(255, 200, 50));
        lblTitulo.setFont(new Font("Consolas", Font.BOLD, 24));
        lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        mainPanel.add(lblTitulo);
        mainPanel.add(Box.createVerticalStrut(15));

        // --- MODO Y DIFICULTAD DE CPU ---
        JPanel panelConfig = new JPanel(new GridLayout(1, 2, 10, 0));
        panelConfig.setBackground(new Color(45, 25, 80));
        panelConfig.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
        
        cmbModo = new JComboBox<>(new String[]{"1 Jugador", "2 Jugadores"});
        cmbDificultad = new JComboBox<>(new String[]{"Fácil", "Medio", "Difícil"});
        
        panelConfig.add(cmbModo);
        panelConfig.add(cmbDificultad);
        panelConfig.setMaximumSize(new Dimension(400, 40));
        mainPanel.add(panelConfig);
        mainPanel.add(Box.createVerticalStrut(10));

        // --- CATÁLOGO DE VEHÍCULOS ---
        String[] catalogoAutos = {
            "- Auto Rojo -", 
            "- Deportivo Azul -", 
            "- Deportivo Rojo -", 
            "- Fórmula 1 -", 
            "- Fórmula 2 -", 
            "- Vocho -"
        };

        // Jugador 1
        cmbAutosJ1 = new JComboBox<>(catalogoAutos);
        JPanel panelJ1 = crearPanelConTitulo("Jugador 1", cmbAutosJ1);
        panelJ1.setMaximumSize(new Dimension(400, 55));
        mainPanel.add(panelJ1);
        mainPanel.add(Box.createVerticalStrut(10));

        // Jugador 2 (Dinámico)
        cmbAutosJ2 = new JComboBox<>(catalogoAutos);
        panelJ2 = crearPanelConTitulo("Jugador 2", cmbAutosJ2);
        panelJ2.setMaximumSize(new Dimension(400, 55));
        panelJ2.setVisible(false); // Inicia oculto en 1 Jugador
        mainPanel.add(panelJ2);
        mainPanel.add(Box.createVerticalStrut(15));

        // --- BOTONES DE ACCIÓN ---
        btnIniciar = crearBotonEstilizado("Iniciar Carrera");
        btnManual = crearBotonEstilizado("Manual de Usuario");
        btnSonido = crearBotonEstilizado("Ajustes de Sonido");
        btnSalirApp = crearBotonEstilizado("Salir del Juego");

        mainPanel.add(btnIniciar);
        mainPanel.add(Box.createVerticalStrut(10));
        mainPanel.add(btnManual);
        mainPanel.add(Box.createVerticalStrut(10));
        mainPanel.add(btnSonido);
        mainPanel.add(Box.createVerticalStrut(10));
        mainPanel.add(btnSalirApp);

        add(mainPanel);

        // --- EVENTOS ---
        cmbModo.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                boolean esDosJugadores = cmbModo.getSelectedItem().equals("2 Jugadores");
                panelJ2.setVisible(esDosJugadores);
                revalidate();
                repaint();
            }
        });

        btnIniciar.addActionListener(e -> ejecutarArranque());
        btnSalirApp.addActionListener(e -> System.exit(0));
        btnManual.addActionListener(e -> new ManualUsuario(this).setVisible(true));
        
        // Conexión del botón de Ajustes de Sonido con la clase de audio de tu compañero
        btnSonido.addActionListener(e -> {
            try {
                // Si la clase se llama ConfiguracionAudio o AjustesSonido, se abrirá aquí
                // Cambia el nombre si tu compañero usó otro identificador exacto en el paquete vista
                new ConfiguracionAudio(this).setVisible(true);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Ventana de audio abierta o no encontrada: " + ex.getMessage());
            }
        });
    }

    private JPanel crearPanelConTitulo(String titulo, JComboBox<String> combo) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(45, 25, 80));
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(120, 40, 180), 2),
            BorderFactory.createEmptyBorder(5, 8, 5, 8)
        ));
        
        JLabel lbl = new JLabel(titulo);
        lbl.setForeground(Color.WHITE);
        lbl.setFont(new Font("Consolas", Font.BOLD, 12));
        
        panel.add(lbl, BorderLayout.NORTH);
        panel.add(combo, BorderLayout.CENTER);
        return panel;
    }

    private JButton crearBotonEstilizado(String texto) {
        JButton boton = new JButton(texto);
        boton.setBackground(new Color(200, 40, 120)); // Color rosado unificado
        boton.setForeground(Color.WHITE);
        boton.setFont(new Font("Consolas", Font.BOLD, 14));
        boton.setOpaque(true);
        boton.setBorderPainted(false);
        boton.setFocusPainted(false);
        boton.setAlignmentX(Component.CENTER_ALIGNMENT);
        boton.setMaximumSize(new Dimension(350, 35));
        return boton;
    }

    private void ejecutarArranque() {
        try {
            boolean esDosJugadores = cmbModo.getSelectedItem().equals("2 Jugadores");
            boolean esUnJugador = !esDosJugadores;
            String nivelDificultad = cmbDificultad.getSelectedItem().toString(); 

            Vehiculo auto1 = fabricarVehiculo(cmbAutosJ1.getSelectedItem().toString());
            Vehiculo auto2;
            
            if (esDosJugadores) {
                auto2 = fabricarVehiculo(cmbAutosJ2.getSelectedItem().toString());
            } else {
                auto2 = new Camioneta("Fantasma"); 
            }
            
            String imagenCPU = "deportivo_rojo"; 

            PistaCarrera pista = new PistaCarrera(auto1, auto2, nivelDificultad, imagenCPU, esUnJugador);
            pista.setVisible(true);
            this.dispose(); 
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error de motor: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private Vehiculo fabricarVehiculo(String eleccion) {
        switch(eleccion) {
            case "- Auto Rojo -": return new AutoDeportivo("auto_rojo");
            case "- Deportivo Azul -": return new AutoDeportivo("deportivo_azul");
            case "- Deportivo Rojo -": return new AutoDeportivo("deportivo_rojo");
            case "- Fórmula 1 -": return new Formula1("formula_1");
            case "- Fórmula 2 -": return new Formula1("formula_2");
            case "- Vocho -": return new Camioneta("vocho");
            default: return new AutoDeportivo("auto_rojo");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MenuPrincipal().setVisible(true));
    }
}