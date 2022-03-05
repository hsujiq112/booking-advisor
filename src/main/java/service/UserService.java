package service;

import model.User;
import repository.UserRepository;

public class UserService {

    private final UserRepository userRepository;

    public UserService() {
        this.userRepository = new UserRepository();
    }

    public void createUser(User user) {
        //checkers
        userRepository.insertUser(user);
    }
}
