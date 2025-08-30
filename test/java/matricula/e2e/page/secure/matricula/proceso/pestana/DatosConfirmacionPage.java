package matricula.e2e.page.secure.matricula.proceso.pestana;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

import lombok.Getter;
import matricula.e2e.page.secure.HomePage;
import matricula.e2e.page.secure.matricula.proceso.pago.FormularioTPVPage;
import matricula.e2e.page.secure.matricula.proceso.pestana.DatosEconomicosPage;
import matricula.e2e.test.utils.TestProperties;
import matricula.e2e.test.utils.excepciones.TestExecutionException;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

/**
 *
 * @author aaperez
 */
public class DatosConfirmacionPage {
    @Getter
    private final Page page;

    private static final String PAGE_NAME = "DatosConfirmacionPage";




    public DatosConfirmacionPage(HomePage homePage, TestProperties props) {
        this.page = homePage.getPage();
        page.setDefaultTimeout(props.getTimeout());
    }

    public DatosConfirmacionPage(DatosEconomicosPage datosEconomicosPage, TestProperties props) {
        this.page = datosEconomicosPage.getPage();
        page.setDefaultTimeout(props.getTimeout());
    }

    public DatosConfirmacionPage(FormularioTPVPage formularioTPVPage, TestProperties props) {
        this.page = formularioTPVPage.getPage();
        page.setDefaultTimeout(props.getTimeout());
    }

    public Locator getBarraProgreso() {
        return page.locator("#barraProgreso");
    }

    public Locator getBarraProgresoConfirmacionDatos() {
        return page.locator("#confirmacionDatosBubble");
    }

    public Locator getBarraProgresoMatriculaFinalizada() {
        return page.locator("#matriculaFinalizadaBubble");
    }

    public Locator getBotonCondicionesNoDECA() {
        return page.locator("#btnCondicionesNoDECA");
    }

    public Locator getBotonCondicionesNI() {
        return page.locator("#btnCondicionesNI");
    }


    public Locator getBotonConfirmarDatos() {
        return page.locator("#btnConfirmarSinConsent");
    }

    public Locator getPanelConfirmacion() {
        return page.locator("#panelConfirmacionConf");
    }

    public Locator getBtnCerrar() {
        return page.locator("#btnCloseConf");
    }



    public Locator getBotonCondicionesDECA() {
        return page.locator("#btnCondicionesDECA");
    }

    public Locator getBotonAceptarCondicionesDECA() {
        return page.locator("#btnAceptarDECA");
    }

    public Locator getModalClausulas() {
        return page.locator("#dlgClausulas");
    }

    public Locator getBotonAceptarClausulas() {
        return page.locator("#btnAceptarClausulas");
    }

    public Locator getBotonAceptarClausulasNI() {
        return page.locator("#btnAceptarCondicionesNI");
    }

    public List<Locator> getChecksClausulas() {
        List<Locator> checks = new ArrayList<>();
        Locator check;
        int i = 0;

        while ((check = page.locator("//*[@id=\"repClausulas:"+ Integer.toString(i) +":checkClausula\"]")).isVisible()) {
            checks.add(check);
            i++;
        }

        return checks;
    }


    public void rellenarClausulas() {
        try {
            for (int i = 0; i < getChecksClausulas().size(); i++) {
                try {
                    if (!getChecksClausulas().get(i).isDisabled()) {
                        getChecksClausulas().get(i).check();
                    }
                } catch (Exception e) {
                    throw new TestExecutionException("Error al marcar checkbox de cláusula", e, "ERROR_CHECK", PAGE_NAME, page, "Checkbox cláusula índice " + i);
                }
                page.waitForTimeout(200);
            }
        } catch (Exception e) {
            throw new TestExecutionException("Error al rellenar cláusulas", e, "ERROR_FORM", PAGE_NAME, page, "rellenarClausulas");
        }
    }


    public Locator getBotonGuardarContinuar() {
        return page.locator("#btnConfirmar");
    }

    public Locator getBotonConfirmar() {
        return page.locator("#btnConfirmarDatos");
    }

    public Locator getBotonAceptar() {
        return page.locator("#btnAceptar");
    }

    public Locator getBotonCondicionesLSOL() {
        return page.locator("#condicionesLSOL");
    }

    /**
     * Verifica que la página de confirmación de datos está correctamente cargada.
     * Lanza TestExecutionException si falla algún paso.
     */
    public void verificarCargaPagina() {
        try {
            if (!getBarraProgreso().isVisible()) {
                throw new TestExecutionException("#barraProgreso no visible", "ERROR_ELEMENTO", PAGE_NAME, page, "verificarCargaPagina");
            }
            assertThat(getBarraProgresoConfirmacionDatos()).hasAttribute("class", "active");

        } catch (Exception e) {
            throw new TestExecutionException("Error en verificación de carga de página de confirmación de datos", e, "ERROR_VERIFICACION", PAGE_NAME, page, "verificarCargaPagina");
        }
    }

    /**
     * Realiza todo el proceso de confirmación de datos, incluyendo aceptación de condiciones y clicks necesarios.
     * Lanza TestExecutionException si falla algún paso.
     */
    public void confirmarDatos() {
        try {
            verificarCargaPagina();
        } catch (Exception e) {
            throw new TestExecutionException("Error al verificar carga de página de confirmación de datos", e, "ERROR_VERIFICACION", PAGE_NAME, page, "confirmarDatos");
        }
        try {
            if (getBotonCondicionesLSOL().isVisible()) {
                getBotonCondicionesLSOL().click();
            } else if( getBotonCondicionesDECA().isVisible()) {
                getBotonCondicionesNoDECA().click();
                getBotonAceptarClausulas().click();
            }
        } catch (Exception e) {
            throw new TestExecutionException("Error al aceptar condiciones", e, "ERROR_CLICK", PAGE_NAME, page, "confirmarDatos");
        }
        try {
            getBotonConfirmarDatos().click();
        } catch (Exception e) {
            throw new TestExecutionException("Error al hacer click en botón Confirmar Datos", e, "ERROR_CLICK", PAGE_NAME, page, "confirmarDatos");
        }

        try {
            getBotonConfirmar().click();
        } catch (Exception e) {
            throw new TestExecutionException("Error al hacer click en botón Confirmar", e, "ERROR_CLICK", PAGE_NAME, page, "confirmarDatos");
        }
        try {
            getBotonAceptar().click();
        } catch (Exception e) {
            throw new TestExecutionException("Error al hacer click en botón Aceptar", e, "ERROR_CLICK", PAGE_NAME, page, "confirmarDatos");
        }
    }
}
