/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package matricula.unit.mother.model.mat;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import model.acad.estudios.Asignatura;
import model.mat.PmatAlumnoInscripcionAsignatura;

/** @author amuro */
public class PmatAlumnoInscripcionAsignaturaMother {

  public static PmatAlumnoInscripcionAsignatura createInscripcionAsignatura(double precioLoyola) {
    PmatAlumnoInscripcionAsignatura inscripcionAsignatura = PmatAlumnoInscripcionAsignatura
        .createPmatAlumnoInscripcionAsignaturaTesting();
    inscripcionAsignatura.setPrecio(BigDecimal.valueOf(precioLoyola));
    Asignatura asignatura = new Asignatura();
    asignatura.setAsignaturaID(Double.valueOf(precioLoyola).intValue());
    inscripcionAsignatura.setAsignatura(asignatura);
    return inscripcionAsignatura;
  }

  public static List<PmatAlumnoInscripcionAsignatura> createInscripcionAsignaturas(
      int numeroElementos, double precioLoyola) {
    List<PmatAlumnoInscripcionAsignatura> resultado = new ArrayList();
    for (int i = 0; i < numeroElementos; i++) {
      PmatAlumnoInscripcionAsignatura inscripcionAsignatura = PmatAlumnoInscripcionAsignatura
          .createPmatAlumnoInscripcionAsignaturaTesting();
      inscripcionAsignatura.setPrecio(BigDecimal.valueOf(precioLoyola));
      Asignatura asignatura = new Asignatura();
      asignatura.setAsignaturaID(i);
      inscripcionAsignatura.setAsignatura(asignatura);
      resultado.add(inscripcionAsignatura);
    }
    return resultado;
  }
}
