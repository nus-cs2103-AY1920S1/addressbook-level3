package seedu.address.overview.logic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import static seedu.address.overview.ui.OverviewMessages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.overview.ui.OverviewMessages.MESSAGE_INVALID_NUMBER_FORMAT;
import static seedu.address.overview.ui.OverviewMessages.MESSAGE_NOTIFY_BUDGET_SUCCESS;
import static seedu.address.overview.ui.OverviewMessages.MESSAGE_SET_BUDGET_SUCCESS;
import static seedu.address.testutil.TypicalOverview.OVERVIEW_MODEL_WITH_DATA;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.overview.model.Model;
import seedu.address.overview.model.ModelManager;
import seedu.address.overview.storage.Storage;
import seedu.address.stubs.InventoryLogicStubForOverview;
import seedu.address.stubs.OverviewStorageStub;
import seedu.address.stubs.TransactionLogicStubForOverview;
import seedu.address.testutil.TypicalItem;
import seedu.address.testutil.TypicalTransactions;
import seedu.address.util.CommandResult;

public class LogicManagerTest {

    private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("0.00");

    private LogicManager logicManager;

    public LogicManagerTest() {
        Model overviewModel = new ModelManager(OVERVIEW_MODEL_WITH_DATA);
        Storage overviewStorage = new OverviewStorageStub(OVERVIEW_MODEL_WITH_DATA);

        seedu.address.transaction.logic.Logic transactionLogicStub =
                new TransactionLogicStubForOverview(TypicalTransactions.getTypicalTransactionList());

        seedu.address.inventory.logic.Logic inventoryLogicStub =
                new InventoryLogicStubForOverview(TypicalItem.getTypicalInventoryListForInventoryUse());

        logicManager = new LogicManager(overviewModel, overviewStorage,
                transactionLogicStub, inventoryLogicStub);
    }

    @Test
    public void executeSetCommandTest_correctInput_success() {
        try {
            CommandResult cr = logicManager.execute("set b/500");
            assertEquals(cr.getFeedbackToUser(), String.format(MESSAGE_SET_BUDGET_SUCCESS,
                    DECIMAL_FORMAT.format(500.0)));
        } catch (Exception e) {
            throw new AssertionError("Error: " + e.getMessage());
        }
    }

    @Test
    public void executeSetCommandTest_missingParams_failure() {
        try {
            CommandResult cr = logicManager.execute("set");
            throw new AssertionError("This should not have happened.");
        } catch (Exception e) {
            assertEquals(MESSAGE_INVALID_COMMAND_FORMAT, e.getMessage());
        }
    }

    @Test
    public void executeSetCommandTest_notNumberParams_failure() {
        try {
            CommandResult cr = logicManager.execute("set b/abcde");
            throw new AssertionError("This should not have happened.");
        } catch (Exception e) {
            assertEquals(MESSAGE_INVALID_NUMBER_FORMAT, e.getMessage());
        }
    }

    @Test
    public void executeNotifyCommandTest_correctInput_success() {
        try {
            CommandResult cr = logicManager.execute("notify b/80");
            assertEquals(cr.getFeedbackToUser(), String.format(MESSAGE_NOTIFY_BUDGET_SUCCESS,
                    DECIMAL_FORMAT.format(80.00)));
        } catch (Exception e) {
            throw new AssertionError("Error: " + e.getMessage());
        }
    }

    @Test
    public void executeNotifyCommandTest_missingParams_failure() {
        try {
            CommandResult cr = logicManager.execute("notify");
            throw new AssertionError("This should not have happened.");
        } catch (Exception e) {
            assertEquals(MESSAGE_INVALID_COMMAND_FORMAT, e.getMessage());
        }
    }

    @Test
    public void executeNotifyCommandTest_notNumberParams_failure() {
        try {
            CommandResult cr = logicManager.execute("notify b/abcde");
            throw new AssertionError("This should not have happened.");
        } catch (Exception e) {
            assertEquals(MESSAGE_INVALID_NUMBER_FORMAT, e.getMessage());
        }
    }

    @Test
    public void correctValue_getTotalExpenses_success() {
        assertEquals(-0.0, logicManager.getTotalExpenses());
    }

    @Test
    public void correctValue_getTotalSales_success() {
        assertEquals(0.0, logicManager.getTotalSales());
    }

    @Test
    public void correctValue_getRemainingBudget_success() {
        assertEquals(1500.0, logicManager.getRemainingBudget());
    }

    @Test
    public void correctValue_getExpenseTarget_success() {
        assertEquals(500.0, logicManager.getExpenseTarget());
    }

    @Test
    public void correctValue_getSalesTarget_success() {
        assertEquals(200.0, logicManager.getSalesTarget());
    }

    @Test
    public void correctValue_getBudgetTarget_success() {
        assertEquals(1500, logicManager.getBudgetTarget());
    }

    @Test
    public void correctValue_getTransactionCategories_success() {
        List<String> list = new ArrayList<>();
        list.add("marketing");
        list.add("events");

        assertTrue(list.equals(logicManager.getTransactionCategories()));
    }

    @Test
    public void correctValue_getInventoryCategories_success() {
        List<String> list = new ArrayList<>();
        list.add("food");
        list.add("Book");
        list.add("accessory");

        assertTrue(list.equals(logicManager.getInventoryCategories()));
    }

    @Test
    public void correctValue_getTransactionTotalByCategory_success() {
        assertEquals(-0.0, logicManager.getTransactionTotalByCategory("marketing"));
    }

    @Test
    public void correctValue_getInventoryTotalByCategory_success() {
        assertEquals(4787.0, logicManager.getInventoryTotalByCategory("food"));
    }

    @Test
    public void correctValue_getSalesTotalByMonth_success() {
        assertEquals(0.0, logicManager.getSalesTotalByMonth(
                LocalDate.of(2019, Month.SEPTEMBER, 1)));
    }

    @Test
    public void correctValue_getBudgetLeftByMonth_success() {
        assertEquals(1487.0, logicManager.getBudgetLeftByMonth(
                LocalDate.of(2019, Month.SEPTEMBER, 1)));
    }

    @Test
    public void correctValue_checkNotifications_success() {
        assertTrue(logicManager.checkNotifications().isEmpty());
    }

}
