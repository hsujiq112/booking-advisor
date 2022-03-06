package service;

import exceptions.InvalidDestinationException;
import model.Destination;
import repository.DestinationRepository;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;
import java.util.regex.Pattern;

public class DestinationService implements ServiceI<Destination> {

    private final DestinationRepository destinationRepository;

    public DestinationService() {
        this.destinationRepository= new DestinationRepository();
    }

    public void insert(Destination destination) throws Exception {
        destinationRepository.insert(destination);
    }

    public Destination findById(UUID id) throws Exception {
        return destinationRepository.findById(id);
    }

    public ArrayList<Destination> dbSet() throws Exception {
        var destinations = new ArrayList<Destination>();
        destinationRepository.dbSet().forEach(i -> {
            if (i != null) {
                destinations.add(i);
            }
        });
        return destinations;
    }

    public void update(Destination destination) throws Exception {
        destinationRepository.update(destination);
    }

    public void delete(UUID id) throws Exception {
        destinationRepository.delete(id);
    }

    public void tryAddNewDestination(String destinationName) throws InvalidDestinationException, Exception {
        getErrors(destinationName);
        var destinationToAdd = new Destination(destinationName);
        destinationRepository.insert(destinationToAdd);
    }

    public void tryUpdateDestination(UUID id, String destinationName) throws InvalidDestinationException, Exception {
        getErrors(destinationName);
        var destinationToUpdate = new Destination(id, destinationName);
        destinationRepository.update(destinationToUpdate);
    }

    private void getErrors(String destinationName) throws InvalidDestinationException {
        var pattern = Pattern.compile("[^\\sa-zAZ_-]", Pattern.CASE_INSENSITIVE);
        if (destinationName.equals("")) {
            throw new InvalidDestinationException("Empty destination");
        }
        if (destinationName.length() > 255) {
            throw new InvalidDestinationException("Destination exceeds 255 characters");
        }
        if (pattern.matcher(destinationName).find()) {
            throw new InvalidDestinationException("Invalid Characters found in Destination");
        }
    }

    public Destination getDestinationByName(String destName) {
        return destinationRepository.getDestinationByName(destName);
    }
}
