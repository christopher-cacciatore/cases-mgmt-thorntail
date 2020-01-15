package fr.batigere.casesmgmt.daos;

import fr.batigere.casesmgmt.entities.CaseEntity;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@ApplicationScoped
public class CasesDaoImpl implements CasesDao{

    @PersistenceContext
    EntityManager em;

    @Override
    public List<CaseEntity> findAll() {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<CaseEntity> criteriaQuery = criteriaBuilder.createQuery(CaseEntity.class);
        Root<CaseEntity> root = criteriaQuery.from(CaseEntity.class);
        criteriaQuery.select(root);
        return em.createQuery(criteriaQuery).getResultList();
    }

    @Override
    public CaseEntity retrieve(String id) {
        return em.find(CaseEntity.class, id);
    }

    @Override
    public CaseEntity save(CaseEntity c) {
        CaseEntity createdEntity = em.merge(c);
        return createdEntity;
    }

    @Override
    public boolean delete(String id) {
        CaseEntity toDelete = this.retrieve(id);

        if(toDelete == null){
            return false;
        } else {
            em.remove(toDelete);
            return true;
        }
    }
}
