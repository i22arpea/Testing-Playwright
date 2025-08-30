
package matricula.unit.mother.model.mat;

import java.math.BigDecimal;

import dto.matricula.MatriculaPreciosHelper;
import model.acad.estudios.Titulacion;
import model.acad.estudios.TitulacionDoble;
import model.acad.estudios.TitulacionTipo;
import model.adm.GruposTipoTitulacion;
import model.crm.Alumno;
import model.crm.facilities.Campus;
import model.crm.persona.PersonaEntity;
import model.crm.tiempo.MesEntity;
import model.dbo.CursoAcademico;
import model.dbo.usuarios.Usuario;
import model.expe.Expediente;
import model.expe.ExpedienteDoble;
import model.mat.PmatAlumnoInscripcion;
import model.mat.ProcesoMatricula;
import model.mat.precios.MatriculaPreciosAbstract;
import model.mat.precios.MatriculaPreciosException;
import model.mat.precios.MatriculaPreciosMatricula;
import model.mat.precios.MatriculaPreciosPlantilla;

/**
 *
 * @author aamunoz
 */
public class InscripcionColectivosMatriculaMother {

    public static PmatAlumnoInscripcion createMasterNuevoIngreso() {
        return create(new TitulacionTipo("M"), true, null);
    }

    public static PmatAlumnoInscripcion createWithExpedienteDobleNuevoIngreso() {
        ExpedienteDoble expedienteDoble = new ExpedienteDoble(1);
        return create(new TitulacionTipo("M"), true, expedienteDoble);
    }

    private static PmatAlumnoInscripcion create(TitulacionTipo tipoTitulacion, boolean nuevoIngreso,
            ExpedienteDoble expedienteDoble) {
        int renovacion = 2;
        Alumno alumno = new Alumno(new PersonaEntity());
        Campus campus = new Campus();

        Titulacion titulacion = new Titulacion(1);
        titulacion.setTipoFK(tipoTitulacion);

        Expediente expediente = new Expediente(alumno, campus, titulacion, true);

        if (expedienteDoble != null) {
            TitulacionDoble titulacionDoble = new TitulacionDoble();
            titulacionDoble.setTitulacion(titulacion);
            expedienteDoble.setTitulaciondobleFK(titulacionDoble);
            expedienteDoble.setActivo(Boolean.TRUE);
            expediente.setExpedienteDoble1(expedienteDoble);
            if (expedienteDoble.getExpe1FK() == null) {
                expedienteDoble.setExpe1FK(expediente);
            }
        }

        CursoAcademico cursoMat = new CursoAcademico("2024/25");
        ProcesoMatricula proceso = ProcesoMatriculaMother.create(cursoMat);

        GruposTipoTitulacion colectivoTitulacion = new GruposTipoTitulacion();
        colectivoTitulacion.setNuevoIngreso(nuevoIngreso);

        expediente.setIdGrupoTipoTitulacionFK(colectivoTitulacion);

        Usuario usuario = new Usuario(Usuario.USUARIO_SISTEMA);

        BigDecimal importeMatricula = BigDecimal.valueOf(700);
        BigDecimal importeReservaAdmision = BigDecimal.valueOf(1000);
        BigDecimal credito1 = BigDecimal.valueOf(100);
        BigDecimal credito2 = BigDecimal.valueOf(102);
        BigDecimal credito3 = BigDecimal.valueOf(103);
        BigDecimal credito4 = BigDecimal.valueOf(104);
        BigDecimal creditoCF = BigDecimal.valueOf(105);

        BigDecimal precioSeguro = new BigDecimal(50);
        Boolean reciboSemestral = true;

        int mensualidades = 9;
        MesEntity mesPrimeraMensualidad = new MesEntity("10");

        MatriculaPreciosMatricula preciosTitulacion;
        MatriculaPreciosAbstract matriculaPreciosAbstract = null;
        try {
            matriculaPreciosAbstract = MatriculaPreciosAbstract.preparePreciosPlantillaCreditos(importeMatricula,
                    importeReservaAdmision,
                    credito1, credito2, credito3, credito4, creditoCF, mensualidades, mesPrimeraMensualidad,
                    reciboSemestral, usuario);

            preciosTitulacion = MatriculaPreciosMatricula.preparePreciosMatricula(cursoMat, titulacion,
                    MatriculaPreciosPlantilla.MODO_PRECIOS_CREDITOS, matriculaPreciosAbstract);
        } catch (MatriculaPreciosException e) {
            throw new RuntimeException(e);
        }

        MatriculaPreciosHelper preciosHelper = MatriculaPreciosHelper.of(proceso.getCursoID(), preciosTitulacion,
                importeMatricula, precioSeguro);

        if (expedienteDoble != null) {
            return PmatAlumnoInscripcion.prepareInscripcionRegladaPCE(proceso, colectivoTitulacion, expedienteDoble,
                    preciosHelper);
        } else {
            return PmatAlumnoInscripcion.prepareInscripcionReglada(proceso, colectivoTitulacion, expediente,
                    preciosHelper);
        }

    }

}
