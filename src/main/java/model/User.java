package model;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "user")
public class User {

    @Id
    @Type(type = "org.hibernate.type.UUIDCharType")
    private UUID userId;

    @Column(unique = true, nullable = false)
    private String emailAddress;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false)
    private Boolean isAdmin;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "vacation_package_user_join", joinColumns = @JoinColumn(name = "userId"), inverseJoinColumns = @JoinColumn(name = "vacationPackageId"))
    private List<VacationPackage> vacationPackageUsers;

    public User(String emailAddress, String firstName, String lastName) {
        this.userId = UUID.randomUUID();
        this.emailAddress = emailAddress;
        this.firstName = firstName;
        this.lastName = lastName;
        this.isAdmin = false;
    }

    public User(UUID id, String emailAddress, String firstName, String lastName, Boolean isAdmin) {
        this.userId = id;
        this.emailAddress = emailAddress;
        this.firstName = firstName;
        this.lastName = lastName;
        this.isAdmin = isAdmin;
    }

    public User() {

    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", emailAddress='" + emailAddress + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", isAdmin=" + isAdmin +
                ", vacationPackageUsers=" + vacationPackageUsers +
                '}';
    }
}
