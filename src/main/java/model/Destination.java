package model;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "destination")
public class Destination {

    @Id
    @Type(type = "uuid-char")
    private UUID destinationId;

    @Column(nullable = false, unique = true)
    private String destinationName;

    @OneToMany(mappedBy = "destination", cascade = CascadeType.ALL)
    private List<VacationPackage> vacationPackages;

    public Destination(UUID destinationId, String destinationName) {
        this.destinationId = destinationId;
        this.destinationName = destinationName;
    }

    public Destination(String destinationName) {
        this.destinationId = UUID.randomUUID();
        this.destinationName = destinationName;
    }

    public Destination() {

    }

    public UUID getDestinationId() {
        return destinationId;
    }

    public String getDestinationName() {
        return destinationName;
    }

    public List<VacationPackage> getVacationPackages() {
        return vacationPackages;
    }
}
