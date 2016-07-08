package database;

import java.util.ArrayList;
import java.util.List;
import models.base.Author;
import models.business.LibraryMember;
import models.business.Publication;
import models.business.User;

public class DatabaseFacade implements DataManager {

    private List<User> users;
    private List<Publication> publications;
    private List<LibraryMember> libraryMembers;
    private List<Author> authors;

    private static DataManager databaseFacade;

    private DatabaseFacade() {
        authors = DatabaseIO.loadAuthors();
        users = DatabaseIO.loadUsers();
        publications = DatabaseIO.loadPublications();
        libraryMembers = DatabaseIO.loadLibraryMembers();

        users = users == null ? new ArrayList<User>() : users;
        publications = publications == null ? new ArrayList<Publication>() : publications;
        libraryMembers = libraryMembers == null ? new ArrayList<LibraryMember>() : libraryMembers;
        authors = authors == null ? new ArrayList<Author>() : authors;
    }

    public static DataManager getDataManager() {
        if (databaseFacade == null) {
            databaseFacade = new DatabaseFacade();
        }
        return databaseFacade;
    }

    @Override
    public boolean saveUser(User user) {
        if (!users.contains(user)) {
            users.add(user);
        }
        return DatabaseIO.saveUsers(users);
    }

    @Override
    public boolean savePublication(Publication publication) {
        if (!publications.contains(publication)) {
            publications.add(publication);
        }
        return DatabaseIO.savePublications(publications);
    }

    @Override
    public boolean saveLibraryMember(LibraryMember libraryMember) {
        if (!libraryMembers.contains(libraryMember)) {
            libraryMembers.add(libraryMember);
        }
        return DatabaseIO.saveLibraryMembers(libraryMembers);
    }

    @Override
    public boolean saveAuthor(Author author) {
        if (!authors.contains(author)) {
            authors.add(author);
        }
        return DatabaseIO.saveAuthors(authors);
    }

    @Override
    public User getUser(String username) {
        for (User user : users) {
            if (user.getUsername().equalsIgnoreCase(username)) {
                return user;
            }
        }
        return null;
    }

    @Override
    public List<Publication> getPublications() {
        return publications;
    }

    @Override
    public LibraryMember getLibraryMember(String memberId) {
        for (LibraryMember libraryMember : libraryMembers) {
            if (libraryMember.getMemberId().equalsIgnoreCase(memberId)) {
                return libraryMember;
            }
        }
        return null;
    }

    @Override
    public boolean deleteUser(String username) {
        User user = getUser(username);
        if (user == null) {
            return false;
        }
        users.remove(user);
        return DatabaseIO.saveUsers(users);
    }

    @Override
    public List<User> getUsers() {
        return users;
    }

    @Override
    public List<LibraryMember> getLibraryMembers() {
        return libraryMembers;
    }

    @Override
    public List<Author> getAuthors() {
        return authors;
    }

}
