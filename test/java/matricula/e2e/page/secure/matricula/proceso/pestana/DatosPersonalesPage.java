package matricula.e2e.page.secure.matricula.proceso.pestana;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

import com.microsoft.playwright.options.SelectOption;
import com.microsoft.playwright.options.WaitForSelectorState;
import lombok.Getter;
import matricula.e2e.page.secure.HomePage;
import matricula.e2e.test.utils.TestProperties;
import matricula.e2e.test.utils.excepciones.TestExecutionException;
import net.datafaker.Faker;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Random;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

/**
 *
 * @author aaperez
 */

public class DatosPersonalesPage {
    private static final String PAGE_NAME = "DatosPersonalesPage";
    @Getter
    private final Page page;
    private Random random = new Random();
    private Faker faker = new Faker(new Locale("es"));



    public DatosPersonalesPage(HomePage homePage, TestProperties props) {
        this.page = homePage.getPage();
        page.setDefaultTimeout(props.getTimeout());
    }

    public Locator getBarraProgreso() {
        return page.locator("#barraProgreso");
    }

    public Locator getBarraProgresoDatosPersonales() {
        return page.locator("#datosPersonalesBubble");
    }

    public Locator getBotonGuardarContinuar() {
        return page.locator("//*[@id=\"btnGuardarPer\"]");
    }

    //primer tab

    public Locator getFechaNacimiento() {
        return page.locator("#fnacimiento");
    }

    public Locator getPaisNacimiento() {
        return page.locator("#cpaisnac");
    }

    public Locator getProvinciaNacimiento() {
        return page.locator("#cpronac");
    }

    public Locator getProvinciaNacimientoExt() {
        return page.locator("#localinac");
    }

    public Locator getInputLocalidadNacimiento() {
        return page.locator("#localinac");
    }

    public Locator getMunicipioNacimiento() {
        return page.locator("#cmunnac");
    }

    public Locator getLocalidadNacimiento() {
        return page.locator("#clocnac");
    }

    public Locator getNSS() { return page.locator("#numSS"); }

    public Locator getCompPrivado() { return page.locator("#segPrivadoComp"); }

    public Locator getNSPrivado() { return page.locator("#segPrivadoNum"); }


    //Elementos del tercer tab

    public Locator getInputTelefono() {
        return page.locator("#telefo");
    }

    public Locator getInputMovil() {
        return page.locator("#movil");
    }

    public Locator getInputCorreo() {
        return page.locator("#email");
    }

    //Elementos del segundo tab

    public Locator getPaisDireccion() {
        return page.locator("#fcpais");
    }

    public Locator getSelectDireccionMunicipio() {
        return page.locator("#fcmun");
    }

    public Locator getSelectDireccionLocalidad() {
        return page.locator("#flocaliesp");
    }

    public Locator getSelectDireccionLocalidadExt() {
        return page.locator("#flocali");
    }

    public Locator getInputDireccion() {
        return page.locator("#fdirecc");
    }

    public Locator getInputDireccionCodigoPostal() {
        return page.locator("#fcopost");
    }

    public Locator getInputDireccionTelefono() {
        return page.locator("#ftelefo");
    }

    public Locator getCheckDatosCurso() {
        return page.locator("#checkCopia");
    }

    public Locator getSelectDireccionCursoProvincia() {
        return page.locator("#cpro");
    }

    public Locator getSelectDireccionCursoMunicipio() {
        return page.locator("#cmun");
    }

    public Locator getSelectDireccionCursoLocalidad() {
        return page.locator("#localiesp");
    }

    public Locator getSelectDireccionCursoLocalidadExt() {
        return page.locator("#locali");
    }

    public Locator getInputDireccionCurso() {
        return page.locator("#direcc");
    }

    public Locator getInputDireccionCursoCodigoPostal() {
        return page.locator("#copost");
    }

    //Elementos del cuarto tab

    public Locator getAmbitoProcendencia() {
        return page.locator("#ambitoprocedencia");
    }

    public Locator getAmbitoDestino() {
        return page.locator("#ambitodestino");
    }

    public Locator getTipoProgMovilidad() {
        return page.locator("#tipoprogmovilidad");
    }

    public Locator getFechaInicioEstancia() {
        return page.locator("#finicioest");
    }

    public Locator getFechaFinEstancia() {
        return page.locator("#ffinest");
    }

    public Locator getCalendario() {
        return page.locator(".datepicker-dropdown");
    }

    public Locator getCalendarioDia() {
        return getCalendario().locator("td >> text=15");
    }



