/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package matricula.unit.mother.model.mat;

import java.math.BigDecimal;

import model.mat.PmatAlumnoDatosEconomicos;

/** @author amuro */
public class PmatAlumnoDatosEconomicosMother {

  public static PmatAlumnoDatosEconomicos createDeca(BigDecimal totalMatricula, BigDecimal totalCreditos) {
    PmatAlumnoDatosEconomicos datosEconomicos = new PmatAlumnoDatosEconomicos();
    datosEconomicos.setTotalMatricula(totalMatricula);
    datosEconomicos.setTotal(totalCreditos);
    return datosEconomicos;
  }
}
