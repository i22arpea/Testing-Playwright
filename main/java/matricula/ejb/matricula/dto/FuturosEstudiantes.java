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
public class FuturosEstudiantes implements Serializable {

    private static final long serialVersionUID = 1L;

    private String cursoAcad;
    private int idAlumno;
    private Character tipoTitulacion;
    private int idInscripcion;
    private boolean solo_TF;
    private boolean curso_cerrado;
    private int egresado;
    private BigDecimal creditosPendientes;
    private String email;
    private String uid;




    public static FuturosEstudiantes empty() {
        return FuturosEstudiantes.builder().build();
    }

}
