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

    private Budget budget;
    private BudgetCard budgetCard;

    public BudgetPanel(Budget budget) {
        super(FXML);
        this.budget = budget;
        expenseListPanel = new ExpenseListPanel(this.budget.getExpenses(), false);
        expenseListPanelPlaceholder.getChildren().add(expenseListPanel.getRoot());
        budgetCardPlaceholder.getChildren().add(new BudgetCard(this.budget).getRoot());
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
