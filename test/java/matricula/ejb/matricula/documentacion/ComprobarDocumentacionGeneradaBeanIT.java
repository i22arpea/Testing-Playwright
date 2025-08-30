package matricula.ejb.matricula.documentacion;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(locations = "classpath:test.properties")
class ComprobarDocumentacionGeneradaBeanIT {

    @Autowired
    private ComprobarDocumentacionGeneradaBean sut;



    @Test
    void should_call_comprobar_documentacion() {
        // Arrange
        // Act
        sut.execute();

        // Assert
    }

}