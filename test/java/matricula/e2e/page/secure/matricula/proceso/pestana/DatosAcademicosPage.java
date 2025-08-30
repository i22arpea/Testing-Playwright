package matricula.e2e.page.secure.matricula.proceso.pestana;

import java.util.ArrayList;
import java.util.List;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

import com.microsoft.playwright.options.SelectOption;
import com.microsoft.playwright.options.WaitForSelectorState;
import lombok.Getter;
import lombok.extern.java.Log;
import matricula.e2e.page.secure.HomePage;
import matricula.e2e.page.secure.matricula.proceso.pestana.DatosRGPDPage;
import matricula.e2e.test.utils.TestProperties;
import matricula.e2e.test.utils.excepciones.TestExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

/**
 *
 * @author aaperez
 */
@Log

public class DatosAcademicosPage {
    @Getter
    private final Page page;

    private static final String PAGE_NAME = "DatosAcademicosPage";

    public DatosAcademicosPage(HomePage homePage, TestProperties props) {
        this.page = homePage.getPage();
        page.setDefaultTimeout(props.getTimeout());
    }

    public DatosAcademicosPage(DatosRGPDPage datosRGPDPage, TestProperties props) {
        this.page = datosRGPDPage.getPage();
        page.setDefaultTimeout(props.getTimeout());
    }

    public Locator getBarraProgreso() {
        return page.locator("#barraProgreso");
    }

    public Locator getBarraProgresoDatosAcademicos() {
        return page.locator("#datosAcademicosBubble");
    }

    public Locator getSelectGrupo() {
        return page.locator("#grupoSeleccionado");
    }

    public Locator getBotonComplementosFormacion() {
        return page.locator("#BtnComplFormacion");
    }




    public List<Locator> getChecksAsignaturas() {
        List<Locator> checks = new ArrayList<>();
        Locator check;
        int i = 0;

        while ((check = page.locator("//*[@id=\"rep:" + Integer.toString(i) + ":chkAsignatura\"]")).isVisible()) {
            checks.add(check);
            i++;
        }

        return checks;
    }

    public List<Locator> getGruposAsignaturas() {
        List<Locator> grupos = new ArrayList<>();
        Locator grupo;
        int i = 0;

        while ((grupo = page.locator("//*[@id=\"rep:" + Integer.toString(i) + ":selectGruposAsignatura\"]"))
                .isVisible()) {
            grupos.add(grupo);
            i++;
        }

        return grupos;
    }

    public List<Locator> getColumnasAsignaturas() {
        List<Locator> grupos = new ArrayList<>();
        Locator grupo;
        int i = 0;

        while ((grupo = page.locator("//*[@id=\"rep:" + Integer.toString(i) + ":columnaAsignatura\"]"))
                .isVisible()) {
            grupos.add(grupo);
            i++;
        }

        return grupos;
    }

    public Locator getCreditosMinimos() {
        return page.locator("#creditosMinimos");
    }

    public Locator getCreditosMaximos() {
        return page.locator("#creditosMaximos");
    }

    public Locator getCreditosTotales() {
        return page.locator("#creditosTotales");
    }

    public Locator getCheckSolapes() {
        return page.locator("#chkSolapes");
    }

    public Locator getCheckSolapes2() {
        return page.locator("#chkSolapes2");
    }

    public Locator getBotonGuardarContinuar() {
        return page.locator("#btn_Guardar");
    }

    public Locator getModalErrorCheck() {
        return page.locator("#paneErrorCheck");
    }

    public Locator getModalErrorValidacion() {
        return page.locator("#paneErrorValidacion");
    }

    public Locator getSelectConvocatoriaTF() {
        return page.locator("#convTF");
    }



    public List<Locator> getChecksReconocimientos() {
        List<Locator> checks = new ArrayList<>();
        Locator check;
        int i = 0;

        while ((check = page.locator("//*[@id=\"repReconocimientos:"+ Integer.toString(i) +":asigReconocer:0:chkReconocimiento\"]")).isVisible()) {
            checks.add(check);
            i++;
        }

        return checks;
    }


