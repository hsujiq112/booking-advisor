package presentation;

import service.UserService;

import javax.swing.*;

public class MainGUI {


    private JTabbedPane tabbedPane1;
    private JPanel panel1;
    private JTextField loginUsernameText;
    private JPasswordField loginPassword;
    private JTextField registerEmailText;
    private JTextField registerFirstNameText;
    private JTextField resiterLastNameText;
    private JTextField registerUsernameText;
    private JPasswordField registerPassword;
    private JButton registerButton;
    private JButton loginButton;
    private JTabbedPane tabbedPane4;
    private JComboBox vacayComboBox;
    private JButton vacayRefresh;
    private JTabbedPane tabbedPane3;
    private JComboBox destinationComboBox;
    private JButton adminRefresh;
    private JTable destinationsTable;
    private JTable allVacayTable;
    private JTable userVacayTable;
    private JTable vacationsByDestinationTable;
    private JButton addDestinationButton;
    private JButton addVacationPackageButton;
    private JScrollPane destinationScrollFrame;

    public JScrollPane getDestinationScrollFrame() {
        return destinationScrollFrame;
    }

    public JPanel getPanel1() {
        return panel1;
    }

    public JTextField getLoginUsernameText() {
        return loginUsernameText;
    }

    public JPasswordField getLoginPassword() {
        return loginPassword;
    }

    public JTextField getRegisterEmailText() {
        return registerEmailText;
    }

    public JTextField getRegisterFirstNameText() {
        return registerFirstNameText;
    }

    public JTextField getResiterLastNameText() {
        return resiterLastNameText;
    }

    public JTextField getRegisterUsernameText() {
        return registerUsernameText;
    }

    public JPasswordField getRegisterPassword() {
        return registerPassword;
    }

    public JButton getRegisterButton() {
        return registerButton;
    }

    public JButton getLoginButton() {
        return loginButton;
    }

    public JComboBox getVacayComboBox() {
        return vacayComboBox;
    }

    public JButton getVacayRefresh() {
        return vacayRefresh;
    }

    public JComboBox getDestinationComboBox() {
        return destinationComboBox;
    }

    public JButton getAdminRefresh() {
        return adminRefresh;
    }

    public JTable getDestinationsTable() {
        return destinationsTable;
    }

    public JTable getAllVacayTable() {
        return allVacayTable;
    }

    public JTable getUserVacayTable() {
        return userVacayTable;
    }

    public JTable getVacationsByDestinationTable() {
        return vacationsByDestinationTable;
    }

    public JButton getAddDestinationButton() {
        return addDestinationButton;
    }

    public JButton getAddVacationPackageButton() {
        return addVacationPackageButton;
    }
}
