package cat.pratfp.servihub.ra2.etapes8a10;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * ETAPA 8 · Exemple B · Diversos repartidors i la «píndola de FI».
 * <p>
 * Problema: ara hi ha 3 repartidors. Cadascun fa un bucle infinit de {@code take()}.
 * Quan el cuiner acaba, com saben els repartidors que ja no vindrà cap comanda més?
 * Si no fem res, es quedarien esperant per sempre i el programa NO acabaria mai.
 * <p>
 * Solució: la «píndola de FI» (en anglès, <i>poison pill</i>). El cuiner, quan acaba,
 * posa a la cua un missatge especial, {@code "FI"}, <b>un per a cada repartidor</b>.
 * Cada repartidor que se'n troba un, surt del bucle i plega.
 * <p>
 * Prova-ho: canvia el bucle de les píndoles perquè en posi només 1 i torna-ho a executar.
 * Veuràs que un repartidor plega però els altres dos es queden encallats (el programa no acaba).
 */
public class Etapa8b_PindolaDeFi {

    /** El missatge especial que vol dir «ja no en vindran més». */
    static final String FI = "FI";

    static final int COMANDES = 9;
    static final int REPARTIDORS = 3;

    public static void main(String[] args) throws InterruptedException {

        BlockingQueue<String> passaplats = new ArrayBlockingQueue<>(3);

        // 1. PRODUCTOR: prepara les comandes i, al final, una píndola de FI per repartidor.
        Thread cuiner = new Thread(() -> {
            try {
                for (int i = 1; i <= COMANDES; i++) {
                    Consola.dorm(50);
                    passaplats.put("comanda " + i);
                    Consola.diu("He deixat la comanda " + i);
                }
                for (int r = 1; r <= REPARTIDORS; r++) {    // UNA píndola per a CADA repartidor
                    passaplats.put(FI);
                }
                Consola.diu("He acabat i he posat " + REPARTIDORS + " píndoles de FI");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }, "cuiner");

        // 2. CONSUMIDORS: tres repartidors iguals. Creem els fils en un bucle i els guardem en una llista.
        List<Thread> repartidors = new ArrayList<>();
        for (int r = 1; r <= REPARTIDORS; r++) {
            Thread repartidor = new Thread(() -> {
                try {
                    int portades = 0;
                    while (true) {                              // bucle «infinit»...
                        String comanda = passaplats.take();
                        if (comanda.equals(FI)) {               // ...fins que arriba la píndola
                            break;
                        }
                        Consola.diu("Porto la " + comanda);
                        Consola.dorm(150);
                        portades++;
                    }
                    Consola.diu("He trobat la píndola de FI. Plego després de portar " + portades + " comandes");
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }, "repartidor-" + r);
            repartidors.add(repartidor);
        }

        // 3. Engeguem tothom.
        cuiner.start();
        for (Thread t : repartidors) {
            t.start();
        }

        // 4. Esperem que acabin TOTS (el cuiner i els tres repartidors).
        cuiner.join();
        for (Thread t : repartidors) {
            t.join();
        }
        Consola.diu("Tothom ha plegat. El programa acaba bé.");
    }
}
