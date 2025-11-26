/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ariketa2;



/**
 🧩 Exercise 2: Bank Account Deposits

Level: Basic
Goal: Practice synchronization on a shared object.

Description:
Create a class BankAccount that holds a balance (starting at 0).
Start 3 threads.
Each thread should deposit 100 units into the account 1,000 times.
Ensure the final balance is correct using synchronization.
 */
public class Ariketa2 {
    
    static class BankAccount {
        
        private long dinero = 0;
        
        public long getDinero() {
            
            return dinero;
        }
        
        public synchronized void deposito(int cantidad) {
            dinero += cantidad;
        }   
    }
    
    static class incrementMoney extends Thread {
        
        private final BankAccount cuenta;
        private final int veces, cantidad;
        
        public incrementMoney(BankAccount cuenta, int veces, int cantidad) {
            
            this.cuenta = cuenta;
            this.veces = veces;
            this.cantidad = cantidad;
        }
        
        @Override
        public void run() {
            for(int i = 0; i < veces; i ++) {
                cuenta.deposito(cantidad);
            }
        }
    }
    
    public static void main(String[] args) throws InterruptedException {
        
        final int LIMITE = 1000;
        final int CANTIDAD = 100;
        final int NUM_HILOS = 3;
        
        BankAccount cuenta = new BankAccount();
        Thread [] hilos = new Thread[NUM_HILOS];
        
        for (int i = 0; i < NUM_HILOS; i++) {
            hilos[i] = new incrementMoney(cuenta, LIMITE, CANTIDAD);
            hilos[i].start();
        }
        
        for (Thread h : hilos) {
            h.join();
        }
        
        System.out.println(cuenta.getDinero());
    }
}
