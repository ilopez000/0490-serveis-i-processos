package cat.pratfp.servihub.ra2.cursa;

public class Repartidor implements Runnable {

    private final String nom;
    private final int paquets;

    public Repartidor(String nom, int paquets) {
        this.nom = nom;
        this.paquets = paquets;
    }

    @Override
    public void run() {
        for (int i = 1; i <= paquets; i++) {
            System.out.println(nom + " entrega el paquet " + i
                    + "  [" + Thread.currentThread().getName() + "]");
            try {
                Thread.sleep((long) (Math.random() * 500));
            } catch (InterruptedException e) {
                System.out.println(nom + " ha rebut una interrupció i plega.");
                return;
            }
        }
        System.out.println(">>> " + nom + " ha acabat!");
    }
}
