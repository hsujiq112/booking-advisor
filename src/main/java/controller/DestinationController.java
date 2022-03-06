package controller;

import model.Destination;
import presentation.ButtonColumn;
import presentation.DestinationEditPopup;
import presentation.MainGUI;
import service.DestinationService;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.UUID;

public class DestinationController {

    DestinationService destService = new DestinationService();
    MainGUI mainGUI;

    public DestinationController(MainGUI mainGUI) {
        this.mainGUI = mainGUI;
        mainGUI.getDestinationsTable().setModel(createTableModel(destService.dbSet()));
        resetButtonRenderers();
        mainGUI.getAddDestinationButton().addActionListener(add -> {
            var frame = (JFrame)SwingUtilities.getRoot(mainGUI.getPanel1());
            var modal = new JDialog(frame, "Add Destination", true);
            var popup = new DestinationEditPopup();
            popup.getDestinationLabel().setText("Add Destination");
            popup.getDestinationButton().setText("Add Destination");
            popup.getDestinationButton().addActionListener(l -> {
                var newDestName = popup.getDestinationNameTextField().getText();
                var destToAdd = new Destination(newDestName);
                destService.insert(destToAdd);
                mainGUI.getDestinationsTable().setModel(createTableModel(destService.dbSet()));
                resetButtonRenderers();
                JOptionPane.showMessageDialog(null,
                        newDestName + " added successfully");
                modal.setVisible(false);
            });
            modal.getContentPane().add(popup.getDestinationPopupPanel());
            modal.pack();
            modal.setLocationRelativeTo(null);
            modal.setVisible(true);
        });
    }

    Action delete = new AbstractAction()
    {
        public void actionPerformed(ActionEvent e)
        {
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
                mainGUI.getDestinationsTable().setModel(createTableModel(destService.dbSet()));
                resetButtonRenderers();
                JOptionPane.showMessageDialog(null,
                        destName + " deleted successfully");
            }
        }
    };

    Action edit = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            var table = (JTable)e.getSource();
            int rowPressed = Integer.parseInt( e.getActionCommand() );
            var destID = (UUID) table.getModel().getValueAt(rowPressed, 0);
            var destName = (String) table.getModel().getValueAt(rowPressed, 1);
            var frame = (JFrame)SwingUtilities.getRoot(mainGUI.getPanel1());
            var modal = new JDialog(frame, "Edit Destination", true);
            var popup = new DestinationEditPopup();
            popup.getDestinationNameTextField().setText(destName);
            popup.getDestinationLabel().setText("Edit Destination");
            popup.getDestinationButton().setText("Edit Destination");
            popup.getDestinationButton().addActionListener(edit -> {
                var newDestName = popup.getDestinationNameTextField().getText();
                var editedDest = new Destination(destID, newDestName);
                destService.update(editedDest);
                mainGUI.getDestinationsTable().setModel(createTableModel(destService.dbSet()));
                resetButtonRenderers();
                JOptionPane.showMessageDialog(null,
                        newDestName + " updated successfully");
                modal.setVisible(false);
            });
            modal.getContentPane().add(popup.getDestinationPopupPanel());
            modal.pack();
            modal.setLocationRelativeTo(null);
            modal.setVisible(true);
        }
    };

    private AbstractTableModel createTableModel(ArrayList<Destination> destinations) {
        var defaultTableModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column != 0 && column != 1;
            }
        };

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

        return defaultTableModel;
    }

    private void resetButtonRenderers() {
        new ButtonColumn(mainGUI.getDestinationsTable(), edit, 2);
        new ButtonColumn(mainGUI.getDestinationsTable(), delete, 3);
    }
    
}
