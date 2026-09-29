package cat.pratfp.servihub.sessio2;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/** Exemple 5: directori de treball, variables d'entorn i sortida redirigida a fitxers. */
public class Ex5EntornIFitxers {

    public static void main(String[] args) throws IOException, InterruptedException {
        Path carpeta = Path.of("sortida");
        Files.createDirectories(carpeta);

        ProcessBuilder pb = new ProcessBuilder(Jvm.ordre(Mostrador.class));
        pb.directory(carpeta.toFile());                       // el fill "viu" dins de sortida/
        pb.environment().put("SERVIHUB_MODE", "proves");      // una variable només per al fill
        pb.redirectOutput(new File("sortida/mostrador.log")); // la sortida va a un fitxer...
        pb.redirectError(ProcessBuilder.Redirect.INHERIT);    // ...i els errors, a la nostra consola

        int codi = pb.start().waitFor();
        System.out.println("Codi: " + codi);
        System.out.println("--- Contingut de sortida/mostrador.log ---");
        System.out.print(Files.readString(Path.of("sortida", "mostrador.log")));
    }
}
