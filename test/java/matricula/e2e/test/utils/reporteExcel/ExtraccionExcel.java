package matricula.e2e.test.utils.reporteExcel;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.common.usermodel.HyperlinkType;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import com.microsoft.playwright.Page;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
/**
 *
 * @author aaperez
 */
@Slf4j
public class ExtraccionExcel {

    public static void writeResults(List<TestResult> results, String fileName) {
        Workbook workbook = null;
        FileOutputStream fileOut = null;

        try {
            // Intentar abrir el archivo si ya existe o crear uno nuevo
            File file = new File(fileName);
            boolean existingFile = file.exists();

            if (existingFile) {
                file.delete(); // Elimina el archivo anterior para empezar desde cero
                workbook = new XSSFWorkbook();
                log.info("Archivo Excel eliminado y nuevo workbook creado: {}", fileName);
            } else {
                workbook = new XSSFWorkbook();
            }

            // Crear estructura del documento
            CreationHelper helper = workbook.getCreationHelper();

            // Eliminar hoja de resultados si ya existe
            int sheetIndex = workbook.getSheetIndex("Resultados");
            if (sheetIndex >= 0) {
                workbook.removeSheetAt(sheetIndex);
                log.info("Hoja 'Resultados' existente eliminada");
            }

            // Crear nueva hoja de resultados
            Sheet resultSheet = workbook.createSheet("Resultados");
            String[] COLUMNS = {"Test", "Usuario", "Resultado", "Mensaje", "Duración(s)", "Fecha", "Captura"};

            Row headerRow = resultSheet.createRow(0);
            for (int i = 0; i < COLUMNS.length; i++) {
                headerRow.createCell(i).setCellValue(COLUMNS[i]);
            }

            Drawing<?> resultDrawing = resultSheet.createDrawingPatriarch();
            int rowNum = 1;

            // Procesar resultados
            for (TestResult result : results) {
                try {
                    Row row = resultSheet.createRow(rowNum);
                    row.createCell(0).setCellValue(result.getTestName());
                    row.createCell(1).setCellValue(result.getUsuario());
                    row.createCell(2).setCellValue(result.getStatus());
                    row.createCell(3).setCellValue(result.getMessage());
                    row.createCell(4).setCellValue(result.getDuracion() / 1000);
                    row.createCell(5).setCellValue(result.getTimestamp());

                    String imagePath = result.getScreenshotPath();
                    procesarImagen(workbook, helper, row, imagePath, result.getTestName());

                    rowNum++;
                } catch (Exception e) {
                    log.error("Error al procesar resultado: {}", e.getMessage());
                }
            }

            // Ajustar columnas hoja principal
            for (int i = 0; i < COLUMNS.length; i++) {
                resultSheet.autoSizeColumn(i);
            }

            // Crear directorio si no existe
            File outFile = new File(fileName);
            if (!outFile.getParentFile().exists()) {
                outFile.getParentFile().mkdirs();
            }

            // Guardar el archivo nuevo
            fileOut = new FileOutputStream(outFile);
            workbook.write(fileOut);
            log.info("Archivo Excel guardado exitosamente: {}", fileName);

        } catch (Exception e) {
            log.error("Error al crear/guardar el archivo Excel: {}", e.getMessage());
            e.printStackTrace();

            // Intentar guardar en un archivo alternativo si falla
            if (workbook != null) {
                try {
                    String alternativeFileName = fileName.replace(".xlsx", "_alt_" + System.currentTimeMillis() + ".xlsx");
                    FileOutputStream altFileOut = new FileOutputStream(new File(alternativeFileName));
                    workbook.write(altFileOut);
                    altFileOut.close();
                    log.info("Se guardó en archivo alternativo: {}", alternativeFileName);
                } catch (Exception ex) {
                    log.error("Error al guardar archivo alternativo", ex);
                }
            }
        } finally {
            try {
                if (fileOut != null) {
                    fileOut.close();
                }
            } catch (IOException e) {
                log.warn("Error al cerrar FileOutputStream: {}", e.getMessage());
            }

            try {
                if (workbook != null) {
                    workbook.close();
                }
            } catch (IOException e) {
                log.warn("Error al cerrar workbook: {}", e.getMessage());
            }
        }
    }

