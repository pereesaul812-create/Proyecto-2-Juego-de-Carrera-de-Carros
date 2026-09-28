package main;
import vista.MenuPrincipal;

public class Main {
    public static void main(String[] args) {
        // Invocamos el menú inicial de forma segura
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MenuPrincipal().setVisible(true);
            }
        });
    }
}