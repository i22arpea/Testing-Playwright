package matricula.ejb.matricula.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import es.uloyola.rdto.erp.dbo.ContactoDTO;
import lombok.*;

import java.io.Serializable;



@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class FuturosEstudiantesAdmisiones implements Serializable {

    private static final long serialVersionUID = 1L;

    private int idProceso;
    private int idTitulacion;
    private int idCandidato;
    private String email;
    private String estado;
    private String nombreestado;
    private int inicio_matriculacion;
    private String uid;
    //private int matriculado;
    //private String denominacion;


    public static FuturosEstudiantesAdmisiones empty() {
        return FuturosEstudiantesAdmisiones.builder().build();
    }

}
