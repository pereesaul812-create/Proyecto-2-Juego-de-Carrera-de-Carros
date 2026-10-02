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
    
    // NUEVAS VARIABLES PARA LA PAUSA
    private boolean juegoPausado = false;
    private JButton btnPausar;
    
    private final int META_X = 1050; 

    public PistaCarrera(Vehiculo eleccionJ1, Vehiculo eleccionJ2, String dificultadCPU, String imgCPU, boolean unJugador) {
        this.unJugador = unJugador;
        setTitle("Copa Loca Racing - Pista");
        setSize(1200, 600); 
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Asegura que se apague todo al cerrar la ventana a la fuerza
        setLocationRelativeTo(null);

        this.auto1 = eleccionJ1;
        this.auto2 = eleccionJ2;
        this.cpu = new CarroCPU(dificultadCPU);

        JPanel fondoPista = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                
                g.setColor(new Color(30, 15, 60)); 
                g.fillRect(0, 0, getWidth(), 150);
                dibujarProporcional(g2d, "sol.png", 420, 10, 140); 
                dibujarProporcional(g2d, "1nube.png", 100, 30, 40); 
                dibujarProporcional(g2d, "2nubes.png", 680, 40, 45); 
                dibujarProporcional(g2d, "1nube.png", 950, 20, 35);

                g.setColor(new Color(20, 60, 40)); 
                g.fillRect(0, 150, getWidth(), getHeight() - 150);

                dibujarProporcional(g2d, "edificio1.png", 20, 10, 140);
                dibujarProporcional(g2d, "edificio2.png", 180, 20, 130);
                dibujarProporcional(g2d, "edificio3.png", 290, 15, 135);
                dibujarProporcional(g2d, "edificio4.png", 580, 20, 130);
                dibujarProporcional(g2d, "edificio5.png", 730, 10, 140);
                dibujarProporcional(g2d, "edificio6.png", 1050, 25, 125); 

                dibujarMosaicoHorizontal(g2d, "adoquin.png", 185, 35, getWidth(), 20); 
                dibujarMosaicoHorizontal(g2d, "adoquin.png", 480, 35, getWidth(), 20);

                for (int i = 10; i < getWidth(); i += 250) {
                    dibujarProporcional(g2d, "Arbol.png", i, 60, 95);       
                    dibujarProporcional(g2d, "arbusto1.png", i + 100, 125, 30); 
                }

                g.setColor(new Color(45, 45, 50));
                g.fillRect(0, 220, getWidth(), 260);
                g.setColor(Color.WHITE);
                for (int i = 0; i < getWidth(); i += 80) {
                    g.fillRect(i, 305, 40, 5); 
                    g.fillRect(i, 390, 40, 5); 
                }

                dibujarProporcional(g2d, "parada_bus.png", 120, 85, 105);      
                dibujarProporcional(g2d, "maquina_expendedora.png", 340, 115, 75); 
                dibujarProporcional(g2d, "maseta.png", 470, 150, 40);          
                dibujarProporcional(g2d, "caseta.png", 650, 85, 105);          
                dibujarProporcional(g2d, "toma_agua.png", 850, 145, 45);        

                for (int i = 30; i < getWidth(); i += 250) {
                    dibujarProporcional(g2d, "arbusto2.png", i, 510, 35);      
                    dibujarProporcional(g2d, "Arbol.png", i + 120, 450, 110);  
                }

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

            private void dibujarMosaicoHorizontal(Graphics2D g2d, String nombre, int y, int alto, int anchoV, int superposicion) {
                java.net.URL url = getClass().getResource("/imagenes/" + nombre);
                if (url != null) {
                    Image img = new ImageIcon(url).getImage();
                    if (img.getWidth(null) > 0) {
                        int ancho = (img.getWidth(null) * alto) / img.getHeight(null);
                        for (int x = 0; x < anchoV; x += (ancho - superposicion)) {
                            g2d.drawImage(img, x, y, ancho, alto, null);
                        }
                    }
                }
            }
        };
        fondoPista.setLayout(null);
        setContentPane(fondoPista);

        // --- BOTONES EN CARRERA (Pausa y Salir) ---
        btnPausar = new JButton("⏸ PAUSAR");
        btnPausar.setBounds(920, 20, 120, 35);
        btnPausar.setBackground(Color.ORANGE);
        btnPausar.setForeground(Color.BLACK);
        btnPausar.setFont(new Font("Consolas", Font.BOLD, 14));
        btnPausar.setFocusable(false); // ¡CRÍTICO PARA QUE EL TECLADO SIGA FUNCIONANDO!
        btnPausar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnPausar.addActionListener(e -> alternarPausa());
        fondoPista.add(btnPausar);

        JButton btnSalir = new JButton("✖ SALIR");
        btnSalir.setBounds(1050, 20, 100, 35);
        btnSalir.setBackground(new Color(200, 40, 40));
        btnSalir.setForeground(Color.WHITE);
        btnSalir.setFont(new Font("Consolas", Font.BOLD, 14));
        btnSalir.setFocusable(false); // ¡CRÍTICO!
        btnSalir.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnSalir.addActionListener(e -> System.exit(0)); // Apaga todo inmediatamente
        fondoPista.add(btnSalir);

        lblSemaforo = new JLabel("Cargando Motores...", SwingConstants.CENTER);
        lblSemaforo.setFont(new Font("Consolas", Font.BOLD, 60));
        lblSemaforo.setForeground(Color.WHITE);
        lblSemaforo.setBounds(0, 260, 1200, 100);
        fondoPista.add(lblSemaforo);

        lblJugador1 = crearVehiculoImagen(auto1.getNombre() + ".png", 240);
        lblJugador2 = crearVehiculoImagen(auto2.getNombre() + ".png", 325);
        lblCPU = crearVehiculoImagen(imgCPU + ".png", 410);

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) { 
                // Si la carrera no está activa o ESTÁ PAUSADA, los controles no responden
                if (!carreraActiva || juegoPausado) return; 

                if (e.getKeyCode() == KeyEvent.VK_D) {
                    auto1.avanzar();
                    lblJugador1.setLocation(10 + auto1.getPosicion(), lblJugador1.getY());
                }
                else if (e.getKeyCode() == KeyEvent.VK_RIGHT && !unJugador) {
                    auto2.avanzar();
                    lblJugador2.setLocation(10 + auto2.getPosicion(), lblJugador2.getY());
                }
                verificarGanador();
            }
        });
        setFocusable(true); 

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

    // Método de la pausa
    private void alternarPausa() {
        if (!carreraActiva) return; // No hace nada si están en semáforo o ya ganaron
        
        juegoPausado = !juegoPausado;
        if (juegoPausado) {
            btnPausar.setText("REANUDAR");
            btnPausar.setBackground(Color.GREEN);
            timerCPU.stop(); // Detiene a la CPU
            lblSemaforo.setText("--- PAUSADO ---");
            lblSemaforo.setForeground(Color.YELLOW);
            lblSemaforo.setVisible(true);
        } else {
            btnPausar.setText("PAUSAR");
            btnPausar.setBackground(Color.ORANGE);
            timerCPU.start(); // Revive a la CPU
            lblSemaforo.setVisible(false);
        }
    }

    private void iniciarSemaforo() {
        Timer semaforoTimer = new Timer(1000, new java.awt.event.ActionListener() {
            int paso = 0;
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                if (paso == 0) {
                    lblSemaforo.setText("2 PREPARADOS...");
                    lblSemaforo.setForeground(new Color(255, 50, 50));
                } else if (paso == 1) {
                    lblSemaforo.setText("1 LISTOS...");
                    lblSemaforo.setForeground(new Color(255, 255, 50));
                } else if (paso == 2) {
                    lblSemaforo.setText("0 ¡GOGOGO!");
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

    private void verificarGanador() {
        if (!carreraActiva) return;

        String ganador = "";
        if (auto1.getPosicion() >= META_X - 100) ganador = "JUGADOR 1 (" + auto1.getClass().getSimpleName() + ")";
        else if (auto2.getPosicion() >= META_X - 100) ganador = "JUGADOR 2 (" + auto2.getClass().getSimpleName() + ")";
        else if (cpu.getPosicion() >= META_X - 100) ganador = "LA CPU";

        if (!ganador.isEmpty()) {
            carreraActiva = false;
            timerCPU.stop();

            // --- VENTANA DE VICTORIA ACTUALIZADA CON 3 BOTONES ---
            JDialog dialogVictoria = new JDialog(this, "Fin de la Carrera", true);
            dialogVictoria.setSize(480, 240); // Más ancha para que quepan los 3 botones
            dialogVictoria.setLocationRelativeTo(this);
            dialogVictoria.setUndecorated(true);
            
            JPanel panelFondo = new JPanel();
            panelFondo.setBackground(new Color(30, 15, 60)); 
            panelFondo.setBorder(BorderFactory.createLineBorder(new Color(255, 215, 0), 4)); 
            panelFondo.setLayout(null); 

            JLabel lblTitulo = new JLabel("--- FIN DE LA CARRERA ---", SwingConstants.CENTER);
            lblTitulo.setFont(new Font("Consolas", Font.BOLD, 24));
            lblTitulo.setForeground(new Color(255, 60, 0)); 
            lblTitulo.setBounds(0, 20, 480, 30);
            panelFondo.add(lblTitulo);

            JLabel lblSub = new JLabel("El gran ganador absoluto es:", SwingConstants.CENTER);
            lblSub.setFont(new Font("Consolas", Font.PLAIN, 16));
            lblSub.setForeground(Color.WHITE);
            lblSub.setBounds(0, 60, 480, 20);
            panelFondo.add(lblSub);

            JLabel lblGanador = new JLabel(ganador, SwingConstants.CENTER);
            lblGanador.setFont(new Font("Consolas", Font.BOLD, 26));
            lblGanador.setForeground(new Color(255, 215, 0)); 
            lblGanador.setBounds(0, 90, 480, 40);
            panelFondo.add(lblGanador);

            // Botón 1: Repetir
            JButton btnRepetir = new JButton(">> REPETIR <<");
            btnRepetir.setBounds(20, 160, 180, 40);
            btnRepetir.setBackground(new Color(50, 205, 50)); 
            btnRepetir.setForeground(Color.BLACK);
            btnRepetir.setFont(new Font("Consolas", Font.BOLD, 14));
            btnRepetir.setFocusPainted(false);
            btnRepetir.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
            btnRepetir.setCursor(new Cursor(Cursor.HAND_CURSOR));
            panelFondo.add(btnRepetir);

            // Botón 2: Menú
            JButton btnMenu = new JButton("[ Menú ]");
            btnMenu.setBounds(215, 160, 110, 40);
            btnMenu.setBackground(Color.DARK_GRAY);
            btnMenu.setForeground(Color.WHITE);
            btnMenu.setFont(new Font("Consolas", Font.BOLD, 14));
            btnMenu.setFocusPainted(false);
            btnMenu.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));
            btnMenu.setCursor(new Cursor(Cursor.HAND_CURSOR));
            panelFondo.add(btnMenu);

            // Botón 3: Salir Juego
            JButton btnSalirFin = new JButton("X Salir");
            btnSalirFin.setBounds(340, 160, 110, 40);
            btnSalirFin.setBackground(new Color(200, 40, 40)); 
            btnSalirFin.setForeground(Color.WHITE);
            btnSalirFin.setFont(new Font("Consolas", Font.BOLD, 14));
            btnSalirFin.setFocusPainted(false);
            btnSalirFin.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));
            btnSalirFin.setCursor(new Cursor(Cursor.HAND_CURSOR));
            panelFondo.add(btnSalirFin);

            // Funcionalidades de Victoria
            btnRepetir.addActionListener(e -> {
                dialogVictoria.dispose(); 
                auto1.reiniciar();
                auto2.reiniciar();
                cpu.reiniciar();
                lblJugador1.setLocation(10, lblJugador1.getY());
                lblJugador2.setLocation(10, lblJugador2.getY());
                lblCPU.setLocation(10, lblCPU.getY());
                lblSemaforo.setVisible(true);
                iniciarSemaforo();
            });

            btnMenu.addActionListener(e -> {
                dialogVictoria.dispose(); 
                new MenuPrincipal().setVisible(true); 
                this.dispose(); 
            });

            btnSalirFin.addActionListener(e -> System.exit(0)); // Apaga todo inmediatamente

            dialogVictoria.add(panelFondo);
            dialogVictoria.setVisible(true);
        }
    }

    private JLabel crearVehiculoImagen(String nombreArchivo, int y) {
        JLabel label = new JLabel();
        
        if (nombreArchivo.equals("Fantasma.png") && unJugador) {
            label.setVisible(false);
            add(label);
            return label;
        }

        java.net.URL url = getClass().getResource("/imagenes/" + nombreArchivo);
        if (url != null) {
            ImageIcon iconoOriginal = new ImageIcon(url);
            Image imagenEscalada = iconoOriginal.getImage().getScaledInstance(100, 50, Image.SCALE_SMOOTH);
            label.setIcon(new ImageIcon(imagenEscalada));
        } else {
            label.setVisible(false);
        }
        
        label.setBounds(10, y, 100, 50); 
        add(label);
        return label;
    }
}