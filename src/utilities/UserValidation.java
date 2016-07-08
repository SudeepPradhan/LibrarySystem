package utilities;

import java.util.List;
import models.business.User;

public class UserValidation {
    
    private static final int MIN_PASSWORD_LENGTH = 6;
    private static final int MIN_USER_NAME_LENGTH = 2;
    
    private static boolean validateUsername(String username) {
        return username != null && username.length() >= MIN_USER_NAME_LENGTH;
    }
    
    public static boolean validatePassword(String password) {
        return password != null && password.length() >= MIN_PASSWORD_LENGTH;
    }
    
    private static boolean validateUniqueUser(String username) {
        List<User> users = database.DatabaseFacade.getDataManager().getUsers();
        for (User c_user : users) {
            if (c_user.getUsername().equalsIgnoreCase(username)) {
                return false;
            }
        }
        return true;
    }
    
    public static boolean isValid(User user) {
        return validate(user.getUsername(), user.getPassword(), user.getPassword(), user.getUserType()) == null;
    }
    
    public static String validate(String username, String password, String repeatPassword, User.UserType usertype) {
        if (!validateUsername(username)) {
            return "Username must be at least " + MIN_USER_NAME_LENGTH + " characters";
        }
        if (!validateUniqueUser(username)) {
            return "Username is already taken";
        }
        return validate(password, repeatPassword, usertype);
    }
    
    public static String validate(String password, String repeatPassword, User.UserType usertype) {
        if (!validatePassword(password)) {
            return "Password must be at least " + MIN_PASSWORD_LENGTH + " characters";
        }
        if (!password.equals(repeatPassword)) {
            return "Password do not match";
        }
        if (usertype == null) {
            return "Usertype can not be blank";
        }
        if (usertype == null) {
            return "Usertype cannot be blank";
        }
        return null;
    }
}
