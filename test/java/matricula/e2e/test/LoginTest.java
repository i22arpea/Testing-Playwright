package matricula.e2e.test;

import com.microsoft.playwright.*;
import com.microsoft.playwright.impl.junit.PlaywrightExtension;
import lombok.extern.slf4j.Slf4j;
import matricula.e2e.page.IndexPage;
import matricula.e2e.test.utils.TestProperties;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.nio.file.Paths;
import java.util.Locale;
import java.util.ResourceBundle;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

/**
 *
 * @author aaperez
 */
@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestPropertySource(locations = "classpath:playwright.properties")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(PlaywrightExtension.class)
class LoginTest {

    @Autowired
    private TestProperties props;

    private static Playwright playwright;
    private static Browser browser;

    private BrowserContext context;
    private BrowserContext context2;
    private Page page;

    private IndexPage loginPage;

    private static ResourceBundle rb;

    @BeforeAll
    public void setUpAll() {
        rb = ResourceBundle.getBundle("matricula/properties/Matricula", new Locale("ES"));
        playwright = Playwright.create();
        // Configurar navegador según las propiedades
        BrowserType browserType = null;
        switch(props.getNavegador().toLowerCase()) {
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
        );


    }

    @BeforeEach
    public void setUp() {

        if( props.isVideo()) {
            log.info("Grabando video de la prueba");
            // Crear contexto con grabación de video
            Browser.NewContextOptions options = new Browser.NewContextOptions()
                    .setRecordVideoDir(Paths.get("src/test/java/matricula/e2e/reports/videos/")) // Carpeta de salida
                    .setRecordVideoSize(1280, 720); // Tamaño del video
            context = browser.newContext(options);
        } else {
            log.info("No se grabará video de la prueba");
        }

        context = browser.newContext();
        loginPage = new IndexPage(context.newPage(), props);

        long startTime = System.currentTimeMillis();
        loginPage.getPage().navigate(props.getUrlLogin());
        long endTime = System.currentTimeMillis();
        log.info("Tiempo de carga de Matrícula: {} ms", endTime - startTime);
        log.info("");
        //assert (endTime - startTime) < 2000;

        loginPage.getPage().pause();
    }

    @AfterEach
    public void tearDown() {
        context.close();
    }

    @AfterAll
    public static void tearDownAll() {
        playwright.close();
    }

    /*
    * Pruebas de Funcionalidad
    */


    /**
     * Método de éxito del login
     */
    @Test
    void loginExito() {

        assertThat(loginPage.getPage()).hasURL(props.getUrlLogin());

        loginPage.ejecutarLogin("38228@a.com", "a");

        assertThat(loginPage.getPage()).hasURL(props.getUrlHome());
    }

    /**
     * Método de fallo del login mediante identificador falso y contraseña falsa
     */
    @Test
    void loginFalloIdentificador() {
        assertThat(loginPage.getPage()).hasURL(props.getUrlLogin());

        loginPage.ejecutarLogin("identificador_falso", "contraseña_falsa");

        assertThat(loginPage.getPanelErrorLogin()).isVisible();
        assertThat(loginPage.getPanelErrorLogin()).containsText(rb.getString("web_login_error_acceso_incorecto"));
    }


    /**
     * Método de fallo del login mediante contraseña falsa
     */
    @Test
    void loginFalloPassword() {
        assertThat(loginPage.getPage()).hasURL(props.getUrlLogin());

        loginPage.ejecutarLogin("38228@a.com", "contraseña_falsa");

        assertThat(loginPage.getPanelErrorLogin()).isVisible();
        assertThat(loginPage.getPanelErrorLogin()).containsText(rb.getString("web_login_error_acceso_incorecto"));
    }


    //tests de parámetros vacíos
    @Test
    void loginIdentificadorVacio() {
        assertThat(loginPage.getPage()).hasURL(props.getUrlLogin());

        loginPage.ejecutarLogin("", "contraseña_falsa");


        assertThat(loginPage.getiderror()).hasText("Es obligatorio introducir un valor");

    }


    /**
     * Método de fallo de login mediante contraseña vacía
     */
    @Test
    void loginPasswordVacia() {
        assertThat(loginPage.getPage()).hasURL(props.getUrlLogin());

        loginPage.ejecutarLogin("38228@a.com", "");

        assertThat(loginPage.getpassworderror()).hasText("Es obligatorio introducir un valor");
    }


    /**
     * Método de fallo de login con ambos parámetros vacíos
     */
    @Test
    void loginVacio() {
        assertThat(loginPage.getPage()).hasURL(props.getUrlLogin());

        loginPage.ejecutarLogin("", "");

        assertThat(loginPage.getiderror()).hasText("Es obligatorio introducir un valor");
        assertThat(loginPage.getpassworderror()).hasText("Es obligatorio introducir un valor");
    }


    //en este test encontramos el primer error de la página ya que si haces un intento de inicio de sesión con un usuario
    //luego borras ese usuario y haces otro intento de inicio de sesión aparece el usuario anterior


    /**
     * Método de fallo de login vacío despues de intento de login con un usuario
     */
    @Test
    void loginFallidoVacio() {
        assertThat(loginPage.getPage()).hasURL(props.getUrlLogin());

        loginPage.ejecutarLogin("38228@a.com", "");

        assertThat(loginPage.getpassworderror()).hasText("Es obligatorio introducir un valor");

        loginPage.ejecutarLogin("", "");

        //con el metodo hasText no detecta que se escribe de nuevo el login anterior por lo que hay que usar el metodo hasValue
        assertThat(loginPage.getInputIdentificadorDeUsuario()).hasValue("");
        assertThat(loginPage.getInputPassword()).hasValue("");
        assertThat(loginPage.getiderror()).hasText("Es obligatorio introducir un valor");
        assertThat(loginPage.getpassworderror()).hasText("Es obligatorio introducir un valor");
    }



}
