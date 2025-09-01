package matricula.ejb.matricula;

import lombok.extern.slf4j.Slf4j;
import matricula.dao.mat.FuturosEstudiantesAdmisionesFacade;
import matricula.dao.mat.FuturosEstudiantesRenovacionFacade;
import matricula.dao.mat.FuturosEstudiantesTFFacade;
import matricula.ejb.matricula.dto.FuturosEstudiantesAdmisiones;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import java.util.List;

@Slf4j

@Service
public class ResetAlumnosBean {

    @Autowired
    private FuturosEstudiantesAdmisionesFacade futurosEstudiantesAdmisionesFacade;

    @Autowired
    private FuturosEstudiantesRenovacionFacade futurosEstudiantesRenovacionFacade;

    @Autowired
    private FuturosEstudiantesTFFacade futurosEstudiantesTFFacade;


    public boolean resetearAlumnoFuturosEstudiantesCandidatos(int candidatoId, int procesoAdmisionId, int titulacionId) {

        return futurosEstudiantesAdmisionesFacade.resetFuturosEstudianteAdmisiones(candidatoId,procesoAdmisionId,titulacionId);

    }

    public boolean eliminarInscripcionAlumnoFuturosEstudiantes(int alumnoId, int idins, String cursoAcad) {



        return futurosEstudiantesRenovacionFacade.EliminarInscripcionFuturosEstudiantes(alumnoId, idins, cursoAcad);

    }

    public boolean resetearAlumnoFuturosEstudiantesTF(int alumnoId, int idins, String cursoAcad, char tipoTitulacion) {



        return futurosEstudiantesTFFacade.ResetFuturosEstudianteTF(alumnoId, idins, cursoAcad, tipoTitulacion);

    }

    public boolean resetearInscripcionAlumnoFuturosEstudiantes(int alumnoId, int idins, String cursoAcad) {



        return futurosEstudiantesRenovacionFacade.ResetFuturosEstudiantes(alumnoId, idins, cursoAcad);

    }



}
