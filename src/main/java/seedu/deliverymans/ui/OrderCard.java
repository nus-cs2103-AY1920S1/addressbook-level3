package seedu.deliverymans.ui;

import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.deliverymans.model.order.Order;

/**
 * An UI component that displays information of a {@code Person}.
 */

public class OrderCard extends UiPart<Region> {

    private static final String FXML = "PersonListCard.fxml";

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
    private Label orderName;
    @FXML
    private Label id;
    @FXML
    private Label customer;
    @FXML
    private Label restaurant;
    @FXML
    private Label deliveryStatus;
    @FXML
    private FlowPane tags;
    @FXML
    private Label deliveryman;

    public OrderCard(Order order, int displayedIndex) {
        super(FXML);
        this.order = order;
        id.setText(displayedIndex + ". ");
        orderName.setText(order.getOrderName().fullName);
        customer.setText(order.getCustomer().fullName);
        restaurant.setText(order.getRestaurant().fullName);
        deliveryman.setText(order.getDeliveryman().fullName);
        deliveryStatus.setText(String.valueOf(order.isCompleted()));
        order.getFood().stream()
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
                && order.equals(card.orderName);
    }
}
