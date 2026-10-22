package cat.pratfp.servihub.ra2.teoria;

public class EstatsDUnFil {

    public static void main(String[] args) throws InterruptedException {
        Thread dormilega = new Thread(() -> {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        System.out.println("Abans de start(): " + dormilega.getState());
        dormilega.start();
        Thread.sleep(100);
        System.out.println("Mentre dorm:      " + dormilega.getState());
        dormilega.join();
        System.out.println("Després de join(): " + dormilega.getState());
    }
}
