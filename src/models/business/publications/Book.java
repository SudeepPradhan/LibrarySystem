package models.business.publications;

import java.io.Serializable;
import java.util.List;
import models.base.Author;

import businessmodels.Product;

public class Book extends Product implements Serializable {

    private List<Author> authors;

    public Book(String isbn, String title, double dailyRate, double dailyFine, int borrowDuration, List<Author> authors) {
        super(isbn, title, dailyRate, dailyFine, borrowDuration);
        this.authors = authors;
    }

    public List<Author> getAuthors() {
        return authors;
    }

    public String getIsbn() {
        return this.productId;
    }

    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }

        @Override
    public String toString() {
        return this.getTitle();
    }
}
