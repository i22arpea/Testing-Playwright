package matricula.e2e.test;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import com.deque.html.axecore.results.AxeResults;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsoft.playwright.*;
import com.microsoft.playwright.options.LoadState;
import com.microsoft.playwright.options.SelectOption;
import lombok.extern.slf4j.Slf4j;
import matricula.e2e.page.ext.hosted_payment.CardPage;
import matricula.e2e.page.ext.tpv.RecepcionInfoPagoPage;
import matricula.e2e.page.secure.ProcesosPage;
import matricula.e2e.page.secure.matricula.proceso.pestana.*;
import matricula.e2e.test.utils.MDCTestContextFilter;
import matricula.e2e.test.utils.accesibilidad.AccessibilityExcelExporter;
import matricula.e2e.test.utils.accesibilidad.HighlightAccessibilityIssues;
import matricula.e2e.test.utils.reporteExcel.ExtraccionExcel;
import matricula.e2e.test.utils.reporteExcel.TestResult;
import matricula.e2e.test.utils.servicioRest.ObtenerUsuarios;
import matricula.e2e.test.utils.TestProperties;
import matricula.e2e.test.utils.servicioRest.ResetearUsuarios;
import matricula.ejb.matricula.dto.FuturosEstudiantesAdmisiones;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import com.microsoft.playwright.impl.junit.PlaywrightExtension;
import com.microsoft.playwright.options.WaitForSelectorState;

import matricula.e2e.page.IndexPage;
import matricula.e2e.page.secure.HomePage;
import matricula.e2e.page.secure.matricula.proceso.pago.FormularioTPVPage;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import javax.annotation.PostConstruct;

/**
 *
 * @author aaperez
 */
@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestPropertySource(locations = "classpath:playwright.properties")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(PlaywrightExtension.class)
@Execution(ExecutionMode.CONCURRENT)
public class ProcesoInscripcionNuevoIngresoTest {

    @Autowired
    private TestProperties props;

    @Autowired
    private ObtenerUsuarios obtenerUsuarios;

    @Autowired
    private ResetearUsuarios resetearUsuarios;

    private static Playwright playwright;
    private static APIRequestContext request;
    private RecepcionInfoPagoPage recepcionInfoPagoPage;
    private CardPage cardPage;
    private int contador = 1;
    private static long start;
    private static long startTimeMs;

    // ConcurrentHashMap para resultados compartidos entre hilos
    private static final ConcurrentHashMap<String, TestResult> resultMap = new ConcurrentHashMap<>();

    private static final AtomicInteger TEST_COUNTER = new AtomicInteger(0);
    private static final long DELAY_BETWEEN_TESTS = 10000;

    // Variables para guardar la última ejecución
    private static volatile long lastExecutionTime = 0;

    private Semaphore TEST_SEMAPHORE;

    @PostConstruct
    public void initSemaphore() {
        TEST_SEMAPHORE = new Semaphore(props.getUsuarios_en_paralelo());
    }


    @BeforeAll
    public void setUpAll() {
        playwright = Playwright.create();
        request = playwright.request().newContext();

        // Registro un test por defecto para logs globales
        MDCTestContextFilter.registerTest("Playwright-Setup", "global");
        log.info("Iniciando configuración de tests de Playwright");
    }

    //obtener listado de usuarios en función de los datos pasados
     Stream<FuturosEstudiantesAdmisiones> usuarios(){
        return obtenerUsuarios.obtenerUsuariosAdmisiones(request, props.getRestUsuariosNuevoIngreso(), props.getNumero_alumnos());
    }

    @BeforeEach
    public void setUp() throws InterruptedException {
        synchronized (ProcesoInscripcionNuevoIngresoTest.class) {
            long now = System.currentTimeMillis();
            long wait = lastExecutionTime + DELAY_BETWEEN_TESTS - now;
            if (wait > 0) {
                Thread.sleep(wait);
            }
            lastExecutionTime = System.currentTimeMillis();
        }
    }

    @AfterEach
    public void tearDown() {
        // No es necesario limpiar el MDC aquí ya que se hace en el método del test
    }

    @AfterAll
    public void tearDownAll() {
        // Limpiar el contexto MDC global
        MDCTestContextFilter.clearTest("Playwright-Setup");
        log.info("Finalizando todos los tests de Playwright");

        // Convertir el mapa concurrente a lista para el reporte final
        List<TestResult> finalResults = new ArrayList<>(resultMap.values());
        ExtraccionExcel.writeResults(finalResults, "src/test/java/matricula/e2e/reports/results/ProcesoInscripcionNuevoIngreso.xlsx");
    }

