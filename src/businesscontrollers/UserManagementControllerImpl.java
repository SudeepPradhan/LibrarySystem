package businesscontrollers;

import database.DataManager;
import database.DatabaseFacade;
import java.util.List;
//import models.business.User;
import utilities.CryptoHelper;
import utilities.UserValidation;
import views.MPPLibraryApplication;
import businessmodels.User;

public class UserManagementControllerImpl implements UserManagementController {

    private DataManager dataManager = DatabaseFacade.getDataManager();

    public static User loginUser = null;

    @Override
    public User authenticate(String username, String password) {
        User user = dataManager.getUser(username);
        if (user != null) {
            if (user.getPassword().equals(CryptoHelper.getSha1Hash(password))) {
                return user;
            }
        }
        return null;
    }

    @Override
    public boolean createUser(String username, String password, businessmodels.User.UserType userType) {
        User user = new User(username, password, userType);
        if (UserValidation.isValid(user)) {
            user.setPassword(CryptoHelper.getSha1Hash(password));
            return dataManager.saveUser(user);
        }
        return false;
    }

    @Override
    public boolean deleteUser(String username) {
        return dataManager.deleteUser(username);
    }

    @Override
    public boolean updateUser(String username, String password, User.UserType userType) {
        if (username == null || userType == null || !UserValidation.validatePassword(password)) {
            return false;
        }
        User user = dataManager.getUser(username);
        if (user == null) {
            return false;
        }
        user.setPassword(CryptoHelper.getSha1Hash(password));
        user.setUserType(userType);
        return dataManager.saveUser(user);
    }
 
    @Override
    public List<User> getUsers() {
        return dataManager.getUsers();
    }

    @Override
    public void logout() {
        loginUser = null;
        MPPLibraryApplication.showLoginPane();
    }

}
