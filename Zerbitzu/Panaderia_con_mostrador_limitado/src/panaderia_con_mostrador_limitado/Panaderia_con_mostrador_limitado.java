
package panaderia_con_mostrador_limitado;

import java.util.ArrayList;
import java.util.List;

public class Panaderia_con_mostrador_limitado {

    static final int TERMINAR_HORNEADA = -1;
    
    static class Mostrador {
        
        private final List<Integer> listaPanes = new ArrayList<>();
        private final int capacidad;
        
        public Mostrador (int capacidad) {
            this.capacidad = capacidad;
        }
        
        public synchronized void dejar (int pan) throws InterruptedException {
            
            while (listaPanes.size() == capacidad) {
                wait();
            }            
            listaPanes.add(pan);
            notifyAll();
        }
        
        public synchronized int coger() throws InterruptedException {
            
            while(listaPanes.isEmpty()) {
                wait();
            }
            int pan = listaPanes.remove(0);
            notifyAll();
            return pan;
        }
        
    }
    
    static class Panadero extends Thread {
        
        private final Mostrador mostrador;
        private final int capacidad;
        
        public Panadero (String nombre, Mostrador mostrador, int capacidad) {
            
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
                mostrador.dejar(TERMINAR_HORNEADA);
                
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
    
    static class Dependiente extends Thread {
        
        private final Mostrador mostrador;
        
        public Dependiente (String nombre, Mostrador mostrador) {
            
            super(nombre);
            this.mostrador = mostrador;
        }
        
        @Override
        public void run() {
            
            try {
                while (true) {
                    int pan = mostrador.coger();
                    if (pan == TERMINAR_HORNEADA) {
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
        
        Panadero panadero = new Panadero ("Javi", mostrador, TOTAL_BARRAS);
        Dependiente dependiente1 = new Dependiente ("Emma", mostrador);
        Dependiente dependiente2 = new Dependiente ("Fede", mostrador);
        
        panadero.start();
        dependiente1.start();
        dependiente2.start();
        
        panadero.join();
        dependiente1.join();
        dependiente2.join();
        
        mostrador.dejar(TERMINAR_HORNEADA);
        mostrador.dejar(TERMINAR_HORNEADA);
        
    }
    
}
