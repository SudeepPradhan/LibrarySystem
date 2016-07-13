package Validation;

import interfaces.Validator;
import interfaces.ValidateOutput;
import businessmodels.Address;
import businessmodels.Author;

public class AuthorValidation implements Validator<Author> {

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

    private static String validate(String firstName, String lastName, String phoneNumber, 
            String credentials, String biography, Address address) {
        String validation = validate(firstName, lastName, phoneNumber, credentials, biography);
        if (validation != null) {
            return validation;
        }
        
        ValidateOutput result = address.validate(new AddressValidation());
        if (!result.isValid()) {
            return result.getError();
        }
        return null;
    }

    private static String validate(String firstName, String lastName, String phoneNumber, 
            String credentials, String biography) {
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

    @Override
    public ValidateOutput isValid(Author author) {
        if (author == null) {
            return new ValidateOutputImpl(false, "");
        }
        String error = null;
        if (author.getAddress() != null) {
            error = validate(
                    author.getFirstName(), 
                    author.getLastName(), 
                    author.getPhoneNumber(), 
                    author.getCredentials(), 
                    author.getBiography(), 
                    author.getAddress());
        } else {
            error = validate(
                    author.getFirstName(), 
                    author.getLastName(), 
                    author.getPhoneNumber(), 
                    author.getCredentials(), 
                    author.getBiography());
        }
        
        return new ValidateOutputImpl(error == null, error);
    }
}
