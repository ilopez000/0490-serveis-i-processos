package cat.pratfp.servihub.ra2.teoria;

public class Vigilant {

    public static void main(String[] args) throws InterruptedException {
        Thread vigilant = new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                System.out.println("Vigilo...");
                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
            System.out.println("Vigilant: m'han demanat que pari. Plego!");
        });

        vigilant.start();
        Thread.sleep(1000);
        vigilant.interrupt();
        vigilant.join();
        System.out.println("main: el vigilant ja ha acabat");
    }
}
