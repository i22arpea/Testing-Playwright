/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package matricula.unit.mother.model.mat;

import java.util.Calendar;
import java.util.Date;

import model.acad.estudios.TitulacionTipo;
import model.dbo.CursoAcademico;
import model.mat.EstadoProcesoMatricula;
import model.mat.ProcesoMatricula;
import model.mat.TipoProcesoMatricula;

/** @author amuro */
public class ProcesoMatriculaMother {

  public static ProcesoMatricula create(CursoAcademico cursoMat) {

    Calendar cal = Calendar.getInstance();
    Date fechaInicio;
    if (cursoMat.getFechaInicio() == null) {
      cal.set(Calendar.MONTH, 9);
      cal.set(Calendar.DAY_OF_MONTH, 1);
      fechaInicio = cal.getTime();
      cursoMat.setFechaInicio(fechaInicio);
    } else {
      fechaInicio = cursoMat.getFechaInicio();
    }

    Date fechaFin;
    if (cursoMat.getFechaInicio() == null) {
      cal.add(Calendar.YEAR, 1);
      cal.set(Calendar.MONTH, 8);
      cal.set(Calendar.DAY_OF_MONTH, 30);
      fechaFin = cal.getTime();
      cursoMat.setFechaFin(fechaFin);
    } else {
      fechaFin = cursoMat.getFechaFin();
    }

    TitulacionTipo tipoTitulacion = new TitulacionTipo("G");
    TipoProcesoMatricula tipoProcesoMatricula = new TipoProcesoMatricula(1);
    EstadoProcesoMatricula estadoProcesoMatricula = new EstadoProcesoMatricula(1);

    ProcesoMatricula proceso = ProcesoMatricula.of(cursoMat, "Proceso Matricula",
            tipoTitulacion, tipoProcesoMatricula,
            estadoProcesoMatricula, fechaInicio, fechaFin, 0,
        true, true, true, true);

    return proceso;
  }
}
