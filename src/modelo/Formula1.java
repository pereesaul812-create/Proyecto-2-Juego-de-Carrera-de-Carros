package modelo;

public class Formula1 extends Vehiculo {
    public Formula1(String nombre) {
        super(nombre, 18); 
    }

    @Override
    public void avanzar() {
        setPosicion(getPosicion() + getVelocidadBase()); 
    }
}