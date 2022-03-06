package service;

import model.Destination;
import repository.DestinationRepository;

import java.util.ArrayList;
import java.util.UUID;

public class DestinationService implements ServiceI<Destination> {

    private final DestinationRepository destinationRepository;

    public DestinationService() {
        this.destinationRepository= new DestinationRepository();
    }

    public void insert(Destination destination) {
        destinationRepository.insert(destination);
    }

    public Destination findById(UUID id) {
        return destinationRepository.findById(id);
    }

    public ArrayList<Destination> dbSet() {
        var destinations = new ArrayList<Destination>();
        destinationRepository.dbSet().forEach(i -> {
            if (i != null) {
                destinations.add(i);
            }
        });
        return destinations;
    }

    public void update(Destination destination) {
        destinationRepository.update(destination);
    }

    public void delete(UUID id) {
        destinationRepository.delete(id);
    }
}
