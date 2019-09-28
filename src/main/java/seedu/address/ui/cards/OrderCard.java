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
 * An UI component that displays information of a {@code Order}.
 */
public class OrderCard extends UiPart<Region> {

    private static final String FXML = "OrderListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Order order;

    @FXML
    private HBox cardPane;
    @FXML
    private Label id;
    @FXML
    private Label customerName;
    @FXML
    private Label phoneName;
    @FXML
    private Label phoneColour;
    @FXML
    private Label phoneCapacity;
    @FXML
    private Label orderId;
    @FXML
    private Label price;
    @FXML
    private Label status;
    @FXML
    private FlowPane tags;

    public OrderCard(Order order, int displayedIndex) {
        super(FXML);
        this.order = order;
        id.setText(displayedIndex + ". ");
        customerName.setText(order.getCustomer().getName().fullName);
        phoneName.setText(order.getPhone().getName().fullName);
        phoneColour.setText(order.getPhone().getColour().value);
        phoneCapacity.setText(order.getPhone().getCapacity().toString());
        orderId.setText(order.getId().toString());
        price.setText(order.getPrice().value);
        status.setText(order.getStatus().toString());
        order.getTags().stream()
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
        if (!(other instanceof OrderCard)) {
            return false;
        }

        // state check
        OrderCard card = (OrderCard) other;
        return id.getText().equals(card.id.getText())
                && order.equals(card.order);
    }
}
