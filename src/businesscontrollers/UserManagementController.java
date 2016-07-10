package businesscontrollers;

import java.util.List;
//import models.business.User;
import businessmodels.User;

public interface UserManagementController {

    /**
     * Checks for user credentials.
     *
     * @param username the user name.
     * @param password the password.
     * @return the {@link User} if the credentials are valid, null otherwise.
     */
    public User authenticate(String username, String password);

    public void logout();

    /**
     * Gets list of users.
     *
     * @return list of users.
     */
    public List<User> getUsers();

    /**
     * Creates a new user.
     *
     * @param username the user name.
     * @param password the password.
     * @param userType the {@link Usesr#UserType}.
     * @return true if the user is created, false otherwise.
     */
    public boolean createUser(String username, String password, User.UserType userType);

    /**
     *
     * @param username the user name.
     * @param password the password.
     * @param userType the {@link Usesr#UserType}.
     * @return true if the user type is updated, false otherwise.
     */
    public boolean updateUser(String username, String password, User.UserType userType);

    /**
     * Deletes a user.
     *
     * @param username the user name.
     * @return true if the user is deleted, false otherwise.
     */
    public boolean deleteUser(String username);

}
