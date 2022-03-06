package controller;

import model.User;
import service.UserService;

import java.util.ArrayList;
import java.util.UUID;

public class UserController {

    private final UserService userService;

    public UserController() {
        userService = new UserService();
    }

    public void createUser(String emailAddress, String firstName, String lastName) {
        var user = new User(emailAddress, firstName, lastName);
        userService.createUser(user);
    }

    public ArrayList<User> dbSet() {
        return userService.dbSet();
    }

    public User getUserByID(UUID id) {
        return userService.getUserByID(id);
    }
}
