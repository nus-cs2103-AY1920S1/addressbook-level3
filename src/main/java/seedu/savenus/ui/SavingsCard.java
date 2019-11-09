package seedu.savenus.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.savenus.model.savings.Savings;

/**
 * An UI component that displays information of a {@code Savings}.
 */
public class SavingsCard extends UiPart<Region> {

    private static final String FXML = "SavingsCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Savings savings;
    @FXML
    private HBox cardPane;
    @FXML
    private Label savingsPane;
    @FXML
    private Label timePane;

    public SavingsCard(Savings savings) {
        super(FXML);
        this.savings = savings;
        if (this.savings.isWithdraw()) { // check if it is a withdrawal.
            savingsPane.setText("-$" + savings.getSavingsAmount().getAmount().abs());
            timePane.setText(savings.getTimeStamp().getTimeAgoString());
            savingsPane.getStyleClass().clear();
            savingsPane.getStyleClass().add("cell_withdrawal");
            timePane.getStyleClass().clear();
            timePane.getStyleClass().add("cell_savings_timing");
        } else {
            savingsPane.setText("$" + savings.toString());
            timePane.setText(savings.getTimeStamp().getTimeAgoString());
            timePane.getStyleClass().clear();
            timePane.getStyleClass().add("cell_savings_timing");
        }
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof SavingsCard)) {
            return false;
        }

        // state check
        SavingsCard card = (SavingsCard) other;
        return savings.equals(card.savings);
    }
}
