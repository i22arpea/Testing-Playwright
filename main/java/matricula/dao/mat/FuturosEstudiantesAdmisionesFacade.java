package matricula.dao.mat;

import lombok.extern.slf4j.Slf4j;
import lombok.extern.slf4j.Slf4j;
import matricula.dao.adm.PadmTitulacionListaAdmitidosFacade;
import matricula.ejb.matricula.dto.FuturosEstudiantesAdmisiones;
import model.acad.estudios.Titulacion;
import model.adm.*;
import model.crm.Alumno;
import model.crm.Alumno_;
import model.crm.facilities.Campus;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.*;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Slf4j
@Service

public class FuturosEstudiantesAdmisionesFacade {



    @PersistenceContext
    private EntityManager em;

    public EntityManager getEntityManager() {
        return em;
    }


    public List<FuturosEstudiantesAdmisiones> getListaFuturosEstudiantesAdmisiones(Integer idProceso) {
        List<FuturosEstudiantesAdmisiones> listaFuturosEstudiantesAdmisiones = null;

        try{


            CriteriaBuilder queryBuilder = em.getCriteriaBuilder();
            CriteriaQuery<FuturosEstudiantesAdmisiones> queryDefinition = queryBuilder.createQuery(FuturosEstudiantesAdmisiones.class);
            Root<PadmCandidato> root = queryDefinition.from(PadmCandidato.class);
            Join<PadmCandidato, ProcesoAdmision> join = root.join(PadmCandidato_.procesoAdmision);
            Join<PadmCandidato, Candidato> join2 = root.join(PadmCandidato_.candidato);
            Join<PadmCandidato, PadmTitulacionListaAdmitidos> join3 = root.join(PadmCandidato_.padmTitulacionListaAdmitidosList);
            Predicate c1 = queryBuilder.equal(join3.get(PadmTitulacionListaAdmitidos_.procesoAdmision), join);
            Predicate c2 = queryBuilder.equal(join3.get(PadmTitulacionListaAdmitidos_.candidato), join2);
            Join<PadmCandidato, PadmCandidatoEstado> join4 = root.join(PadmCandidato_.estadoFK);
            Join<PadmTitulacionListaAdmitidos, TitulacionesCurso> join5 = join3.join(PadmTitulacionListaAdmitidos_.titulacionesCurso);
            Join<TitulacionesCurso, Titulacion> join6 = join5.join(TitulacionesCurso_.titulacionFK);
            Join<TitulacionesCurso, Campus> join7 = join5.join(TitulacionesCurso_.campusFK);
            Join<PadmCandidato, PadmCandidatoEstado> join8 = root.join(PadmCandidato_.estadoFK);
            Join<Candidato, Alumno> join9 = join2.join(Candidato_.alumno);

            Predicate c3 = queryBuilder.equal(join8.get(PadmCandidatoEstado_.id), EstadosInscripcion.ADMITIDO.id());
            Predicate c4 = queryBuilder.equal(join.get(ProcesoAdmision_.idProceso), idProceso);
            Predicate c5 = queryBuilder.equal(join3.get(PadmTitulacionListaAdmitidos_.estado), EstadosSolicitud.ADMITIDO.id());
            Predicate c6 = queryBuilder.notEqual(join3.get(PadmTitulacionListaAdmitidos_.matriculado), 0);


            queryDefinition.where(queryBuilder.and(c1, c2, c3, c4, c5, c6));

            queryDefinition.select(
                queryBuilder.construct(FuturosEstudiantesAdmisiones.class,
                        join.get(ProcesoAdmision_.idProceso),
                        join5.get(TitulacionesCurso_.idTitulacionCurso),
                        join2.get(Candidato_.idCandidato),
                        join2.get(Candidato_.correoElectronico),
                        join3.get(PadmTitulacionListaAdmitidos_.estado),
                        join4.get(PadmCandidatoEstado_.nombre),
                        join3.get(PadmTitulacionListaAdmitidos_.inicioMatriculacion),
                        join9.get(Alumno_.uid)

                ));
            return this.getEntityManager().createQuery(queryDefinition).getResultList();

        } catch (Exception ex) {
            log.error(ex.getMessage(),ex);

        }
        return listaFuturosEstudiantesAdmisiones;
    }

