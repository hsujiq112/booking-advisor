import controller.DestinationController;
import presentation.MainGUI;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {
        JFrame frame = new JFrame("MainGUI");
        var gui = new MainGUI();
        frame.setContentPane(gui.getPanel1());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1280, 720);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        new DestinationController(gui);
    }
}
