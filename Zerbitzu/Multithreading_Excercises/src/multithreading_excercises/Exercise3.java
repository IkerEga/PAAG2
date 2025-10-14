
package multithreading_excercises;

/**
 *
 * @author egana.iker
 */
import java.util.concurrent.Semaphore;

public class Exercise3 {

    // Acumulador protegido con un semáforo (mutex)
    static class Acumulador {
        private long total = 0;
        private final Semaphore mutex = new Semaphore(1); // semáforo binario

        public void add(long parcial) {
            try {
                mutex.acquire();   // entrar a la sección crítica (gastamos ficha)
                total += parcial;  // actualizar la suma total
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } finally {
                mutex.release();   // salir de la sección crítica (conseguimos ficha)
            }
        }

        public long getTotal() {
            return total;
        }
    }

    // Hilo que suma un rango [inicio..fin]
    static class Trabajador extends Thread {
        private final long inicio, fin;
        private final Acumulador acc;
        private final Semaphore done; // semáforo para avisar que terminó

        public Trabajador(long inicio, long fin, Acumulador acc, Semaphore done) {
            this.inicio = inicio;
            this.fin = fin;
            this.acc = acc;
            this.done = done;
        }

        @Override
        public void run() {
            long parcial = 0;
            for (long i = inicio; i <= fin; i++) {
                parcial += i;
            }
            acc.add(parcial); // añadir mi parcial al total
            done.release();   // avisar que este hilo ha terminado
        }
    }

    public static void main(String[] args) {
        final long LIMITE = 1_000_000L;
        final int NUM_HILOS = 4;

        Acumulador acc = new Acumulador();
        Thread[] hilos = new Thread[NUM_HILOS];

        // Semáforo para esperar a que acaben los 4 hilos (empieza en 0)
        Semaphore done = new Semaphore(0);

        long tam = LIMITE / NUM_HILOS; // 250000
        long ini = 1;

        // Crear e iniciar hilos
        for (int t = 0; t < NUM_HILOS; t++) {
            long fin = (t == NUM_HILOS - 1) ? LIMITE : (ini + tam - 1);
                        /*
            long fin;
                if (t == NUM_HILOS - 1) {
                    fin = LIMITE;
                } else {
                    fin = ini + tam - 1;
                }
            */
            hilos[t] = new Trabajador(ini, fin, acc, done);
            hilos[t].start();
            ini = fin + 1;
        }

        // Esperar a que terminen los 4 (cada hilo hace un release)
        //CON ESTO VAMOS HACIENDO 1 A 1
        /*try {
            for (int i = 0; i < NUM_HILOS; i++) {
                done.acquire(); // consume un "permiso" por cada hilo terminado
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }*/
        
        //CON ESTO HACEMOS LOS 4 DE GOLPE
        try {
            done.acquire(NUM_HILOS); //De esta manera esperamos a que haya 4 permisos de golpe
 
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // Mostrar resultado
        System.out.println(acc.getTotal());
    }
}

