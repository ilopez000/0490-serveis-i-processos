package cat.pratfp.servihub.sessio2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/** Exemple 3: llegir la sortida estàndard i la d'error per separat, sense bloquejar el fill. */
public class Ex3SortidaIError {

    public static void main(String[] args) throws IOException, InterruptedException {
        // Llancem dues vegades la Tasca: una que va bé i una que falla.
        for (String nom : new String[]{"bona", "malament"}) {
            Process p = new ProcessBuilder(Jvm.ordre(Tasca.class, nom, "1")).start();

            // Un fil per a cada flux: si només en llegíssim un, l'altre es podria omplir i bloquejar el fill.
            Thread fout = llegeixEnSegonPla(p.getInputStream(), "  [out] ");
            Thread ferr = llegeixEnSegonPla(p.getErrorStream(), "  [ERR] ");

            int codi = p.waitFor();     // esperem el procés...
            fout.join();                // ...i que els lectors hagin buidat els fluxos
            ferr.join();
            System.out.println(nom + " -> codi " + codi + (codi == 0 ? " (correcte)" : " (ERROR)"));
        }
    }

    /** Crea i engega un fil que copia un flux a la consola línia a línia. */
    static Thread llegeixEnSegonPla(InputStream flux, String prefix) {
        Thread t = new Thread(() -> {
            try (BufferedReader r = new BufferedReader(new InputStreamReader(flux))) {
                String linia;
                while ((linia = r.readLine()) != null) {
                    System.out.println(prefix + linia);
                }
            } catch (IOException e) {
                System.out.println(prefix + "error llegint: " + e.getMessage());
            }
        });
        t.start();
        return t;
    }
}
