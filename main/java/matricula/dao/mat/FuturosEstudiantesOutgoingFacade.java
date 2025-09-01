package matricula.dao.mat;

import lombok.extern.java.Log;
import matricula.ejb.matricula.dto.FuturosEstudiantes;
import matricula.ejb.matricula.dto.FuturosEstudiantesOutgoing;
import model.acad.estudios.Titulacion;
import model.acad.estudios.Titulacion_;
import model.acad.rrii.*;
import model.adm.Candidato;
import model.adm.Candidato_;
import model.crm.Alumno;
import model.crm.Alumno_;
import model.dbo.CursoAcademico;
import model.dbo.CursoAcademico_;
import model.expe.*;

import org.springframework.stereotype.Service;


import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.*;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


@Log
@Service
public class FuturosEstudiantesOutgoingFacade {


    @PersistenceContext
    private EntityManager em;

    public EntityManager getEntityManager() {
        return em;
    }


    public List<FuturosEstudiantesOutgoing> getListaFuturosEstudiantesOutgoing(String curso) {
        List<FuturosEstudiantesOutgoing> listaFuturosEstudiantes = null;

        try{

        CriteriaBuilder queryBuilder = em.getCriteriaBuilder();
        CriteriaQuery<FuturosEstudiantesOutgoing> queryDefinition = queryBuilder.createQuery(FuturosEstudiantesOutgoing.class);
        Root<EstanciaOut> root = queryDefinition.from(EstanciaOut.class);
        Join<EstanciaOut, EstanciaOutEstado> join = root.join(EstanciaOut_.estadoFK);
        Join<EstanciaOut, CursoAcademico> join2 = root.join(EstanciaOut_.cursoFK);
        Join<EstanciaOut, Expediente> join3 = root.join(EstanciaOut_.expeFK);
        Join<Expediente, Alumno> join4 = join3.join(Expediente_.alumnoFK);
        Join<Expediente, Titulacion> join5 = join3.join(Expediente_.titulacionFK);
            Join<Alumno, Candidato> join6 = join4.join(Alumno_.candidatoFK);


        Predicate c1 = queryBuilder.equal(join2.get(CursoAcademico_.cursoID), curso);
        Predicate c2 = queryBuilder.equal(join.get(EstanciaOutEstado_.estadoID), 3);
        Predicate c3 = queryBuilder.isNotNull(root.get(EstanciaOut_.laVigenteFK));




            queryDefinition.where(queryBuilder.and(c1,c2,c3));

        queryDefinition.select(
                queryBuilder.construct(FuturosEstudiantesOutgoing.class,
                        join2.get(CursoAcademico_.cursoID),
                        join4.get(Alumno_.alumnoID),
                        join5.get(Titulacion_.titulacionID),
                        join6.get(Candidato_.correoElectronico)
                        , join4.get(Alumno_.uid)
                ));

        log.info(getEntityManager().createQuery(queryDefinition).getResultList().toString());
        return this.getEntityManager().createQuery(queryDefinition).getResultList();
        } catch (Exception ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, ex.getMessage(), ex);

        }
        return listaFuturosEstudiantes;
    }

}
