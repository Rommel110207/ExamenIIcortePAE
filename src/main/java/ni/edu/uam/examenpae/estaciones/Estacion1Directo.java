package ni.edu.uam.examenpae.estaciones;

import ni.edu.uam.examenpae.util.ProcesoVentanilla;

public class Estacion1Directo {
    public static void main(String[] args) {
        System.out.println("Con run() directo");
        new ProcesoVentanilla("A").run();
        new ProcesoVentanilla("B").run();
        System.out.println(Thread.currentThread().getName()
                + " | terminó");
    }
}
