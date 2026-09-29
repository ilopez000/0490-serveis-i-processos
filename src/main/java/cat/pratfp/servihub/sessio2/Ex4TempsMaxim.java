package cat.pratfp.servihub.sessio2;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/** Exemple 4: no esperar mai indefinidament: waitFor amb temps màxim i destroy. */
public class Ex4TempsMaxim {

    public static void main(String[] args) throws IOException, InterruptedException {
        // Una tasca de 10 segons... però només li'n donem 3.
        ProcessBuilder pb = new ProcessBuilder(Jvm.ordre(Tasca.class, "lenta", "10"));
        pb.inheritIO();
        Process p = pb.start();
        long inici = System.currentTimeMillis();

        boolean haAcabat = p.waitFor(3, TimeUnit.SECONDS);   // true si acaba a temps, false si no

        if (haAcabat) {
            System.out.println("Ha acabat amb codi " + p.exitValue());
        } else {
            System.out.println("Temps esgotat: la mato");
            p.destroy();                                       // petició educada d'acabar
            if (!p.waitFor(2, TimeUnit.SECONDS)) {
                p.destroyForcibly();                           // si no fa cas, a la força
            }
            System.out.println("Viu? " + p.isAlive() + " · codi " + p.exitValue());
        }
        System.out.println("Temps total: " + (System.currentTimeMillis() - inici) / 1000 + " s");
    }
}
