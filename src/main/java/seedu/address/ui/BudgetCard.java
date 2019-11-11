package seedu.address.ui;

import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.transaction.Budget;

/**
 * An UI component that displays information of a {@code Budget}.
 */
public class BudgetCard extends UiPart<Region> {
    private static final String FXML = "BudgetListCard.fxml";

    public final Budget budget;

    @FXML
    private HBox cardPane;
    @FXML
    private Label amount;
    @FXML
    private Label minus;
    @FXML
    private Label id;
    @FXML
    private Label date;
    @FXML
    private FlowPane categories;
    @FXML
    private FlowPane budgetDetails;

    public BudgetCard(Budget budget, int displayedIndex) {
        super(FXML);
        this.budget = budget;
        id.setText(displayedIndex + ". ");
        displayBudget(budget);
        date.setText(budget.getDeadline().toString());
        displayRemDays(budget);
        budgetDetails.getChildren().add(new Label(budget.displayPercentage()));
        budget.getCategories().stream()
            .sorted(Comparator.comparing(category -> category.categoryName))
            .forEach(category -> categories.getChildren().add(new Label(category.categoryName)));
    }

    /**
     * Determines if the number of remaining days till deadline is less than or equals to 3.
     * If so, a {@code DEFICIT} label is displayed, otherwise {@code budgetDetails} label is displayed.
     *
     * @param budget to be displayed in this budget card
     */
    private void displayRemDays(Budget budget) {
        if (budget.getBetweenRaw() < 0) {
            budgetDetails.getChildren().add(new Label("Past"));
            budgetDetails.setId("budgetMinus");
        } else if (budget.getBetweenRaw() <= 3) {
            budgetDetails.getChildren().add(new Label(budget.getBetween()));
            budgetDetails.setId("budgetMinus");
        } else {
            budgetDetails.getChildren().add(new Label(budget.getBetween()));
        }
    }

    /**
     * Determines if the amount of the remaining budget is less than $0.00.
     * If so, a {@code minus} label is displayed, otherwise {@code amount} label is displayed.
     *
     * @param budget to be displayed in this budget card
     */
    private void displayBudget(Budget budget) {
        if (budget.getBudget().isNegative()) {
            amount.setText("");
            minus.setText(budget.displayBudget());
        } else {
            amount.setText(budget.displayBudget());
            minus.setText("");
        }
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof BudgetCard)) {
            return false;
        }

        // state check
        BudgetCard card = (BudgetCard) other;
        return id.getText().equals(card.id.getText())
            && budget.equals(card.budget);
    }
}
