/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package matricula.unit.test.economico;

import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import matricula.ejb.economico.AplicarBonificacionesIndividualBean;
import matricula.ejb.matricula.InscripcionMatriculaBean;
import matricula.unit.mother.model.acad.estudios.TitulacionMother;
import matricula.unit.mother.model.crm.AlumnoMother;
import matricula.unit.mother.model.crm.facilities.CampusMother;
import matricula.unit.mother.model.expe.ExpedienteMother;
import matricula.unit.mother.model.mat.BonificacionMother;
import matricula.unit.mother.model.mat.PmatAlumnoDatosEconomicosMother;
import matricula.unit.mother.model.mat.PmatAlumnoInscripcionAsignaturaMother;
import matricula.unit.mother.model.mat.PmatAlumnoInscripcionMother;
import matricula.unit.mother.model.mat.ProcesoMatriculaMother;
import model.acad.estudios.Titulacion;
import model.adm.GruposTipoTitulacion;
import model.dbo.CursoAcademico;
import model.dbo.Sistema;
import model.expe.Expediente;
import model.mat.Bonificacion;
import model.mat.BonificacionEstado;
import model.mat.BonificacionTipo;
import model.mat.PmatAlumnoDatosEconomicos;
import model.mat.PmatAlumnoInscripcion;
import model.mat.PmatAlumnoInscripcionAsignatura;

/** @author amuro */
@ExtendWith(MockitoExtension.class)
public class AplicarBonificacionesIndividualBeanTest {

    private AplicarBonificacionesIndividualBean sut;

    @Mock
    private InscripcionMatriculaBean inscripcionMatriculaBean;

    @Test
    void testCalcularImporteBonficableCreditos() {
        // given

        /*
         * Sistema sistema = new Sistema();
         * sistema.setCursoAcademico("2022/23");
         * Date fechaEmision =
         * FechaUtil.setFirstMinuteOfDay(FechaUtil.toFechaDate("06/02/2023",
         * "dd/MM/yyyy"));
         * CursoAcademico curso = new CursoAcademico(sistema.getCursoAcademico());
         */
        Sistema sistema = new Sistema();
        sistema.setCursoAcademico("2022/23");
        CursoAcademico curso = new CursoAcademico(sistema.getCursoAcademico());
        Titulacion titulacion = TitulacionMother.random();
        GruposTipoTitulacion colectivo = new GruposTipoTitulacion(GruposTipoTitulacion.DECA_MAGISTERIO);
        Expediente expediente = ExpedienteMother.create(
                AlumnoMother.create(), CampusMother.createCampusRealCordoba(), titulacion, true);

        PmatAlumnoDatosEconomicos alumnoDatosEconomicos = PmatAlumnoDatosEconomicosMother.createDeca(BigDecimal.ZERO,
                BigDecimal.ZERO);


        PmatAlumnoInscripcion alumnoInscripcion = PmatAlumnoInscripcionMother.createDeca(
                expediente, alumnoDatosEconomicos, ProcesoMatriculaMother.create(curso), colectivo, null);

        alumnoInscripcion.setPmatAlumnoInscripcionAsignaturasList(createInscripcionAsignaturaList());

        Bonificacion bonificacionAlumnoLoyola = BonificacionMother.createBonificacionCreditos(
                expediente, BigDecimal.TEN, BonificacionTipo.ALUMNO_DECA, BonificacionEstado.CONCEDIDA);
        // BigDecimal importeSegundoPago = BigDecimal.ZERO;
        sut = new AplicarBonificacionesIndividualBean();

        // when

       /* when(inscripcionMatriculaBean.listarAsignaturasInscritasVigentesCalcularCosteCurso(Mockito.any()))
                .thenReturn(listaAsig);*/

        BigDecimal resultado = sut.calcularImporteBonificableCreditos(alumnoInscripcion, bonificacionAlumnoLoyola);

        Assertions.assertEquals(BigDecimal.valueOf(65.03), resultado);
    }

    static List<PmatAlumnoInscripcionAsignatura> createInscripcionAsignaturaList() {
        List<PmatAlumnoInscripcionAsignatura> resultado = new ArrayList<>();
        resultado.add(PmatAlumnoInscripcionAsignaturaMother.createInscripcionAsignatura(81.24));
        resultado.addAll(PmatAlumnoInscripcionAsignaturaMother.createInscripcionAsignaturas(10, 54.16));
        resultado.add(PmatAlumnoInscripcionAsignaturaMother.createInscripcionAsignatura(27.08));
        return resultado;
    }
}
