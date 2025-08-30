/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package matricula.unit.test.matricula;

import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import matricula.dao.acad.GrupoSimplePlazasOcupadasFacade;
import matricula.ejb.matricula.ComprobarPlazasGrupoBean;
import matricula.unit.mother.model.acad.planif.GrupoSimpleMother;
import matricula.unit.mother.model.acad.planif.GrupoSimplePlazasOcupadasMother;
import model.acad.planif.GrupoSimple;
import model.acad.planif.GrupoSimplePlazasOcupadas;

/**
 * @author amuro
 */
@ExtendWith(MockitoExtension.class)
public class ComprobarPlazasGrupoTest {

  private ComprobarPlazasGrupoBean sut;

  @Mock
  private GrupoSimplePlazasOcupadasFacade grupoSimplePlazasOcupadasFacade;

  @ParameterizedTest(name = "{index} {arguments}")
  @CsvSource({
      // nuevo=TRUE gs_reserva 10 ,gs_nuevos 5, gs2_nuevos 5,
      // plazas=gs_reserva-gs_nuevos
      "TRUE, 10, 10, 5, 5, FALSE, 5, 10, 0, 0, TRUE, 5, 0, 0, 0, TRUE, 0, 0, 0, 0, 20, 0, 0,  5",
      // nuevo=TRUE gs_reserva 10 ,gs_nuevos 5, gs2_nuevos 5,
      // plazas=gs_reserva-gs_nuevos
      "TRUE, 10, 10, 10, 10, FALSE, 10, 15, 0, 0, TRUE, 5, 0, 0, 0, TRUE, 0, 0, 0, 0, 20, 0, 0,  0",
      // nuevo=FALSE gs_reserva 10 ,gs_nuevos 5, gm_nuevos 10, gs_renovacion=5
      // plazas_GM=TOTALES-gs_resrva-gs2_nuevos-gm_renovacion
      "FALSE, 10, 10, 5, 5, FALSE, 5, 10, 0, 0, TRUE, 5, 0, 0, 0, TRUE, 0, 0, 0, 0, 20, 0, 0, 0",
      // nuevo=TRUE gs_reserva 10 ,gs_nuevos 11, gs2_nuevos 5,
      // plazas=TOTALES-gm_reserva
      "TRUE, 10, 10, 11, 11, FALSE, 11, 16, 0, 0, TRUE, 4, 4, 0, 0, TRUE, 0, 0, 0, 0, 20, 0, 0, 0",
      // nuevo=FALSE gs_reserva 10 ,gs_nuevos 11, gs2_nuevos 5,
      // plazas=TOTALES-gm_reserva
      "FALSE, 10, 10, 11, 11, FALSE, 11, 16, 0, 0, TRUE, 4, 4, 0, 0, TRUE, 0, 0, 0, 0, 20, 0, 0, 0",
      // nuevo=TRUE gs_reserva 10 ,gs2_nuevos 5, gs2_renovacion 5,
      // plazas=gs_reserva-gs_nuevos
      "TRUE, 10, 10, 0, 0, FALSE, 0, 5, 0, 0, TRUE, 0, 5, 0, 0, TRUE, 0, 0, 0, 0, 20, 0, 0, 10",
      // nuevo=FALSE gs_reserva 10 , gs_renovacion 5, plazas=TOTAL-gm_reserva-
      "FALSE, 10, 10, 0, 0, FALSE, 0, 5, 0, 0, TRUE, 0, 5, 0, 0, TRUE, 0, 0, 0, 0, 20, 0, 0, 0",
  })
  void checkGrupoSimpleReservaPlaza(
      boolean asignaturaPrimeraMatricula,
      int plazas_reservadas_nuevos_gs,
      int plazas_reservadas_nuevos_gm,
      int plazas_reservadas_nuevos_gs_ocupadas,
      int plazas_reservadas_nuevos_gm_ocupadas,
      boolean es_piia,
      int plazas_nuevos_gs,
      int plazas_nuevos_gm,
      int plazas_nuevos_gs_bloqueo,
      int plazas_nuevos_gm_bloqueo,
      boolean control_plazas,
      int plazas_renovacion_gs,
      int plazas_renovacion_gm,
      int plazas_renovacion_gs_bloqueo,
      int plazas_renovacion_gm_bloqueo,
      boolean control_plazas_extendido,
      int plazas_piia_gs,
      int plazas_piia_gm,
      int plazas_piia_gs_bloqueo,
      int plazas_piia_gm_bloqueo,
      int n_plazas_max,
      int n_plazas_piia_max,
      int n_plazas_rep_max,
      Integer plazasRestantes) {

    // given
    sut = new ComprobarPlazasGrupoBean(grupoSimplePlazasOcupadasFacade);
    GrupoSimplePlazasOcupadas gsPlazas = GrupoSimplePlazasOcupadasMother.create(
        es_piia,
        plazas_reservadas_nuevos_gs,
        plazas_reservadas_nuevos_gm,
        plazas_reservadas_nuevos_gs_ocupadas,
        plazas_reservadas_nuevos_gm_ocupadas,
        plazas_nuevos_gs,
        plazas_nuevos_gm,
        plazas_nuevos_gs_bloqueo,
        plazas_nuevos_gm_bloqueo,
        control_plazas,
        plazas_renovacion_gs,
        plazas_renovacion_gm,
        plazas_renovacion_gs_bloqueo,
        plazas_renovacion_gm_bloqueo,
        control_plazas_extendido,
        plazas_piia_gs,
        plazas_piia_gm,
        plazas_piia_gs_bloqueo,
        plazas_piia_gm_bloqueo,
        n_plazas_max,
        n_plazas_piia_max,
        n_plazas_rep_max);

    GrupoSimple gs = GrupoSimpleMother.createGrupoSimple(1);
    // when
    when(grupoSimplePlazasOcupadasFacade.find(Mockito.any())).thenReturn(gsPlazas);
    GrupoSimplePlazasOcupadas gspo = sut.getConfiguracionPlazas(gs);

    Integer resultado = gspo.getPlazasDisponibles(asignaturaPrimeraMatricula);
    Assertions.assertEquals(
        plazasRestantes, resultado);
    Assertions.assertEquals(
        plazasRestantes > -1 && plazasRestantes != 0,
        gspo.isPlazasDisponibles(asignaturaPrimeraMatricula));
  }

