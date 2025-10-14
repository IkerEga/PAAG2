
package multithreading_excercises;

/**
 *
 * @author egana.iker
 */
class ThreadExample extends Thread {
    private volatile boolean stopRequested = false;
    private int n1;
    private int n2;
    private int temp;
    
    public ThreadExample() {
        n1 = 1;
        n2 = 1;
        temp = 0;
    }
    
    @Override
    public void run() {
        while (!stopRequested) {
            temp = n2;
            n2 = n2 + n1;
            n1 = temp;

            if (n1 == 1) {
                for (int i = 0; i <= 1; i++) {
                    System.out.println(n1);
                }
                System.out.println(n2);
            } else {
                System.out.println(n2);
            }
            if (n2 > 100) {
                stopThread();
            }
        }
    }
    
    public void stopThread() {
        stopRequested = true;
    }
}

public class Exercise1 {
    public static void main(String[] args) {
        ThreadExample thread = new ThreadExample();
        
        thread.start();
    }
}
