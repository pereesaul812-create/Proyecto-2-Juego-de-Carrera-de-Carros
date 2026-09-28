package vista;
import javax.swing.JOptionPane;

// TODO (Para el equipo): Crear un JFrame Form en NetBeans llamado MenuPrincipal
// Diseñar la ventana con un título divertido, un botón "Iniciar Carrera" y un botón "Manual de Usuario".

public class MenuPrincipal extends javax.swing.JFrame {
    
    // Este código irá dentro del botón "Iniciar Carrera" que arrastren en NetBeans
    private void btnIniciarCarreraActionPerformed(java.awt.event.ActionEvent evt) {
        try {
            // Aquí abriremos la ventana de la pista
            // PistaCarrera pista = new PistaCarrera();
            // pista.setVisible(true);
            // this.dispose(); 
            
            System.out.println("Abriendo la pista...");
            
        } catch (Exception e) {
            // Manejo de excepciones obligatorio según rúbrica
            JOptionPane.showMessageDialog(this, "Error al cargar la carrera: " + e.getMessage(), "Falla del Motor", JOptionPane.ERROR_MESSAGE);
        }
    }
}