package cat.pratfp.servihub.ra2.etapes8a10;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * ETAPA 9 · Exemple B · Feines que RETORNEN un resultat: Callable i Future.
 * <p>
 * Un {@link Runnable} fa una feina però no retorna res ({@code void}).
 * Un {@link Callable} fa una feina i RETORNA un valor (aquí, un {@code Integer}).
 * <p>
 * Quan enviem un Callable a la piscina amb {@code submit}, ens torna DE SEGUIDA un {@link Future}:
 * un «tiquet» per recollir el resultat més tard. Quan el volem, fem {@code futur.get()}:
 * si el resultat ja està, ens el dona; si encara no, el fil principal s'espera.
 * <p>
 * Situació: a l'hora de tancar, cada una de les 4 caixes de ServiHub ha de sumar els seus tiquets.
 * Ho fem en paral·lel amb una piscina de 2 fils.
 */
public class Etapa9b_CallableIFuture {

    public static void main(String[] args) throws InterruptedException, ExecutionException {

        // Els imports dels tiquets de cada caixa (en euros).
        List<List<Integer>> caixes = List.of(
                List.of(12, 8, 30),        // caixa 1 → 50
                List.of(5, 5, 5, 5),       // caixa 2 → 20
                List.of(100),              // caixa 3 → 100
                List.of(7, 3, 15));        // caixa 4 → 25

        ExecutorService piscina = Executors.newFixedThreadPool(2);
        try {
            // 1. Enviem una feina per caixa i guardem el «tiquet» (Future) de cadascuna.
            List<Future<Integer>> futurs = new ArrayList<>();
            for (int i = 0; i < caixes.size(); i++) {
                int numCaixa = i + 1;
                List<Integer> tiquets = caixes.get(i);

                Callable<Integer> sumaCaixa = () -> {          // Callable: té un return
                    Consola.diu("Sumo la caixa " + numCaixa);
                    Consola.dorm(500);                         // sumar triga 500 ms
                    int total = 0;
                    for (int t : tiquets) {
                        total += t;
                    }
                    return total;
                };

                Future<Integer> futur = piscina.submit(sumaCaixa);
                futurs.add(futur);
            }
            Consola.diu("He enviat les 4 sumes. Ara recullo els resultats...");

            // 2. Recollim els resultats EN ORDRE. get() espera si la suma encara no ha acabat.
            int totalDelDia = 0;
            for (int i = 0; i < futurs.size(); i++) {
                int total = futurs.get(i).get();
                Consola.diu("Caixa " + (i + 1) + ": " + total + " euros");
                totalDelDia += total;
            }
            Consola.diu("TOTAL DEL DIA: " + totalDelDia + " euros");

        } finally {
            // 3. Sempre tanquem la piscina (dins d'un finally, passi el que passi).
            //    Si no, els seus fils seguirien vius i el programa no acabaria.
            piscina.shutdown();
        }
    }
}
