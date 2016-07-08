package models.business.publications;

import java.io.Serializable;
import java.util.List;
import models.base.Author;
import models.business.Publication;

public class Book extends Publication implements Serializable {

    private String isbn;
    private List<Author> authors;

    public Book(String title, int borrowDuration, String isbn, List<Author> authors) {
        super(title, borrowDuration);
        this.isbn = isbn;
        this.authors = authors;
    }

    public List<Author> getAuthors() {
        return authors;
    }

    public String getIsbn() {
        return isbn;
    }
    @Override
    public String getUniqueIdentifier() {
        return isbn;
    }

    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }

}
