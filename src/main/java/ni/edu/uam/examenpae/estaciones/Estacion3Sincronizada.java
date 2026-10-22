package ni.edu.uam.examenpae.estaciones;

public class Estacion3Sincronizada {

    // Clase para el contador compartido
    public static class ContadorCompartido {
        private int total = 0;

        // Se agrega 'synchronized' para garantizar la exclusión mutua durante la escritura
        public synchronized void incrementar() {
            total++;
        }


        public synchronized int getTotal() {
            return total;
        }
    }

    public static void main(String[] args) {
        System.out.println("--- Prueba con synchronized (10 repeticiones) ---");

        for (int i = 1; i <= 10; i++) {
            ContadorCompartido contador = new ContadorCompartido();

            // Definición del trabajo para cada hilo
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
                // Se espera la finalización fuera de JavaFX para evitar problemas de bloqueo
                hilo1.join();
                hilo2.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            System.out.println("Ejecución " + i + ": Resultado final = " + contador.getTotal());
        }
    }
}
