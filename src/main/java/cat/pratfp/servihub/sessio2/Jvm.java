package cat.pratfp.servihub.sessio2;

/**
 * Utilitat per llançar classes d'aquest mateix projecte com a processos fills.
 * Fa servir el mateix executable java i el mateix classpath que el procés actual,
 * així els exemples funcionen igual a Windows, Linux i macOS.
 */
public final class Jvm {

    private Jvm() {}

    /** Ruta de l'executable java que està executant aquest programa. */
    public static String executable() {
        return ProcessHandle.current().info().command().orElse("java");
    }

    /** L'ordre completa per executar una classe del projecte amb els arguments donats. */
    public static String[] ordre(Class<?> classe, String... args) {
        // El classpath es passa absolut: així el fill el troba encara que canviem el seu directori de treball.
        String cp = java.nio.file.Path.of(System.getProperty("java.class.path")).toAbsolutePath().toString();
        String[] base = {executable(), "-cp", cp, classe.getName()};
        String[] tot = new String[base.length + args.length];
        System.arraycopy(base, 0, tot, 0, base.length);
        System.arraycopy(args, 0, tot, base.length, args.length);
        return tot;
    }
}
