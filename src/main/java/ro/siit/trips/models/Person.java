package ro.siit.trips.models;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.lang.annotation.Documented;
import java.util.List;
import java.util.Set;

@Entity
public class Person {
    @Column(name = "personid")
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long id;
    private String firstName;
    private String lastName;

    @Column(unique = true)
    @NotNull(message = "You have to insert a username!")
    private String userName;

    @NotNull
    @Pattern(regexp = "^(?=.*?[0-9])(?=.*[A-Za-z]).{10,}$", message = "The password must contain at least 10 characters")
    private String password;
    private String city;
    private String address;

    @Pattern(regexp = "(0)[0-9]{9}", message = "The inserted value must be a valid Romanian phone")
    private String phone;

    @OneToMany(mappedBy = "person", fetch = FetchType.EAGER)
    @Valid
    private Set<Trip> trips;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Set<Trip> getTrips() {
        return trips;
    }

    public void setTrips(Set<Trip> trips) {
        this.trips = trips;
    }


}