package seedu.address.ui;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.transaction.Amount;
import seedu.address.model.transaction.BankAccountOperation;

/**
 * A ui for the status bar that is displayed at the footer of the application.
 */
public class StatusBarFooter extends UiPart<Region> {

    private static final String FXML = "StatusBarFooter.fxml";

    private final Logger logger = LogsCenter.getLogger(getClass());

    @FXML
    private Label saveLocationStatus;

    @FXML
    private Label balance;

    private Amount amount;

    public StatusBarFooter(Path saveLocation, List<BankAccountOperation> transactions) {
        super(FXML);
        saveLocationStatus.setText(Paths.get(".").resolve(saveLocation).toString());
        setBalance(transactions);
    }

    public void setBalance(List<BankAccountOperation> transactions) {
        Amount amount = Amount.zero();
        for (BankAccountOperation transaction : transactions) {
            amount = amount.addAmount(transaction.getAmount());
        }
        this.amount = amount;
        balance.setText("Balance: " + this.amount.toString());
    }

    public void setBalance(Amount amount) {
        logger.info("Amount supplied: " + amount.toString());
        balance.setText(amount.toString());
    }
}
