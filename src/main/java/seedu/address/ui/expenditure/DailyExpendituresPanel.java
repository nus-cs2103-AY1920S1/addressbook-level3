package seedu.address.ui.expenditure;

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
 * TODO: Implement display for inventory and booking labels.
 */
public class DailyExpendituresPanel extends UiPart<HBox> {
    private static final String FXML = "expenses/DailyExpendituresPanel.fxml";

    @FXML
    private VBox expenditureCardContainer;
    @FXML
    private Label dailyBudgetLabel;
    @FXML
    private Label dailyExpensesLabel;
    @FXML
    private Label dailyBudgetLeftLabel;

    private List<ExpenditureCard> expenditures;
    private Index index;
    private Model model;

    public DailyExpendituresPanel(List<ExpenditureCard> expenditures, Index index, Model model) {
        super(FXML);
        this.expenditures = expenditures;
        this.index = index;
        this.model = model;
        fillDailyExpendituresPanelLabels();
    }

    /**
     * Fills the labels of this expenditure card.
     */
    private void fillDailyExpendituresPanelLabels() {
        expenditureCardContainer.getChildren().clear();

        double totalExpense = IntStream.range(0, expenditures.size())
                .mapToDouble(index -> expenditures.get(index).getExpenditure().getBudget().value).sum();
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
                        day.getTotalBudget().get().value - totalExpense)).getValueStringInCurrency(currency));
            } else {
                dailyBudgetLabel.setText("Budget Not Set");
                dailyBudgetLeftLabel.setText("");
            }
        }



        List<Node> expenditureCards = IntStream.range(0, expenditures.size())
                .mapToObj(Index::fromZeroBased)
                .map(index -> {
                    return expenditures.get(index.getZeroBased()).getRoot();
                }).collect(Collectors.toList());

        expenditureCardContainer.getChildren().addAll(expenditureCards);

    }
    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DailyExpendituresPanel)) {
            return false;
        }

        // state check
        DailyExpendituresPanel otherCard = (DailyExpendituresPanel) other;
        return expenditures.equals(otherCard.expenditures)
                && this.index.equals(otherCard.index);
    }
}
