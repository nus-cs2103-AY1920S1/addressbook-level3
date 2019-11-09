package seedu.address.ui.expense;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import seedu.address.commons.core.index.Index;
import seedu.address.model.Model;
import seedu.address.model.currency.CustomisedCurrency;
import seedu.address.model.itinerary.Budget;
import seedu.address.model.itinerary.day.Day;
import seedu.address.ui.UiPart;

/**
 * A component for displaying the budget details of a {@code Day}.
 */
public class DailyExpensesPanel extends UiPart<HBox> {

    private static final String FXML = "expense/DailyExpensesPanel.fxml";

    @FXML
    private VBox expenseCardContainer;
    @FXML
    private Label dailyBudgetLabel;
    @FXML
    private Label dailyExpensesLabel;
    @FXML
    private Label dailyBudgetLeftLabel;

    private List<ExpenseCard> expenses;
    private Index index;
    private Model model;
    private CustomisedCurrency currency;
    private double totalExpense;

    public DailyExpensesPanel(List<ExpenseCard> expenses, Index index, Model model) {
        super(FXML);
        this.expenses = expenses;
        this.index = index;
        this.model = model;
        currency = model.getTravelPal().getCurrencies().get(0);
        totalExpense = IntStream.range(0, expenses.size())
                .mapToDouble(i -> expenses.get(i).getExpense().getBudget().getValue()).sum();
        fillDailyExpensesPanelLabels();
    }

    /**
     * Fills the labels of this expense card.
     */
    private void fillDailyExpensesPanelLabels() {
        expenseCardContainer.getChildren().clear();
        //fill labels
        dailyExpensesLabel.setText("Expenses: " + new Budget(totalExpense).getValueStringInCurrency(currency));
        dailyBudgetLeftLabel.setText("");
        if (index.getZeroBased() == 0) {
            dailyBudgetLabel.setText("");
        } else {
            Day day = model.getPageStatus().getTrip().getDayList().internalList.get(index.getZeroBased() - 1);
            if (day.getTotalBudget().isPresent()) {
                dailyBudgetLabel.setText("Budget: " + day.getTotalBudget().get().getValueStringInCurrency(currency));
                fillDailyBudgetLeftLabel(day);
            } else {
                dailyBudgetLabel.setText("Budget Not Set");
            }
        }

        List<Node> expenseCards = IntStream.range(0, expenses.size())
                .mapToObj(Index::fromZeroBased)
                .map(index -> {
                    return expenses.get(index.getZeroBased()).getRoot();
                }).collect(Collectors.toList());

        expenseCardContainer.getChildren().addAll(expenseCards);

    }

    /**
     * Fills the {@code dailyBudgetLeftLabel} according to budget left.
     * @param day the day the expense belongs to
     */
    private void fillDailyBudgetLeftLabel(Day day) {
        double budgetLeft = day.getTotalBudget().get().getValue() - totalExpense;
        if (budgetLeft < 0) {
            dailyBudgetLeftLabel.setStyle("-fx-text-fill: red");
        }
        dailyBudgetLeftLabel.setText("Budget left: " + (new Budget(budgetLeft))
                .getValueStringInCurrency(currency));

    }


    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DailyExpensesPanel)) {
            return false;
        }

        // state check
        DailyExpensesPanel otherCard = (DailyExpensesPanel) other;
        return expenses.equals(otherCard.expenses)
                && this.index.equals(otherCard.index);
    }

}
