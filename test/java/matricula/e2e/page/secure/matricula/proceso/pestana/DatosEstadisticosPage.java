package matricula.e2e.page.secure.matricula.proceso.pestana;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

import com.microsoft.playwright.options.SelectOption;
import com.microsoft.playwright.options.WaitForSelectorState;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import matricula.e2e.page.secure.HomePage;
import matricula.e2e.test.utils.TestProperties;
import matricula.e2e.test.utils.excepciones.TestExecutionException;
import net.datafaker.Faker;
import org.springframework.stereotype.Component;

import java.util.Locale;
import java.util.Random;
/**
 *
 * @author aaperez
 */
@Slf4j
public class DatosEstadisticosPage {
    private static final String PAGE_NAME = "DatosEstadisticosPage";
    @Getter
    private final Page page;
    private Random random = new Random();
    private Faker faker = new Faker(new Locale("es"));




    public DatosEstadisticosPage(DatosPersonalesPage datosPersonalesPage, TestProperties props) {
        this.page = datosPersonalesPage.getPage();
        page.setDefaultTimeout(props.getTimeout());
    }

    public Locator getBarraProgreso() {
        return page.locator("#barraProgreso");
    }

    public Locator getBarraProgresoDatosEstadisticos() {
        return page.locator("#datosEstadisticosBubble");
    }

    public Locator getSelectTrabajoEstudiante() {
        return page.locator("#ocupacion");
    }

    public Locator getSelectFamiliaNumerosa() {
        return page.locator("#fnumerosa");
    }

    public Locator getSelectEstudiosPadre() {
        return page.locator("#nestpadre");
    }

    public Locator getSelectEstudiosMadre() {
        return page.locator("#nestmadre");
    }

    public Locator getSelectTrabajoPadre() {
        return page.locator("#trabpadre");
    }

    public Locator getSelectTrabajoMadre() {
        return page.locator("#trabmadre");
    }


    public Locator getSelectPaisdeEstudio() {
        return page.locator("#epais");
    }

    public Locator getBotonPrimeraVezSistemaUniversitarioSi() {
        return page.locator("#primeraVezSi");
    }

    public Locator getBotonPrimeraVezSistemaUniversitarioNo() {
        return page.locator("#primeraVezNo");
    }


    public Locator getSelectEstudiosAcceso() {
        return page.locator("#eacceso");
    }

    public Locator getSelectModalidadBachillerato() {
        return page.locator("#eestudio");
    }

    public Locator getSelectNaturalezaColegio() {
        return page.locator("#ninstituto");
    }


    public Locator getSelectProvinciaCentroProcedencia() {
        return page.locator("#eprov");
    }

    public Locator getSelectMunicipioCentroProcedencia() {
        return page.locator("#mcentro");
    }

    public Locator getSelectLocalidadCentroProcedencia() {
        return page.locator("#cloc");
    }

    public Locator getSelectNombreCentroProcedencia() {
        return page.locator("#instituto");
    }

    public Locator getSelectCentroExt() {
        return page.locator("#insti_ext");
    }

    public Locator getCheckInstiNo() {
        return page.locator("#instiNoAparece");
    }

    public Locator getLocalidadInstiExt() {
        return page.locator("#locInstiExt");
    }

    public Locator getNombreInstiExt() {
        return page.locator("#nombreInstiExt");
    }

    public Locator getCheckUnivNo() {
        return page.locator("#univNoAparece");
    }

    public Locator getLocalidadUnivNo() {
        return page.locator("#locUniversidad");
    }

    public Locator getNombreUnivNo() {
        return page.locator("#nombreUniversidad");
    }

    public Locator getCheckIngresoSUE() {
        return page.locator("#chkAnnoAcceso");
    }

    public Locator getSelectIngresoSUE() {
        return page.locator("#uanioAcceso");
    }

    public Locator getCheckAnioEstudio() {
        return page.locator("#chkUltimoAnnoEstudio");
    }

    public Locator getSelectAnioEstudio() {
        return page.locator("#uanio");
    }

    public Locator getSelectUniversidadProcedencia() {
        return page.locator("#univAcceso");
    }

    public Locator getSelectFormaAdmision() {
        return page.locator("#forma_admision");
    }

    public Locator getBotonGuardarContinuar() {
        return page.locator("#btnGuardarEst");
    }


