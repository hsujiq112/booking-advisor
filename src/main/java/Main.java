import controller.*;
import presentation.MainGUI;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {
        JFrame frame = new JFrame("MainGUI");
        var gui = new MainGUI();
        for(int i = 1; i < gui.getMainTabbedPane().getTabCount(); i++) {
            gui.getMainTabbedPane().setEnabledAt(i, false);
        }
        frame.setContentPane(gui.getPanel1());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1280, 720);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        var destController = new DestinationController(gui);
        var adminVacPackController = new AdminVacationPackageController(gui);
        var vacayController = new VacaySeekerAllVacationsController(gui);
        var vacayUserController = new VacaySeekerUserVacationsController(gui);
        var welcomePageController = new WelcomePageController(gui, vacayController, vacayUserController);
        destController.addListener(adminVacPackController);
        welcomePageController.addListener(destController);
        welcomePageController.addListener(adminVacPackController);
        welcomePageController.addListener(vacayController);
        welcomePageController.addListener(vacayUserController);
        vacayController.addUserPackageUpdateListener(welcomePageController);
        vacayController.addVacationAddedToUserListener(vacayUserController);
        vacayUserController.addUserPackageUpdateListener(welcomePageController);
        vacayUserController.addVacationRemovedToUserListener(vacayController);
    }
}
