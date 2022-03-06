package presentation;

import controller.UserController;
import model.User;

import javax.swing.*;
import java.util.UUID;

public class MainGUI {


    private JTabbedPane tabbedPane1;
    private JPanel panel1;

    private static final UserController userController = new UserController();

    public static void main(String[] args) {
        JFrame frame = new JFrame("MainGUI");
        frame.setContentPane(new MainGUI().panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        //userController.createUser("munti@yahoo.com", "Tudi", "TheMunti");
        //userController.createUser("rares@yahoo.com", "Rares", "Man");
        //userController.createUser("mirkyu@yahoo.com", "Mirky", "Salady");
        var users = userController.dbSet();
        users.forEach(System.out::println);
        userController.delteUser(users.get(0).getUserId());
        users = userController.dbSet();
        users.forEach(System.out::println);
    }
}
