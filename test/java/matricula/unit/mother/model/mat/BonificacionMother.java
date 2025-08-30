/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package matricula.unit.mother.model.mat;

import java.math.BigDecimal;

import model.expe.Expediente;
import model.mat.Bonificacion;
import model.mat.BonificacionEstado;
import model.mat.BonificacionTipo;

/** @author amuro */
public class BonificacionMother {

  public static Bonificacion createBonificacionCreditos(
      Expediente expediente,
      BigDecimal porcentajeBonficacion,
      int bonificacionTipoId,
      char bonificacionEstado) {
    Bonificacion bonificacion = new Bonificacion();
    BonificacionTipo bonificacionTipo = new BonificacionTipo(bonificacionTipoId);
    bonificacionTipo.setPorcentaje(porcentajeBonficacion.intValue());
    bonificacionTipo.setImporteFijo(Boolean.FALSE);
    bonificacion.setIdTipoBonificacion(bonificacionTipo);
    bonificacion.setIdEstado(new BonificacionEstado(bonificacionEstado));
    bonificacion.setPorcentajeBonificacion(porcentajeBonficacion);
    bonificacion.setExpeFK(expediente);

    return bonificacion;
  }

}
