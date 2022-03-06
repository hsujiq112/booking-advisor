package repository;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.UUID;

public abstract class RepositoryBase<T> {

    private final EntityManagerFactory entityManagerFactory =
            Persistence.createEntityManagerFactory("lab.SD.assignment.1");

    private final Class<T> model = (Class<T>) ((ParameterizedType) getClass()
            .getGenericSuperclass()).getActualTypeArguments()[0];

    public void insert(T param) {
        var em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();
        em.persist(param);
        em.getTransaction().commit();
        em.close();
    }

    public T findById(UUID id) {
        var em = entityManagerFactory.createEntityManager();
        return em.find(model, id);
    }

    public List<T> dbSet() {
        var em = entityManagerFactory.createEntityManager();
        var cb = em.getCriteriaBuilder();
        var cq = cb.createQuery(model);
        var re = cq.from(model);
        var getAll = cq.select(re);
        var getAllQuery = em.createQuery(getAll);
        return getAllQuery.getResultList();
    }

    private void update(T model) {
        var em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();
        em.merge(model);
        em.getTransaction().commit();
        em.close();
    }

    public void delete(UUID id) {
        var em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();
        var entity = em.find(model, id);
        em.remove(entity);
        em.getTransaction().commit();
        em.close();
    }
}
