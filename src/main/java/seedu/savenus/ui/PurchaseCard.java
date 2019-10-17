package seedu.savenus.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.savenus.model.purchase.Purchase;

/**
 * An UI component that displays information of a {@code Purchase}.
 */
public class PurchaseCard extends UiPart<Region> {

    private static final String FXML = "PurchaseListCard.fxml";

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
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label price;
    @FXML
    private Label timeAgo;

    public PurchaseCard(Purchase purchase) {
        super(FXML);
        this.purchase = purchase;
        name.setText(purchase.getPurchasedFoodName().fullName);
        price.setText("$" + purchase.getPurchasedFoodPrice());
        timeAgo.setText(purchase.getTimeAgoString());
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

        // state check
        PurchaseCard card = (PurchaseCard) other;
        return purchase.equals(card.purchase);
    }
}
