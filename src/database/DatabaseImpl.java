package database;

import businessmodels.User;
import interfaces.Customer;
import interfaces.Product;
import java.util.ArrayList;
import java.util.List;
import businessmodels.Owner;
import decorators.CustomerDecorator;
import models.business.LibraryMember;

public class DatabaseImpl implements Database {

    private List<User> users;
    private List<CustomerDecorator> libraryMembers;
    private List<Owner> authors;

    private List<Product> products;

    DatabaseImpl() {
        authors = DatabaseIO.loadAuthors();
        users = DatabaseIO.loadUsers();
        products = DatabaseIO.loadProducts();
        libraryMembers = DatabaseIO.loadLibraryMembers();

        users = users == null ? new ArrayList<User>() : users;
        products = products == null ? new ArrayList<Product>() : products;
        libraryMembers = libraryMembers == null ? new ArrayList<CustomerDecorator>() : libraryMembers;
        authors = authors == null ? new ArrayList<Owner>() : authors;
    }

    @Override
    public boolean createProduct(Product product) {
        if (!products.contains(product)) {
            products.add(product);
        }
        return DatabaseIO.saveProducts(products);
    }

    @Override
    public boolean createCustomer(Customer customer) {
        if (!libraryMembers.contains(customer)) {
            libraryMembers.add((LibraryMember) customer);
        }
        return DatabaseIO.saveLibraryMembers(libraryMembers);
    }

    @Override
    public boolean createUser(User user) {
        if (!users.contains(user)) {
            users.add(user);
        }
        return DatabaseIO.saveUsers(users);
    }

    @Override
    public boolean updateProduct(Product product) {
        if (!products.contains(product)) {
            products.add(product);
        }
        return DatabaseIO.saveProducts(products);
    }

    @Override
    public boolean updateCustomer(Customer customer) {
        if (!libraryMembers.contains(customer)) {
            libraryMembers.add((LibraryMember) customer);
        }
        return DatabaseIO.saveLibraryMembers(libraryMembers);
    }

    @Override
    public boolean updateUser(User user) {
        if (!users.contains(user)) {
            users.add(user);
        }
        return DatabaseIO.saveUsers(users);
    }

    @Override
    public User searchUser(String username) {
        return null;
    }

    @Override
    public List<Product> getAllProducts() {
        return products;
    }

    @Override
    public List<Customer> getAllCustomers() {
        return null;
    }

    @Override
    public List<User> getAllUsers() {
        return users;
    }

}
