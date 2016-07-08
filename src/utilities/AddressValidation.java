package utilities;

import models.base.Address;

public class AddressValidation {

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

    public static boolean isValid(Address address) {
        return validate(address.getStreet(), address.getCity(), address.getState(), address.getZip()) == null;
    }

    public static String validate(String street, String city, String state, String zip) {
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

}