    public List<FuturosEstudiantesAdmisiones> getListaFuturosEstudiantesAdmisionesMat(Integer idProceso) {
        List<FuturosEstudiantesAdmisiones> listaFuturosEstudiantesAdmisiones = null;

        try{


            CriteriaBuilder queryBuilder = em.getCriteriaBuilder();
            CriteriaQuery<FuturosEstudiantesAdmisiones> queryDefinition = queryBuilder.createQuery(FuturosEstudiantesAdmisiones.class);
            Root<PadmCandidato> root = queryDefinition.from(PadmCandidato.class);
            Join<PadmCandidato, ProcesoAdmision> join = root.join(PadmCandidato_.procesoAdmision);
            Join<PadmCandidato, Candidato> join2 = root.join(PadmCandidato_.candidato);
            Join<PadmCandidato, PadmTitulacionListaAdmitidos> join3 = root.join(PadmCandidato_.padmTitulacionListaAdmitidosList);
            Predicate c1 = queryBuilder.equal(join3.get(PadmTitulacionListaAdmitidos_.procesoAdmision), join);
            Predicate c2 = queryBuilder.equal(join3.get(PadmTitulacionListaAdmitidos_.candidato), join2);
            Join<PadmCandidato, PadmCandidatoEstado> join4 = root.join(PadmCandidato_.estadoFK);
            Join<PadmTitulacionListaAdmitidos, TitulacionesCurso> join5 = join3.join(PadmTitulacionListaAdmitidos_.titulacionesCurso);
            Join<TitulacionesCurso, Titulacion> join6 = join5.join(TitulacionesCurso_.titulacionFK);
            Join<TitulacionesCurso, Campus> join7 = join5.join(TitulacionesCurso_.campusFK);
            Join<PadmCandidato, PadmCandidatoEstado> join8 = root.join(PadmCandidato_.estadoFK);
            Join<Candidato, Alumno> join9 = join2.join(Candidato_.alumno);

            Predicate c3 = queryBuilder.equal(join8.get(PadmCandidatoEstado_.id), EstadosInscripcion.ADMITIDO.id());
            Predicate c4 = queryBuilder.equal(join3.get(PadmTitulacionListaAdmitidos_.inicioMatriculacion), idProceso);
            Predicate c5 = queryBuilder.equal(join3.get(PadmTitulacionListaAdmitidos_.estado), EstadosSolicitud.ADMITIDO.id());
            Predicate c6 = queryBuilder.equal(join3.get(PadmTitulacionListaAdmitidos_.matriculado), 0);


            queryDefinition.where(queryBuilder.and(c1, c2, c3, c4, c5, c6));

            queryDefinition.select(
                    queryBuilder.construct(FuturosEstudiantesAdmisiones.class,
                            join.get(ProcesoAdmision_.idProceso),
                            join5.get(TitulacionesCurso_.idTitulacionCurso),
                            join2.get(Candidato_.idCandidato),
                            join2.get(Candidato_.correoElectronico),
                            join3.get(PadmTitulacionListaAdmitidos_.estado),
                            join4.get(PadmCandidatoEstado_.nombre),
                            join3.get(PadmTitulacionListaAdmitidos_.inicioMatriculacion),
                            join9.get(Alumno_.uid)

                    ));
            return this.getEntityManager().createQuery(queryDefinition).getResultList();

        } catch (Exception ex) {
            log.error(ex.getMessage(),ex);

        }
        return listaFuturosEstudiantesAdmisiones;
    }

