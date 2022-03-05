package model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Optional;
import java.util.UUID;


@Entity
@Table(name = "user")
public class User {

    @Id
    private UUID id;

    @Column(unique = true, nullable = false)
    private String emailAddress;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column()
    private Boolean isAdmin;

    public User(String emailAddress, String firstName, String lastName) {
        this.id = UUID.randomUUID();
        this.emailAddress = emailAddress;
        this.firstName = firstName;
        this.lastName = lastName;
        this.isAdmin = false;
    }

    public User(UUID id, String emailAddress, String firstName, String lastName, Boolean isAdmin) {
        this.id = id;
        this.emailAddress = emailAddress;
        this.firstName = firstName;
        this.lastName = lastName;
        this.isAdmin = isAdmin;
    }

    public User() {

    }
}
