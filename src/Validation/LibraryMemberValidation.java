package Validation;

import models.base.Address;
import models.business.LibraryMember;

public class LibraryMemberValidation implements Validator<LibraryMember> {

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

    public static String validate(String memberId, String firstName, String lastName, String phoneNumber, 
            Address address) {
        if (!validateBlank(memberId)) {
            return "Member ID cannot be blank";
        }
        if (!validateUniqueMemberId(memberId)) {
            return "Member ID already exists";
        }
        return validate(firstName, lastName, phoneNumber, address);
    }

    public static String validate(String firstName, String lastName, String phoneNumber, Address address) {
        if (!validateBlank(firstName)) {
            return "First name cannot be blank";
        }
        if (!validateBlank(lastName)) {
            return "Last name cannot be blank";
        }
        if (!validatePhoneNumber(phoneNumber)) {
            return "Phone number must be numeric and at least " + MIN_PHONE_LENGTH + " digits";
        }

        ValidateOutput result = address.validate(new AddressValidation());
        if (!result.isValid()) {
            return result.getError();
        }
        return null;
    }
 
    @Override
    public ValidateOutput isValid(LibraryMember libraryMember) {
        if (libraryMember == null || libraryMember.getAddress() == null) {
            return new ValidateOutputImpl(false, "");
        }
        String error =  validate(libraryMember.getMemberId(), libraryMember.getFirstName(), 
                                    libraryMember.getLastName(), libraryMember.getPhoneNumber(), 
                                    libraryMember.getAddress());
        
        return new ValidateOutputImpl(error == null, error);
    }
}
