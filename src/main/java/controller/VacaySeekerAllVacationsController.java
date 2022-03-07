package controller;

import exceptions.InvalidVacationPackageException;
import listeners.UserConnectedListener;
import listeners.UserPackageUpdateListener;
import listeners.VacationChangedToUserListener;
import model.User;
import model.VacationPackage;
import presentation.ButtonColumn;
import presentation.MainGUI;
import service.VacationService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.UUID;

public class VacaySeekerAllVacationsController implements UserConnectedListener, VacationChangedToUserListener {

    private final MainGUI mainGUI;
    private final VacationService vacationService = new VacationService();
    private final ArrayList<UserPackageUpdateListener> userPackageUpdateListeners = new ArrayList<>();
    private final ArrayList<VacationChangedToUserListener> vacationAddedToUserListeners = new ArrayList<>();
    private User currentUserLoggedIn;

    public VacaySeekerAllVacationsController(MainGUI mainGUI) {
        this.mainGUI = mainGUI;
        try {

        } catch (Exception ex) {
            showErrorMessage(ex.getMessage(), "Fatal Error");
        }
    }

    Action addPackageToUser = new AbstractAction()
    {
        public void actionPerformed(ActionEvent e)
        {
            try {
                var table = (JTable)e.getSource();
                int rowPressed = Integer.parseInt( e.getActionCommand() );
                var vacationID = (UUID) table.getModel().getValueAt(rowPressed, 0);
                var vacationPackage = vacationService.findById(vacationID);
                try {
                    vacationService.tryAddVacationToUser(currentUserLoggedIn, vacationPackage);
                } catch (InvalidVacationPackageException ex) {
                    showErrorMessage(ex.getMessage(), "Oh no!");
                }
                emitVacationAdd();
                emitUpdatedUserPackage();
                updateTable();
                JOptionPane.showMessageDialog(null,
                        vacationPackage.getVacationName() + " added successfully to your packages");
            } catch (Exception ex) {
                showErrorMessage(ex.getMessage() + "\nCheck the logs for more info", "Fatal Error");
            }
        }
    };

    private void resetButtonRenderers() {
        new ButtonColumn(mainGUI.getAllVacayTable(), addPackageToUser, 7);
    }

    private DefaultTableModel createTableModel(ArrayList<VacationPackage> vacationPackages) {
        var defaultTableModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 7;
            }
        };
        try {
            String[] columnNames = {"Vacation ID", "Vacation Name", "Extra Details", "Vacation Price",
                    "Start Period", "End Period", "Capacity", ""};
            defaultTableModel.setColumnIdentifiers(columnNames);
            for(var vacationPackage: vacationPackages) {
                Object[] aux = new Object[8];
                aux[0] = vacationPackage.getVacationPackageId();
                aux[1] = vacationPackage.getVacationName();
                aux[2] = vacationPackage.getExtraDetails();
                aux[3] = vacationPackage.getVacationPrice();
                aux[4] = vacationPackage.getStartPeriod();
                aux[5] = vacationPackage.getEndPeriod();
                aux[6] = vacationPackage.getVacationCapacity();
                aux[7] = "Add to your Vacations";
                defaultTableModel.addRow(aux);
            }
        } catch (Exception ex) {
            showErrorMessage(ex.getMessage() + "\nCheck the logs for more info", "Fatal Error");
            return new DefaultTableModel();
        }
        return defaultTableModel;
    }

    private void showErrorMessage(String message, String title) {
        JOptionPane.showMessageDialog(new JFrame(), message, title,
                JOptionPane.ERROR_MESSAGE);
    }

    public void setCurrentUserLoggedIn(User currentUserLoggedIn) {
        this.currentUserLoggedIn = currentUserLoggedIn;
    }

    public void addVacationAddedToUserListener(VacationChangedToUserListener listener) {
        vacationAddedToUserListeners.add(listener);
    }

    public void emitVacationAdd() {
        for(var i: vacationAddedToUserListeners) {
            i.reinitializeTable();
        }
    }

    public void addUserPackageUpdateListener(UserPackageUpdateListener listener) {
        userPackageUpdateListeners.add(listener);
    }

    public void emitUpdatedUserPackage() {
        for(var i: userPackageUpdateListeners) {
            i.reinitializeUser();
        }
    }

    @Override
    public void updateTable() {
        var allVacations = vacationService.getAllAvailablePackagesForUser(currentUserLoggedIn.getUserId());
        mainGUI.getAllVacayTable().setModel(createTableModel(allVacations));
        var columnModel = mainGUI.getAllVacayTable().getColumnModel().getColumn(0);
        columnModel.setMinWidth(0);
        columnModel.setMaxWidth(0);
        columnModel.setPreferredWidth(0);
        resetButtonRenderers();
    }

    @Override
    public void reinitializeTable() {
        updateTable();
    }
}
