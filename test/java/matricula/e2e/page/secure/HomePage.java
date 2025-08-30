package matricula.e2e.page.secure;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

import lombok.Getter;
import matricula.e2e.page.IndexPage;
import matricula.e2e.test.utils.TestProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.test.context.TestPropertySource;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author aaperez
 */

public class HomePage {
    @Getter
    private final Page page;


    public HomePage(IndexPage indexPage, TestProperties props) {
        this.page = indexPage.getPage();
        page.setDefaultTimeout(props.getTimeout());
    }


    public HomePage(ProcesosPage procesosPage, TestProperties props) {
        this.page = procesosPage.getPage();
        page.setDefaultTimeout(props.getTimeout());
    }

    public Locator getBotonMatricularme() {
        if(page.locator("//*[@id=\"procesosMatricula:0:btnMatricularme\"]").isVisible()) {
            return page.locator("//*[@id=\"procesosMatricula:0:btnMatricularme\"]");
        }else if(page.locator("//*[@id=\"procesosMatricula:1:btnMatricularme\"]").isVisible()) {
            return page.locator("//*[@id=\"procesosMatricula:1:btnMatricularme\"]");
        }else if(page.locator("//*[@id=\"procesosMatricula:2:btnMatricularme\"]").isVisible()) {
            return page.locator("//*[@id=\"procesosMatricula:2:btnMatricularme\"]");
        }else if(page.locator("//*[@id=\"BtnMatDeuda0\"]").isVisible()) {
            return page.locator("//*[@id=\"BtnMatDeuda0\"]");
        }else if(page.locator("//*[@id=\"BtnMatDeuda1\"]").isVisible()) {
            return page.locator("//*[@id=\"BtnMatDeuda1\"]");
        }else if(page.locator("//*[@id=\"BtnMatNormativa0\"]").isVisible()) {
            return page.locator("//*[@id=\"BtnMatNormativa0\"]");
        }else if(page.locator("//*[@id=\"BtnMatNormativa1\"]").isVisible()) {
            return page.locator("//*[@id=\"BtnMatNormativa1\"]");
        } else if(page.locator("//*[@id=\"BtnMatCalPendientes0\"]").isVisible()) {
            return page.locator("//*[@id=\"BtnMatCalPendientes0\"]");
        } else if(page.locator("//*[@id=\"BtnMatCalPendientes1\"]").isVisible()) {
            return page.locator("//*[@id=\"BtnMatCalPendientes1\"]");
        } else if(page.locator("//*[@id=\"procesosMatriculaPeque:0:btnMatricularme\"]").isVisible()) {
            return page.locator("//*[@id=\"procesosMatriculaPeque:0:btnMatricularme\"]");
        } else if(page.locator("//*[@id=\"procesosMatriculaPeque:1:btnMatricularme\"]").isVisible()) {
            return page.locator("//*[@id=\"procesosMatriculaPeque:1:btnMatricularme\"]");
        }
        return null;
    }

    public List<Locator> getBotonesTFG() {
        List<Locator> checks = new ArrayList<>();
        Locator check;
        int i = 0;

        while ((check = page.locator("//*[@id=\"rep:" + Integer.toString(i) + ":accesoTFG\"]")).isVisible()) {
            checks.add(check);
            i++;
        }

        return checks;
    }

    public Locator getBotonAceptarClausula() {
        return page.locator("//*[@id=\"AcceptTFG\"]");
    }

    public Locator getBotonPagarMatricula() {
        return page.locator("//*[@id=\"procesosMatricula:0:btnPagarMatricula\"]");
    }


    //TF

    public Locator getBotonTF() {
        if(page.locator("//*[@id=\"rep:0:accesoTFG\"]").isVisible()) {
            return page.locator("//*[@id=\"rep:0:accesoTFG\"]");
        }else if(page.locator("//*[@id=\"rep:1:accesoTFG\"]").isVisible()) {
            return page.locator("//*[@id=\"rep:1:accesoTFG\"]");
        }
        else if(page.locator("//*[@id=\"rep:0:selectTFG\"]").isVisible()) {
            return page.locator("//*[@id=\"rep:0:selectTFG\"]");
        }
        return null;
    }

    public Locator getPaneTF() {
        return page.locator("//*[@id=\"panelInfoTFE\"]");
    }

    public List<Locator> getChecksClausulas() {
        List<Locator> checks = new ArrayList<>();
        Locator check;
        int i = 0;

        while ((check = page.locator("//*[@id=\"repTFE:"+ Integer.toString(i) +":ClausulaTFE\"]")).isVisible()) {
            checks.add(check);
            i++;
        }

        return checks;
    }


    public void rellenarClausulas() {

        for (int i = 0; i < getChecksClausulas().size(); i++) {

            if (!getChecksClausulas().get(i).isDisabled()) {
                getChecksClausulas().get(i).check();

            }

            page.waitForTimeout(200);


        }


    }

    public Locator getBotonAceptarClausulas() {
        return page.locator("//*[@id=\"BtnClausulasTFE\"]");
    }

    public Locator getBotonModificarMatricula() {
        return page.locator("//*[@id=\"repMod:0:btnModificacion\"]");
    }


}



