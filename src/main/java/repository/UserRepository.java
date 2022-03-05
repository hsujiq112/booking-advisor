package repository;

import model.User;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class UserRepository {

    private final EntityManagerFactory entityManagerFactory =
            Persistence.createEntityManagerFactory("lab.SD.assignment.1");

    public void insertUser(User user) {
        var em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();
        em.persist(user);
        em.getTransaction().commit();
        em.close();
    }
}