    public Locator getBotonAceptarErrorCheck() {
        return page.locator("#btnAceptarErrorCheck");
    }

    public Locator getBotonRenococimientos() {
        return page.locator("#btn_GuardarReconocimientos");
    }

    public void rellenarAsignaturas() {
        try {
            for (int i = 0; i < getChecksAsignaturas().size()
                    && Double.parseDouble(getCreditosTotales().textContent()) < Double
                    .parseDouble(getCreditosMinimos().textContent()); i++) {
                try {
                    if (!getChecksAsignaturas().get(i).isDisabled()) {
                        getChecksAsignaturas().get(i).check();
                    }
                } catch (Exception e) {
                    throw new PageElementException(PAGE_NAME, "Checkbox asignatura índice " + i, e);
                }
                page.waitForTimeout(200);
            }
        } catch (Exception e) {
            throw new PageElementException(PAGE_NAME, "rellenarAsignaturas", e);
        }
    }

    public void rellenarMasAsignaturas() {
        try {
            for (int i = 0; i < getChecksAsignaturas().size()
                    && Double.parseDouble(getCreditosTotales().textContent()) <= Double
                    .parseDouble(getCreditosMaximos().textContent()); i++) {
                try {
                    if (!getChecksAsignaturas().get(i).isDisabled()) {
                        getChecksAsignaturas().get(i).check();
                    }
                } catch (Exception e) {
                    throw new PageElementException(PAGE_NAME, "Checkbox asignatura índice " + i, e);
                }
                page.waitForTimeout(200);
            }
        } catch (Exception e) {
            throw new PageElementException(PAGE_NAME, "rellenarMasAsignaturas", e);
        }
    }

    public void rellenarAsigyGrupos() {
        try {
            for (int i = 0; i < getChecksAsignaturas().size()
                    && Double.parseDouble(getCreditosTotales().textContent()) < Double
                    .parseDouble(getCreditosMinimos().textContent()); i++) {
                try {
                    if (!getChecksAsignaturas().get(i).isDisabled()) {
                        getChecksAsignaturas().get(i).check();
                    }
                } catch (Exception e) {
                    throw new TestExecutionException("Error al marcar checkbox de asignatura", e, "ERROR_CHECK", PAGE_NAME, page, "Checkbox asignatura índice " + i);
                }
                page.waitForTimeout(200);
                try {
                    if(!getGruposAsignaturas().get(i).isDisabled()) {
                        String str = getGruposAsignaturas().get(i).toString();
                        str = str.replaceFirst("^Locator@", "");
                        page.waitForSelector(str, new Page.WaitForSelectorOptions().setTimeout(60000));
                        getGruposAsignaturas().get(i).selectOption(new SelectOption().setIndex(1));
                    }
                } catch (Exception e) {
                    throw new TestExecutionException("Error al seleccionar grupo de asignatura", e, "ERROR_SELECT", PAGE_NAME, page, "Grupo asignatura índice " + i);
                }
            }
        } catch (Exception e) {
            throw new TestExecutionException("Error al rellenar asignaturas y grupos", e, "ERROR_FORM", PAGE_NAME, page, "rellenarAsigyGrupos");
        }
    }

    public void rellenarAsignaturasMarcadas() {
        try {
            for (int i = 0; i < getColumnasAsignaturas().size(); i++) {
                try {
                    if(!getGruposAsignaturas().get(i).isDisabled()) {
                        String str = getGruposAsignaturas().get(i).toString();
                        str = str.replaceFirst("^Locator@", "");
                        page.waitForSelector(str, new Page.WaitForSelectorOptions().setTimeout(60000));
                        getGruposAsignaturas().get(i).selectOption(new SelectOption().setIndex(1));
                    }
                } catch (Exception e) {
                    throw new TestExecutionException("Error al seleccionar grupo de asignatura marcada", e, "ERROR_SELECT", PAGE_NAME, page, "Grupo asignatura marcada índice " + i);
                }
            }
        } catch (Exception e) {
            throw new TestExecutionException("Error al rellenar asignaturas marcadas", e, "ERROR_FORM", PAGE_NAME, page, "rellenarAsignaturasMarcadas");
        }
    }

