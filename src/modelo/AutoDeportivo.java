package modelo;
import java.util.Random;

public class AutoDeportivo extends Vehiculo {

    public AutoDeportivo(String nombre) {
        super(nombre, 10); // Velocidad base de 10
    }

    @Override
    public void avanzar() {
        // Polimorfismo: El auto deportivo avanza rápido pero con saltos aleatorios
        Random rand = new Random();
        int avance = velocidadBase + rand.nextInt(15); 
        this.posicion += avance;
    }
}