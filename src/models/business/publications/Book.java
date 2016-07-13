package models.business.publications;

import businessmodels.ProductImpl;
import java.io.Serializable;
import java.util.List;
import businessmodels.Owner;

import decorators.ProductDecorator;

public class Book extends ProductDecorator implements Serializable {

    private List<Owner> authors;

    public Book(String isbn, String title, double dailyRate, double dailyFine, int borrowDuration, List<Owner> authors) {
        super(new ProductImpl(isbn, title, dailyRate, dailyFine, borrowDuration));
        this.authors = authors;
    }

    public List<Owner> getAuthors() {
        return authors;
    }

    public String getIsbn() {
        return this.getProductId();
    }

    public void setAuthors(List<Owner> authors) {
        this.authors = authors;
    }

    @Override
    public String toString() {
        return this.getTitle();
    }
}
