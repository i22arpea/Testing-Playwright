/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package matricula.unit.mother.model.acad.planif;

import model.acad.planif.GrupoSimple;

/**
 * @author amuro
 */
public class GrupoSimpleMother {
  public static GrupoSimple createGrupoSimpleReserva(int id, Integer numeroReservaPlaza) {
    GrupoSimple grupoSimple = createGrupoSimple(id);
    grupoSimple.setPlazasReservadasNuevosGrupoSimple(numeroReservaPlaza);
    return grupoSimple;
  }

  public static GrupoSimple createGrupoSimple(int id) {
    return new GrupoSimple(id);
  }
}
