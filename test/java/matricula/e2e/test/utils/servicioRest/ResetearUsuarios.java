package matricula.e2e.test.utils.servicioRest;

import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.APIResponse;
import lombok.extern.slf4j.Slf4j;
import matricula.e2e.test.utils.TestProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ActiveProfiles;

/**
 *
 * @author aaperez
 */
@Component
@ActiveProfiles(value = "test")
@Slf4j
public class ResetearUsuarios {

    @Autowired
    private TestProperties props;

    public boolean eliminarUsuariosRenovacion(int alumnoId, int idins, String cursoAcad, APIRequestContext request) {
        try {

            String url = String.format(
                    props.getEliminarFuturosEstudiantes(),
                    alumnoId, idins, cursoAcad
            );

            APIResponse resetResponse = request.get(url);

            // Si el código de respuesta es 200, retornamos true
            if (resetResponse.status() == 200) {
                log.info("se ha reseteado el usuario");
                return true;
            } else {
                log.warn("Error al resetear el usuario: " + resetResponse.status());
                return false;
            }

        } catch (Exception e) {
            e.printStackTrace();
            log.warn("Ocurrió un error al resetear los usuarios");
            return false;
        }
    }

    public boolean resetearUsuariosFE(int alumnoId, int idins, String cursoAcad, APIRequestContext request) {
        try {

            String url = String.format(
                    props.getResetFuturosEstudiantes(),
                    alumnoId, idins, cursoAcad
            );

            APIResponse resetResponse = request.get(url);

            // Si el código de respuesta es 200, retornamos true
            if (resetResponse.status() == 200) {
                log.info("se ha reseteado el usuario");
                return true;
            } else {
                log.warn("Error al resetear el usuario: " + resetResponse.status());
                return false;
            }

        } catch (Exception e) {
            e.printStackTrace();
            log.warn("Ocurrió un error al resetear los usuarios");
            return false;
        }
    }

    public boolean resetearUsuariosAdmisiones(int candidatoId, int procesoAdmisionId, int titulacionId, APIRequestContext request) {
        try {

            String url = String.format(
                    props.getResetAdmisiones(),
                    candidatoId, procesoAdmisionId, titulacionId
            );

            APIResponse resetResponse = request.get(url);

            // Si el código de respuesta es 200, retornamos true
            if (resetResponse.status() == 200) {
                log.info("se ha reseteado el usuario");
                return true;
            } else {
                log.warn("Error al resetear el usuario: " + resetResponse.status());
                return false;
            }

        } catch (Exception e) {
            e.printStackTrace();
            log.warn("Ocurrió un error al resetear los usuarios");
            return false;
        }
    }

    public boolean resetearUsuariosTF(int alumnoId, int idins, String cursoAcad, char tipoTitulacion, APIRequestContext request) {
        try {

            String url = String.format(
                    props.getResetTF(),
                    alumnoId, idins, cursoAcad, tipoTitulacion
            );

            APIResponse resetResponse = request.get(url);

            // Si el código de respuesta es 200, retornamos true
            if (resetResponse.status() == 200) {
                log.info("se ha reseteado el usuario");
                return true;
            } else {
                log.warn("Error al resetear el usuario: " + resetResponse.status());
                return false;
            }

        } catch (Exception e) {
            e.printStackTrace();
            log.warn("Ocurrió un error al resetear los usuarios");
            return false;
        }
    }

}
