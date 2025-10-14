package multithreading_excercises;

/**
 *
 * @author egana.iker
 */
public class Exercise2 {

    // Monitor muy simple para acumular la suma total
    static class Acumulador {

        private long total = 0;

        public synchronized void add(long parcial) {
            total += parcial; // sección crítica
        }

        public long getTotal() {
            return total;
        }
    }

    // Hilo que suma un rango [inicio..fin] y lo añade al acumulador
    static class Trabajador extends Thread {

        private final long inicio, fin;
        private final Acumulador acc;

        public Trabajador(long inicio, long fin, Acumulador acc) {
            this.inicio = inicio;
            this.fin = fin;
            this.acc = acc;
        }

        @Override
        public void run() {
            long parcial = 0;
            for (long i = inicio; i <= fin; i++) {
                parcial += i;
            }
            acc.add(parcial); // añadir la parcial de este hilo
        }
    }

    public static void main(String[] args) throws InterruptedException {
        final long LIMITE = 1_000_000L;
        final int NUM_HILOS = 4;

        Acumulador acc = new Acumulador();
        Thread[] hilos = new Thread[NUM_HILOS];

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
            hilos[t] = new Trabajador(ini, fin, acc);
            hilos[t].start();
            ini = fin + 1;
        }

        // Esperar a que terminen
        for (Thread h : hilos) {
            h.join();
        }

        // Mostrar resultado
        System.out.println(acc.getTotal());
    }
}
