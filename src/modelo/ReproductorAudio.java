package modelo;

import javax.sound.sampled.*;
import java.net.URL;

public class ReproductorAudio {
    
    private static ReproductorAudio instancia;
    private Clip musicaFondo;
    private long ultimoTiempoMotor = 0; 
    
    private int volumenMusica = 50; // Rango 0-100 (50 es el predeterminado)
    private int volumenEfectos = 80; // Rango 0-100 (80 es el predeterminado)
    
    private ReproductorAudio() {}
    
    public static ReproductorAudio getInstancia() {
        if (instancia == null) {
            instancia = new ReproductorAudio();
        }
        return instancia;
    }
    
    // Convertir de escala lineal (0-100) a decibeles logarítmicos
    private float calcularDecibeles(int porcentaje) {
        if (porcentaje <= 0) return -80.0f; // Silencio
        return 20.0f * (float) Math.log10(porcentaje / 100.0);
    }
    
    public void setVolumenMusica(int porcentaje) {
        this.volumenMusica = Math.max(0, Math.min(100, porcentaje));
        if (musicaFondo != null && musicaFondo.isOpen()) {
            try {
                FloatControl gainControl = (FloatControl) musicaFondo.getControl(FloatControl.Type.MASTER_GAIN);
                gainControl.setValue(calcularDecibeles(this.volumenMusica));
            } catch (Exception e) {}
        }
    }
    
    public void setVolumenEfectos(int porcentaje) {
        this.volumenEfectos = Math.max(0, Math.min(100, porcentaje));
    }
    
    public int getVolumenMusica() { return volumenMusica; }
    public int getVolumenEfectos() { return volumenEfectos; }
    
    public void reproducirMusica(String nombreArchivo) {
        detenerMusica(); 
        try {
            URL url = getClass().getResource("/sonidos/" + nombreArchivo);
            if (url != null) {
                AudioInputStream audioIn = AudioSystem.getAudioInputStream(url);
                musicaFondo = AudioSystem.getClip();
                musicaFondo.open(audioIn);
                
                try {
                    FloatControl gainControl = (FloatControl) musicaFondo.getControl(FloatControl.Type.MASTER_GAIN);
                    gainControl.setValue(calcularDecibeles(volumenMusica)); 
                } catch (IllegalArgumentException e) {}
                
                musicaFondo.loop(Clip.LOOP_CONTINUOUSLY);
                musicaFondo.start();
            } else {
                System.out.println("Música no encontrada: /sonidos/" + nombreArchivo);
            }
        } catch (Exception e) {
            System.out.println("Error de audio: " + e.getMessage());
        }
    }
    
    public void detenerMusica() {
        if (musicaFondo != null) {
            if (musicaFondo.isRunning()) {
                musicaFondo.stop();
            }
            musicaFondo.close();
            musicaFondo = null;
        }
    }
    
    public void reproducirEfecto(String nombreArchivo) {
        long tiempoActual = System.currentTimeMillis();
        if (nombreArchivo.equals("motor.wav")) {
            if ((tiempoActual - ultimoTiempoMotor) < 200) {
                return;
            }
            ultimoTiempoMotor = tiempoActual;
        }
        
        try {
            URL url = getClass().getResource("/sonidos/" + nombreArchivo);
            if (url != null) {
                AudioInputStream audioIn = AudioSystem.getAudioInputStream(url);
                Clip efecto = AudioSystem.getClip();
                efecto.open(audioIn);
                
                try {
                    FloatControl gainControl = (FloatControl) efecto.getControl(FloatControl.Type.MASTER_GAIN);
                    gainControl.setValue(calcularDecibeles(volumenEfectos)); 
                } catch (IllegalArgumentException e) {}
                
                efecto.start();
                
                efecto.addLineListener(event -> {
                    if (event.getType() == LineEvent.Type.STOP) {
                        event.getLine().close();
                    }
                });
            } else {
                System.out.println("Efecto no encontrado: /sonidos/" + nombreArchivo);
            }
        } catch (Exception e) {
            System.out.println("Error de efecto: " + e.getMessage());
        }
    }
}
