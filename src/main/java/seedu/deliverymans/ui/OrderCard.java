package seedu.deliverymans.ui;

import java.util.Map;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.deliverymans.model.Name;
import seedu.deliverymans.model.order.Order;

/**
 * An UI component that displays information of a {@code Person}.
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
    private Label deliveryman;
    @FXML
    private Label food;
    @FXML
    private FlowPane tags;

    public OrderCard(Order order, int displayedIndex) {
        super(FXML);
        this.order = order;
        id.setText(displayedIndex + ". ");
        orderName.setText(order.getOrderName().fullName);
        customer.setText("Customer: \t" + order.getCustomer().fullName);
        restaurant.setText("Restaurant: \t" + order.getRestaurant().fullName);
        deliveryman.setText("Deliveryman: \t" + order.getDeliveryman().fullName);
        deliveryStatus.setText("\nDelivery completed: " + order.isCompleted());
        Map<Name, Integer> foodList = order.getFoodList();
        StringBuilder sb = new StringBuilder("\n");
        for (Map.Entry<Name, Integer> entry : foodList.entrySet()) {
            sb.append(String.format("Food: %s \tQuantity: %d", entry.getKey().fullName, entry.getValue())).append("\n");
        }
        food.setText(sb.toString());
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
        return id.getText().equals(card.id.getText());
    }
}
