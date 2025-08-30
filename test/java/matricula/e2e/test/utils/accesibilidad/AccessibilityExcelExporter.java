package matricula.e2e.test.utils.accesibilidad;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.util.IOUtils;
import org.apache.poi.ss.util.CellRangeAddress;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
/**
 *
 * @author aaperez
 */
@Slf4j
public class AccessibilityExcelExporter {
    public void exportViolationsToExcel(String jsonPath, String excelPath, String screenshotPath) {
        try {
            // Leer JSON de accesibilidad
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode root = objectMapper.readTree(new File(jsonPath));
            JsonNode violations = root.get("violations");

            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("Accessibility Violations");

            // Título con imagen (si se quiere añadir visual)
            if (screenshotPath != null && Files.exists(Paths.get(screenshotPath))) {
                InputStream is = new FileInputStream(screenshotPath);
                byte[] bytes = IOUtils.toByteArray(is);
                int pictureIdx = workbook.addPicture(bytes, Workbook.PICTURE_TYPE_PNG);
                is.close();

                CreationHelper helper = workbook.getCreationHelper();
                Drawing drawing = sheet.createDrawingPatriarch();
                ClientAnchor anchor = helper.createClientAnchor();
                anchor.setCol1(5);
                anchor.setRow1(0);
                Picture pict = drawing.createPicture(anchor, pictureIdx);
                pict.resize(1.0);
            }

            // Cabecera
            Row header = sheet.createRow(0);
            header.createCell(0).setCellValue("Regla");
            header.createCell(1).setCellValue("Severidad");
            header.createCell(2).setCellValue("Descripción");
            header.createCell(3).setCellValue("Selector afectado");



            // Cuerpo
            int rowNum = 1;
            for (JsonNode violation : violations) {
                String rule = violation.get("id").asText();
                String severity = violation.get("impact").asText();
                String desc = violation.get("description").asText();

                for (JsonNode node : violation.get("nodes")) {
                    String selector = node.get("target").get(0).asText();

                    Row row = sheet.createRow(rowNum++);
                    row.createCell(0).setCellValue(rule);
                    row.createCell(1).setCellValue(severity);
                    row.createCell(2).setCellValue(desc);
                    row.createCell(3).setCellValue(selector);
                }
            }

            // Crear directorios si no existen
            File file = Paths.get(excelPath).toFile();
            File parent = file.getParentFile();
            if (parent != null && !parent.exists()) {
                parent.mkdirs();
            }

            // Guardar archivo
            try (FileOutputStream out = new FileOutputStream(excelPath)) {
                workbook.write(out);
            }

            //log.info("✅ Reporte exportado a: " + excelPath);
        } catch (IOException e) {
            log.warn("❌ Error al generar el Excel: " + e.getMessage());
        }
    }
}
