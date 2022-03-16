package controller;

import exceptions.InvalidVacationPackageException;
import listeners.DestinationAddedListener;
import listeners.UserConnectedListener;
import model.Destination;
import model.User;
import model.VacationPackage;
import presentation.ButtonColumn;
import presentation.MainGUI;
import presentation.VacationPackagePopup;
import service.DestinationService;
import service.VacationService;

import javax.persistence.NoResultException;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class AdminVacationPackageController implements DestinationAddedListener, UserConnectedListener {

    DestinationService destService = new DestinationService();
    VacationService vacationService = new VacationService();
    MainGUI mainGUI;

    public AdminVacationPackageController(MainGUI mainGUI) {
        this.mainGUI = mainGUI;
        try {
            var destinationNames = new ArrayList<>(destService.dbSet().stream().map(Destination::getDestinationName).toList());
            destinationNames.forEach(i -> this.mainGUI.getDestinationComboBox().addItem(i));
            this.mainGUI.getDestinationComboBox().addItemListener(i -> {
                try {
                    if (i.getStateChange() != ItemEvent.SELECTED) {
                        return;
                    }
                    updateTable();
                } catch (Exception ex) {
                    showErrorMessage(ex.getMessage() + "\nCheck the logs for more info", "Fatal Error");
                }
            });
            mainGUI.getAddVacationPackageButton().addActionListener(add -> {
                var frame = (JFrame)SwingUtilities.getRoot(mainGUI.getPanel1());
                var selectedDestination = (String) mainGUI.getDestinationComboBox().getSelectedItem();
                var modal = new JDialog(frame, "Add Vacation Package for " + selectedDestination, true);
                var popup = new VacationPackagePopup();
                popup.getVacationLabel().setText("Add Vacation Pakcage");
                popup.getVacationPackageButton().setText("Add Vacation Pakcage");
                popup.getVacationPackageButton().addActionListener(l -> {
                    try {
                        var vacName = popup.getVacationNameText().getText();
                        var vacExtraDet = popup.getExtraDetailsText().getText();
                        var price = popup.getVacationPriceText().getText();
                        var capacity = popup.getVacationCapacityText().getText();
                        var startDate = popup.getStartPeriodDate().getDate();
                        var endDate = popup.getEndPeriodDate().getDate();
                        var destination = destService.getDestinationByName(selectedDestination);
                        try {
                            vacationService.tryAddNewVacationPackage(vacName, vacExtraDet, price,
                                startDate, endDate, capacity, destination);
                        } catch (InvalidVacationPackageException exception) {
                            showErrorMessage(exception.getMessage(), "Uh oh!");
                            return;
                        }
                        updateTable();
                        modal.setVisible(false);
                        JOptionPane.showMessageDialog(null,
                                vacName + " added successfully");
                    } catch (Exception ex) {
                        showErrorMessage(ex.getMessage() + "\nCheck the logs for more info", "Fatal Error");
                    }
                });
                modal.getContentPane().add(popup.getVacationPackagePopupPanel());
                modal.pack();
                modal.setLocationRelativeTo(null);
                modal.setVisible(true);
            });
        } catch (Exception ex) {
            showErrorMessage(ex.getMessage() + "\nCheck the logs for more info", "Fatal Error");
        }
    }

    Action delete = new AbstractAction()
    {
        public void actionPerformed(ActionEvent e)
        {
            try {
                int result = JOptionPane.showConfirmDialog(null,
                        "Are you sure you want to delete this?",
                        "Delete Alert",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE);
                if (result == JOptionPane.YES_OPTION) {
                    var table = (JTable)e.getSource();
                    int rowPressed = Integer.parseInt( e.getActionCommand() );
                    var vacationID = (UUID) table.getModel().getValueAt(rowPressed, 0);
                    var vacationName = (String) table.getModel().getValueAt(rowPressed, 1);
                    vacationService.delete(vacationID);
                    updateTable();
                    JOptionPane.showMessageDialog(null,
                            vacationName + " deleted successfully");
                }
            } catch (Exception ex) {
                showErrorMessage(ex.getMessage() + "\nCheck the logs for more info", "Fatal Error");
            }
        }
    };

    Action edit = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                var table = (JTable) e.getSource();
                int rowPressed = Integer.parseInt(e.getActionCommand());
                var selectedDestination = (String) mainGUI.getDestinationComboBox().getSelectedItem();
                var vacationID = (UUID) table.getModel().getValueAt(rowPressed, 0);
                var vacationName = (String) table.getModel().getValueAt(rowPressed, 1);
                var vacationExtraDetails = (String) table.getModel().getValueAt(rowPressed, 2);
                var price = (Float) table.getModel().getValueAt(rowPressed, 3);
                var startDate = (LocalDate) table.getModel().getValueAt(rowPressed, 4);
                var endDate = (LocalDate) table.getModel().getValueAt(rowPressed, 5);
                var capacity = (Integer) table.getModel().getValueAt(rowPressed, 6);
                var vacationPackageUsers = new ArrayList<>(((List<User>) table.getModel().getValueAt(rowPressed, 10)));
                var destination = destService.getDestinationByName(selectedDestination);
                var frame = (JFrame) SwingUtilities.getRoot(mainGUI.getPanel1());
                var modal = new JDialog(frame, "Edit Vacation Package", true);
                var popup = new VacationPackagePopup();
                popup.getVacationNameText().setText(vacationName);
                popup.getExtraDetailsText().setText(vacationExtraDetails);
                popup.getVacationPriceText().setText(price.toString());
                popup.getStartPeriodDate().setDate(startDate);
                popup.getEndPeriodDate().setDate(endDate);
                popup.getVacationCapacityText().setText(capacity.toString());
                popup.getVacationLabel().setText("Edit Vacation Package");
                popup.getVacationPackageButton().setText("Edit Vacation Package");
                popup.getVacationPackageButton().addActionListener(edit -> {
                    try {
                        var newVacName = popup.getVacationNameText().getText();
                        var newVacExtraDet = popup.getExtraDetailsText().getText();
                        var newPrice = popup.getVacationPriceText().getText();
                        var newCapacity = popup.getVacationCapacityText().getText();
                        var newStartDate = popup.getStartPeriodDate().getDate();
                        var newEndDate = popup.getEndPeriodDate().getDate();
                        try {
                            vacationService.tryUpdateVacationPackage(vacationID, newVacName, newVacExtraDet, newPrice,
                                    newStartDate, newEndDate, newCapacity, destination, vacationPackageUsers, null, true);
                        } catch (InvalidVacationPackageException ex) {
                            showErrorMessage(ex.getMessage(), "Uh oh!");
                            return;
                        }
                        updateTable();
                        modal.setVisible(false);
                        JOptionPane.showMessageDialog(null,
                                newVacName + " updated successfully");
                    } catch (Exception ex) {
                        showErrorMessage(ex.getMessage() + "\nCheck the logs for more info", "Fatal Error");
                    }
                });
                modal.getContentPane().add(popup.getVacationPackagePopupPanel());
                modal.pack();
                modal.setLocationRelativeTo(null);
                modal.setVisible(true);
            } catch (Exception ex) {
                showErrorMessage(ex.getMessage() + "\nCheck the logs for more info", "Fatal Error");
            }
        }
    };

    private DefaultTableModel createTableModel(ArrayList<VacationPackage> vacationPackages) {
        var defaultTableModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 8 || column == 9;
            }
        };
        try {
            String[] columnNames = {"Vacation ID", "Vacation Name", "Extra Details", "Vacation Price (€)",
                    "Start Period", "End Period", "Capacity", "Status", "", "", "Nothing to see here"};
            defaultTableModel.setColumnIdentifiers(columnNames);
            for(var vacationPackage: vacationPackages) {
                Object[] aux = new Object[11];
                aux[0] = vacationPackage.getVacationPackageId();
                aux[1] = vacationPackage.getVacationName();
                aux[2] = vacationPackage.getExtraDetails();
                aux[3] = vacationPackage.getVacationPrice();
                aux[4] = vacationPackage.getStartPeriod();
                aux[5] = vacationPackage.getEndPeriod();
                aux[6] = vacationPackage.getVacationCapacity();
                aux[7] = vacationPackage.getStatusEnumForVacation();
                aux[8] = "Edit";
                aux[9] = "Delete";
                aux[10] = vacationPackage.getVacationPackageUsers();
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

    @Override
    public void destinationChange() {
        mainGUI.getDestinationComboBox().removeAllItems();
        try {
            var destinationNames = new ArrayList<>(destService.dbSet().stream().map(Destination::getDestinationName).toList());
            destinationNames.forEach(i -> this.mainGUI.getDestinationComboBox().addItem(i));
        } catch (Exception ex) {
            showErrorMessage(ex.getMessage() + "\nCheck the logs for more info", "Fatal Error");
        }
    }

    @Override
    public void updateTable() {
        try {
            try {
                var vacPacks = new ArrayList<>(destService
                        .getDestinationByName((String) mainGUI.getDestinationComboBox()
                                .getSelectedItem()).getVacationPackages());
                mainGUI.getVacationsByDestinationTable()
                        .setModel(createTableModel(vacPacks));
            } catch (NoResultException ex) {
                mainGUI.getVacationsByDestinationTable()
                        .setModel(createTableModel(new ArrayList<>()));
            }
            var columnModel = mainGUI.getVacationsByDestinationTable().getColumnModel().getColumn(10);
            columnModel.setMinWidth(0);
            columnModel.setMaxWidth(0);
            columnModel.setPreferredWidth(0);
            resetButtonRenderers();
        } catch (Exception ex) {
            showErrorMessage(ex.getMessage(), "Fatal Error");
        }
    }

    private void resetButtonRenderers() {
        new ButtonColumn(mainGUI.getVacationsByDestinationTable(), edit, 8);
        new ButtonColumn(mainGUI.getVacationsByDestinationTable(), delete, 9);
    }

}
