package controller;

import exceptions.InvalidDestinationException;
import listeners.DestinationAddedListener;
import listeners.UserConnectedListener;
import model.Destination;
import presentation.ButtonColumn;
import presentation.DestinationPopup;
import presentation.MainGUI;
import service.DestinationService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.UUID;

public class DestinationController implements UserConnectedListener {

    private final DestinationService destService = new DestinationService();
    private final MainGUI mainGUI;
    private final ArrayList<DestinationAddedListener> listenerList = new ArrayList<>();

    public DestinationController(MainGUI mainGUI) {
        this.mainGUI = mainGUI;
        try {
            mainGUI.getAddDestinationButton().addActionListener(add -> {
                try {
                    var frame = (JFrame)SwingUtilities.getRoot(mainGUI.getPanel1());
                    var modal = new JDialog(frame, "Add Destination", true);
                    var popup = new DestinationPopup();
                    popup.getDestinationLabel().setText("Add Destination");
                    popup.getDestinationButton().setText("Add Destination");
                    popup.getDestinationButton().addActionListener(l -> {
                        try {
                            var newDestName = popup.getDestinationNameTextField().getText();
                            try {
                                destService.tryAddNewDestination(newDestName);
                            } catch (InvalidDestinationException exception) {
                                showErrorMessage(exception.getMessage(), "Uh oh!");
                                return;
                            }
                            updateTable();
                            modal.setVisible(false);
                            JOptionPane.showMessageDialog(null,
                                    newDestName + " added successfully");
                            emitDestinationChange();
                        } catch (Exception ex) {
                            showErrorMessage(ex.getMessage() + "\nCheck the logs for more info", "Fatal Error");
                        }
                    });
                    modal.getContentPane().add(popup.getDestinationPopupPanel());
                    modal.pack();
                    modal.setLocationRelativeTo(null);
                    modal.setVisible(true);
                } catch (Exception ex) {
                    showErrorMessage(ex.getMessage() + "\nCheck the logs for more info", "Fatal Error");
                }
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
                    var destID = (UUID) table.getModel().getValueAt(rowPressed, 0);
                    var destName = (String) table.getModel().getValueAt(rowPressed, 1);
                    destService.delete(destID);
                    updateTable();
                    JOptionPane.showMessageDialog(null,
                            destName + " deleted successfully");
                    emitDestinationChange();
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
                var table = (JTable)e.getSource();
                int rowPressed = Integer.parseInt( e.getActionCommand() );
                var destID = (UUID) table.getModel().getValueAt(rowPressed, 0);
                var destName = (String) table.getModel().getValueAt(rowPressed, 1);
                var frame = (JFrame)SwingUtilities.getRoot(mainGUI.getPanel1());
                var modal = new JDialog(frame, "Edit Destination", true);
                var popup = new DestinationPopup();
                popup.getDestinationNameTextField().setText(destName);
                popup.getDestinationLabel().setText("Edit Destination");
                popup.getDestinationButton().setText("Edit Destination");
                popup.getDestinationButton().addActionListener(edit -> {
                    try {
                        var newDestName = popup.getDestinationNameTextField().getText();
                        try {
                            destService.tryUpdateDestination(destID, newDestName);
                        } catch (InvalidDestinationException ex) {
                            showErrorMessage(ex.getMessage(), "Uh oh!");
                            return;
                        }
                        updateTable();
                        modal.setVisible(false);
                        JOptionPane.showMessageDialog(null,
                                newDestName + " updated successfully");
                        emitDestinationChange();
                    } catch (Exception ex) {
                        showErrorMessage(ex.getMessage() + "\nCheck the logs for more info", "Fatal Error");
                    }
                });
                modal.getContentPane().add(popup.getDestinationPopupPanel());
                modal.pack();
                modal.setLocationRelativeTo(null);
                modal.setVisible(true);
            } catch (Exception ex) {
                showErrorMessage(ex.getMessage() + "\nCheck the logs for more info", "Fatal Error");
            }
        }
    };

    public void addListener(DestinationAddedListener listener) {
        listenerList.add(listener);
    }

    private void emitDestinationChange() {
        for (var listener: listenerList) {
            listener.destinationChange();
        }
    }

    private DefaultTableModel createTableModel(ArrayList<Destination> destinations) {
        var defaultTableModel = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
                return column != 0 && column != 1;
            }
        };
        try {
            String[] columnNames = {"Destination ID", "Destination Name", "", ""};
            defaultTableModel.setColumnIdentifiers(columnNames);
            for(var destination: destinations) {
                Object[] aux = new Object[4];
                aux[0] = destination.getDestinationId();
                aux[1] = destination.getDestinationName();
                aux[2] = "Edit";
                aux[3] = "Delete";
                defaultTableModel.addRow(aux);
            }
        } catch (Exception ex) {
            showErrorMessage(ex.getMessage() + "\nCheck the logs for more info", "Fatal Error");
            return new DefaultTableModel();
        }
        return defaultTableModel;
    }

    private void resetButtonRenderers() {
        new ButtonColumn(mainGUI.getDestinationsTable(), edit, 2);
        new ButtonColumn(mainGUI.getDestinationsTable(), delete, 3);
    }

    private void showErrorMessage(String message, String title) {
        JOptionPane.showMessageDialog(new JFrame(), message, title,
                JOptionPane.ERROR_MESSAGE);
    }

    @Override
    public void updateTable() {
        try {
            mainGUI.getDestinationsTable().setModel(createTableModel(destService.dbSet()));
            resetButtonRenderers();
        } catch (Exception ex) {
            showErrorMessage(ex.getMessage(), "Fatal Error");
        }
    }
}
