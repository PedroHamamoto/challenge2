package br.com.challenge2.model;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Represents an user address stored in the MongoDB
 */
@Document
public class Address {

    private String id;

    @NotEmpty(message = "error.cep.notEmpty")
    private String cep;
    @NotEmpty(message = "error.street.notEmpty")
    private String street;
    @NotEmpty(message = "error.number.notEmpty")
    private String number;
    @NotEmpty(message = "error.state.notEmpty")
    private String state;
    @NotEmpty(message = "error.city.notEmpty")
    private String city;

    private String neighborhood;
    private String complement;

    public Address() {

    }

    public Address(String id, String cep, String street, String number, String state, String city, String neighborhood, String complement) {
        this.id = id;
        this.cep = cep;
        this.street = street;
        this.number = number;
        this.state = state;
        this.city = city;
        this.neighborhood = neighborhood;
        this.complement = complement;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getNeighborhood() {
        return neighborhood;
    }

    public void setNeighborhood(String neighborhood) {
        this.neighborhood = neighborhood;
    }

    public String getComplement() {
        return complement;
    }

    public void setComplement(String complement) {
        this.complement = complement;
    }
}
