package models.base;

import Validation.AddressValidation;
import Validation.Validatable;
import Validation.ValidateOutput;
import Validation.Validator;
import java.io.Serializable;

public class Address implements Validatable<Address>, Serializable {

    private String street;
    private String city;
    private String state;
    private String zip;

    public Address(String street, String city, String state, String zip) {
        this.street = street;
        this.city = city;
        this.state = state;
        this.zip = zip;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public String getStreet() {
        return street;
    }

    public String getZip() {
        return zip;
    }

    @Override
    public ValidateOutput validate(Validator<Address> validator) {
        return validator.isValid(this);
    }

    public ValidateOutput validate(AddressValidation addressValidation, String addressValidationResult) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
