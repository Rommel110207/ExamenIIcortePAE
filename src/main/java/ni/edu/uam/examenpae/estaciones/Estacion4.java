package ni.edu.uam.examenpae.estaciones;

import ni.edu.uam.examenpae.util.ProcesoVentanilla;

public class Estacion4 {
    public static void main(String[] args) throws InterruptedException {
        // Primera prueba: detener dos ventanillas antes de que terminen.
        ProcesoVentanilla ventanillaA = new ProcesoVentanilla("A", 20);
        ProcesoVentanilla ventanillaB = new ProcesoVentanilla("B", 20);
        Thread hiloA = new Thread(ventanillaA, "Ventanilla-A");
        Thread hiloB = new Thread(ventanillaB, "Ventanilla-B");

        System.out.println("=== Prueba de detención ===");
        System.out.println("Antes de iniciar: " + ventanillaA.getEstado());
        hiloA.start();
        hiloB.start();

        // Espera de prueba: solamente se ejecuta desde main, NO desde JavaFX.
        Thread.sleep(700);
        System.out.println("A: " + ventanillaA.getEstado()
                + " | B: " + ventanillaB.getEstado());

        // Detener cambia la bandera; interrupt despierta a quien esté en sleep.
        ventanillaA.detener();
        ventanillaB.detener();
        System.out.println("Solicitud: A=" + ventanillaA.getEstado()
                + ", B=" + ventanillaB.getEstado());
        hiloA.interrupt();
        hiloB.interrupt();

        // Join se usa aquí por ser una prueba de consola, nunca en el hilo gráfico.
        hiloA.join();
        hiloB.join();
        System.out.println("Resultado A: " + ventanillaA.getEstado()
                + " (" + hiloA.getState() + ")");
        System.out.println("Resultado B: " + ventanillaB.getEstado()
                + " (" + hiloB.getState() + ")");

        // Segunda prueba: dejar que una ventanilla termine normalmente.
        System.out.println("\n=== Prueba de finalización normal ===");
        ProcesoVentanilla pruebaFinalizada = new ProcesoVentanilla("Prueba", 2);
        Thread hiloFinalizado = new Thread(pruebaFinalizada, "Ventanilla-Prueba");
        hiloFinalizado.start();
        hiloFinalizado.join();
        System.out.println("Resultado: " + pruebaFinalizada.getEstado());
    }
}
