package matricula.unit.test.economico;

import dto.matricula.MatriculaPreciosHelper;
import matricula.unit.mother.model.acad.estudios.AsignaturaMother;
import matricula.unit.mother.model.acad.estudios.TitulacionMother;
import matricula.unit.mother.model.expe.ExpedienteMother;
import matricula.unit.mother.model.mat.ProcesoMatriculaMother;
import model.acad.estudios.Asignatura;
import model.acad.estudios.Titulacion;
import model.acad.planif.GrupoSimple;
import model.adm.GruposTipoTitulacion;
import model.dbo.CursoAcademico;
import model.expe.Expediente;
import model.mat.InscripcionAsignaturaExpedienteException;
import model.mat.PmatAlumnoInscripcion;
import model.mat.PmatAlumnoInscripcionAsignatura;
import model.mat.PmatAlumnoTipoMatriculaAsignatura;
import model.mat.precios.MatriculaPreciosMatricula;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

@ExtendWith(MockitoExtension.class)
public class CalcularPreciosReconocimientoExternoTest {

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
    void testRecExternoPrimerCurso()
            throws java.sql.SQLTransactionRollbackException, InscripcionAsignaturaExpedienteException {
        CursoAcademico curso = CalcularPreciosTestUtils.cursoAcademico;

        Titulacion titulacion = TitulacionMother.create(201, "ECO", true);
        Expediente expediente = ExpedienteMother.create(titulacion, true);

        BigDecimal importeRecExterno = BigDecimal.valueOf(90);
        BigDecimal porcentajeRecExternoPrimerCurso = BigDecimal.valueOf(10);

        MatriculaPreciosMatricula preciosMatricula = CalcularPreciosTestUtils
                .crearPreciosReconocimientoExterno(curso, titulacion, importeRecExterno, porcentajeRecExternoPrimerCurso);

        preciosHelper = MatriculaPreciosHelper.of(curso, preciosMatricula,
                CalcularPreciosTestUtils.importeMatricula, CalcularPreciosTestUtils.importeReservaAdmision);

        pmatAlumnoInscripcion = PmatAlumnoInscripcion.prepareInscripcionReglada(
                ProcesoMatriculaMother.create(curso), new GruposTipoTitulacion(GruposTipoTitulacion.GRADO_MENOSDE_60CREDS_SUPERADOS),
                expediente, preciosHelper);

        Integer annusMat = 1;
        Integer cursoAsignatura = 1;

        // Cálculo precios asignatura
        Asignatura asignatura = AsignaturaMother.createObligatoria(titulacion, BigDecimal.valueOf(3));
        PmatAlumnoInscripcionAsignatura inscripcionAsignatura = CalcularPreciosTestUtils
                .crearMatriculaAsignaturaReconocimientoExterno(pmatAlumnoInscripcion, asignatura, cursoAsignatura, annusMat);
        inscripcionAsignatura = CalcularPreciosTestUtils.calcularPrecioMatriculaAsignatura(pmatAlumnoInscripcion, inscripcionAsignatura);

        // Comprobación resultados
        BigDecimal resultadoPrecioRecExterno = BigDecimal.valueOf(27);

        Assertions.assertEquals(resultadoPrecioRecExterno.doubleValue(), inscripcionAsignatura.getPrecio().doubleValue(),
                CalcularPreciosTestUtils._TOLERANCIA);
        Assertions.assertEquals(inscripcionAsignatura.getIdTipo().getIdTipo(), PmatAlumnoTipoMatriculaAsignatura.MATRICULA_TIPO_ASIGNATURA_EXTERNO);
        Assertions.assertNull(inscripcionAsignatura.getGrupoSimple());
    }

    @Test
    void testRecExterno()
            throws java.sql.SQLTransactionRollbackException, InscripcionAsignaturaExpedienteException {
        CursoAcademico curso = CalcularPreciosTestUtils.cursoAcademico;

        Titulacion titulacion = TitulacionMother.create(201, "ECO", true);
        Expediente expediente = ExpedienteMother.create(titulacion, true);

        BigDecimal importeRecExterno = BigDecimal.valueOf(90);
        BigDecimal porcentajeRecExternoPrimerCurso = BigDecimal.valueOf(10);

        MatriculaPreciosMatricula preciosMatricula = CalcularPreciosTestUtils
                .crearPreciosReconocimientoExterno(curso, titulacion, importeRecExterno, porcentajeRecExternoPrimerCurso);

        preciosHelper = MatriculaPreciosHelper.of(curso, preciosMatricula,
                CalcularPreciosTestUtils.importeMatricula, CalcularPreciosTestUtils.importeReservaAdmision);

        pmatAlumnoInscripcion = PmatAlumnoInscripcion.prepareInscripcionReglada(
                ProcesoMatriculaMother.create(curso), new GruposTipoTitulacion(GruposTipoTitulacion.GRADO_RENOVACION),
                expediente, preciosHelper);

        Integer annusMat = 1;
        Integer cursoAsignatura = 2;

        // Cálculo precios asignatura
        Asignatura asignatura = AsignaturaMother.createObligatoria(titulacion, BigDecimal.valueOf(3));
        PmatAlumnoInscripcionAsignatura inscripcionAsignatura = CalcularPreciosTestUtils
                .crearMatriculaAsignaturaReconocimientoExterno(pmatAlumnoInscripcion, asignatura, cursoAsignatura, annusMat);
        inscripcionAsignatura = CalcularPreciosTestUtils.calcularPrecioMatriculaAsignatura(pmatAlumnoInscripcion, inscripcionAsignatura);

        // Comprobación resultados
        BigDecimal resultadoPrecioRecExterno = BigDecimal.valueOf(270);

        Assertions.assertEquals(resultadoPrecioRecExterno.doubleValue(), inscripcionAsignatura.getPrecio().doubleValue(),
                CalcularPreciosTestUtils._TOLERANCIA);
        Assertions.assertEquals(inscripcionAsignatura.getIdTipo().getIdTipo(), PmatAlumnoTipoMatriculaAsignatura.MATRICULA_TIPO_ASIGNATURA_EXTERNO);
        Assertions.assertNull(inscripcionAsignatura.getGrupoSimple());
    }

}