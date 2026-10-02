package modelo;
import java.util.Random;

public class MotoCarreras extends Vehiculo {
    public MotoCarreras(String nombre) {
        super(nombre, 5); 
    }

    @Override
    public void avanzar() {
        Random rand = new Random();
        setPosicion(getPosicion() + getVelocidadBase() + rand.nextInt(25)); 
    }
}