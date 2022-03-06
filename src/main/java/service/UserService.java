package service;

import exceptions.InvalidUserException;
import model.User;
import org.hazlewood.connor.bottema.emailaddress.EmailAddressCriteria;
import org.hazlewood.connor.bottema.emailaddress.EmailAddressValidator;
import repository.UserRepository;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import java.security.spec.KeySpec;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.UUID;
import java.util.regex.Pattern;

public class UserService implements ServiceI<User> {

    private final UserRepository userRepository;
    private static final String UNICODE_FORMAT = "UTF8";
    public static final String DESEDE_ENCRYPTION_SCHEME = "DESede";
    private final Cipher cipher;
    SecretKey key;

    public UserService() throws Exception {
        this.userRepository = new UserRepository();
        String myEncryptionKey = "11a50a7c-d167-4e1c-9022-71395ca78f54";
        String myEncryptionScheme = DESEDE_ENCRYPTION_SCHEME;
        var arrayBytes = myEncryptionKey.getBytes(UNICODE_FORMAT);
        KeySpec ks = new DESedeKeySpec(arrayBytes);
        SecretKeyFactory skf = SecretKeyFactory.getInstance(myEncryptionScheme);
        cipher = Cipher.getInstance(myEncryptionScheme);
        key = skf.generateSecret(ks);
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

    public void tryRegisterUser(String emailAddress, String firstName, String lastName, String username, String password) throws InvalidUserException, Exception {
        getErrors(emailAddress, firstName, lastName, username, password, false);
        var userToAdd = new User(emailAddress, firstName, lastName, username, encrypt(password));
        insert(userToAdd);
    }

    public User tryLoginUser(String username, String password) {
        getErrors(null, null, null, username, password, true);
        var user = userRepository.getUserByUsername(username);
        if (user == null) {
            throw new InvalidUserException("Username or Password is incorrect");
        }
        if (!Arrays.equals(decrypt(user.getPassword()).getBytes(), password.getBytes())) {
            throw new InvalidUserException("Username or Password is incorrect");
        }
        return user;
    }

    private void getErrors(String emailAddress, String firstName, String lastName, String username, String password, boolean isForLogin) {
        if (isForLogin) {
            if (username.equals("")) {
                throw new InvalidUserException("Invalid Username");
            }
            if (password.equals("")) {
                throw new InvalidUserException("Empty Password?");
            }
            return;
        }
        if (emailAddress.equals("")) {
            throw new InvalidUserException("Empty Email Address");
        }
        if (!EmailAddressValidator.isValid(emailAddress, EmailAddressCriteria.RECOMMENDED)) {
            throw new InvalidUserException("Invalid Email Address");
        }
        if (emailAddress.length() > 255) {
            throw new InvalidUserException("Email Address exceeds 255 characters");
        }
        var pattern = Pattern.compile("[^\\sa-zAZ_-]", Pattern.CASE_INSENSITIVE);
        if (firstName.equals("")) {
            throw new InvalidUserException("Invalid First Name");
        }
        if (pattern.matcher(firstName).find()) {
            throw new InvalidUserException("Illegal characters found in First Name");
        }
        if (lastName.equals("")) {
            throw new InvalidUserException("Invalid Last Name");
        }
        if (pattern.matcher(lastName).find()) {
            throw new InvalidUserException("Illegal characters found in Last Name");
        }
        if (password.equals("")) {
            throw new InvalidUserException("Empty Password?");
        }
        if (password.length() < 6) {
            throw new InvalidUserException("Password must be at least 6 characters");
        }
        if (username.equals("")) {
            throw new InvalidUserException("Invalid Username");
        }
    }

    private String encrypt(String password) {
        String encryptedString = null;
        try {
            cipher.init(Cipher.ENCRYPT_MODE, key);
            var plainText = password.getBytes();
            byte[] encryptedText = cipher.doFinal(plainText);
            encryptedString = new String(Base64.getEncoder().encode(encryptedText));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return encryptedString;
    }


    private String decrypt(String password) {
        String decryptedText = null;
        try {
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] encryptedText = Base64.getDecoder().decode(password);
            byte[] plainText = cipher.doFinal(encryptedText);
            decryptedText= new String(plainText);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return decryptedText;
    }
}
