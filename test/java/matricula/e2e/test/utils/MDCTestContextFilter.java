package matricula.e2e.test.utils;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.turbo.TurboFilter;
import ch.qos.logback.core.spi.FilterReply;
import org.slf4j.MDC;
import org.slf4j.Marker;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Filtro que asegura que todas las trazas de log se asignen al mismo archivo Playwright-XXX
 * unificando los logs de todos los hilos relacionados con un test específico.
 *
 * Este filtro mejora la captura de logs de backend y base de datos de matricula y es.loyola,
 * asociándolos al test que los originó para facilitar la depuración.
 */
public class MDCTestContextFilter extends TurboFilter {

    // Mapa para relacionar hilos con IDs de test
    private static final Map<String, String> threadToTestMap = new ConcurrentHashMap<>();

    // Mapa para almacenar detalles adicionales como backend y base de datos
    private static final Map<String, Map<String, String>> testDetailsMap = new ConcurrentHashMap<>();

    // Referencia al último test de Playwright que se ejecutó
    private static final AtomicReference<String> lastPlaywrightTest = new AtomicReference<>("default");

    @Override
    public FilterReply decide(Marker marker, Logger logger, Level level, String format, Object[] params, Throwable t) {
        String currentThreadName = Thread.currentThread().getName();
        String threadId = String.valueOf(Thread.currentThread().getId());
        String testName = MDC.get("testId");
        String userEmail = MDC.get("userEmail");
        String loggerName = logger.getName();

        // Detectar el tipo de log basado en el nombre del logger
        String logType = determineLogType(loggerName);
        if (logType != null) {
            MDC.put("logType", logType);
        }

        // Si ya tenemos un testName específico de Playwright, registramos esta asociación
        if (testName != null && !testName.isEmpty() && testName.startsWith("Playwright-")) {
            threadToTestMap.put(currentThreadName, testName);
            threadToTestMap.put(threadId, testName); // También asociar por ID de hilo
            lastPlaywrightTest.set(testName);

            // Guardar información adicional para este test
            Map<String, String> details = testDetailsMap.computeIfAbsent(testName, k -> new ConcurrentHashMap<>());
            if (userEmail != null && !userEmail.isEmpty()) {
                details.put("userEmail", userEmail);
            }

            // Configurar el nombre de archivo de log para este test específico
            String fileLogName = testName + "_" + (userEmail != null ? userEmail.replaceAll("[^a-zA-Z0-9]", "_") : "unknown");
            MDC.put("fileLogName", fileLogName);

            return FilterReply.ACCEPT;
        }

        // Si el hilo actual está en el mapa, usamos ese testName
        if (threadToTestMap.containsKey(currentThreadName) || threadToTestMap.containsKey(threadId)) {
            String assignedTestName = threadToTestMap.getOrDefault(currentThreadName, threadToTestMap.get(threadId));
            MDC.put("testId", assignedTestName);

            // Recuperar detalles adicionales
            Map<String, String> details = testDetailsMap.get(assignedTestName);
            if (details != null) {
                if (details.containsKey("userEmail")) {
                    MDC.put("userEmail", details.get("userEmail"));
                }

                // Configurar el nombre de archivo de log para este hilo específico
                String userEmailValue = details.getOrDefault("userEmail", "unknown");
                String fileLogName = assignedTestName + "_" + userEmailValue.replaceAll("[^a-zA-Z0-9]", "_");
                MDC.put("fileLogName", fileLogName);
            }

            return FilterReply.ACCEPT;
        }

        // Si no hay testName específico, asociamos el log al último test de Playwright conocido
        String testId = lastPlaywrightTest.get();
        if (!testId.equals("default")) {
            MDC.put("testId", testId);
            threadToTestMap.put(currentThreadName, testId);
            threadToTestMap.put(threadId, testId); // También asociar por ID de hilo

            // Recuperar detalles adicionales para este último test
            Map<String, String> details = testDetailsMap.get(testId);
            if (details != null) {
                if (details.containsKey("userEmail")) {
                    MDC.put("userEmail", details.get("userEmail"));
                }

                // Configurar el nombre de archivo de log
                String userEmailValue = details.getOrDefault("userEmail", "unknown");
                String fileLogName = testId + "_" + userEmailValue.replaceAll("[^a-zA-Z0-9]", "_");
                MDC.put("fileLogName", fileLogName);
            } else {
                // Si no hay detalles, usar nombre genérico
                MDC.put("fileLogName", testId + "_unknown");
            }
        } else {
            MDC.put("testId", "default");
            MDC.put("fileLogName", "default");
        }

        return FilterReply.ACCEPT;
    }

    /**
     * Determina el tipo de log basado en el nombre del logger
     * @param loggerName Nombre completo del logger
     * @return Tipo de log (backend, database, etc.)
     */
    private String determineLogType(String loggerName) {
        if (loggerName == null) {
            return null;
        }

        // Detectar logs del backend
        if (loggerName.startsWith("matricula") && !loggerName.startsWith("matricula.e2e")) {
            return "backend-matricula";
        } else if (loggerName.startsWith("es.loyola")) {
            return "backend-loyola";
        }

        // Detectar logs de base de datos
        if (loggerName.startsWith("org.hibernate") ||
            loggerName.startsWith("org.springframework.jdbc") ||
            loggerName.startsWith("org.springframework.transaction") ||
            loggerName.startsWith("com.zaxxer.hikari")) {
            return "database";
        }

        // Logs de test
        if (loggerName.startsWith("matricula.e2e")) {
            return "test";
        }

        return null;
    }

    /**
     * Método para registrar manualmente un test.
     * Útil para registrar tests al inicio de su ejecución.
     */
    public static void registerTest(String testId, String userEmail) {
        if (testId != null && !testId.isEmpty()) {
            String currentThreadName = Thread.currentThread().getName();
            String threadId = String.valueOf(Thread.currentThread().getId());

            threadToTestMap.put(currentThreadName, testId);
            threadToTestMap.put(threadId, testId);
            lastPlaywrightTest.set(testId);

            Map<String, String> details = testDetailsMap.computeIfAbsent(testId, k -> new ConcurrentHashMap<>());
            if (userEmail != null && !userEmail.isEmpty()) {
                details.put("userEmail", userEmail);
            }

            // Configurar MDC para el hilo actual
            MDC.put("testId", testId);
            MDC.put("userEmail", userEmail);

            String fileLogName = testId + "_" + (userEmail != null ? userEmail.replaceAll("[^a-zA-Z0-9]", "_") : "unknown");
            MDC.put("fileLogName", fileLogName);
        }
    }

    /**
     * Método para limpiar el registro de un test.
     */
    public static void clearTest(String testId) {
        if (testId != null && !testId.isEmpty()) {
            // Eliminar todas las entradas que apunten a este testId
            threadToTestMap.entrySet().removeIf(entry -> testId.equals(entry.getValue()));
            testDetailsMap.remove(testId);

            // Si el último test conocido era este, resetearlo
            if (lastPlaywrightTest.get().equals(testId)) {
                lastPlaywrightTest.set("default");
            }

        }

            // Limpiar el MDC del hilo actual
            MDC.remove("testId");
            MDC.remove("userEmail");
            MDC.remove("fileLogName");
            MDC.remove("logType");
    }
}

