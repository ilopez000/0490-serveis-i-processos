package cat.pratfp.servihub.ra2.teoria;

public class Interruptor {

    private volatile boolean encesa = true;

    public void apaga() {
        encesa = false;
    }

    public static void main(String[] args) throws InterruptedException {
        Interruptor llum = new Interruptor();
        Thread treballador = new Thread(() -> {
            long voltes = 0;
            while (llum.encesa) {
                voltes++;
            }
            System.out.println("Apagat després de " + voltes + " voltes");
        });
        treballador.start();
        Thread.sleep(500);
        llum.apaga();
        treballador.join();
    }
}
