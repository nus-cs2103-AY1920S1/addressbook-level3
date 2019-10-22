package seedu.address.ui;

import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.eatery.Eatery;

/**
 * An UI component that displays information of a {@code Eatery}.
 */
public class EateryCard extends UiPart<Region> {

    private static final String FXML = "EateryListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Eatery eatery;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label address;
    @FXML
    private Label category;
    @FXML
    private FlowPane tags;

    public EateryCard(Eatery eatery, int displayedIndex) {
        super(FXML);
        this.eatery = eatery;
        id.setText(displayedIndex + ". ");
        name.setText(eatery.getName().fullName);
        address.setText(eatery.getAddress().value);
        category.setText(eatery.getCategory().getName());
        eatery.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.getName()))
                .forEach(tag -> tags.getChildren().add(new Label(tag.getName())));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EateryCard)) {
            return false;
        }

        // state check
        EateryCard card = (EateryCard) other;
        return id.getText().equals(card.id.getText())
                && eatery.equals(card.eatery);
    }
}
