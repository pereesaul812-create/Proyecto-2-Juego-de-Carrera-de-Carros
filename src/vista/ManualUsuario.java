package vista;

import javax.swing.*;
import java.awt.*;

public class ManualUsuario extends JDialog {
    
    public ManualUsuario(Frame parent) {
        super(parent, "Manual de Usuario", true);
        setSize(450, 450);
        setLocationRelativeTo(parent);
        setUndecorated(true); // Estilo moderno sin bordes de ventana del sistema
        
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(new Color(30, 15, 60)); // Mismo fondo synthwave
        mainPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(255, 200, 50), 3),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));

        // Título del manual
        JLabel lblTitulo = new JLabel("MANUAL DE USUARIO");
        lblTitulo.setForeground(new Color(255, 200, 50));
        lblTitulo.setFont(new Font("Consolas", Font.BOLD, 24));
        lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        mainPanel.add(lblTitulo);
        mainPanel.add(Box.createVerticalStrut(20));

        // Banner 1: Un jugador
        String texto1 = "En modo de un solo jugador, la tecla para manejar y avanzar es la flecha de dirección (Presiona la Flecha Derecha repetidamente).";
        mainPanel.add(crearBanner("MODO 1 JUGADOR", texto1));
        mainPanel.add(Box.createVerticalStrut(15));
        
        // Banner 2: Dos jugadores
        String texto2 = "En modo 2 jugadores, las teclas para avanzar son:\n• Jugador 1: Tecla 'D'\n• Jugador 2: Flecha Derecha";
        mainPanel.add(crearBanner("MODO 2 JUGADORES", texto2));
        mainPanel.add(Box.createVerticalStrut(15));
        
        // Banner 3: Pausar
        String texto3 = "Presiona la tecla 'ESC' (Escape) en cualquier momento de la carrera para abrir el menú de pausa.";
        mainPanel.add(crearBanner("PAUSAR JUEGO", texto3));
        mainPanel.add(Box.createVerticalStrut(25));
        
        // Botón Cerrar
        JButton btnCerrar = new JButton("Cerrar Manual");
        btnCerrar.setBackground(new Color(200, 40, 120));
        btnCerrar.setForeground(Color.WHITE);
        btnCerrar.setFont(new Font("Consolas", Font.BOLD, 14));
        btnCerrar.setOpaque(true);
        btnCerrar.setBorderPainted(false);
        btnCerrar.setFocusPainted(false);
        btnCerrar.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnCerrar.addActionListener(e -> dispose());
        
        mainPanel.add(btnCerrar);

        add(mainPanel);
    }
    
    // Método auxiliar para crear banners estilizados
    private JPanel crearBanner(String titulo, String texto) {
        JPanel panel = new JPanel(new BorderLayout(0, 5));
        panel.setBackground(new Color(45, 25, 80)); // Fondo ligeramente más claro
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(120, 40, 180), 2), // Borde morado
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        
        JLabel lblTop = new JLabel(titulo);
        lblTop.setForeground(new Color(255, 200, 50));
        lblTop.setFont(new Font("Consolas", Font.BOLD, 16));
        lblTop.setHorizontalAlignment(SwingConstants.CENTER);
        
        JTextArea txtBody = new JTextArea(texto);
        txtBody.setBackground(new Color(45, 25, 80));
        txtBody.setForeground(Color.WHITE);
        txtBody.setFont(new Font("Consolas", Font.PLAIN, 14));
        txtBody.setLineWrap(true);
        txtBody.setWrapStyleWord(true);
        txtBody.setEditable(false);
        txtBody.setFocusable(false);
        
        panel.add(lblTop, BorderLayout.NORTH);
        panel.add(txtBody, BorderLayout.CENTER);
        
        return panel;
    }
}
