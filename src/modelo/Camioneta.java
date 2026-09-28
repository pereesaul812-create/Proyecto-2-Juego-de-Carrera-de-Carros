package modelo;

public class Camioneta extends Vehiculo {
    public Camioneta(String nombre) {
        super(nombre, 8); // Velocidad base baja
    }

    @Override
    public void avanzar() {
        this.posicion += velocidadBase; 
    }
}