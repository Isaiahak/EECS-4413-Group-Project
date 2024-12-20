package com.example.BidlySignUpService.model;
import jakarta.persistence.*;

//User Information Object. This class defines the table structure of the userInfo table, and is used to perform DB operations
@Entity
@Table(name = "userinfo")
public class UserInfo {
    @Id
    @Column(name = "username")
    private String username;

    private String firstName;
    private String lastName;
    private String street;
    private String city;
    private String province;
    private String zipcode;

    public String getUsername() {
        return username;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getStreet() {
        return street;
    }

    public String getCity() {
        return city;
    }

    public String getProvince() {
        return province;
    }

    public String getZipcode() {
        return zipcode;
    }


    public void setUsername(String username) {
        this.username = username;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

}
