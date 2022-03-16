package repository;

import model.User;

public class UserRepository extends RepositoryBase<User> {
    public User getUserByUsername(String username) {
        var em = getEntityManagerFactory().createEntityManager();
        return (User) em.createQuery("SELECT u FROM User u WHERE u.username = :username")
                .setParameter("username", username).getSingleResult();
    }
}
