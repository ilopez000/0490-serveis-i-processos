package cat.pratfp.servihub.sessio2;

import java.nio.file.Path;

/** Procés fill de l'exemple 5: escriu on és, què veu de l'entorn i un avís per la sortida d'error. */
public class Mostrador {

    public static void main(String[] args) {
        System.out.println("Directori de treball: " + Path.of("").toAbsolutePath());
        System.out.println("SERVIHUB_MODE = " + System.getenv("SERVIHUB_MODE"));
        System.out.println("PATH definit? " + (System.getenv("PATH") != null));
        System.err.println("(això és la sortida d'error del fill)");
    }
}
