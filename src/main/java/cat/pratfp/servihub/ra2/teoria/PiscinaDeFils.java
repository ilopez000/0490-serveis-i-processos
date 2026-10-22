package cat.pratfp.servihub.ra2.teoria;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class PiscinaDeFils {

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        try (ExecutorService piscina = Executors.newFixedThreadPool(3)) {
            List<Future<Integer>> resultats = new ArrayList<>();

            for (int n = 1; n <= 5; n++) {
                int numero = n;
                Callable<Integer> tasca = () -> {
                    System.out.println(Thread.currentThread().getName() + " calcula " + numero + " al quadrat");
                    return numero * numero;
                };
                resultats.add(piscina.submit(tasca));
            }

            int suma = 0;
            for (Future<Integer> f : resultats) {
                suma += f.get();
            }
            System.out.println("Suma dels quadrats: " + suma);
        }
    }
}
