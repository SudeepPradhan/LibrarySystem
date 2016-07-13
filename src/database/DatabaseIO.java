package database;

import java.util.List;
import businessmodels.Author;
import interfaces.Product;
import businessmodels.User;
import decorators.CustomerDecorator;
import interfaces.Customer;

public class DatabaseIO {

    private static final String OUTPUT_DIR = System.getProperty("user.dir") + "\\storage";
    private static final String USERS_PATH = OUTPUT_DIR + "\\users.ser";
    private static final String PUBLICATIONS_PATH = OUTPUT_DIR + "\\publications.ser";
    private static final String LIBRARY_MEMBERS_PATH = OUTPUT_DIR + "\\librarymembers.ser";
    private static final String AUTHORS_PATH = OUTPUT_DIR + "\\authors.ser";

    protected static boolean saveUsers(List<User> users) {
        return IOTool.writeObject(users, USERS_PATH);
    }

    protected static List<User> loadUsers() {
        List<User> users = (List<User>) IOTool.readObject(USERS_PATH);
        return users;
    }

    protected static boolean saveProducts(List<Product> products) {
        return IOTool.writeObject(products, PUBLICATIONS_PATH);
    }

    protected static List<Product> loadProducts() {
        List<Product> products = (List<Product>) IOTool.readObject(PUBLICATIONS_PATH);
        return products;
    }

    protected static boolean saveLibraryMembers(List<CustomerDecorator> libraryMembers) {
        return IOTool.writeObject(libraryMembers, LIBRARY_MEMBERS_PATH);
    }

    protected static List<CustomerDecorator> loadLibraryMembers() {
        List<CustomerDecorator> libraryMembers = (List<CustomerDecorator>) IOTool.readObject(LIBRARY_MEMBERS_PATH);
        return libraryMembers;
    }

    protected static boolean saveAuthors(List<Author> authors) {
        return IOTool.writeObject(authors, AUTHORS_PATH);
    }

    protected static List<Author> loadAuthors() {
        List<Author> libraryMembers = (List<Author>) IOTool.readObject(AUTHORS_PATH);
        return libraryMembers;
    }
}
