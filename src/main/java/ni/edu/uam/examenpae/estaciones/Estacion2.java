package ni.edu.uam.examenpae.estaciones;

import ni.edu.uam.examenpae.util.ProcesoVentanilla;

public class Estacion2 {
    public static void main(String[] args) throws InterruptedException {
        Thread hilo = new Thread(new ProcesoVentanilla("A"), "Ventanilla-A");
        System.out.println("Antes: " + hilo.getState());

        // El monitor es otro hilo: mira el estado cada 50 milisegundos
        // y solo escribe cuando el estado cambia.
        Thread monitor = new Thread(() -> {
            Thread.State anterior = null;
            while (true) {
                Thread.State actual = hilo.getState();
                if (actual != anterior) {
                    System.out.println("[Monitor] " + hilo.getName() + " -> " + actual);
                    anterior = actual;
                }
                if (actual == Thread.State.TERMINATED) break;
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        }, "Monitor");

        monitor.start();
        Thread.sleep(200);   // damos tiempo para que el monitor vea NEW

        hilo.start();
        System.out.println("Después de start: " + hilo.getState());
    }
}