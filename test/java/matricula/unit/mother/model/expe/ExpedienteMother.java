package matricula.unit.mother.model.expe;

import matricula.unit.mother.model.acad.estudios.TitulacionMother;
import matricula.unit.mother.model.crm.AlumnoMother;
import matricula.unit.mother.model.crm.facilities.CampusMother;
import model.acad.estudios.Titulacion;
import model.crm.Alumno;
import model.crm.facilities.Campus;
import model.expe.Expediente;
import utils.testing.util.motherobject.IntegerMother;

public final class ExpedienteMother {

	public static Expediente create(Alumno alumnoFK, Campus campusFK, Titulacion titulacionFK, boolean activo) {
		Expediente expediente = new Expediente(alumnoFK, campusFK, titulacionFK, activo);
		expediente.setExpeID(IntegerMother.random());
		return expediente;
	}

	public static Expediente create(Titulacion titulacion, boolean activo) {
		Expediente expediente = ExpedienteMother.random();
		expediente.setTitulacionFK(titulacion);
		expediente.setActivo(activo);

		return expediente;
	}

	public static Expediente random() {
		return create(AlumnoMother.create(), CampusMother.random(), TitulacionMother.random(), true);
	}

}
