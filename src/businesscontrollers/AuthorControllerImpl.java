package businesscontrollers;

import controllers.AuthorController;
import database.DataManager;
import database.DatabaseFacade;
import java.util.ArrayList;
import java.util.List;
import businessmodels.Address;
import businessmodels.Author;
import Validation.AuthorValidation;

public class AuthorControllerImpl implements AuthorController {

    private DataManager dataManager = DatabaseFacade.getDataManager();

    @Override
    public boolean createAuthor(String firstName, String lastName, Address address, String phoneNumber, String credentials, String biography) {
        Author author = new Author(firstName, lastName, address, phoneNumber, credentials, biography);
        if (author.validate(new AuthorValidation()).isValid()) {
            return dataManager.saveAuthor(author);
        }
        return false;
    }

    @Override
    public boolean updateAuthor(Author author, String firstName, String lastName, Address address, String phoneNumber, String credentials, String biography) {
        if (author == null) {
            return false;
        }
        author.setFirstName(firstName);
        author.setLastName(lastName);
        author.setAddress(address);
        author.setPhoneNumber(phoneNumber);
        author.setCredentials(credentials);
        author.setBiography(biography);
        if (author.validate(new AuthorValidation()).isValid()) {
            return dataManager.saveAuthor(author);
        }
        return false;
    }

    @Override
    public List<Author> searchAuthors(String name) {
        List<Author> authors = dataManager.getAuthors();
        List<Author> searchResult = new ArrayList<Author>();

        for (Author author : authors) {
            if (author.getLastName().toLowerCase().contains(name.toLowerCase())
                    || author.getFirstName().toLowerCase().contains(name.toLowerCase())) {
                searchResult.add(author);
            }
        }

        return searchResult;
    }

    @Override
    public List<Author> getAuthors() {
        return dataManager.getAuthors();
    }

}
