package matricula.dao.mat;

import com.github.jknack.handlebars.internal.antlr.atn.SemanticContext;
import lombok.extern.slf4j.Slf4j;
import lombok.extern.slf4j.Slf4j;
import matricula.dao.adm.PadmTitulacionListaAdmitidosFacade;
import matricula.ejb.matricula.dto.FuturosEstudiantes;
import matricula.ejb.matricula.dto.FuturosEstudiantesAdmisiones;
import matricula.ejb.matricula.dto.FuturosEstudiantesAsignatura;
import model.acad.estudios.Asignatura;
import model.acad.estudios.Asignatura_;
import model.acad.estudios.Titulacion;
import model.acad.estudios.Titulacion_;
import model.adm.*;
import model.crm.Alumno;
import model.crm.Alumno_;
import model.crm.facilities.Campus;
import model.dbo.CursoAcademico;
import model.dbo.CursoAcademico_;
import model.expe.*;
import model.mat.PmatAlumnoInscripcion;
import model.mat.PmatAlumnoInscripcion_;
import model.mat.ProcesoMatricula;
import model.mat.ProcesoMatricula_;

import javax.annotation.security.PermitAll;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Slf4j
@Service
public class FuturosEstudiantesRenovacionFacade {


    @PersistenceContext
    private EntityManager em;

    public EntityManager getEntityManager() {
        return em;
    }


    public List<FuturosEstudiantes> getListaFuturosEstudiantesRenovacion(String tipoTitulacion, String curso, int solo_TF) {
        List<FuturosEstudiantes> listaFuturosEstudiantes = null;

        try{

            CriteriaBuilder queryBuilder = em.getCriteriaBuilder();
            CriteriaQuery<FuturosEstudiantes> queryDefinition = queryBuilder.createQuery(FuturosEstudiantes.class);
            Root<ExpedientesCurso> root = queryDefinition.from(ExpedientesCurso.class);
            Join<ExpedientesCurso, ExpedientesCursoPK> join = root.join(ExpedientesCurso_.expedientesCursoPK);
            Join<ExpedientesCurso, PmatAlumnoInscripcion> join2 = root.join(ExpedientesCurso_.pmatAlumnoInscripcion);
            Join<PmatAlumnoInscripcion, Alumno> join4 = join2.join(PmatAlumnoInscripcion_.alumnoFK);
            Join<Alumno, Candidato> join5 = join4.join(Alumno_.candidatoFK);


            Predicate c1 = queryBuilder.equal(join.get(ExpedientesCursoPK_.cursoFK), curso);
            Predicate c2 = queryBuilder.greaterThan(root.get(ExpedientesCurso_.creditosPendientes), BigDecimal.valueOf(0));
            Predicate c3 = queryBuilder.equal(root.get(ExpedientesCurso_.soloTF), solo_TF);
            Predicate c4 = queryBuilder.equal(root.get(ExpedientesCurso_.egresado), 0);
            Predicate c5 = queryBuilder.equal(root.get(ExpedientesCurso_.tipoFK), tipoTitulacion);
            Predicate c6 = queryBuilder.equal(join4.get(Alumno_.alumnoID), root.get(ExpedientesCurso_.alumnoFK));




            queryDefinition.where(queryBuilder.and(c1,c2,c3,c4,c5,c6));

            queryDefinition.select(
                    queryBuilder.construct(FuturosEstudiantes.class,
                            join.get(ExpedientesCursoPK_.cursoFK),
                            root.get(ExpedientesCurso_.alumnoFK),
                            root.get(ExpedientesCurso_.tipoFK),
                            root.get(ExpedientesCurso_.lastinscripcionFK),
                            root.get(ExpedientesCurso_.soloTF),
                            root.get(ExpedientesCurso_.cursoCerrado),
                            root.get(ExpedientesCurso_.egresado),
                            root.get(ExpedientesCurso_.creditosPendientes),
                            join5.get(Candidato_.correoElectronico)
                            , join4.get(Alumno_.uid)
                    ));

            log.info(getEntityManager().createQuery(queryDefinition).getResultList().toString());
            return this.getEntityManager().createQuery(queryDefinition).getResultList();
        } catch (Exception ex) {
            log.error(ex.getMessage(),ex);

        }
        return listaFuturosEstudiantes;
    }

