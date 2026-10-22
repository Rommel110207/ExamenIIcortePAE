package ni.edu.uam.examenpae.util;

public class ProcesoVentanilla implements Runnable {
    private final String nombre;
    private final int cantidadOperaciones;

    // Volatile permite que otro hilo vea el cambio al solicitar la detención.
    private volatile boolean activo = true;
    private volatile String estado = "En espera";

    // Conserva el constructor que usan las estaciones 1 y 2.
    public ProcesoVentanilla(String nombre) {
        this(nombre, 5);
    }

    // Permite cambiar la duración de la prueba sin modificar las otras estaciones.
    public ProcesoVentanilla(String nombre, int cantidadOperaciones) {
        if (cantidadOperaciones <= 0) {
            throw new IllegalArgumentException("La cantidad de operaciones debe ser positiva.");
        }
        this.nombre = nombre;
        this.cantidadOperaciones = cantidadOperaciones;
    }

    // La ventanilla deja de aceptar más trabajo en cuanto detecta la solicitud.
    public void detener() {
        if (!estado.equals("Detenido") && !estado.equals("Finalizado")) {
            activo = false;
            estado = "Deteniendo";
        }
    }

    public String getEstado() {
        return estado;
    }

    @Override
    public void run() {
        int completadas = 0;
        if (activo && !Thread.currentThread().isInterrupted()) {
            estado = "Ejecutando";
        }

        // Se verifica la bandera y la interrupción antes de cada operación.
        while (activo && !Thread.currentThread().isInterrupted()
                && completadas < cantidadOperaciones) {
            completadas++;
            System.out.println(Thread.currentThread().getName()
                    + " | " + nombre + " procesa " + completadas);

            // No es necesario esperar después de completar la última operación.
            if (completadas == cantidadOperaciones) {
                break;
            }

            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                // Se recupera la señal y se sale: nunca se ignora la interrupción.
                Thread.currentThread().interrupt();
                break;
            }
        }

        // Si hizo todas las operaciones finalizó; de lo contrario fue detenido.
        estado = completadas == cantidadOperaciones ? "Finalizado" : "Detenido";
        System.out.println(Thread.currentThread().getName() + " | " + estado);
    }
}
