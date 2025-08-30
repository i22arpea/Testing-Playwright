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
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

@ExtendWith(MockitoExtension.class)
public class CalcularPreciosAsignaturaPMITest {

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

        Titulacion titulacion = TitulacionMother.create(201, "ECO", true);
        Expediente expediente = ExpedienteMother.create(titulacion, true);

        BigDecimal importeCredito = BigDecimal.valueOf(90);
        Integer porcentajeCreditoPIC = Integer.valueOf(50);

        MatriculaPreciosMatricula preciosMatricula = CalcularPreciosTestUtils
                .crearPreciosPMI(curso, titulacion, importeCredito, porcentajeCreditoPIC);

        preciosHelper = MatriculaPreciosHelper.of(curso, preciosMatricula,
                CalcularPreciosTestUtils.importeMatricula, CalcularPreciosTestUtils.importeReservaAdmision);

        pmatAlumnoInscripcion = PmatAlumnoInscripcion.prepareInscripcionReglada(
                ProcesoMatriculaMother.create(curso), new GruposTipoTitulacion(GruposTipoTitulacion.GRADO_RENOVACION),
                expediente, preciosHelper);

        Integer annusMat = 1;

        // Cálculo precios asignatura
        Asignatura asignatura = AsignaturaMother.createPMI(BigDecimal.valueOf(18));
        PmatAlumnoInscripcionAsignatura inscripcionAsignatura = CalcularPreciosTestUtils
                .crearMatriculaAsignaturaPMI(pmatAlumnoInscripcion, asignatura, expediente, annusMat);
        inscripcionAsignatura = CalcularPreciosTestUtils.calcularPrecioMatriculaAsignatura(pmatAlumnoInscripcion, inscripcionAsignatura);

        // Comprobación resultados
        BigDecimal resultadoPrecioPIC = BigDecimal.valueOf(810);

        Assertions.assertEquals(resultadoPrecioPIC.doubleValue(), inscripcionAsignatura.getPrecio().doubleValue(),
                CalcularPreciosTestUtils._TOLERANCIA);
        Assertions.assertEquals(inscripcionAsignatura.getIdTipo().getIdTipo(), PmatAlumnoTipoMatriculaAsignatura.MATRICULA_TIPO_ASIGNATURA_PIC);
        Assertions.assertNull(inscripcionAsignatura.getGrupoSimple());
    }

}