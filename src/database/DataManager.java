package database;

import java.util.List;
import models.base.Author;
import models.business.LibraryMember;
//import models.business.User;

import businessmodels.Product;
import businessmodels.User;

public interface DataManager {

    public boolean saveUser(User user);
    
    //sudeep
    public boolean saveProduct(Product product);

    public boolean saveLibraryMember(LibraryMember libraryMember);

    public boolean saveAuthor(Author author);

    public User getUser(String username);

    public List<User> getUsers();

    public List<LibraryMember> getLibraryMembers();

    public List<Product> getProducts();

    public LibraryMember getLibraryMember(String memberId);

    public List<Author> getAuthors();

    public boolean deleteUser(String username);

}
