package matricula.e2e.page.secure.matricula.proceso.pago;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

import lombok.Getter;
import matricula.e2e.page.secure.HomePage;
import matricula.e2e.page.secure.matricula.proceso.pestana.DatosConfirmacionPage;
import matricula.e2e.page.secure.matricula.proceso.pestana.PageElementException;
import matricula.e2e.test.utils.TestProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class FormularioTPVPage {
    @Getter
    private final Page page;




    public FormularioTPVPage(HomePage homePage, TestProperties props) {
        this.page = homePage.getPage();
        page.setDefaultTimeout(props.getTimeout());
    }

    public FormularioTPVPage(DatosConfirmacionPage confirmacionDatosPage, TestProperties props) {
        this.page = confirmacionDatosPage.getPage();
        page.setDefaultTimeout(60000);
    }

    public Locator getBarraProgreso() {
        return page.locator("#barraProgresoPago");
    }

    public Locator getBarraProgresoPagoMatricula() {
        return page.locator("#pagoMatriculaBubble");
    }

    public Locator getTextRevSecretaria() {
        return page.locator("#TextRevSecretaria");
    }




    public Locator getRadioTpv() {
        return page.locator("#checkTpv");
    }

    public Locator getRadioOtroMetodo() {
        return page.locator("#checkTpv");
    }

    public Locator getBotonConfirmarPagar() {
        return page.locator("#btnConfirmarPagarTPV");
    }

    public Locator getBotonConfirmarPagar2() {
        return page.locator("#btnConfirmarPagarTPV2");
    }


    public Locator getBotonConfirmarPagarDECA() {
        return page.locator("#btnConfirmarPagarTPVDECA");
    }

    public Locator getBotonPagoUnico() {
        return page.locator("#btnPagoUnico");
    }

    public Locator getBotonPagoDoble() {
        return page.locator("#btnPagoDoble");
    }


    public Locator getBotonFormalizarMatricula() {
        return page.locator("#btnFormalizarMatricula");
    }

    public Locator getBotonConfirmarFormalizarMatricula() {
        return page.locator("#btnConfirmarFormalizar");
    }

    public Locator getBotonSimularPago() {
        return page.locator("#btnSimularPago");
    }

    /**
     * Verifica que la página de pago de matrícula (TPV) está correctamente cargada.
     * Lanza PageElementException si falla algún paso.
     */
    public void verificarCargaPagina() {
        try {
            if (!getBarraProgreso().isVisible()) {
                throw new PageElementException("FormularioTPVPage", "#barraProgresoPago no visible");
            }
            assertThat(getBarraProgresoPagoMatricula()).hasAttribute("class", "active");

        } catch (Exception e) {
            throw new PageElementException("FormularioTPVPage", "Verificación de carga de página de pago de matrícula", e);
        }
    }

}
