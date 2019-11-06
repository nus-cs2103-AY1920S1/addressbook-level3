package seedu.address.ui;

import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
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
    @FXML
    private FlowPane DEFICIT;

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

    private void displayRemDays(Budget budget) {
        if (budget.getBetweenRaw() <= 3) {
            DEFICIT.getChildren().add(new Label(budget.getBetween()));
        } else {
            budgetDetails.getChildren().add(new Label(budget.getBetween()));
        }
    }

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
