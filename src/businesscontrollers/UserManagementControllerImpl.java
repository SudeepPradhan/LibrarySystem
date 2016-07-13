package businesscontrollers;

import database.DataManager;
import database.DatabaseFacade;
import java.util.List;
import Validation.UserValidation;
import views.MPPLibraryApplication;
import businessmodels.User;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import security.hash.PasswordHasher;
import security.hash.SHA1Hasher;

public class UserManagementControllerImpl implements UserManagementController {

    PasswordHasher passwordhasher = new PasswordHasher(new SHA1Hasher());
    private DataManager dataManager = DatabaseFacade.getDataManager();

    public static User loginUser = null;

    @Override
    public User authenticate(String username, String password) {
        User user = dataManager.getUser(username);
        if (user != null) {
            try {
                if (user.getPassword().equals(passwordhasher.encrypt(password))) {
                    return user;
                }
            } catch (NoSuchAlgorithmException ex) {
                Logger.getLogger(UserManagementControllerImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }

    @Override
    public boolean createUser(String username, String password, String userType) {
        User user = new User(username, password, userType);
        if (UserValidation.isValid(user)) {
            try {
                user.setPassword(passwordhasher.encrypt(password));
            } catch (NoSuchAlgorithmException ex) {
                Logger.getLogger(UserManagementControllerImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
            return dataManager.saveUser(user);
        }
        return false;
    }

    @Override
    public boolean deleteUser(String username) {
        return dataManager.deleteUser(username);
    }

    @Override
    public boolean updateUser(String username, String password, String userType) {
        if (username == null || userType == null || !UserValidation.validatePassword(password)) {
            return false;
        }
        User user = dataManager.getUser(username);
        if (user == null) {
            return false;
        }
        try {
            user.setPassword(passwordhasher.encrypt(password));
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(UserManagementControllerImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
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
