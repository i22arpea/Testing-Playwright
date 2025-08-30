package matricula.e2e.test.utils.accesibilidad;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsoft.playwright.Page;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Component
@Slf4j
@Data
public class TestAccesibilidad {

    private String baseScreenshotsDir;
    private String baseReportsDir;
    private String axeScriptPath = "src/test/java/e2e/resources/axe.min.js";


    /**
     * Ejecuta la prueba de accesibilidad en la página actual
     *
     * @param page Página de Playwright
     * @param nombrePagina Nombre de la página para identificar la captura
     * @param tipoTest Tipo de test (Usabilidad, Responsive, etc.)
     * @throws Exception Si hay algún error durante la prueba
     */
    public void testAccessibility(Page page, String nombrePagina, String tipoTest) throws Exception {
        String outputDir = baseReportsDir + "accesibilidad/" + tipoTest + "/";
        try {
            Files.createDirectories(Path.of(outputDir));
            Files.createDirectories(Path.of(outputDir + "Excel/"));
        } catch (Exception e) {
            log.error("Error creando directorios de salida: {}", e.getMessage(), e);
            throw new Exception("Error creando directorios de salida", e);
        }

        String axeScript;
        try {
            axeScript = new String(Files.readAllBytes(Paths.get(axeScriptPath)));
        } catch (Exception e) {
            log.error("Error leyendo el script de axe-core: {}", e.getMessage(), e);
            throw new Exception("Error leyendo el script de axe-core", e);
        }
        try {
            //page.evaluate(axeScript);
            page.addInitScript(axeScript);
        } catch (Exception e) {
            log.error("Error evaluando el script de axe-core en la página: {}", e.getMessage(), e);
            throw new Exception("Error evaluando el script de axe-core en la página", e);
        }

        String screenshotPath = baseScreenshotsDir + nombrePagina + ".png";
        try {
            page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get(screenshotPath)).setFullPage(true));
        } catch (Exception e) {
            log.error("Error tomando la captura de pantalla: {}", e.getMessage(), e);
            throw new Exception("Error tomando la captura de pantalla", e);
        }

        Object results;
        try {
            results = page.evaluate("() => { " +
                    "return axe.run().then(results => { " +
                    "    results.violations.forEach(violation => { " +
                    "        violation.nodes.forEach(node => { " +
                    "            node.boundingBox = document.querySelector(node.target)?.getBoundingClientRect(); " +
                    "        }); " +
                    "    }); " +
                    "    return results; " +
                    "}); " +
                    "}");
        } catch (Exception e) {
            log.error("Error ejecutando el análisis de accesibilidad con axe: {}", e.getMessage(), e);
            throw new Exception("Error ejecutando el análisis de accesibilidad con axe", e);
        }

        ObjectMapper objectMapper = new ObjectMapper();
        String violationsJson;
        try {
            violationsJson = objectMapper.writeValueAsString(results);
        } catch (Exception e) {
            log.error("Error serializando los resultados de accesibilidad: {}", e.getMessage(), e);
            throw new Exception("Error serializando los resultados de accesibilidad", e);
        }

        String violationsJsonPath = baseReportsDir + "violations.json";
        try {
            Files.write(Paths.get(violationsJsonPath), violationsJson.getBytes());
        } catch (Exception e) {
            log.error("Error guardando el archivo violations.json: {}", e.getMessage(), e);
            throw new Exception("Error guardando el archivo violations.json", e);
        }

        String highlightedPath = baseScreenshotsDir + nombrePagina + "_highlighted.png";
        try {
            HighlightAccessibilityIssues.highlightViolations(
                    screenshotPath,
                    violationsJsonPath,
                    highlightedPath
            );
        } catch (Exception e) {
            log.error("Error resaltando violaciones en la imagen: {}", e.getMessage(), e);
            throw new Exception("Error resaltando violaciones en la imagen", e);
        }

        try {
            generateHtmlReport(violationsJson, nombrePagina, outputDir);
        } catch (Exception e) {
            log.error("Error generando el reporte HTML: {}", e.getMessage(), e);
            throw new Exception("Error generando el reporte HTML", e);
        }

        try {
            new AccessibilityExcelExporter().exportViolationsToExcel(
                    violationsJsonPath,
                    outputDir + "Excel/reporte_accesibilidad_" + nombrePagina + ".xlsx",
                    highlightedPath
            );
        } catch (Exception e) {
            log.error("Error exportando a Excel: {}", e.getMessage(), e);
            throw new Exception("Error exportando a Excel", e);
        }

        try {
            Files.deleteIfExists(Paths.get(screenshotPath));
            log.info("Imagen original eliminada correctamente.");
        } catch (IOException e) {
            log.warn("Error al eliminar la imagen original: " + e.getMessage());
        }
    }

    /**
     * Genera el reporte HTML de accesibilidad
     */
    private void generateHtmlReport(String json, String nombrePagina, String outputDir) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(json);

        // Construcción del HTML
        StringBuilder html = new StringBuilder();
        html.append("<!DOCTYPE html><html lang=\"es\"><head>")
                .append("<meta charset=\"UTF-8\">")
                .append("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">")
                .append("<title>Reporte de Accesibilidad ").append(nombrePagina).append("</title>")
                .append("<link href=\"https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css\" rel=\"stylesheet\">")
                .append("<script src=\"https://cdn.jsdelivr.net/npm/chart.js\"></script>")
                .append("</head><body class=\"container mt-4\">");

        html.append("<h1 class=\"text-center\">Reporte de Accesibilidad ").append(nombrePagina).append("</h1>");

        // Usar la imagen resaltada en el reporte
        html.append("<img src=\"../../screenshots/").append(nombrePagina).append("_highlighted.png\" class=\"img-fluid\" alt=\"Captura de pantalla con errores resaltados\">");

        // Generar tablas de resultados (violaciones, passes, etc.)
        generateViolationsTable(html, rootNode);
        generatePassesTable(html, rootNode);
        generateInapplicableTable(html, rootNode);
        generateIncompleteTable(html, rootNode);

        // Finalizar HTML
        html.append("<script src=\"https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js\"></script>")
                .append("</body></html>");

        // Guardar el HTML
        FileWriter fileWriter = new FileWriter(outputDir + "reporte" + nombrePagina + ".html");
        fileWriter.write(html.toString());
        fileWriter.close();
    }

    /**
     * Genera la tabla de violaciones en el reporte HTML
     */
    private void generateViolationsTable(StringBuilder html, JsonNode rootNode) {
        int violationCounter = 1;

        html.append("<h2 class=\"mt-4\">Violaciones encontradas</h2>")
                .append("<div class=\"table-responsive\">")
                .append("<table class=\"table table-bordered table-striped\">")
                .append("<thead class=\"table-dark\"><tr><th>#</th><th>ID</th><th>Impacto</th><th>Descripción</th><th>Solución</th></tr></thead>")
                .append("<tbody id=\"violationsTable\">");

        JsonNode violations = rootNode.get("violations");
        if (violations != null && violations.isArray() && violations.size() > 0) {
            for (JsonNode violation : violations) {
                html.append("<tr>")
                        .append("<td>").append(violationCounter++).append("</td>")
                        .append("<td>").append(violation.get("id").asText()).append("</td>")
                        .append("<td>").append(violation.get("impact").asText()).append("</td>")
                        .append("<td>").append(violation.get("help").asText()).append("</td>")
                        .append("<td>").append(violation.get("helpUrl").asText()).append("</td>")
                        .append("</tr>");
            }
        } else {
            html.append("<tr><td colspan=\"5\" class=\"text-center\">No se encontraron violaciones</td></tr>");
        }

        html.append("</tbody></table></div>");
    }

    /**
     * Genera la tabla de checks pasados, no aplicables e incompletos en el reporte HTML
     */
    private void generatePassesTable(StringBuilder html, JsonNode rootNode) {
        int passCounter = 1;

        html.append("<h2 class=\"mt-4\">Checks Pasados</h2>")
                .append("<div class=\"table-responsive\">")
                .append("<table class=\"table table-bordered table-striped\">")
                .append("<thead class=\"table-dark\"><tr><th>#</th><th>ID</th><th>Descripción</th></tr></thead>")
                .append("<tbody id=\"passesTable\">");

        JsonNode passes = rootNode.get("passes");
        if (passes != null && passes.isArray() && passes.size() > 0) {
            for (JsonNode pass : passes) {
                html.append("<tr>")
                        .append("<td>").append(passCounter++).append("</td>")
                        .append("<td>").append(pass.get("id").asText()).append("</td>")
                        .append("<td>").append(pass.get("description").asText()).append("</td>")
                        .append("</tr>");
            }
        } else {
            html.append("<tr><td colspan=\"3\" class=\"text-center\">No se encontraron checks pasados</td></tr>");
        }

        html.append("</tbody></table></div>");
    }

    /**
     * Genera la tabla de checks no aplicables e incompletos en el reporte HTML
     */
    private void generateInapplicableTable(StringBuilder html, JsonNode rootNode) {
        int inapplicableCounter = 1;

        html.append("<h2 class=\"mt-4\">Checks No Aplicables</h2>")
                .append("<div class=\"table-responsive\">")
                .append("<table class=\"table table-bordered table-striped\">")
                .append("<thead class=\"table-dark\"><tr><th>#</th><th>ID</th><th>Descripción</th></tr></thead>")
                .append("<tbody id=\"inapplicableTable\">");

        JsonNode inapplicable = rootNode.get("inapplicable");
        if (inapplicable != null && inapplicable.isArray() && inapplicable.size() > 0) {
            for (JsonNode item : inapplicable) {
                html.append("<tr>")
                        .append("<td>").append(inapplicableCounter++).append("</td>")
                        .append("<td>").append(item.get("id").asText()).append("</td>")
                        .append("<td>").append(item.get("description").asText()).append("</td>")
                        .append("</tr>");
            }
        } else {
            html.append("<tr><td colspan=\"3\" class=\"text-center\">No se encontraron checks no aplicables</td></tr>");
        }

        html.append("</tbody></table></div>");
    }

    /**
     * Genera la tabla de checks incompletos en el reporte HTML
     */
    private void generateIncompleteTable(StringBuilder html, JsonNode rootNode) {
        int incompleteCounter = 1;

        html.append("<h2 class=\"mt-4\">Checks Incompletos</h2>")
                .append("<div class=\"table-responsive\">")
                .append("<table class=\"table table-bordered table-striped\">")
                .append("<thead class=\"table-dark\"><tr><th>#</th><th>ID</th><th>Descripción</th></tr></thead>")
                .append("<tbody id=\"incompleteTable\">");

        JsonNode incomplete = rootNode.get("incomplete");
        if (incomplete != null && incomplete.isArray() && incomplete.size() > 0) {
            for (JsonNode item : incomplete) {
                html.append("<tr>")
                        .append("<td>").append(incompleteCounter++).append("</td>")
                        .append("<td>").append(item.get("id").asText()).append("</td>")
                        .append("<td>").append(item.get("description").asText()).append("</td>")
                        .append("</tr>");
            }
        } else {
            html.append("<tr><td colspan=\"3\" class=\"text-center\">No se encontraron checks incompletos</td></tr>");
        }

        html.append("</tbody></table></div>");
    }



}

