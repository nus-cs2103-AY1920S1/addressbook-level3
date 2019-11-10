package seedu.guilttrip.ui;

//import static java.time.Duration.ofMillis;
//import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;
import static seedu.guilttrip.testutil.TypicalEntries.getTypicalBudgets;

import java.util.Collections;

//import org.junit.jupiter.api.Test;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import seedu.guilttrip.model.entry.Amount;
import seedu.guilttrip.model.entry.Budget;
import seedu.guilttrip.model.entry.Category;
import seedu.guilttrip.model.entry.Date;
import seedu.guilttrip.model.entry.Description;
import seedu.guilttrip.model.entry.Period;
import seedu.guilttrip.model.util.CategoryType;
import seedu.guilttrip.ui.budget.BudgetPanel;
import seedu.guilttrip.ui.gui.guihandles.BudgetCardHandle;
import seedu.guilttrip.ui.gui.guihandles.BudgetListPanelHandle;

/**
 * Unit test for budget list panel.
 */
public class BudgetListPanelTest extends GuiUnitTest {
    private static final ObservableList<Budget> TYPICAL_BUDGETS = FXCollections.observableList(getTypicalBudgets());

    private static final long CARD_CREATION_AND_DELETION_TIMEOUT = 5000;

    private final SimpleObjectProperty<Budget> selectedBudget = new SimpleObjectProperty<>();
    private BudgetListPanelHandle budgetListPanelHandle;

    /*@Test
    public void display() {
        initUi(TYPICAL_BUDGETS);

        for (int i = 0; i < TYPICAL_BUDGETS.size(); i++) {
            budgetListPanelHandle.navigateToCard(TYPICAL_BUDGETS.get(i));
            Budget expectedBudget = TYPICAL_BUDGETS.get(i);

            BudgetCardHandle actualCard = budgetListPanelHandle.getBudgetCardHandle(i);

            assertCardDisplaysEntry(expectedBudget, actualCard);
            assertEquals((i + 1) + ". ", actualCard.getId());
        }
    }*/

    private void assertCardDisplaysEntry(Budget expectedBudget, BudgetCardHandle actualCard) {
    }

    /*@Test
    public void selection_modelSelectedBudgetChanged_selectionChanges() {
        initUi(TYPICAL_BUDGETS);
        Budget secondBudget = TYPICAL_BUDGETS.get(INDEX_SECOND_ENTRY.getZeroBased());
        guiRobot.interact(() -> selectedBudget
                .set(secondBudget));
        guiRobot.pauseForHuman();

        BudgetCardHandle expectedBudget = budgetListPanelHandle
                .getBudgetCardHandle(INDEX_SECOND_ENTRY.getZeroBased());
        BudgetCardHandle selectedBudget = budgetListPanelHandle.getHandleToSelectedCard();
        assertCardEquals(expectedBudget, selectedBudget);
    }*/

    /**
     * Verifies that creating and deleting large number of entries in {@code BudgetPanel} requires lesser than
     * {@code CARD_CREATION_AND_DELETION_TIMEOUT} milliseconds to execute.
     */
    /*@Test
    public void performanceTest() {
        ObservableList<Budget> backingList = createBackingList(10000);

        assertTimeoutPreemptively(ofMillis(CARD_CREATION_AND_DELETION_TIMEOUT), () -> {
            initUi(backingList);
            guiRobot.interact(backingList::clear);
        }, "Creation and deletion of budget cards exceeded time limit");
    }*/

    /**
     * Returns a list of entries containing {@code budgetCount} entries that is used to populate the
     * {@code BudgetPanel}.
     */
    private ObservableList<Budget> createBackingList(int budgetCount) {
        ObservableList<Budget> backingList = FXCollections.observableArrayList();
        for (int i = 0; i < budgetCount; i++) {
            Description desc = new Description(i + "a");
            Amount amt = new Amount("200");
            Category category = new Category("Food", CategoryType.EXPENSE);
            Date date = new Date("2019 11 01");
            Period period = new Period("1m");
            Budget budget = new Budget(category, desc, date, period, amt, Collections.emptySet());
            backingList.add(budget);
        }
        return backingList;
    }

    /**
     * Initializes {@code budgetPanelHandle} with a {@code BudgetPanel} backed by {@code backingList}.
     * Also shows the {@code Stage} that displays only {@code BudgetPanel}.
     */
    private void initUi(ObservableList<Budget> backingList) {
        BudgetPanel budgetPanel =
                new BudgetPanel(backingList);
        uiPartExtension.setUiPart(budgetPanel);

        budgetListPanelHandle = new BudgetListPanelHandle(getChildNode(budgetPanel.getRoot(),
                BudgetListPanelHandle.BUDGET_LIST_VIEW_ID));
    }
}
