package modelo;

public class Formula1 extends Vehiculo {
    public Formula1(String nombre) {
        super(nombre, 18); // Velocidad base muy alta
    }

    @Override
    public void avanzar() {
        // Avance constante y largo por cada teclazo
        this.posicion += velocidadBase; 
    }
}