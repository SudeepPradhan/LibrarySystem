package utilities;

import models.business.LibraryMember;

public class LibraryMemberValidation {

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

    private static boolean validateUniqueMemberId(String memberId) {
        return database.DatabaseFacade.getDataManager().getLibraryMember(memberId) == null;
    }

    private static boolean validatePhoneNumber(String phoneNumber) {
        return validateBlank(phoneNumber) && phoneNumber.length() >= MIN_PHONE_LENGTH && validateNumeric(phoneNumber);
    }

    public static boolean isValid(LibraryMember libraryMember) {
        if (libraryMember == null || libraryMember.getAddress() == null) {
            return false;
        }
        return validate(libraryMember.getMemberId(), libraryMember.getFirstName(), libraryMember.getLastName(), libraryMember.getPhoneNumber(), libraryMember.getAddress().getStreet(), libraryMember.getAddress().getCity(), libraryMember.getAddress().getState(), libraryMember.getAddress().getZip()) == null;
    }

    public static String validate(String memberId, String firstName, String lastName, String phoneNumber, String street, String city, String state, String zip) {
        if (!validateBlank(memberId)) {
            return "Member ID cannot be blank";
        }
        if (!validateUniqueMemberId(memberId)) {
            return "Member ID already exists";
        }
        return validate(firstName, lastName, phoneNumber, street, city, state, zip);
    }

    public static String validate(String firstName, String lastName, String phoneNumber, String street, String city, String state, String zip) {
        if (!validateBlank(firstName)) {
            return "First name cannot be blank";
        }
        if (!validateBlank(lastName)) {
            return "Last name cannot be blank";
        }
        if (!validatePhoneNumber(phoneNumber)) {
            return "Phone number must be numeric and at least " + MIN_PHONE_LENGTH + " digits";
        }
        String addressValidationResult = AddressValidation.validate(street, city, state, zip);
        if (addressValidationResult != null) {
            return addressValidationResult;
        }
        return null;
    }
}
