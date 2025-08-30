package matricula.unit.mother.model.mat;

import java.util.ArrayList;
import java.util.List;

import model.mat.ColectivoEspecialMatricula;
import model.mat.ColectivosEspecialesTiposProcesos;
import model.mat.ColectivosEspecialesTiposProcesosPK;
import model.mat.PmatAlumnoInscripcion;
import model.mat.TipoProcesoColectivoEspecial;

/**
 *
 * @author aamunoz
 */
public class ColectivoEspecialMatriculaMother {

    public static List<ColectivosEspecialesTiposProcesos> createSimultaneidadTitulacionesExcluidosExpedientesDobles(
            PmatAlumnoInscripcion inscripcion) {
        return new ArrayList<>(create(1, inscripcion));
    }

    public static List<ColectivosEspecialesTiposProcesos> createAyudanteInvestigacionDoctorado(
            PmatAlumnoInscripcion inscripcion) {
        return new ArrayList<>(create(3, inscripcion));
    }

    public static List<ColectivosEspecialesTiposProcesos> createAlumnosRenovacionMasterPropioMIMMCCD(
            PmatAlumnoInscripcion inscripcion) {
        return new ArrayList<>(create(9, inscripcion));
    }

    private static List<ColectivosEspecialesTiposProcesos> create(Integer colectivoId,
            PmatAlumnoInscripcion inscripcion) {
        List<ColectivosEspecialesTiposProcesos> colectivosTiposTitulacion = new ArrayList<>();

        ColectivoEspecialMatricula colectivoMatricula = new ColectivoEspecialMatricula(1);
        TipoProcesoColectivoEspecial colectivoTipo = new TipoProcesoColectivoEspecial();
        colectivoTipo.setId(1);

        ColectivosEspecialesTiposProcesosPK id = new ColectivosEspecialesTiposProcesosPK(1,
                inscripcion.getExpeFK().getTitulacionFK().getTipoFK().getTipoID(),
                inscripcion.getExpeFK().getIdGrupoTipoTitulacionFK().getNuevoIngreso() ? 1 : 2);
        ColectivosEspecialesTiposProcesos colectivoTipoTitulacion = new ColectivosEspecialesTiposProcesos(id);
        colectivoTipoTitulacion.setColectivoEspecialMatricula(colectivoMatricula);
        colectivoTipoTitulacion.setTipoProcesoColectivoEspecial(colectivoTipo);

        colectivosTiposTitulacion.add(colectivoTipoTitulacion);
        return colectivosTiposTitulacion;
    }

}
