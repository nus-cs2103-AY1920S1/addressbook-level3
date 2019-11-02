package seedu.address.ui.expense;

import java.time.format.DateTimeFormatter;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Region;
import seedu.address.model.expense.Expense;
import seedu.address.ui.UiPart;

/**
 * An UI component that displays information of a {@code Expense}.
 */
public class ExpenseCard extends UiPart<Region> {

    private static final String FXML = "ExpenseListCard.fxml";
    //    private static final String FOOD_ICON = "images/category/food.png";
    //    private static final String TRANSPORT_ICON = "images/category/transport.png";
    //    private static final String UTILITIES_ICON = "images/category/utilities.png";
    //    private static final String TRAVEL_ICON = "images/category/travel.png";
    //    private static final String EDUCATION_ICON = "images/category/education.png";
    //    private static final String ENTERTAINMENT_ICON = "images/category/entertainment.png";
    //    private static final String OTHERS_ICON = "images/category/others.png";
    //    private static final String HEALTHCARE_ICON = "images/category/healthcare.png";


    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on MooLah level 4</a>
     */

    public final Expense expense;

    @FXML
    private AnchorPane expenseCardPane;
    @FXML
    private Label description;
    @FXML
    private Label index;
    @FXML
    private Label price;
    @FXML
    private Label date;
    @FXML
    private Label time;
    @FXML
    private FlowPane categories;
    //    @FXML
    //    private Circle clip;
    //    @FXML
    //    private ImageView icon;

    public ExpenseCard(Expense expense, int displayedIndex) {
        super(FXML);
        this.expense = expense;
        index.setText(Integer.toString(displayedIndex));
        description.setText(expense.getDescription().fullDescription);
        price.setText("$" + expense.getPrice());
        categories.getChildren().add(new Label(expense.getCategory().getCategoryName()));
        date.setText(expense.getTimestamp().fullTimestamp.format(DateTimeFormatter.ISO_DATE));
        time.setText(null);
        //clip.setFill(new ImagePattern(getImage(expense.getCategory().getCategoryName())));
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
        return index.getText().equals(card.index.getText())
                && expense.equals(card.expense);
    }
}
