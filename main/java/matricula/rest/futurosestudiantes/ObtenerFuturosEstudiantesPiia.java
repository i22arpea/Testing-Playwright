package matricula.rest.futurosestudiantes;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import matricula.ejb.matricula.FuturosEstudiantesBean;
import matricula.ejb.matricula.dto.FuturosEstudiantes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import java.util.List;


@RestController
@RequestMapping("/futurosestudiantespiia")
@Slf4j
public class ObtenerFuturosEstudiantesPiia {


    @Autowired
    private FuturosEstudiantesBean futurosEstudiantesBean;

    @GetMapping
    public ResponseEntity<?> getFuturosEstudiantesPiia(
            @RequestParam(value = "cursoAcad", required = false) String cursoAcad,
            @RequestParam(value = "tipoTitulacion", required = false) String tipoFK,
            @RequestParam(value = "soloTF", required = false) String solo_TF) {

        try {
            if (cursoAcad == null || cursoAcad.isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("{\"error\": \"ID de proceso no proporcionado\"}");
            }

            if (tipoFK == null || tipoFK.isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("{\"error\": \"ID de proceso no proporcionado\"}");
            }

            if (solo_TF == null || solo_TF.isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("{\"error\": \"ID de proceso no proporcionado\"}");
            }

            List<FuturosEstudiantes> listaFE = futurosEstudiantesBean.obtenerFuturosEstudiantesRenovacion(tipoFK, cursoAcad, Integer.parseInt(solo_TF));

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

    @GetMapping("/colectivo")
    public ResponseEntity<?> getFuturosEstudiantesPiiaColectivo(
            @RequestParam(value = "cursoAcad", required = false) String cursoAcad,
            @RequestParam(value = "tipoTitulacion", required = false) String tipoFK,
            @RequestParam(value = "soloTF", required = false) String solo_TF,
            @RequestParam(value = "colectivo", required = false) String colectivo) {

        try {
            if (cursoAcad == null || cursoAcad.isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("{\"error\": \"curso academico no proporcionado\"}");
            }

            if (tipoFK == null || tipoFK.isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("{\"error\": \"tipo de titulacion no proporcionada\"}");
            }

            if (solo_TF == null || solo_TF.isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("{\"error\": \"soloTF no proporcionado\"}");
            }

            List<FuturosEstudiantes> listaFE = futurosEstudiantesBean.obtenerFuturosEstudiantesPiiaColectivo(tipoFK, cursoAcad, Integer.parseInt(solo_TF), Integer.parseInt(colectivo));

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
