package cat.pratfp.servihub.ra2.etapes8a10;

/**
 * Petita ajuda per veure QUI fa QUÈ i QUAN.
 * <p>
 * Totes les classes d'aquest paquet escriuen per pantalla amb {@link #diu(String)}.
 * Cada línia surt així:
 * <pre>
 *   [  105 ms] repartidor-1     | He recollit la comanda 3
 *    ^ temps des que ha començat el programa
 *              ^ nom del fil que escriu la línia
 * </pre>
 * Així es veu molt bé que hi ha diversos fils treballant alhora.
 */
public final class Consola {

    /** Moment en què s'ha carregat la classe: el «zero» del rellotge. */
    private static final long INICI = System.nanoTime();

    // Constructor privat: aquesta classe només té mètodes static, no se'n creen objectes.
    private Consola() {}

    /**
     * Escriu un missatge amb el temps i el nom del fil que l'escriu.
     * És {@code synchronized} perquè dos fils no barregin les seves línies a la pantalla.
     */
    public static synchronized void diu(String missatge) {
        long ms = (System.nanoTime() - INICI) / 1_000_000;          // nanosegons → mil·lisegons
        System.out.printf("[%5d ms] %-16s | %s%n", ms, nomDelFil(), missatge);
    }

    /**
     * Retorna el nom del fil actual.
     * Els fils virtuals no tenen nom (és una cadena buida), així que els anomenem «virtual-» + número.
     */
    public static String nomDelFil() {
        Thread actual = Thread.currentThread();
        if (actual.getName().isEmpty()) {
            return "virtual-" + actual.threadId();
        }
        return actual.getName();
    }

    /**
     * Fa esperar el fil actual uns mil·lisegons: així «simulem» una feina que triga
     * (cuinar un plat, portar una comanda…).
     * Si algú interromp el fil mentre dorm, tornem a marcar la interrupció i sortim.
     */
    public static void dorm(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();   // no amaguem la interrupció (etapa 4)
        }
    }
}