    public List<FuturosEstudiantes> getListaFuturosEstudiantesRenovacionColectivo(String tipoTitulacion, String curso, int solo_TF, int colectivo) {
        List<FuturosEstudiantes> listaFuturosEstudiantes = null;

        try{

            CriteriaBuilder queryBuilder = em.getCriteriaBuilder();
            CriteriaQuery<FuturosEstudiantes> queryDefinition = queryBuilder.createQuery(FuturosEstudiantes.class);
            Root<ExpedientesCurso> root = queryDefinition.from(ExpedientesCurso.class);
            Join<ExpedientesCurso, ExpedientesCursoPK> join = root.join(ExpedientesCurso_.expedientesCursoPK);
            Join<ExpedientesCurso, PmatAlumnoInscripcion> join2 = root.join(ExpedientesCurso_.pmatAlumnoInscripcion);
            Join<PmatAlumnoInscripcion, GruposTipoTitulacion> join3 = join2.join(PmatAlumnoInscripcion_.gruposTipoTitulacion);
            Join<PmatAlumnoInscripcion, Alumno> join4 = join2.join(PmatAlumnoInscripcion_.alumnoFK);
            Join<Alumno, Candidato> join5 = join4.join(Alumno_.candidatoFK);



            Predicate c1 = queryBuilder.equal(join.get(ExpedientesCursoPK_.cursoFK), curso);
            Predicate c2 = queryBuilder.greaterThan(root.get(ExpedientesCurso_.creditosPendientes), BigDecimal.valueOf(0));
            Predicate c3 = queryBuilder.equal(root.get(ExpedientesCurso_.soloTF), solo_TF);
            Predicate c4 = queryBuilder.equal(root.get(ExpedientesCurso_.egresado), 0);
            Predicate c5 = queryBuilder.equal(root.get(ExpedientesCurso_.tipoFK), tipoTitulacion);
            Predicate c6 = queryBuilder.equal(join4.get(Alumno_.alumnoID), root.get(ExpedientesCurso_.alumnoFK));
            Predicate c7 = queryBuilder.equal(join3.get(GruposTipoTitulacion_.idGrupo), colectivo);




            queryDefinition.where(queryBuilder.and(c1,c2,c3,c4,c5,c6,c7));

            queryDefinition.select(
                    queryBuilder.construct(FuturosEstudiantes.class,
                            join.get(ExpedientesCursoPK_.cursoFK),
                            root.get(ExpedientesCurso_.alumnoFK),
                            root.get(ExpedientesCurso_.tipoFK),
                            root.get(ExpedientesCurso_.lastinscripcionFK),
                            root.get(ExpedientesCurso_.soloTF),
                            root.get(ExpedientesCurso_.cursoCerrado),
                            root.get(ExpedientesCurso_.egresado),
                            root.get(ExpedientesCurso_.creditosPendientes),
                            join5.get(Candidato_.correoElectronico)
                            , join4.get(Alumno_.uid)
                    ));

            log.info(getEntityManager().createQuery(queryDefinition).getResultList().toString());
            return this.getEntityManager().createQuery(queryDefinition).getResultList();
        } catch (Exception ex) {
            log.error(ex.getMessage(),ex);

        }
        return listaFuturosEstudiantes;
    }

