package cat.pratfp.servihub.sessio1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Optional;

/** ServiHub v0 (sessió 1): llançar un procés fill i llegir-ne la sortida. */
public class App {

    public static void main(String[] args) throws IOException, InterruptedException {
        System.out.println("Sóc el procés " + ProcessHandle.current().pid());

        // L'ordre i els seus arguments van separats: mai en una sola cadena.
        ProcessBuilder pb = new ProcessBuilder("java", "-version");
        // java -version escriu per la sortida d'error; la fusionem amb l'estàndard.
        pb.redirectErrorStream(true);

        Process p = pb.start();
        System.out.println("He llançat el procés " + p.pid());

        // Cal llegir la sortida ABANS de waitFor(): si no, el fill es pot bloquejar.
        try (BufferedReader lector = new BufferedReader(new InputStreamReader(p.getInputStream()))) {
            String linia;
            while ((linia = lector.readLine()) != null) {
                System.out.println("  fill> " + linia);
            }
        }

        int codi = p.waitFor();
        System.out.println("Codi de retorn: " + codi + " (" + descripcio(codi).orElse("desconegut") + ")");
    }

    static Optional<String> descripcio(int codi) {
        return codi == 0 ? Optional.of("èxit") : Optional.empty();
    }
}
