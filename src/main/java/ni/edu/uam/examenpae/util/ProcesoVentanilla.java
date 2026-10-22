package ni.edu.uam.examenpae.util;

public class ProcesoVentanilla implements Runnable
{
    private final String nombre;

    public ProcesoVentanilla(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public void run() {
        for (int i = 1; i <= 5; i++) {
            if (Thread.currentThread().isInterrupted()) return;

            System.out.println(Thread.currentThread().getName()
                    + " | " + nombre + " procesa " + i);

            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
        }
    }
}
