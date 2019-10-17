package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import seedu.address.model.visit.Visit;

/**
 * An UI component that displays information of a {@code Visit}.
 */
public class OngoingVisitCard extends UiPart<Region> {

    private static final String FXML = "VisitListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Visit visit;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label phone;
    @FXML
    private Label address;
    @FXML
    private Label email;
    @FXML
    private FlowPane tags;
    @FXML
    private VBox visitTodos;

    public OngoingVisitCard(Visit visit) {
        super(FXML);
        this.visit = visit;
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof OngoingVisitCard)) {
            return false;
        }

        // state check
        OngoingVisitCard card = (OngoingVisitCard) other;
        return id.getText().equals(card.id.getText())
                && visit.equals(card.visit);
    }
}
