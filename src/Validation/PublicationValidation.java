package Validation;

import java.util.List;
import models.base.Address;
import models.base.Author;
import models.business.publications.Book;

import interfaces.Product;
import models.business.LibraryMember;

public class PublicationValidation implements Validator<LibraryMember>{

    private static int MIN_BOOK_TITLE_LENGTH = 1;
    private static int MAX_BORROW_DURATION = 21;
    private static int MIN_BORROW_DURATION = 7;
    private static int MIN_NUMBER_OF_COPIES_TO_ADD = 1;
    private static int MAX_NUMBER_OF_COPIES_TO_ADD = 1000;

    private static int MIN_ISBN_LENGTH = 10;
    private static int MAX_ISBN_LENGTH = 14;

    private static boolean validateTitle(String title) {
        if (title == null || title.length() < MIN_BOOK_TITLE_LENGTH) {
            return false;
        }
        return true;
    }

    private static boolean validateBorrowDuration(int borrowDuration) {
        if (borrowDuration < MIN_BORROW_DURATION || borrowDuration > MAX_BORROW_DURATION) {
            return false;
        }
        return true;
    }

    private static boolean validateNumberOfCopiesToAdd(int numberOfCopiesToAdd) {
        if (numberOfCopiesToAdd < MIN_NUMBER_OF_COPIES_TO_ADD || numberOfCopiesToAdd > MAX_NUMBER_OF_COPIES_TO_ADD) {
            return false;
        }
        return true;
    }

    private static boolean validateIsbnLength(String isbn) {
        if (isbn == null || isbn.length() < MIN_ISBN_LENGTH || isbn.length() > MAX_ISBN_LENGTH) {
            return false;
        }
        return true;
    }

    public static boolean isValidBook(String title, int borrowDuration, String isbn, List<Author> authors, boolean isNewBook) {
        return validateBook(title, borrowDuration, isbn, authors, isNewBook) == null;
    }

    public static String validateBook(String title, int borrowDuration, String isbn, List<Author> authors, boolean isNewBook) {
        if (!validateTitle(title)) {
            return "Title should be atleast " + MIN_BOOK_TITLE_LENGTH + " charcter/s long";
        }
        if (!validateBorrowDuration(borrowDuration)) {
            return "Borrow duration should be between " + MIN_BORROW_DURATION + " and " + MAX_BORROW_DURATION;
        }
        if (!validateIsbnLength(isbn)) {
            return "Invalid ISBN length";
        }
        if (isNewBook) {
            if (!validateUniquIsbn(isbn)) {
                return "ISBN must be unique";
            }
        }
        if (authors == null || authors.size() == 0) {
            return "Book should have atleast one author";
        }
        for (Author author : authors) {
            if (author == null) {
                return "Invalid author";
            } 
        }
        return null;
    }

    public static boolean validateUniquIsbn(String isbn) {
        List<Product> publications = database.DatabaseFacade.getDataManager().getProducts();
        for (Product publication : publications) {
            if (publication instanceof Book) {
                if (((Book) publication).getIsbn().equals(isbn)) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public ValidateOutput isValid(LibraryMember entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
