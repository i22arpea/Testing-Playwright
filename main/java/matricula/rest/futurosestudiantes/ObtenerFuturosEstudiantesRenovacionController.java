package matricula.rest.futurosestudiantes;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import matricula.ejb.matricula.FuturosEstudiantesBean;
import matricula.ejb.matricula.dto.FuturosEstudiantes;
import matricula.ejb.matricula.dto.FuturosEstudiantesAsignatura;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/futurosestudiantesrenovacion")
@Slf4j
public class ObtenerFuturosEstudiantesRenovacionController {

    @Autowired
    private FuturosEstudiantesBean futurosEstudiantesBean;

    @GetMapping
    public ResponseEntity<?> getFuturosEstudiantesRenovacion(
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
    public ResponseEntity<?> getFuturosEstudiantesRenovacionColectivo(
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

            List<FuturosEstudiantes> listaFE = futurosEstudiantesBean.obtenerFuturosEstudiantesRenovacionColectivo(tipoFK, cursoAcad, Integer.parseInt(solo_TF), Integer.parseInt(colectivo));

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

    @GetMapping("/masasig")
    public ResponseEntity<?> getMasAsignaturasRenovacion(
            @RequestParam(value = "cursoAcad", required = false) String cursoAcad,
            @RequestParam(value = "tipoTitulacion", required = false) String tipoFK,
            @RequestParam(value = "soloTF", required = false) String solo_TF,
            @RequestParam(value = "colectivo", required = false) String colectivo) {

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

            List<FuturosEstudiantes> lista = futurosEstudiantesBean.obtenerFuturosEstudiantesRenovacionMasAsignatura(tipoFK, cursoAcad, Integer.parseInt(solo_TF), Integer.parseInt(colectivo));

            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(lista);

            return ResponseEntity.ok(json);

        } catch (JsonProcessingException ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"error\": \"Error procesando JSON\", \"detalle\": \"" + ex.getMessage() + "\"}");
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"error\": \"Error inesperado\", \"detalle\": \"" + ex.getMessage() + "\"}");
        }
    }

    @GetMapping("/asigrestacept")
    public ResponseEntity<?> getAsignaturaRestriccionAceptacionRenovacion(
            @RequestParam(value = "asigSuperada", required = false) String asignaturaSuperada,
            @RequestParam(value = "cursoAcad", required = false) String cursoAcad,
            @RequestParam(value = "asigMat", required = false) String asignaturaMatricular) {

        try {
            if (asignaturaSuperada == null || asignaturaSuperada.isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("{\"error\": \"ID de proceso no proporcionado\"}");
            }

            if (cursoAcad == null || cursoAcad.isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("{\"error\": \"ID de proceso no proporcionado\"}");
            }

            if (asignaturaMatricular == null || asignaturaMatricular.isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("{\"error\": \"ID de proceso no proporcionado\"}");
            }

            List<FuturosEstudiantesAsignatura> lista = futurosEstudiantesBean.obtenerFuturosEstudiantesRenovacionAsigRestrAcept(Integer.parseInt(asignaturaSuperada), cursoAcad, Integer.parseInt(asignaturaMatricular));

            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(lista);

            return ResponseEntity.ok(json);

        } catch (JsonProcessingException ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"error\": \"Error procesando JSON\", \"detalle\": \"" + ex.getMessage() + "\"}");
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"error\": \"Error inesperado\", \"detalle\": \"" + ex.getMessage() + "\"}");
        }
    }

    @GetMapping("/asigresterror")
    public ResponseEntity<?> getAsignaturaRestriccionErrorRenovacion(
            @RequestParam(value = "asigSuperada", required = false) String asignaturaSuperada,
            @RequestParam(value = "cursoAcad", required = false) String cursoAcad,
            @RequestParam(value = "asigMat", required = false) String asignaturaMatricular) {

        try {
            if (asignaturaSuperada == null || asignaturaSuperada.isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("{\"error\": \"ID de proceso no proporcionado\"}");
            }

            if (cursoAcad == null || cursoAcad.isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("{\"error\": \"ID de proceso no proporcionado\"}");
            }

            if (asignaturaMatricular == null || asignaturaMatricular.isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("{\"error\": \"ID de proceso no proporcionado\"}");
            }

            List<FuturosEstudiantesAsignatura> lista = futurosEstudiantesBean.obtenerFuturosEstudiantesRenovacionAsigRestrError(Integer.parseInt(asignaturaSuperada), cursoAcad, Integer.parseInt(asignaturaMatricular));

            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(lista);

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