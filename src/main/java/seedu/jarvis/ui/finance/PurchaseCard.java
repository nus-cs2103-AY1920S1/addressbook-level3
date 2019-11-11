package seedu.jarvis.ui.finance;

import java.text.NumberFormat;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.jarvis.model.finance.purchase.Purchase;
import seedu.jarvis.ui.UiPart;

/**
 * A UI component that displays information of a {@code Purchase}.
 */
public class PurchaseCard extends UiPart<Region> {

    private static final String FXML = "PurchaseListCard.fxml";

    private static NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance();

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Purchase purchase;

    @FXML
    private HBox cardPane;
    @FXML
    private Label id;
    @FXML
    private Label description;
    @FXML
    private Label moneySpent;
    @FXML
    private Label dateOfPurchase;

    public PurchaseCard(Purchase purchase, int displayedIndex) {
        super(FXML);
        this.purchase = purchase;
        id.setText(displayedIndex + ".");
        description.setText(purchase.getDescription().getPurchaseDescription());
        moneySpent.setText("I paid " + currencyFormatter.format(purchase.getMoneySpent().getPurchaseAmount()));
        dateOfPurchase.setText("on " + purchase.getDateOfPurchase().toString());
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof PurchaseCard)) {
            return false;
        }

        //state check
        PurchaseCard card = (PurchaseCard) other;
        return id.getText().equals(card.id.getText())
                && description.getText().equals(card.description.getText())
                && moneySpent.getText().equals(card.moneySpent.getText())
                && dateOfPurchase.getText().equals(card.dateOfPurchase.getText());
    }
}
