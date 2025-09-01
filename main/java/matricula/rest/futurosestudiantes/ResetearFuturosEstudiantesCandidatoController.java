package matricula.rest.futurosestudiantes;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import matricula.ejb.matricula.ResetAlumnosBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/resetfuturosestudiantescandidato")
@Slf4j
public class ResetearFuturosEstudiantesCandidatoController {

    @Autowired
    private ResetAlumnosBean resetAlumnosBean;

    @GetMapping
    public ResponseEntity<?> resetFuturosEstudiantes(
            @RequestParam(value = "candidatoId", required = false) String candidatoId,
            @RequestParam(value = "procesoAdmisionId", required = false) String procesoAdmisionId,
            @RequestParam(value = "titulacionCursoId", required = false) String titulacionId) {

        try {
            if (candidatoId == null || candidatoId.isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("{\"error\": \"id de candidato no proporcionado\"}");
            }

            if (procesoAdmisionId == null || procesoAdmisionId.isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("{\"error\": \"ID de proceso de admision no proporcionado\"}");
            }

            if (titulacionId == null || titulacionId.isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("{\"error\": \"curso no proporcionado\"}");
            }

            boolean exito = resetAlumnosBean.resetearAlumnoFuturosEstudiantesCandidatos(
                    Integer.parseInt(candidatoId),
                    Integer.parseInt(procesoAdmisionId),
                    Integer.parseInt(titulacionId)
            );

            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(exito);

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