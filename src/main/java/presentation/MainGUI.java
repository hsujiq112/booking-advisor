package presentation;

import controller.UserController;

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
        var myFoundUser = userController.getUserByID(UUID.fromString("a63799f4-c752-40f3-9d7a-26fece92379c"));
        System.out.println("UITE AM GASIT USERU");
        System.out.println(myFoundUser);
    }
}
