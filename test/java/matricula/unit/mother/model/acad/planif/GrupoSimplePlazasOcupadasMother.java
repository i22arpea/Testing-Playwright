/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package matricula.unit.mother.model.acad.planif;

import model.acad.planif.GrupoSimplePlazasOcupadas;

/**
 * @author amuro
 */
public class GrupoSimplePlazasOcupadasMother {

  public static GrupoSimplePlazasOcupadas createPiia(
      boolean es_piia,
      boolean control_plazas,
      boolean control_plazas_extendido,
      int plazas_piia_gs,
      int plazas_piia_gm,
      int plazas_piia_gs_bloqueo,
      int plazas_piia_gm_bloqueo,
      int n_plazas_max,
      int n_plazas_piia_max) {
    return GrupoSimplePlazasOcupadas.builder()
        .idGrupoSimple(1)
        .idGrupoMixto(1)
        .esPiia(es_piia)
        .plazasReservadasNuevosGrupoMixto(0)
        .plazasReservadasNuevosGrupoMixtoOcupadas(0)
        .plazasNuevosGrupoMixtoBloqueo(0)
        .plazasNuevosGrupoMixto(0)
        .plazasNuevosGrupoMixtoBloqueo(0)
        .plazasRenovacionGrupoMixto(0)
        .plazasRenovacionGrupoMixtoBloqueo(0)
        .controlPlazas(control_plazas)
        .controlPlazasExtendido(control_plazas_extendido)
        .plazasPiiaGrupoSimple(plazas_piia_gs)
        .plazasPiiaGrupoMixto(plazas_piia_gm)
        .plazasPiiaGrupoSimpleBloqueo(plazas_piia_gs_bloqueo)
        .plazasPiiaGrupoMixtoBloqueo(plazas_piia_gm_bloqueo)
        .grupoMixtoNumeroPlazasMax(n_plazas_max)
        .grupoMixtoNumeroPlazasPiiaMax(n_plazas_piia_max)
        .build();
  }

  public static GrupoSimplePlazasOcupadas create(
      boolean es_piia,
      Integer plazas_reservadas_nuevos_gs,
      Integer plazas_reservadas_nuevos_gm,
      int plazas_reservadas_nuevos_gs_ocupadas,
      int plazas_reservadas_nuevos_gm_ocupadas,
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
      int n_plazas_rep_max) {
    return GrupoSimplePlazasOcupadas.builder()
        .idGrupoSimple(1)
        .idGrupoMixto(1)
        .esPiia(es_piia)
        .plazasReservadasNuevosGrupoSimple(plazas_reservadas_nuevos_gs)
        .plazasReservadasNuevosGrupoMixto(plazas_reservadas_nuevos_gm)
        .plazasReservadasNuevosGrupoSimpleOcupadas(plazas_reservadas_nuevos_gs_ocupadas)
        .plazasReservadasNuevosGrupoMixtoOcupadas(plazas_reservadas_nuevos_gm_ocupadas)
        .plazasNuevosGrupoSimple(plazas_nuevos_gs)
        .plazasNuevosGrupoMixto(plazas_nuevos_gm)
        .plazasNuevosGrupoSimpleBloqueo(plazas_nuevos_gs_bloqueo)
        .plazasNuevosGrupoMixtoBloqueo(plazas_nuevos_gm_bloqueo)
        .controlPlazas(control_plazas)
        .plazasRenovacionGrupoSimple(plazas_renovacion_gs)
        .plazasRenovacionGrupoMixto(plazas_renovacion_gm)
        .plazasRenovacionGrupoSimpleBloqueo(plazas_renovacion_gs_bloqueo)
        .plazasRenovacionGrupoMixtoBloqueo(plazas_renovacion_gm_bloqueo)
        .controlPlazasExtendido(control_plazas_extendido)
        .plazasPiiaGrupoSimple(plazas_piia_gs)
        .plazasPiiaGrupoMixto(plazas_piia_gm)
        .plazasPiiaGrupoSimpleBloqueo(plazas_piia_gs_bloqueo)
        .plazasPiiaGrupoMixtoBloqueo(plazas_piia_gm_bloqueo)
        .grupoMixtoNumeroPlazasMax(n_plazas_max)
        .grupoMixtoNumeroPlazasPiiaMax(n_plazas_piia_max)
        .grupoMixtoNumeroPlazasRenovacionMax(n_plazas_rep_max)
        .build();
  }
}
