import java.io.IOException;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * Muestra información sobre un proceso: PID, PPID, PIDs de los procesos hijos
 */
public class ProcesoInfo {
    public static void main(String[] args) {
        // Obtener el proceso actual
        ProcessHandle procesoActual = ProcessHandle.current();

        // Obtener el PID
        long pid = procesoActual.pid();

        // Obtener el proceso padre (PPID)
        Optional<ProcessHandle> procesoPadre = procesoActual.parent();

        // Mostrar el PID
        System.out.println("PID del proceso actual: " + pid);

        // Mostrar el PPID si existe
        if (procesoPadre.isPresent()) {
            System.out.println("PPID del proceso padre: " + procesoPadre.get().pid());
        } else {
            System.out.println("El proceso actual no tiene un proceso padre (tal vez es el inicial).");
        }

        // Mostrar la jerarquía de procesos hijos
        System.out.println("Procesos hijos:");
        Stream<ProcessHandle> hijos = procesoActual.children();
        hijos.forEach(hijo -> System.out.println("  - PID del hijo: " + hijo.pid()));
        pausa();
    }

    private static void pausa() {
        System.out.println("Pulsa Enter para continuar");
        try {
            System.in.read();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
