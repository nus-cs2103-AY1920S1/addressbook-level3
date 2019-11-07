package seedu.address.ui;

import java.nio.file.Path;
import java.nio.file.Paths;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import seedu.address.model.transaction.Amount;
import seedu.address.model.transaction.BankAccountOperation;

/**
 * A ui for the status bar that is displayed at the footer of the application.
 */
public class StatusBarFooter extends UiPart<Region> {

    private static final String FXML = "StatusBarFooter.fxml";

    @FXML
    private Label saveLocationStatus;

    @FXML
    private Label balance;

    private Amount amount;

    public StatusBarFooter(Path saveLocation, ObservableList<BankAccountOperation> transactions) {
        super(FXML);
        saveLocationStatus.setText(Paths.get(".").resolve(saveLocation).toString());
        setBalance(transactions);
    }

    public void setBalance(ObservableList<BankAccountOperation> transactions) {
        Amount amount = Amount.zero();
        for (BankAccountOperation transaction : transactions) {
            amount = amount.addAmount(transaction.getAmount());
        }
        this.amount = amount;
        balance.setText("Balance: " + this.amount.toString());
    }

    public void setBalance(Amount amount) {
        balance.setText(amount.toString());
    }
}
