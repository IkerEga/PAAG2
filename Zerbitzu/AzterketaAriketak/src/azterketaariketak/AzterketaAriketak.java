/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package azterketaariketak;

/**
 
Exercise 1: Concurrent Counter
Level: Basic
Goal: Learn basic synchronization.
Description:
Create a class Counter that holds an integer shared among multiple threads.
Start 5 threads that each increment the counter 1,000 times.
Ensure the final result is correct using synchronized blocks or semaphores.
 */
public class AzterketaAriketak {

    
    static class Contador {
        
        private int contador = 0;
        
        public synchronized void increment() {
            contador++;
        }
        
        public int getContador() {
            return contador;
        }

    }
    
    static class IncrementTask extends Thread {
        
        private final Contador counter;
        
        public IncrementTask(Contador counter) {
            this.counter = counter;
        }
        
        @Override
        public void run(){
            
            for (int i = 0; i < 1000; i++) {
                counter.increment();
            }
        }
    }
    
    public static void main(String[] args) throws InterruptedException {
        final int LIMITE = 1000;
        final int NUM_HILOS = 5;
        
        Contador counter = new Contador();
        Thread [] hilos = new Thread[NUM_HILOS];
        
        for (int i = 0; i < NUM_HILOS; i++) {
            hilos[i] = new IncrementTask(counter);
            hilos[i].start();
        }
        
        for (Thread h : hilos) {
            h.join();
        }
        
        System.out.println(counter.getContador());
    }
    
}
