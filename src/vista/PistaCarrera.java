package vista;

import modelo.*; 
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class PistaCarrera extends JFrame {

    private JLabel lblJugador1, lblJugador2, lblCPU, lblSemaforo;
    private Timer timerCPU;
    private Vehiculo auto1, auto2;
    private CarroCPU cpu;
    private boolean carreraActiva = false;
    private boolean unJugador;
    
    private final int META_X = 1050; 

    public PistaCarrera(Vehiculo eleccionJ1, Vehiculo eleccionJ2, String dificultadCPU, String imgCPU, boolean unJugador) {
        this.unJugador = unJugador;
        setTitle("Copa Loca Racing - Torneo Mayor");
        setSize(1200, 600); 
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); 
        setLocationRelativeTo(null);

        this.auto1 = eleccionJ1;
        this.auto2 = eleccionJ2;
        this.cpu = new CarroCPU(dificultadCPU);

        JPanel fondoPista = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                
                // Cielo y Nubes
                g.setColor(new Color(30, 15, 60)); 
                g.fillRect(0, 0, getWidth(), 150);
                dibujarProporcional(g2d, "sol.png", 420, 10, 140); 
                dibujarProporcional(g2d, "1nube.png", 100, 30, 40); 
                dibujarProporcional(g2d, "2nubes.png", 680, 40, 45); 
                dibujarProporcional(g2d, "1nube.png", 950, 20, 35);

                // Césped oscuro
                g.setColor(new Color(20, 60, 40)); 
                g.fillRect(0, 150, getWidth(), getHeight() - 150);

                // Ciudad
                dibujarProporcional(g2d, "edificio1.png", 20, 10, 140);
                dibujarProporcional(g2d, "edificio2.png", 180, 20, 130);
                dibujarProporcional(g2d, "edificio3.png", 290, 15, 135);
                dibujarProporcional(g2d, "edificio4.png", 580, 20, 130);
                dibujarProporcional(g2d, "edificio5.png", 730, 10, 140);
                dibujarProporcional(g2d, "edificio6.png", 1050, 25, 125); 

                // Adoquín en bucle (Se agregó un valor de 20 para superponer y ocultar los huecos)
                dibujarMosaicoHorizontal(g2d, "adoquin.png", 185, 35, getWidth(), 20); 
                dibujarMosaicoHorizontal(g2d, "adoquin.png", 480, 35, getWidth(), 20);

                // Árboles de fondo
                for (int i = 10; i < getWidth(); i += 250) {
                    dibujarProporcional(g2d, "Arbol.png", i, 60, 95);       
                    dibujarProporcional(g2d, "arbusto1.png", i + 100, 125, 30); 
                }

                // Asfalto y líneas blancas
                g.setColor(new Color(45, 45, 50));
                g.fillRect(0, 220, getWidth(), 260);
                g.setColor(Color.WHITE);
                for (int i = 0; i < getWidth(); i += 80) {
                    g.fillRect(i, 305, 40, 5); 
                    g.fillRect(i, 390, 40, 5); 
                }

                // Elementos Frontales
                dibujarProporcional(g2d, "parada_bus.png", 120, 85, 105);      
                dibujarProporcional(g2d, "maquina_expendedora.png", 340, 115, 75); 
                dibujarProporcional(g2d, "maseta.png", 470, 150, 40);          
                dibujarProporcional(g2d, "caseta.png", 650, 85, 105);         
                dibujarProporcional(g2d, "toma_agua.png", 850, 145, 45);       

                for (int i = 30; i < getWidth(); i += 250) {
                    dibujarProporcional(g2d, "arbusto2.png", i, 510, 35);     
                    dibujarProporcional(g2d, "Arbol.png", i + 120, 450, 110);  
                }

                // Meta cuadriculada
                for (int i = 220; i < 480; i += 20) {
                    if ((i / 20) % 2 == 0) g.setColor(Color.WHITE);
                    else g.setColor(Color.BLACK);
                    g.fillRect(META_X, i, 20, 20);
                }
            }
            
            private void dibujarProporcional(Graphics2D g2d, String nombre, int x, int y, int alto) {
                java.net.URL url = getClass().getResource("/imagenes/" + nombre);
                if (url != null) {
                    Image img = new ImageIcon(url).getImage();
                    if (img.getWidth(null) > 0) g2d.drawImage(img, x, y, (img.getWidth(null) * alto) / img.getHeight(null), alto, null);
                }
            }

            // Método actualizado para aceptar el parámetro de "superposicion"
            private void dibujarMosaicoHorizontal(Graphics2D g2d, String nombre, int y, int alto, int anchoV, int superposicion) {
                java.net.URL url = getClass().getResource("/imagenes/" + nombre);
                if (url != null) {
                    Image img = new ImageIcon(url).getImage();
                    if (img.getWidth(null) > 0) {
                        int ancho = (img.getWidth(null) * alto) / img.getHeight(null);
                        // Restamos la superposición al ancho en cada salto del ciclo
                        for (int x = 0; x < anchoV; x += (ancho - superposicion)) {
                            g2d.drawImage(img, x, y, ancho, alto, null);
                        }
                    }
                }
            }
        };
        fondoPista.setLayout(null);
        setContentPane(fondoPista);

        lblSemaforo = new JLabel("Cargando Motores...", SwingConstants.CENTER);
        lblSemaforo.setFont(new Font("Consolas", Font.BOLD, 60));
        lblSemaforo.setForeground(Color.WHITE);
        lblSemaforo.setBounds(0, 260, 1200, 100);
        fondoPista.add(lblSemaforo);

        lblJugador1 = crearVehiculoImagen(auto1.getNombre() + ".png", 240);
        lblJugador2 = crearVehiculoImagen(auto2.getNombre() + ".png", 325);
        lblCPU = crearVehiculoImagen(imgCPU + ".png", 410);

        // 4. Controles (¡MODO COMPETITIVO ESTRICTO!)
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) { // <-- EL SECRETO: Solo avanza al soltar la tecla
                if (!carreraActiva) return;

                // Jugador 1: Obligado a machacar y soltar la 'D' repetidamente
                if (e.getKeyCode() == KeyEvent.VK_D) {
                    auto1.avanzar();
                    lblJugador1.setLocation(10 + auto1.getPosicion(), lblJugador1.getY());
                }
                // Jugador 2: Obligado a machacar y soltar la Flecha Derecha
                else if (e.getKeyCode() == KeyEvent.VK_RIGHT && !unJugador) {
                    auto2.avanzar();
                    lblJugador2.setLocation(10 + auto2.getPosicion(), lblJugador2.getY());
                }
                verificarGanador();
            }
        });
        setFocusable(true); 

        // 5. Motor Automático (Ajustado a 200ms para competir justamente contra humanos)
        timerCPU = new Timer(150, e -> {
            cpu.avanzar();
            lblCPU.setLocation(10 + cpu.getPosicion(), lblCPU.getY());
            
            if (unJugador) {
                auto2.avanzar(); 
                lblJugador2.setLocation(10 + auto2.getPosicion(), lblJugador2.getY());
            }
            verificarGanador();
        });

        iniciarSemaforo();
    }

    private void iniciarSemaforo() {
        Timer semaforoTimer = new Timer(1000, new java.awt.event.ActionListener() {
            int paso = 0;
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                if (paso == 0) {
                    lblSemaforo.setText("🔴 PREPARADOS...");
                    lblSemaforo.setForeground(new Color(255, 50, 50));
                } else if (paso == 1) {
                    lblSemaforo.setText("🟡 LISTOS...");
                    lblSemaforo.setForeground(new Color(255, 255, 50));
                } else if (paso == 2) {
                    lblSemaforo.setText("🟢 ¡GOGOGO!");
                    lblSemaforo.setForeground(new Color(50, 255, 50));
                    carreraActiva = true;
                    timerCPU.start();
                } else if (paso == 3) {
                    lblSemaforo.setVisible(false);
                    ((Timer)e.getSource()).stop();
                }
                paso++;
            }
        });
        semaforoTimer.start();
    }

    // Método actualizado con Opciones de Fin de Juego
    private void verificarGanador() {
        if (!carreraActiva) return;

        String ganador = "";
        if (auto1.getPosicion() >= META_X - 100) ganador = "Jugador 1 (" + auto1.getClass().getSimpleName() + ")";
        else if (auto2.getPosicion() >= META_X - 100) ganador = "Jugador 2 (" + auto2.getClass().getSimpleName() + ")";
        else if (cpu.getPosicion() >= META_X - 100) ganador = "La CPU";

        if (!ganador.isEmpty()) {
            carreraActiva = false;
            timerCPU.stop();
            
            // Opciones del Menú
            String[] opciones = {"Repetir Carrera", "Regresar al Menú"};
            int eleccion = JOptionPane.showOptionDialog(this,
                    "🏁 ¡El ganador es: " + ganador + "!\n¿Qué deseas hacer ahora?",
                    "Fin de la Carrera",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    opciones,
                    opciones[0]);

            if (eleccion == 0) {
                // Opción 1: Repetir Carrera (Reinicia todo a la posición inicial)
                auto1.reiniciar();
                auto2.reiniciar();
                cpu.reiniciar();
                lblJugador1.setLocation(10, lblJugador1.getY());
                lblJugador2.setLocation(10, lblJugador2.getY());
                lblCPU.setLocation(10, lblCPU.getY());
                
                lblSemaforo.setVisible(true);
                iniciarSemaforo();
            } else {
                // Opción 2: Cierra la pista y abre el Menú Principal
                new MenuPrincipal().setVisible(true);
                this.dispose();
            }
        }
    }

    private JLabel crearVehiculoImagen(String nombreArchivo, int y) {
        JLabel label = new JLabel();
        java.net.URL url = getClass().getResource("/imagenes/" + nombreArchivo);
        if (url != null) {
            ImageIcon iconoOriginal = new ImageIcon(url);
            Image imagenEscalada = iconoOriginal.getImage().getScaledInstance(100, 50, Image.SCALE_SMOOTH);
            label.setIcon(new ImageIcon(imagenEscalada));
        } else {
            label.setText("IMG_ERROR"); label.setForeground(Color.RED); label.setOpaque(true);
        }
        label.setBounds(10, y, 100, 50); 
        add(label);
        return label;
    }
}