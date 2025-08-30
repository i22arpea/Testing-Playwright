package matricula.unit.mother.model.acad.estudios;

import model.acad.estudios.Titulacion;
import model.acad.estudios.TitulacionDoble;

public final class TitulacionDobleMother {

  public static TitulacionDoble create(Integer titulacionID, Titulacion titulacion1, Titulacion titulacion2) {
    TitulacionDoble titulacionDoble = new TitulacionDoble();
    titulacionDoble.setTitulaciondobleID(titulacionID);
    titulacionDoble.setTitulacion1FK(titulacion1);
    titulacionDoble.setTitulacion2FK(titulacion2);

    return titulacionDoble;
  }
}