    //Piia
    public Locator getLabelTarjetaSanitaria() {
        return page.locator("//*[@id=\"divDomFam\"]/label[1]");
    }


    public Locator getEHIC() {
        return page.locator("#ehic");
    }

    //Métodos para rellenar las secciones del formulario de Datos Personales

    public void rellenarPrimeraSeccion() {
        try {
            // Generar fecha de nacimiento aleatoria entre 17 y 25 años atrás (estudiante universitario típico)
            LocalDate fechaNacimiento = LocalDate.now().minusYears(18 + random.nextInt(7));
            getFechaNacimiento().fill(fechaNacimiento.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        } catch (Exception e) {
            throw new TestExecutionException("Error al rellenar fecha de nacimiento", e, "ERROR_FILL", PAGE_NAME, page, "rellenarPrimeraSeccion");
        }
        try {
            getPaisNacimiento().selectOption(new SelectOption().setIndex(
                    random.nextInt(getLocalidadNacimiento().locator("option").count() + 1) + 1));
        } catch (Exception e) {
            throw new TestExecutionException("Error al seleccionar país de nacimiento", e, "ERROR_SELECCION", PAGE_NAME, page, "rellenarPrimeraSeccion");
        }
        try {
            if(getPaisNacimiento().inputValue().equals("ESP")){
                try {
                    getProvinciaNacimiento().selectOption(new SelectOption().setIndex(
                            random.nextInt(getProvinciaNacimiento().locator("option").count() + 1) + 1));
                } catch (Exception e) {
                    throw new TestExecutionException("Error al seleccionar provincia de nacimiento", e, "ERROR_SELECCION", PAGE_NAME, page, "rellenarPrimeraSeccion");
                }
                try {
                    page.waitForSelector("//*[@id=\"loading\"]", new Page.WaitForSelectorOptions().setState(WaitForSelectorState.HIDDEN).setTimeout(60000));
                } catch (Exception e) {
                    throw new TestExecutionException("Error al esperar desaparición del loading", e, "ERROR_ESPERA", PAGE_NAME, page, "rellenarPrimeraSeccion");
                }
                try {
                    getMunicipioNacimiento().selectOption(new SelectOption().setIndex(
                            random.nextInt(getMunicipioNacimiento().locator("option").count() + 1) + 1));
                } catch (Exception e) {
                    throw new TestExecutionException("Error al seleccionar municipio de nacimiento", e, "ERROR_SELECCION", PAGE_NAME, page, "rellenarPrimeraSeccion");
                }
                try {
                    page.waitForSelector("//*[@id=\"loading\"]", new Page.WaitForSelectorOptions().setState(WaitForSelectorState.HIDDEN).setTimeout(60000));
                } catch (Exception e) {
                    throw new TestExecutionException("Error al esperar desaparición del loading", e, "ERROR_ESPERA", PAGE_NAME, page, "rellenarPrimeraSeccion");
                }
                try {
                    getLocalidadNacimiento().selectOption(new SelectOption().setIndex(
                            random.nextInt(getLocalidadNacimiento().locator("option").count() + 1) + 1));
                } catch (Exception e) {
                    throw new TestExecutionException("Error al seleccionar localidad de nacimiento", e, "ERROR_SELECCION", PAGE_NAME, page, "rellenarPrimeraSeccion");
                }
            }else{
                try {
                    page.waitForSelector("//*[@id=\"loading\"]", new Page.WaitForSelectorOptions().setState(WaitForSelectorState.HIDDEN).setTimeout(60000));
                } catch (Exception e) {
                    throw new TestExecutionException("Error al esperar desaparición del loading", e, "ERROR_ESPERA", PAGE_NAME, page, "rellenarPrimeraSeccion");
                }
                try {
                    getProvinciaNacimientoExt().fill(faker.address().city());
                } catch (Exception e) {
                    throw new TestExecutionException("Error al rellenar provincia/localidad de nacimiento extranjera", e, "ERROR_FILL", PAGE_NAME, page, "rellenarPrimeraSeccion");
                }
            }
        } catch (Exception e) {
            throw new TestExecutionException("Error en validación de país de nacimiento", e, "ERROR_VALIDACION", PAGE_NAME, page, "rellenarPrimeraSeccion");
        }
        try {
            // Generamos un número de 12 dígitos para la tarjeta sanitaria o NSS
            String numeroTarjeta = faker.numerify("############");

            if (getLabelTarjetaSanitaria().textContent().equals("EHIC (Tarjeta Sanitaria Europea)")) {
                getEHIC().fill(numeroTarjeta);
            } else if(getLabelTarjetaSanitaria().textContent().equals("Número de Seguridad Social")){
                getNSS().fill(numeroTarjeta);
            }
        } catch (Exception e) {
            throw new TestExecutionException("Error al rellenar datos de tarjeta sanitaria/NSS", e, "ERROR_FILL", PAGE_NAME, page, "rellenarPrimeraSeccion");
        }
        try {
            getCompPrivado().fill(faker.company().name());
        } catch (Exception e) {
            throw new TestExecutionException("Error al rellenar compañía privada", e, "ERROR_FILL", PAGE_NAME, page, "rellenarPrimeraSeccion");
        }
        try {
            getNSPrivado().fill(faker.numerify("#########"));
        } catch (Exception e) {
            throw new TestExecutionException("Error al rellenar número de seguro privado", e, "ERROR_FILL", PAGE_NAME, page, "rellenarPrimeraSeccion");
        }
        try {
            getFechaNacimiento().click();
        } catch (Exception e) {
            throw new TestExecutionException("Error al hacer click en fecha de nacimiento", e, "ERROR_CLICK", PAGE_NAME, page, "rellenarPrimeraSeccion");
        }
    }

    public void rellenarSegundaSeccion() {
        try {
            page.evaluate("document.querySelector('#linkTab2').click()");
        } catch (Exception e) {
            throw new TestExecutionException("Error al ir a segunda sección", e, "ERROR_CLICK", PAGE_NAME, page, "rellenarSegundaSeccion");
        }
        try {
            getPaisDireccion().selectOption(new SelectOption().setIndex(
                    random.nextInt(getLocalidadNacimiento().locator("option").count() + 1) + 1));
        } catch (Exception e) {
            throw new TestExecutionException("Error al seleccionar país de dirección", e, "ERROR_SELECCION", PAGE_NAME, page, "rellenarSegundaSeccion");
        }
        try {
            if(getPaisDireccion().inputValue().equals("ESP")){
                try {
                    getProvinciaNacimiento().selectOption(new SelectOption().setIndex(
                            random.nextInt(getProvinciaNacimiento().locator("option").count() + 1) + 1));
                } catch (Exception e) {
                    throw new TestExecutionException("Error al seleccionar provincia de dirección", e, "ERROR_SELECCION", PAGE_NAME, page, "rellenarSegundaSeccion");
                }
                try {
                    page.waitForSelector("//*[@id=\"loading\"]", new Page.WaitForSelectorOptions().setState(WaitForSelectorState.HIDDEN).setTimeout(60000));
                } catch (Exception e) {
                    throw new TestExecutionException("Error al esperar desaparición del loading", e, "ERROR_ESPERA", PAGE_NAME, page, "rellenarSegundaSeccion");
                }
                try {
                    getSelectDireccionMunicipio().selectOption(new SelectOption().setIndex(
                            random.nextInt(getMunicipioNacimiento().locator("option").count() + 1) + 1));
                } catch (Exception e) {
                    throw new TestExecutionException("Error al seleccionar municipio dirección", e, "ERROR_SELECCION", PAGE_NAME, page, "rellenarSegundaSeccion");
                }
                try {
                    page.waitForSelector("//*[@id=\"loading\"]", new Page.WaitForSelectorOptions().setState(WaitForSelectorState.HIDDEN).setTimeout(60000));
                } catch (Exception e) {
                    throw new TestExecutionException("Error al esperar desaparición del loading", e, "ERROR_ESPERA", PAGE_NAME, page, "rellenarSegundaSeccion");
                }
                try {
                    getSelectDireccionLocalidad().selectOption(new SelectOption().setIndex(
                            random.nextInt(getLocalidadNacimiento().locator("option").count() + 1) + 1));
                } catch (Exception e) {
                    throw new TestExecutionException("Error al seleccionar localidad dirección", e, "ERROR_SELECCION", PAGE_NAME, page, "rellenarSegundaSeccion");
                }
                try {
                    // Generar código postal español con DataFaker (formato: 5 dígitos)
                    getInputDireccionCodigoPostal().fill(faker.address().zipCode().replaceAll("[^0-9]", "").substring(0, 5));
                } catch (Exception e) {
                    throw new TestExecutionException("Error al rellenar código postal dirección", e, "ERROR_FILL", PAGE_NAME, page, "rellenarSegundaSeccion");
                }
            }else{
                try {
                    // Generar ciudad aleatoria con DataFaker
                    getSelectDireccionLocalidadExt().fill(faker.address().city());
                } catch (Exception e) {
                    throw new TestExecutionException("Error al rellenar localidad dirección ext", e, "ERROR_FILL", PAGE_NAME, page, "rellenarSegundaSeccion");
                }
            }
        } catch (Exception e) {
            throw new TestExecutionException("Error en validación país de dirección", e, "ERROR_VALIDACION", PAGE_NAME, page, "rellenarSegundaSeccion");
        }
        try {
            // Generar dirección aleatoria con DataFaker
            getInputDireccion().fill(faker.address().streetAddress());
        } catch (Exception e) {
            throw new TestExecutionException("Error al rellenar dirección", e, "ERROR_FILL", PAGE_NAME, page, "rellenarSegundaSeccion");
        }
        try {
            // Generar teléfono español con DataFaker
            getInputDireccionTelefono().fill("+34" + faker.phoneNumber().cellPhone().replaceAll("[^0-9]", "").substring(0, 9));
        } catch (Exception e) {
            throw new TestExecutionException("Error al rellenar teléfono dirección", e, "ERROR_FILL", PAGE_NAME, page, "rellenarSegundaSeccion");
        }
        try {
            if (!getCheckDatosCurso().isChecked()) {
                try {
                    getSelectDireccionCursoProvincia().selectOption(new SelectOption().setIndex(
                            random.nextInt(getSelectDireccionCursoProvincia().locator("option").count() + 1) + 1));
                } catch (Exception e) {
                    throw new TestExecutionException("Error al seleccionar provincia curso", e, "ERROR_SELECCION", PAGE_NAME, page, "rellenarSegundaSeccion");
                }
                try {
                    page.waitForSelector("//*[@id=\"loading\"]", new Page.WaitForSelectorOptions().setState(WaitForSelectorState.HIDDEN).setTimeout(60000));
                } catch (Exception e) {
                    throw new TestExecutionException("Error al esperar desaparición del loading", e, "ERROR_ESPERA", PAGE_NAME, page, "rellenarSegundaSeccion");
                }
                try {
                    getSelectDireccionCursoMunicipio().selectOption(new SelectOption().setIndex(
                            random.nextInt(getSelectDireccionCursoMunicipio().locator("option").count() + 1) + 1));
                } catch (Exception e) {
                    throw new TestExecutionException("Error al seleccionar municipio curso", e, "ERROR_SELECCION", PAGE_NAME, page, "rellenarSegundaSeccion");
                }
                try {
                    page.waitForSelector("//*[@id=\"loading\"]", new Page.WaitForSelectorOptions().setState(WaitForSelectorState.HIDDEN).setTimeout(60000));
                } catch (Exception e) {
                    throw new TestExecutionException("Error al esperar desaparición del loading", e, "ERROR_ESPERA", PAGE_NAME, page, "rellenarSegundaSeccion");
                }
                try {
                    if (getSelectDireccionCursoLocalidad().locator("option").count() == 1) {
                        getSelectDireccionCursoLocalidad().selectOption(new SelectOption().setIndex(1));
                    } else {
                        getSelectDireccionCursoLocalidad().selectOption(new SelectOption().setIndex(
                                random.nextInt(getSelectDireccionCursoLocalidad().locator("option").count() - 1)
                                        + 1));
                    }
                } catch (Exception e) {
                    throw new TestExecutionException("Error al seleccionar localidad curso", e, "ERROR_SELECCION", PAGE_NAME, page, "rellenarSegundaSeccion");
                }
                try {
                    // Generar dirección aleatoria con DataFaker
                    getInputDireccionCurso().fill(faker.address().streetAddress());
                } catch (Exception e) {
                    throw new TestExecutionException("Error al rellenar dirección curso", e, "ERROR_FILL", PAGE_NAME, page, "rellenarSegundaSeccion");
                }
                try {
                    // Generar código postal español con DataFaker
                    getInputDireccionCursoCodigoPostal().fill(faker.address().zipCode().replaceAll("[^0-9]", "").substring(0, 5));
                } catch (Exception e) {
                    throw new TestExecutionException("Error al rellenar código postal curso", e, "ERROR_FILL", PAGE_NAME, page, "rellenarSegundaSeccion");
                }
            }
        } catch (Exception e) {
            throw new TestExecutionException("Error en validación datos curso", e, "ERROR_VALIDACION", PAGE_NAME, page, "rellenarSegundaSeccion");
        }
    }

    public void rellenarSegundaSeccionPiia() {
        try {
            page.evaluate("document.querySelector('#linkTab2').click()");
        } catch (Exception e) {
            throw new TestExecutionException("Error al ir a segunda sección Piia", e, "ERROR_CLICK", PAGE_NAME, page, "rellenarSegundaSeccionPiia");
        }
        try {
            getPaisDireccion().selectOption(new SelectOption().setIndex(
                    random.nextInt(getLocalidadNacimiento().locator("option").count() + 1) + 1));
        } catch (Exception e) {
            throw new TestExecutionException("Error al seleccionar país de dirección Piia", e, "ERROR_SELECCION", PAGE_NAME, page, "rellenarSegundaSeccionPiia");
        }
        try {
            if(getPaisDireccion().inputValue().equals("ESP")){
                try {
                    getProvinciaNacimiento().selectOption(new SelectOption().setIndex(
                            random.nextInt(getProvinciaNacimiento().locator("option").count() + 1) + 1));
                } catch (Exception e) {
                    throw new TestExecutionException("Error al seleccionar provincia dirección Piia", e, "ERROR_SELECCION", PAGE_NAME, page, "rellenarSegundaSeccionPiia");
                }
                try {
                    page.waitForSelector("//*[@id=\"loading\"]", new Page.WaitForSelectorOptions().setState(WaitForSelectorState.HIDDEN).setTimeout(60000));
                } catch (Exception e) {
                    throw new TestExecutionException("Error al esperar desaparición del loading", e, "ERROR_ESPERA", PAGE_NAME, page, "rellenarSegundaSeccionPiia");
                }
                try {
                    getSelectDireccionMunicipio().selectOption(new SelectOption().setIndex(
                            random.nextInt(getMunicipioNacimiento().locator("option").count() + 1) + 1));
                } catch (Exception e) {
                    throw new TestExecutionException("Error al seleccionar municipio dirección Piia", e, "ERROR_SELECCION", PAGE_NAME, page, "rellenarSegundaSeccionPiia");
                }
                try {
                    page.waitForSelector("//*[@id=\"loading\"]", new Page.WaitForSelectorOptions().setState(WaitForSelectorState.HIDDEN).setTimeout(60000));
                } catch (Exception e) {
                    throw new TestExecutionException("Error al esperar desaparición del loading", e, "ERROR_ESPERA", PAGE_NAME, page, "rellenarSegundaSeccionPiia");
                }
                try {
                    getSelectDireccionLocalidad().selectOption(new SelectOption().setIndex(
                            random.nextInt(getLocalidadNacimiento().locator("option").count() + 1) + 1));
                } catch (Exception e) {
                    throw new TestExecutionException("Error al seleccionar localidad dirección Piia", e, "ERROR_SELECCION", PAGE_NAME, page, "rellenarSegundaSeccionPiia");
                }
                try {
                    getInputDireccionCodigoPostal().fill("41092");
                } catch (Exception e) {
                    throw new TestExecutionException("Error al rellenar código postal dirección Piia", e, "ERROR_FILL", PAGE_NAME, page, "rellenarSegundaSeccionPiia");
                }
                try {
                    getInputDireccion().fill("Calle Falsa, 123");
                } catch (Exception e) {
                    throw new TestExecutionException("Error al rellenar dirección Piia", e, "ERROR_FILL", PAGE_NAME, page, "rellenarSegundaSeccionPiia");
                }
                try {
                    getInputDireccionTelefono().fill("+34666666666");
                } catch (Exception e) {
                    throw new TestExecutionException("Error al rellenar teléfono dirección Piia", e, "ERROR_FILL", PAGE_NAME, page, "rellenarSegundaSeccionPiia");
                }
                try {
                    if(getSelectDireccionCursoLocalidad().isEnabled()){
                        try {
                            page.waitForSelector("//*[@id=\"loading\"]", new Page.WaitForSelectorOptions().setState(WaitForSelectorState.HIDDEN).setTimeout(60000));
                        } catch (Exception e) {
                            throw new TestExecutionException("Error al esperar desaparición del loading", e, "ERROR_ESPERA", PAGE_NAME, page, "rellenarSegundaSeccionPiia");
                        }
                        try {
                            getSelectDireccionCursoProvincia().selectOption(new SelectOption().setIndex(
                                    random.nextInt(getSelectDireccionCursoProvincia().locator("option").count() + 1) + 1));
                        } catch (Exception e) {
                            throw new TestExecutionException("Error al seleccionar provincia curso Piia", e, "ERROR_SELECCION", PAGE_NAME, page, "rellenarSegundaSeccionPiia");
                        }
                        try {
                            page.waitForSelector("//*[@id=\"loading\"]", new Page.WaitForSelectorOptions().setState(WaitForSelectorState.HIDDEN).setTimeout(60000));
                        } catch (Exception e) {
                            throw new TestExecutionException("Error al esperar desaparición del loading", e, "ERROR_ESPERA", PAGE_NAME, page, "rellenarSegundaSeccionPiia");
                        }
                        try {
                            getSelectDireccionCursoMunicipio().selectOption(new SelectOption().setIndex(
                                    random.nextInt(getSelectDireccionCursoMunicipio().locator("option").count() + 1) + 1));
                        } catch (Exception e) {
                            throw new TestExecutionException("Error al seleccionar municipio curso Piia", e, "ERROR_SELECCION", PAGE_NAME, page, "rellenarSegundaSeccionPiia");
                        }
                        try {
                            page.waitForSelector("//*[@id=\"loading\"]", new Page.WaitForSelectorOptions().setState(WaitForSelectorState.HIDDEN).setTimeout(60000));
                        } catch (Exception e) {
                            throw new TestExecutionException("Error al esperar desaparición del loading", e, "ERROR_ESPERA", PAGE_NAME, page, "rellenarSegundaSeccionPiia");
                        }
                        try {
                            if (getSelectDireccionCursoLocalidad().locator("option").count() == 1) {
                                getSelectDireccionCursoLocalidad().selectOption(new SelectOption().setIndex(1));
                            } else {
                                getSelectDireccionCursoLocalidad().selectOption(new SelectOption().setIndex(
                                        random.nextInt(getSelectDireccionCursoLocalidad().locator("option").count() - 1)
                                                + 1));
                            }
                        } catch (Exception e) {
                            throw new TestExecutionException("Error al seleccionar localidad curso Piia", e, "ERROR_SELECCION", PAGE_NAME, page, "rellenarSegundaSeccionPiia");
                        }
                        try {
                            getInputDireccionCurso().fill(faker.address().streetAddress());
                        } catch (Exception e) {
                            throw new TestExecutionException("Error al rellenar dirección curso Piia", e, "ERROR_FILL", PAGE_NAME, page, "rellenarSegundaSeccionPiia");
                        }
                        try {
                            getInputDireccionCursoCodigoPostal().fill(faker.address().zipCode().replaceAll("[^0-9]", "").substring(0, 5));
                        } catch (Exception e) {
                            throw new TestExecutionException("Error al rellenar código postal curso Piia", e, "ERROR_FILL", PAGE_NAME, page, "rellenarSegundaSeccionPiia");
                        }
                    }
                } catch (Exception e) {
                    throw new TestExecutionException("Error en validación datos curso Piia", e, "ERROR_VALIDACION", PAGE_NAME, page, "rellenarSegundaSeccionPiia");
                }
            }else{
                try {
                    getSelectDireccionLocalidadExt().fill(faker.address().city());
                } catch (Exception e) {
                    throw new TestExecutionException("Error al rellenar localidad dirección ext Piia", e, "ERROR_FILL", PAGE_NAME, page, "rellenarSegundaSeccionPiia");
                }
                try {
                    getInputDireccion().fill(faker.address().streetAddress());
                } catch (Exception e) {
                    throw new TestExecutionException("Error al rellenar dirección ext Piia", e, "ERROR_FILL", PAGE_NAME, page, "rellenarSegundaSeccionPiia");
                }
                try {
                    getInputDireccionTelefono().fill("+34" + faker.phoneNumber().cellPhone().replaceAll("[^0-9]", "").substring(0, 9));
                } catch (Exception e) {
                    throw new TestExecutionException("Error al rellenar teléfono dirección ext Piia", e, "ERROR_FILL", PAGE_NAME, page, "rellenarSegundaSeccionPiia");
                }
                try {
                    if(getSelectDireccionCursoLocalidadExt().isVisible() && getSelectDireccionCursoLocalidadExt().isEnabled()){
                        try {
                            page.waitForSelector("//*[@id=\"loading\"]", new Page.WaitForSelectorOptions().setState(WaitForSelectorState.HIDDEN).setTimeout(60000));
                        } catch (Exception e) {
                            throw new TestExecutionException("Error al esperar desaparición del loading", e, "ERROR_ESPERA", PAGE_NAME, page, "rellenarSegundaSeccionPiia");
                        }
                        try {
                            getSelectDireccionCursoProvincia().selectOption(new SelectOption().setIndex(
                                    random.nextInt(getSelectDireccionCursoProvincia().locator("option").count() + 1) + 1));
                        } catch (Exception e) {
                            throw new TestExecutionException("Error al seleccionar provincia curso ext Piia", e, "ERROR_SELECCION", PAGE_NAME, page, "rellenarSegundaSeccionPiia");
                        }
                        try {
                            page.waitForSelector("//*[@id=\"loading\"]", new Page.WaitForSelectorOptions().setState(WaitForSelectorState.HIDDEN).setTimeout(60000));
                        } catch (Exception e) {
                            throw new TestExecutionException("Error al esperar desaparición del loading", e, "ERROR_ESPERA", PAGE_NAME, page, "rellenarSegundaSeccionPiia");
                        }
                        try {
                            getSelectDireccionCursoMunicipio().selectOption(new SelectOption().setIndex(
                                    random.nextInt(getSelectDireccionCursoMunicipio().locator("option").count() + 1) + 1));
                        } catch (Exception e) {
                            throw new TestExecutionException("Error al seleccionar municipio curso ext Piia", e, "ERROR_SELECCION", PAGE_NAME, page, "rellenarSegundaSeccionPiia");
                        }
                        try {
                            page.waitForSelector("//*[@id=\"loading\"]", new Page.WaitForSelectorOptions().setState(WaitForSelectorState.HIDDEN).setTimeout(60000));
                        } catch (Exception e) {
                            throw new TestExecutionException("Error al esperar desaparición del loading", e, "ERROR_ESPERA", PAGE_NAME, page, "rellenarSegundaSeccionPiia");
                        }
                        try {
                            if (getSelectDireccionCursoLocalidad().locator("option").count() == 1) {
                                getSelectDireccionCursoLocalidad().selectOption(new SelectOption().setIndex(1));
                            } else {
                                getSelectDireccionCursoLocalidad().selectOption(new SelectOption().setIndex(
                                        random.nextInt(getSelectDireccionCursoLocalidad().locator("option").count() - 1)
                                                + 1));
                            }
                        } catch (Exception e) {
                            throw new TestExecutionException("Error al seleccionar localidad curso ext Piia", e, "ERROR_SELECCION", PAGE_NAME, page, "rellenarSegundaSeccionPiia");
                        }
                        try {
                            getInputDireccionCurso().fill(faker.address().streetAddress());
                        } catch (Exception e) {
                            throw new TestExecutionException("Error al rellenar dirección curso ext Piia", e, "ERROR_FILL", PAGE_NAME, page, "rellenarSegundaSeccionPiia");
                        }
                        try {
                            getInputDireccionCursoCodigoPostal().fill(faker.address().zipCode().replaceAll("[^0-9]", "").substring(0, 5));
                        } catch (Exception e) {
                            throw new TestExecutionException("Error al rellenar código postal curso ext Piia", e, "ERROR_FILL", PAGE_NAME, page, "rellenarSegundaSeccionPiia");
                        }
                    }
                } catch (Exception e) {
                    throw new TestExecutionException("Error en validación datos curso ext Piia", e, "ERROR_VALIDACION", PAGE_NAME, page, "rellenarSegundaSeccionPiia");
                }
            }
        } catch (Exception e) {
            throw new TestExecutionException("Error en validación país de dirección Piia", e, "ERROR_VALIDACION", PAGE_NAME, page, "rellenarSegundaSeccionPiia");
        }
    }

    public void rellenarTerceraSeccion() {
        try {
            page.evaluate("document.querySelector('#linkTab3').click()");
        } catch (Exception e) {
            throw new TestExecutionException("Error al ir a tercera sección", e, "ERROR_CLICK", PAGE_NAME, page, "rellenarTerceraSeccion");
        }
        try {
            getInputTelefono().fill("+34" + faker.phoneNumber().cellPhone().replaceAll("[^0-9]", "").substring(0, 9));
        } catch (Exception e) {
            throw new TestExecutionException("Error al rellenar teléfono", e, "ERROR_FILL", PAGE_NAME, page, "rellenarTerceraSeccion");
        }
        // Si quieres capturar el error del email, descomenta y añade try-catch
        // try {
        //     getInputCorreo().fill(faker.internet().emailAddress());
        // } catch (Exception e) {
        //     throw new TestExecutionException("Error al rellenar correo", e, "ERROR_FILL", PAGE_NAME, page, "rellenarTerceraSeccion");
        // }
        try {
            getInputMovil().fill("+34" + faker.phoneNumber().cellPhone().replaceAll("[^0-9]", "").substring(0, 9));
        } catch (Exception e) {
            throw new TestExecutionException("Error al rellenar móvil", e, "ERROR_FILL", PAGE_NAME, page, "rellenarTerceraSeccion");
        }
    }

    public void rellenarCuartaSeccion() {
        try {
            page.evaluate("document.querySelector('#linkTab4').click()");
        } catch (Exception e) {
            throw new TestExecutionException("Error al ir a cuarta sección", e, "ERROR_CLICK", PAGE_NAME, page, "rellenarCuartaSeccion");
        }
        try {
            getAmbitoProcendencia().selectOption(new SelectOption().setIndex(
                    random.nextInt(getProvinciaNacimiento().locator("option").count() + 1) + 1));
        } catch (Exception e) {
            throw new TestExecutionException("Error al seleccionar ámbito procedencia", e, "ERROR_SELECCION", PAGE_NAME, page, "rellenarCuartaSeccion");
        }
        try {
            getAmbitoDestino().selectOption(new SelectOption().setIndex(
                    random.nextInt(getProvinciaNacimiento().locator("option").count() + 1) + 1));
        } catch (Exception e) {
            throw new TestExecutionException("Error al seleccionar ámbito destino", e, "ERROR_SELECCION", PAGE_NAME, page, "rellenarCuartaSeccion");
        }
        try {
            getTipoProgMovilidad().selectOption(new SelectOption().setIndex(
                    random.nextInt(getProvinciaNacimiento().locator("option").count() + 1) + 1));
        } catch (Exception e) {
            throw new TestExecutionException("Error al seleccionar tipo programa movilidad", e, "ERROR_SELECCION", PAGE_NAME, page, "rellenarCuartaSeccion");
        }
        try {
            if (getFechaInicioEstancia().inputValue().isEmpty()) {
                try {
                    getFechaInicioEstancia().click();
                } catch (Exception e) {
                    throw new TestExecutionException("Error al hacer click en fecha inicio", e, "ERROR_CLICK", PAGE_NAME, page, "rellenarCuartaSeccion");
                }
                try {
                    getFechaInicioEstancia().waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
                } catch (Exception e) {
                    throw new TestExecutionException("Error al esperar calendario visible", e, "ERROR_ESPERA", PAGE_NAME, page, "rellenarCuartaSeccion");
                }
                try {
                    getCalendarioDia().click();
                } catch (Exception e) {
                    throw new TestExecutionException("Error al seleccionar día inicio", e, "ERROR_CLICK", PAGE_NAME, page, "rellenarCuartaSeccion");
                }
                try {
                    page.waitForSelector("//*[@id=\"loading\"]", new Page.WaitForSelectorOptions().setState(WaitForSelectorState.HIDDEN).setTimeout(60000));
                } catch (Exception e) {
                    throw new TestExecutionException("Error al esperar desaparición del loading", e, "ERROR_ESPERA", PAGE_NAME, page, "rellenarCuartaSeccion");
                }
            }
        } catch (Exception e) {
            throw new TestExecutionException("Error en validación fecha inicio estancia", e, "ERROR_VALIDACION", PAGE_NAME, page, "rellenarCuartaSeccion");
        }
        try {
            if (getFechaFinEstancia().inputValue().isEmpty()) {
                try {
                    getFechaFinEstancia().click();
                } catch (Exception e) {
                    throw new TestExecutionException("Error al hacer click en fecha fin", e, "ERROR_CLICK", PAGE_NAME, page, "rellenarCuartaSeccion");
                }
                try {
                    getFechaInicioEstancia().waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
                } catch (Exception e) {
                    throw new TestExecutionException("Error al esperar calendario visible", e, "ERROR_ESPERA", PAGE_NAME, page, "rellenarCuartaSeccion");
                }
                try {
                    getCalendarioDia().click();
                } catch (Exception e) {
                    throw new TestExecutionException("Error al seleccionar día fin", e, "ERROR_CLICK", PAGE_NAME, page, "rellenarCuartaSeccion");
                }
            }
        } catch (Exception e) {
            throw new TestExecutionException("Error en validación fecha fin estancia", e, "ERROR_VALIDACION", PAGE_NAME, page, "rellenarCuartaSeccion");
        }
    }

    /**
     * Verifica que la página de datos personales está correctamente cargada.
     * Lanza TestExecutionException si falla algún paso.
     */
    public void verificarCargaPagina() {
        try {
            if (!getBarraProgreso().isVisible()) {
                throw new TestExecutionException(
                    "La barra de progreso no está visible",
                    "CARGA_PÁGINA",
                    PAGE_NAME,
                    page,
                    "verificarCargaPagina"
                );
            }
            assertThat(getBarraProgresoDatosPersonales()).hasAttribute("class", "active");

        } catch (Exception e) {
            if (!(e instanceof TestExecutionException)) {
                throw new TestExecutionException(
                    "Error al verificar la carga de la página de datos personales",
                    e,
                    "CARGA_PÁGINA",
                    PAGE_NAME,
                    page,
                    "verificarCargaPagina"
                );
            }
            throw e;
        }
    }

}
