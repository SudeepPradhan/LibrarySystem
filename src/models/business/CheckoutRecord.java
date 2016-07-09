package models.business;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import businessmodels.CheckoutRecordEntry;

public class CheckoutRecord implements Serializable {

    private LibraryMember libraryMember;
    private List<CheckoutRecordEntry> checkoutRecordEntries;

    protected CheckoutRecord(LibraryMember libraryMember) {
        this.libraryMember = libraryMember;
        checkoutRecordEntries = new ArrayList<CheckoutRecordEntry>();
    }

    /**
     * Adds a checkout record.
     *
     * @param checkoutRecordEntry
     */
    protected void addRecord(CheckoutRecordEntry checkoutRecordEntry) {
        checkoutRecordEntries.add(checkoutRecordEntry);
    }

    protected LibraryMember getLibraryMember() {
        return libraryMember;
    }

    public List<CheckoutRecordEntry> getCheckoutRecordEntries() {
        return checkoutRecordEntries;
    }
}
