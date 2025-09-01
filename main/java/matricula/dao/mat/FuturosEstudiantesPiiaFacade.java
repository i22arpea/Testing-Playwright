package matricula.dao.mat;

import lombok.extern.java.Log;
import matricula.ejb.matricula.dto.FuturosEstudiantes;
import matricula.ejb.matricula.dto.FuturosEstudiantesAsignatura;
import model.acad.estudios.Asignatura;
import model.acad.estudios.Asignatura_;
import model.adm.Candidato;
import model.adm.Candidato_;
import model.adm.GruposTipoTitulacion;
import model.adm.GruposTipoTitulacion_;
import model.crm.Alumno;
import model.crm.Alumno_;
import model.dbo.CursoAcademico;
import model.dbo.CursoAcademico_;
import model.expe.*;
import model.mat.PmatAlumnoInscripcion;
import model.mat.PmatAlumnoInscripcion_;
import model.mat.ProcesoMatricula;
import model.mat.ProcesoMatricula_;
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

@Log
@Service
public class FuturosEstudiantesPiiaFacade {


    @PersistenceContext
    private EntityManager em;

    public EntityManager getEntityManager() {
        return em;
    }


    public List<FuturosEstudiantes> getListaFuturosEstudiantesPiia(String tipoTitulacion, String curso, int solo_TF) {
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
            Predicate c3 = queryBuilder.equal(root.get(ExpedientesCurso_.soloTF), solo_TF);
            Predicate c4 = queryBuilder.equal(root.get(ExpedientesCurso_.egresado), 0);
            Predicate c5 = queryBuilder.equal(root.get(ExpedientesCurso_.tipoFK), tipoTitulacion);
            Predicate c6 = queryBuilder.equal(join4.get(Alumno_.alumnoID), root.get(ExpedientesCurso_.alumnoFK));




            queryDefinition.where(queryBuilder.and(c1,c3,c4,c5,c6));

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
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, ex.getMessage(), ex);

        }
        return listaFuturosEstudiantes;
    }

    public List<FuturosEstudiantes> getListaFuturosEstudiantesPiiaColectivo(String tipoTitulacion, String curso, int solo_TF, int colectivo) {
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
            Predicate c3 = queryBuilder.equal(root.get(ExpedientesCurso_.soloTF), solo_TF);
            Predicate c4 = queryBuilder.equal(root.get(ExpedientesCurso_.egresado), 0);
            Predicate c5 = queryBuilder.equal(root.get(ExpedientesCurso_.tipoFK), tipoTitulacion);
            Predicate c6 = queryBuilder.equal(join4.get(Alumno_.alumnoID), root.get(ExpedientesCurso_.alumnoFK));
            Predicate c7 = queryBuilder.equal(join3.get(GruposTipoTitulacion_.idGrupo), colectivo);




            queryDefinition.where(queryBuilder.and(c1,c3,c4,c5,c6,c7));

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
                    ));

            log.info(getEntityManager().createQuery(queryDefinition).getResultList().toString());
            return this.getEntityManager().createQuery(queryDefinition).getResultList();
        } catch (Exception ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, ex.getMessage(), ex);

        }
        return listaFuturosEstudiantes;
    }
}
