package cat.pratfp.servihub.ra2.teoria;

public class Prioritats {

    public static void main(String[] args) {
        Thread important = new Thread(() -> System.out.println("Sóc important"));
        Thread secundari = new Thread(() -> System.out.println("Sóc secundari"));

        important.setPriority(Thread.MAX_PRIORITY);
        secundari.setPriority(Thread.MIN_PRIORITY);

        secundari.start();
        important.start();
    }
}
