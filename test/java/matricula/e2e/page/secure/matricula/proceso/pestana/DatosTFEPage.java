package matricula.e2e.page.secure.matricula.proceso.pestana;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import lombok.Getter;
import matricula.e2e.page.secure.HomePage;
import matricula.e2e.test.utils.TestProperties;
import matricula.e2e.test.utils.excepciones.TestExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author aaperez
 */

public class DatosTFEPage {
    @Getter
    private final Page page;

    private static final String PAGE_NAME = "DatosTFEPage";


    public DatosTFEPage(HomePage homePage, TestProperties props) {
        this.page = homePage.getPage();
        page.setDefaultTimeout(props.getTimeout());
    }

    public Locator getSelectConvocatoria() {
        return page.locator("#ConvTFE");
    }

    public Locator getBotonMatricular() {
        return page.locator("#btnGuardar");
    }

    public List<Locator> getChecksGrupos() {
        List<Locator> checks = new ArrayList<>();
        Locator check;
        int i = 0;

        while ((check = page.locator("//*[@id=\"repGrTF:"+ Integer.toString(i) +":checkGrTF\"]")).isVisible()) {
            checks.add(check);
            i++;
        }

        return checks;
    }

    public Locator getBotonContinuarPago() {
        return page.locator("#btnToPago");
    }

    public Locator getBotonPagar() {
        return page.locator("#btnTpv");
    }

    public void rellenarGrupos() {
        try {
            for (int i = 0; i < getChecksGrupos().size(); i++) {
                try {
                    if (!getChecksGrupos().get(i).isDisabled()) {
                        getChecksGrupos().get(i).check();
                    }
                } catch (Exception e) {
                    throw new TestExecutionException("Error al marcar checkbox de grupo", e, "ERROR_CHECK", PAGE_NAME, page, "Checkbox grupo índice " + i);
                }
                page.waitForTimeout(200);
            }
        } catch (Exception e) {
            throw new TestExecutionException("Error al rellenar grupos", e, "ERROR_FORM", PAGE_NAME, page, "rellenarGrupos");
        }
    }

}
