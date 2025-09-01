package matricula.rest.futurosestudiantes;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import matricula.ejb.matricula.ResetAlumnosBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("resetfuturosestudiantestf")
@Slf4j
public class ResetearFuturosEstudiantesTF {

    @Autowired
    private ResetAlumnosBean resetAlumnosBean;

    @GetMapping
    public ResponseEntity<?> resetFuturosEstudiantes(@RequestParam("alumnoId") String alumnoId, @RequestParam("idins") String idins, @RequestParam("cursoAcad") String cursoAcad, @RequestParam("tipoTitulacion") char tipoTitulacion) {

        try {

            if (alumnoId == null || alumnoId.isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("{\"error\": \"ID de alumno no proporcionado\"}");
            }

            if (idins == null || idins.isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("{\"error\": \"ID de inscripcion no proporcionado\"}");
            }

            if (cursoAcad == null || cursoAcad.isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("{\"error\": \"Curso academico no proporcionado\"}");
            }


            boolean exito = resetAlumnosBean.resetearAlumnoFuturosEstudiantesTF(Integer.parseInt(alumnoId), Integer.parseInt(idins), cursoAcad, tipoTitulacion);


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
