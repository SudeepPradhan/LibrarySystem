package models.business;

import java.io.Serializable;

public class LendableCopy implements Serializable {

    private Publication publication;
    private int copyNumber;
    private boolean available;

    /**
     * Public constructor.
     *
     * @param publication
     * @param copyNumber
     */
    protected LendableCopy(Publication publication, int copyNumber) {
        this.publication = publication;
        this.copyNumber = copyNumber;
        this.available = true;
    }

    public Publication getPublication() {
        return publication;
    }

    protected int getCopyNumber() {
        return copyNumber;
    }

    protected boolean isAvailable() {
        return available;
    }

    protected void setAvailable(boolean available) {
        this.available = available;
    }

}