    public List<FuturosEstudiantes> getListaMasAsignaturaRenovacionColectivo(String tipoTitulacion, String curso, int solo_TF, int colectivo) {
        List<FuturosEstudiantes> listaFuturosEstudiantes = null;

        try{

            CriteriaBuilder queryBuilder = em.getCriteriaBuilder();
            CriteriaQuery<FuturosEstudiantes> queryDefinition = queryBuilder.createQuery(FuturosEstudiantes.class);
            Root<ExpedientesCurso> root = queryDefinition.from(ExpedientesCurso.class);
            Join<ExpedientesCurso, ExpedientesCursoPK> join = root.join(ExpedientesCurso_.expedientesCursoPK);
            Join<ExpedientesCurso, PmatAlumnoInscripcion> join2 = root.join(ExpedientesCurso_.pmatAlumnoInscripcion);
            Join<PmatAlumnoInscripcion, GruposTipoTitulacion> join3 = join2.join(PmatAlumnoInscripcion_.gruposTipoTitulacion);
            Join<PmatAlumnoInscripcion, Alumno> join4 = join2.join(PmatAlumnoInscripcion_.alumnoFK);
            Join<Alumno, Candidato> join5 = join4.join(Alumno_.candidatoFK);



            Predicate c1 = queryBuilder.equal(join.get(ExpedientesCursoPK_.cursoFK), curso);
            Predicate c2 = queryBuilder.greaterThan(root.get(ExpedientesCurso_.creditosPendientes), BigDecimal.valueOf(0));
            Predicate c3 = queryBuilder.equal(root.get(ExpedientesCurso_.soloTF), solo_TF);
            Predicate c4 = queryBuilder.equal(root.get(ExpedientesCurso_.egresado), 0);
            Predicate c5 = queryBuilder.equal(root.get(ExpedientesCurso_.tipoFK), tipoTitulacion);
            Predicate c6 = queryBuilder.equal(join3.get(GruposTipoTitulacion_.idGrupo), colectivo);
            Predicate c7 = queryBuilder.equal(root.get(ExpedientesCurso_.cursoIngresoFK), "2023/24");




            queryDefinition.where(queryBuilder.and(c1,c2,c3,c4,c5,c6,c7));

            queryDefinition.select(
                    queryBuilder.construct(FuturosEstudiantes.class,
                            join.get(ExpedientesCursoPK_.cursoFK),
                            root.get(ExpedientesCurso_.alumnoFK),
                            root.get(ExpedientesCurso_.tipoFK),
                            root.get(ExpedientesCurso_.lastinscripcionFK),
                            root.get(ExpedientesCurso_.soloTF),
                            root.get(ExpedientesCurso_.cursoCerrado),
                            root.get(ExpedientesCurso_.egresado),
                            root.get(ExpedientesCurso_.creditosPendientes),
                            join5.get(Candidato_.correoElectronico)
                            , join4.get(Alumno_.uid)
                    ));

            log.info(getEntityManager().createQuery(queryDefinition).getResultList().toString());
            return this.getEntityManager().createQuery(queryDefinition).getResultList();
        } catch (Exception ex) {
            log.error(ex.getMessage(),ex);

        }
        return listaFuturosEstudiantes;
    }


