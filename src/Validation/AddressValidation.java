package Validation;

import models.base.Address;

public class AddressValidation implements Validator<Address> {

    private static final int MIN_ZIP_LENGTH = 5;

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

    private static boolean validateZip(String zip) {
        return validateBlank(zip) && zip.length() >= MIN_ZIP_LENGTH && validateNumeric(zip);
    }

    private static String validate(String street, String city, String state, String zip) {
        if (!validateBlank(street)) {
            return "Street cannot be blank";
        }
        if (!validateBlank(city)) {
            return "City cannot be blank";
        }
        if (!validateBlank(state)) {
            return "State cannot be blank";
        }
        if (!validateZip(zip)) {
            return "Zip code must be numeric and at least " + MIN_ZIP_LENGTH + " digits";
        }
        return null;
    }

    @Override
    public ValidateOutput isValid(Address address) {
        String error =  validate(address.getStreet(), address.getCity(), address.getState(), address.getZip());
        return new ValidateOutputImpl(error == null, error);
    }
}
