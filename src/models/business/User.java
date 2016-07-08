package models.business;

import java.io.Serializable;

public class User implements Serializable {

    public static enum UserType {

        ADMIN, LIBRARIAN, BOTH
    };

    private String username;
    private String password;
    private UserType userType;

    public User(String userName, String password, UserType userType) {
        this.username = userName;
        this.password = password;
        this.userType = userType;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return username;
    }

}
