package seedu.address.ui;

import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.spending.Spending;

/**
 * An UI component that displays information of a {@code Spending}.
 */
public class SpendingCard extends UiPart<Region> {

    private static final String FXML = "SpendingListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Spending spending;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label date;
    @FXML
    private Label address;
    @FXML
    private Label email;
    @FXML
    private FlowPane tags;

    public SpendingCard(Spending spending, int displayedIndex) {
        super(FXML);
        this.spending = spending;
        id.setText(displayedIndex + ". ");
        name.setText(spending.getName().fullName);
        date.setText(spending.getDate().value);
        address.setText(spending.getAddress().value);
        email.setText(spending.getEmail().value);
        spending.getTags().stream()
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
        if (!(other instanceof SpendingCard)) {
            return false;
        }

        // state check
        SpendingCard card = (SpendingCard) other;
        return id.getText().equals(card.id.getText())
                && spending.equals(card.spending);
    }
}
