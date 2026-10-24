package ni.edu.uam.examenpae.util;

public class ProcesoVentanilla implements Runnable {
    private final String nombre;
    private final int cantidadOperaciones;
    private volatile boolean activo = true;
    private volatile String estado = "En espera";
    private volatile int operacionesRealizadas = 0;

    // Conserva el comportamiento de las estaciones anteriores: cinco operaciones.
    public ProcesoVentanilla(String nombre) {
        this(nombre, 5);
    }

    // Permite configurar cuántas operaciones procesará la ventanilla.
    public ProcesoVentanilla(String nombre, int cantidadOperaciones) {
        if (cantidadOperaciones <= 0) {
            throw new IllegalArgumentException("La cantidad de operaciones debe ser positiva");
        }
        this.nombre = nombre;
        this.cantidadOperaciones = cantidadOperaciones;
    }

    // Solicita la detención sin cambiar un estado que ya es definitivo.
    public synchronized void detener() {
        if ("Detenido".equals(estado) || "Finalizado".equals(estado)) {
            return;
        }
        activo = false;
        estado = "Deteniendo";
    }

    public String getEstado() {
        return estado;
    }

    public int getOperacionesRealizadas() {
        return operacionesRealizadas;
    }

    @Override
    public void run() {
        // Coordina el inicio con una posible solicitud de detención inmediata.
        synchronized (this) {
            if (activo && !Thread.currentThread().isInterrupted()) {
                estado = "Ejecutando";
            }
        }

        for (int i = 1; i <= cantidadOperaciones; i++) {
            // Comprueba la cancelación antes de comenzar cada operación.
            if (!activo || Thread.currentThread().isInterrupted()) {
                break;
            }

            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                // sleep limpia la señal; se restablece antes de salir.
                Thread.currentThread().interrupt();
                break;
            }

            // Una operación interrumpida durante la espera no se cuenta como realizada.
            if (!activo || Thread.currentThread().isInterrupted()) {
                break;
            }
            operacionesRealizadas++;
            System.out.println(Thread.currentThread().getName()
                    + " | " + nombre + " procesa " + i);
        }

        // Publica el resultado final y el número de operaciones completadas.
        synchronized (this) {
            estado = operacionesRealizadas == cantidadOperaciones ? "Finalizado" : "Detenido";
        }
        System.out.println(Thread.currentThread().getName() + " | " + nombre
                + " | " + estado + " (" + operacionesRealizadas + "/"
                + cantidadOperaciones + " operaciones)");
    }
}
