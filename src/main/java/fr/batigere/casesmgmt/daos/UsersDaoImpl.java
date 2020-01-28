package fr.batigere.casesmgmt.daos;

import fr.batigere.casesmgmt.entities.CaseEntity;
import fr.batigere.casesmgmt.entities.UserEntity;
import org.eclipse.microprofile.opentracing.Traced;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

@Transactional
@ApplicationScoped
@Traced
public class UsersDaoImpl implements UsersDao {

    @PersistenceContext
    EntityManager em;

    @Override
    public UserEntity findByUsername(String username) {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<UserEntity> criteriaQuery = criteriaBuilder.createQuery(UserEntity.class);
        Root<UserEntity> root = criteriaQuery.from(UserEntity.class);
        criteriaQuery.select(root);
        criteriaQuery.where(criteriaBuilder.equal(root.get("username"), username));
        return em.createQuery(criteriaQuery).getSingleResult();
    }
}
