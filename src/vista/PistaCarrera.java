package vista;

import modelo.*; // Importa todos los vehículos
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class PistaCarrera extends JFrame {

    private JLabel lblJugador1, lblJugador2, lblCPU;
    private Timer timerCPU;
    private Vehiculo auto1, auto2;
    private CarroCPU cpu;
    private boolean carreraActiva = false;

    public PistaCarrera(Vehiculo eleccionJ1, Vehiculo eleccionJ2, String dificultadCPU) {
        setTitle("Copa Loca Racing - ¡Machaca los botones!");
        setSize(800, 450);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // DISPOSE para no cerrar todo el programa
        setLocationRelativeTo(null);
        setLayout(null);
        getContentPane().setBackground(new Color(40, 40, 40));

        // 1. Asignar los vehículos recibidos desde el Menú de Selección
        this.auto1 = eleccionJ1;
        this.auto2 = eleccionJ2;
        this.cpu = new CarroCPU(dificultadCPU);

        // 2. Crear etiquetas con los nombres de los carros
        lblJugador1 = crearEtiquetaCarro("J1: " + auto1.getClass().getSimpleName(), 50, Color.CYAN);
        lblJugador2 = crearEtiquetaCarro("J2: " + auto2.getClass().getSimpleName(), 150, Color.ORANGE);
        lblCPU = crearEtiquetaCarro("CPU", 250, Color.RED);

        JPanel lineaMeta = new JPanel();
        lineaMeta.setBackground(Color.WHITE);
        lineaMeta.setBounds(700, 0, 10, 450);
        add(lineaMeta);

        // 3. Controles de Teclado (Eventos KeyListener)
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (!carreraActiva) return; // Si no ha empezado, no hacen nada

                // Jugador 1 machaca la tecla 'D'
                if (e.getKeyCode() == KeyEvent.VK_D) {
                    auto1.avanzar();
                    lblJugador1.setLocation(auto1.getPosicion(), lblJugador1.getY());
                }
                // Jugador 2 machaca la 'Flecha Derecha'
                else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    auto2.avanzar();
                    lblJugador2.setLocation(auto2.getPosicion(), lblJugador2.getY());
                }
                verificarGanador();
            }
        });
        setFocusable(true); // Necesario para que el JFrame detecte el teclado

        // 4. El motor EXCLUSIVO de la CPU
        timerCPU = new Timer(150, e -> {
            cpu.avanzar();
            lblCPU.setLocation(cpu.getPosicion(), lblCPU.getY());
            verificarGanador();
        });

        // Iniciar carrera automáticamente al abrir (puedes poner un botón si prefieres)
        iniciarCarrera();
    }

    private JLabel crearEtiquetaCarro(String texto, int y, Color color) {
        JLabel label = new JLabel(texto);
        label.setOpaque(true);
        label.setBackground(color);
        label.setFont(new Font("Arial", Font.BOLD, 12));
        label.setBounds(10, y, 120, 40);
        add(label);
        return label;
    }

    private void iniciarCarrera() {
        carreraActiva = true;
        timerCPU.start();
        JOptionPane.showMessageDialog(this, "¡J1 presiona 'D'! \n¡J2 presiona 'Flecha Derecha'!\n¡GOGOGO!");
    }

    private void verificarGanador() {
        if (!carreraActiva) return;

        String ganador = "";
        if (auto1.getPosicion() >= 700) ganador = "Jugador 1 (" + auto1.getClass().getSimpleName() + ")";
        else if (auto2.getPosicion() >= 700) ganador = "Jugador 2 (" + auto2.getClass().getSimpleName() + ")";
        else if (cpu.getPosicion() >= 700) ganador = "La CPU (" + cpu.getNombre() + ")";

        if (!ganador.isEmpty()) {
            carreraActiva = false;
            timerCPU.stop();
            JOptionPane.showMessageDialog(this, "🏁 ¡El ganador es: " + ganador + "!", "Fin", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}