    public List<FuturosEstudiantesAsignatura> getListaAsigRestrAceptRenovacion(int asignaturaSuperada, String curso, int asignaturaMatricular) {
        List<FuturosEstudiantesAsignatura> listaFuturosEstudiantes = null;

        try{

            CriteriaBuilder queryBuilder = em.getCriteriaBuilder();
            CriteriaQuery<FuturosEstudiantesAsignatura> queryDefinition = queryBuilder.createQuery(FuturosEstudiantesAsignatura.class);
            Root<Calificacion> root = queryDefinition.from(Calificacion.class);
            Join<Calificacion, Expediente> join = root.join(Calificacion_.expeFK);
            Join<Expediente, Alumno> join3 = join.join(Expediente_.alumnoFK);
            Join<Calificacion, CursoAcademico> join4 = root.join(Calificacion_.cursoFK);
            Join<Calificacion, Asignatura> join5 = root.join(Calificacion_.asignaturaFK);
            Join<Expediente, PmatAlumnoInscripcion> join8 = join.join(Expediente_.lastInscripcionFK);
            Join<Alumno, Candidato> join12 = join3.join(Alumno_.candidatoFK);


            Predicate c1 = queryBuilder.equal(join5.get(Asignatura_.asignaturaID), asignaturaMatricular);
            Predicate c2 = queryBuilder.equal(join4.get(CursoAcademico_.cursoID), curso);


            //Subquery para obteneer la calificación para matricular
            Subquery<Calificacion> subquery1 = queryDefinition.subquery(Calificacion.class);
            Root<Calificacion> c = subquery1.from(Calificacion.class);
            Join<Calificacion, Asignatura> join6 = c.join(Calificacion_.asignaturaFK);
            Join<Calificacion, CursoAcademico> join7 = c.join(Calificacion_.cursoFK);


            Predicate c3 = queryBuilder.equal(c.get(Calificacion_.expeFK), root.get(Calificacion_.expeFK));
            Predicate c4 = queryBuilder.equal(join6.get(Asignatura_.asignaturaID), asignaturaSuperada);
            Predicate c5 = queryBuilder.equal(c.get(Calificacion_.consolidado), true);
            Predicate c6 = queryBuilder.greaterThan(c.get(Calificacion_.nota), "-149");
            Predicate c7 = queryBuilder.lessThan(c.get(Calificacion_.nota), "-201");
            Predicate c8 = queryBuilder.lessThan(join7.get(CursoAcademico_.cursoID), curso);

            subquery1.select(c);
            subquery1.where(c3,c4,c5,c6,c7,c8);


            queryDefinition.where(queryBuilder.and(queryBuilder.exists(subquery1)),queryBuilder.and(c1,c2));

            queryDefinition.select(
                    queryBuilder.construct(FuturosEstudiantesAsignatura.class,
                            join3.get(Alumno_.alumnoID),
                            join.get(Expediente_.expeID),
                            join8.get(PmatAlumnoInscripcion_.idInscripcionAlumno),
                            join12.get(Candidato_.correoElectronico),
                            join5.get(Asignatura_.nombre),
                            join4.get(CursoAcademico_.cursoID)
                            , join3.get(Alumno_.uid)
                    ));

            log.info(getEntityManager().createQuery(queryDefinition).getResultList().toString());
            return this.getEntityManager().createQuery(queryDefinition).getResultList();
        } catch (Exception ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, ex.getMessage(), ex);

        }
        return listaFuturosEstudiantes;
    }


    public List<FuturosEstudiantesAsignatura> getListaAsigRestrErrorRenovacionColectivo(int asignaturaSuspensa, String curso, int asignaturaMatricular) {
        List<FuturosEstudiantesAsignatura> listaFuturosEstudiantes = null;

        try{

            CriteriaBuilder queryBuilder = em.getCriteriaBuilder();
            CriteriaQuery<FuturosEstudiantesAsignatura> queryDefinition = queryBuilder.createQuery(FuturosEstudiantesAsignatura.class);
            Root<Calificacion> root = queryDefinition.from(Calificacion.class);
            Join<Calificacion, Expediente> join = root.join(Calificacion_.expeFK);
            Join<Expediente, Alumno> join3 = join.join(Expediente_.alumnoFK);
            Join<Calificacion, Asignatura> join5 = root.join(Calificacion_.asignaturaFK);
            Join<Expediente, PmatAlumnoInscripcion> join8 = join.join(Expediente_.lastInscripcionFK);
            Join<Calificacion, CursoAcademico> join7 = root.join(Calificacion_.cursoFK);
            Join<PmatAlumnoInscripcion, ProcesoMatricula> join9 = join8.join(PmatAlumnoInscripcion_.procesoMatricula);
            Join<ProcesoMatricula, CursoAcademico> join10 = join9.join(ProcesoMatricula_.cursoID);
            Join<Alumno, Candidato> join12 = join3.join(Alumno_.candidatoFK);


            Predicate c1 = queryBuilder.equal(join5.get(Asignatura_.asignaturaID), asignaturaSuspensa);
            Predicate c2 = queryBuilder.greaterThan(root.get(Calificacion_.nota), "-100");
            Predicate c3 = queryBuilder.lessThan(root.get(Calificacion_.nota), "-149");
            Predicate c4 = queryBuilder.lessThan(join7.get(CursoAcademico_.cursoID), curso);


            //Subquery para verificar que no exista
            Subquery<Calificacion> subquery1 = queryDefinition.subquery(Calificacion.class);
            Root<Calificacion> c = subquery1.from(Calificacion.class);

            Predicate c5 = queryBuilder.equal(c.get(Calificacion_.expeFK), root.get(Calificacion_.expeFK));
            Predicate c6 = queryBuilder.equal(c.get(Calificacion_.asignaturaFK), root.get(Calificacion_.asignaturaFK));
            Predicate c7 = queryBuilder.greaterThan(c.get(Calificacion_.numeconv), root.get(Calificacion_.numeconv));

            subquery1.select(c);
            subquery1.where(c5,c6,c7);

            //Subquery para verificar que no exista la asignatura a matricular
            Subquery<Calificacion> subquery2 = queryDefinition.subquery(Calificacion.class);
            Root<Calificacion> ca = subquery2.from(Calificacion.class);
            Join<Calificacion, Asignatura> join6 = ca.join(Calificacion_.asignaturaFK);
            Join<Calificacion, CursoAcademico> join11 = ca.join(Calificacion_.cursoFK);

            Predicate c8 = queryBuilder.equal(ca.get(Calificacion_.expeFK), root.get(Calificacion_.expeFK));
            Predicate c9 = queryBuilder.equal(join6.get(Asignatura_.asignaturaID), asignaturaMatricular);
            Predicate c10 = queryBuilder.equal(join11.get(CursoAcademico_.cursoID), curso);


            subquery2.select(ca);
            subquery2.where(c8,c9,c10);





            queryDefinition.where(queryBuilder.and(c1,c2,c3,c4), queryBuilder.not(queryBuilder.exists(subquery1)), queryBuilder.and(queryBuilder.exists(subquery2)));

            queryDefinition.select(
                    queryBuilder.construct(FuturosEstudiantesAsignatura.class,
                            join3.get(Alumno_.alumnoID),
                            join.get(Expediente_.expeID),
                            join8.get(PmatAlumnoInscripcion_.idInscripcionAlumno),
                            join12.get(Candidato_.correoElectronico),
                            join5.get(Asignatura_.nombre),
                            join10.get(CursoAcademico_.cursoID)
                            , join3.get(Alumno_.uid)
                    ));

            log.info(getEntityManager().createQuery(queryDefinition).getResultList().toString());
            return this.getEntityManager().createQuery(queryDefinition).getResultList();
        } catch (Exception ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, ex.getMessage(), ex);

        }
        return listaFuturosEstudiantes;
    }


    @Transactional
    public boolean EliminarInscripcionFuturosEstudiantes(int alumnoId, int idins, String cursoAcad) {
        boolean resultado = false;

        try {
            Query query = this.getEntityManager().createNativeQuery(" EXEC [mat].[ResetFuturosEstudiantesRenovacion] ?, ?, ?");
            query.setParameter(1, alumnoId);
            query.setParameter(2, idins);
            query.setParameter(3, cursoAcad);
            int result = query.executeUpdate();
            resultado = (result  > 0);

        } catch (Exception ex) {
            log.error(ex.getMessage(),ex);
            resultado = false;
        }
        return resultado;

    }

    @Transactional
    public boolean ResetFuturosEstudiantes(int alumnoId, int idins, String cursoAcad) {
        boolean resultado = false;

        try {
            Query query = this.getEntityManager().createNativeQuery(" EXEC [mat].[ResetFuturosEstudiantesDECA] ?, ?, ?");
            query.setParameter(1, alumnoId);
            query.setParameter(2, idins);
            query.setParameter(3, cursoAcad);

            int result = query.executeUpdate();
            resultado = (result  > 0);

        } catch (Exception ex) {
            log.error(ex.getMessage(),ex);
            resultado = false;
        }
        return resultado;

    }
}
