package matricula.e2e.test.utils.servicioRest;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.APIResponse;
import lombok.extern.slf4j.Slf4j;
import matricula.ejb.matricula.dto.FuturosEstudiantes;
import matricula.ejb.matricula.dto.FuturosEstudiantesAdmisiones;
import matricula.ejb.matricula.dto.FuturosEstudiantesAsignatura;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;
/**
 *
 * @author aaperez
 */
@Component
@Slf4j
public class ObtenerUsuarios {

    private final Random random = new Random();
    private final ObjectMapper objectMapper = new ObjectMapper();

    public Stream<FuturosEstudiantes> obtenerFuturosEstudiantes(APIRequestContext request, String restUsuarios, int numeroEstudiantes) {
        try {
            APIResponse response = request.get(restUsuarios);
            List<FuturosEstudiantes> estudiantes = objectMapper.readValue(response.body(), new TypeReference<List<FuturosEstudiantes>>() {});
            List<FuturosEstudiantes> estudiantes5validos = new ArrayList<>();
            for (int i = 0; i < numeroEstudiantes; i++) {
                estudiantes5validos.add(estudiantes.get(random.nextInt(estudiantes.size())));
            }
            return estudiantes5validos.stream();
        } catch (Exception e) {
            e.printStackTrace();
            log.warn("Ocurrió un error al obtener los usuarios");
            return Stream.empty();
        }
    }



    public Stream<FuturosEstudiantesAsignatura> obtenerFuturosEstudiantesAsignatura(APIRequestContext request, String restUsuarios, int numeroEstudiantes) {
        try {
            APIResponse response = request.get(restUsuarios);
            List<FuturosEstudiantesAsignatura> estudiantes = objectMapper.readValue(response.body(), new TypeReference<List<FuturosEstudiantesAsignatura>>() {});
            List<FuturosEstudiantesAsignatura> estudiantes5validos = new ArrayList<>();
            for (int i = 0; i < numeroEstudiantes; i++) {
                estudiantes5validos.add(estudiantes.get(random.nextInt(estudiantes.size())));
            }
            return estudiantes5validos.stream();
        } catch (Exception e) {
            e.printStackTrace();
            log.warn("Ocurrió un error al obtener los usuarios");
            return Stream.empty();
        }
    }

    public Stream<FuturosEstudiantesAdmisiones> obtenerUsuariosAdmisiones(APIRequestContext request, String restUsuarios, int numeroEstudiantes) {
        try {
            APIResponse response = request.get(restUsuarios);
            List<FuturosEstudiantesAdmisiones> estudiantes = objectMapper.readValue(response.body(), new TypeReference<List<FuturosEstudiantesAdmisiones>>() {});
            List<FuturosEstudiantesAdmisiones> estudiantes5validos = new ArrayList<>();
            for (int i = 0; i < numeroEstudiantes; i++) {
                estudiantes5validos.add(estudiantes.get(random.nextInt(estudiantes.size())));
            }
            return estudiantes5validos.stream();
        } catch (Exception e) {
            e.printStackTrace();
            log.warn("Ocurrió un error al obtener los usuarios");
            return Stream.empty();
        }
    }
}