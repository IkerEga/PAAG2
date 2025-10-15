package filosofoak_semaphore;

import java.util.concurrent.Semaphore;

/*
    En el ejercicio de semáfoos:
        - Los semaforos son el recurso compartido y la sincronizacion al mismo tiempo.
        - Cada filosodo directamente hace acquire() / release() sobre los semadotos de los palillos dentro de su propio run ()
        - Por eso, el flujo completo de decidir quien come y quien espera ocurre dentro de run(), no en un objeto monitor separado

    En resumen: En semaforos, el hilo lo gestiona directamente la sincronizacion, mientra que en monitores la sincronizacion estaba "externalizada" en el monitor
                con los metodos cogerPalillo() y dejarPalillo()
*/

public class Filosofoak_Semaphore {

    // Número de filósofos
    private static final int NUM_FILOSOFOS = 5;

    // Un semáforo por cada palillo
    private static final Semaphore[] palillos = new Semaphore[NUM_FILOSOFOS];

    public static void main(String[] args) {
        // Inicializamos los semáforos (cada palillo está libre, valor = 1)
        for (int i = 0; i < NUM_FILOSOFOS; i++) {
            palillos[i] = new Semaphore(1);
        }

        // Creamos e iniciamos los filósofos
        for (int i = 0; i < NUM_FILOSOFOS; i++) {
            new Filosofo(i).start();
        }
    }

    // Clase interna Filosofo
    static class Filosofo extends Thread {

        private final int id;

        public Filosofo(int id) {
            this.id = id;
        }

        @Override
        public void run() {
            try {
                while (true) {
                    // Pensar
                    System.out.println("Filósofo " + id + " está pensando");
                    Thread.sleep((int) (Math.random() * 2000));

                    // Intenta Comer
                    System.out.println("Filósofo " + id + " intenta coger los palillos");

                    // Determinamos qué palillo coger primero
                    int palilloIzquierdo = id;
                    int palilloDerecho = (id + 1) % NUM_FILOSOFOS;

                    // Estrategia del último filósofo
                    if (id == NUM_FILOSOFOS - 1) { // último filósofo
                        // Invierte el orden
                        int temp = palilloIzquierdo;
                        palilloIzquierdo = palilloDerecho;
                        palilloDerecho = temp;
                    }

                    // Coger Palillos
                    palillos[palilloIzquierdo].acquire();
                    palillos[palilloDerecho].acquire();

                    // Comer
                    System.out.println("Filósofo " + id + " está comiendo");
                    Thread.sleep((int) (Math.random() * 2000));

                    // Soltar los palillos
                    palillos[palilloIzquierdo].release();
                    palillos[palilloDerecho].release();
                    System.out.println("Filósofo " + id + " terminó de comer");
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

    }

}
