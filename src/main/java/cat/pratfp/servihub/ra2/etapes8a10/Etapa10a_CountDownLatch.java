package cat.pratfp.servihub.ra2.etapes8a10;

import java.util.concurrent.CountDownLatch;

/**
 * ETAPA 10 · Exemple A · CountDownLatch: «espera que tots hagin acabat».
 * <p>
 * Situació: una taula ha demanat 3 plats. Cada plat el fa un cuiner diferent (un fil per cuiner).
 * El cambrer (el fil principal, {@code main}) només ha de portar els plats quan els TRES estiguin a punt.
 * <p>
 * Un {@link CountDownLatch} és un comptador enrere que es fa servir com una «porta»:
 * <ul>
 *   <li>Es crea amb un número: {@code new CountDownLatch(3)}.</li>
 *   <li>Cada cuiner, quan acaba, fa {@code countDown()}: el comptador baixa 1.</li>
 *   <li>El cambrer fa {@code await()}: s'espera fins que el comptador arriba a 0.</li>
 * </ul>
 * Diferència amb {@code join()}: amb {@code join()} esperes un fil concret que s'ha d'acabar;
 * amb el latch esperes «N avisos», vinguin de qui vinguin (fils normals, virtuals o d'una piscina).
 */
public class Etapa10a_CountDownLatch {

    public static void main(String[] args) throws InterruptedException {

        String[] plats = {"l'amanida", "la pizza", "les postres"};
        String[] cuiners = {"cuiner-amanida", "cuiner-pizza", "cuiner-postres"};
        int[] tempsDeCuina = {300, 900, 600};          // cada plat triga un temps diferent (ms)

        // 1. La porta: comença a 3 perquè esperem 3 plats.
        CountDownLatch platsPendents = new CountDownLatch(plats.length);

        // 2. Un fil per cuiner. Quan acaba el seu plat, fa countDown().
        for (int i = 0; i < plats.length; i++) {
            String plat = plats[i];
            int temps = tempsDeCuina[i];
            new Thread(() -> {
                Consola.diu("Començo a fer " + plat);
                Consola.dorm(temps);
                Consola.diu("Ja tinc " + plat + " a punt!");
                platsPendents.countDown();                 // un plat menys: el comptador baixa 1
            }, cuiners[i]).start();
        }

        // 3. El cambrer espera que el comptador arribi a 0.
        Consola.diu("Cambrer: espero que estiguin tots els plats...");
        platsPendents.await();
        Consola.diu("Cambrer: tots els plats a punt! Els porto a la taula.");
    }
}
