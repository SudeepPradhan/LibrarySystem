package database;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;
import models.base.Author;
import models.business.LibraryMember;
import models.business.User;
import utilities.ExceptionHandler;

import businessmodels.Product;

public class DatabaseIO {

    private static final String OUTPUT_DIR = System.getProperty("user.dir") + "\\storage";
    private static final String USERS_PATH = OUTPUT_DIR + "\\users.ser";
    private static final String PUBLICATIONS_PATH = OUTPUT_DIR + "\\publications.ser";
    private static final String LIBRARY_MEMBERS_PATH = OUTPUT_DIR + "\\librarymembers.ser";
    private static final String AUTHORS_PATH = OUTPUT_DIR + "\\authors.ser";

    protected static boolean saveUsers(List<User> users) {
        return writeObject(users, USERS_PATH);
    }

    protected static List<User> loadUsers() {
        List<User> users = (List<User>) readObject(USERS_PATH);
        return users;
    }
  
    //sudeep
    protected static boolean saveProducts(List<Product> products) {
        return writeObject(products, PUBLICATIONS_PATH);
    }

    protected static List<Product> loadProducts() {
        List<Product> products = (List<Product>) readObject(PUBLICATIONS_PATH);
        return products;
    }

    protected static boolean saveLibraryMembers(List<LibraryMember> libraryMembers) {
        return writeObject(libraryMembers, LIBRARY_MEMBERS_PATH);
    }

    protected static List<LibraryMember> loadLibraryMembers() {
        List<LibraryMember> libraryMembers = (List<LibraryMember>) readObject(LIBRARY_MEMBERS_PATH);
        return libraryMembers;
    }

    protected static boolean saveAuthors(List<Author> authors) {
        return writeObject(authors, AUTHORS_PATH);
    }

    protected static List<Author> loadAuthors() {
        List<Author> libraryMembers = (List<Author>) readObject(AUTHORS_PATH);
        return libraryMembers;
    }

    private static boolean writeObject(Object object, String relativePath) {
        try {
            FileOutputStream fileOut = new FileOutputStream(relativePath);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(object);
            out.close();
            fileOut.close();
            return true;
        } catch (IOException e) {
            ExceptionHandler.handleDatabaseIOException(e);
            return false;
        }
    }

    private static Object readObject(String relativePath) {
        Object object = null;
        try {
            FileInputStream fileIn = new FileInputStream(relativePath);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            object = in.readObject();
            in.close();
            fileIn.close();
        } catch (IOException e) {
            ExceptionHandler.handleDatabaseIOException(e);
        } catch (ClassNotFoundException e) {
            ExceptionHandler.handleDatabaseIOException(e);
        } finally {
            return object;
        }
    }

}
