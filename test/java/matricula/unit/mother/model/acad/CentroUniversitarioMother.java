/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package matricula.unit.mother.model.acad;

import model.acad.CentroUniversitario;
import utils.testing.util.motherobject.IntegerMother;
import utils.testing.util.motherobject.WordMother;

/** @author amuro */
public class CentroUniversitarioMother {
  public static CentroUniversitario create(Integer centroId, String nombre, String emisora) {
    CentroUniversitario centroUniversitario = new CentroUniversitario(centroId, nombre);
    centroUniversitario.setEmisora(emisora);
    return centroUniversitario;
  }

  public static CentroUniversitario ramdon(String emisora) {
    return create(IntegerMother.random(), WordMother.random(), emisora);
  }
}
