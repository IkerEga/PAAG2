package ariketa6_productor.consumidor.simple;

import java.util.ArrayList;
import java.util.List;

public class Ariketa6_ProductorConsumidorSimple {

    static final int sinPlato = -1;

    static class Mesa {

        private final List<Integer> listaPlatos = new ArrayList<>();
        private final int capacidad;

        public Mesa(int capacidad) {
            this.capacidad = capacidad;
        }

        public synchronized void dejar(int plato) throws InterruptedException {

            while (listaPlatos.size() == capacidad) {
                wait();
            }
            listaPlatos.add(plato);
            notifyAll();
        }

        public synchronized int coger() throws InterruptedException {

            while (listaPlatos.isEmpty()) {
                wait();
            }
            int plato = listaPlatos.remove(0);
            notifyAll();
            return plato;
        }

    }

    static class Cocinero extends Thread {

        private final Mesa mesa;
        private final int cantidad;

        public Cocinero(String nombre, Mesa mesa, int cantidad) {

            super(nombre);
            this.cantidad = cantidad;
            this.mesa = mesa;
        }

        @Override
        public void run() {
            try {
                for (int i = 0; i <= cantidad; i++) {
                    mesa.dejar(i);
                }
                mesa.dejar(sinPlato);

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

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
                    if (plato == sinPlato) {
                        break;
                    }
                    servidos++;
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
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
        System.out.println("Platos servidos por camarero: " + camarero.getServidos());
        System.out.println("OK: el programa termina cuando llega el SIN_PLATO.");
    }

}