    public void rellenarDatosEstadisticos() {
        try {
            getSelectTrabajoEstudiante().selectOption(new SelectOption().setIndex(
                    random.nextInt(getSelectTrabajoEstudiante().locator("option").count() - 1) + 1));
        } catch (Exception e) {
            throw new TestExecutionException("Error al seleccionar trabajo estudiante", e, "ERROR_SELECCION", PAGE_NAME, page, "rellenarDatosEstadisticos");
        }
        try {
            getSelectFamiliaNumerosa().selectOption(new SelectOption().setIndex(
                    random.nextInt(getSelectFamiliaNumerosa().locator("option").count() - 1) + 1));
        } catch (Exception e) {
            throw new TestExecutionException("Error al seleccionar familia numerosa", e, "ERROR_SELECCION", PAGE_NAME, page, "rellenarDatosEstadisticos");
        }
        try {
            getSelectEstudiosPadre().selectOption(new SelectOption().setIndex(
                    random.nextInt(getSelectEstudiosPadre().locator("option").count() - 1) + 1));
        } catch (Exception e) {
            throw new TestExecutionException("Error al seleccionar estudios padre", e, "ERROR_SELECCION", PAGE_NAME, page, "rellenarDatosEstadisticos");
        }
        try {
            getSelectEstudiosMadre().selectOption(new SelectOption().setIndex(
                    random.nextInt(getSelectEstudiosMadre().locator("option").count() - 1) + 1));
        } catch (Exception e) {
            throw new TestExecutionException("Error al seleccionar estudios madre", e, "ERROR_SELECCION", PAGE_NAME, page, "rellenarDatosEstadisticos");
        }
        try {
            getSelectTrabajoPadre().selectOption(new SelectOption().setIndex(
                    random.nextInt(getSelectTrabajoPadre().locator("option").count() - 1) + 1));
        } catch (Exception e) {
            throw new TestExecutionException("Error al seleccionar trabajo padre", e, "ERROR_SELECCION", PAGE_NAME, page, "rellenarDatosEstadisticos");
        }
        try {
            getSelectTrabajoMadre().selectOption(new SelectOption().setIndex(
                    random.nextInt(getSelectTrabajoMadre().locator("option").count() - 1) + 1));
        } catch (Exception e) {
            throw new TestExecutionException("Error al seleccionar trabajo madre", e, "ERROR_SELECCION", PAGE_NAME, page, "rellenarDatosEstadisticos");
        }
        try {
            getSelectPaisdeEstudio().selectOption(new SelectOption().setIndex(
                    random.nextInt(getSelectPaisdeEstudio().locator("option").count() - 1) + 1));
        } catch (Exception e) {
            throw new TestExecutionException("Error al seleccionar país de estudio", e, "ERROR_SELECCION", PAGE_NAME, page, "rellenarDatosEstadisticos");
        }
        String textoSeleccionado;
        try {
            textoSeleccionado = getSelectPaisdeEstudio()
                .locator("option:checked")
                .textContent();
        } catch (Exception e) {
            throw new TestExecutionException("Error al obtener país seleccionado", e, "ERROR_LECTURA", PAGE_NAME, page, "rellenarDatosEstadisticos");
        }



            if(!getBotonPrimeraVezSistemaUniversitarioSi().isChecked() ) {
                try {
                    getBotonPrimeraVezSistemaUniversitarioSi().click();
                } catch (Exception e) {
                    throw new TestExecutionException("Error al hacer click en primera vez sistema universitario", e, "ERROR_CLICK", PAGE_NAME, page, "rellenarDatosEstadisticos");
                }
            }


                try {
                    page.waitForTimeout(1000);
                    int opciones = getSelectEstudiosAcceso().locator("option").count() - 1;
                    if(opciones <= 1){
                        getSelectEstudiosAcceso().selectOption(new SelectOption().setIndex(1));
                    }else {
                        getSelectEstudiosAcceso().selectOption(new SelectOption().setIndex(
                                random.nextInt(getSelectEstudiosAcceso().locator("option").count() - 1)
                                        + 1));
                    }

                } catch (Exception e) {
                    throw new TestExecutionException("Error al seleccionar estudios acceso", e, "ERROR_SELECCION", PAGE_NAME, page, "rellenarDatosEstadisticos");
                }

                try {
                    if (!getSelectEstudiosAcceso().inputValue().equals("05") && !getSelectEstudiosAcceso().inputValue().equals("06")) {
                        page.waitForTimeout(2500);

                        page.waitForSelector("//*[@id=\"eestudio\"]", new Page.WaitForSelectorOptions().setState(WaitForSelectorState.VISIBLE).setTimeout(60000));

                        if(getSelectModalidadBachillerato().locator("option").count() == 1){
                            getSelectModalidadBachillerato().selectOption(new SelectOption().setIndex(1));
                        }else {
                            getSelectModalidadBachillerato().selectOption(new SelectOption().setIndex(
                                    random.nextInt(getSelectModalidadBachillerato().locator("option").count() - 1)
                                            + 1));
                        }
                    }
                } catch (Exception e) {
                    throw new TestExecutionException("Error al seleccionar modalidad bachillerato", e, "ERROR_SELECCION", PAGE_NAME, page, "rellenarDatosEstadisticos");
                }

                try {
                    getSelectNaturalezaColegio().selectOption(new SelectOption().setIndex(
                            random.nextInt(getSelectNaturalezaColegio().locator("option").count() - 1) + 1));
                } catch (Exception e) {
                    throw new TestExecutionException("Error al seleccionar naturaleza colegio", e, "ERROR_SELECCION", PAGE_NAME, page, "rellenarDatosEstadisticos");
                }
                try {
                    page.waitForSelector("//*[@id=\"loading\"]", new Page.WaitForSelectorOptions().setState(WaitForSelectorState.HIDDEN).setTimeout(60000));
                } catch (Exception e) {
                    throw new TestExecutionException("Error al esperar desaparición del loading", e, "ERROR_ESPERA", PAGE_NAME, page, "rellenarDatosEstadisticos");
                }

        try {
            if (!textoSeleccionado.equals("España")) {
                page.waitForTimeout(2500);
                try {
                    getCheckInstiNo().check();
                } catch (Exception e) {
                    throw new TestExecutionException("Error al marcar instituto no aparece", e, "ERROR_CHECK", PAGE_NAME, page, "rellenarDatosEstadisticos");
                }
                try {
                    // Usando DataFaker para generar ciudad aleatoria
                    getLocalidadInstiExt().fill(faker.address().city());
                    getLocalidadInstiExt().press("Enter");
                } catch (Exception e) {
                    throw new TestExecutionException("Error al rellenar localidad instituto ext", e, "ERROR_LECTURA", PAGE_NAME, page, "rellenarDatosEstadisticos");
                }
                try {
                    // Usando DataFaker para generar nombre de universidad aleatorio
                    getNombreInstiExt().fill(faker.university().name());
                    getNombreInstiExt().press("Enter");
                } catch (Exception e) {
                    throw new TestExecutionException("Error al rellenar nombre instituto ext", e, "ERROR_LECTURA", PAGE_NAME, page, "rellenarDatosEstadisticos");
                }
            }else{
                page.waitForTimeout(2500);
                try {
                    if(getSelectProvinciaCentroProcedencia().locator("option").count() == 1){
                        getSelectProvinciaCentroProcedencia().selectOption(new SelectOption().setIndex(1));
                    }else {
                        getSelectProvinciaCentroProcedencia()
                                .selectOption(new SelectOption().setIndex(random.nextInt(
                                        getSelectProvinciaCentroProcedencia().locator("option").count())
                                        + 1));
                    }
                } catch (Exception e) {
                    throw new TestExecutionException("Error al seleccionar provincia centro", e, "ERROR_SELECCION", PAGE_NAME, page, "rellenarDatosEstadisticos");
                }
                try {
                    if(getSelectMunicipioCentroProcedencia().locator("option").count() == 1){
                        getSelectMunicipioCentroProcedencia().selectOption(new SelectOption().setIndex(1));
                    }else {
                        getSelectMunicipioCentroProcedencia()
                                .selectOption(new SelectOption().setIndex(random.nextInt(
                                        getSelectMunicipioCentroProcedencia().locator("option").count() - 1)
                                        + 1));
                    }
                } catch (Exception e) {
                    throw new TestExecutionException("Error al seleccionar municipio centro", e, "ERROR_SELECCION", PAGE_NAME, page, "rellenarDatosEstadisticos");
                }
                try {
                    getSelectLocalidadCentroProcedencia()
                            .selectOption(new SelectOption().setIndex(random.nextInt(
                                    getSelectLocalidadCentroProcedencia().locator("option").count() - 1)
                                    + 1));
                } catch (Exception e) {
                    throw new TestExecutionException("Error al seleccionar localidad centro", e, "ERROR_SELECCION", PAGE_NAME, page, "rellenarDatosEstadisticos");
                }
                try {
                    if(getSelectNombreCentroProcedencia().locator("option").count() == 1){
                        getSelectNombreCentroProcedencia().selectOption(new SelectOption().setIndex(0));
                    }else{
                        getSelectNombreCentroProcedencia().selectOption(new SelectOption().setIndex(
                                random.nextInt(getSelectNombreCentroProcedencia().locator("option").count() - 1)
                                        + 1));
                    }
                } catch (Exception e) {
                    throw new TestExecutionException("Error al seleccionar nombre centro", e, "ERROR_SELECCION", PAGE_NAME, page, "rellenarDatosEstadisticos");
                }
            }
        } catch (Exception e) {
            throw new TestExecutionException("Error en la validación del centro de procedencia", e, "ERROR_VALIDACION", PAGE_NAME, page, "rellenarDatosEstadisticos");
        }
        try {
            page.waitForSelector("//*[@id=\"loading\"]", new Page.WaitForSelectorOptions().setState(WaitForSelectorState.HIDDEN).setTimeout(60000));
        } catch (Exception e) {
            throw new TestExecutionException("Error al esperar desaparición del loading", e, "ERROR_ESPERA", PAGE_NAME, page, "rellenarDatosEstadisticos");
        }
        try {
            if(!getSelectUniversidadProcedencia().isHidden()){
                try {
                    getCheckUnivNo().check();
                } catch (Exception e) {
                    throw new TestExecutionException("Error al marcar universidad no aparece", e, "ERROR_CHECK", PAGE_NAME, page, "rellenarDatosEstadisticos");
                }
                try {
                    page.waitForSelector("//*[@id=\"loading\"]", new Page.WaitForSelectorOptions().setState(WaitForSelectorState.HIDDEN).setTimeout(60000));
                } catch (Exception e) {
                    throw new TestExecutionException("Error al esperar desaparición del loading", e, "ERROR_ESPERA", PAGE_NAME, page, "rellenarDatosEstadisticos");
                }
                try {
                    // Usando DataFaker para generar ciudad aleatoria
                    getLocalidadUnivNo().fill(faker.address().city());
                    getLocalidadInstiExt().press("Enter");
                } catch (Exception e) {
                    throw new TestExecutionException("Error al rellenar localidad universidad", e, "ERROR_LECTURA", PAGE_NAME, page, "rellenarDatosEstadisticos");
                }
                try {
                    // Usando DataFaker para generar nombre de universidad aleatorio
                    getNombreUnivNo().fill(faker.university().name());
                    getLocalidadInstiExt().press("Enter");
                } catch (Exception e) {
                    throw new TestExecutionException("Error al rellenar nombre universidad", e, "ERROR_LECTURA", PAGE_NAME, page, "rellenarDatosEstadisticos");
                }
            }
        } catch (Exception e) {
            throw new TestExecutionException("Error en la validación de la universidad de procedencia", e, "ERROR_VALIDACION", PAGE_NAME, page, "rellenarDatosEstadisticos");
        }
        try {
            if(getCheckIngresoSUE().isVisible()) {
                if (getCheckIngresoSUE().isChecked()) {
                    getSelectIngresoSUE().selectOption(new SelectOption().setIndex(
                            random.nextInt(getSelectIngresoSUE().locator("option").count() - 1)
                                    + 1));
                }
            }
        } catch (Exception e) {
            throw new TestExecutionException("Error al seleccionar ingreso SUE", e, "ERROR_SELECCION", PAGE_NAME, page, "rellenarDatosEstadisticos");
        }
        try {
            if(getCheckAnioEstudio().isChecked()){
                getSelectAnioEstudio().selectOption(new SelectOption().setIndex(
                        random.nextInt(getSelectAnioEstudio().locator("option").count() - 1)
                                + 1));
            }
        } catch (Exception e) {
            throw new TestExecutionException("Error al seleccionar año de estudio", e, "ERROR_SELECCION", PAGE_NAME, page, "rellenarDatosEstadisticos");
        }
        try {
            getSelectFormaAdmision().selectOption(new SelectOption().setIndex(
                    random.nextInt(getSelectFormaAdmision().locator("option").count() - 1) + 1));
        } catch (Exception e) {
            throw new TestExecutionException("Error al seleccionar forma de admisión", e, "ERROR_SELECCION", PAGE_NAME, page, "rellenarDatosEstadisticos");
        }
    }

}
