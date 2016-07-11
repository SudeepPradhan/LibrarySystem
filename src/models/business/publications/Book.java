package models.business.publications;

import businessmodels.ProductImpl;
import java.io.Serializable;
import java.util.List;
import models.base.Author;

import decorators.ProductDecorator;

public class Book extends ProductDecorator implements Serializable {

    private List<Author> authors;

    public Book(String isbn, String title, double dailyRate, double dailyFine, int borrowDuration, List<Author> authors) {
        super(new ProductImpl(isbn, title, dailyRate, dailyFine, borrowDuration));
        this.authors = authors;
    }

    public List<Author> getAuthors() {
        return authors;
    }

    public String getIsbn() {
        return this.getProductId();
    }

    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }

        @Override
    public String toString() {
        return this.getTitle();
    }
}
