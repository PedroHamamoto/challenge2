package br.com.challenge2.model;

/**
 * Creates addresses easily
 */
public class AddressBuilder {

    private String id;
    private String cep;
    private String street;
    private String number;
    private String state;
    private String city;
    private String neighborhood;
    private String complement;

    public AddressBuilder withId(String id) {
        this.id = id;
        return this;
    }

    public AddressBuilder withCep(String cep) {
        this.cep = cep;
        return this;
    }

    public AddressBuilder withStreet(String street) {
        this.street = street;
        return this;
    }

    public AddressBuilder withNumber(String number) {
        this.number = number;
        return this;
    }

    public AddressBuilder withState(String state) {
        this.state = state;
        return this;
    }

    public AddressBuilder withCity(String city) {
        this.city = city;
        return this;
    }

    public AddressBuilder withNeighborhood(String neighborhood) {
        this.neighborhood = neighborhood;
        return this;
    }

    public AddressBuilder withComplement(String complement) {
        this.complement = complement;
        return this;
    }

    public Address build() {
        return new Address(id, cep, street, number, state, city, neighborhood, complement);
    }

}