    /**
     * Método auxiliar para procesar imágenes y crear hojas para capturas
     */
    private static void procesarImagen(Workbook workbook, CreationHelper helper, Row row, String imagePath, String testName) {
        if (imagePath != null && !imagePath.isEmpty()) {
            File imageFile = new File(imagePath);
            if (imageFile.exists()) {
                try {
                    byte[] imageBytes = Files.readAllBytes(imageFile.toPath());
                    int pictureIdx = workbook.addPicture(imageBytes, Workbook.PICTURE_TYPE_PNG);

                    // Crear nombre de hoja único
                    String hojaNombre = testName.replaceAll("[\\\\/:*?\"<>|]", "_");

                    // Si es una captura de excepción, añadir prefijo para identificarla
                    boolean esExcepcion = imagePath.contains("exception_");
                    if (esExcepcion) {
                        hojaNombre = "ERROR_" + hojaNombre;
                    }

                    // Limitar longitud del nombre (máx. 31 caracteres permitidos en Excel)
                    if (hojaNombre.length() > 31) {
                        hojaNombre = hojaNombre.substring(0, 31);
                    }

                    log.info("Preparando hoja para captura: {}", hojaNombre);

                    // Verificar si la hoja ya existe y eliminarla
                    int existingIndex = workbook.getSheetIndex(hojaNombre);
                    if (existingIndex >= 0) {
                        workbook.removeSheetAt(existingIndex);
                        log.info("Hoja existente '{}' eliminada", hojaNombre);
                    }

                    try {
                        // Crear nueva hoja
                        Sheet imageSheet = workbook.createSheet(hojaNombre);

                        // Aplicar estilo al título
                        CellStyle titleStyle = workbook.createCellStyle();
                        Font titleFont = workbook.createFont();
                        titleFont.setBold(true);
                        titleFont.setFontHeightInPoints((short) 14);

                        // Si es una excepción, usar rojo para el título
                        if (esExcepcion) {
                            titleFont.setColor(IndexedColors.RED.getIndex());
                        }

                        titleStyle.setFont(titleFont);

                        // Crear título
                        Row titleRow = imageSheet.createRow(0);
                        Cell titleCell = titleRow.createCell(0);
                        titleCell.setCellValue(esExcepcion ? "ERROR: " + testName : "Captura de: " + testName);
                        titleCell.setCellStyle(titleStyle);

                        // Crear botón de volver a resultados
                        Row backRow = imageSheet.createRow(1);
                        Cell backCell = backRow.createCell(0);
                        backCell.setCellValue("← Volver a Resultados");

                        CellStyle linkStyle = workbook.createCellStyle();
                        Font linkFont = workbook.createFont();
                        linkFont.setUnderline(Font.U_SINGLE);
                        linkFont.setColor(IndexedColors.BLUE.getIndex());
                        linkStyle.setFont(linkFont);
                        backCell.setCellStyle(linkStyle);

                        Hyperlink backLink = helper.createHyperlink(HyperlinkType.DOCUMENT);
                        backLink.setAddress("'Resultados'!A1");
                        backCell.setHyperlink(backLink);

                        // Si es excepción, mostrar información adicional
                        int rowOffset = 2;
                        if (esExcepcion) {
                            Row infoRow = imageSheet.createRow(2);
                            Cell infoCell = infoRow.createCell(0);
                            infoCell.setCellValue("Esta captura se generó automáticamente debido a un error durante la ejecución del test.");

                            // Extraer timestamp del nombre del archivo si existe
                            try {
                                String fileName = imageFile.getName();
                                String[] parts = fileName.split("_");
                                if (parts.length >= 3) {
                                    String dateStr = parts[parts.length - 2] + "_" + parts[parts.length - 1].replace(".png", "");
                                    try {
                                        LocalDateTime dateTime = LocalDateTime.parse(dateStr,
                                            DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));

                                        Row timestampRow = imageSheet.createRow(3);
                                        timestampRow.createCell(0).setCellValue("Momento del error: " +
                                            dateTime.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")));
                                        rowOffset = 4;
                                    } catch (Exception ex) {
                                        // No hacer nada si no se puede parsear la fecha
                                    }
                                }
                            } catch (Exception ex) {
                                // Ignorar errores al intentar extraer timestamp
                            }
                        }

                        // Añadir imagen
                        Drawing<?> drawing = imageSheet.createDrawingPatriarch();
                        ClientAnchor anchor = helper.createClientAnchor();
                        anchor.setCol1(0);
                        anchor.setRow1(rowOffset);

                        Picture pict = drawing.createPicture(anchor, pictureIdx);
                        pict.resize();

                        // Añadir hipervínculo en la hoja principal
                        Cell linkCell = row.createCell(6);
                        linkCell.setCellValue(esExcepcion ? "Ver error" : "Ver captura");

                        // Estilo para el enlace en la hoja principal
                        CellStyle resultLinkStyle = workbook.createCellStyle();
                        Font resultLinkFont = workbook.createFont();
                        resultLinkFont.setUnderline(Font.U_SINGLE);

                        // Si es excepción, usar color rojo para el enlace
                        if (esExcepcion) {
                            resultLinkFont.setColor(IndexedColors.RED.getIndex());
                        } else {
                            resultLinkFont.setColor(IndexedColors.BLUE.getIndex());
                        }

                        resultLinkStyle.setFont(resultLinkFont);
                        linkCell.setCellStyle(resultLinkStyle);

                        Hyperlink link = helper.createHyperlink(HyperlinkType.DOCUMENT);
                        link.setAddress("'" + hojaNombre + "'!A1");
                        linkCell.setHyperlink(link);

                        // Ajustar ancho de columna para el título
                        imageSheet.autoSizeColumn(0);
                    } catch (Exception e) {
                        log.error("Error al crear hoja para imagen: {}", e.getMessage());
                        row.createCell(6).setCellValue("Error al crear hoja");
                    }
                } catch (IOException e) {
                    row.createCell(6).setCellValue("Error al cargar imagen");
                    log.error("Error al procesar imagen: {}", e.getMessage());
                }
            } else {
                row.createCell(6).setCellValue("Imagen no encontrada");
            }
        } else {
            row.createCell(6).setCellValue("Sin captura");
        }
    }