    public void rellenarAsignaturaRestriccion(String asignatura) {
        try {
            Locator spanAsignatura = page.locator("span", new Page.LocatorOptions().setHasText(asignatura));
            Locator fila = spanAsignatura.locator("xpath=ancestor::tr");
            Locator checkbox = fila.locator("input[type='checkbox']");
            if (!checkbox.isChecked()) {
                checkbox.click();
            }
        } catch (Exception e) {
            throw new PageElementException(PAGE_NAME, "rellenarAsignaturaRestriccion: " + asignatura, e);
        }
    }

    public boolean verificarnorellenarAsignaturaRestriccion(String asignatura) {
        try {
            Locator spanAsignatura = page.locator("span", new Page.LocatorOptions().setHasText(asignatura));
            Locator fila = spanAsignatura.locator("xpath=ancestor::tr");
            Locator checkbox = fila.locator("input[type='checkbox']");
            return !checkbox.isEnabled();
        } catch (Exception e) {
            throw new PageElementException(PAGE_NAME, "verificarnorellenarAsignaturaRestriccion: " + asignatura, e);
        }
    }

    public void rellenarReconocimientos() {
        try {
            for (int i = 0; i < getChecksAsignaturas().size(); i++) {
                try {
                    if (!getChecksReconocimientos().get(i).isDisabled()) {
                        getChecksReconocimientos().get(i).check();
                    }
                } catch (Exception e) {
                    throw new PageElementException(PAGE_NAME, "Checkbox reconocimiento índice " + i, e);
                }
            }
        } catch (Exception e) {
            throw new PageElementException(PAGE_NAME, "rellenarReconocimientos", e);
        }
    }

    public Locator getCalendar() {
        return page.locator("//*[@id=\"btnVerCalendario_modal\"]/i");
    }

    public Locator getOutCalendar() {
        return page.locator("//*[@id=\"calendarioPanelModal\"]/div/div/div[3]/center/button[2]");
    }

    public void aceptacionSolapes(){
        try {
            if (getModalErrorCheck().isVisible()) {
                log.info("Hay solapes en las asignaturas seleccionadas, se procede a aceptar el mensaje de error");
                try {
                    getBotonAceptarErrorCheck().click();
                } catch (Exception e) {
                    throw new PageElementException(PAGE_NAME, "Botón aceptar error solapes", e);
                }
                try {
                    if(getCheckSolapes().isVisible()){
                        getCheckSolapes().check();
                    }else if(getCheckSolapes2().isVisible()){
                        getCheckSolapes2().check();
                    }
                } catch (Exception e) {
                    throw new PageElementException(PAGE_NAME, "Check solapes", e);
                }
                try {
                    getBotonGuardarContinuar().click();
                } catch (Exception e) {
                    throw new PageElementException(PAGE_NAME, "Botón guardar continuar tras solapes", e);
                }
            }
        } catch (Exception e) {
            throw new PageElementException(PAGE_NAME, "aceptacionSolapes", e);
        }
    }

    /**
     * Verifica que la página de datos académicos está correctamente cargada.
     * Lanza PageElementException si falla algún paso.
     */
    public void verificarCargaPagina() {
        try {
            if (!getBarraProgreso().isVisible()) {
                throw new PageElementException(PAGE_NAME, "#barraProgreso no visible");
            }
            assertThat(getBarraProgresoDatosAcademicos()).hasAttribute("class", "active");

        } catch (Exception e) {
            throw new PageElementException(PAGE_NAME, "Verificación de carga de página de datos académicos", e);
        }
    }


}
