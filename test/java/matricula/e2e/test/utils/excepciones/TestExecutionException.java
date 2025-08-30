package matricula.e2e.test.utils.excepciones;

import com.microsoft.playwright.Page;

/**
 * Excepción personalizada para errores durante la ejecución de pruebas.
 * Proporciona información contextual sobre dónde ocurrió el error.
 *
 * @author aaperez
 */
public class TestExecutionException extends RuntimeException {

    private final String codigoError;
    private final String paginaName;
    private final Page page;
    private final String metodo;

    /**
     * Constructor para errores sin excepción previa.
     *
     * @param mensaje Mensaje descriptivo del error
     * @param codigoError Código que identifica el tipo de error
     * @param paginaName Nombre de la página donde ocurrió el error
     * @param page Instancia de la página de Playwright
     * @param metodo Nombre del método donde ocurrió el error
     */
    public TestExecutionException(String mensaje, String codigoError, String paginaName, Page page, String metodo) {
        super(mensaje);
        this.codigoError = codigoError;
        this.paginaName = paginaName;
        this.page = page;
        this.metodo = metodo;
    }

    /**
     * Constructor para errores con una excepción previa.
     *
     * @param mensaje Mensaje descriptivo del error
     * @param causa Excepción que causó este error
     * @param codigoError Código que identifica el tipo de error
     * @param paginaName Nombre de la página donde ocurrió el error
     * @param page Instancia de la página de Playwright
     * @param metodo Nombre del método donde ocurrió el error
     */
    public TestExecutionException(String mensaje, Throwable causa, String codigoError, String paginaName, Page page, String metodo) {
        super(mensaje, causa);
        this.codigoError = codigoError;
        this.paginaName = paginaName;
        this.page = page;
        this.metodo = metodo;
    }

    /**
     * @return El código de error asociado con esta excepción
     */
    public String getCodigoError() {
        return codigoError;
    }

    /**
     * @return El nombre de la página donde ocurrió el error
     */
    public String getPaginaName() {
        return paginaName;
    }

    /**
     * @return La instancia de la página de Playwright
     */
    public Page getPage() {
        return page;
    }

    /**
     * @return El nombre del método donde ocurrió el error
     */
    public String getMetodo() {
        return metodo;
    }

    @Override
    public String toString() {
        return String.format("TestExecutionException[código=%s, página=%s, método=%s]: %s",
                codigoError, paginaName, metodo, getMessage());
    }
}
