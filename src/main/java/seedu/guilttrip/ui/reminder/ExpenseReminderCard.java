package seedu.guilttrip.ui.reminder;

import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.guilttrip.model.entry.Expense;
import seedu.guilttrip.model.entry.Period;
import seedu.guilttrip.model.reminders.EntryReminder;
import seedu.guilttrip.model.util.Frequency;
import seedu.guilttrip.ui.UiPart;
import seedu.guilttrip.ui.expense.ExpenseCard;

/**
* Displays entry for EntryReminder.
*/
public class ExpenseReminderCard extends UiPart<Region> {

    private static final String FXML = "expense/ExpenseCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on GuiltTrip level 4</a>
     */

    public final Expense expense;
    public final Period period;
    public final Frequency freq;
    private EntryReminder reminder;

    @javafx.fxml.FXML
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

    public ExpenseReminderCard(EntryReminder entryReminder, int displayedIndex) {
        super(FXML);
        this.reminder = entryReminder;
        this.expense = (Expense) entryReminder.getEntry();
        this.period = entryReminder.getPeriod();
        this.freq = entryReminder.getFrequency();
        id.setText(displayedIndex + ". ");

        String descWithType = entryReminder.getHeader().toString() + " ~ " + expense.getDesc().fullDesc;
        desc.setText(descWithType);
        date.setText(expense.getDate().toString() + "period: " + period + " freq: " + freq.toString());
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
        ExpenseReminderCard card = (ExpenseReminderCard) other;
        return id.getText().equals(card.id.getText())
                && reminder.equals(card.reminder);
    }
}
