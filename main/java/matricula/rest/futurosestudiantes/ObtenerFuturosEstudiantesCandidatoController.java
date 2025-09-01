package matricula.rest.futurosestudiantes;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import matricula.ejb.matricula.FuturosEstudiantesBean;
import matricula.ejb.matricula.dto.FuturosEstudiantesAdmisiones;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/futurosestudiantescandidato")
@Slf4j
public class ObtenerFuturosEstudiantesCandidatoController {

    @Autowired
    private FuturosEstudiantesBean futurosEstudiantesBean;

    @GetMapping
    public ResponseEntity<List<FuturosEstudiantesAdmisiones>> getFuturosEstudiantes(@RequestParam(value = "idProceso", required = false) String idProceso) {
        try {
            if (idProceso == null || idProceso.isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(null);
            }

            List<FuturosEstudiantesAdmisiones> listaFE = futurosEstudiantesBean.obtenerFuturosEstudiantesAdmisiones(Integer.parseInt(idProceso));
            return ResponseEntity.ok(listaFE);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }
    }

    @GetMapping("/Mat")
    public ResponseEntity<List<FuturosEstudiantesAdmisiones>> getFuturosEstudiantesMat(@RequestParam(value = "idProceso", required = false) String idProceso) {
        try {
            if (idProceso == null || idProceso.isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(null);
            }

            List<FuturosEstudiantesAdmisiones> listaFE = futurosEstudiantesBean.obtenerFuturosEstudiantesAdmisionesMat(Integer.parseInt(idProceso));
            return ResponseEntity.ok(listaFE);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }
    }


    @GetMapping("/Titulacion")
    public ResponseEntity<List<FuturosEstudiantesAdmisiones>> getFuturosEstudiantesTitulacion(@RequestParam(value = "idProceso", required = false) String idProceso, @RequestParam(value = "titulacion", required = false) String titulacion) {
        try {
            if (idProceso == null || idProceso.isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(null);
            }

            List<FuturosEstudiantesAdmisiones> listaFE = futurosEstudiantesBean.obtenerFuturosEstudiantesAdmisionesTitulacion(Integer.parseInt(idProceso), Integer.parseInt(titulacion));
            return ResponseEntity.ok(listaFE);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }
    }
}