package thrift.ui;

import static thrift.ui.ColouredLabelFactory.getColouredTagLabel;

import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import thrift.model.transaction.Transaction;

/**
 * An UI component that displays information of a {@code Expense}.
 */
public class ExpenseTransactionCard extends UiPart<Region> {

    private static final String FXML = "ExpenseTransactionCard.fxml";


    private static final int LONG_TAGNAME_LEN = 10;
    private static final String BACKGROUND_COLOR_GREEN = "-fx-background-color: #00897B";
    private static final String BACKGROUND_COLOR_RED = "-fx-background-color: #e53935";
    private static final String BACKGROUND_COLOR_BLUE = "-fx-background-color: #3949AB";
    private static final String BACKGROUND_COLOR_YELLOW = "-fx-background-color: #FFA000";
    private static final String BACKGROUND_COLOR_PURPLE = "-fx-background-color: #755990";
    private static final String BACKGROUND_COLOR_GREY = "-fx-background-color: #959595";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on Thrift level 4</a>
     */

    public final Transaction transaction;

    @FXML
    private HBox cardPane;
    @FXML
    private Label expenseDescription;
    @FXML
    private Label id;
    @FXML
    private Label expenseValue;
    @FXML
    private Label expenseDate;
    @FXML
    private Label expenseRemark;
    @FXML
    private FlowPane tags;

    public ExpenseTransactionCard(Transaction transaction, int displayedIndex) {
        super(FXML);
        this.transaction = transaction;
        id.setText(displayedIndex + ". ");
        expenseDescription.setText(transaction.getDescription().toString());
        expenseValue.setText("-$" + transaction.getValue().toString());
        expenseDate.setText(transaction.getDate().toString());
        expenseRemark.setText("Remarks: " + transaction.getRemark().toString());
        transaction.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> tags.getChildren().add(getColouredTagLabel(tag.tagName)));
        expenseDescription.setWrapText(true);
        expenseValue.setWrapText(true);
        expenseRemark.setWrapText(true);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ExpenseTransactionCard)) {
            return false;
        }

        // state check
        ExpenseTransactionCard card = (ExpenseTransactionCard) other;
        return id.getText().equals(card.id.getText())
                && transaction.equals(card.transaction);
    }
}
