package matricula.ejb.matricula;

import lombok.extern.slf4j.Slf4j;
import matricula.dao.acad.GrupoMixtoFacade;
import matricula.dao.mat.*;
import matricula.ejb.matricula.dto.FuturosEstudiantes;
import matricula.ejb.matricula.dto.FuturosEstudiantesAdmisiones;

import matricula.ejb.matricula.dto.FuturosEstudiantesAsignatura;
import matricula.ejb.matricula.dto.FuturosEstudiantesOutgoing;
import model.mat.TipoProcesoMatricula;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

@Slf4j

@Service
public class FuturosEstudiantesBean {

    @Autowired
    private FuturosEstudiantesAdmisionesFacade futurosEstudiantesAdmisionesFacade;

    @Autowired
    private FuturosEstudiantesRenovacionFacade futurosEstudiantesRenovacionFacade;

    @Autowired
    private FuturosEstudiantesOutgoingFacade futurosEstudiantesOutgoingFacade;

    @Autowired
    private FuturosEstudiantesTFFacade futurosEstudiantesTFFacade;

    @Autowired
    private FuturosEstudiantesPiiaFacade futurosEstudiantesPiiaFacade;


    public List<FuturosEstudiantesAdmisiones> obtenerFuturosEstudiantesAdmisiones(int idProceso) {

        return futurosEstudiantesAdmisionesFacade.getListaFuturosEstudiantesAdmisiones(idProceso);

    }

    public List<FuturosEstudiantesAdmisiones> obtenerFuturosEstudiantesAdmisionesMat(int idProceso) {

        return futurosEstudiantesAdmisionesFacade.getListaFuturosEstudiantesAdmisionesMat(idProceso);

    }

    public List<FuturosEstudiantesAdmisiones> obtenerFuturosEstudiantesAdmisionesTitulacion(int idProceso, int idTitulacion) {

        return futurosEstudiantesAdmisionesFacade.getListaFuturosEstudiantesAdmisionesTitulacion(idProceso, idTitulacion);

    }


    public List<FuturosEstudiantes> obtenerFuturosEstudiantesRenovacion(String tipoTitulacion, String curso, int solo_TF) {

        return futurosEstudiantesRenovacionFacade.getListaFuturosEstudiantesRenovacion(tipoTitulacion, curso, solo_TF);

    }

    public List<FuturosEstudiantes> obtenerFuturosEstudiantesRenovacionColectivo(String tipoTitulacion, String curso, int solo_TF, int colectivo) {

        return futurosEstudiantesRenovacionFacade.getListaFuturosEstudiantesRenovacionColectivo(tipoTitulacion, curso, solo_TF, colectivo);

    }

    public List<FuturosEstudiantes> obtenerFuturosEstudiantesPiiaColectivo(String tipoTitulacion, String curso, int solo_TF, int colectivo) {

        return futurosEstudiantesPiiaFacade.getListaFuturosEstudiantesPiiaColectivo(tipoTitulacion, curso, solo_TF, colectivo);

    }

    public List<FuturosEstudiantes> obtenerFuturosEstudiantesRenovacionMasAsignatura(String tipoTitulacion, String curso, int solo_TF, int colectivo) {

        return futurosEstudiantesRenovacionFacade.getListaMasAsignaturaRenovacionColectivo(tipoTitulacion, curso, solo_TF, colectivo);

    }

    public List<FuturosEstudiantesAsignatura> obtenerFuturosEstudiantesRenovacionAsigRestrAcept(int asignaturaSuperada, String curso, int asignaturaMatricular) {

        return futurosEstudiantesRenovacionFacade.getListaAsigRestrAceptRenovacion(asignaturaSuperada, curso, asignaturaMatricular);

    }

    public List<FuturosEstudiantesAsignatura> obtenerFuturosEstudiantesRenovacionAsigRestrError(int asignaturaSuperada, String curso, int asignaturaMatricular) {

        return futurosEstudiantesRenovacionFacade.getListaAsigRestrErrorRenovacionColectivo(asignaturaSuperada, curso, asignaturaMatricular);

    }

    public List<FuturosEstudiantesOutgoing> obtenerFuturosEstudiantesOutgoing(String curso) {

        return futurosEstudiantesOutgoingFacade.getListaFuturosEstudiantesOutgoing( curso);

    }

    public List<FuturosEstudiantes> obtenerFuturosEstudiantesTFM(String tipoTitulacion, String curso, int solo_TF) {

        return futurosEstudiantesTFFacade.getListaFuturosEstudiantesTF(tipoTitulacion, curso, solo_TF, TipoProcesoMatricula.TIPO_PROCESO_MATRICULA_TRABAJO_FIN_MASTER);

    }

    public List<FuturosEstudiantes> obtenerFuturosEstudiantesTFG(String tipoTitulacion, String curso, int solo_TF) {

        return futurosEstudiantesTFFacade.getListaFuturosEstudiantesTF(tipoTitulacion, curso, solo_TF,TipoProcesoMatricula.TIPO_PROCESO_MATRICULA_TRABAJO_FIN_GRADO);

    }

    public List<FuturosEstudiantes> obtenerFuturosEstudiantesTFGMod(String tipoTitulacion, String curso, int solo_TF) {

        return futurosEstudiantesTFFacade.getListaFuturosEstudiantesTFMod(tipoTitulacion, curso, solo_TF, TipoProcesoMatricula.TIPO_PROCESO_MATRICULA_TRABAJO_FIN_GRADO, TipoProcesoMatricula.TIPO_PROCESO_MATRICULA_RENOVACION_GRADO);

    }

    public List<FuturosEstudiantes> obtenerFuturosEstudiantesTFMMod(String tipoTitulacion, String curso, int solo_TF) {

        return futurosEstudiantesTFFacade.getListaFuturosEstudiantesTFMod(tipoTitulacion, curso, solo_TF, TipoProcesoMatricula.TIPO_PROCESO_MATRICULA_TRABAJO_FIN_MASTER, TipoProcesoMatricula.TIPO_PROCESO_MATRICULA_RENOVACION_MASTER);

    }



}
