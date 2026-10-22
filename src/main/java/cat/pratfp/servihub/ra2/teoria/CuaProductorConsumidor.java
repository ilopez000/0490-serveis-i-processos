package cat.pratfp.servihub.ra2.teoria;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class CuaProductorConsumidor {

    public static void main(String[] args) throws InterruptedException {
        BlockingQueue<String> cua = new ArrayBlockingQueue<>(3);

        Thread productor = new Thread(() -> {
            try {
                for (int i = 1; i <= 6; i++) {
                    cua.put("pizza " + i);
                    System.out.println("Forn: he posat la pizza " + i);
                }
                cua.put("FI");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        Thread consumidor = new Thread(() -> {
            try {
                String pizza = cua.take();
                while (!pizza.equals("FI")) {
                    System.out.println("    Repartidor: m'emporto la " + pizza);
                    Thread.sleep(200);
                    pizza = cua.take();
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        productor.start();
        consumidor.start();
        productor.join();
        consumidor.join();
        System.out.println("Totes les pizzes repartides");
    }
}
