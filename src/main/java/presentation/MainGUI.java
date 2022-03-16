package presentation;

import com.github.lgooddatepicker.components.DatePicker;

import javax.swing.*;

public class MainGUI {


    private JTabbedPane mainTabbedPane;
    private JPanel panel1;
    private JTextField loginUsernameText;
    private JPasswordField loginPassword;
    private JTextField registerEmailText;
    private JTextField registerFirstNameText;
    private JTextField registerLastNameText;
    private JTextField registerUsernameText;
    private JPasswordField registerPassword;
    private JButton registerButton;
    private JButton loginButton;
    private JComboBox<String> vacayComboBox;
    private JButton vacayRefreshButton;
    private JComboBox<String> destinationComboBox;
    private JTable destinationsTable;
    private JTable allVacayTable;
    private JTable userVacayTable;
    private JTable vacationsByDestinationTable;
    private JButton addDestinationButton;
    private JButton addVacationPackageButton;
    private JButton logOutButton;
    private JTextField usernameTextField;
    private JTextField firstNameTextField;
    private JTextField lastNameTextField;
    private JTextField emailTextField;
    private JTextField filterValueTextBox;
    private DatePicker filterDatePicker;
    private JComboBox<String> filterDestinationComboBox;

    public JComboBox<String> getFilterDestinationComboBox() {
        return filterDestinationComboBox;
    }

    public JTabbedPane getMainTabbedPane() {
        return mainTabbedPane;
    }

    public JPanel getPanel1() {
        return panel1;
    }

    public JTextField getLoginUsernameText() {
        return loginUsernameText;
    }

    public JTextField getRegisterEmailText() {
        return registerEmailText;
    }

    public JTextField getRegisterFirstNameText() {
        return registerFirstNameText;
    }

    public JTextField getRegisterLastNameText() {
        return registerLastNameText;
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

    public JComboBox getDestinationComboBox() {
        return destinationComboBox;
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

    public JPasswordField getLoginPassword() {
        return loginPassword;
    }

    public JButton getLogOutButton() {
        return logOutButton;
    }

    public JTextField getUsernameTextField() {
        return usernameTextField;
    }

    public JTextField getFirstNameTextField() {
        return firstNameTextField;
    }

    public JTextField getLastNameTextField() {
        return lastNameTextField;
    }

    public JTextField getEmailTextField() {
        return emailTextField;
    }

    public JButton getVacayRefreshButton() {
        return vacayRefreshButton;
    }

    public JTextField getFilterValueTextBox() {
        return filterValueTextBox;
    }

    public DatePicker getFilterDatePicker() {
        return filterDatePicker;
    }
}
