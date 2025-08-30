package matricula.unit.test.economico;

import matricula.dao.acad.ExpedienteFacade;
import matricula.dao.acad.FestivoFacade;
import matricula.dao.adm.PadmCandidatoLiquidacionFacade;
import matricula.dao.eco.LiquidacionFacade;
import matricula.dao.eco.LiquidacionPagotpvFacade;
import matricula.dao.mat.PmatAlumnoInscripcionFacade;
import matricula.ejb.economico.AplicarBonificacionesIndividualBean;
import matricula.ejb.economico.LiquidacionesBean;
import matricula.ejb.genericos.SistemaBean;
import matricula.unit.mother.model.acad.CentroUniversitarioMother;
import matricula.unit.mother.model.acad.estudios.TitulacionMother;
import matricula.unit.mother.model.crm.AlumnoMother;
import matricula.unit.mother.model.crm.facilities.CampusMother;
import matricula.unit.mother.model.expe.ExpedienteMother;
import matricula.unit.mother.model.mat.BonificacionMother;
import matricula.unit.mother.model.mat.PmatAlumnoDatosEconomicosMother;
import matricula.unit.mother.model.mat.PmatAlumnoInscripcionMother;
import matricula.unit.mother.model.mat.ProcesoMatriculaMother;
import model.acad.CentroUniversitario;
import model.acad.estudios.Titulacion;
import model.adm.GruposTipoTitulacion;
import model.dbo.CursoAcademico;
import model.dbo.Sistema;
import model.eco.liquidaciones.Liquidacion;
import model.eco.liquidaciones.Sufijo;
import model.expe.Expediente;
import model.mat.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import utils.FechaUtil;

import java.math.BigDecimal;
import java.util.Date;

import static org.mockito.Mockito.when;

/** @author amuro */
@ExtendWith(MockitoExtension.class)
public class LiquidacionesBeanTest {

    @Mock
    private LiquidacionFacade liquidacionFacade;
    @Mock
    private SistemaBean sistemaBean;
    @Mock
    private FestivoFacade festivoFacade;
    @Mock
    private ExpedienteFacade expedienteFacade;
    @Mock
    private PadmCandidatoLiquidacionFacade candidatoLiquidacionFacade;
    @Mock
    private LiquidacionPagotpvFacade liquidacionPagotpvFacade;
    @Mock
    private PmatAlumnoInscripcionFacade alumnoInscripcionFacade;
    @Mock
    private AplicarBonificacionesIndividualBean aplicarBonificacionesIndividualBean;

    private LiquidacionesBean sut;

    @Test
    void crearLiquidacion2MatriculaDECATest() {

        BigDecimal importeCreditos = BigDecimal.valueOf(549.9);
        BigDecimal importeLiquidacion = BigDecimal.valueOf(274.95);
        BigDecimal importeLiquidacion2 = BigDecimal.valueOf(219.95);
        // given
        String emisora = "14894158";
        Sistema sistema = new Sistema();
        sistema.setCursoAcademico("2022/23");
        Date fechaEmision = FechaUtil.setFirstMinuteOfDay(FechaUtil.toFechaDate("06/02/2023", "dd/MM/yyyy"));
        CursoAcademico curso = new CursoAcademico(sistema.getCursoAcademico());

        GruposTipoTitulacion colectivo = new GruposTipoTitulacion(GruposTipoTitulacion.DECA_MAGISTERIO);

        CentroUniversitario centro = CentroUniversitarioMother.ramdon(emisora);
        Titulacion titulacion = TitulacionMother.randomWithCentro(centro);
        Expediente expediente = ExpedienteMother.create(
                AlumnoMother.create(), CampusMother.createCampusRealCordoba(), titulacion, true);

        PmatAlumnoDatosEconomicos alumnoDatosEconomicos = PmatAlumnoDatosEconomicosMother.createDeca(BigDecimal.ZERO,
                importeCreditos);
        Liquidacion liquidacionMatricula = Liquidacion.generarLiquidacionConCaducidad(
                emisora,
                new Sufijo(Sufijo.MATRICULA_CADUCIDAD),
                importeLiquidacion,
                curso,
                fechaEmision,
                fechaEmision,
                expediente);
        liquidacionMatricula.setLiquidacionID(10);

        PmatAlumnoInscripcion alumnoInscripcion = PmatAlumnoInscripcionMother.createDeca(
                expediente,
                alumnoDatosEconomicos,
                ProcesoMatriculaMother.create(curso),
                colectivo,
                liquidacionMatricula);
        Bonificacion bonificacionAlumnoLoyola = BonificacionMother.createBonificacionCreditos(
                expediente, BigDecimal.TEN, BonificacionTipo.ALUMNO_DECA, BonificacionEstado.CONCEDIDA);

        sut = new LiquidacionesBean(
                liquidacionFacade,
                sistemaBean,
                festivoFacade,
                expedienteFacade,
                candidatoLiquidacionFacade,
                liquidacionPagotpvFacade,
                alumnoInscripcionFacade,
                aplicarBonificacionesIndividualBean);

        Liquidacion liquidacionConID = Liquidacion.generarLiquidacionSinCaducidad(
                emisora,
                new Sufijo(Sufijo.SUFIJO_PAGO_APLAZADO_MENSUALIDADES),
                importeLiquidacion2,
                curso,
                fechaEmision,
                expediente);
        liquidacionConID.setLiquidacionID(10);

        // when
        when(liquidacionFacade.find(null)).thenReturn(liquidacionConID);
        when(liquidacionFacade.find(liquidacionConID.getLiquidacionID())).thenReturn(liquidacionConID);
        when(aplicarBonificacionesIndividualBean.calcularImporteBonificableCreditos(
                Mockito.any(), Mockito.any()))
                .thenReturn(BigDecimal.valueOf(55));

        Liquidacion resultado = sut.crearLiquidacion2MatriculaDECA(alumnoInscripcion, bonificacionAlumnoLoyola);

        // then
        String digitoControl = "15";
        Assertions.assertEquals(
                "9050714894158"
                        + liquidacionConID.getSufijoFK().getSufijoID()
                        + ""
                        + liquidacionConID.getSufijoFK().getSufijoID()
                        + "00000010"
                        + digitoControl
                        + FechaUtil.toFechaStr(fechaEmision, "yyMMdd")
                        + "00000219950",
                resultado.getBarcode());
    }

