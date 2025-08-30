package matricula.unit.mother.model.acad.estudios;


import java.math.BigDecimal;

import model.acad.estudios.*;

public final class AsignaturaMother {

  public static Asignatura createObligatoria(Titulacion titulacion, BigDecimal creditos) {
    CualidadTipotitulacion tipoAsig = new CualidadTipotitulacion(2, "Obligatoria");
    tipoAsig.setCualidadFK(new AsignaturaCualidad(AsignaturaCualidad.OBLIGATORIA_ID));

    Asignatura asignatura = new Asignatura();
    asignatura.setTitulacionFK(titulacion);
    asignatura.setCreditos(creditos);
    asignatura.setTipo(tipoAsig);

    return asignatura;
  }

  public static Asignatura createPMI(BigDecimal creditos) {
    CualidadTipotitulacion tipoAsig = new CualidadTipotitulacion(CualidadTipotitulacion.CUALIDAD_PMI_ID, "Obligatoria");
    tipoAsig.setCualidadFK(new AsignaturaCualidad(AsignaturaCualidad.OBLIGATORIA_ID));
    tipoAsig.setTipotituFK(new TitulacionTipo(TitulacionTipo.FICTICIA));

    Asignatura asignatura = new Asignatura();
    asignatura.setTitulacionFK(TitulacionMother.create(Titulacion.TITULACION_PMI, "TITU_PMI", true));
    asignatura.setCreditos(creditos);
    asignatura.setTipo(tipoAsig);
    asignatura.setNombre("PMI " + creditos + " créditos");

    return asignatura;
  }


}
