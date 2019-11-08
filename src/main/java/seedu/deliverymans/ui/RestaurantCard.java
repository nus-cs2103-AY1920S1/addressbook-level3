package seedu.deliverymans.ui;

import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;

import seedu.deliverymans.model.restaurant.Restaurant;

/**
 * An UI component that displays information of a {@code Restaurant}.
 */
public class RestaurantCard extends UiPart<Region> {
    private static final String FXML = "RestaurantListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Restaurant restaurant;

    @javafx.fxml.FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label loc;
    @FXML
    private Label rating;
    @FXML
    private FlowPane tags;

    public RestaurantCard(Restaurant restaurant, int displayedIndex) {
        super(FXML);
        this.restaurant = restaurant;
        id.setText(displayedIndex + ". ");
        name.setText(restaurant.getName().fullName);
        loc.setText("Location: " + restaurant.getLocation().name);
        rating.setText("Rating: " + restaurant.getRating().toString());
        //quantity.setText("Quantity Ordered: " + restaurant.getQuantityOrdered());
        restaurant.getTags().stream()
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
        if (!(other instanceof RestaurantCard)) {
            return false;
        }

        // state check
        RestaurantCard card = (RestaurantCard) other;
        return id.getText().equals(card.id.getText())
                && restaurant.equals(card.restaurant);
    }
}
