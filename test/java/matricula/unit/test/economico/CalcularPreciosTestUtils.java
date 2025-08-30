package matricula.unit.test.economico;

import model.acad.estudios.Asignatura;
import model.acad.estudios.Titulacion;
import model.acad.planif.GrupoSimple;
import model.crm.tiempo.MesEntity;
import model.dbo.CursoAcademico;
import model.dbo.usuarios.Usuario;
import model.expe.Expediente;
import model.mat.*;
import model.mat.precios.MatriculaPreciosAbstract;
import model.mat.precios.MatriculaPreciosMatricula;
import model.mat.precios.MatriculaPreciosPlantilla;

import java.math.BigDecimal;


public class CalcularPreciosTestUtils {

    private static String sesionID = "sesion-123";

    public static BigDecimal importeMatricula = BigDecimal.valueOf(700);
    public static BigDecimal importeReservaAdmision = BigDecimal.valueOf(300);
    public static double _TOLERANCIA = 0.001;
    public static CursoAcademico cursoAcademico = new CursoAcademico("2024/25");

    public static MatriculaPreciosMatricula crearPrecios(CursoAcademico cursoAcademico, Titulacion titulacion,
                                                   BigDecimal importeCredito, BigDecimal importeCredito2) {
        Usuario usuario = new Usuario(Usuario.USUARIO_SISTEMA);

        BigDecimal importeFormacion = BigDecimal.ZERO;
        int mensualidades = 10;
        MesEntity mesPrimeraMensualidad = new MesEntity("10");
        boolean reciboSemestral = true;

        MatriculaPreciosAbstract matriculaPreciosAbstract = MatriculaPreciosAbstract.preparePreciosPlantillaPrecio(
                importeMatricula, importeReservaAdmision, importeFormacion, mensualidades, mesPrimeraMensualidad, reciboSemestral, usuario);
        matriculaPreciosAbstract.setImporteCredito(importeCredito);
        matriculaPreciosAbstract.setImporteCredito2(importeCredito2);

        MatriculaPreciosMatricula preciosMatriculaTituDoble = MatriculaPreciosMatricula.preparePreciosMatricula(cursoAcademico, titulacion,
                MatriculaPreciosPlantilla.MODO_PRECIOS_CREDITOS, matriculaPreciosAbstract);

        return preciosMatriculaTituDoble;
    }

    public static MatriculaPreciosMatricula crearPreciosReconocimientoExterno(CursoAcademico cursoAcademico, Titulacion titulacion,
                                                         BigDecimal importeRecExterno, BigDecimal porcentajeRecExternoPrimerCurso) {
        MatriculaPreciosMatricula preciosMatricula = crearPrecios(cursoAcademico, titulacion, BigDecimal.ZERO, BigDecimal.ZERO);
        preciosMatricula.setImporteReconocimientoExterno(importeRecExterno);
        preciosMatricula.setPorcentajeRecExternoPrimerCurso(porcentajeRecExternoPrimerCurso);

        return preciosMatricula;
    }

    public static MatriculaPreciosMatricula crearPreciosReconocimientoDoble(CursoAcademico cursoAcademico, Titulacion titulacion,
                                                         BigDecimal importeRecDoble) {
        MatriculaPreciosMatricula preciosMatricula = crearPrecios(cursoAcademico, titulacion, BigDecimal.ZERO, BigDecimal.ZERO);
        preciosMatricula.setImporteReconocimientoDoble(importeRecDoble);

        return preciosMatricula;
    }

    public static MatriculaPreciosMatricula crearPreciosPMI(CursoAcademico cursoAcademico, Titulacion titulacion,
                                                            BigDecimal importeCredito, Integer porcentajeRecPIC) {
        MatriculaPreciosMatricula preciosMatricula = crearPrecios(cursoAcademico, titulacion, importeCredito, BigDecimal.ZERO);
        preciosMatricula.setPorcentajeReconocimientoPic(porcentajeRecPIC);

        return preciosMatricula;
    }

    public static PmatAlumnoInscripcionAsignatura crearMatriculaAsignaturaOrdinaria(
            PmatAlumnoInscripcion pmatAlumnoInscripcion, Asignatura asignatura, GrupoSimple grupoSimple, Integer annusMat)
            throws InscripcionAsignaturaExpedienteException {

        return PmatAlumnoInscripcionAsignatura
                .createPmatAlumnoInscripcionAsignatura(pmatAlumnoInscripcion, asignatura, annusMat,
                        grupoSimple, new PmatAlumnoTipoMatriculaAsignatura(PmatAlumnoTipoMatriculaAsignatura.MATRICULA_TIPO_ASIGNATURA_ORDINARIA),
                        2, 0.0, sesionID);
    }

    public static PmatAlumnoInscripcionAsignatura crearMatriculaAsignaturaReconocimientoExterno(
            PmatAlumnoInscripcion pmatAlumnoInscripcion, Asignatura asignatura,
            Integer cursoAsignatura, Integer annusMat)
            throws InscripcionAsignaturaExpedienteException {

        return PmatAlumnoInscripcionAsignatura.createPmatAlumnoInscripcionAsignaturaReconocimiento(
                pmatAlumnoInscripcion, asignatura, annusMat,
                new PmatAlumnoTipoMatriculaAsignatura(PmatAlumnoTipoMatriculaAsignatura.MATRICULA_TIPO_ASIGNATURA_EXTERNO),
                cursoAsignatura,  false, new PmatAlumnoInscripcionAsignaturaEvento(), false, sesionID);
    }

    public static PmatAlumnoInscripcionAsignatura crearMatriculaAsignaturaReconocimientoDoble(
            PmatAlumnoInscripcion pmatAlumnoInscripcion, Asignatura asignatura, Integer annusMat)
            throws InscripcionAsignaturaExpedienteException {

        return PmatAlumnoInscripcionAsignatura.createPmatAlumnoInscripcionAsignaturaReconocimiento(
                pmatAlumnoInscripcion, asignatura, annusMat,
                new PmatAlumnoTipoMatriculaAsignatura(PmatAlumnoTipoMatriculaAsignatura.MATRICULA_TIPO_ASIGNATURA_DOBLE_GRADO),
                2,  false, new PmatAlumnoInscripcionAsignaturaEvento(), false, sesionID);
    }

    public static PmatAlumnoInscripcionAsignatura crearMatriculaAsignaturaPMI(
            PmatAlumnoInscripcion pmatAlumnoInscripcion, Asignatura asignatura, Expediente expediente, Integer annusMat)
            throws InscripcionAsignaturaExpedienteException {

        return PmatAlumnoInscripcionAsignatura.createPmatAlumnoInscripcionAsignaturaPMI(
                pmatAlumnoInscripcion, asignatura, annusMat, expediente, 2, sesionID);
    }

    public static PmatAlumnoInscripcionAsignatura calcularPrecioMatriculaAsignatura(
            PmatAlumnoInscripcion pmatAlumnoInscripcion, PmatAlumnoInscripcionAsignatura inscripcionAsignatura)
            throws java.sql.SQLTransactionRollbackException {
        BigDecimal precioAsignatura = pmatAlumnoInscripcion.calcularPrecioAsignaturaGenerico(inscripcionAsignatura);
        inscripcionAsignatura.setPrecio(precioAsignatura);

        return inscripcionAsignatura;
    }

}