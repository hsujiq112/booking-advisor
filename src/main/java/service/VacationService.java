package service;

import model.VacationPackage;
import repository.VacationRepository;

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

    public void update(VacationPackage vacationPackage) {
        vacationRepository.update(vacationPackage);
    }

    public void delete(UUID id) {
        vacationRepository.delete(id);
    }
}
