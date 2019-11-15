package seedu.guilttrip.ui.autoexpense;

import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.guilttrip.model.entry.AutoExpense;
import seedu.guilttrip.ui.UiPart;

/**
 * An UI component that displays information of a {@code AutoExpense}.
 */
public class AutoExpensesCard extends UiPart<Region> {

    private static final String FXML = "autoexpense/AutoExpenseListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on GuiltTrip level 4</a>
     */

    public final AutoExpense autoExpense;

    @FXML
    private HBox cardPane;
    @FXML
    private Label desc;
    @FXML
    private Label id;
    @FXML
    private Label freq;
    @FXML
    private Label nextTime;
    @FXML
    private Label amt;
    @FXML
    private Label category;
    @FXML
    private FlowPane tags;

    public AutoExpensesCard(AutoExpense autoExpense, int displayedIndex) {
        super(FXML);
        this.autoExpense = autoExpense;
        id.setText(displayedIndex + ". ");

        String fullDesc = autoExpense.getDesc().fullDesc;
        desc.setText(fullDesc);
        freq.setText("[" + autoExpense.getFrequency().toString() + "] ");
        nextTime.setText("Next: " + autoExpense.getNextTime().toString());
        amt.setText("$" + autoExpense.getAmount().toString());
        category.setText(autoExpense.getCategory().toString());

        autoExpense.getTags().stream()
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
        if (!(other instanceof AutoExpensesCard)) {
            return false;
        }

        // state check
        AutoExpensesCard card = (AutoExpensesCard) other;
        return id.getText().equals(card.id.getText())
                && autoExpense.equals(card.autoExpense);
    }
}

