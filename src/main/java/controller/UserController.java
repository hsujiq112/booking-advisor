package controller;

import model.User;
import service.UserService;

public class UserController {

    private final UserService userService;

    public UserController() {
        userService = new UserService();
    }

    public void createUser(String emailAddress, String firstName, String lastName) {
        var user = new User(emailAddress, firstName, lastName);
        userService.createUser(user);
    }
}
