package businesscontrollers;

import java.util.List;
import models.base.Author;
import models.business.Publication;
import models.business.publications.Book;

public interface PublicationController {

    /**
     * Searches book with ISBN.
     *
     * @param isbn the ISBN number.
     * @return the {@link Book}.
     */
    Book searchBookWithIsbn(String isbn);

    /**
     * Searches Books with title.
     *
     * @param title the title or part of the title.
     * @return list of books containing the specified title.
     */
    List<Book> searchBooksWithTitle(String title);

    /**
     * Adds new book.
     *
     * @param title the title of the book.
     * @param borrowDuration the duration of borrowing.
     * @param isbn the ISBN.
     * @param authors the list of {@link Author}s.
     * @return the added {@link Book} if the operation succeeds, false
     * otherwise.
     */
    Book addBook(String title, int borrowDuration, String isbn, List<Author> authors);

    public List<Book> getBooks();

    /**
     * @param isbn the ISBN. book
     * @param borrowDuration
     * @param authors
     * @return
     */
    boolean updateBook(String isbn, String title, int borrowDuration, List<Author> authors);

    /**
     * Adds copies to a book.
     *
     * @param isbn the ISBN.
     * @param numberOfCopies the number of copies to add.
     * @return true if the operation succeeds, false otherwise.
     */
    boolean addCopies(String isbn, int numberOfCopies);
}