    /**
     * Captura una pantalla cuando ocurre una excepción y la añade al listado de resultados
     * @param page Página de Playwright donde ocurrió la excepción
     * @param testName Nombre del test
     * @param usuario Usuario con el que se ejecutó el test
     * @param e Excepción que se produjo
     * @param results Lista de resultados donde se añadirá la captura
     * @return La ruta del archivo de captura
     */
    public static String captureExceptionScreenshot(Page page, String testName, String usuario, Throwable e, List<TestResult> results) {
        if (page == null) {
            log.error("Page es nula, no se puede hacer captura de excepción");
            return null;
        }

        try {
            // Crear directorio para capturas si no existe
            String dirPath = "src/test/java/matricula/e2e/reports/screenshots";
            Files.createDirectories(Paths.get(dirPath));

            // Generar nombre único para la captura con timestamp
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
            String timestamp = LocalDateTime.now().format(formatter);
            String safeTestName = testName.replaceAll("[\\\\/:*?\"<>|]", "_");
            String fileName = dirPath + "/exception_" + safeTestName + "_" + timestamp + ".png";

            // Tomar la captura de pantalla con Playwright
            try {
                // Intentar hacer scroll arriba para capturar mejor el contexto
                page.evaluate("window.scrollTo(0, 0)");
                page.screenshot(new Page.ScreenshotOptions()
                        .setPath(Paths.get(fileName))
                        .setFullPage(true));

                log.info("Captura de excepción guardada: {}", fileName);

                // Crear un nuevo objeto TestResult con la información de la excepción
                TestResult result = new TestResult(
                        testName,
                        usuario,
                        "FAILED",
                        e.getMessage() != null ? e.getMessage() : "Error sin mensaje: " + e.getClass().getName(),
                        System.currentTimeMillis(),
                        LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
                        fileName
                );

                // Añadir a la lista de resultados si se proporciona
                if (results != null) {
                    results.add(result);
                }

                return fileName;
            } catch (Exception screenshotException) {
                log.error("Error al capturar pantalla de excepción: {}", screenshotException.getMessage());
                return null;
            }
        } catch (Exception ex) {
            log.error("Error al preparar captura de excepción: {}", ex.getMessage());
            return null;
        }
    }

    /**
     * Ejecuta un test y captura automáticamente cualquier excepción que ocurra,
     * tomando una captura de pantalla y añadiéndola a la lista de resultados.
     *
     * @param page Página de Playwright a usar
     * @param testName Nombre del test
     * @param usuario Usuario del test
     * @param results Lista de resultados donde añadir el resultado
     * @param testAction La acción a ejecutar (lambda)
     */
    public static void executeTestWithScreenshotOnException(
            Page page,
            String testName,
            String usuario,
            List<TestResult> results,
            Runnable testAction) {

        long startTime = System.currentTimeMillis();

        try {
            // Ejecutar la acción del test
            testAction.run();

            // Si llegamos aquí, el test fue exitoso
            long duration = System.currentTimeMillis() - startTime;
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

            // Crear resultado exitoso (sin captura)
            TestResult successResult = new TestResult(
                    testName,
                    usuario,
                    "PASSED",
                    "Test ejecutado correctamente",
                    duration,
                    timestamp,
                    null
            );

            results.add(successResult);

        } catch (Throwable e) {
            // Capturar la excepción, tomar screenshot y añadir a resultados
            captureExceptionScreenshot(page, testName, usuario, e, results);

            // Re-lanzar la excepción para que el test falle normalmente
            if (e instanceof RuntimeException) {
                throw (RuntimeException) e;
            } else {
                throw new RuntimeException("Error en test: " + e.getMessage(), e);
            }
        }
    }
}

