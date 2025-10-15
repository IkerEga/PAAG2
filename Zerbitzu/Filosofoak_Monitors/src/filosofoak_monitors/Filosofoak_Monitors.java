/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package filosofoak_monitors;

/**
 *
 * @author egana.iker
 */
public class Filosofoak_Monitors {

    // ===== MONITOR: Mesa =====
    static class Mesa {

        private final int NUM_FILOSOFOS = 5;
        private enum Estado {PENSANDO, COMIENDO}    //Guardamos los estados para saber si otro hilo puede comer
        private final Estado[] estados = new Estado[NUM_FILOSOFOS];

        public Mesa() { //Simplemente para inicializar a todos los filosofos pensando
            for (int i = 0; i < NUM_FILOSOFOS; i++) {
                estados[i] = Estado.PENSANDO;
            }
        }

        public synchronized void cogerPalillos(int id) throws InterruptedException {
            while (estados[izquierda(id)] == Estado.COMIENDO || estados[derecha(id)] == Estado.COMIENDO) {
                wait();
            }
            estados[id] = Estado.COMIENDO;
        }

        public synchronized void dejarPalillos(int id) {
            estados[id] = Estado.PENSANDO;
            notifyAll();
        }

        private int izquierda(int id) {
            /*
            Queremos restar 1 al indice para obtener el vecino de la izquierda
            id = 0 -> 0 -1 = -1. Como no queremos indices negativos le sumamos NUM_FILOSOFOS y luego hacemos "% NUM_FILOSOFOS"
            El vecino izquierdo de id = 3, es el id = 2;
            id = 3 ---> (3 + 5 - 1) % 5 --> 7 % 5 = 2. Dividimo 7 y 5 --- Entra una vez en el módulo y sobran 2. 
             */
            return (id + NUM_FILOSOFOS - 1) % NUM_FILOSOFOS;
        }

        private int derecha(int id) {
            /*
            Queremos sumar 1 al índice para obtener el vecino de la derecha
            id = 4 -> (4 + 1) & 5 = 0. Dividimos 5 y 5 --- Entra una vez en el módulo y sobran 0. Vecino derecho de id = 4 es id = 0.
             */
            return (id + 1) % NUM_FILOSOFOS;
        }
    }

    static class Filosofo extends Thread {

        private final Mesa mesa;    //Referencia al monitor
        private final int id;       //Identificador del Filosofo
        private final int tiempo;   //Tiempo para pensar y comer

        public Filosofo(Mesa mesa, int id, int tiempo) {
            this.mesa = mesa;
            this.id = id;
            this.tiempo = tiempo;
        }

        @Override
        public void run() {
            try {
                while (true) { //bucle infinito: pensar -> comer -> pensar

                    // Pensar
                    System.out.println("Filósofo " + id + " está pensando");
                    Thread.sleep((int) (Math.random() * tiempo));

                    // Intentar comer
                    System.out.println("Filósofo " + id + " intenta coger los palillos");
                    mesa.cogerPalillos(id);

                    // Comer
                    System.out.println("Filósofo " + id + " está comiendo");
                    Thread.sleep((int) (Math.random() * tiempo));

                    // Terminar de comer
                    mesa.dejarPalillos(id);
                    System.out.println("Filósofo " + id + " terminó de comer y vuelve a pensar");
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public static void main(String[] args) {
        final int NUM_FILOSOFOS = 5;
        final int tiempo = 2000;

        //Creamos la mesa donde se sentarán los filosofos a comer y pensar
        Mesa mesa = new Mesa();

        //Creamos los filosofos que queremos
        Filosofo[] filosofos = new Filosofo[NUM_FILOSOFOS];
        for (int i = 0; i < NUM_FILOSOFOS; i++) {
            filosofos[i] = new Filosofo(mesa, i, tiempo);
        }

        //Arrancamos los hilos
        for (int i = 0; i < NUM_FILOSOFOS; i++) {
            filosofos[i].start();
        }
    }
}
