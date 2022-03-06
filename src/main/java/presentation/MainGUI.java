package presentation;

import controller.UserController;
import service.UserService;

import javax.swing.*;

public class MainGUI {


    private JTabbedPane tabbedPane1;
    private JPanel panel1;
    private JTextField textField1;
    private JPasswordField passwordField1;
    private JTextField textField2;
    private JTextField textField3;
    private JTextField textField4;
    private JTextField textField5;
    private JPasswordField passwordField2;
    private JButton registerButton;
    private JButton loginButton;

    private static final UserService userService = new UserService();

    public static void main(String[] args) {
        JFrame frame = new JFrame("MainGUI");
        frame.setContentPane(new MainGUI().panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        //userController.createUser("munti@yahoo.com", "Tudi", "TheMunti");
        //userController.createUser("rares@yahoo.com", "Rares", "Man");
        //userController.createUser("mirkyu@yahoo.com", "Mirky", "Salady");


    }
}
