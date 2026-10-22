package cat.pratfp.servihub.ra2.teoria;

public class ComptadorSegur {

    private int valor = 0;

    public synchronized void incrementa() {
        valor++;
    }

    public synchronized int valor() {
        return valor;
    }

    public static void main(String[] args) throws InterruptedException {
        ComptadorSegur c = new ComptadorSegur();
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
