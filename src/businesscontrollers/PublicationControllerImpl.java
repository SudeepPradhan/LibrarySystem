package businesscontrollers;

import database.DataManager;
import database.DatabaseFacade;
import java.util.ArrayList;
import java.util.List;
import models.base.Author;
import models.business.Publication;
import models.business.publications.Book;
import utilities.PublicationValidation;

public class PublicationControllerImpl implements PublicationController {

    private DataManager dataManager = DatabaseFacade.getDataManager();

    @Override
    public Book searchBookWithIsbn(String isbn) {
        List<Publication> publications = dataManager.getPublications();
        for (Publication publication : publications) {
            if (publication instanceof Book) {
                if (((Book) publication).getIsbn().equalsIgnoreCase(isbn)) {
                    return (Book) publication;
                }
            }
        }
        return null;
    }

    @Override
    public List<Book> searchBooksWithTitle(String title) {
        List<Publication> publications = dataManager.getPublications();
        List<Book> books = new ArrayList<Book>();
        for (Publication publication : publications) {
            if (publication instanceof Book) {
                if (((Book) publication).getTitle().toLowerCase().contains(title.toLowerCase())) {
                    books.add((Book) publication);
                }
            }
        }
        return books;
    }

    @Override
    public Book addBook(String title, int borrowDuration, String isbn, List<Author> authors) {
        Book book = new Book(title, borrowDuration, isbn, authors);
        if (PublicationValidation.isValidBook(title, borrowDuration, isbn, authors, true)) {
            dataManager.savePublication(book);
            return book;
        }
        return null;
    }

    @Override
    public List<Book> getBooks() {
        List<Publication> publications = dataManager.getPublications();
        List<Book> books = new ArrayList<Book>();
        for (Publication publication : publications) {
            if (publication instanceof Book) {
                books.add((Book) publication);
            }
        }
        return books;
    }

    @Override
    public boolean addCopies(String isbn, int numberOfCopies) {
        Book book = searchBookWithIsbn(isbn);
        if (book == null) {
            return false;
        }
        book.addCopies(numberOfCopies);
        if (PublicationValidation.isValidBook(book.getTitle(), book.getBorrowDuration(), book.getIsbn(), book.getAuthors(), false)) {
            return dataManager.savePublication(book);
        }
        return false;
    }

    @Override
    public boolean updateBook(String isbn, String title, int borrowDuration, List<Author> authors) {
        Book book = searchBookWithIsbn(isbn);
        if (book == null) {
            return false;
        }
        book.setTitle(title);
        book.setBorrowDuration(borrowDuration);
        book.setAuthors(authors);
        if (PublicationValidation.isValidBook(title, borrowDuration, book.getIsbn(), authors, false)) {
            return dataManager.savePublication(book);
        }
        return false;
    }

}
