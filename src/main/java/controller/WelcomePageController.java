package controller;

import exceptions.InvalidUserException;
import listeners.UserConnectedListener;
import listeners.UserPackageUpdateListener;
import model.User;
import presentation.MainGUI;
import service.UserService;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Arrays;

public class WelcomePageController implements UserPackageUpdateListener {

    private VacaySeekerAllVacationsController vacaySeekerAllVacationsController;
    private VacaySeekerUserVacationsController vacaySeekerUserVacationsController;
    private VacationsFilterController vacationsFilterController;
    private final ArrayList<UserConnectedListener> listenerList = new ArrayList<>();
    private UserService userService;
    private User loggedInUser;

    public WelcomePageController(MainGUI mainGUI, VacaySeekerAllVacationsController vacaySeekerAllVacationsController,
                                 VacaySeekerUserVacationsController vacaySeekerUserVacationsController,
                                 VacationsFilterController vacationsFilterController) {
        try {
            this.vacaySeekerAllVacationsController = vacaySeekerAllVacationsController;
            this.vacaySeekerUserVacationsController = vacaySeekerUserVacationsController;
            this.vacationsFilterController = vacationsFilterController;
            userService = new UserService();
            mainGUI.getLoginButton().addActionListener(i -> {
                try {
                    var username = mainGUI.getLoginUsernameText().getText();
                    var password = Arrays.toString(mainGUI.getLoginPassword().getPassword());
                    loggedInUser = null;
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
                    mainGUI.getUsernameTextField().setText(loggedInUser.getUsername());
                    mainGUI.getFirstNameTextField().setText(loggedInUser.getFirstName());
                    mainGUI.getLastNameTextField().setText(loggedInUser.getLastName());
                    mainGUI.getEmailTextField().setText(loggedInUser.getEmailAddress());
                    vacaySeekerAllVacationsController.setCurrentUserLoggedIn(loggedInUser);
                    vacaySeekerUserVacationsController.setCurrentUserLoggedIn(loggedInUser);
                    vacationsFilterController.setCurrentUserLoggedIn(loggedInUser);
                    emitUserConnected();
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
                    var lastName = mainGUI.getRegisterLastNameText().getText();
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
                    mainGUI.getRegisterLastNameText().setText("");
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
                mainGUI.getUsernameTextField().setText("");
                mainGUI.getFirstNameTextField().setText("");
                mainGUI.getLastNameTextField().setText("");
                mainGUI.getEmailTextField().setText("");
                vacaySeekerAllVacationsController.setCurrentUserLoggedIn(null);
                vacaySeekerUserVacationsController.setCurrentUserLoggedIn(null);
                vacationsFilterController.setCurrentUserLoggedIn(null);
            });
        } catch (Exception ex) {
            showErrorMessage(ex.getMessage(), "Fatal Error");
        }
    }

    public void addListener(UserConnectedListener listener) {
        listenerList.add(listener);
    }

    private void emitUserConnected() {
        for (var listener: listenerList) {
            listener.updateTable();
        }
    }

    private void showErrorMessage(String message, String title) {
        JOptionPane.showMessageDialog(new JFrame(), message, title,
                JOptionPane.ERROR_MESSAGE);
    }

    @Override
    public void reinitializeUser() {
        loggedInUser = userService.findById(loggedInUser.getUserId());
        vacaySeekerAllVacationsController.setCurrentUserLoggedIn(loggedInUser);
        vacaySeekerUserVacationsController.setCurrentUserLoggedIn(loggedInUser);
    }
}
