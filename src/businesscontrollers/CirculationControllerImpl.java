package businesscontrollers;

import database.DataManager;
import database.DatabaseFacade;
import java.time.LocalDate;
import java.util.List;
import models.base.Address;
import models.business.CheckoutRecordEntry;
import models.business.LendableCopy;
import models.business.LibraryMember;
import utilities.LibraryMemberValidation;

public class CirculationControllerImpl implements CirculationController {

    private DataManager dataManager = DatabaseFacade.getDataManager();

    @Override
    public LibraryMember searchLibraryMember(String memberId) {
        return dataManager.getLibraryMember(memberId);
    }

    @Override
    public List<LibraryMember> getLibraryMembers() {
        return dataManager.getLibraryMembers();
    }

    @Override
    public boolean checkout(String memberId, LendableCopy lendableCopy) {
        int borrowDuration = lendableCopy.getPublication().getBorrowDuration();
        LocalDate checkoutDate = LocalDate.now();
        LibraryMember libraryMember = searchLibraryMember(memberId);
        if (libraryMember == null) {
            return false;
        }
        libraryMember.checkoutPublication(lendableCopy, checkoutDate, checkoutDate.plusDays(borrowDuration));
        if (dataManager.saveLibraryMember(libraryMember)) {
            return dataManager.savePublication(lendableCopy.getPublication());
        } else {
            return false;
        }
    }

    @Override
    public boolean checkin(String memberId, CheckoutRecordEntry checkoutRecordEntry) {
        LocalDate checkinDate = LocalDate.now();
        LibraryMember libraryMember = searchLibraryMember(memberId);
        if (libraryMember == null) {
            return false;
        }
        libraryMember.returnPublication(checkoutRecordEntry, checkinDate);
        if (dataManager.saveLibraryMember(libraryMember)) {
            return dataManager.savePublication(checkoutRecordEntry.getLendableCopy().getPublication());
        } else {
            return false;
        }
    }

    @Override
    public boolean addLibraryMember(String memberId, String firstName, String lastName, Address address, String phoneNumber) {
        LibraryMember libraryMember = new LibraryMember(memberId, firstName, lastName, address, phoneNumber);
        if (LibraryMemberValidation.isValid(libraryMember)) {
            return dataManager.saveLibraryMember(libraryMember);
        }
        return false;
    }

    @Override
    public boolean updateLibraryMember(String memberId, String firstName, String lastName, Address address, String phoneNumber) {
        LibraryMember libraryMember = searchLibraryMember(memberId);
        if (libraryMember == null) {
            return false;
        }
        libraryMember.setFirstName(firstName);
        libraryMember.setLastName(lastName);
        libraryMember.setAddress(address);
        libraryMember.setPhoneNumber(phoneNumber);
        if (LibraryMemberValidation.isValid(libraryMember)) {
            return dataManager.saveLibraryMember(libraryMember);
        }
        return false;
    }
}
