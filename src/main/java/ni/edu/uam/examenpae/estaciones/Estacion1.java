package ni.edu.uam.examenpae.estaciones;

import ni.edu.uam.examenpae.util.ProcesoVentanilla;

public class Estacion1 {
    public static void main(String[] args) {
        Thread hiloA = new Thread(new ProcesoVentanilla("A"), "Ventanilla-A");
        Thread hiloB = new Thread(new ProcesoVentanilla("B"), "Ventanilla-B");

        System.out.println("Con start()");
        hiloA.start();
        hiloB.start();
        System.out.println(Thread.currentThread().getName()
                + " | terminó de pedir el arranque");
    }
}