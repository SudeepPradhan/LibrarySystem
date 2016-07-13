package models.business.publications;

import Validation.Validatable;
import businessmodels.ProductImpl;
import decorators.ProductDecorator;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public abstract class Publication extends ProductDecorator implements Serializable {

    private String title;
    private int borrowDuration;
    private List<LendableCopy> lendableCopies;

    /**
     * Public constructor.
     *
     * @param title
     */
    public Publication(String title, int borrowDuration) {
        super(new ProductImpl(title, borrowDuration));
//        this.title = title;
//        this.borrowDuration = borrowDuration;
        this.lendableCopies = new ArrayList<LendableCopy>();
        addCopies(1);
    }

    public abstract String getUniqueIdentifier();

    public void addCopies(int numberOfCopies) {

        int lastCopyNumber = 0;
        if (lendableCopies.size() > 0) {
            lastCopyNumber = lendableCopies.get(lendableCopies.size() - 1).getCopyNumber();
        }

        for (int i = 0; i < numberOfCopies; i++) {
            lastCopyNumber++;
            lendableCopies.add(new LendableCopy(this, lastCopyNumber));
        }
    }

    @Override
    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public void setBorrowDuration(int borrowDuration) {
        this.borrowDuration = borrowDuration;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public int getBorrowDuration() {
        return borrowDuration;
    }

    public int getNumberOfCopies() {
        return lendableCopies.size();
    }

    public int getNumberOfAvailableCopies() {
        int availableCopies = 0;
        for (LendableCopy copy : lendableCopies) {
            availableCopies += (copy.isAvailable() ? 1 : 0);
        }
        return availableCopies;
    }

    public LendableCopy getAvailableLendableCopy() {
        for (LendableCopy copy : lendableCopies) {
            if (copy.isAvailable()) {
                return copy;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return title;
    }
}
