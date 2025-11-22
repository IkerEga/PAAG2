
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.Semaphore;

public class Ariketa6_CocineroCamarero_Semaforos {

    static final int SIN_PLATO = -1;

    // Recurso compartido: buffer acotado
    static class Mesa {

        private final Queue<Integer> buffer = new ArrayDeque<>();
        private final int capacidad;

        // Semáforos
        private final Semaphore empty; // huecos libres
        private final Semaphore full;  // elementos disponibles
        private final Semaphore mutex; // exclusión mutua

        public Mesa(int capacidad) {
            this.capacidad = capacidad;
            this.empty = new Semaphore(capacidad); // al principio todos los huecos están libres
            this.full = new Semaphore(0);         // no hay platos
            this.mutex = new Semaphore(1);         // candado libre
        }

        // Productor deja un plato
        public void dejar(int plato) throws InterruptedException {
            empty.acquire();      // espera hueco
            mutex.acquire();      // entra a sección crítica
            try {
                buffer.add(plato);
            } finally {
                mutex.release();  // sale de sección crítica aunque haya excepción
            }
            full.release();       // hay un plato más
        }

        // Consumidor coge un plato
        public int coger() throws InterruptedException {
            full.acquire();       // espera a que haya plato
            mutex.acquire();      // entra a sección crítica
            int plato;
            try {
                plato = buffer.remove();
            } finally {
                mutex.release();  // sale
            }
            empty.release();      // libera un hueco
            return plato;
        }
    }

    // Productor
    static class Cocinero extends Thread {

        private final Mesa mesa;
        private final int cantidad;

        public Cocinero(String nombre, Mesa mesa, int cantidad) {
            super(nombre);
            this.mesa = mesa;
            this.cantidad = cantidad;
        }

        @Override
        public void run() {
            try {
                for (int i = 1; i <= cantidad; i++) {
                    mesa.dejar(i);
                }
                // señal de fin
                mesa.dejar(SIN_PLATO);
            } catch (InterruptedException e) {
                interrupt();
            }
        }
    }

    // Consumidor
    static class Camarero extends Thread {

        private final Mesa mesa;
        private int servidos = 0;

        public Camarero(String nombre, Mesa mesa) {
            super(nombre);
            this.mesa = mesa;
        }

        @Override
        public void run() {
            try {
                while (true) {
                    int plato = mesa.coger();
                    if (plato == SIN_PLATO) {
                        // si hubiera más consumidores, podrías reinyectar el SIN_PLATO:
                        // mesa.dejar(SIN_PLATO);
                        break;
                    }
                    servidos++;
                }
            } catch (InterruptedException e) {
                interrupt();
            }
        }

        public int getServidos() {
            return servidos;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        final int CAPACIDAD = 5;
        final int TOTAL_PLATOS = 20;

        Mesa mesa = new Mesa(CAPACIDAD);
        Cocinero cocinero = new Cocinero("Cocinero", mesa, TOTAL_PLATOS);
        Camarero camarero = new Camarero("Camarero", mesa);

        cocinero.start();
        camarero.start();

        cocinero.join();
        camarero.join();

        System.out.println("Platos producidos: " + TOTAL_PLATOS);
        System.out.println("Platos servidos:   " + camarero.getServidos());
    }
}
