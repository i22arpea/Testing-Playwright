package matricula.e2e.page.secure.matricula.proceso.pestana;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

import lombok.Getter;
import matricula.e2e.page.secure.HomePage;
import matricula.e2e.page.secure.matricula.proceso.pestana.DatosEstadisticosPage;
import matricula.e2e.page.secure.matricula.proceso.pestana.DatosPersonalesPage;
import matricula.e2e.test.utils.TestProperties;
import matricula.e2e.test.utils.excepciones.TestExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

/**
 *
 * @author aaperez
 */

public class DatosRGPDPage {
    @Getter
    private final Page page;

    private static final String PAGE_NAME = "DatosRGPDPage";


    public DatosRGPDPage(HomePage homePage, TestProperties props) {
        this.page = homePage.getPage();
        page.setDefaultTimeout(props.getTimeout());
    }


    public DatosRGPDPage(DatosPersonalesPage datosPersonalesPage, TestProperties props) {
        this.page = datosPersonalesPage.getPage();
        page.setDefaultTimeout(props.getTimeout());
    }

    public DatosRGPDPage(DatosEstadisticosPage datosEstadisticosPage, TestProperties props) {
        this.page = datosEstadisticosPage.getPage();
        page.setDefaultTimeout(props.getTimeout());
    }

    public Locator getBarraProgreso() {
        return page.locator("#barraProgreso");
    }

    public Locator getBarraProgresoProteccionDeDatos() {
        return page.locator("#datosRGPDBubble");
    }

    public Locator getCheckAutorizo() {
        return page.locator("#chkAutorizo");
    }

    public Locator getCheckNoAutorizo() {
        return page.locator("#chkNoAutorizo");
    }

    public Locator getBotonGuardarContinuar() {
        return page.locator("#btn_lopd");
    }

    public List<Locator> getChecksClausulas() {
        List<Locator> checks = new ArrayList<>();
        Locator check;
        int i = 0;

        while ((check = page.locator("//*[@id=\"rep:" + Integer.toString(i) + ":clausulaRGPD\"]")).isVisible()) {
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

    /**
     * Verifica que la página de datos RGPD está correctamente cargada.
     * Lanza TestExecutionException si falla algún paso.
     */
    public void verificarCargaPagina() {
        try {
            if (!getBarraProgreso().isVisible()) {
                throw new TestExecutionException("#barraProgreso no visible", "ERROR_ELEMENTO", PAGE_NAME, page, "verificarCargaPagina");
            }
            assertThat(getBarraProgresoProteccionDeDatos()).hasAttribute("class", "active");

        } catch (Exception e) {
            throw new TestExecutionException("Error en verificación de carga de página de datos RGPD", e, "ERROR_VERIFICACION", PAGE_NAME, page, "verificarCargaPagina");
        }
    }
}
