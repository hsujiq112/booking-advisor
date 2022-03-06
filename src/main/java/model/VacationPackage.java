package model;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
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
    private String vacationPrice;

    @Column
    private Date startPeriod;

    @Column
    private Date endPeriod;

    @Column
    private Integer vacationCapacity;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "vacation_package_user_join", joinColumns = @JoinColumn(name = "vacationPackageId"), inverseJoinColumns = @JoinColumn(name = "userId"))
    private List<User> vacationPackageUsers;

    @ManyToOne
    @JoinColumn(name = "destinationId")
    private Destination destination;

    public VacationPackageStatusEnum getStatusEnumForVacation() {
        var userCount = vacationPackageUsers.stream().count();
        if (userCount == 0) {
            return VacationPackageStatusEnum.NOT_BOOKED;
        }
        return userCount < vacationCapacity ? VacationPackageStatusEnum.IN_PROGRESS : VacationPackageStatusEnum.BOOKED;
    }

    public VacationPackage(UUID vacationPackageId, String vacationName, String extraDetails, String vacationPrice, Date startPeriod, Date endPeriod, Integer vacationCapacity) {
        this.vacationPackageId = vacationPackageId;
        this.vacationName = vacationName;
        this.extraDetails = extraDetails;
        this.vacationPrice = vacationPrice;
        this.startPeriod = startPeriod;
        this.endPeriod = endPeriod;
        this.vacationCapacity = vacationCapacity;
    }

    public VacationPackage() {

    }
}
