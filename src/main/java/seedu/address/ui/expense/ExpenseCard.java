package seedu.address.ui.expense;

import static seedu.address.commons.util.AppUtil.getImage;

import java.time.format.DateTimeFormatter;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Region;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import seedu.address.model.category.Category;
import seedu.address.model.expense.Expense;
import seedu.address.ui.UiPart;

/**
 * An UI component that displays information of a {@code Expense}.
 */
public class ExpenseCard extends UiPart<Region> {

    private static final Category FOOD = new Category("FOOD");
    private static final Category TRAVEL = new Category("TRAVEL");
    private static final Category TRANSPORT = new Category("TRANSPORT");
    private static final Category SHOPPING = new Category("SHOPPING");
    private static final Category UTILITIES = new Category("UTILITIES");
    private static final Category HEALTHCARE = new Category("HEALTHCARE");
    private static final Category ENTERTAINMENT = new Category("ENTERTAINMENT");
    private static final Category EDUCATION = new Category("EDUCATION");

    private static final String FXML = "ExpenseListCard.fxml";
    private static final String FOOD_ICON = "/images/category/food.png";
    private static final String SHOPPING_ICON = "/images/category/shopping.png";
    private static final String TRANSPORT_ICON = "/images/category/transport.png";
    private static final String UTILITIES_ICON = "/images/category/utilities.png";
    private static final String TRAVEL_ICON = "/images/category/travel.png";
    private static final String EDUCATION_ICON = "/images/category/education.png";
    private static final String ENTERTAINMENT_ICON = "/images/category/entertainment.png";
    private static final String HEALTHCARE_ICON = "/images/category/healthcare.png";
    private static final String OTHERS_ICON = "/images/category/others.png";

    private static final String DATE_PATTERN = "dd MMM yyyy";
    private static final String TIME_PATTERN = "HH:mm";
    private static final String CURRENCY_SYMBOL = "$";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on MooLah level 4</a>
     */

    private final Expense expense;

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
    @FXML
    private Circle icon;
    @FXML
    private Circle iconBackground;

    public ExpenseCard(Expense expense, int displayedIndex) {
        super(FXML);

        this.expense = expense;

        index.setText(Integer.toString(displayedIndex));

        description.setText(expense.getDescription().fullDescription);
        price.setText(String.format("%s%,.2f", CURRENCY_SYMBOL, expense.getPrice().getAsDouble()));

        date.setText(expense.getTimestamp().getFullTimestamp().format(DateTimeFormatter.ofPattern(DATE_PATTERN)));
        time.setText(expense.getTimestamp().getFullTimestamp().format(DateTimeFormatter.ofPattern(TIME_PATTERN)));

        categories.getChildren().add(new Label(expense.getCategory().getCategoryName()));
        if (FOOD.equals(expense.getCategory())) {
            icon.setFill(new ImagePattern(getImage(FOOD_ICON)));
        } else if (TRAVEL.equals(expense.getCategory())) {
            icon.setFill(new ImagePattern(getImage(TRAVEL_ICON)));
        } else if (HEALTHCARE.equals(expense.getCategory())) {
            icon.setFill(new ImagePattern(getImage(HEALTHCARE_ICON)));
        } else if (EDUCATION.equals(expense.getCategory())) {
            icon.setFill(new ImagePattern(getImage(EDUCATION_ICON)));
        } else if (ENTERTAINMENT.equals(expense.getCategory())) {
            icon.setFill(new ImagePattern(getImage(ENTERTAINMENT_ICON)));
        } else if (UTILITIES.equals(expense.getCategory())) {
            icon.setFill(new ImagePattern(getImage(UTILITIES_ICON)));
        } else if (SHOPPING.equals(expense.getCategory())) {
            icon.setFill(new ImagePattern(getImage(SHOPPING_ICON)));
        } else if (TRANSPORT.equals(expense.getCategory())) {
            icon.setFill(new ImagePattern(getImage(TRANSPORT_ICON)));
        } else {
            icon.setFill(new ImagePattern(getImage(OTHERS_ICON)));
        }
        icon.toFront();
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
