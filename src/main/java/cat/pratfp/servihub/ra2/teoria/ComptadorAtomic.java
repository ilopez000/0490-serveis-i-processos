package cat.pratfp.servihub.ra2.teoria;

import java.util.concurrent.atomic.AtomicInteger;

public class ComptadorAtomic {

    public static void main(String[] args) throws InterruptedException {
        AtomicInteger visites = new AtomicInteger(0);
        Runnable suma = () -> {
            for (int i = 0; i < 100_000; i++) {
                visites.incrementAndGet();
            }
        };
        Thread a = new Thread(suma);
        Thread b = new Thread(suma);
        a.start();
        b.start();
        a.join();
        b.join();
        System.out.println("Visites: " + visites.get());
    }
}
