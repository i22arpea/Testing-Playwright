package matricula.rest.futurosestudiantes;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import matricula.ejb.matricula.FuturosEstudiantesBean;
import matricula.ejb.matricula.dto.FuturosEstudiantes;
import matricula.ejb.matricula.dto.FuturosEstudiantesOutgoing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import java.util.List;


@RestController
@RequestMapping("/futurosestudiantesoutgoing")
@Slf4j
public class ObtenerFuturosEstudiantesOutgoing {



    @Autowired
    private FuturosEstudiantesBean futurosEstudiantesBean;

    /**
     * Obtiene la lista de futuros estudiantes para el curso académico dado.
     *
     * @param cursoAcad El curso académico para el que se desea obtener la lista.
     * @return La lista de futuros estudiantes en formato JSON.
     */
    @GetMapping("/proximocurso")
    public ResponseEntity<?> getFuturosEstudiantesOutgoing( @RequestParam(value = "cursoAcad", required = false) String cursoAcad) {

        try {
            if (cursoAcad == null || cursoAcad.isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("{\"error\": \"curso academico no proporcionado\"}");
            }

            List<FuturosEstudiantesOutgoing> listaFE = futurosEstudiantesBean.obtenerFuturosEstudiantesOutgoing(cursoAcad);

            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(listaFE);

            return ResponseEntity.ok(json);

        } catch (JsonProcessingException ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"error\": \"Error procesando JSON\", \"detalle\": \"" + ex.getMessage() + "\"}");
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"error\": \"Error inesperado\", \"detalle\": \"" + ex.getMessage() + "\"}");
        }
    }






}