  @ParameterizedTest(name = "{index} {arguments}")
  @CsvSource({
      // PIIA
      // PIIA=TRUE plazas_totales 20 ,plazas_piia 5,
      // plazas=gm_plazas_piia-gm_plazas_piia_ocupadas
      "TRUE, TRUE, TRUE, 0, 0, 0, 0, 20, 5, 5",
      // PIIA=TRUE gs_piia_ocupadas 5, plazas_totales 20, plazas_piia 5,
      // plazas=gm_plazas_piia-gm_plazas_piia_ocupadas
      "TRUE, TRUE, TRUE, 5, 5, 0, 0, 20, 5, 0",
      // PIIA=TRUE, EXTENDIDO=false, plazas_totales 20, plazas=totales
      "TRUE, TRUE, FALSE, 0, 0, 0, 0, 20, 0, 20",
      // PIIA=TRUE, EXTENDIDO=false, gs_piia_ocupadas 5, plazas_totales 20,
      // plazas=totales-gm_piia_ocupadas
      "TRUE, TRUE, FALSE, 5, 5, 0, 0, 20, 0, 15",
      // PIIA=TRUE, CONTROL = FALSE, gs_piia_ocupadas 5, plazas_totales 0, plazas=99
      "TRUE, FALSE, FALSE, 5, 5, 0, 0, 0, 0, 99",
  })
  void checkGrupoSimpleReservaPlazaPIIA(
      boolean es_piia,
      boolean control_plazas,
      boolean control_plazas_extendido,
      int plazas_piia_gs,
      int plazas_piia_gm,
      int plazas_piia_gs_bloqueo,
      int plazas_piia_gm_bloqueo,
      int n_plazas_max,
      int n_plazas_piia_max,
      Integer plazasRestantes) {

    // given
    sut = new ComprobarPlazasGrupoBean(grupoSimplePlazasOcupadasFacade);
    GrupoSimplePlazasOcupadas gsPlazas = GrupoSimplePlazasOcupadasMother.createPiia(
        es_piia,
        control_plazas,
        control_plazas_extendido,
        plazas_piia_gs,
        plazas_piia_gm,
        plazas_piia_gs_bloqueo,
        plazas_piia_gm_bloqueo,
        n_plazas_max,
        n_plazas_piia_max);

    GrupoSimple gs = GrupoSimpleMother.createGrupoSimple(1);
    // when
    when(grupoSimplePlazasOcupadasFacade.find(Mockito.any())).thenReturn(gsPlazas);
    GrupoSimplePlazasOcupadas resultado = sut.getConfiguracionPlazas(gs);

    Assertions.assertEquals(plazasRestantes, resultado.getPlazasDisponibles(Boolean.FALSE));
    Assertions.assertEquals(
        plazasRestantes > -1 && plazasRestantes != 0, resultado.isPlazasDisponibles(Boolean.FALSE));
  }

