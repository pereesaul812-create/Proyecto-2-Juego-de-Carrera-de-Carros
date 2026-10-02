package modelo;

public class Camioneta extends Vehiculo {
    public Camioneta(String nombre) {
        super(nombre, 8); 
    }

    @Override
    public void avanzar() {
        setPosicion(getPosicion() + getVelocidadBase()); 
    }
}