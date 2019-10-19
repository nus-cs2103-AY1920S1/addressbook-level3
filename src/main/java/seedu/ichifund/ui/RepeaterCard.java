package seedu.ichifund.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.paint.Paint;
import seedu.ichifund.model.repeater.Repeater;

/**
 * An UI component that displays information of a {@code Repeater}.
 */
public class RepeaterCard extends UiPart<Region> {
    private static final String FXML = "RepeaterListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Repeater repeater;

    @FXML
    private HBox cardPane;
    @FXML
    private Label id;
    @FXML
    private Label description;
    @FXML
    private Label amount;
    @FXML
    private Label category;


    public RepeaterCard(Repeater repeater, int displayedIndex) {
        super(FXML);
        this.repeater = repeater;
        id.setText(displayedIndex + ". ");
        description.setText(repeater.getDescription().toString());
        amount.setText("$" + repeater.getAmount().toString());
        category.setText(repeater.getCategory().toString().toUpperCase());
        if (repeater.isExpenditure()) {
            amount.setTextFill(Paint.valueOf("#ef5350"));
        } else {
            amount.setTextFill(Paint.valueOf("#4caf50"));
        }
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof RepeaterCard)) {
            return false;
        }

        // state check
        RepeaterCard card = (RepeaterCard) other;
        return id.getText().equals(card.id.getText())
                && repeater.equals(card.repeater);
    }
}
