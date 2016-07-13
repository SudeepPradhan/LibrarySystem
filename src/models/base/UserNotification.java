/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models.base;

import businessmodels.User;
import java.util.List;

/**
 *
 * @author Bishal
 */
public class UserNotification implements Subject {

    List<User> members = database.DatabaseFacade.getDataManager().getUsers();

    @Override

    public void newBookAvailableNotification() {
        String message = "New book is available in the library";
        for (User user : members) {
            user.nofityUser("Dear " + user.getUsername() + message);
        }
    }
}
