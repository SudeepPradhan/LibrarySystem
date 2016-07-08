package utilities;

import models.base.Author;

public class AuthorValidation {

    private static final int MIN_PHONE_LENGTH = 10;

    private static boolean validateBlank(String string) {
        return string != null && string.length() > 0;
    }

    private static boolean validateNumeric(String string) {
        char arr[] = string.toCharArray();
        for (char c : arr) {
            if (!Character.isDigit(c)) {
                return false;
            }
        }
        return true;
    }

    private static boolean validatePhoneNumber(String phoneNumber) {
        return validateBlank(phoneNumber) && phoneNumber.length() >= MIN_PHONE_LENGTH && validateNumeric(phoneNumber);
    }

    public static boolean isValid(Author author, boolean validateAddress) {
        if (author == null || (validateAddress && author.getAddress() == null)) {
            return false;
        }
        if (validateAddress) {
            return validate(author.getFirstName(), author.getLastName(), author.getPhoneNumber(), author.getCredentials(), author.getBiography(), author.getAddress().getStreet(), author.getAddress().getCity(), author.getAddress().getState(), author.getAddress().getZip()) == null;
        } else {
            return validate(author.getFirstName(), author.getLastName(), author.getPhoneNumber(), author.getCredentials(), author.getBiography()) == null;
        }
    }

    public static String validate(String firstName, String lastName, String phoneNumber, String credentials, String biography, String street, String city, String state, String zip) {
        String validation = validate(firstName, lastName, phoneNumber, credentials, biography);
        if (validation != null) {
            return validation;
        }
        String addressValidationResult = AddressValidation.validate(street, city, state, zip);
        if (addressValidationResult != null) {
            return addressValidationResult;
        }
        return null;
    }

    public static String validate(String firstName, String lastName, String phoneNumber, String credentials, String biography) {
        if (!validateBlank(firstName)) {
            return "First name cannot be blank";
        }
        if (!validateBlank(lastName)) {
            return "Last name cannot be blank";
        }
        if (!validatePhoneNumber(phoneNumber)) {
            return "Phone number must be numeric and at least " + MIN_PHONE_LENGTH + " digits";
        }
        if (!validateBlank(credentials)) {
            return "Credentials cannot be blank";
        }
        if (!validateBlank(biography)) {
            return "Biography cannot be blank";
        }
        return null;
    }

}
