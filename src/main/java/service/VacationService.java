package service;

import exceptions.InvalidUserException;
import exceptions.InvalidVacationPackageException;
import model.Destination;
import model.User;
import model.VacationPackage;
import model.VacationPackageStatusEnum;
import repository.VacationRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.UUID;

public class VacationService implements ServiceI<VacationPackage> {

    private final VacationRepository vacationRepository;

    public VacationService() {
        this.vacationRepository = new VacationRepository();
    }

    public void insert(VacationPackage vacationPackage) {
        vacationRepository.insert(vacationPackage);
    }

    public VacationPackage findById(UUID id) {
        return vacationRepository.findById(id);
    }

    public ArrayList<VacationPackage> dbSet() {
        var vacationPackages = new ArrayList<VacationPackage>();
        vacationRepository.dbSet().forEach(i -> {
            if (i != null) {
                vacationPackages.add(i);
            }
        });
        return vacationPackages;
    }

    public ArrayList<VacationPackage> getAllAvailablePackagesForUser(UUID userID) {
        return new ArrayList<>(dbSet().stream().filter(i ->
                !i.getStatusEnumForVacation().equals(VacationPackageStatusEnum.BOOKED) &&
                !i.getVacationPackageUsers().stream().anyMatch(j -> j.getUserId().equals(userID)))
                .toList());
    }

    public void update(VacationPackage vacationPackage) {
        vacationRepository.update(vacationPackage);
    }

    public void delete(UUID id) {
        vacationRepository.delete(id);
    }

    public ArrayList<VacationPackage> getVacationPackagesByDestinationName(String destinationName) {
        var destinationService = new DestinationService();
        var destination = destinationService.getDestinationByName(destinationName);
        return new ArrayList<>(dbSet().stream().filter(i -> i.getDestination()
                .getDestinationId().equals(destination.getDestinationId())).toList());
    }

    public void tryAddVacationToUser(User user, VacationPackage vacationPackage) {
        tryUpdateVacationPackage(vacationPackage.getVacationPackageId(),
                vacationPackage.getVacationName(), vacationPackage.getExtraDetails(),
                vacationPackage.getVacationPrice().toString(), vacationPackage.getStartPeriod(),
                vacationPackage.getEndPeriod(), vacationPackage.getVacationCapacity().toString(),
                vacationPackage.getDestination(), new ArrayList<>(vacationPackage.getVacationPackageUsers()), user, true);
    }

    public void tryRemoveVacationFromUser(User user, VacationPackage vacationPackage) {
        tryUpdateVacationPackage(vacationPackage.getVacationPackageId(),
                vacationPackage.getVacationName(), vacationPackage.getExtraDetails(),
                vacationPackage.getVacationPrice().toString(), vacationPackage.getStartPeriod(),
                vacationPackage.getEndPeriod(), vacationPackage.getVacationCapacity().toString(),
                vacationPackage.getDestination(), new ArrayList<>(vacationPackage.getVacationPackageUsers()), user, false);
    }

    public void tryAddNewVacationPackage(String vacName, String vacDetails, String vacPrice, LocalDate startPeriod,
                                         LocalDate endPeriod, String capacity, Destination destination) {
        getErrors(vacName, vacDetails, vacPrice, startPeriod, endPeriod, capacity, destination, null, null, false, false);
        float parsedPrice = Float.parseFloat(vacPrice);
        int parsedCapacity = Integer.parseInt(capacity);
        var vacationToAdd = new VacationPackage(vacName, vacDetails, parsedPrice,
                startPeriod, endPeriod, parsedCapacity, destination);
        vacationRepository.insert(vacationToAdd);
    }

    public void tryUpdateVacationPackage(UUID id, String vacName, String vacDetails, String vacPrice, LocalDate startPeriod,
                                         LocalDate endPeriod, String capacity, Destination destination, ArrayList<User> vacationPackageUsers,
                                         User user, boolean isForAdd) {
        getErrors(vacName, vacDetails, vacPrice, startPeriod, endPeriod, capacity, destination, vacationPackageUsers, user, true, isForAdd);
        float parsedPrice = Float.parseFloat(vacPrice);
        int parsedCapacity = Integer.parseInt(capacity);
        if (user != null) {
            if (isForAdd) {
                vacationPackageUsers.add(user);
            } else {
                vacationPackageUsers = new ArrayList<>(vacationPackageUsers.stream()
                        .filter(i -> !i.getUserId().equals(user.getUserId())).toList());
            }
        }
        var vacationToUpdate = new VacationPackage(id, vacName, vacDetails, parsedPrice,
                startPeriod, endPeriod, parsedCapacity, destination, vacationPackageUsers);
        vacationRepository.update(vacationToUpdate);
    }

    private void getErrors(String vacName, String vacDetails, String vacPrice, LocalDate startPeriod, LocalDate endPeriod,
                           String capacity, Destination destination, ArrayList<User> vacationPackageUsers, User user, boolean isForUpdate, boolean isForAdd) {
        int parsedCapacity;
        float parsedPrice;
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
            parsedPrice = Float.parseFloat(vacPrice);
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
            parsedCapacity = Integer.parseInt(capacity);
            if (parsedCapacity <= 0) {
                throw new InvalidVacationPackageException("Invalid Capacity. Cannot be 0 or negative");
            }
        } catch (NumberFormatException ex) {
            throw new InvalidVacationPackageException("Cannot Parse the Capacity correctly");
        }
        if (destination == null) {
            throw new InvalidVacationPackageException("Something happened to the destination selected");
        }
        if (isForUpdate) {
            if (user != null) { //add to many-to-many
                if (isForAdd) {
                    if (vacationPackageUsers.size() == parsedCapacity) {
                        throw new InvalidUserException("The Vacation is already fully booked. Try again if a spot becomes free.");
                    }
                    if (vacationPackageUsers.stream().anyMatch(i -> i.getUserId().equals(user.getUserId()))) {
                        throw new InvalidUserException("You have already booked to this vacation. Do you want to pay double price?");
                    }
                } else {
                    if (vacationPackageUsers.size() == 0) {
                        throw new RuntimeException("This should not be possible");
                    }
                }
            } else { // admin update
                if (vacationPackageUsers.size() > parsedCapacity) {
                    throw new InvalidUserException("You already have a lot of people booked..." +
                            " you cannot change capacity to that number");
                }
            }
        }
    }
}
