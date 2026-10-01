package modelo;

public abstract class Vehiculo {
    protected String nombre;
    protected int posicion;
    protected int velocidadBase;

    public Vehiculo(String nombre, int velocidadBase) {
        this.nombre = nombre;
        this.posicion = 0;
        this.velocidadBase = velocidadBase;
    }

    // Método que aplicará Polimorfismo (cada carro avanzará distinto)
    public abstract void avanzar();

    public int getPosicion() {
        return posicion;
    }

    public String getNombre() {
        return nombre;
    }
    public void reiniciar() {
        this.posicion = 0;
    }
}