package cat.pratfp.servihub.ra2.comandes;

public class Comandes {

    static final int CAIXES = 4;
    static final int COMANDES_PER_CAIXA = 100_000;

    public static void main(String[] args) throws InterruptedException {
        Comptador comptador = new Comptador();
        Thread[] caixes = new Thread[CAIXES];

        for (int i = 0; i < CAIXES; i++) {
            caixes[i] = new Thread(() -> {
                for (int j = 0; j < COMANDES_PER_CAIXA; j++) {
                    comptador.incrementa();
                }
            }, "caixa-" + (i + 1));
            caixes[i].start();
        }

        for (Thread caixa : caixes) {
            caixa.join();
        }

        int esperat = CAIXES * COMANDES_PER_CAIXA;
        System.out.println("Esperat: " + esperat + "  ·  Obtingut: " + comptador.valor());
    }
}
