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
@RequestMapping("/resetfuturosestudiantes")
@Slf4j

public class ResetearFuturosEstudiantes {



    @Autowired
    private ResetAlumnosBean resetAlumnosBean;

    @GetMapping
    public ResponseEntity<?> resetFuturosEstudiantes(
            @RequestParam(value = "alumnoId", required = false) String alumnoId,
            @RequestParam(value = "idins", required = false) String idins,
            @RequestParam(value = "cursoAcad", required = false) String cursoAcad) {

        try {
            if (alumnoId == null || alumnoId.isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("{\"error\": \"id de candidato no proporcionado\"}");
            }

            if (idins == null || idins.isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("{\"error\": \"id de inscripcion no proporcionado\"}");
            }

            if (cursoAcad == null || cursoAcad.isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("{\"error\": \"ID de proceso de admision no proporcionado\"}");
            }

            boolean exito = resetAlumnosBean.resetearInscripcionAlumnoFuturosEstudiantes(Integer.parseInt(alumnoId), Integer.parseInt(idins), cursoAcad);


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