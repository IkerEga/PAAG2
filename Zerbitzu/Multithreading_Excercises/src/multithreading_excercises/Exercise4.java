/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package multithreading_excercises;

/**
 *
 * @author egana.iker
 */

import java.util.LinkedList;
import java.util.Queue;

public class Exercise4 {

    // Valor especial para indicar "no hay más trabajo"
    static final int POISON = -1;

    // ===== MONITOR: Buffer acotado =====
    static class Buffer {
        private final Queue<Integer> cola = new LinkedList<>();
        private final int capacidad;

        public Buffer(int capacidad) {
            this.capacidad = capacidad;
        }

        public synchronized void put(int valor) throws InterruptedException {
            while (cola.size() == capacidad) {
                wait(); // esperar a que haya hueco
            }
            cola.add(valor);
            notifyAll(); // avisar a consumidores
        }

        public synchronized int take() throws InterruptedException {
            while (cola.isEmpty()) {
                wait(); // esperar a que haya elementos
            }
            int v = cola.remove();
            notifyAll(); // avisar a productores
            return v;
        }
    }

    // ===== Productor =====
    static class Producer extends Thread {
        private final Buffer buffer;
        private final int cantidad; // cuántos ítems produce
        private final int offset;   // para distinguir valores

        public Producer(String nombre, Buffer buffer, int cantidad, int offset) {
            super(nombre);
            this.buffer = buffer;
            this.cantidad = cantidad;
            this.offset = offset;
        }

        @Override
        public void run() {
            try {
                for (int i = 1; i <= cantidad; i++) {
                    int valor = offset + i;     // p.ej. 1001, 1002...
                    buffer.put(valor);
                    // pequeño descanso para ver la concurrencia (opcional)
                    Thread.sleep(2);
                }
                // System.out.println(getName() + " terminó");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    // ===== Consumidor =====
    static class Consumer extends Thread {
        private final Buffer buffer;

        public Consumer(String nombre, Buffer buffer) {
            super(nombre);
            this.buffer = buffer;
        }

        @Override
        public void run() {
            try {
                while (true) {
                    int v = buffer.take();
                    if (v == POISON) {
                        // System.out.println(getName() + " se detiene");
                        break;
                    }
                    // "Procesar" (aquí solo mostramos o podrías sumar, etc.)
                    // System.out.println(getName() + " consumió: " + v);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    // ===== main =====
    public static void main(String[] args) throws InterruptedException {
        final int TAM_BUFFER = 5;
        final int NUM_PRODUCTORES = 2;
        final int NUM_CONSUMIDORES = 3;
        final int PRODUCE_CADA_UNO = 20; // sencillo y finito

        Buffer buffer = new Buffer(TAM_BUFFER);

        // Crear productores (con offsets distintos para distinguir valores)
        Thread p1 = new Producer("P1", buffer, PRODUCE_CADA_UNO, 1000);
        Thread p2 = new Producer("P2", buffer, PRODUCE_CADA_UNO, 2000);

        // Crear consumidores
        Thread c1 = new Consumer("C1", buffer);
        Thread c2 = new Consumer("C2", buffer);
        Thread c3 = new Consumer("C3", buffer);

        // Arrancar hilos
        p1.start();
        p2.start();
        c1.start();
        c2.start();
        c3.start();

        // Esperar a que los productores terminen
        p1.join();
        p2.join();

        // Enviar "píldoras venenosas" (una por consumidor) para que paren
        for (int i = 0; i < NUM_CONSUMIDORES; i++) {
            buffer.put(POISON);
        }

        // Esperar a que los consumidores terminen
        c1.join();
        c2.join();
        c3.join();

        // Resultado final (no hay un "total", solo demostración de sincronización)
        System.out.println("Fin: productores y consumidores han terminado.");
    }
}
