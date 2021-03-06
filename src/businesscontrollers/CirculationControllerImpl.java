package businesscontrollers;

import controllers.CirculationController;
import database.DataManager;
import database.DatabaseFacade;
import java.time.LocalDate;
import java.util.List;
import businessmodels.Address;
import models.business.LibraryMember;
import Validation.LibraryMemberValidation;

import businessmodels.CheckoutRecordEntry;
import businessmodels.Inventory;
import decorators.CustomerDecorator;
import interfaces.Customer;

public class CirculationControllerImpl implements CirculationController {

    private DataManager dataManager = DatabaseFacade.getDataManager();

    @Override
    public CustomerDecorator searchCustomer(String memberId) {
        return dataManager.getLibraryMember(memberId);
    }

    @Override
    public List<CustomerDecorator> getCustomer() {
        return dataManager.getLibraryMembers();
    }

    @Override
    public boolean checkout(String memberId, Inventory inventory) {
        int borrowDuration = inventory.getProduct().getBorrowDuration();
        LocalDate checkoutDate = LocalDate.now();
        CustomerDecorator libraryMember = searchCustomer(memberId);
        if (libraryMember == null) {
            return false;
        }
        libraryMember.checkoutProduct(inventory, checkoutDate, checkoutDate.plusDays(borrowDuration));
        if (dataManager.saveLibraryMember(libraryMember)) {
            return dataManager.saveProduct(inventory.getProduct());
        } else {
            return false;
        }
    }

    //Sudeep - need to remove
    @Override
    public boolean checkin(String memberId, CheckoutRecordEntry checkoutRecordEntry) {
        LocalDate checkinDate = LocalDate.now();
        CustomerDecorator libraryMember = searchCustomer(memberId);
        if (libraryMember == null) {
            return false;
        }
        libraryMember.returnProduct(checkoutRecordEntry, checkinDate);
        if (dataManager.saveLibraryMember(libraryMember)) {
            return dataManager.saveProduct(checkoutRecordEntry.getInventory().getProduct());
        } else {
            return false;
        }
    }

    @Override
    public boolean addCustomer(String memberId, String firstName, String lastName, Address address, String phoneNumber) {
        CustomerDecorator libraryMember = new LibraryMember(memberId, firstName, lastName, address, phoneNumber);
        if (libraryMember.validate(new LibraryMemberValidation()).isValid()) {
            return dataManager.saveLibraryMember(libraryMember);
        }
        return false;
    }

    @Override
    public boolean updateCustomer(String memberId, String firstName, String lastName, Address address, String phoneNumber) {
        CustomerDecorator libraryMember = searchCustomer(memberId);
        if (libraryMember == null) {
            return false;
        }
        libraryMember.setFirstName(firstName);
        libraryMember.setLastName(lastName);
        libraryMember.setAddress(address);
        libraryMember.setPhoneNumber(phoneNumber);
        if (libraryMember.validate(new LibraryMemberValidation()).isValid()) {
            return dataManager.saveLibraryMember(libraryMember);
        }
        return false;
    }
}
