package models.business;

import java.io.Serializable;
import java.time.LocalDate;

public class CheckoutRecordEntry implements Serializable {

    private LendableCopy lendableCopy;
    private LocalDate checkoutDate;
    private LocalDate dueDate;
    private LocalDate returnDate;

    protected CheckoutRecordEntry(LendableCopy copy, LocalDate checkoutDate, LocalDate dueDate) {
        this.lendableCopy = copy;
        this.checkoutDate = checkoutDate;
        this.dueDate = dueDate;
        this.returnDate = null;
    }

    public LendableCopy getLendableCopy() {
        return lendableCopy;
    }

    public LocalDate getCheckoutDate() {
        return checkoutDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    protected void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

}
