package matricula.e2e.page.secure.matricula.proceso.pestana;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import lombok.Getter;
import matricula.e2e.page.ext.hosted_payment.CardPage;
import matricula.e2e.page.secure.HomePage;
import matricula.e2e.page.secure.matricula.proceso.pago.FormularioTPVPage;
import matricula.e2e.test.utils.TestProperties;
import matricula.e2e.test.utils.excepciones.TestExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.test.context.TestPropertySource;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

/**
 *
 * @author aaperez
 */


public class DatosMatriculaFinalizadaPage {
    @Getter
    private final Page page;

    private static final String PAGE_NAME = "DatosMatriculaFinalizadaPage";




    public DatosMatriculaFinalizadaPage(HomePage homePage, TestProperties props) {
        this.page = homePage.getPage();
        page.setDefaultTimeout(props.getTimeout());
    }


    public DatosMatriculaFinalizadaPage(CardPage cardPage, TestProperties props) {
        this.page = cardPage.getPage();
        page.setDefaultTimeout(props.getTimeout());
    }


    public DatosMatriculaFinalizadaPage(FormularioTPVPage formularioTPVPage, TestProperties props) {
        this.page = formularioTPVPage.getPage();
        page.setDefaultTimeout(props.getTimeout());
    }

    public Locator getBarraProgreso() {
        return page.locator("#barraProgreso");
    }

    public Locator getBarraProgresoMatriculaFinalizada() {
        return page.locator("#matriculaFinalizadaBubble");
    }

    /**
     * Espera y verifica que la página de matrícula finalizada se ha cargado correctamente.
     * Lanza TestExecutionException si falla algún paso.
     */
    public void verificarCargaPagina() {
        try {
            page.waitForSelector("#barraProgreso", new Page.WaitForSelectorOptions().setState(com.microsoft.playwright.options.WaitForSelectorState.VISIBLE).setTimeout(150000));
        } catch (Exception e) {
            throw new TestExecutionException("Error al esperar barra de progreso visible", e, "ERROR_ESPERA", PAGE_NAME, page, "Esperar barra de progreso visible");
        }
        try {
            if (!getBarraProgreso().isVisible()) {
                throw new TestExecutionException("#barraProgreso no visible", "ERROR_ELEMENTO", PAGE_NAME, page, "verificarCargaPagina");
            }
            assertThat(getBarraProgresoMatriculaFinalizada()).hasAttribute("class", "completed");
        } catch (Exception e) {
            throw new TestExecutionException("Error al verificar barra de progreso o estado finalizada", e, "ERROR_VERIFICACION", PAGE_NAME, page, "verificarCargaPagina");
        }
    }

    /**
     * Verifica la descarga de PDFs en la página de matrícula finalizada.
     * Lanza TestExecutionException si falla algún paso.
     */
    public void verificarDescargaPDFs() {
        Locator pdfIcons = page.locator("i.fa.fa-file-pdf-o:visible");
        int count;
        try {
            count = pdfIcons.count();
        } catch (Exception e) {
            throw new TestExecutionException("Error al contar iconos PDF", e, "ERROR_COUNT", PAGE_NAME, page, "Contar iconos PDF");
        }
        for (int i = 0; i < count; i++) {
            Locator icon = pdfIcons.nth(i);
            try {
                icon.waitFor(new Locator.WaitForOptions().setState(com.microsoft.playwright.options.WaitForSelectorState.VISIBLE));
            } catch (Exception e) {
                throw new TestExecutionException("Error al esperar icono PDF visible", e, "ERROR_ESPERA", PAGE_NAME, page, "Esperar icono PDF visible índice " + i);
            }
            try {
                Page pdfPage = page.waitForPopup(() -> {
                    icon.click();
                });
                pdfPage.waitForLoadState(com.microsoft.playwright.options.LoadState.NETWORKIDLE);
                if (pdfPage == null) {
                    throw new TestExecutionException("No se abrió una nueva página al hacer clic en el icono PDF", "ERROR_POPUP", PAGE_NAME, page, "Apertura PDF índice " + i);
                }
                pdfPage.close();
            } catch (Exception e) {
                throw new TestExecutionException("Error en apertura o cierre de PDF", e, "ERROR_PDF", PAGE_NAME, page, "Apertura o cierre de PDF índice " + i);
            }
        }
    }

}
