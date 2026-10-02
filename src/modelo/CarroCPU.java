package modelo;
import java.util.Random;

public class CarroCPU extends Vehiculo {
    
    private String dificultad;

    public CarroCPU(String dificultad) {
        super("Computadora", 10); // Velocidad base
        this.dificultad = dificultad;
    }

    @Override
    public void avanzar() {
        Random rand = new Random();
        int avance = velocidadBase;

        // El texto debe coincidir EXACTAMENTE con lo que envía el MenuPrincipal (incluyendo tildes)
        switch (dificultad) {
            case "Fácil":
                // Súper lento, ideal para probar que el jugador gane
                avance += rand.nextInt(5); 
                break;
            case "Medio":
                // Desafío normal, avanza a buen ritmo
                avance += rand.nextInt(15); 
                break;
            case "Difícil":
                // El jugador tendrá que machacar la tecla rapidísimo para ganar
                avance += rand.nextInt(35); 
                break;
            case "Dios":
                // Modo locura: casi imbatible
                avance += rand.nextInt(60); 
                break;
            default:
                // Si algo falla, avanza normal
                avance += rand.nextInt(10);
                break;
        }
        
        this.posicion += avance;
    }
}   