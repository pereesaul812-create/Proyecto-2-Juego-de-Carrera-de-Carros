package modelo;

public abstract class Vehiculo {
    private String nombre;
    private int posicion;
    private int velocidadBase;

    public Vehiculo(String nombre, int velocidadBase) {
        this.nombre = nombre;
        this.posicion = 0;
        this.velocidadBase = velocidadBase;
    }

    public abstract void avanzar();

    // Getters y Setters obligatorios para POO
    public int getPosicion() { return posicion; }
    public void setPosicion(int posicion) { this.posicion = posicion; }
    
    public String getNombre() { return nombre; }
    public int getVelocidadBase() { return velocidadBase; }
    
    public void reiniciar() { this.posicion = 0; }
}
