package cat.pratfp.servihub.ra2.teoria;

import java.util.concurrent.locks.ReentrantLock;

public class ComptadorAmbLock {

    private final ReentrantLock pany = new ReentrantLock();
    private int valor = 0;

    public void incrementa() {
        pany.lock();
        try {
            valor++;
        } finally {
            pany.unlock();
        }
    }

    public int valor() {
        pany.lock();
        try {
            return valor;
        } finally {
            pany.unlock();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ComptadorAmbLock c = new ComptadorAmbLock();
        Runnable suma = () -> {
            for (int i = 0; i < 100_000; i++) {
                c.incrementa();
            }
        };
        Thread a = new Thread(suma);
        Thread b = new Thread(suma);
        a.start();
        b.start();
        a.join();
        b.join();
        System.out.println("Total: " + c.valor());
    }
}
