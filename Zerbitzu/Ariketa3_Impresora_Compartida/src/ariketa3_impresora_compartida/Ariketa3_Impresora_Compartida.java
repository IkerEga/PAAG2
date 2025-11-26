/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package ariketa3_impresora_compartida;

/**
 *
 * @author egana.iker
 */
public class Ariketa3_Impresora_Compartida {

    static class Impresora {
        public synchronized void imprimirDocumento(String nombre, int veces) {
            
            System.out.println("== Comienza impresión de: " + nombre + " ==");
            for (int i = 1; i <= veces; i++) {
                System.out.println("[" + nombre + "] página " + i + " de " + veces);
                // (Opcional) Simular tiempo de impresión:
                // try { Thread.sleep(5); } catch (InterruptedException ignored) {}
            }
            System.out.println("== Termina impresión de: " + nombre + " ==\n");
        }
    }

    // Tarea: cada hilo pide a la impresora imprimir su documento 'veces' páginas
    static class ImprimirTarea extends Thread {
        
        private final Impresora impresora;
        private final String nombreDocumento;
        private final int veces;

        public ImprimirTarea(Impresora impresora, String nombreDocumento, int veces) {
            this.impresora = impresora;
            this.nombreDocumento = nombreDocumento;
            this.veces = veces;
        }

        @Override
        public void run() {
            impresora.imprimirDocumento(nombreDocumento, veces);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        final int VECES = 5;
        final int NUM_HILOS = 3;

        Impresora impresora = new Impresora();
        Thread[] hilos = new Thread[NUM_HILOS];

        hilos[0] = new ImprimirTarea(impresora, "Usuario A - Informe", VECES);
        hilos[1] = new ImprimirTarea(impresora, "Usuario B - Factura", VECES);
        hilos[2] = new ImprimirTarea(impresora, "Usuario C - Carta", VECES);

        for (Thread h : hilos){
            h.start();
        }
        for (Thread h : hilos){
            h.join();
        }

        System.out.println("Impresiones finalizadas.");
    }
}