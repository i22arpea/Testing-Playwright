package matricula.e2e.page.secure.matricula.proceso.pestana;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

import com.microsoft.playwright.options.WaitForSelectorState;
import lombok.Getter;
import matricula.e2e.page.secure.HomePage;
import matricula.e2e.page.secure.matricula.proceso.pestana.DatosAcademicosPage;
import matricula.e2e.test.utils.TestProperties;
import matricula.e2e.test.utils.excepciones.TestExecutionException;
import net.datafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Locale;
import java.util.Random;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

/**
 *
 * @author aaperez
 */

public class DatosEconomicosPage {
    @Getter
    private final Page page;
    private static final String PAGE_NAME = "DatosEconomicosPage";
    private Faker faker = new Faker(new Locale("es"));



    public DatosEconomicosPage(HomePage homePage, TestProperties props) {
        this.page = homePage.getPage();
        page.setDefaultTimeout(props.getTimeout());
    }

    public DatosEconomicosPage(DatosAcademicosPage datosAcademicosPage, TestProperties props) {
        this.page = datosAcademicosPage.getPage();
        page.setDefaultTimeout(props.getTimeout());
    }

    public Locator getBarraProgreso() {
        return page.locator("#barraProgreso");
    }

    public Locator getBarraProgresoDatosEconomicos() {
        return page.locator("#datosEconomicosBubble");
    }

    //Sección 1

    public Locator getBotonTab() {
        return page.locator("#linkTab1");
    }

    public Locator getSelectTipoDocumento() {
        return page.locator("#tipodoc");
    }

    public Locator getInputTipoDocumento() {
        return page.locator("#documento");
    }

    public Locator getInputEmail() {
        return page.locator("#demail");
    }

    public Locator getInputNombre() {
        return page.locator("#cnomb");
    }

    public Locator getInputApellido1() {
        return page.locator("#cape1");
    }

    public Locator getInputApellido2() {
        return page.locator("#cape2");
    }

    public Locator getTelefonoTitular() {
        return page.locator("#ctele");
    }

    public Locator getMovilTitular() {
        return page.locator("#demovil");
    }

    //Sección 2

    public Locator getPanelBecaUnionEuropea() {
        return page.locator("#datosIBANPIC");
    }

    public Locator getInputIBANPIC() {
        return page.locator("#bibanpic");
    }

    public Locator getBotonGuardarContinuar() {
        return page.locator("#btnContinuar");
    }

    public Locator getFormaPago() {
        return page.locator("#formapago");
    }

    public Locator getCheckPagador() {
        return page.locator("#chkPagador");
    }

    public Locator getBotonTab2() {
        return page.locator("#linkTab2");
    }

    public Locator getInputDireccionTitular() {
        return page.locator("#ccalle");
    }

    public Locator getInputLocalidadTitular() {
        return page.locator("#cciud");
    }

    public Locator getInputCodigoPostalTitular() {
        return page.locator("#ccopost");
    }

    public Locator getInputNombreBanco() {
        return page.locator("#bnombre");
    }

    public Locator getInputIBAN() {
        return page.locator("#biban");
    }

    public void rellenarPrimeraSeccion() {
        try {
            getCheckPagador().check();
        } catch (Exception e) {
            throw new TestExecutionException("Error al marcar check pagador", e, "ERROR_CHECK", PAGE_NAME, page, "rellenarPrimeraSeccion");
        }
        try {
            page.waitForSelector("//*[@id=\"loading\"]", new Page.WaitForSelectorOptions().setState(WaitForSelectorState.HIDDEN).setTimeout(60000));
        } catch (Exception e) {
            throw new TestExecutionException("Error al esperar desaparición del loading", e, "ERROR_ESPERA", PAGE_NAME, page, "rellenarPrimeraSeccion");
        }
        try {
            getInputEmail().fill(faker.internet().emailAddress());
        } catch (Exception e) {
            throw new TestExecutionException("Error al rellenar email", e, "ERROR_FILL", PAGE_NAME, page, "rellenarPrimeraSeccion");
        }
        try {
            getInputNombre().fill(faker.name().firstName());
        } catch (Exception e) {
            throw new TestExecutionException("Error al rellenar nombre", e, "ERROR_FILL", PAGE_NAME, page, "rellenarPrimeraSeccion");
        }
        try {
            getInputApellido1().fill(faker.name().lastName());
        } catch (Exception e) {
            throw new TestExecutionException("Error al rellenar primer apellido", e, "ERROR_FILL", PAGE_NAME, page, "rellenarPrimeraSeccion");
        }
        try {
            getInputApellido2().fill(faker.name().lastName());
        } catch (Exception e) {
            throw new TestExecutionException("Error al rellenar segundo apellido", e, "ERROR_FILL", PAGE_NAME, page, "rellenarPrimeraSeccion");
        }
    }

