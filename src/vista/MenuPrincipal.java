package vista;

import modelo.*;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ItemEvent;

public class MenuPrincipal extends JFrame {

    private JComboBox<String> cmbModo;
    private JComboBox<String> cmbDificultad;
    private JComboBox<String> cmbAutosJ1;
    private JComboBox<String> cmbAutosJ2;
    private JButton btnIniciar;
    private JButton btnManual;

    public MenuPrincipal() {
        setUndecorated(true);
        setSize(500, 400); 
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Mata el proceso al cerrar
        setLocationRelativeTo(null);

        JPanel fondoMenu = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g.setColor(new Color(30, 15, 60)); 
                g.fillRect(0, 0, getWidth(), getHeight());
                g.setColor(new Color(45, 45, 50));
                g.fillRect(0, 280, getWidth(), 120);
                g.setColor(Color.WHITE);
                for (int i = 0; i < getWidth(); i += 80) {
                    g.fillRect(i, 320, 40, 5); 
                }
            }
        };
        fondoMenu.setLayout(null);
        fondoMenu.setBorder(BorderFactory.createLineBorder(new Color(255, 215, 0), 4));
        setContentPane(fondoMenu);

        // --- TÍTULO ---
        JLabel lblTitulo = new JLabel("--- COPA LOCA RACING ---", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Consolas", Font.BOLD, 28));
        lblTitulo.setForeground(new Color(255, 60, 0)); 
        lblTitulo.setBounds(0, 30, 500, 35);
        fondoMenu.add(lblTitulo);

        // --- SELECTORES ---
        cmbModo = new JComboBox<>(new String[]{"1 Jugador", "2 Jugadores"});
        cmbModo.setBounds(70, 90, 150, 30);
        fondoMenu.add(cmbModo);

        cmbDificultad = new JComboBox<>(new String[]{"CPU: Fácil", "CPU: Medio", "CPU: Difícil", "CPU: Dios"});
        cmbDificultad.setBounds(280, 90, 150, 30);
        fondoMenu.add(cmbDificultad);

        String[] catalogoAutos = {
            "- Auto Rojo -", "- Deportivo Azul -", "- Deportivo Rojo -", 
            "- Fórmula 1 -", "- Fórmula 2 -", "- Vocho -"
        };
        
        cmbAutosJ1 = new JComboBox<>(catalogoAutos);
        cmbAutosJ1.setBounds(70, 150, 150, 45);
        TitledBorder bordeJ1 = BorderFactory.createTitledBorder("Jugador 1");
        bordeJ1.setTitleColor(Color.WHITE); 
        cmbAutosJ1.setBorder(bordeJ1);
        cmbAutosJ1.setBackground(Color.LIGHT_GRAY);
        fondoMenu.add(cmbAutosJ1);

        cmbAutosJ2 = new JComboBox<>(catalogoAutos);
        cmbAutosJ2.setBounds(280, 150, 150, 45);
        TitledBorder bordeJ2 = BorderFactory.createTitledBorder("Jugador 2");
        bordeJ2.setTitleColor(Color.WHITE);
        cmbAutosJ2.setBorder(bordeJ2);
        cmbAutosJ2.setBackground(Color.LIGHT_GRAY);
        cmbAutosJ2.setVisible(false); 
        fondoMenu.add(cmbAutosJ2);

        // --- BOTONES ---
        btnIniciar = new JButton(">> INICIAR CARRERA <<");
        btnIniciar.setBounds(120, 215, 260, 45); 
        btnIniciar.setBackground(new Color(50, 205, 50)); 
        btnIniciar.setForeground(Color.BLACK);
        btnIniciar.setFont(new Font("Consolas", Font.BOLD, 18)); 
        btnIniciar.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2)); 
        btnIniciar.setCursor(new Cursor(Cursor.HAND_CURSOR)); 
        btnIniciar.setFocusPainted(false);
        fondoMenu.add(btnIniciar);

        btnManual = new JButton("[ Manual de Usuario ]");
        btnManual.setBounds(150, 275, 200, 30);
        btnManual.setBackground(Color.DARK_GRAY);
        btnManual.setForeground(Color.WHITE);
        btnManual.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        btnManual.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnManual.setFocusPainted(false);
        fondoMenu.add(btnManual);

        // NUEVO BOTÓN DE SALIR
        JButton btnSalirApp = new JButton("[ Salir del Juego ]");
        btnSalirApp.setBounds(150, 315, 200, 30);
        btnSalirApp.setBackground(new Color(200, 40, 40));
        btnSalirApp.setForeground(Color.WHITE);
        btnSalirApp.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        btnSalirApp.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnSalirApp.setFocusPainted(false);
        btnSalirApp.addActionListener(e -> System.exit(0)); // Apaga todo
        fondoMenu.add(btnSalirApp);

        // --- EVENTOS ---
        cmbModo.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                cmbAutosJ2.setVisible(cmbModo.getSelectedItem().equals("2 Jugadores"));
            }
        });

        btnIniciar.addActionListener(e -> ejecutarArranque());
    }

    private void ejecutarArranque() {
        try {
            boolean esDosJugadores = cmbModo.getSelectedItem().equals("2 Jugadores");
            boolean esUnJugador = !esDosJugadores;
            String nivelDificultad = cmbDificultad.getSelectedItem().toString().replace("CPU: ", ""); 

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