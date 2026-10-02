package modelo;
import java.util.Random;

public class CarroCPU extends Vehiculo {
    private String dificultad;

    public CarroCPU(String dificultad) {
        super("Computadora", 10); 
        this.dificultad = dificultad;
    }

    @Override
    public void avanzar() {
        Random rand = new Random();
        int avance = getVelocidadBase();

        switch (dificultad) {
            case "Fácil": avance += rand.nextInt(2); break;
            case "Medio": avance += rand.nextInt(4); break;
            case "Difícil": avance += rand.nextInt(8); break;
            case "Dios": avance += rand.nextInt(12); break;
            default: avance += rand.nextInt(3); break;
        }
        
        setPosicion(getPosicion() + avance);
    }
}