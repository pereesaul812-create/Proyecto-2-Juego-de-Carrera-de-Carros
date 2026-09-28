package modelo;
import java.util.Random;

public class MotoCarreras extends Vehiculo {
    public MotoCarreras(String nombre) {
        super(nombre, 5); // Velocidad base bajísima
    }

    @Override
    public void avanzar() {
        // Polimorfismo: Depende 100% de la suerte. O avanza poquísimo, o da un salto masivo.
        Random rand = new Random();
        this.posicion += velocidadBase + rand.nextInt(25); 
    }
}