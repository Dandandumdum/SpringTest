package com.example.springtest.customers;


public class Customer {
    private String Id;
    private String firstName;
    private String lastName;
    private String country;
    private String postalCode;
    private String phoneNumber;
    private String email;

    public Customer(String id, String firstName, String lastName, String country,
                    String postalCode, String phoneNumber, String email){
        this.setId(id);
        this.setFirstName(firstName);
        this.setLastName(lastName);
        this.setCountry(country);
        this.setPostalCode(postalCode);
        this.setPhoneNumber(phoneNumber);
        this.setEmail(email);

    }
    public Customer(){

    }


    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
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

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
