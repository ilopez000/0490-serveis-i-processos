package cat.pratfp.servihub.ra2.etapes8a10;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * ETAPA 9 · Exemple A · Una piscina de 3 fils per a 6 feines.
 * <p>
 * Fins ara fèiem {@code new Thread(...)} per a cada feina. Si tenim 6 feines, 6 fils;
 * si en tenim 10.000, 10.000 fils... i un fil de sistema operatiu costa memòria i temps de crear.
 * <p>
 * Una PISCINA DE FILS (<i>thread pool</i>) és un grup fix de fils que ja estan creats i
 * que van agafant feines d'una cua. Quan un fil acaba una feina, agafa la següent.
 * Nosaltres només donem les feines amb {@code submit(...)}: no creem ni engeguem cap fil.
 * <p>
 * Executa-ho i fixa't que, tot i ser 6 feines, només surten 3 noms de fil diferents
 * (pool-1-thread-1, -2 i -3): els fils es REAPROFITEN.
 */
public class Etapa9a_PiscinaDeFils {

    public static void main(String[] args) throws InterruptedException {

        // 1. Creem una piscina amb 3 fils fixos.
        ExecutorService piscina = Executors.newFixedThreadPool(3);

        // 2. Hi enviem 6 feines. Cada feina és un Runnable (una lambda sense resultat).
        for (int i = 1; i <= 6; i++) {
            int numero = i;    // la lambda necessita una variable que no canviï («effectively final»)
            piscina.submit(() -> {
                Consola.diu("Començo la feina " + numero);
                Consola.dorm(300);                       // la feina triga 300 ms
                Consola.diu("Acabo la feina " + numero);
            });
        }
        Consola.diu("Ja he enviat les 6 feines (submit no espera: torna de seguida)");

        // 3. Tanquem la piscina: no acceptarà feines noves, però acabarà les que té.
        piscina.shutdown();

        // 4. Esperem (com a màxim 10 s) que acabi totes les feines pendents.
        boolean acabat = piscina.awaitTermination(10, TimeUnit.SECONDS);
        Consola.diu("Piscina tancada. Ha acabat a temps? " + acabat);
    }
}
