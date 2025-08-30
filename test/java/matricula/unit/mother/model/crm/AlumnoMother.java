package matricula.unit.mother.model.crm;

import model.crm.Alumno;
import model.crm.persona.PersonaEntity;

public final class AlumnoMother {

	public static Alumno create() {
		return new Alumno(new PersonaEntity());
	}

	public static Alumno createWithEmail(String email) {
		Alumno alumno = create();
		alumno.setEmail(email);
		return alumno;
	}

}
