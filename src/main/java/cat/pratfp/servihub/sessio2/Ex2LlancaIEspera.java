package cat.pratfp.servihub.sessio2;

import java.io.IOException;

/** Exemple 2: llançar un procés, esperar-lo i llegir el codi de retorn. */
public class Ex2LlancaIEspera {

    public static void main(String[] args) throws IOException, InterruptedException {
        // 1. UNA ORDRE QUE VA BÉ: la mateixa JVM que ens executa, demanant la versió.
        int codi = executa(Jvm.executable(), "-version");
        System.out.println("java -version ha retornat " + codi);

        // 2. UNA ORDRE QUE FALLA: una opció que no existeix.
        codi = executa(Jvm.executable(), "-opcio-inventada");
        System.out.println("java -opcio-inventada ha retornat " + codi);

        // 3. UN PROGRAMA QUE NO EXISTEIX: ni tan sols arrenca.
        try {
            executa("programa-que-no-existeix");
        } catch (IOException e) {
            System.out.println("No s'ha pogut llançar: " + e.getMessage());
        }
    }

    static int executa(String... ordre) throws IOException, InterruptedException {
        ProcessBuilder pb = new ProcessBuilder(ordre);
        pb.inheritIO();                 // el fill escriu directament a la nostra consola
        Process p = pb.start();
        System.out.println("[llançat PID " + p.pid() + "] " + String.join(" ", ordre));
        int codi = p.waitFor();         // bloqueja fins que el fill acaba
        if (codi != 0) {
            System.out.println("[avís] el procés ha acabat amb error (" + codi + ")");
        }
        return codi;
    }
}
