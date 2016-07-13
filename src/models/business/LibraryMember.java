package models.business;

import interfaces.Validatable;
import interfaces.ValidateOutput;
import interfaces.Validator;
import java.io.Serializable;
import java.time.LocalDate;
import businessmodels.Address;

import businessmodels.CheckoutRecordEntry;
import businessmodels.CustomerImpl;
import businessmodels.Inventory;
import decorators.CustomerDecorator;
import utilities.LogOutput;

public class LibraryMember extends CustomerDecorator implements Validatable<CustomerDecorator>, Serializable {

    public LibraryMember(String memberId, String firstName, String lastName, Address address, String phoneNumber) {
        super(new CustomerImpl(memberId));
        this.setFirstName(firstName);
        this.setLastName(lastName);
        this.setAddress(address);
        this.setPhoneNumber(phoneNumber);;
        
        LogOutput.getLogger().info("Library Member constructor");
    }

    public void checkoutProduct(Inventory inventory, LocalDate checkoutDate, LocalDate dueDate) {
        CheckoutRecordEntry checkoutRecordEntry = new CheckoutRecordEntry(inventory, checkoutDate, dueDate);
        inventory.setAvailable(false);
        this.getCheckoutRecord().addCheckoutRecordEntry(checkoutRecordEntry);
 
        LogOutput.getLogger().info("Library Member checkoutProduct method");
    }

    public void returnProduct(CheckoutRecordEntry checkoutRecordEntry, LocalDate returnDate) {
        checkoutRecordEntry.setReturnDate(returnDate);
        checkoutRecordEntry.getInventory().setAvailable(true);
        
        LogOutput.getLogger().info("Library Member returnProduct method");
    }

    @Override
    public String toString() {
        return this.getCustomerId();
    }

    @Override
    public ValidateOutput validate(Validator<CustomerDecorator> validator) {
        return validator.isValid(this);
    }
}
