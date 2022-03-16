package model;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "vacation_package")
public class VacationPackage {

    @Id
    @Type(type = "uuid-char")
    private UUID vacationPackageId;

    @Column
    private String vacationName;

    @Column
    private String extraDetails;

    @Column
    private Float vacationPrice;

    @Column
    private LocalDate startPeriod;

    @Column
    private LocalDate endPeriod;

    @Column
    private Integer vacationCapacity;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "vacation_package_user_join", joinColumns = @JoinColumn(name = "vacationPackageId"), inverseJoinColumns = @JoinColumn(name = "userId"))
    private List<User> vacationPackageUsers;

    @ManyToOne
    @JoinColumn(name = "destinationId")
    private Destination destination;

    public VacationPackageStatusEnum getStatusEnumForVacation() {
        var userCount = vacationPackageUsers.size();
        if (userCount == 0) {
            return VacationPackageStatusEnum.NOT_BOOKED;
        }
        return userCount < vacationCapacity ? VacationPackageStatusEnum.IN_PROGRESS : VacationPackageStatusEnum.BOOKED;
    }

    public VacationPackage(UUID vacationPackageId, String vacationName, String extraDetails, Float vacationPrice, LocalDate startPeriod, LocalDate endPeriod, Integer vacationCapacity, Destination destination, List<User> vacationPackageUsers) {
        this.vacationPackageId = vacationPackageId;
        this.vacationName = vacationName;
        this.extraDetails = extraDetails;
        this.vacationPrice = vacationPrice;
        this.startPeriod = startPeriod;
        this.endPeriod = endPeriod;
        this.vacationCapacity = vacationCapacity;
        this.destination = destination;
        this.vacationPackageUsers = vacationPackageUsers == null ? new ArrayList<>() : vacationPackageUsers;
    }

    public VacationPackage(String vacationName, String extraDetails, Float vacationPrice, LocalDate startPeriod, LocalDate endPeriod, Integer vacationCapacity, Destination destination) {
        this.vacationPackageId = UUID.randomUUID();
        this.vacationName = vacationName;
        this.extraDetails = extraDetails;
        this.vacationPrice = vacationPrice;
        this.startPeriod = startPeriod;
        this.endPeriod = endPeriod;
        this.vacationCapacity = vacationCapacity;
        this.destination = destination;
        this.vacationPackageUsers = new ArrayList<>();
    }

    public VacationPackage() {

    }

    public UUID getVacationPackageId() {
        return vacationPackageId;
    }

    public String getVacationName() {
        return vacationName;
    }

    public String getExtraDetails() {
        return extraDetails;
    }

    public Float getVacationPrice() {
        return vacationPrice;
    }

    public LocalDate getStartPeriod() {
        return startPeriod;
    }

    public LocalDate getEndPeriod() {
        return endPeriod;
    }

    public Integer getVacationCapacity() {
        return vacationCapacity;
    }

    public List<User> getVacationPackageUsers() {
        return vacationPackageUsers;
    }

    public Destination getDestination() {
        return destination;
    }
}

