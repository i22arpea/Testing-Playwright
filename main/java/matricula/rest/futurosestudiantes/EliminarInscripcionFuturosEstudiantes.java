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
@RequestMapping("/eliminarinsfuturosestudiantes")
@Slf4j
public class EliminarInscripcionFuturosEstudiantes {

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
                        .body("{\"error\": \"ID de alumno no proporcionado\"}");
            }

            if (cursoAcad == null || cursoAcad.isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("{\"error\": \"curso academico no proporcionado\"}");
            }


            boolean exito = resetAlumnosBean.eliminarInscripcionAlumnoFuturosEstudiantes(Integer.parseInt(alumnoId), Integer.parseInt(idins), cursoAcad);


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
