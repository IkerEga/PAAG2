package ariketa5_inventario_compartido;

public class Ariketa5_Inventario_Compartido {

    static class Inventario {

        private int stock = 100;

        public synchronized boolean vender(int unidades) {

            if (stock >= unidades) {
                
                stock -= unidades;
                return true;
            }
            return false;
        }

        public synchronized int getStock() {
            return stock;
        }

    }

    static class Vendedor extends Thread {

        private final String nombre;
        private final int intentos;
        private int ventasExitosas = 0;
        private final Inventario inventario;

        public Vendedor(String nombre, Inventario inventario, int intentos) {

            this.nombre = nombre;
            this.inventario = inventario;
            this.intentos = intentos;

        }

        @Override
        public void run() {

            for (int i = 1; i <= intentos; i++) {
                boolean ok = inventario.vender(1);

                if (ok) {
                    ventasExitosas++;
                }
            }
        }

        public int getVentasExitosas() {
            return ventasExitosas;
        }

        public String getVendedorNombre() {
            return nombre;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        final int STOCK_INICIAL = 100;
        final int NUM_HILOS = 4;
        final int INTENTOS_POR_VENDEDOR = 30;

        Inventario inventario = new Inventario();
        Vendedor[] hilos = new Vendedor[NUM_HILOS];

        hilos[0] = new Vendedor("Iker", inventario, INTENTOS_POR_VENDEDOR);
        hilos[1] = new Vendedor("Unai", inventario, INTENTOS_POR_VENDEDOR);
        hilos[2] = new Vendedor("Ander", inventario, INTENTOS_POR_VENDEDOR);
        hilos[3] = new Vendedor("Julen", inventario, INTENTOS_POR_VENDEDOR);

        for (Thread h : hilos) {
            h.start();
        }

        for (Thread h : hilos) {
            h.join();
        }
        
        int totalVentas = 0;
        for (Vendedor v : hilos) {
            totalVentas += v.getVentasExitosas();
        }
        
        System.out.println("Stock unicial: " + STOCK_INICIAL);
        System.out.println("Ventas totales realizadas: " + totalVentas);
        System.out.println("Stock final: " + inventario.getStock());
    }

}
