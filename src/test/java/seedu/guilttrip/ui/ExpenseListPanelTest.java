package seedu.guilttrip.ui;

import static java.time.Duration.ofMillis;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;
import static seedu.guilttrip.testutil.TypicalEntries.getTypicalExpenses;

import java.util.Collections;

import org.junit.jupiter.api.Test;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import seedu.guilttrip.model.entry.Amount;
import seedu.guilttrip.model.entry.Category;
import seedu.guilttrip.model.entry.Date;
import seedu.guilttrip.model.entry.Description;
import seedu.guilttrip.model.entry.Expense;
import seedu.guilttrip.ui.expense.ExpenseListPanel;
import seedu.guilttrip.ui.gui.guihandles.ExpenseCardHandle;
import seedu.guilttrip.ui.gui.guihandles.ExpenseListPanelHandle;

/**
 * Unit test for expense list panel.
 */
public class ExpenseListPanelTest extends GuiUnitTest {
    private static final ObservableList<Expense> TYPICAL_EXPENSES = FXCollections.observableList(getTypicalExpenses());

    private static final long CARD_CREATION_AND_DELETION_TIMEOUT = 5000;

    private final SimpleObjectProperty<Expense> selectedExpense = new SimpleObjectProperty<>();
    private ExpenseListPanelHandle expenseListPanelHandle;

    @Test
    public void display() {
        initUi(TYPICAL_EXPENSES);

        for (int i = 0; i < TYPICAL_EXPENSES.size(); i++) {
            expenseListPanelHandle.navigateToCard(TYPICAL_EXPENSES.get(i));
            Expense expectedExpense = TYPICAL_EXPENSES.get(i);

            ExpenseCardHandle actualCard = expenseListPanelHandle.getExpenseCardHandle(i);

            assertCardDisplaysEntry(expectedExpense, actualCard);
            assertEquals((i + 1) + ". ", actualCard.getId());
        }
    }

    private void assertCardDisplaysEntry(Expense expectedExpense, ExpenseCardHandle actualCard) {
    }

    /*@Test
    public void selection_modelSelectedExpenseChanged_selectionChanges() {
        initUi(TYPICAL_EXPENSES);
        Expense secondExpense = TYPICAL_EXPENSES.get(INDEX_SECOND_ENTRY.getZeroBased());
        guiRobot.interact(() -> selectedExpense
                .set(secondExpense));
        guiRobot.pauseForHuman();

        ExpenseCardHandle expectedExpense = expenseListPanelHandle
                .getExpenseCardHandle(INDEX_SECOND_ENTRY.getZeroBased());
        ExpenseCardHandle selectedExpense = expenseListPanelHandle.getHandleToSelectedCard();
        assertCardEquals(expectedExpense, selectedExpense);
    }*/

    /**
     * Verifies that creating and deleting large number of entries in {@code ExpenseListPanel} requires lesser than
     * {@code CARD_CREATION_AND_DELETION_TIMEOUT} milliseconds to execute.
     */
    @Test
    public void performanceTest() {
        ObservableList<Expense> backingList = createBackingList(10000);

        assertTimeoutPreemptively(ofMillis(CARD_CREATION_AND_DELETION_TIMEOUT), () -> {
            initUi(backingList);
            guiRobot.interact(backingList::clear);
        }, "Creation and deletion of expense cards exceeded time limit");
    }

    /**
     * Returns a list of entries containing {@code expenseCount} entries that is used to populate the
     * {@code ExpenseListPanel}.
     */
    private ObservableList<Expense> createBackingList(int expenseCount) {
        ObservableList<Expense> backingList = FXCollections.observableArrayList();
        for (int i = 0; i < expenseCount; i++) {
            Description desc = new Description(i + "a");
            Amount amt = new Amount("20");
            Category category = new Category("Food", "Expense");
            Date date = new Date("2019 11 09");
            Expense expense = new Expense(category, desc, date, amt, Collections.emptySet());
            backingList.add(expense);
        }
        return backingList;
    }

    /**
     * Initializes {@code expenseListPanelHandle} with a {@code ExpenseListPanel} backed by {@code backingList}.
     * Also shows the {@code Stage} that displays only {@code ExpenseListPanel}.
     */
    private void initUi(ObservableList<Expense> backingList) {
        ExpenseListPanel expenseListPanel =
                new ExpenseListPanel(backingList);
        uiPartExtension.setUiPart(expenseListPanel);

        expenseListPanelHandle = new ExpenseListPanelHandle(getChildNode(expenseListPanel.getRoot(),
                ExpenseListPanelHandle.EXPENSE_LIST_VIEW_ID));
    }
}
