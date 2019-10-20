package seedu.address.ui.expenditure;

import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.AnchorPane;
import seedu.address.logic.Logic;
import seedu.address.model.Model;
import seedu.address.model.expenditure.Expenditure;
import seedu.address.ui.MainWindow;
import seedu.address.ui.template.PageWithSidebar;

/**
 * {@code Page} for displaying the expenditure details.
 */
public abstract class ExpensesPage extends PageWithSidebar<AnchorPane> {


    protected static final String FXML = "expenses/ExpensesPage.fxml";

    protected List<Expenditure> expenses;

    protected double totalExpenditure;

    protected double totalBudget;

    @FXML
    private Label totalBudgetLabel;

    @FXML
    private Label totalExpenditureLabel;

    @FXML
    private Label budgetLeftLabel;

    @FXML
    private ToggleButton viewOptionButton;


    public ExpensesPage(MainWindow mainWindow, Logic logic, Model model) {
        super(FXML, mainWindow, logic, model);
        expenses = model.getPageStatus().getTrip().getExpenditureList().internalUnmodifiableList;
        generateSummary();
        totalBudgetLabel.setText("Your budget for the trip: $" + totalBudget);
        totalExpenditureLabel.setText("Your total expenses: $" + totalExpenditure);
        budgetLeftLabel.setText("Your budget left: $" + (totalBudget - totalExpenditure));
        viewOptionButton.setText("Show Days");
    }

    /**
     * Generates total expenditure and total budget for the trip
     */
    private void generateSummary(){
        totalExpenditure = expenses.stream().mapToDouble(expense -> {
            return Double.parseDouble(expense.getBudget().toString());
        }).sum();

        totalBudget = Double.parseDouble(model.getPageStatus().getTrip().getBudget().toString());
    };

}
