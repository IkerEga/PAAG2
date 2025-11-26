/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package ariketa3_lista_compartida;

import java.util.List;
import java.util.ArrayList;

public class Ariketa3_Lista_Compartida {

    static class ListaCompartida {

        private final List<Integer> lista = new ArrayList<Integer>();

        //No hace falta hacer un bucle, solo añadimos un numero, si un hilo entra, otro no entrará hasta que termine
        public synchronized void añadirNumeros(int x) {
            lista.add(x);
            /* Si pusiesemos un bucle aqui, el hilo 1 añadiria sus 100 numeros, y luego el hilo 2
            
            for (int i = 0; i < veces; i++) {
                lista.add(i);
            }
             */
        }

        public synchronized int tamaño() {
            return lista.size();

        }
    }

    static class ImprimirNumeros extends Thread {

        private final int veces, base;
        private final ListaCompartida lista;

        public ImprimirNumeros(ListaCompartida lista, int veces, int base) {

            this.lista = lista;
            this.veces = veces;
            this.base = base;
        }

        @Override
        public void run() {
            //Aqui si que hay que usar bucle, porque tenemos que hacer una tarea 100 veces
            for (int i = 0; i < veces; i++) {
                lista.añadirNumeros(base + i);

                System.out.println("Añadido " + (base + i));
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {

        final int VECES = 100;
        final int BASE = 0;
        final int NUM_HILOS = 4;

        ListaCompartida lista = new ListaCompartida();
        Thread[] hilos = new Thread[NUM_HILOS];

        hilos[0] = new ImprimirNumeros(lista, VECES, 0);
        hilos[1] = new ImprimirNumeros(lista, VECES, 1000);
        hilos[2] = new ImprimirNumeros(lista, VECES, 2000);
        hilos[3] = new ImprimirNumeros(lista, VECES, 3000);

        for (Thread h : hilos) {
            h.start();
        }

        for (Thread h : hilos) {
            h.join();
        }

        int numerosTotalesEsperados = NUM_HILOS * VECES;
        int numerosReales = lista.tamaño();
        System.out.println("Numeros totales en la lista: " + numerosReales + "; //  Esperados --> " + numerosTotalesEsperados);
    }

}
