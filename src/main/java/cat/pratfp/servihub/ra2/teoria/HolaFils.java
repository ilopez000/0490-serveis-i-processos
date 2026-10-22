package cat.pratfp.servihub.ra2.teoria;

public class HolaFils {

    public static void main(String[] args) throws InterruptedException {
        Runnable feina = () -> {
            String nom = Thread.currentThread().getName();
            for (int i = 1; i <= 3; i++) {
                System.out.println(nom + " diu hola " + i);
            }
        };

        Thread f1 = new Thread(feina, "Fil-1");
        Thread f2 = new Thread(feina, "Fil-2");

        f1.start();
        f2.start();

        f1.join();
        f2.join();
        System.out.println("main: els dos fils han acabat");
    }
}
