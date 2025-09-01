package matricula.ejb.matricula.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;


@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class FuturosEstudiantesAsignatura implements Serializable {

    private static final long serialVersionUID = 1L;

    private int idAlumno;
    private int expediente;
    private int idInscripcion;
    private String email;
    private String asignatura;
    private String cursoAcad;
    private String uid;


    public static FuturosEstudiantesAsignatura empty() {
        return FuturosEstudiantesAsignatura.builder().build();
    }

}
