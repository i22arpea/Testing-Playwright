package matricula.unit.test.matricula;

import matricula.dao.mat.ColectivosEspecialesTiposProcesosFacade;
import matricula.ejb.matricula.InscripcionMatriculaBean;
import matricula.unit.mother.model.mat.ColectivoEspecialMatriculaMother;
import matricula.unit.mother.model.mat.InscripcionColectivosMatriculaMother;
import model.acad.estudios.TitulacionTipo;
import model.mat.ColectivoEspecialMatricula;
import model.mat.ColectivosEspecialesTiposProcesos;
import model.mat.PmatAlumnoInscripcion;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

/**
 *
 * @author aamunoz
 */

@ExtendWith(MockitoExtension.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class InscripcionMatriculaBeanTest {

  @InjectMocks
  private InscripcionMatriculaBean sut;

  @Mock
  private ColectivosEspecialesTiposProcesosFacade colectivosEspecialesTiposProcesosFacade;

  @Test
  void should_find_colectivo_simultaneidad_titulaciones_for_inscripcion_expediente_simple() throws Exception {
    PmatAlumnoInscripcion inscripcion = InscripcionColectivosMatriculaMother.createMasterNuevoIngreso();
    shouldFindColectivos(
        ColectivoEspecialMatriculaMother.createSimultaneidadTitulacionesExcluidosExpedientesDobles(inscripcion));
    List<ColectivoEspecialMatricula> colectivos = sut.findColectivosEspecialesMatricula(inscripcion);
    Assertions.assertThat(colectivos).hasSize(1);
  }

  @Test
  void should_not_find_colectivo_simultaneidad_titulaciones_for_inscripcion_expediente_doble() throws Exception {
    PmatAlumnoInscripcion inscripcion = InscripcionColectivosMatriculaMother.createWithExpedienteDobleNuevoIngreso();
    shouldFindColectivos(
        ColectivoEspecialMatriculaMother.createSimultaneidadTitulacionesExcluidosExpedientesDobles(inscripcion));
    List<ColectivoEspecialMatricula> colectivos = sut.findColectivosEspecialesMatricula(inscripcion);
    Assertions.assertThat(colectivos).isEmpty();
  }

  private void shouldFindColectivos(List<ColectivosEspecialesTiposProcesos> colectivosTiposTitulacion) {
    Mockito.when(colectivosEspecialesTiposProcesosFacade.findColectivosEspeciales(new TitulacionTipo("G")))
        .thenReturn(colectivosTiposTitulacion);
  }
}
