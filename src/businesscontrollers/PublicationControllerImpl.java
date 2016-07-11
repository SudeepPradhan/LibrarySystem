package businesscontrollers;

import database.DataManager;
import database.DatabaseFacade;
import java.util.ArrayList;
import java.util.List;
import models.base.Author;
import models.business.publications.Book;
import Validation.PublicationValidation;

import businessmodels.Product;

public class PublicationControllerImpl implements PublicationController {

    private DataManager dataManager = DatabaseFacade.getDataManager();

    @Override
    public Book searchBookWithIsbn(String isbn) {
        List<Product> products = dataManager.getProducts();
        for (Product product : products) {
            if (product instanceof Book) {
                if (((Book) product).getIsbn().equalsIgnoreCase(isbn)) {
                    return (Book) product;
                }
            }
        }
        return null;
    }

    @Override
    public List<Book> searchBooksWithTitle(String title) {
        List<Product> products = dataManager.getProducts();
        List<Book> books = new ArrayList<Book>();
        for (Product product : products) {
            if (product instanceof Book) {
                if (((Book) product).getTitle().toLowerCase().contains(title.toLowerCase())) {
                    books.add((Book) product);
                }
            }
        }
        return books;
    }

    @Override
    public Book addBook(String isbn, String title, double dailyRate, double dailyFine, int borrowDuration, List<Author> authors) {
        Book book = new Book(isbn, title, dailyRate, dailyFine, borrowDuration, authors);
        if (PublicationValidation.isValidBook(title, borrowDuration, isbn, authors, true)) {
            dataManager.saveProduct(book);
            return book;
        }
        return null;
    }

    @Override
    public List<Book> getBooks() {
        List<Product> products = dataManager.getProducts();
        List<Book> books = new ArrayList<Book>();
        for (Product product : products) {
            if (product instanceof Book) {
                books.add((Book) product);
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
        book.addDefaultInventory(numberOfCopies);
        if (PublicationValidation.isValidBook(book.getTitle(), book.getBorrowDuration(), book.getIsbn(), book.getAuthors(), false)) {
            return dataManager.saveProduct(book);
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
            return dataManager.saveProduct(book);
        }
        return false;
    }

}
