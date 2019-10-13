package seedu.address.ui;

import static seedu.address.overview.ui.OverviewMessages.BUDGET_SUMMARY_TEXT;
import static seedu.address.overview.ui.OverviewMessages.EXPENSE_SUMMARY_TEXT;
import static seedu.address.overview.ui.OverviewMessages.INVENTORY_SUMMARY_TEXT;
import static seedu.address.overview.ui.OverviewMessages.SALES_SUMMARY_TEXT;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import seedu.address.overview.logic.Logic;


/**
 * Defines the display for the overview tab in the user interface.
 */
public class Overview extends UiPart<Region> {

    private static final String FXML = "Overview.fxml";

    @FXML
    private Label totalExpenseValue;

    @FXML
    private Label totalInventoryValue;

    @FXML
    private Label totalSalesValue;

    @FXML
    private Label totalAmountRemaining;

    public Overview(Logic overviewLogic) {
        super(FXML);
        totalExpenseValue.setText(String.format(EXPENSE_SUMMARY_TEXT, overviewLogic.getTotalExpenses(),
                overviewLogic.getExpenseTarget()));
        totalInventoryValue.setText(String.format(INVENTORY_SUMMARY_TEXT, overviewLogic.getTotalInventory()));
        totalSalesValue.setText(String.format(SALES_SUMMARY_TEXT, overviewLogic.getTotalSales(),
                overviewLogic.getSalesTarget()));
        totalAmountRemaining.setText(String.format(BUDGET_SUMMARY_TEXT, overviewLogic.getRemainingBudget(),
                overviewLogic.getBudgetTarget()));
    }

}
