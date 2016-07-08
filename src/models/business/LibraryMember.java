package models.business;

import java.io.Serializable;
import java.time.LocalDate;
import models.base.Address;

public class LibraryMember implements Serializable {

    private String memberId;
    private String firstName;
    private String lastName;
    private Address address;
    private String phoneNumber;
    private CheckoutRecord checkoutRecord;

    public LibraryMember(String memberId, String firstName, String lastName, Address address, String phoneNumber) {
        this.memberId = memberId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.checkoutRecord = new CheckoutRecord(this);
    }

    public void checkoutPublication(LendableCopy lendableCopy, LocalDate checkoutDate, LocalDate dueDate) {
        CheckoutRecordEntry checkoutRecordEntry = new CheckoutRecordEntry(lendableCopy, checkoutDate, dueDate);
        lendableCopy.setAvailable(false);
        checkoutRecord.addRecord(checkoutRecordEntry);
    }

    public void returnPublication(CheckoutRecordEntry checkoutRecordEntry, LocalDate returnDate) {
        checkoutRecordEntry.setReturnDate(returnDate);
        checkoutRecordEntry.getLendableCopy().setAvailable(true);
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getMemberId() {
        return memberId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Address getAddress() {
        return address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public CheckoutRecord getCheckoutRecord() {
        return checkoutRecord;
    }

    @Override
    public String toString() {
        return this.memberId;
    }
}
