package matricula.unit.test.economico;

import dto.matricula.MatriculaPreciosHelper;
import matricula.unit.mother.model.acad.estudios.AsignaturaMother;
import matricula.unit.mother.model.acad.estudios.TitulacionDobleMother;
import matricula.unit.mother.model.acad.estudios.TitulacionMother;
import matricula.unit.mother.model.expe.ExpedienteDobleMother;
import matricula.unit.mother.model.expe.ExpedienteMother;
import matricula.unit.mother.model.mat.ProcesoMatriculaMother;
import model.adm.GruposTipoTitulacion;
import model.dbo.CursoAcademico;
import model.expe.Expediente;
import model.expe.ExpedienteDoble;
import model.mat.*;
import model.mat.precios.MatriculaPreciosMatricula;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.math.BigDecimal;
import model.acad.estudios.Asignatura;
import model.acad.estudios.Titulacion;
import model.acad.estudios.TitulacionDoble;
import model.acad.planif.GrupoSimple;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class CalcularPreciosAsignaturaPCEODiferentesTest {

//    @InjectMocks
//    private DatosAcademicosSessionMBean datosAcademicosSessionMBean;

    @Mock
    private PmatAlumnoInscripcion pmatAlumnoInscripcion;

    @Mock
    private GrupoSimple grupoSimple;

    @Mock
    private MatriculaPreciosHelper preciosHelper;

    @BeforeAll
    public static void setUp() {
    }

    @Test
    void test()
            throws java.sql.SQLTransactionRollbackException, InscripcionAsignaturaExpedienteException {
        CursoAcademico curso = CalcularPreciosTestUtils.cursoAcademico;

        Titulacion titu1 = TitulacionMother.create(200, "ADE", true);
        Titulacion titu2 = TitulacionMother.create(1035, "GITI", true);
        TitulacionDoble titulacionDoble = TitulacionDobleMother.create(1036, titu1, titu2);

        Titulacion titulacion = Titulacion.prepareTitulacionDobleTest(titulacionDoble);
        titulacion.setTitulacionID(titulacionDoble.getTitulaciondobleID());
        titulacion.setNombre("ADE + GITI");
        titulacion.setPreciosPceoDiferentes(true);

        titulacionDoble.setTitulacion(titulacion);

        Expediente expe1 = ExpedienteMother.create(titu1, true);
        Expediente expe2 = ExpedienteMother.create(titu2, true);

        ExpedienteDoble expeDoble = ExpedienteDobleMother.create(expe1, expe2, titulacionDoble, true);
        expe1.setExpedienteDoble1(expeDoble);
        expe2.setExpedienteDoble2(expeDoble);

        BigDecimal importeCreditoTitu = BigDecimal.valueOf(100);
        BigDecimal importeCredito2Titu = BigDecimal.valueOf(120);

        BigDecimal importeCreditoTitu1 = BigDecimal.valueOf(60);
        BigDecimal importeCredito2Titu1 = BigDecimal.valueOf(80);

        BigDecimal importeCreditoTitu2 = BigDecimal.valueOf(90);
        BigDecimal importeCredito2Titu2 = BigDecimal.valueOf(110);

        MatriculaPreciosMatricula preciosMatriculaTituDoble =
                CalcularPreciosTestUtils.crearPrecios(curso, titulacion, importeCreditoTitu, importeCredito2Titu);
        MatriculaPreciosMatricula preciosMatriculaTitu1 =
                CalcularPreciosTestUtils.crearPrecios(curso, titu1, importeCreditoTitu1, importeCredito2Titu1);
        MatriculaPreciosMatricula preciosMatriculaTitu2 =
                CalcularPreciosTestUtils.crearPrecios(curso, titu2, importeCreditoTitu2, importeCredito2Titu2);

        preciosHelper = MatriculaPreciosHelper.of(curso, preciosMatriculaTituDoble,
                CalcularPreciosTestUtils.importeMatricula, CalcularPreciosTestUtils.importeReservaAdmision);
        preciosHelper.withPreciosTitulacion1(preciosMatriculaTitu1);
        preciosHelper.withPreciosTitulacion2(preciosMatriculaTitu2);

        pmatAlumnoInscripcion = PmatAlumnoInscripcion.prepareInscripcionRegladaPCE(
                ProcesoMatriculaMother.create(curso),
                new GruposTipoTitulacion(GruposTipoTitulacion.GRADO_RENOVACION),
                expeDoble, preciosHelper);

        Integer annusMat = 1;

        // Cálculo precios asignatura 1
        Asignatura asignatura1 = AsignaturaMother.createObligatoria(titu1, BigDecimal.valueOf(6));
        PmatAlumnoInscripcionAsignatura inscripcionAsignatura1 =
                CalcularPreciosTestUtils.crearMatriculaAsignaturaOrdinaria(pmatAlumnoInscripcion, asignatura1, grupoSimple, annusMat);
        inscripcionAsignatura1 = CalcularPreciosTestUtils.calcularPrecioMatriculaAsignatura(pmatAlumnoInscripcion, inscripcionAsignatura1);

        // Cálculo precios asignatura 2
        Asignatura asignatura2 = AsignaturaMother.createObligatoria(titu2, BigDecimal.valueOf(6));
        PmatAlumnoInscripcionAsignatura inscripcionAsignatura2 =
                CalcularPreciosTestUtils.crearMatriculaAsignaturaOrdinaria(pmatAlumnoInscripcion, asignatura2, grupoSimple, annusMat);
        inscripcionAsignatura2 = CalcularPreciosTestUtils.calcularPrecioMatriculaAsignatura(pmatAlumnoInscripcion, inscripcionAsignatura2);

        // Comprobación resultados
        BigDecimal resultadoPrecioAsignatura1 = BigDecimal.valueOf(360.0);
        BigDecimal resultadoPrecioAsignatura2 = BigDecimal.valueOf(540.0);

        Assertions.assertEquals(resultadoPrecioAsignatura1.doubleValue(), inscripcionAsignatura1.getPrecio().doubleValue(),
                CalcularPreciosTestUtils._TOLERANCIA);
        Assertions.assertNotNull(inscripcionAsignatura1.getGrupoSimple());

        Assertions.assertEquals(resultadoPrecioAsignatura2.doubleValue(), inscripcionAsignatura2.getPrecio().doubleValue(),
                CalcularPreciosTestUtils._TOLERANCIA);
        Assertions.assertNotNull(inscripcionAsignatura2.getGrupoSimple());

        Assertions.assertTrue(pmatAlumnoInscripcion.isPreciosPorPce());
    }

}