    public void rellenarSegundaSeccion() {
        try {
            page.evaluate("document.querySelector('#linkTab2').click()");
        } catch (Exception e) {
            throw new TestExecutionException("Error al ir a la segunda sección", e, "ERROR_CLICK", PAGE_NAME, page, "rellenarSegundaSeccion");
        }
        try {
            getInputDireccionTitular().fill(faker.address().streetAddress());
        } catch (Exception e) {
            throw new TestExecutionException("Error al rellenar dirección titular", e, "ERROR_FILL", PAGE_NAME, page, "rellenarSegundaSeccion");
        }
        try {
            getInputLocalidadTitular().fill(faker.address().city());
        } catch (Exception e) {
            throw new TestExecutionException("Error al rellenar localidad titular", e, "ERROR_FILL", PAGE_NAME, page, "rellenarSegundaSeccion");
        }
        try {
            getInputCodigoPostalTitular().fill(faker.address().zipCode().replaceAll("[^0-9]", "").substring(0, 5));
        } catch (Exception e) {
            throw new TestExecutionException("Error al rellenar código postal titular", e, "ERROR_FILL", PAGE_NAME, page, "rellenarSegundaSeccion");
        }
        try {
            getInputNombreBanco().fill("Banco " + faker.company().name());
        } catch (Exception e) {
            throw new TestExecutionException("Error al rellenar nombre banco", e, "ERROR_FILL", PAGE_NAME, page, "rellenarSegundaSeccion");
        }
        try {
            // Mantenemos IBAN válido español para las pruebas
            getInputIBAN().fill("ES6621000418401234567891");
        } catch (Exception e) {
            throw new TestExecutionException("Error al rellenar IBAN", e, "ERROR_FILL", PAGE_NAME, page, "rellenarSegundaSeccion");
        }

        if (getPanelBecaUnionEuropea().isVisible()
                && getInputIBANPIC().inputValue().isEmpty()) {
            try {
                getInputIBANPIC().fill("ES9121000418450200051332");
            } catch (Exception e) {
                throw new TestExecutionException("Error al rellenar IBAN PIC", e, "ERROR_FILL", PAGE_NAME, page, "rellenarSegundaSeccion");
            }
        }

        if (!getTelefonoTitular().inputValue().startsWith("+")) {
            try {
                getTelefonoTitular()
                        .fill("+34" + faker.phoneNumber().cellPhone().replaceAll("[^0-9]", "").substring(0, 9));
            } catch (Exception e) {
                throw new TestExecutionException("Error al rellenar teléfono titular", e, "ERROR_FILL", PAGE_NAME, page, "rellenarSegundaSeccion");
            }
        }

        if (!getMovilTitular().inputValue().startsWith("+")) {
            try {
                getMovilTitular().fill("+34" + faker.phoneNumber().cellPhone().replaceAll("[^0-9]", "").substring(0, 9));
            } catch (Exception e) {
                throw new TestExecutionException("Error al rellenar móvil titular", e, "ERROR_FILL", PAGE_NAME, page, "rellenarSegundaSeccion");
            }
        }
    }

    /**
     * Verifica que la página de datos económicos está correctamente cargada.
     * Lanza TestExecutionException si falla algún paso.
     */
    public void verificarCargaPagina() {
        try {
            if (!getBarraProgreso().isVisible()) {
                throw new TestExecutionException(
                        "La barra de progreso no está visible",
                        "CARGA_PÁGINA",
                        PAGE_NAME,
                        page,
                        "verificarCargaPagina"
                );
            }
            assertThat(getBarraProgresoDatosEconomicos()).hasAttribute("class", "active");

        } catch (Exception e) {
            if (!(e instanceof TestExecutionException)) {
                throw new TestExecutionException(
                        "Error al verificar la carga de la página de datos económicos",
                        e,
                        "CARGA_PÁGINA",
                        PAGE_NAME,
                        page,
                        "verificarCargaPagina"
                );
            }
            throw e;
        }
    }
}
