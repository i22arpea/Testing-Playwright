package matricula.unit.mother.model.acad.estudios;

import model.acad.CentroUniversitario;
import model.acad.estudios.TipologiaEstatusTitulacion;
import model.acad.estudios.Titulacion;
import model.acad.estudios.TitulacionTipo;
import model.acad.siiu.SiiuRamaEnsenanza;
import model.crm.idioma.Idioma;
import utils.testing.util.motherobject.IntegerMother;
import utils.testing.util.motherobject.WordMother;

public final class TitulacionMother {

  public static Titulacion create(Integer titulacionID, String nombre, boolean vigente) {
    return new Titulacion(titulacionID, nombre, vigente);
  }

  public static Titulacion random() {
    return create(IntegerMother.random(), WordMother.random(), true);
  }

  public static Titulacion randomWithCentro(CentroUniversitario centro) {
    Titulacion titulacion = create(IntegerMother.random(), WordMother.random(), true);
    titulacion.setCentroFK(centro);
    return titulacion;
  }

  public static Titulacion createTitulacionSetGrado(TipologiaEstatusTitulacion tipologia) {
    Titulacion t = random();
    t.setTipologiaFK(tipologia);
    t.setTipoFK(new TitulacionTipo(TitulacionTipo.GRADO));
    t.setRamaFK(new SiiuRamaEnsenanza());
    t.setIdiomaImparticionFK(new Idioma(Idioma.ESPANOL, "Castellano"));

    t.setRequisitosAcceso("ra " + t.getNombre());
    t.setRequisitosAccesoEng("ra Eng " + t.getNombre());
    t.setReqProgramasConjuntosInt("rpc " + t.getNombre());
    t.setReqProgramasConjuntosIntEng("rpc Eng " + t.getNombre());
    t.setResultadosApredizaje("rap " + t.getNombre());
    t.setResultadosApredizajeEng("rap Eng " + t.getNombre());
    t.setAccesoEstudiosPosteriores("aep " + t.getNombre());
    t.setAccesoEstudiosPosterioresEng("aep Eng " + t.getNombre());
    t.setObjetivos("o " + t.getNombre());
    t.setObjetivosEng("o Eng " + t.getNombre());
    t.setCompetencias("c " + t.getNombre());
    t.setCompetenciasEng("c Eng " + t.getNombre());
    return t;

  }
}
