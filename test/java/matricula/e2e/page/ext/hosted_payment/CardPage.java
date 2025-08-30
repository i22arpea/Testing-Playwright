package matricula.e2e.page.ext.hosted_payment;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

import lombok.Getter;
import matricula.e2e.page.ext.tpv.RecepcionInfoPagoPage;
import matricula.e2e.test.utils.TestProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.ThreadLocalRandom;

public class CardPage {
    @Getter
    private final Page page;

    public CardPage(RecepcionInfoPagoPage recepcionInfoPagoPage, TestProperties props) {
        this.page = recepcionInfoPagoPage.getPage();
        page.setDefaultTimeout(props.getTimeout());
    }

    public Locator getInputNumeroTarjeta() {
        return page.locator("#pas_ccnum");
    }

    public Locator getInputCaducidad() {
        return page.locator("#pas_expiry");
    }

    public Locator getInputCVV() {
        return page.locator("#pas_cccvc");
    }

    public Locator getInputNombreTitularTarjeta() {
        return page.locator("#pas_ccname");
    }

    public Locator getBotonPagarAhora() {
        return page.locator("#rxp-primary-btn");
    }

    /*
    type(text): Escribe el texto carácter por carácter, simulando la escritura humana.
    press(key): Simula la pulsación de una tecla específica (por ejemplo, press("Enter")).
    evaluate(): Puedes establecer el valor directamente usando JavaScript.
    */


    public void rellenarDatosCard() {

        getInputNumeroTarjeta().hover();
        getInputNumeroTarjeta().click();
        page.waitForTimeout(randomDelay());
        getInputNumeroTarjeta().type("4000120000001154", new Locator.TypeOptions().setDelay(100));
        page.keyboard().press("Tab");
        page.waitForTimeout(randomDelay());

        getInputCaducidad().hover();
        getInputCaducidad().click();
        page.waitForTimeout(randomDelay());
        getInputCaducidad().type("08/29", new Locator.TypeOptions().setDelay(100));
        page.keyboard().press("Tab");
        page.waitForTimeout(randomDelay());

        getInputCVV().hover();
        getInputCVV().click();
        page.waitForTimeout(randomDelay());
        getInputCVV().type("123", new Locator.TypeOptions().setDelay(100));
        page.keyboard().press("Tab");
        page.waitForTimeout(randomDelay());

        getInputNombreTitularTarjeta().hover();
        getInputNombreTitularTarjeta().click();
        page.waitForTimeout(randomDelay());
        getInputNombreTitularTarjeta().type("Nombre Falso", new Locator.TypeOptions().setDelay(100));

    }

    private int randomDelay() {
        // Retardo aleatorio entre 100 y 400 ms
        return ThreadLocalRandom.current().nextInt(100, 400);
    }

    public void rellenarDatosCardFalsa() {

        getInputNumeroTarjeta().fill("4000120000001154");
        getInputCaducidad().fill("08/27");
        getInputCVV().fill("577");
        getInputNombreTitularTarjeta().fill("Nombre Falso");


    }
}

