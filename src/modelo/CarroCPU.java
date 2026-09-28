package modelo;
import java.util.Random;

public class CarroCPU extends Vehiculo {
    
    private String dificultad; // Puede ser "Facil", "Medio" o "Dificil"

    public CarroCPU(String dificultad) {
        super("Computadora", 10); // Velocidad base de 10
        this.dificultad = dificultad;
    }

    @Override
    public void avanzar() {
        Random rand = new Random();
        int avance = velocidadBase;

        // Polimorfismo: El avance cambia según la dificultad
        switch (dificultad) {
            case "Facil":
                // Avanza lento (max 8 extra)
                avance += rand.nextInt(8); 
                break;
            case "Medio":
                // Avanza normal (max 15 extra, igual que un AutoDeportivo)
                avance += rand.nextInt(15); 
                break;
            case "Dificil":
                // Avanza rapidísimo (max 22 extra), es casi imbatible
                avance += rand.nextInt(22); 
                break;
            default:
                avance += rand.nextInt(10);
                break;
        }
        
        this.posicion += avance;
    }
}