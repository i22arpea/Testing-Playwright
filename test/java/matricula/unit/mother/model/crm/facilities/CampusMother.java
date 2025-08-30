package matricula.unit.mother.model.crm.facilities;

import model.crm.facilities.Campus;
import model.crm.facilities.CampusReal;
import utils.testing.util.motherobject.IntegerMother;

public final class CampusMother {
	public static Campus create(Integer campusID) {
		return new Campus(campusID);
	}

	public static Campus random() {
		return create(IntegerMother.random());
	}

	public static Campus createCampusRealCordoba() {
		Campus elemento = create(Campus.CAMPUS_CORDOBA);
		elemento.setCampusrealFK(new CampusReal(CampusReal.CAMPUS_CORDOBA));
		return elemento;
	}
}
