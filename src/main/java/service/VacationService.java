package service;

import exceptions.InvalidDestinationException;
import exceptions.InvalidVacationPackageException;
import model.Destination;
import model.VacationPackage;
import repository.VacationRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.UUID;

public class VacationService implements ServiceI<VacationPackage> {

    private final VacationRepository vacationRepository;

    public VacationService() {
        this.vacationRepository = new VacationRepository();
    }

    public void insert(VacationPackage vacationPackage) throws Exception {
        vacationRepository.insert(vacationPackage);
    }

    public VacationPackage findById(UUID id) throws Exception {
        return vacationRepository.findById(id);
    }

    public ArrayList<VacationPackage> dbSet() throws Exception {
        var vacationPackages = new ArrayList<VacationPackage>();
        vacationRepository.dbSet().forEach(i -> {
            if (i != null) {
                vacationPackages.add(i);
            }
        });
        return vacationPackages;
    }

    public void update(VacationPackage vacationPackage) throws Exception {
        vacationRepository.update(vacationPackage);
    }

    public void delete(UUID id) throws Exception {
        vacationRepository.delete(id);
    }

    public ArrayList<VacationPackage> getVacationPackagesByDestinationName(String destinationName) throws Exception {
        var destinationService = new DestinationService();
        var destination = destinationService.getDestinationByName(destinationName);
        return new ArrayList<>(dbSet().stream().filter(i -> i.getDestination()
                .getDestinationId().equals(destination.getDestinationId())).toList());
    }

    public void tryAddNewVacationPackage(String vacName, String vacDetails, String vacPrice, LocalDate startPeriod,
                                         LocalDate endPeriod, String capacity, Destination destination) throws InvalidVacationPackageException, Exception {
        getErrors(vacName, vacDetails, vacPrice, startPeriod, endPeriod, capacity, destination);
        float parsedPrice = Float.parseFloat(vacPrice);
        int parsedCapacity = Integer.parseInt(capacity);
        var vacationToAdd = new VacationPackage(vacName, vacDetails, parsedPrice,
                startPeriod, endPeriod, parsedCapacity, destination);
        vacationRepository.insert(vacationToAdd);
    }

    public void tryUpdateVacationPackage(UUID id, String vacName, String vacDetails, String vacPrice, LocalDate startPeriod,
                                         LocalDate endPeriod, String capacity, Destination destination) throws InvalidVacationPackageException, Exception {
        try {
            getErrors(vacName, vacDetails, vacPrice, startPeriod, endPeriod, capacity, destination);
            float parsedPrice = Float.parseFloat(vacPrice);
            int parsedCapacity = Integer.parseInt(capacity);
            var vacationToAdd = new VacationPackage(id, vacName, vacDetails, parsedPrice,
                    startPeriod, endPeriod, parsedCapacity, destination);
            vacationRepository.update(vacationToAdd);
        } catch (InvalidDestinationException ex) {
            throw new InvalidVacationPackageException(ex.getMessage());
        }
    }

    private void getErrors(String vacName, String vacDetails, String vacPrice, LocalDate startPeriod,
                           LocalDate endPeriod, String capacity, Destination destination) {
        if (vacName.equals("")) {
            throw new InvalidVacationPackageException("Empty Vacation Name");
        }
        if (vacName.length() > 255) {
            throw new InvalidVacationPackageException("Vacation Name exceeds 255 characters");
        }
        if (vacDetails.equals("")) {
            throw new InvalidVacationPackageException("Empty Vacation Extra Details");
        }
        if (vacDetails.length() > 255) {
            throw new InvalidVacationPackageException("Vacation Details exceeds 255 characters");
        }
        if (vacPrice.equals("")) {
            throw new InvalidVacationPackageException("Empty Vacation Price");
        }
        try {
            var parsedPrice = Float.parseFloat(vacPrice);
            if (parsedPrice <= 0) {
                throw new InvalidVacationPackageException("Invalid Price. Cannot be 0 or negative");
            }
        } catch (NumberFormatException ex) {
            throw new InvalidVacationPackageException("Cannot Parse the Price correctly");
        }
        if (startPeriod == null) {
            throw new InvalidVacationPackageException("Empty Start Date");
        }
        if (endPeriod == null) {
            throw new InvalidVacationPackageException("Empty End Date");
        }
        if (startPeriod.isAfter(endPeriod)) {
            throw new InvalidVacationPackageException("Start Date cannot be later than the End Date");
        }
        if (capacity.equals("")) {
            throw new InvalidVacationPackageException("Empty Vacation Capacity");
        }
        try {
            var parsedCapacity = Integer.parseInt(capacity);
            if (parsedCapacity <= 0) {
                throw new InvalidVacationPackageException("Invalid Capacity. Cannot be 0 or negative");
            }
        } catch (NumberFormatException ex) {
            throw new InvalidVacationPackageException("Cannot Parse the Capacity correctly");
        }
        if (destination == null) {
            throw new InvalidVacationPackageException("Something happened to the destination selected");
        }
    }
}
