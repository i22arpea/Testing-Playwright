package matricula.dao.mat;

import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import matricula.ejb.matricula.dto.FuturosEstudiantes;

import model.acad.estudios.TitulacionTipo;
import model.acad.estudios.TitulacionTipo_;

import model.adm.Candidato;
import model.adm.Candidato_;
import model.crm.Alumno;
import model.crm.Alumno_;

import model.expe.*;
import model.mat.*;
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
public class FuturosEstudiantesTFFacade {


    @PersistenceContext
    private EntityManager em;

    public EntityManager getEntityManager() {
        return em;
    }


    public List<FuturosEstudiantes> getListaFuturosEstudiantesTF(String tipoTitulacion, String curso, int solo_TF, int tipoProcesoMatricula) {
        List<FuturosEstudiantes> listaFuturosEstudiantes = null;

        try{

            CriteriaBuilder queryBuilder = em.getCriteriaBuilder();
            CriteriaQuery<FuturosEstudiantes> queryDefinition = queryBuilder.createQuery(FuturosEstudiantes.class);
            Root<ExpedientesCurso> root = queryDefinition.from(ExpedientesCurso.class);
            Join<ExpedientesCurso, ExpedientesCursoPK> join = root.join(ExpedientesCurso_.expedientesCursoPK);
            Join<ExpedientesCurso, PmatAlumnoInscripcion> join2 = root.join(ExpedientesCurso_.pmatAlumnoInscripcion);
            Join<PmatAlumnoInscripcion, ProcesoMatricula> join3 = join2.join(PmatAlumnoInscripcion_.procesoMatricula);
            Join<ProcesoMatricula, TitulacionTipo> join5 = join3.join(ProcesoMatricula_.tipoID);
            Join<ProcesoMatricula, TipoProcesoMatricula> join6 = join3.join(ProcesoMatricula_.idTipoMatricula);
            Join<PmatAlumnoInscripcion, Alumno> join4 = join2.join(PmatAlumnoInscripcion_.alumnoFK);
            Join<Alumno, Candidato> join9 = join4.join(Alumno_.candidatoFK);
            Join<PmatAlumnoInscripcion, Expediente> join7 = join2.join(PmatAlumnoInscripcion_.expeFK);


            Predicate c1 = queryBuilder.equal(join.get(ExpedientesCursoPK_.cursoFK), curso);
            Predicate c2 = queryBuilder.greaterThan(root.get(ExpedientesCurso_.creditosPendientes), BigDecimal.valueOf(0));
            Predicate c3 = queryBuilder.equal(root.get(ExpedientesCurso_.soloTF), solo_TF);
            Predicate c4 = queryBuilder.equal(root.get(ExpedientesCurso_.egresado), 0);
            Predicate c5 = queryBuilder.equal(root.get(ExpedientesCurso_.tipoFK), tipoTitulacion);
            Predicate c6 = queryBuilder.equal(join4.get(Alumno_.alumnoID), root.get(ExpedientesCurso_.alumnoFK));
            Predicate c7 = queryBuilder.equal(join5.get(TitulacionTipo_.tipoID), tipoTitulacion);
            Predicate c8 = queryBuilder.equal(join6.get(TipoProcesoMatricula_.idTipoMatricula), tipoProcesoMatricula);
            Predicate c9 = queryBuilder.equal(join7.get(Expediente_.activo), true);



            queryDefinition.where(queryBuilder.and(c1,c2,c3,c4,c5,c6,c7,c8,c9));

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
                            join9.get(Candidato_.correoElectronico),
                            join4.get(Alumno_.uid)
                    ));

