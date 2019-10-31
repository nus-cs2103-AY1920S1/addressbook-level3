package seedu.address.ui.budget;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.layout.StackPane;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.budget.Budget;
import seedu.address.ui.expense.ExpenseListPanel;
import seedu.address.ui.panel.Panel;
import seedu.address.ui.panel.PanelName;

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
