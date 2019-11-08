package seedu.moneygowhere.ui;

import java.nio.file.Path;
import java.nio.file.Paths;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import seedu.moneygowhere.model.ReadOnlySpendingBook;

/**
 * A ui for the status bar that is displayed at the footer of the application.
 */
public class StatusBarFooter extends UiPart<Region> {

    private static final String FXML = "StatusBarFooter.fxml";

    @FXML
    private Label saveLocationStatus;

    @FXML
    private Label currency;


    public StatusBarFooter(Path saveLocation, ReadOnlySpendingBook spendingBook) {
        super(FXML);
        spendingBook.registerCurrencyChangedListener((observable, oldValue, newValue) -> {
            currency.setText("Currency: " + newValue.name);
        });
        currency.setText("Currency: " + spendingBook.getCurrencyInUse().name);
        saveLocationStatus.setText(Paths.get(".").resolve(saveLocation).toString());
    }

}
