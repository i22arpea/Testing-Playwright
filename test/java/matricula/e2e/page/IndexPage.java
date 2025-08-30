package matricula.e2e.page;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

import lombok.Getter;
import matricula.e2e.page.ext.hosted_payment.CardPage;
import matricula.e2e.test.utils.TestProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author aaperez
 */

public class IndexPage {
    @Getter
    private final Page page;


    public IndexPage(Page page, TestProperties props) {
        this.page = page;
        page.setDefaultTimeout(props.getTimeout());
    }

    public IndexPage(CardPage cardPage) {
        this.page = cardPage.getPage();
    }

    public Locator getInputIdentificadorDeUsuario() {
        return page.locator("//*[@id=\'login\']");
    }

    public Locator getInputPassword() {
        return page.locator("#password");
    }

    public Locator getBotonIdentificarme() {
        return page.locator("#btnLoginSecure");
    }

    public Locator getTitulo() {
        return page.locator("#loginTitulo");
    }

    public Locator getPanelErrorLogin() {
        return page.locator("#errorLoginPanel");
    }

    //localización del error al loguear sin introducir el id
    public Locator getiderror() { return page.locator("//*[@id='head']/div/div/div[3]/span/div/div[1]/span[2]"); }

    //localización del error al loguear sin introducir el id
    public Locator getpassworderror() { return page.locator("//*[@id=\"head\"]/div/div/div[3]/span/div/div[2]/span[2]"); }

    public void ejecutarLogin(String usuario, String password) {
        getInputIdentificadorDeUsuario().fill(usuario);
        getInputPassword().fill(password);
        getBotonIdentificarme().click();
    }
}