            log.info("listado de resultados: "+getEntityManager().createQuery(queryDefinition).getResultList().toString());
            return this.getEntityManager().createQuery(queryDefinition).getResultList();
        } catch (Exception ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, ex.getMessage(), ex);
            log.info("eerror: "+ex.getMessage());

        }
        return listaFuturosEstudiantes;
    }

    public List<FuturosEstudiantes> getListaFuturosEstudiantesTFMod(String tipoTitulacion, String curso, int solo_TF, int tipoProcesoMatricula, int matriculaAnterior) {
        List<FuturosEstudiantes> listaFuturosEstudiantes = null;

        try{

            CriteriaBuilder queryBuilder = em.getCriteriaBuilder();
            CriteriaQuery<FuturosEstudiantes> queryDefinition = queryBuilder.createQuery(FuturosEstudiantes.class);
            Root<ExpedientesCurso> root = queryDefinition.from(ExpedientesCurso.class);
            Join<ExpedientesCurso, ExpedientesCursoPK> join = root.join(ExpedientesCurso_.expedientesCursoPK);
            Join<ExpedientesCurso, PmatAlumnoInscripcion> join2 = root.join(ExpedientesCurso_.pmatAlumnoInscripcion);
            Join<PmatAlumnoInscripcion, ProcesoMatricula> join3 = join2.join(PmatAlumnoInscripcion_.procesoMatricula);
            Join<PmatAlumnoInscripcion, ProcesoMatricula> join7 = join2.join(PmatAlumnoInscripcion_.procesoModificacionFK);
            Join<ProcesoMatricula, TitulacionTipo> join5 = join3.join(ProcesoMatricula_.tipoID);
            Join<ProcesoMatricula, TipoProcesoMatricula> join6 = join3.join(ProcesoMatricula_.idTipoMatricula);
            Join<ProcesoMatricula, TipoProcesoMatricula> join8 = join7.join(ProcesoMatricula_.idTipoMatricula);
            Join<PmatAlumnoInscripcion, Alumno> join4 = join2.join(PmatAlumnoInscripcion_.alumnoFK);
            Join<Alumno, Candidato> join9 = join4.join(Alumno_.candidatoFK);
            Join<PmatAlumnoInscripcion, Expediente> join10 = join2.join(PmatAlumnoInscripcion_.expeFK);


            Predicate c1 = queryBuilder.equal(join.get(ExpedientesCursoPK_.cursoFK), curso);
            Predicate c2 = queryBuilder.greaterThan(root.get(ExpedientesCurso_.creditosPendientes), BigDecimal.valueOf(0));
            Predicate c3 = queryBuilder.equal(root.get(ExpedientesCurso_.soloTF), solo_TF);
            Predicate c4 = queryBuilder.equal(root.get(ExpedientesCurso_.egresado), 0);
            Predicate c5 = queryBuilder.equal(root.get(ExpedientesCurso_.tipoFK), tipoTitulacion);
            Predicate c6 = queryBuilder.equal(join4.get(Alumno_.alumnoID), root.get(ExpedientesCurso_.alumnoFK));
            Predicate c7 = queryBuilder.equal(join5.get(TitulacionTipo_.tipoID), tipoTitulacion);
            Predicate c8 = queryBuilder.equal(join6.get(TipoProcesoMatricula_.idTipoMatricula), matriculaAnterior);
            Predicate c9 = queryBuilder.equal(join8.get(TipoProcesoMatricula_.idTipoMatricula), tipoProcesoMatricula);
            Predicate c10 = queryBuilder.equal(join10.get(Expediente_.activo), true);



            queryDefinition.where(queryBuilder.and(c1,c2,c3,c4,c5,c6,c7,c8,c9,c10));

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
                            join9.get(Candidato_.correoElectronico),
                            join4.get(Alumno_.uid)
                    ));

            log.info(getEntityManager().createQuery(queryDefinition).getResultList().toString());
            return this.getEntityManager().createQuery(queryDefinition).getResultList();
        } catch (Exception ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, ex.getMessage(), ex);

        }
        return listaFuturosEstudiantes;
    }

    @Transactional
    public boolean ResetFuturosEstudianteTF(int alumnoId, int idins, String cursoAcad, char tipoTitulacion) {
        boolean resultado = false;

        try {
            Query query = this.getEntityManager().createNativeQuery(" EXEC [mat].[ResetFuturosEstudiantesTFe] ?, ?, ?, ?");
            query.setParameter(1, alumnoId);
            query.setParameter(2, idins);
            query.setParameter(3, cursoAcad);
            query.setParameter(4, tipoTitulacion);
            int result = query.executeUpdate();
            resultado = (result  > 0);

        } catch (Exception ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, ex.getMessage(), ex);
            resultado = false;
        }
        return resultado;

    }

}
