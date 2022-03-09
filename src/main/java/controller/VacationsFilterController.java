package controller;

import listeners.FilterAppliedListener;
import listeners.UserConnectedListener;
import model.User;
import model.VacationPackageFilterEnum;
import presentation.MainGUI;
import service.VacationService;

import javax.swing.*;
import java.awt.event.ItemEvent;
import java.util.ArrayList;

public class VacationsFilterController implements UserConnectedListener {

    private final ArrayList<FilterAppliedListener> filterAppliedListeners = new ArrayList<>();
    private final VacationService vacationService;
    private final MainGUI mainGUI;
    private User currentUserLoggedIn;

    public VacationsFilterController(MainGUI mainGUI) {
        this.mainGUI = mainGUI;
        vacationService = new VacationService();
        VacationPackageFilterEnum.getEnumsDisplayNames().forEach(i -> mainGUI.getVacayComboBox().addItem(i));
        mainGUI.getVacayComboBox().addItemListener(i -> {
            try {
                if (i.getStateChange() != ItemEvent.SELECTED) {
                    return;
                }
                mainGUI.getFilterValueTextBox().setText("");
                mainGUI.getFilterDatePicker().setText("");
                mainGUI.getFilterDestinationComboBox().setSelectedIndex(0);
                if (i.getItem().equals(VacationPackageFilterEnum.START_DATE)
                        || i.getItem().equals(VacationPackageFilterEnum.END_DATE)) {
                    mainGUI.getFilterDatePicker().setVisible(true);
                    mainGUI.getFilterDestinationComboBox().setVisible(false);
                    mainGUI.getFilterValueTextBox().setVisible(false);
                } else if (i.getItem().equals(VacationPackageFilterEnum.DESTINATION)) {
                    mainGUI.getFilterDestinationComboBox().setVisible(true);
                    mainGUI.getFilterValueTextBox().setVisible(false);
                    mainGUI.getFilterDatePicker().setVisible(false);
                } else {
                    mainGUI.getFilterValueTextBox().setVisible(true);
                    mainGUI.getFilterDestinationComboBox().setVisible(false);
                    mainGUI.getFilterDatePicker().setVisible(false);
                }
                emitFilterApplied();
            } catch (Exception ex) {
                showErrorMessage(ex.getMessage() + "\nCheck the logs for more info");
            }
        });
        mainGUI.getFilterDestinationComboBox().addItemListener(i -> {
            if (i.getStateChange() != ItemEvent.SELECTED) {
                return;
            }
            emitFilterApplied();
        });
        mainGUI.getVacayRefreshButton().addActionListener(i -> emitFilterApplied());
    }

    public void addFilterAppliedListener(FilterAppliedListener listener) {
        filterAppliedListeners.add(listener);
    }

    public void emitFilterApplied() {
        for(var i: filterAppliedListeners) {
            i.applyFilter();
        }
    }

    private void showErrorMessage(String message) {
        JOptionPane.showMessageDialog(new JFrame(), message, "Fatal Error",
                JOptionPane.ERROR_MESSAGE);
    }

    public void setCurrentUserLoggedIn(User currentUserLoggedIn) {
        this.currentUserLoggedIn = currentUserLoggedIn;
    }

    @Override
    public void updateTable() {
        var allAvailableDestinations = vacationService
                .getAllAvailablePackagesForUser(currentUserLoggedIn.getUserId())
                    .stream().map(i -> i.getDestination().getDestinationName()).distinct().toList();
        mainGUI.getFilterDestinationComboBox().removeAllItems();
        allAvailableDestinations.forEach(i -> mainGUI.getFilterDestinationComboBox().addItem(i));
    }
}