    /**
     * Método de test que realiza el proceso de inscripción completo para un usuario de admisión.
     * @param estudiante El estudiante que se va a inscribir.
     */
    @ParameterizedTest
    @MethodSource("usuarios")
    void procesoInscripcionCompletoTest(FuturosEstudiantesAdmisiones estudiante) throws InterruptedException {
        TEST_SEMAPHORE.acquire();
        Browser browser = null;
        BrowserContext context = null;
        Page page = null;

        // Identificador único para este test específico
        String testId = String.format("Playwright-%03d", TEST_COUNTER.incrementAndGet());

        // Extraer solo la parte local del email (antes del @)
        String uid = estudiante.getUid();

        try {
            // Registrar este test en el MDCTestContextFilter para configurar el contexto de log
            MDCTestContextFilter.registerTest(testId, uid);

            // Log de inicio de test
            log.info("==================== INICIO TEST {} con usuario: {} ====================",
                    testId, uid);

            // Ejecuciones subsiguientes: esperar 5 segundos
            log.info("Esperando 5 segundos antes de iniciar test con usuario {}", estudiante.getEmail());
            Thread.sleep(DELAY_BETWEEN_TESTS);

            // Actualizar el tiempo de la última ejecución
            lastExecutionTime = System.currentTimeMillis();

            // Crear y configurar Playwright con logging explícito
            log.debug("Creando instancia de Playwright para el test {}", testId);
            Playwright playwright = Playwright.create();
            APIRequestContext request = playwright.request().newContext();

            // Configurar navegador según las propiedades
            BrowserType browserType = null;
            log.debug("Configurando navegador: {}", props.getNavegador().toLowerCase());
            switch (props.getNavegador().toLowerCase()) {
                case "chromium":
                    browserType = Playwright.create().chromium();
                    break;
                case "firefox":
                    browserType = Playwright.create().firefox();
                    break;
                case "webkit":
                    browserType = Playwright.create().webkit();
                    break;
                default:
                    browserType = Playwright.create().chromium();
            }

            browser = browserType.launch(new BrowserType.LaunchOptions()
                    .setHeadless(props.isHeadless())
                    .setSlowMo(props.getSlowmo())
                    .setTimeout(props.getTimeout())
            );

            Browser.NewContextOptions options = new Browser.NewContextOptions();

            if (props.isVideo()) {
                options.setRecordVideoDir(Paths.get("src/test/java/matricula/e2e/reports/videos/NIMaster_Aceptacion")) // Carpeta de salida
                        .setRecordVideoSize(1280, 720); // Tamaño del video
            }

            switch (props.getDispositivo()) {
                case "tablet":
                    options.setViewportSize(800, 1280)
                            .setUserAgent("Mozilla/5.0 (iPad; CPU OS 13_2 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/13.0.3 Mobile/15E148 Safari/604.1");
                    break;
                case "movil_moderno":
                    options.setViewportSize(390, 844)
                            .setUserAgent("Mozilla/5.0 (iPhone; CPU iPhone OS 15_0 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/15.0 Mobile/15E148 Safari/604.1");
                    break;
                case "movil_pequeno":
                    options.setViewportSize(320, 568)
                            .setUserAgent("Mozilla/5.0 (iPhone; CPU iPhone OS 12_0 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/12.0 Mobile/15A372 Safari/604.1");
                    break;
                case "escritorio":
                    options.setViewportSize(1920, 1080)
                            .setUserAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36");
                default: // escritorio
                    options.setViewportSize(1920, 1080)
                            .setUserAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36");
            }

            log.debug("Creando contexto del navegador para test {}", testId);
            context = browser.newContext(options);

            //Navegación al login
            IndexPage loginPage = new IndexPage(context.newPage(), props);
            log.info("Navegando a página de login: {}", props.getUrlLogin());
            loginPage.getPage().navigate(props.getUrlLogin());

            page = loginPage.getPage();

            startTimeMs = System.currentTimeMillis();

            if (props.isDepuracion()) {
                log.info("Depuración activada");
                loginPage.getPage().pause();
            } else {
                log.debug("Depuración desactivada");
            }

            log.info("Ejecutando test con usuario {} en hilo {}",
                    estudiante.getEmail(), Thread.currentThread().getId());

            String uniqueId = Thread.currentThread().getId() + "_" + System.currentTimeMillis();
            String testName = "ProcesoInscripcion-NuevoIngreso_" + uniqueId;

            try {
                contador++;

                log.info("Usuario de prueba: {}", estudiante.getEmail());

                //!RESETEO DE USUARIO
                if (props.isResetearUsuario()) {
                    log.debug("Reseteando usuario: {}", estudiante.getEmail());
                    boolean reseteoExitoso = resetearUsuarios.resetearUsuariosAdmisiones(estudiante.getIdCandidato(), estudiante.getIdProceso(), estudiante.getIdTitulacion(), request);
                    assertTrue(reseteoExitoso, "El reseteo del usuario falló.");

                    loginPage.getPage().waitForTimeout(1000);
                }

                //!PROCESO DE LOGIN
                log.info("Iniciando proceso de login con usuario: {}", estudiante.getEmail());
                loginPage.ejecutarLogin(estudiante.getEmail(), "a");
                HomePage homePage;

                //*Establece la url a la que accede el login
                String currenturl = loginPage.getPage().url();
                log.debug("URL actual después de login: {}", currenturl);


                //*Comprueba si la url a la que accede es la de proceso de admisión o la de home
                if (currenturl.equals(props.getUrlProcesos()) || currenturl.equals(props.getUrlProcesosNo())) {
                    log.info("Accediendo a página de procesos");
                    //*Espera a que aparezca el botón de la siguiente página
                    loginPage.getPage().waitForSelector("//*[@id=\"rep:0:confirmarIns\"]", new Page.WaitForSelectorOptions().setState(WaitForSelectorState.VISIBLE).setTimeout(60000));

                    try {
                        testAccessibility(page,"Procesos_Admision");
                    } catch (Exception e) {
                        log.warn("Error en test de accesibilidad: {}", e.getMessage());
                    }

                    ProcesosPage procesosPage = new ProcesosPage(loginPage, props);
                    page = procesosPage.getPage();

                    procesosPage.getBotonesConfirmarInscripcion().get(0).click();
                    homePage = new HomePage(procesosPage, props);

                } else {
                    homePage = new HomePage(loginPage, props);
                }

                page = homePage.getPage();

                //*Espera a que termine de cargar la página
                homePage.getPage().waitForLoadState(LoadState.NETWORKIDLE);

                //?Realiza el test de accesibilidad en la página de home
                try {
                    testAccessibility(page,"Home");
                } catch (Exception e) {
                    log.warn(e.getMessage());
                }

                homePage.getPage().waitForTimeout(6000);

                //*Presiona el botón de matriculación
                homePage.getBotonMatricularme().click();

                //*Espera a que aparezca el botón de la siguiente página
                homePage.getPage().waitForSelector("//*[@id=\"btnGuardarPer\"]", new Page.WaitForSelectorOptions().setState(WaitForSelectorState.VISIBLE).setTimeout(60000));

                //Crea una página de datos personales
                DatosPersonalesPage datosPersonalesPage = new DatosPersonalesPage(homePage, props);

                page = datosPersonalesPage.getPage();

                //Verifica que la página esté en datos personales
                datosPersonalesPage.verificarCargaPagina();

                //!PROCESO DE DATOS PERSONALES

                //?Realiza el test de accesibilidad en la página de datos personales
                try {
                    testAccessibility(page,"Datos_Personales_Primera_Sección");
                } catch (Exception e) {
                    log.warn(e.getMessage());
                }


                //*Rellena los datos personales de la primera sección
                datosPersonalesPage.rellenarPrimeraSeccion();

                //*Rellena los datos personales dela segunda sección
                datosPersonalesPage.rellenarSegundaSeccion();

                //?Realiza el test de accesibilidad en la página de datos personales
                try {
                    testAccessibility(page,"Datos_Personales_Segunda_Sección");
                } catch (Exception e) {
                    log.warn(e.getMessage());
                }


                //*Rellena los datos personales dela tercera sección
                datosPersonalesPage.rellenarTerceraSeccion();

                //?Realiza el test de accesibilidad en la página de datos personales
                try {
                    testAccessibility(page,"Datos_Personales_Tercera_Sección");
                } catch (Exception e) {
                    log.warn(e.getMessage());
                }

                //*Presiona el botón de continuar
                datosPersonalesPage.getPage().evaluate("document.querySelector('#btnGuardarPer').click()");

                //*Espera a que aparezca el botón de la siguiente página
                datosPersonalesPage.getPage().waitForSelector("//*[@id=\"btnGuardarEst\"]", new Page.WaitForSelectorOptions().setState(WaitForSelectorState.VISIBLE).setTimeout(60000));

                //Crea una página de datos estadísticos
                DatosEstadisticosPage datosEstadisticosPage = new DatosEstadisticosPage(datosPersonalesPage, props);

                page = datosEstadisticosPage.getPage();

                //Verifica que se encuentra en el estado de datos estadísticos
                assertThat(datosEstadisticosPage.getBarraProgreso()).isVisible();
                assertThat(datosEstadisticosPage.getBarraProgresoDatosEstadisticos()).hasAttribute("class", "active");

                //!PROCESO DE DATOS DE ACCESO A GRADO

                //?Realiza el test de accesibilidad en la página de datos estadísticos
                try {
                    testAccessibility(page,"Datos_Estadisticos");
                } catch (Exception e) {
                    log.warn(e.getMessage());
                }

                //*Rellena los datos estadísticos del alumno
                datosEstadisticosPage.rellenarDatosEstadisticos();

                //*Presiona el boton de continuar de página
                datosEstadisticosPage.getBotonGuardarContinuar().click();

                //*Espera a que aparezca el botón de la siguiente página
                datosEstadisticosPage.getPage().waitForSelector("//*[@id=\"btn_lopd\"]", new Page.WaitForSelectorOptions().setState(WaitForSelectorState.VISIBLE).setTimeout(60000));

                //Crea una página de datosRGPD
                DatosRGPDPage datosRGPDPage = new DatosRGPDPage(datosEstadisticosPage, props);

                page = datosRGPDPage.getPage();


                //Verifica que se encuentra en la página de datos RGPD
                //datosRGPDPage.verificarCargaPagina();

                //!PROCESO DE DATOS RGPD

                //?Realiza el test de accesibilidad en la página de datos de RPGD
                try {
                    testAccessibility(page,"Datos_RGPD");
                } catch (Exception e) {
                    log.warn(e.getMessage());
                }

                //*En caso de que haya check de autorización
                if (datosRGPDPage.getCheckAutorizo().isVisible()) {
                    datosRGPDPage.getCheckAutorizo().check();
                }

                //*Rellena las clausulas
                datosRGPDPage.rellenarClausulas();

                //*Presiona el botón de continuar de página
                datosRGPDPage.getBotonGuardarContinuar().click();

                //*Espera a que aparezca el botón de la siguiente página
                datosRGPDPage.getPage().waitForSelector("//*[@id=\"btn_Guardar\"]", new Page.WaitForSelectorOptions().setState(WaitForSelectorState.VISIBLE).setTimeout(80000));

                //Crea una página de datos académicos
                DatosAcademicosPage datosAcademicosPage = new DatosAcademicosPage(datosRGPDPage, props);

                page = datosAcademicosPage.getPage();

                //Verifica que se encuentra en la página de datos académicos
                datosAcademicosPage.verificarCargaPagina();

                //!PROCESO DE DATOS ACADÉMICOS

                //?Realiza el test de accesibilidad en la página de datos académicos
                try {
                    testAccessibility(page,"Datos_Academicos");
                } catch (Exception e) {
                    log.warn(e.getMessage());
                }

                if (datosAcademicosPage.getSelectGrupo().locator("option").count() >= 2) {
                    datosAcademicosPage.getSelectGrupo().selectOption(new SelectOption().setIndex(1));
                }

                //*Espera a que desaparezca el loading
                datosAcademicosPage.getPage().waitForSelector("//*[@id=\"loading\"]", new Page.WaitForSelectorOptions().setState(WaitForSelectorState.HIDDEN).setTimeout(60000));

                //*En caso de que deba aceptar reconocimientos
                if (datosAcademicosPage.getBotonGuardarContinuar().textContent().equals("Guardar y continuar con los reconocimientos")) {
                    //*Continúa y rellena las asignaturas para reconocer
                    datosAcademicosPage.getBotonGuardarContinuar().click();

                    //*Espera a que desaparezca el loading
                    datosAcademicosPage.getPage().waitForSelector("//*[@id=\"loading\"]", new Page.WaitForSelectorOptions().setState(WaitForSelectorState.HIDDEN).setTimeout(60000));

                    //?Realiza el test de accesibilidad en la página de datos RPGD
                    try {
                        testAccessibility(page,"Reconocimientos");
                    } catch (Exception e) {
                        log.warn(e.getMessage());
                    }

                    datosAcademicosPage.rellenarAsignaturas();
                    datosAcademicosPage.getBotonRenococimientos().click();
                    //*En caso contrario
                } else {
                    //*Presiona el botón de continuar
                    datosAcademicosPage.getBotonGuardarContinuar().click();
                }

                //*Espera a que cargue la respuesta de la página
                datosAcademicosPage.getPage().waitForSelector("//*[@id=\"loading\"]", new Page.WaitForSelectorOptions().setState(WaitForSelectorState.HIDDEN).setTimeout(60000));

                //*En caso de que haya solapes
                datosAcademicosPage.aceptacionSolapes();

                //*Espera a que aparezca el botón de la siguiente página
                datosAcademicosPage.getPage().waitForSelector("//*[@id=\"btnContinuar\"]", new Page.WaitForSelectorOptions().setState(WaitForSelectorState.VISIBLE).setTimeout(60000));

                //Crea una página de datos económicos
                DatosEconomicosPage datosEconomicosPage = new DatosEconomicosPage(datosAcademicosPage, props);

                page = datosEconomicosPage.getPage();

                //Verifica que se encuentra en la página de datos económicos
                datosEconomicosPage.verificarCargaPagina();

                //!PROCESO DE DATOS ECONÓMICOS

                //?Realiza el test de accesibilidad en la página de datos económicos
                try {
                    testAccessibility(page,"Datos_Economicos_Primera_Sección");
                } catch (Exception e) {
                    log.warn(e.getMessage());
                }

                //*Rellena los datos económicos
                if (datosEconomicosPage.getBotonTab().isVisible()) {
                    datosEconomicosPage.rellenarPrimeraSeccion();
                }

                //*Rellena los datos económicos
                if (datosEconomicosPage.getBotonTab2().isVisible()) {
                    datosEconomicosPage.rellenarSegundaSeccion();
                }

                //?Realiza el test de accesibilidad en la página de datos económicos
                try {
                    testAccessibility(page,"Datos_Economicos_Segunda_Seccion");
                } catch (Exception e) {
                    log.warn(e.getMessage());
                }

                //*Presiona el botón de continuar de página
                datosEconomicosPage.getPage().evaluate("document.querySelector('#btnContinuar').click()");

                //*Espera a que aparezca el botón de la siguiente página
                datosEconomicosPage.getPage().waitForSelector("//*[@id=\"loading\"]", new Page.WaitForSelectorOptions().setState(WaitForSelectorState.HIDDEN).setTimeout(60000));

                //Crea una página de confirmación de datos
                DatosConfirmacionPage confirmacionDatosPage = new DatosConfirmacionPage(datosEconomicosPage, props);

                page = confirmacionDatosPage.getPage();

                // Verifica que se encuentra en la página de confirmación de datos
                confirmacionDatosPage.verificarCargaPagina();

                //!PROCESO DE CONFIRMACIÓN DE DATOS

                //?Realiza el test de accesibilidad en la página de confirmaciónDatos
                try {
                    testAccessibility(page,"Confirmacion_Datos");
                } catch (Exception e) {
                    log.warn(e.getMessage());
                }

                    //*Pulsa en el botón de condiciones Nuevo Ingreso
                    confirmacionDatosPage.getBotonCondiciones().click();

                    //*Espera a que aparezca el botón de la siguiente página
                    confirmacionDatosPage.getPage().waitForTimeout(700);

                    //*Rellena las clausulas
                    confirmacionDatosPage.rellenarClausulas();

                    //?Realiza el test de accesibilidad en la página de datos personales
                    try {
                        testAccessibility(page,"Condiciones_NI");
                    } catch (Exception e) {
                        log.warn(e.getMessage());
                    }

                    //*Presiona el botón de aceptar la clausula
                    confirmacionDatosPage.getBotonAceptarCondiciones().click();

                    //*Presiona el botón de continuar de página
                    confirmacionDatosPage.getBotonGuardarContinuar().click();



                //*Presiona el botón de confirmar
                confirmacionDatosPage.getBotonConfirmar().click();

                //*Presiona el botón de aceptar
                confirmacionDatosPage.getBotonAceptar().click();

                //*Espera a que aparezca el botón de la siguiente página
                confirmacionDatosPage.getPage().waitForSelector("//*[@id=\"loading\"]", new Page.WaitForSelectorOptions().setState(WaitForSelectorState.HIDDEN).setTimeout(60000));

                //Crea una página de pago de matrícula
                FormularioTPVPage pagoMatriculaPage = new FormularioTPVPage(confirmacionDatosPage, props);

                page = pagoMatriculaPage.getPage();

                //Verifica que se encuentra en la página de formulario tpv
                pagoMatriculaPage.verificarCargaPagina();

                //?Realiza el test de accesibilidad en la página de pagoMatricula
                try {
                    testAccessibility(page,"Pago_Matricula");
                } catch (Exception e) {
                    log.warn(e.getMessage());
                }

                pagoMatriculaPage.getPage().waitForTimeout(5000);

                //*Verifica si aparece la pantalla de formalizar matricula
                if (pagoMatriculaPage.getBotonFormalizarMatricula().isHidden() && !pagoMatriculaPage.getTextRevSecretaria().isVisible()) {

                    if (ThreadLocalRandom.current().nextInt(20) == 0) {
                        //*Presiona el botón de confirmar el pago
                        pagoMatriculaPage.getBotonConfirmarPagar().click();

                        //Crea una página de recepción de información de pago
                        recepcionInfoPagoPage = new RecepcionInfoPagoPage(pagoMatriculaPage, props);

                        page = recepcionInfoPagoPage.getPage();

                        //Espera a que la página navegue a la URL indicada
                        recepcionInfoPagoPage.getPage()
                                .waitForCondition(() -> recepcionInfoPagoPage.getPage().url().equals(props.getUrlLoyolatpv()));

                        //Verifica que se encuentra en la URL correcta
                        assertThat(recepcionInfoPagoPage.getPage()).hasURL(props.getUrlLoyolatpv());

                        //!PROCESO DE RELLENO DE DATOS DE INFORMACIÓN DE PAGO

                        //?Realiza el test de accesibilidad en la página de recepciónInfoPago
                        try {
                            testAccessibility(page,"Recepcion_Info_Pago");
                        } catch (Exception e) {
                            log.warn(e.getMessage());
                        }

                        //*Rellena los datos de información de pago
                        recepcionInfoPagoPage.rellenarDatosInfoPago();

                        //*Presiona el botón de continuar proceso
                        recepcionInfoPagoPage.getBotonContinuar().click();

                        //Crea una página de datos de tarjeta
                        cardPage = new CardPage(recepcionInfoPagoPage, props);

                        page = cardPage.getPage();

                        //Espera a que la página navegue a la url indicada
                        cardPage.getPage().waitForCondition(() -> cardPage.getPage().url().matches(props.getPatternUrlHostedPayment()));

                        //Verifica que se encuentra en la url indicada
                        assertThat(cardPage.getPage()).hasURL(Pattern.compile(props.getPatternUrlHostedPayment()));

                        cardPage.getPage().waitForTimeout(500);

                        //!PROCESO DE RELLENO DE DATOS DE LA TARJETA

                        //?Realiza el test de accesibilidad en la página de cardPage
                        try {
                            testAccessibility(page,"Datos_Tarjeta");
                        } catch (Exception e) {
                            log.warn(e.getMessage());
                        }

                        //*Rellena los datos de la tarjeta
                        cardPage.rellenarDatosCard();

                        //*Pulsa en el botón de pagar ahora
                        cardPage.getBotonPagarAhora().click();

                        //Verifica que la página navega a la URL indicada
                        assertThat(cardPage.getPage()).hasURL("chrome-error://chromewebdata/");

                        //Espera a que se cargue la página de confirmación
                        cardPage.getPage().waitForSelector("#proceed-button", new Page.WaitForSelectorOptions().setState(WaitForSelectorState.VISIBLE).setTimeout(60000));

                        cardPage.getPage().waitForTimeout(500);

                        //Pulsa en el botón de proceder
                        cardPage.getPage().evaluate("document.querySelector('#proceed-button').click()");
                    } else {
                        pagoMatriculaPage.getBotonSimularPago().click();
                    }


                }else if (!pagoMatriculaPage.getBotonFormalizarMatricula().isHidden()) {
                    pagoMatriculaPage.getBotonFormalizarMatricula().click();
                    pagoMatriculaPage.getBotonConfirmarFormalizarMatricula().click();

                }

                //Crea una página de finalización de matrícula
                DatosMatriculaFinalizadaPage matriculaFinalizadaPage = new DatosMatriculaFinalizadaPage(homePage, props);

                page = matriculaFinalizadaPage.getPage();

                //Espera a que se cargue la página de matricula finalizada
                matriculaFinalizadaPage.getPage().waitForSelector("#barraProgreso", new Page.WaitForSelectorOptions().setState(WaitForSelectorState.VISIBLE).setTimeout(200000));

                // Espera y verifica que la página de matrícula finalizada se ha cargado correctamente
                matriculaFinalizadaPage.verificarCargaPagina();

                //?Realiza el test de accesibilidad en la página de matricula finalizada
                try {
                    testAccessibility(page,"matricula_finalizada");
                } catch (Exception e) {
                    log.warn(e.getMessage());
                }

                // Verifica la descarga de los PDFs
                matriculaFinalizadaPage.verificarDescargaPDFs();

                long tiempoActual = System.currentTimeMillis();
                long duracion = Math.max(1, tiempoActual - startTimeMs);
                String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
                resultMap.put(testName, new TestResult(
                        testName, estudiante.getEmail(), "PASSED",
                        "Test ejecutado correctamente", duracion, timestamp, null));

                log.info("Test completado exitosamente para usuario: {}", estudiante.getEmail());

            } catch (AssertionError | Exception e) {
                log.error("Error en test para usuario {}: {}", estudiante.getEmail(), e.getMessage(), e);

                // Capturar screenshot de error
                String uniqueErrorId = testName + "_" + System.currentTimeMillis();
                String safeId = uniqueErrorId.replaceAll("[\\\\/:*?\"<>|]", "_");
                String screenshotPath = "src/test/java/matricula/e2e/reports/screenshots/error_" + safeId + ".png";
                try {
                    page.evaluate("window.scrollTo(0, 0)");
                    page.screenshot(new Page.ScreenshotOptions()
                            .setPath(Paths.get(screenshotPath))
                            .setOmitBackground(true)
                            .setTimeout(0));
                    log.debug("Screenshot guardado en: {}", screenshotPath);
                } catch (Exception ex) {
                    log.warn("Error al capturar pantalla: " + ex.getMessage());
                }

                // Asegurar que la duración siempre sea al menos 1ms
                long tiempoActual = System.currentTimeMillis();
                long tiempoEjecucion = Math.max(1, tiempoActual - startTimeMs);

                String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
                resultMap.put(testName, new TestResult(
                        testName, estudiante.getEmail(), "FAILED",
                        e.getMessage(), tiempoEjecucion, timestamp, screenshotPath));
                throw e;
            }

            if (context != null) {
                log.debug("Cerrando contexto del navegador");
                context.close();
            }

        } finally {
            if (context != null) {
                context.close();
            }
            if (browser != null) {
                browser.close();
            }

            log.info("==================== FIN TEST {} para usuario: {} ====================",
                    testId, uid);

            // Limpiar el contexto MDC al finalizar el test
            MDCTestContextFilter.clearTest(testId);

            TEST_SEMAPHORE.release();
        }
    }

