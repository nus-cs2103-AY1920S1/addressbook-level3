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

    public DailyExpensesPanel(List<ExpenseCard> expenses, Index index, Model model) {
        super(FXML);
        this.expenses = expenses;
        this.index = index;
        this.model = model;
        fillDailyExpensesPanelLabels();
    }

    /**
     * Fills the labels of this expense card.
     */
    private void fillDailyExpensesPanelLabels() {
        expenseCardContainer.getChildren().clear();

        double totalExpense = IntStream.range(0, expenses.size())
                .mapToDouble(index -> expenses.get(index).getExpense().getBudget().getValue()).sum();
        CustomisedCurrency currency = model.getTravelPal().getCurrencies().get(0);

        //fill labels
        dailyExpensesLabel.setText("Expenses: " + new Budget(totalExpense).getValueStringInCurrency(currency));
        if (index.getZeroBased() == 0) {
            dailyBudgetLabel.setText("");
            dailyBudgetLeftLabel.setText("");
        } else {
            Day day = model.getPageStatus().getTrip().getDayList().internalList.get(index.getZeroBased() - 1);
            if (day.getTotalBudget().isPresent()) {
                dailyBudgetLabel.setText("Budget: " + day.getTotalBudget().get().getValueStringInCurrency(currency));
                dailyBudgetLeftLabel.setText("You still have: " + (new Budget(
                        day.getTotalBudget().get().getValue() - totalExpense))
                        .getValueStringInCurrency(currency));
            } else {
                dailyBudgetLabel.setText("Budget Not Set");
                dailyBudgetLeftLabel.setText("");
            }
        }



        List<Node> expenseCards = IntStream.range(0, expenses.size())
                .mapToObj(Index::fromZeroBased)
                .map(index -> {
                    return expenses.get(index.getZeroBased()).getRoot();
                }).collect(Collectors.toList());

        expenseCardContainer.getChildren().addAll(expenseCards);

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
