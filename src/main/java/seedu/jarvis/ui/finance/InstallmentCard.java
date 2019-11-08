package seedu.jarvis.ui.finance;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.jarvis.model.finance.installment.Installment;
import seedu.jarvis.ui.UiPart;

/**
 * A UI component that displays information of a {@code Installment}.
 */
public class InstallmentCard extends UiPart<Region> {

    private static final String FXML = "InstallmentListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Installment installment;

    @FXML
    private HBox cardPane;
    @FXML
    private Label id;
    @FXML
    private Label description;
    @FXML
    private Label subscriptionFee;

    public InstallmentCard(Installment installment, int displayedIndex) {
        super(FXML);
        this.installment = installment;
        id.setText(displayedIndex + ".");
        description.setText(installment.getDescription().getInstallmentDescription());
        subscriptionFee.setText("I pay $" + installment.getMoneySpentOnInstallment().toString());
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof InstallmentCard)) {
            return false;
        }

        //state check
        InstallmentCard card = (InstallmentCard) other;
        return id.getText().equals(card.id.getText())
                && description.getText().equals(card.description.getText())
                && subscriptionFee.getText().equals(card.subscriptionFee.getText());
    }
}
