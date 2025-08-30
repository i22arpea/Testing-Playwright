package matricula.unit.mother.model.expe;

import model.acad.estudios.TitulacionDoble;
import model.expe.Expediente;
import model.expe.ExpedienteDoble;

public final class ExpedienteDobleMother {

	public static ExpedienteDoble create(Expediente expediente1, Expediente expediente2, TitulacionDoble titulacionDoble, boolean activo) {
		ExpedienteDoble expeDoble = new ExpedienteDoble();
		expeDoble.setExpe1FK(expediente1);
		expeDoble.setExpe2FK(expediente2);
		expeDoble.setActivo(activo);
		expeDoble.setTitulaciondobleFK(titulacionDoble);

		return expeDoble;
	}

}
