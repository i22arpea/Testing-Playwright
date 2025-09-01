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
public class FuturosEstudiantesOutgoing implements Serializable {

    private static final long serialVersionUID = 1L;

    private String cursoAcad;
    private int idAlumno;
    private int tipoTitulacion;
    private String email;
    private String uid;




    public static FuturosEstudiantesOutgoing empty() {
        return FuturosEstudiantesOutgoing.builder().build();
    }

}
