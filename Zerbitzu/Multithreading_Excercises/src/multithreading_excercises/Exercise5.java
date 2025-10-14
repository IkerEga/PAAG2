/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package multithreading_excercises;

/**
 *
 * @author egana.iker
 */
import java.util.concurrent.Semaphore;

public class Exercise5 {

    static final int POISON = -1;

    // Buffer acotado con semáforos (sin synchronized)
    static class BoundedBuffer {
        private final int[] data;
        private int in = 0, out = 0;
        private final Semaphore empty; // huecos libres
        private final Semaphore full;  // ítems disponibles
        private final Semaphore mutex; // exclusión mutua

        public BoundedBuffer(int capacity) {
            data = new int[capacity];
            empty = new Semaphore(capacity);
            full  = new Semaphore(0);
            mutex = new Semaphore(1);
        }

        public void put(int x) throws InterruptedException {
            empty.acquire();       // esperar hueco
            mutex.acquire();       // entrar sección crítica
            data[in] = x;
            in = (in + 1) % data.length;
            mutex.release();       // salir sección crítica
            full.release();        // hay un ítem más
        }

        public int take() throws InterruptedException {
            full.acquire();        // esperar ítem
            mutex.acquire();       // entrar sección crítica
            int x = data[out];
            out = (out + 1) % data.length;
            mutex.release();       // salir sección crítica
            empty.release();       // hay un hueco más
            return x;
        }
    }

    // Productor simple
    static class Producer extends Thread {
        private final BoundedBuffer buf;
        private final int count;
        private final int base; // solo para distinguir valores (opcional)

        public Producer(String name, BoundedBuffer buf, int count, int base) {
            super(name);
            this.buf = buf;
            this.count = count;
            this.base = base;
        }

        @Override
        public void run() {
            try {
                for (int i = 1; i <= count; i++) {
                    buf.put(base + i);
                    // System.out.println(getName() + " produjo " + (base + i));
                }
            } catch (InterruptedException e) {
                interrupt();
            }
        }
    }

    // Consumidor simple
    static class Consumer extends Thread {
        private final BoundedBuffer buf;

        public Consumer(String name, BoundedBuffer buf) {
            super(name);
            this.buf = buf;
        }

        @Override
        public void run() {
            try {
                while (true) {
                    int v = buf.take();
                    if (v == POISON) break;
                    // "Procesar" v (aquí lo omitimos para mantenerlo simple)
                    // System.out.println(getName() + " consumió " + v);
                }
            } catch (InterruptedException e) {
                interrupt();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        final int BUFFER_SIZE = 5;
        final int PRODUCERS = 2;
        final int CONSUMERS = 3;
        final int ITEMS_PER_PRODUCER = 20;

        BoundedBuffer buffer = new BoundedBuffer(BUFFER_SIZE);

        // Crear productores (bases diferentes para distinguir valores si imprimes)
        Thread p1 = new Producer("P1", buffer, ITEMS_PER_PRODUCER, 1000);
        Thread p2 = new Producer("P2", buffer, ITEMS_PER_PRODUCER, 2000);

        // Crear consumidores
        Thread c1 = new Consumer("C1", buffer);
        Thread c2 = new Consumer("C2", buffer);
        Thread c3 = new Consumer("C3", buffer);

        // Arrancar
        p1.start(); p2.start();
        c1.start(); c2.start(); c3.start();

        // Esperar a productores
        p1.join();
        p2.join();

        // Enviar 1 POISON por consumidor para que paren
        for (int i = 0; i < CONSUMERS; i++) {
            buffer.put(POISON);
        }

        // Esperar a consumidores
        c1.join(); c2.join(); c3.join();

        System.out.println("Fin: productores y consumidores han terminado.");
    }
}
