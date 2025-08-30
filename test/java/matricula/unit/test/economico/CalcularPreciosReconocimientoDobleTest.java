package matricula.unit.test.economico;

import dto.matricula.MatriculaPreciosHelper;
import matricula.unit.mother.model.acad.estudios.AsignaturaMother;
import matricula.unit.mother.model.acad.estudios.TitulacionDobleMother;
import matricula.unit.mother.model.acad.estudios.TitulacionMother;
import matricula.unit.mother.model.expe.ExpedienteDobleMother;
import matricula.unit.mother.model.expe.ExpedienteMother;
import matricula.unit.mother.model.mat.ProcesoMatriculaMother;
import model.acad.estudios.Asignatura;
import model.acad.estudios.Titulacion;
import model.acad.estudios.TitulacionDoble;
import model.acad.planif.GrupoSimple;
import model.adm.GruposTipoTitulacion;
import model.dbo.CursoAcademico;
import model.expe.Expediente;
import model.expe.ExpedienteDoble;
import model.mat.InscripcionAsignaturaExpedienteException;
import model.mat.PmatAlumnoInscripcion;
import model.mat.PmatAlumnoInscripcionAsignatura;
import model.mat.PmatAlumnoTipoMatriculaAsignatura;
import model.mat.precios.MatriculaPreciosMatricula;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

@ExtendWith(MockitoExtension.class)
public class CalcularPreciosReconocimientoDobleTest {

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

    @org.junit.jupiter.api.Test
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

        BigDecimal importeRecDoble = BigDecimal.valueOf(10);
        BigDecimal importeRecDobleTitu1 = BigDecimal.valueOf(15);
        BigDecimal importeRecDobleTitu2 = BigDecimal.valueOf(12);

        MatriculaPreciosMatricula preciosMatriculaTituDoble =
                CalcularPreciosTestUtils.crearPreciosReconocimientoDoble(curso, titulacion, importeRecDoble);
        MatriculaPreciosMatricula preciosMatriculaTitu1 =
                CalcularPreciosTestUtils.crearPreciosReconocimientoDoble(curso, titu1, importeRecDobleTitu1);
        MatriculaPreciosMatricula preciosMatriculaTitu2 =
                CalcularPreciosTestUtils.crearPreciosReconocimientoDoble(curso, titu2, importeRecDobleTitu2);

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
                CalcularPreciosTestUtils.crearMatriculaAsignaturaReconocimientoDoble(pmatAlumnoInscripcion, asignatura1, annusMat);
        inscripcionAsignatura1 = CalcularPreciosTestUtils.calcularPrecioMatriculaAsignatura(pmatAlumnoInscripcion, inscripcionAsignatura1);

        // Cálculo precios asignatura 2
        Asignatura asignatura2 = AsignaturaMother.createObligatoria(titu2, BigDecimal.valueOf(4));
        PmatAlumnoInscripcionAsignatura inscripcionAsignatura2 =
                CalcularPreciosTestUtils.crearMatriculaAsignaturaReconocimientoDoble(pmatAlumnoInscripcion, asignatura2, annusMat);
        inscripcionAsignatura2 = CalcularPreciosTestUtils.calcularPrecioMatriculaAsignatura(pmatAlumnoInscripcion, inscripcionAsignatura2);

        // Comprobación resultados
        BigDecimal resultadoPrecioAsignatura1 = BigDecimal.valueOf(90.0);
        BigDecimal resultadoPrecioAsignatura2 = BigDecimal.valueOf(48.0);

        Assertions.assertEquals(resultadoPrecioAsignatura1.doubleValue(), inscripcionAsignatura1.getPrecio().doubleValue(),
                CalcularPreciosTestUtils._TOLERANCIA);
        Assertions.assertEquals(inscripcionAsignatura1.getIdTipo().getIdTipo(), PmatAlumnoTipoMatriculaAsignatura.MATRICULA_TIPO_ASIGNATURA_DOBLE_GRADO);
        Assertions.assertNull(inscripcionAsignatura1.getGrupoSimple());

        Assertions.assertEquals(resultadoPrecioAsignatura2.doubleValue(), inscripcionAsignatura2.getPrecio().doubleValue(),
                CalcularPreciosTestUtils._TOLERANCIA);
        Assertions.assertEquals(inscripcionAsignatura2.getIdTipo().getIdTipo(), PmatAlumnoTipoMatriculaAsignatura.MATRICULA_TIPO_ASIGNATURA_DOBLE_GRADO);
        Assertions.assertNull(inscripcionAsignatura2.getGrupoSimple());

        Assertions.assertTrue(pmatAlumnoInscripcion.isPreciosPorPce());
    }

}