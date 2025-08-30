package matricula.e2e.page.ext.tpv;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

import lombok.Getter;
import matricula.e2e.page.secure.matricula.proceso.pago.FormularioTPVPage;
import matricula.e2e.page.secure.matricula.proceso.pestana.DatosTFEPage;
import matricula.e2e.test.utils.TestProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


public class RecepcionInfoPagoPage {
    @Getter
    private final Page page;

    public RecepcionInfoPagoPage(FormularioTPVPage formularioTPVPage, TestProperties props) {
        this.page = formularioTPVPage.getPage();
        page.setDefaultTimeout(props.getTimeout());
    }

    public RecepcionInfoPagoPage(DatosTFEPage tfePage) {
        this.page = tfePage.getPage();
    }

    public Locator getInputCorreoPagador() {
        return page.locator("#inputCorreoPagador");
    }

    public Locator getInputTelefono() {
        return page.locator("#tlf");
    }

    public Locator getInputDireccion1() {
        return page.locator("#direccLine1");
    }

    public Locator getInputCiudad() {
        return page.locator("#textCity");
    }

    public Locator getInputCodigoPostal() {
        return page.locator("#inputCodigoPostal");
    }

    public Locator getBotonContinuar() {
        return page.locator("#btnSubmitJS");
    }

    public void rellenarDatosInfoPago(){

        getInputCorreoPagador().fill("a@gmail.com");
        getInputTelefono().fill("666666789");
        getInputDireccion1().fill("Calle Falsa");
        getInputCiudad().fill("Sevilla");
        getInputCodigoPostal().fill("41081");

    }
}
