package modelo;
import java.util.Random;

public class AutoDeportivo extends Vehiculo {
    public AutoDeportivo(String nombre) {
        super(nombre, 10); 
    }

    @Override
    public void avanzar() {
        Random rand = new Random();
        int avance = getVelocidadBase() + rand.nextInt(15); 
        setPosicion(getPosicion() + avance); // Usando Setters y Getters
    }
}