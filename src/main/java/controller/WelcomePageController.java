package controller;

import exceptions.InvalidUserException;
import model.User;
import presentation.MainGUI;
import service.UserService;

import javax.swing.*;
import java.util.Arrays;

public class WelcomePageController {

    public WelcomePageController(MainGUI mainGUI) {
        try {
            var userService = new UserService();
            mainGUI.getLoginButton().addActionListener(i -> {
                try {
                    var username = mainGUI.getLoginUsernameText().getText();
                    var password = Arrays.toString(mainGUI.getLoginPassword().getPassword());
                    User loggedInUser = null;
                    try {
                        loggedInUser = userService.tryLoginUser(username, password);
                    } catch (InvalidUserException ex) {
                        showErrorMessage(ex.getMessage(), "Uh oh!");
                        return;
                    }
                    if (loggedInUser.getAdmin()) {
                        mainGUI.getMainTabbedPane().setEnabledAt(3, true);
                        mainGUI.getMainTabbedPane().setSelectedIndex(3);
                        mainGUI.getMainTabbedPane().setEnabledAt(1, true);
                        mainGUI.getMainTabbedPane().setEnabledAt(0, false);
                    } else {
                        mainGUI.getMainTabbedPane().setEnabledAt(2, true);
                        mainGUI.getMainTabbedPane().setSelectedIndex(2);
                        mainGUI.getMainTabbedPane().setEnabledAt(1, true);
                        mainGUI.getMainTabbedPane().setEnabledAt(0, false);
                    }
                    mainGUI.getLoginUsernameText().setText("");
                    mainGUI.getLoginPassword().setText("");
                    JOptionPane.showMessageDialog(null,
                            "Welcome, " + username + "!");
                } catch (Exception ex) {
                    showErrorMessage(ex.getMessage(), "Fatal Error");
                }
            });
            mainGUI.getRegisterButton().addActionListener(i -> {
                try {
                    var email = mainGUI.getRegisterEmailText().getText();
                    var firstName = mainGUI.getRegisterFirstNameText().getText();
                    var lastName = mainGUI.getResiterLastNameText().getText();
                    var username = mainGUI.getRegisterUsernameText().getText();
                    var password = Arrays.toString(mainGUI.getRegisterPassword().getPassword());
                    try {
                        userService.tryRegisterUser(email, firstName, lastName, username, password);
                    } catch (InvalidUserException ex) {
                        showErrorMessage(ex.getMessage(), "Uh oh!");
                        return;
                    }
                    JOptionPane.showMessageDialog(null,
                            username + " registered successfully. You can login now!");
                    mainGUI.getRegisterEmailText().setText("");
                    mainGUI.getRegisterFirstNameText().setText("");
                    mainGUI.getResiterLastNameText().setText("");
                    mainGUI.getRegisterUsernameText().setText("");
                    mainGUI.getRegisterPassword().setText("");
                } catch (Exception ex) {
                    showErrorMessage(ex.getMessage(), "Fatal Error");
                }
            });
            mainGUI.getLogOutButton().addActionListener(l -> {
                for(int i = 1; i < mainGUI.getMainTabbedPane().getTabCount(); i++) {
                    mainGUI.getMainTabbedPane().setEnabledAt(i, false);
                }
                mainGUI.getMainTabbedPane().setEnabledAt(0, true);
                mainGUI.getMainTabbedPane().setSelectedIndex(0);
                JOptionPane.showMessageDialog(null,
                        "See you!");
            });
        } catch (Exception ex) {
            showErrorMessage(ex.getMessage(), "Fatal Error");
        }
    }

    private void showErrorMessage(String message, String title) {
        JOptionPane.showMessageDialog(new JFrame(), message, title,
                JOptionPane.ERROR_MESSAGE);
    }
}
