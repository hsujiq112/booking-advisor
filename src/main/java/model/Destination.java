package model;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "destination")
public class Destination {

    @Id
    @Type(type = "uuid-char")
    private UUID destinationId;

    @Column(nullable = false)
    private String destinationName;

    @OneToMany(mappedBy = "destination")
    private List<VacationPackage> vacationPackages;

    public Destination(UUID destinationId, String destinationName) {
        this.destinationId = destinationId;
        this.destinationName = destinationName;
    }

    public Destination() {

    }
}