    @Test
    public void testAccessibility( Page page, String nombrePagina) throws Exception {


        // Cargar el script de axe-core en la página
        String axeScript = new String(Files.readAllBytes(Paths.get("src/test/java/matricula/e2e/resources/axe.min.js")));
        page.evaluate(axeScript);
        page.addInitScript(axeScript);

        page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get("src/test/java/matricula/e2e/reports/screenshots/"+nombrePagina+".png")).setFullPage(true));

        // Ejecuta axe.run() en el navegador y obtiene el resultado como JSON

        Object results = page.evaluate("() => { " +
                "return axe.run().then(results => { " +
                "    results.violations.forEach(violation => { " +
                "        violation.nodes.forEach(node => { " +
                "  node.boundingBox = document.querySelector(node.target)?.getBoundingClientRect(); " +
                "        }); " +
                "    }); " +
                "    return results; " +
                "}); " +
                "}");



        ObjectMapper objectMapper = new ObjectMapper();
        //pasa el objeto results a un Json
        String json = objectMapper.writeValueAsString(results);

        // Guardar el JSON en un archivo para procesarlo después
        Files.write(Paths.get("src/test/java/matricula/e2e/reports/violations.json"), json.getBytes());


        // Llamar a la función para resaltar violaciones en la captura de pantalla
        HighlightAccessibilityIssues.highlightViolations(
                "src/test/java/matricula/e2e/reports/screenshots/"+nombrePagina+".png",
                "src/test/java/matricula/e2e/reports/violations.json",
                "src/test/java/matricula/e2e/reports/screenshots/"+nombrePagina+"_highlighted.png"
        );

        Object result = page.evaluate("() => axe.run()");

        json = objectMapper.writeValueAsString(result);

        // Convierte el JSON en un objeto AxeResults
        AxeResults axeResults = objectMapper.readValue(json, AxeResults.class);
        //convierte el Json a HTML
        try {
            JsonNode rootNode = objectMapper.readTree(json);


            //MUESTRA LOS DATOS EN UN HTML

            // Construcción del HTML
            StringBuilder html = new StringBuilder();
            html.append("<!DOCTYPE html><html lang=\"es\"><head>")
                    .append("<meta charset=\"UTF-8\">")
                    .append("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">")
                    .append("<title>Reporte de Accesibilidad ").append(nombrePagina).append("</title>")
                    .append("<link href=\"https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css\" rel=\"stylesheet\">")
                    .append("<script src=\"https://cdn.jsdelivr.net/npm/chart.js\"></script>")
                    .append("</head><body class=\"container mt-4\">");

            html.append("<h1 class=\"text-center\">Reporte de Accesibilidad ").append(nombrePagina).append("</h1>");

            // Usar la imagen resaltada en el reporte
            html.append("<img src=../../screenshots/").append(nombrePagina).append("_highlighted.png class=\"img-fluid\" alt=\"Captura de pantalla con errores resaltados\">");

            int violationCounter = 1;  // Contador de violaciones

            // Tabla de violaciones
            html.append("<h2 class=\"mt-4\">Violaciones encontradas</h2>")
                    .append("<div class=\"table-responsive\">")
                    .append("<table class=\"table table-bordered table-striped\">")
                    .append("<thead class=\"table-dark\"><tr><th>#</th><th>ID</th><th>Impacto</th><th>Descripción</th><th>Solución</th></tr></thead>")
                    .append("<tbody id=\"violationsTable\">");

            JsonNode violations = rootNode.get("violations");
            if (violations != null && violations.isArray() && violations.size() > 0) {
                for (JsonNode violation : violations) {

                    String impact = violation.get("impact").asText();
                    String badgeClass = "bg-secondary"; // Default class

                    // Cambiar el color del badge según el valor de 'impact'
                    if ("critical".equalsIgnoreCase(impact)) {
                        badgeClass = "bg-danger";  // Color rojo para impacto alto
                    } else if ("serious".equalsIgnoreCase(impact)) {
                        badgeClass = "bg-warning"; // Color amarillo para impacto medio
                    } else if ("moderate".equalsIgnoreCase(impact)) {
                        badgeClass = "bg-success"; // Color verde para impacto bajo
                    }


                    html.append("<tr>")
                            .append("<td>").append(violationCounter).append("</td>")  // Número de la violación
                            .append("<td>").append(violation.get("id").asText()).append("</td>")
                            .append("<td><span class='badge ").append(badgeClass).append("'>").append(impact).append("</span></td>")
                            .append("<td>").append(violation.get("description").asText()).append("</td>")
                            .append("<td>").append(violation.get("help").asText()).append("</td>")
                            .append("</tr>");

                    violationCounter++;

                }
            } else {
                html.append("<tr><td colspan=\"4\" class=\"text-center\">No se encontraron violaciones.</td></tr>");
            }
            html.append("</tbody></table></div>");

            // Tabla de elementos "Passes"
            html.append("<h2 class=\"mt-4\">Pautas cumplidas</h2>")
                    .append("<div class=\"table-responsive\">")
                    .append("<table class=\"table table-bordered table-striped\">")
                    .append("<thead class=\"table-dark\"><tr><th>ID</th><th>Descripción</th></tr></thead>")
                    .append("<tbody>");

            JsonNode passes = rootNode.get("passes");
            if (passes != null && passes.isArray() && passes.size() > 0) {
                for (JsonNode pass : passes) {
                    html.append("<tr>")
                            .append("<td>").append(pass.get("id").asText()).append("</td>")
                            .append("<td>").append(pass.get("description").asText()).append("</td>")
                            .append("</tr>");
                }
            } else {
                html.append("<tr><td colspan=\"2\" class=\"text-center\">No se encontraron elementos.</td></tr>");
            }
            html.append("</tbody></table></div>");

            // Tabla de "Inapplicable"
            html.append("<h2 class=\"mt-4\">Resultados Inapplicable</h2>")
                    .append("<div class=\"table-responsive\">")
                    .append("<table class=\"table table-bordered table-striped\">")
                    .append("<thead class=\"table-dark\"><tr><th>ID</th><th>Descripción</th></tr></thead>")
                    .append("<tbody>");

            JsonNode inapplicable = rootNode.get("inapplicable");
            if (inapplicable != null && inapplicable.isArray() && inapplicable.size() > 0) {
                for (JsonNode item : inapplicable) {
                    html.append("<tr>")
                            .append("<td>").append(item.get("id").asText()).append("</td>")
                            .append("<td>").append(item.get("description").asText()).append("</td>")
                            .append("</tr>");
                }
            } else {
                html.append("<tr><td colspan=\"2\" class=\"text-center\">No hay resultados Inapplicable.</td></tr>");
            }
            html.append("</tbody></table></div>");

            // Tabla de "Incomplete"
            html.append("<h2 class=\"mt-4\">Resultados Incomplete</h2>")
                    .append("<div class=\"table-responsive\">")
                    .append("<table class=\"table table-bordered table-striped\">")
                    .append("<thead class=\"table-dark\"><tr><th>ID</th><th>Descripción</th></tr></thead>")
                    .append("<tbody>");

            JsonNode incomplete = rootNode.get("incomplete");
            if (incomplete != null && incomplete.isArray() && incomplete.size() > 0) {
                for (JsonNode item : incomplete) {
                    html.append("<tr>")
                            .append("<td>").append(item.get("id").asText()).append("</td>")
                            .append("<td>").append(item.get("description").asText()).append("</td>")
                            .append("</tr>");
                }
            } else {
                html.append("<tr><td colspan=\"2\" class=\"text-center\">No hay resultados Incomplete.</td></tr>");
            }
            html.append("</tbody></table></div>");

            // Gráfico de accesibilidad
            html.append("<div class=\"row mt-4\">")
                    .append("<div class=\"col-md-6\">")
                    .append("<canvas id=\"accessibilityChart\"></canvas>")
                    .append("</div></div>");


            // Bootstrap JS
            html.append("<script src=\"https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js\"></script>")
                    .append("</body></html>");


            // Antes de guardar el HTML en un archivo
            String outputDir = "src/test/java/matricula/e2e/reports/accesibilidad/NuevoIngreso/";
            Files.createDirectories(Path.of(outputDir)); // Crea la ruta si no existe

            // Guarda el HTML en un archivo
            FileWriter fileWriter = new FileWriter(outputDir + "reporte" + nombrePagina + ".html");
            fileWriter.write(html.toString());
            fileWriter.close();

            //Exportar resultados a excel
            new AccessibilityExcelExporter().exportViolationsToExcel(
                    "src/test/java/matricula/e2e/reports/violations.json",
                    "src/test/java/matricula/e2e/reports/accesibilidad/NuevoIngreso/Excel/reporte_accesibilidad_" + nombrePagina + ".xlsx",
                    "src/test/java/matricula/e2e/reports/screenshots/" + nombrePagina + "_highlighted.png"
            );

        } catch (
                IOException e) {
            e.printStackTrace();
        }

        try {
            Path imagePath = Paths.get("src/test/java/matricula/e2e/reports/screenshots/" + nombrePagina + ".png");
            Files.deleteIfExists(imagePath); // Esto elimina la imagen si existe

        } catch (IOException e) {
            log.warn("Error al eliminar la imagen: " + e.getMessage());
        }


    }

}
