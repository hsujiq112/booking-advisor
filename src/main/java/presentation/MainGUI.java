package presentation;

import controller.UserController;

import javax.swing.*;

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
        userController.createUser("rares.man@test.test", "Rares", "Man");
    }
}
