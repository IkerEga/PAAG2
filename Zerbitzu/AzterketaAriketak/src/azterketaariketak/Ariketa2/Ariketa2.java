/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package azterketaariketak.Ariketa2;

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
        
        public long sumarDinero() {
            
            return dinero;
        }
        
        public synchronized void sumar() {
            dinero++;
        }   
    }
    
    static class incrementMoney extends Thread {
        
        private final BankAccount cuenta;
        
        public incrementMoney(BankAccount cuenta) {
            
            this.cuenta = cuenta;
        }
        
        @Override
        public void run() {
            for(int i = 0; i < 1000; i ++) {
                cuenta.sumar();
            }
        }
    }
    
    public static void main(String[] args) throws InterruptedException {
        
        final int limite = 1000;
        final int NUM_HILOS = 3;
        
        BankAccount cuenta = new BankAccount();
        Thread [] hilos = new Thread[NUM_HILOS];
        
        for (int i = 0; i < NUM_HILOS; i++) {
            hilos[i] = new incrementMoney(cuenta);
            hilos[i].start();
        }
        
        System.out.println(cuenta.sumarDinero());
    }
}
