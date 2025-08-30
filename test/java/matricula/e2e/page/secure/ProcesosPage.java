package matricula.e2e.page.secure;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import lombok.Getter;
import matricula.e2e.page.IndexPage;
import matricula.e2e.test.utils.TestProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author aaperez
 */

public class ProcesosPage {
    @Getter
    private final Page page;

    public ProcesosPage(IndexPage indexPage, TestProperties props) {
        this.page = indexPage.getPage();
        page.setDefaultTimeout(props.getTimeout());
    }

    public Locator getBotonContinuar() {
        return page.locator("#j_idt109");
    }


    public List<Locator> getBotonesConfirmarInscripcion() {
        List<Locator> checks = new ArrayList<>();
        Locator check;
        int i = 0;

        while ((check = page.locator("//*[@id=\"rep:" + Integer.toString(i) + ":confirmarIns\"]")).isVisible()) {
            checks.add(check);
            i++;
        }

        return checks;
    }

    public Locator getBotonContinuarIns() {return page.locator("//*[@id=\"BtnCont\"]");}

    public Locator getBotonContinuarDECA() {return page.locator("//*[@id=\"BtnContDECA\"]");}


}
