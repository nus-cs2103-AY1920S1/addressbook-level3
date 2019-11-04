package seedu.moolah.ui.budget;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.layout.StackPane;
import seedu.moolah.commons.core.LogsCenter;
import seedu.moolah.model.budget.Budget;
import seedu.moolah.ui.expense.ExpenseListPanel;
import seedu.moolah.ui.panel.Panel;
import seedu.moolah.ui.panel.PanelName;

/**
 * Panel containing the list of expenses.
 */
public class BudgetPanel extends Panel {
    public static final PanelName PANEL_NAME = new PanelName("Primary Budget");
    private static final String FXML = "BudgetPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(BudgetPanel.class);

    @FXML
    private StackPane budgetCardPlaceholder;
    @FXML
    private StackPane expenseListPanelPlaceholder;
    private ExpenseListPanel expenseListPanel;

    private BudgetCard budgetCard;

    public BudgetPanel(Budget budget) {
        super(FXML);
        expenseListPanel = new ExpenseListPanel(budget.getCurrentPeriodExpenses(), false);
        expenseListPanelPlaceholder.getChildren().add(expenseListPanel.getRoot());
        budgetCard = new BudgetCard(budget);
        budgetCardPlaceholder.getChildren().add(budgetCard.getRoot());
    }

    @Override
    public void view() {
        getRoot().setVisible(true);
        getRoot().setDisable(false);
    }

    @Override
    public void hide() {
        getRoot().setVisible(false);
        getRoot().setDisable(true);
    }
}
