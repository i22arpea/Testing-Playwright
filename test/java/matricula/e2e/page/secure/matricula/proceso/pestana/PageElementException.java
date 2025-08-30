package matricula.e2e.page.secure.matricula.proceso.pestana;

public class PageElementException extends RuntimeException {
    public PageElementException(String pageName, String elementDescription, Throwable cause) {
        super("Error en la página '" + pageName + "' al interactuar con el elemento: " + elementDescription, cause);
    }
    public PageElementException(String pageName, String elementDescription) {
        super("Error en la página '" + pageName + "' al interactuar con el elemento: " + elementDescription);
    }
}
