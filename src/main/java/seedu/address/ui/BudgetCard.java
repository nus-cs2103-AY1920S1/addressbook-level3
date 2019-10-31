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

    @javafx.fxml.FXML
    private HBox cardPane;
    @FXML
    private Label amount;
    @FXML
    private Label id;
    @FXML
    private Label date;
    @FXML
    private FlowPane categories;
    @FXML
    private FlowPane between;

    public BudgetCard(Budget budget, int displayedIndex) {
        super(FXML);
        this.budget = budget;
        id.setText(displayedIndex + ". ");
        amount.setText(budget.getBudget().toString());
        date.setText(budget.getDeadline().toString());
        between.getChildren().add(new Label(budget.getBetween()));
        budget.getCategories().stream()
            .sorted(Comparator.comparing(category -> category.categoryName))
            .forEach(category -> categories.getChildren().add(new Label(category.categoryName)));
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
