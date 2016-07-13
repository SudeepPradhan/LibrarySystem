package businesscontrollers;

import database.DataManager;
import database.DatabaseFacade;
import java.util.ArrayList;
import java.util.List;
import businessmodels.Address;
import businessmodels.Owner;
import Validation.AuthorValidation;
import controllers.OwnerController;

public class AuthorControllerImpl implements OwnerController {

    private DataManager dataManager = DatabaseFacade.getDataManager();

    @Override
    public boolean createOwner(String firstName, String lastName, Address address, String phoneNumber, String credentials, String biography) {
        Owner author = new Owner(firstName, lastName, address, phoneNumber, credentials, biography);
        if (author.validate(new AuthorValidation()).isValid()) {
            return dataManager.saveAuthor(author);
        }
        return false;
    }

    @Override
    public boolean updateOwner(Owner author, String firstName, String lastName, Address address, String phoneNumber, String credentials, String biography) {
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
    public List<Owner> searchOwners(String name) {
        List<Owner> authors = dataManager.getAuthors();
        List<Owner> searchResult = new ArrayList<Owner>();

        for (Owner author : authors) {
            if (author.getLastName().toLowerCase().contains(name.toLowerCase())
                    || author.getFirstName().toLowerCase().contains(name.toLowerCase())) {
                searchResult.add(author);
            }
        }

        return searchResult;
    }

    @Override
    public List<Owner> getOwners() {
        return dataManager.getAuthors();
    }

}
