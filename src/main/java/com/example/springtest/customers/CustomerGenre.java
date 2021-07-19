package com.example.springtest.customers;
public class CustomerGenre extends Customer {
    private String genre;

    public CustomerGenre(String id, String firstName, String lastName, String country,
                         String postalCode, String phoneNumber, String email, String genre) {
        super(id, firstName, lastName, country, postalCode, phoneNumber, email);
        this.setGenre(genre);


    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }
}