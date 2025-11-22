/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package ariketa8_cola_de_impresión;

import java.util.ArrayList;
import java.util.List;

public class Ariketa8_Cola_De_Impresión {

    static final int sinDocumentos = -1;

    static class Impresion {

        private final List<Integer> listaDocumentos = new ArrayList<>();
        private int capacidad;

        public Impresion(int capacidad) {

            this.capacidad = capacidad;
        }

        public synchronized int sacar() throws InterruptedException {

            while (listaDocumentos.isEmpty()) {
                wait();
            }
            int documentos = listaDocumentos.remove(0);
            notifyAll();
            return documentos;
        }

        public synchronized void enviar(int documento) throws InterruptedException {

            while (listaDocumentos.size() == capacidad) {
                wait();
            }
            listaDocumentos.add(documento);
            notifyAll();
        }
    }

    static class Usuario extends Thread {

        private int cantidad;
        private Impresion impresion;

        public Usuario(String nombre, Impresion impresion, int cantidad) {

            super(nombre);
            this.impresion = impresion;
            this.cantidad = cantidad;
        }

        @Override
        public void run() {
            try {
                for (int i = 0; i < cantidad; i++) {
                    impresion.enviar(i);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    static class Impresora extends Thread {

        private Impresion impresion;
        private int documentosImpresos = 0;

        public Impresora(String nombre, Impresion impresion) {

            super(nombre);
            this.impresion = impresion;
        }

        public int getDocumentosImpresos() {
            return documentosImpresos;
        }

        @Override
        public void run() {

            try {
                while (true) {
                    int documento = impresion.sacar();
                    if (documento == sinDocumentos) {
                        break;
                    }
                    documentosImpresos++;
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {

        final int CAPACIDAD = 5;

        Impresion impresion = new Impresion(10);

        Usuario usuario1 = new Usuario("Ane", impresion, CAPACIDAD);
        Usuario usuario2 = new Usuario("Lukas", impresion, CAPACIDAD);
        Usuario usuario3 = new Usuario("Ander", impresion, CAPACIDAD);
        Impresora impresora = new Impresora("Impresora", impresion);

        usuario1.start();
        usuario2.start();
        usuario3.start();
        impresora.start();

        usuario1.join();
        usuario2.join();
        usuario3.join();

        impresion.enviar(sinDocumentos);
        impresora.join();

        int totalEnviados = CAPACIDAD * 3; //Depende de los hilos que le metas
        System.out.println("Total documentos enviados: " + totalEnviados);
        System.out.println("Total de documentos impresos: " + impresora.getDocumentosImpresos());
    }
}
