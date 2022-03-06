package service;

import model.User;
import repository.UserRepository;

import java.util.ArrayList;
import java.util.UUID;

public class UserService implements ServiceI<User> {

    private final UserRepository userRepository;

    public UserService() {
        this.userRepository = new UserRepository();
    }

    public void insert(User user) {
        userRepository.insert(user);
    }

    public User findById(UUID id) {
        return userRepository.findById(id);
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

    public void update(User user) {
        userRepository.update(user);
    }

    public void delete(UUID id) {
        userRepository.delete(id);
    }
}
