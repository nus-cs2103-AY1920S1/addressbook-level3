package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.util.Pair;

/**
 * An UI component that displays information of a {@code Pair<String, String>}.
 */
public class HistoryCard extends UiPart<Region> {

    private static final String FXML = "HistoryListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Pair<String, String> command;

    @FXML
    private HBox cardPane;
    @FXML
    private Label userInput;
    @FXML
    private FlowPane tags;

    public HistoryCard(Pair<String, String> command) {
        super(FXML);
        this.command = command;
        userInput.setText(command.getValue());
        tags.getChildren().add(new Label(command.getKey()));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof HistoryCard)) {
            return false;
        }

        // state check
        HistoryCard card = (HistoryCard) other;
        return userInput.equals(card.userInput)
                && tags.equals(card.tags);
    }
}
