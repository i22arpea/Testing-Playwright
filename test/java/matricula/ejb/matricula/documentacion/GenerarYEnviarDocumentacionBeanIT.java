package matricula.ejb.matricula.documentacion;

import matricula.dao.mat.PmatAlumnoInscripcionFacade;
import model.mat.PmatAlumnoInscripcion;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@TestPropertySource(locations = "classpath:test.properties")
class GenerarYEnviarDocumentacionBeanIT {

    @Autowired private GenerarYEnviarDocumentacionBean sut;

    @Autowired private PmatAlumnoInscripcionFacade pmatAlumnoInscripcionFacade;

/*    @Test
    void ensure_documentacion_generada_is_true() {
        // Arrange
        PmatAlumnoInscripcion inscripcionFormalizada = pmatAlumnoInscripcionFacade.find(83907);

        // Act
        sut.execute(inscripcionFormalizada);

        // Assert
        inscripcionFormalizada = pmatAlumnoInscripcionFacade.find(83907);
       // assertThat(inscripcionFormalizada.isDocumentacionGenerada()).isTrue();
    }*/

}