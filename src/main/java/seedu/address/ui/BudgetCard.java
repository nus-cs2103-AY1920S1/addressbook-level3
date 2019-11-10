package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.budget.Budget;


/**
 * An UI component that displays information of a {@code Budget}.
 */
public class BudgetCard extends UiPart<Region> {

    private static final String FXML = "BudgetCard.fxml";

    public final Budget budget;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label amount;
    @FXML
    private Label amountLeft;
    @FXML
    private Label currency;
    @FXML
    private Label currencyLeft;
    @FXML
    private Label startDate;
    @FXML
    private Label endDate;

    public BudgetCard(Budget budget, int displayedIndex) {
        super(FXML);
        this.budget = budget;
        id.setText(displayedIndex + ". ");
        name.setText(budget.getName().fullName);
        amount.setText(budget.getAmount().value);
        currency.setText(budget.getCurrency().value);
        currencyLeft.setText(budget.getCurrency().value);
        startDate.setText(budget.getStartDate().value);
        endDate.setText(budget.getEndDate().value);
        if (budget.isBudgetPositive()) {
            amountLeft.setText(budget.getAmountLeft().value);
        } else {
            amountLeft.setText('-' + budget.getAmountLeft().value);
            amountLeft.setStyle("-fx-text-fill: #FF0000");
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
