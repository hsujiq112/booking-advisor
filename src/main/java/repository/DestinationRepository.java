package repository;

import model.Destination;

public class DestinationRepository extends RepositoryBase<Destination> {

    public Destination getDestinationByName(String destinationName) {
        var em = getEntityManagerFactory().createEntityManager();
        return (Destination) em.createQuery("SELECT d FROM Destination d WHERE d.destinationName = :destName")
                .setParameter("destName", destinationName).getSingleResult();
    }

}
