import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * Muestra información sobre un proceso: PID, PPID, PIDs de los procesos hijos
 * y otros detalles como argumentos, comando, línea de comando, tiempo de inicio,
 * duración total de CPU y usuario.
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

        // Obtener y mostrar los argumentos del proceso
        Optional<String[]> argumentos = procesoActual.info().arguments();
        if (argumentos.isPresent()) {
            System.out.println("Argumentos del proceso:");
            for (String arg : argumentos.get()) {
                System.out.println("  - " + arg);
            }
        } else {
            System.out.println("No hay argumentos disponibles.");
        }

        // Obtener y mostrar el comando del proceso
        Optional<String> comando = procesoActual.info().command();
        comando.ifPresentOrElse(
                cmd -> System.out.println("Comando del proceso: " + cmd),
                () -> System.out.println("No se pudo obtener el comando.")
        );

        // Obtener y mostrar la línea de comando del proceso
        Optional<String> lineaComando = procesoActual.info().commandLine();
        if (lineaComando.isPresent()) {
            System.out.println("Línea de comando del proceso: " + lineaComando.get());
        } else {
            System.out.println("No se pudo obtener la línea de comando.");
        }

        // Obtener y mostrar el tiempo de inicio del proceso
        Optional<Instant> inicioProceso = procesoActual.info().startInstant();
        if (inicioProceso.isPresent()) {
            System.out.println("El proceso comenzó en: " + inicioProceso.get());
        } else {
            System.out.println("No se pudo obtener la hora de inicio.");
        }

        // Obtener y mostrar la duración total de CPU del proceso
        Optional<Duration> duracionCpu = procesoActual.info().totalCpuDuration();
        if (duracionCpu.isPresent()) {
            System.out.println("Tiempo total de CPU usado por el proceso: " + duracionCpu.get());
        } else {
            System.out.println("No se pudo obtener la duración de CPU.");
        }

        // Obtener y mostrar el usuario del proceso
        Optional<String> usuario = procesoActual.info().user();
        if (usuario.isPresent()) {
            System.out.println("Usuario del proceso: " + usuario.get());
        } else {
            System.out.println("No se pudo obtener el usuario del proceso.");
        }

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
