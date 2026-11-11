package cat.pratfp.servihub.ra2.etapes8a10;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * ETAPA 8 · Exemple A · El passaplats (productor-consumidor amb UNA cua bloquejant).
 * <p>
 * Situació: a la cuina de ServiHub hi ha un CUINER que prepara comandes i un REPARTIDOR que
 * les porta. Entre tots dos hi ha un PASSAPLATS on només hi caben 2 comandes.
 * <ul>
 *   <li>El cuiner fa {@code put}: si el passaplats és PLE, el cuiner s'espera.</li>
 *   <li>El repartidor fa {@code take}: si el passaplats és BUIT, el repartidor s'espera.</li>
 * </ul>
 * Tota la sincronització la fa la cua: no hi ha cap {@code synchronized}, cap {@code wait()}
 * ni cap {@code notify()} escrits per nosaltres.
 * <p>
 * Executa-ho i fixa't en dues coses:
 * <ol>
 *   <li>El cuiner va més de pressa: quan el passaplats és ple, es queda esperant.</li>
 *   <li>Les comandes surten en el mateix ordre en què han entrat (FIFO: primer a entrar, primer a sortir).</li>
 * </ol>
 */
public class Etapa8a_Passaplats {

    public static void main(String[] args) throws InterruptedException {

        // 1. El passaplats: una cua bloquejant amb lloc per a 2 comandes com a màxim.
        BlockingQueue<String> passaplats = new ArrayBlockingQueue<>(2);

        // 2. El PRODUCTOR: el cuiner prepara 5 comandes i les posa al passaplats.
        Thread cuiner = new Thread(() -> {
            try {
                for (int i = 1; i <= 5; i++) {
                    String comanda = "comanda " + i;
                    Consola.dorm(100);                          // cuinar triga 100 ms
                    Consola.diu("Vull deixar la " + comanda + " (hi ha " + passaplats.size() + " al passaplats)");
                    passaplats.put(comanda);                    // si és ple, ESPERA aquí
                    Consola.diu("He deixat la " + comanda);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }, "cuiner");

        // 3. El CONSUMIDOR: el repartidor recull les 5 comandes i les porta.
        Thread repartidor = new Thread(() -> {
            try {
                for (int i = 1; i <= 5; i++) {
                    String comanda = passaplats.take();         // si és buit, ESPERA aquí
                    Consola.diu("He recollit la " + comanda + " i me la emporto");
                    Consola.dorm(400);                          // portar-la triga 400 ms (és més lent!)
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }, "repartidor");

        // 4. Engeguem els dos fils i esperem que acabin.
        cuiner.start();
        repartidor.start();
        cuiner.join();
        repartidor.join();

        Consola.diu("Totes les comandes repartides. Fi!");
    }
}
