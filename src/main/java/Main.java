import controller.AdminVacationPackageController;
import controller.DestinationController;
import controller.WelcomePageController;
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
        destController.addListener(adminVacPackController);
        new WelcomePageController(gui);
    }
}
