package seedu.guilttrip.ui.expense;

import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.guilttrip.model.entry.Expense;
import seedu.guilttrip.ui.UiPart;

/**
 * An UI component that displays information of a {@code Expense}.
 */
public class ExpenseCard extends UiPart<Region> {

    private static final String FXML = "expense/ExpenseCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on GuiltTrip level 4</a>
     */

    public final Expense expense;

    @FXML
    private HBox cardPane;
    @FXML
    private Label desc;
    @FXML
    private Label id;
    @FXML
    private Label date;
    @FXML
    private Label amt;
    @FXML
    private Label category;
    @FXML
    private FlowPane tags;

    public ExpenseCard(Expense expense, int displayedIndex) {
        super(FXML);
        this.expense = expense;
        id.setText(displayedIndex + ". ");

        String descWithType = expense.getDesc().fullDesc;
        desc.setText(descWithType);
        date.setText(expense.getDate().toString());
        amt.setText("$" + expense.getAmount().value);
        category.setText(expense.getCategory().getCategoryName());

        expense.getTags().stream()
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
        if (!(other instanceof ExpenseCard)) {
            return false;
        }

        // state check
        ExpenseCard card = (ExpenseCard) other;
        return id.getText().equals(card.id.getText())
                && expense.equals(card.expense);
    }
}
