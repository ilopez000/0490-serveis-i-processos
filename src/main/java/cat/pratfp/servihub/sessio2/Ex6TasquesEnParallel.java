package cat.pratfp.servihub.sessio2;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/** Exemple 6: tres tasques una darrere l'altra contra tres tasques alhora. L'avantsala de ServiHub v1. */
public class Ex6TasquesEnParallel {

    public static void main(String[] args) throws IOException, InterruptedException {
        // 1. SEQÜENCIAL: llançar, esperar; llançar, esperar; llançar, esperar.
        long t0 = System.currentTimeMillis();
        for (String nom : new String[]{"A", "B", "C"}) {
            Process p = new ProcessBuilder(Jvm.ordre(Tasca.class, nom, "2")).inheritIO().start();
            p.waitFor();
        }
        long seq = System.currentTimeMillis() - t0;

        // 2. PARAL·LEL: llançar les tres i, després, esperar-les totes.
        t0 = System.currentTimeMillis();
        List<Process> fills = new ArrayList<>();
        for (String nom : new String[]{"D", "E", "F"}) {
            fills.add(new ProcessBuilder(Jvm.ordre(Tasca.class, nom, "2")).inheritIO().start());
        }
        int errors = 0;
        for (Process p : fills) {
            if (p.waitFor() != 0) errors++;
        }
        long par = System.currentTimeMillis() - t0;

        System.out.println("Seqüencial: " + seq / 1000.0 + " s · Paral·lel: " + par / 1000.0 + " s · errors: " + errors);
    }
}
