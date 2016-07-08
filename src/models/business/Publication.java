package models.business;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public abstract class Publication implements Serializable {

    private String title;
    private int borrowDuration;
    private List<LendableCopy> lendableCopies;

    /**
     * Public constructor.
     *
     * @param title
     */
    public Publication(String title, int borrowDuration) {
        this.title = title;
        this.borrowDuration = borrowDuration;
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

    public void setTitle(String title) {
        this.title = title;
    }

    public void setBorrowDuration(int borrowDuration) {
        this.borrowDuration = borrowDuration;
    }

    public String getTitle() {
        return title;
    }

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