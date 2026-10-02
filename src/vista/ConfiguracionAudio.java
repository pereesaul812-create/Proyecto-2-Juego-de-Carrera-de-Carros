package vista;

import javax.swing.*;
import java.awt.*;
import modelo.ReproductorAudio;

public class ConfiguracionAudio extends JDialog {
    
    public ConfiguracionAudio(Window parent) {
        super(parent, "Ajustes de Sonido", Dialog.ModalityType.APPLICATION_MODAL);
        setSize(350, 300);
        setLocationRelativeTo(parent);
        setUndecorated(true);
        
        JPanel mainPanel = new JPanel(new GridLayout(4, 1, 10, 10));
        mainPanel.setBackground(new Color(30, 15, 60));
        mainPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(255, 200, 50), 3),
                BorderFactory.createEmptyBorder(15, 20, 15, 20)
        ));
        
        JLabel lblTitulo = new JLabel("AJUSTES DE SONIDO", SwingConstants.CENTER);
        lblTitulo.setForeground(new Color(255, 200, 50));
        lblTitulo.setFont(new Font("Consolas", Font.BOLD, 18));
        mainPanel.add(lblTitulo);
        
        // Slider de Música
        JPanel panelMusica = new JPanel(new BorderLayout());
        panelMusica.setOpaque(false);
        JLabel lblMusica = new JLabel("Volumen de Música:");
        lblMusica.setForeground(Color.WHITE);
        lblMusica.setFont(new Font("Consolas", Font.BOLD, 14));
        JSlider sliderMusica = new JSlider(0, 100, ReproductorAudio.getInstancia().getVolumenMusica());
        sliderMusica.setOpaque(false);
        sliderMusica.setForeground(new Color(200, 40, 120)); // Magenta
        sliderMusica.addChangeListener(e -> {
            ReproductorAudio.getInstancia().setVolumenMusica(sliderMusica.getValue());
        });
        panelMusica.add(lblMusica, BorderLayout.NORTH);
        panelMusica.add(sliderMusica, BorderLayout.CENTER);
        mainPanel.add(panelMusica);
        
        // Slider de Efectos
        JPanel panelEfectos = new JPanel(new BorderLayout());
        panelEfectos.setOpaque(false);
        JLabel lblEfectos = new JLabel("Volumen de Efectos:");
        lblEfectos.setForeground(Color.WHITE);
        lblEfectos.setFont(new Font("Consolas", Font.BOLD, 14));
        JSlider sliderEfectos = new JSlider(0, 100, ReproductorAudio.getInstancia().getVolumenEfectos());
        sliderEfectos.setOpaque(false);
        sliderEfectos.setForeground(new Color(200, 40, 120)); // Magenta
        sliderEfectos.addChangeListener(e -> {
            ReproductorAudio.getInstancia().setVolumenEfectos(sliderEfectos.getValue());
            if (!sliderEfectos.getValueIsAdjusting()) {
                ReproductorAudio.getInstancia().reproducirEfecto("click.wav");
            }
        });
        panelEfectos.add(lblEfectos, BorderLayout.NORTH);
        panelEfectos.add(sliderEfectos, BorderLayout.CENTER);
        mainPanel.add(panelEfectos);
        
        // Botón Cerrar
        JButton btnCerrar = new JButton("Cerrar");
        btnCerrar.setBackground(new Color(200, 40, 120));
        btnCerrar.setForeground(Color.WHITE);
        btnCerrar.setFont(new Font("Consolas", Font.BOLD, 14));
        btnCerrar.setOpaque(true);
        btnCerrar.setBorderPainted(false);
        btnCerrar.setFocusPainted(false);
        btnCerrar.addActionListener(e -> {
            ReproductorAudio.getInstancia().reproducirEfecto("click.wav");
            dispose();
        });
        
        // Contenedor para el botón
        JPanel panelBoton = new JPanel();
        panelBoton.setOpaque(false);
        panelBoton.add(btnCerrar);
        
        mainPanel.add(panelBoton);
        
        add(mainPanel);
    }
}
