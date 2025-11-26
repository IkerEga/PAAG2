/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package panaderia_con_semaforos;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.Semaphore;

public class Panaderia_con_semaforos {

    static final int TEMRINAR_HORNEADA = -1;

    static class Mostrador {

        private final Queue<Integer> listaPanes = new ArrayDeque<>();
        private final int capacidad;

        //Semáforos
        private final Semaphore empty;
        private final Semaphore full;
        private final Semaphore mutex;

        public Mostrador(int capacidad) {
            this.capacidad = capacidad;
            this.empty = new Semaphore(capacidad);
            this.full = new Semaphore(0);
            this.mutex = new Semaphore(1);
        }

        public void dejar(int pan) throws InterruptedException {

            empty.acquire();    //Miramos si hay hueco
            mutex.acquire();    //Entramos a sección crítica
            try {
                listaPanes.add(pan);    //Añadimos a la lista lo dejado
            } finally {
                mutex.release();    //Salimos de la sección crítica
            }
            full.release();     //Uno más en la lista añadido
        }

        public int coger() throws InterruptedException {

            full.acquire();
            mutex.acquire();
            int pan;

            try {
                pan = listaPanes.remove();
            } finally {
                mutex.release();
            }
            empty.release();
            return pan;
        }
    }

    static class Panadero extends Thread {

        private final Mostrador mostrador;
        private final int capacidad;

        public Panadero(String nombre, Mostrador mostrador, int capacidad) {

            super(nombre);
            this.mostrador = mostrador;
            this.capacidad = capacidad;
        }

        @Override
        public void run() {
            try {
                for (int i = 0; i < capacidad; i++) {
                    mostrador.dejar(i);
                }
                //mostrador.dejar(TEMRINAR_HORNEADA);

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    static class Dependiente extends Thread {

        private final Mostrador mostrador;

        public Dependiente(String nombre, Mostrador mostrador) {

            super(nombre);
            this.mostrador = mostrador;
        }

        @Override
        public void run() {

            try {
                while (true) {
                    int pan = mostrador.coger();
                    if (pan == TEMRINAR_HORNEADA) {
                        break;
                    }
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {

        final int CAPACIDAD = 4;
        final int TOTAL_BARRAS = 30;

        Mostrador mostrador = new Mostrador(CAPACIDAD);

        Panadero panadero = new Panadero("Javi", mostrador, TOTAL_BARRAS);
        Dependiente dependiente1 = new Dependiente("Emma", mostrador);
        Dependiente dependiente2 = new Dependiente("Fede", mostrador);

        // 1) arrancar
        panadero.start();
        dependiente1.start();
        dependiente2.start();

        // 2) esperar al productor
        panadero.join();

        // 3) enviar dos señales (una por dependiente)
        mostrador.dejar(TEMRINAR_HORNEADA);
        mostrador.dejar(TEMRINAR_HORNEADA);

        // 4) esperar a consumidores
        dependiente1.join();
        dependiente2.join();

    }

}
