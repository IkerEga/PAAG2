/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package ariketa7_aparcamiento_compartido;

import java.util.ArrayList;
import java.util.List;

public class Ariketa7_Aparcamiento_Compartido {

    static final int aparcamientoCerrado = -1;

    static class Aparcamiento {

        private final List<Integer> listaCoches = new ArrayList<>();
        private final int capacidad;

        public Aparcamiento(int capacidad) {

            this.capacidad = capacidad;
        }

        public synchronized int sacarCoche() throws InterruptedException {

            while (listaCoches.isEmpty()) {
                wait();
            }
            int coche = listaCoches.remove(0);
            notifyAll();
            return coche;
        }

        public synchronized void aparcarCoche(int coche) throws InterruptedException {

            while (listaCoches.size() == capacidad) {
                wait();
            }
            listaCoches.add(coche);
            notifyAll();
        }
    }

    static class Coche extends Thread {

        private final Aparcamiento aparcamiento;
        private final int cantidad;

        public Coche(String nombre, Aparcamiento aparcamiento, int cantidad) {
            super(nombre);
            this.aparcamiento = aparcamiento;
            this.cantidad = cantidad;
        }

        @Override
        public void run() {
            try {
                for (int i = 0; i <= cantidad; i++) {
                    aparcamiento.aparcarCoche(i);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    static class Vigilante extends Thread {

        private final Aparcamiento aparcamiento;

        public Vigilante(String nombre, Aparcamiento aparcamiento) {

            super(nombre);
            this.aparcamiento = aparcamiento;
        }

        @Override
        public void run() {

            try {
                while (true) {
                    int coche = aparcamiento.sacarCoche();
                    if (coche == aparcamientoCerrado) {
                        break;
                    }
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {

        final int CAPACIDAD = 3;
        final int TOTAL_APARCAR = 5;

        Aparcamiento aparcamiento = new Aparcamiento(CAPACIDAD);
        Coche coche1 = new Coche("Coche 1", aparcamiento, TOTAL_APARCAR);
        Coche coche2 = new Coche("Coche 2", aparcamiento, TOTAL_APARCAR);
        Coche coche3 = new Coche("Coche 3", aparcamiento, TOTAL_APARCAR);
        Coche coche4 = new Coche("Coche 4", aparcamiento, TOTAL_APARCAR);
        Vigilante vigilante = new Vigilante("Vigilante", aparcamiento);

        coche1.start();
        coche2.start();
        coche3.start();
        coche4.start();
        vigilante.start();

        coche1.join();
        coche2.join();
        coche3.join();
        coche4.join();

        vigilante.join();
        aparcamiento.aparcarCoche(aparcamientoCerrado);

    }

}