    /*
     * @Test
     * void calcularImporteSegundoPagoDECATest() {
     * // given
     * double importeTotalDeuda = 649.92;
     * double importeLiquidacion1 = 324.96;
     * double importeLiquidacion2 = 259.93;
     * double importeBonificacion = 65.03;
     * 
     * Sistema sistema = new Sistema();
     * sistema.setCursoAcademico("2022/23");
     * CursoAcademico curso = new CursoAcademico(sistema.getCursoAcademico());
     * Titulacion titulacion = TitulacionMother.random();
     * Expediente expediente =
     * ExpedienteMother.create(
     * AlumnoMother.create(), CampusMother.createCampusRealCordoba(), titulacion,
     * true);
     * 
     * PmatAlumnoDatosEconomicos alumnoDatosEconomicos =
     * PmatAlumnoDatosEconomicosMother.createDeca(
     * BigDecimal.ZERO, BigDecimal.valueOf(importeTotalDeuda));
     * 
     * List<PmatAlumnoInscripcionAsignatura> listaAsig =
     * createInscripcionAsignaturaList();
     * PmatAlumnoInscripcion alumnoInscripcion =
     * PmatAlumnoInscripcionMother.createDeca(
     * expediente, alumnoDatosEconomicos, ProcesoMatriculaMother.create(curso),
     * null);
     * 
     * BigDecimal importe = BigDecimal.valueOf(importeLiquidacion1);
     * String emisora = "14894158";
     * Date fechaEmision =
     * FechaUtil.setFirstMinuteOfDay(FechaUtil.toFechaDate("06/02/2023",
     * "dd/MM/yyyy"));
     * Liquidacion liquidacionConID =
     * Liquidacion.generarLiquidacionSinCaducidad(
     * emisora,
     * new Sufijo(Sufijo.SUFIJO_PAGO_APLAZADO_MENSUALIDADES),
     * importe,
     * curso,
     * fechaEmision,
     * expediente);
     * liquidacionConID.setLiquidacionID(10);
     * 
     * alumnoInscripcion.setLiquidacionFK(liquidacionConID);
     * 
     * Bonificacion bonificacionAlumnoLoyola =
     * BonificacionMother.createBonificacionCreditos(
     * expediente, BigDecimal.TEN, BonificacionTipo.ALUMNO_DECA,
     * BonificacionEstado.CONCEDIDA);
     * ArrayList<Bonificacion> listaBonificacion = new ArrayList<>();
     * listaBonificacion.add(bonificacionAlumnoLoyola);
     * 
     * sut =
     * new LiquidacionesBean(
     * liquidacionFacade,
     * sistemaBean,
     * festivoFacade,
     * expedienteFacade,
     * candidatoLiquidacionFacade,
     * liquidacionPagotpvFacade,
     * alumnoInscripcionFacade);
     * 
     * // when
     * 
     * when(bonificacionBean.getBonificacionesPorExpedienteCursoYTipo(
     * expediente, curso, BonificacionTipo.ALUMNO_DECA))
     * .thenReturn(listaBonificacion);
     * when(aplicarBonificacionesIndividualBean.calcularImporteBonificableCreditos(
     * Mockito.any(), Mockito.any()))
     * .thenReturn(BigDecimal.valueOf(importeBonificacion));
     * 
     * BigDecimal resultado =
     * sut.calcularImporteSegundoPagoDECAPublic(alumnoInscripcion);
     * 
     * // then
     * Assertions.assertEquals(BigDecimal.valueOf(importeLiquidacion2), resultado);
     * }
     */
    /*
     * static List<PmatAlumnoInscripcionAsignatura>
     * createInscripcionAsignaturaList() {
     * List<PmatAlumnoInscripcionAsignatura> resultado = new ArrayList<>();
     * resultado.add(PmatAlumnoInscripcionAsignaturaMother.
     * createInscripcionAsignatura(81.24));
     * resultado.addAll(PmatAlumnoInscripcionAsignaturaMother.
     * createInscripcionAsignaturas(10, 54.16));
     * resultado.add(PmatAlumnoInscripcionAsignaturaMother.
     * createInscripcionAsignatura(27.08));
     * return resultado;
     * }
     */
}
