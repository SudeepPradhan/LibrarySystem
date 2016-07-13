package database;

import java.util.List;
import businessmodels.Author;
import models.business.LibraryMember;
//import models.business.User;

import interfaces.Product;
import businessmodels.User;
import decorators.CustomerDecorator;
import interfaces.Customer;

public interface DataManager {

    public boolean saveUser(User user);
    
    //sudeep
    public boolean saveProduct(Product product);

    public boolean saveLibraryMember(CustomerDecorator libraryMember);

    public boolean saveAuthor(Author author);

    public User getUser(String username);

    public List<User> getUsers();

    public List<CustomerDecorator> getLibraryMembers();

    public List<Product> getProducts();

    public CustomerDecorator getLibraryMember(String memberId);

    public List<Author> getAuthors();

    public boolean deleteUser(String username);

}
