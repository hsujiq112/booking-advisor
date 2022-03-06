package service;

import model.User;
import repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class UserService {

    private final UserRepository userRepository;

    public UserService() {
        this.userRepository = new UserRepository();
    }

    public void createUser(User user) {
        //checkers
        userRepository.insert(user);
    }

    public ArrayList<User> dbSet() {
        var users = new ArrayList<User>();
        userRepository.dbSet().forEach(i -> {
            if (i != null) {
                users.add(i);
            }
        });
        return users;
    }

    public User getUserByID(UUID id) {
        return userRepository.findById(id);
    }
}
