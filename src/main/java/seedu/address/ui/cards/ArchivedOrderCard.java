package seedu.address.ui.cards;
import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.order.Order;
import seedu.address.ui.UiPart;

/**
 * An UI component that displays information of a {@code Order} that had been archived.
 */
public class ArchivedOrderCard extends UiPart<Region> {

    private static final String FXML = "ArchivedOrderListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Order archivedOrder;

    @FXML
    private HBox cardPane;
    @FXML
    private Label id;
    @FXML
    private Label contactNumber;
    @FXML
    private Label customerName;
    @FXML
    private Label phoneId;
    @FXML
    private Label phoneName;
    @FXML
    private Label phoneColour;
    @FXML
    private Label phoneCapacity;
    @FXML
    private Label orderId;
    @FXML
    private Label orderPrice;
    @FXML
    private Label orderStatus;
    @FXML
    private FlowPane tags;

    public ArchivedOrderCard(Order archivedOrder, int displayedIndex) {
        super(FXML);
        this.archivedOrder = archivedOrder;
        id.setText(displayedIndex + ". ");

        customerName.setText(archivedOrder.getCustomer().getCustomerName().fullName);
        contactNumber.setText(archivedOrder.getCustomer().getContactNumber().value);

        System.out.println(customerName);

        phoneId.setText(archivedOrder.getPhone().getIdentityNumber().value);
        phoneName.setText(archivedOrder.getPhone().getPhoneName().fullName);
        phoneColour.setText(archivedOrder.getPhone().getColour().value);
        phoneCapacity.setText(archivedOrder.getPhone().getCapacity().value);

        orderId.setText(archivedOrder.getId().toString());
        orderPrice.setText(archivedOrder.getPrice().value);
        orderStatus.setText(archivedOrder.getStatus().toString());

        archivedOrder.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ArchivedOrderCard)) {
            return false;
        }

        // state check
        ArchivedOrderCard card = (ArchivedOrderCard) other;
        return id.getText().equals(card.id.getText())
                && archivedOrder.equals(card.archivedOrder);
    }
}