  @ParameterizedTest(name = "{index} {arguments}")
  @CsvSource({
      // incidencia matricula 2024/25
      "TRUE, , , 0, 0, FALSE, 4, 4, 1, 1, TRUE, 0, 0, 0, 0, TRUE, 0, 0, 0, 0, 66, 0, 0, 61",
      // incidencia matricula 2024/25
      "TRUE, , , 0, 0, FALSE, 4, 4, 1, 1, TRUE, 0, 0, 0, 0, FALSE, 0, 0, 0, 0, 66, 0, 0, 61",
      // incidencia matricula 2024/25
      "TRUE, , , 0, 0, FALSE, 4, 4, 1, 1, TRUE, 0, 0, 0, 0, FALSE, 0, 0, 0, 0, 5, 0, 0, 0",
  })
  void checkGrupoSimpleReservaPlazaNuevoIngreso_24_25(
      boolean asignaturaPrimeraMatricula,
      Integer plazas_reservadas_nuevos_gs,
      Integer plazas_reservadas_nuevos_gm,
      int plazas_reservadas_nuevos_gs_ocupadas,
      int plazas_reservadas_nuevos_gm_ocupadas,
      boolean es_piia,
      int plazas_nuevos_gs,
      int plazas_nuevos_gm,
      int plazas_nuevos_gs_bloqueo,
      int plazas_nuevos_gm_bloqueo,
      boolean control_plazas,
      int plazas_renovacion_gs,
      int plazas_renovacion_gm,
      int plazas_renovacion_gs_bloqueo,
      int plazas_renovacion_gm_bloqueo,
      boolean control_plazas_extendido,
      int plazas_piia_gs,
      int plazas_piia_gm,
      int plazas_piia_gs_bloqueo,
      int plazas_piia_gm_bloqueo,
      int n_plazas_max,
      int n_plazas_piia_max,
      int n_plazas_rep_max,
      Integer plazasRestantes) {

    // given
    sut = new ComprobarPlazasGrupoBean(grupoSimplePlazasOcupadasFacade);
    GrupoSimplePlazasOcupadas gsPlazas = GrupoSimplePlazasOcupadasMother.create(
        es_piia,
        plazas_reservadas_nuevos_gs,
        plazas_reservadas_nuevos_gm,
        plazas_reservadas_nuevos_gs_ocupadas,
        plazas_reservadas_nuevos_gm_ocupadas,
        plazas_nuevos_gs,
        plazas_nuevos_gm,
        plazas_nuevos_gs_bloqueo,
        plazas_nuevos_gm_bloqueo,
        control_plazas,
        plazas_renovacion_gs,
        plazas_renovacion_gm,
        plazas_renovacion_gs_bloqueo,
        plazas_renovacion_gm_bloqueo,
        control_plazas_extendido,
        plazas_piia_gs,
        plazas_piia_gm,
        plazas_piia_gs_bloqueo,
        plazas_piia_gm_bloqueo,
        n_plazas_max,
        n_plazas_piia_max,
        n_plazas_rep_max);

    GrupoSimple gs = GrupoSimpleMother.createGrupoSimple(1);
    // when
    when(grupoSimplePlazasOcupadasFacade.find(Mockito.any())).thenReturn(gsPlazas);
    GrupoSimplePlazasOcupadas gspo = sut.getConfiguracionPlazas(gs);

    Integer resultado = gspo.getPlazasDisponibles(asignaturaPrimeraMatricula);
    Assertions.assertEquals(plazasRestantes, resultado);
    Assertions.assertEquals(
        plazasRestantes > -1 && plazasRestantes != 0,
        gspo.isPlazasDisponibles(asignaturaPrimeraMatricula));
  }
}
