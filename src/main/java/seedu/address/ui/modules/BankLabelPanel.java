package seedu.address.ui.modules;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import seedu.address.ui.UiPart;

/**
 * Panel containing the label of the current bank.
 */
public class BankLabelPanel extends UiPart<Region> {
    private static final String FXML = "BankLabelPanel.fxml";

    @FXML
    private Label bankName;

    public BankLabelPanel(String bankName) {
        super(FXML);
        this.bankName.setText(bankName);
    }
}
