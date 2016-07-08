package database;

import java.util.List;
import models.base.Author;
import models.business.LibraryMember;
import models.business.Publication;
import models.business.User;

public interface DataManager {

    public boolean saveUser(User user);

    public boolean savePublication(Publication publication);

    public boolean saveLibraryMember(LibraryMember libraryMember);

    public boolean saveAuthor(Author author);

    public User getUser(String username);

    public List<User> getUsers();

    public List<LibraryMember> getLibraryMembers();

    public List<Publication> getPublications();

    public LibraryMember getLibraryMember(String memberId);

    public List<Author> getAuthors();

    public boolean deleteUser(String username);

}
