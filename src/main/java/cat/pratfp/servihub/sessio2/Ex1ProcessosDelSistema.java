package cat.pratfp.servihub.sessio2;

import java.util.Comparator;

/** Exemple 1: mirar els processos del sistema des de Java amb ProcessHandle. */
public class Ex1ProcessosDelSistema {

    public static void main(String[] args) {
        // 1. EL MEU PROCÉS: PID, ordre i pare.
        ProcessHandle jo = ProcessHandle.current();
        System.out.println("PID: " + jo.pid());
        System.out.println("Ordre: " + jo.info().command().orElse("?"));
        System.out.println("Pare: " + jo.parent().map(p -> p.pid() + " " + p.info().command().orElse("?")).orElse("cap"));

        // 2. TOTS ELS PROCESSOS VISIBLES: quants n'hi ha i els 5 que fa més temps que corren.
        long total = ProcessHandle.allProcesses().count();
        System.out.println("Processos visibles: " + total);
        System.out.println("--- Els cinc més antics ---");
        ProcessHandle.allProcesses()
                .filter(p -> p.info().startInstant().isPresent() && p.info().command().isPresent())
                .sorted(Comparator.comparing(p -> p.info().startInstant().get()))
                .limit(5)
                .forEach(p -> System.out.printf("%6d  %-40s  %s%n",
                        p.pid(),
                        nomCurt(p.info().command().orElse("?")),
                        p.info().user().orElse("?")));
    }

    /** Es queda només amb el nom de l'executable, sense la ruta. */
    static String nomCurt(String ruta) {
        int i = Math.max(ruta.lastIndexOf('/'), ruta.lastIndexOf('\\'));
        return i >= 0 ? ruta.substring(i + 1) : ruta;
    }
}