    public List<FuturosEstudiantesAdmisiones> getListaFuturosEstudiantesAdmisionesTitulacion(Integer idProceso, Integer idTitulacion) {
        List<FuturosEstudiantesAdmisiones> listaFuturosEstudiantesAdmisiones = null;

        try{


            CriteriaBuilder queryBuilder = em.getCriteriaBuilder();
            CriteriaQuery<FuturosEstudiantesAdmisiones> queryDefinition = queryBuilder.createQuery(FuturosEstudiantesAdmisiones.class);
            Root<PadmCandidato> root = queryDefinition.from(PadmCandidato.class);
            Join<PadmCandidato, ProcesoAdmision> join = root.join(PadmCandidato_.procesoAdmision);
            Join<PadmCandidato, Candidato> join2 = root.join(PadmCandidato_.candidato);
            Join<PadmCandidato, PadmTitulacionListaAdmitidos> join3 = root.join(PadmCandidato_.padmTitulacionListaAdmitidosList);
            Predicate c1 = queryBuilder.equal(join3.get(PadmTitulacionListaAdmitidos_.procesoAdmision), join);
            Predicate c2 = queryBuilder.equal(join3.get(PadmTitulacionListaAdmitidos_.candidato), join2);
            Join<PadmCandidato, PadmCandidatoEstado> join4 = root.join(PadmCandidato_.estadoFK);
            Join<PadmTitulacionListaAdmitidos, TitulacionesCurso> join5 = join3.join(PadmTitulacionListaAdmitidos_.titulacionesCurso);
            Join<TitulacionesCurso, Titulacion> join6 = join5.join(TitulacionesCurso_.titulacionFK);
            Join<TitulacionesCurso, Campus> join7 = join5.join(TitulacionesCurso_.campusFK);
            Join<PadmCandidato, PadmCandidatoEstado> join8 = root.join(PadmCandidato_.estadoFK);
            Join<Candidato, Alumno> join9 = join2.join(Candidato_.alumno);

            Predicate c3 = queryBuilder.equal(join8.get(PadmCandidatoEstado_.id), EstadosInscripcion.ADMITIDO.id());
            Predicate c7 = queryBuilder.equal(join5.get(TitulacionesCurso_.idTitulacionCurso), idTitulacion);
            Predicate c4 = queryBuilder.equal(join3.get(PadmTitulacionListaAdmitidos_.inicioMatriculacion), idProceso);
            Predicate c5 = queryBuilder.equal(join3.get(PadmTitulacionListaAdmitidos_.estado), EstadosSolicitud.ADMITIDO.id());
            Predicate c6 = queryBuilder.equal(join3.get(PadmTitulacionListaAdmitidos_.matriculado), 0);


            queryDefinition.where(queryBuilder.and(c1, c2, c3, c4, c5, c6, c7));

            queryDefinition.select(
                    queryBuilder.construct(FuturosEstudiantesAdmisiones.class,
                            join.get(ProcesoAdmision_.idProceso),
                            join5.get(TitulacionesCurso_.idTitulacionCurso),
                            join2.get(Candidato_.idCandidato),
                            join2.get(Candidato_.correoElectronico),
                            join3.get(PadmTitulacionListaAdmitidos_.estado),
                            join4.get(PadmCandidatoEstado_.nombre),
                            join3.get(PadmTitulacionListaAdmitidos_.inicioMatriculacion),
                            join9.get(Alumno_.uid)

                    ));
            return this.getEntityManager().createQuery(queryDefinition).getResultList();

        } catch (Exception ex) {
            log.error(ex.getMessage(),ex);

        }
        return listaFuturosEstudiantesAdmisiones;
    }


    @Transactional
    public boolean resetFuturosEstudianteAdmisiones(int candidatoId, int procesoAdmisionId, int titulacionId) {
        boolean resultado = false;

        try {
            Query query = this.getEntityManager().createNativeQuery(" EXEC [mat].[ResetFuturosEstudiantesAdmisiones] ?, ?, ? ");
            query.setParameter(1, candidatoId);
            query.setParameter(2, procesoAdmisionId);
            query.setParameter(3, titulacionId);
            int result = query.executeUpdate();
            resultado = (result  > 0);
            //log.info("resetFutuosEstudiantesAdmisiones: resultado exitoso" + candidatoId + ", padm " + procesoAdmisionId +  ", titulo " + titulacionId );
        } catch (Exception ex) {
            log.error(ex.getMessage(),ex);
            resultado = false;
        }
        return resultado;

    }
}
