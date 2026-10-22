package ni.edu.uam.examenpae.estaciones;

import java.util.concurrent.atomic.AtomicInteger;

public class Estacion3Atomic {

    // Opción alternativa utilizando operaciones atómicas a nivel de hardware
    public static class ContadorAtómico {
        private final AtomicInteger total = new AtomicInteger(0);

        public void incrementar() {
            total.incrementAndGet(); // Operación atómica integrada
        }

        public int getTotal() {
            return total.get();
        }
    }

    public static void main(String[] args) {
        System.out.println("--- Prueba con AtomicInteger (10 repeticiones) ---");

        for (int i = 1; i <= 10; i++) {
            ContadorAtómico contador = new ContadorAtómico();

            Runnable tarea = () -> {
                for (int j = 0; j < 1000; j++) {
                    contador.incrementar();
                }
            };

            Thread hilo1 = new Thread(tarea, "Hilo-1");
            Thread hilo2 = new Thread(tarea, "Hilo-2");

            hilo1.start();
            hilo2.start();

            try {
                hilo1.join();
                hilo2.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            System.out.println("Ejecución " + i + ": Resultado final = " + contador.getTotal());
        }
    }
}