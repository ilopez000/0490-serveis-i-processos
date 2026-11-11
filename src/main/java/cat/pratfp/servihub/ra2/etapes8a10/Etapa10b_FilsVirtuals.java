package cat.pratfp.servihub.ra2.etapes8a10;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * ETAPA 10 · Exemple B · 10.000 comandes, un fil VIRTUAL per a cadascuna.
 * <p>
 * Els fils virtuals (Java 21) són fils molt lleugers que gestiona la màquina virtual de Java,
 * no el sistema operatiu. Se'n poden crear milers (fins i tot milions) sense problema,
 * sobretot quan la feina consisteix a ESPERAR (una xarxa, un disc, un {@code sleep}...).
 * <p>
 * Aquí ho ajuntem tot:
 * <ul>
 *   <li>{@code Executors.newVirtualThreadPerTaskExecutor()}: un fil virtual nou per a cada feina.</li>
 *   <li>{@link ConcurrentHashMap} amb {@code merge}: molts fils sumen al mateix mapa sense errors.</li>
 *   <li>{@link CountDownLatch}: el fil principal espera que s'hagin processat les 10.000 comandes.</li>
 * </ul>
 * Cada comanda «triga» 20 ms. Si les féssim una darrere l'altra serien 200 segons;
 * amb fils virtuals totes esperen alhora i acaba en una fracció de segon.
 */
public class Etapa10b_FilsVirtuals {

    static final int COMANDES = 10_000;

    public static void main(String[] args) throws InterruptedException {

        String[] plats = {"pizza", "amanida", "pasta", "sopa"};

        // 1. El mapa compartit plat → quantes comandes. Ha de ser CONCURRENT.
        Map<String, Integer> perPlat = new ConcurrentHashMap<>();

        // 2. La porta: s'obrirà quan s'hagin processat les 10.000 comandes.
        CountDownLatch pendents = new CountDownLatch(COMANDES);

        long inici = System.currentTimeMillis();

        // 3. Executor de fils virtuals. El try (...) el tanca sol en acabar (és AutoCloseable).
        try (ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor()) {
            for (int i = 1; i <= COMANDES; i++) {
                String plat = plats[i % plats.length];      // repartim les comandes entre els 4 plats
                executor.submit(() -> {
                    Consola.dorm(20);                       // preparar la comanda triga 20 ms
                    perPlat.merge(plat, 1, Integer::sum);   // suma 1 al plat de manera ATÒMICA
                    pendents.countDown();                   // una comanda menys!
                });
            }
            Consola.diu("He enviat " + COMANDES + " comandes. Espero que acabin...");

            // 4. Esperem que el comptador arribi a 0.
            pendents.await();
        }

        long ms = System.currentTimeMillis() - inici;

        // 5. Informe final.
        Consola.diu(COMANDES + " comandes processades en " + ms + " ms (en sèrie serien " + COMANDES * 20 / 1000 + " s)");
        int total = 0;
        for (String plat : plats) {
            Consola.diu("   " + plat + ": " + perPlat.get(plat));
            total += perPlat.get(plat);
        }
        Consola.diu("   total: " + total + (total == COMANDES ? "  -> OK, no se n'ha perdut cap" : "  -> ERROR, se n'han perdut!"));
    }
}
