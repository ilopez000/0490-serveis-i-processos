package cat.pratfp.servihub.ra2.teoria;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class FilsVirtuals {

    public static void main(String[] args) {
        AtomicInteger acabades = new AtomicInteger();
        try (ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor()) {
            for (int i = 0; i < 10_000; i++) {
                executor.submit(() -> {
                    Thread.sleep(1000);
                    acabades.incrementAndGet();
                    return null;
                });
            }
        }
        System.out.println("Tasques acabades: " + acabades.get());
    }
}
