package presentation;

import com.github.lgooddatepicker.components.DatePicker;

import javax.swing.*;

public class VacationPackagePopup {
    private JLabel vacationLabel;
    private JButton vacationPackageButton;
    private JTextField vacationNameText;
    private JTextField extraDetailsText;
    private JTextField vacationPriceText;
    private DatePicker startPeriodDate;
    private DatePicker endPeriodDate;
    private JPanel vacationPackagePopupPanel;
    private JTextField vacationCapacityText;

    public JLabel getVacationLabel() {
        return vacationLabel;
    }

    public JButton getVacationPackageButton() {
        return vacationPackageButton;
    }

    public JTextField getVacationNameText() {
        return vacationNameText;
    }

    public JTextField getExtraDetailsText() {
        return extraDetailsText;
    }

    public JTextField getVacationPriceText() {
        return vacationPriceText;
    }

    public JTextField getVacationCapacityText() {
        return vacationCapacityText;
    }

    public JPanel getVacationPackagePopupPanel() {
        return vacationPackagePopupPanel;
    }

    public DatePicker getStartPeriodDate() {
        return startPeriodDate;
    }

    public DatePicker getEndPeriodDate() {
        return endPeriodDate;
    }
}
