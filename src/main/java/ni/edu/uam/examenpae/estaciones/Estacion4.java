package ni.edu.uam.examenpae.estaciones;

import ni.edu.uam.examenpae.util.ProcesoVentanilla;

public class Estacion4 {
    public static void main(String[] args) throws InterruptedException {
        // Primera prueba: dos ventanillas con trabajo suficiente para detenerlas.
        ProcesoVentanilla ventanillaA = new ProcesoVentanilla("A", 20);
        ProcesoVentanilla ventanillaB = new ProcesoVentanilla("B", 20);
        Thread hiloA = new Thread(ventanillaA, "Ventanilla-A");
        Thread hiloB = new Thread(ventanillaB, "Ventanilla-B");

        System.out.println("Estado inicial A: " + ventanillaA.getEstado() + " / " + hiloA.getState());
        System.out.println("Estado inicial B: " + ventanillaB.getEstado() + " / " + hiloB.getState());
        hiloA.start();
        hiloB.start();

        // El hilo principal observa el avance tras 700 ms y solicita la cancelación.
        Thread.sleep(700);
        System.out.println("Estado actual A: " + ventanillaA.getEstado() + " / " + hiloA.getState());
        System.out.println("Estado actual B: " + ventanillaB.getEstado() + " / " + hiloB.getState());
        ventanillaA.detener();
        ventanillaB.detener();
        System.out.println("Solicitud de detención A: " + ventanillaA.getEstado());
        System.out.println("Solicitud de detención B: " + ventanillaB.getEstado());
        hiloA.interrupt();
        hiloB.interrupt();

        // Solo el hilo principal espera y comprueba los estados finales.
        hiloA.join();
        hiloB.join();
        System.out.println("Resultado A: " + ventanillaA.getEstado() + " / " + hiloA.getState()
                + " (" + ventanillaA.getOperacionesRealizadas() + "/20 operaciones)");
        System.out.println("Resultado B: " + ventanillaB.getEstado() + " / " + hiloB.getState()
                + " (" + ventanillaB.getOperacionesRealizadas() + "/20 operaciones)");
        if (!"Detenido".equals(ventanillaA.getEstado())
                || !"Detenido".equals(ventanillaB.getEstado())
                || hiloA.getState() != Thread.State.TERMINATED
                || hiloB.getState() != Thread.State.TERMINATED) {
            throw new IllegalStateException("Las ventanillas no se detuvieron correctamente");
        }

        // Segunda prueba: una ventanilla completa sus dos operaciones sin cancelación.
        ProcesoVentanilla ventanillaNormal = new ProcesoVentanilla("Normal", 2);
        Thread hiloNormal = new Thread(ventanillaNormal, "Ventanilla-Normal");
        hiloNormal.start();
        hiloNormal.join();
        System.out.println("Resultado normal: " + ventanillaNormal.getEstado() + " / "
                + hiloNormal.getState() + " (" + ventanillaNormal.getOperacionesRealizadas()
                + "/2 operaciones)");
        if (!"Finalizado".equals(ventanillaNormal.getEstado())
                || ventanillaNormal.getOperacionesRealizadas() != 2
                || hiloNormal.getState() != Thread.State.TERMINATED) {
            throw new IllegalStateException("La ventanilla no finalizó normalmente");
        }
    }
}
