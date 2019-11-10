package seedu.moolah.ui.testutil;

import static java.time.format.DateTimeFormatter.ofPattern;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.moolah.ui.expense.ExpenseCard.CURRENCY_SYMBOL;
import static seedu.moolah.ui.expense.ExpenseCard.DATE_PATTERN;
import static seedu.moolah.ui.expense.ExpenseCard.PRICE_TEMPLATE;
import static seedu.moolah.ui.expense.ExpenseCard.TIME_PATTERN;

import java.util.List;

import guitests.guihandles.ResultDisplayHandle;
import guitests.guihandles.alias.AliasCardHandle;
import guitests.guihandles.event.EventCardHandle;
import guitests.guihandles.expense.ExpenseCardHandle;
import guitests.guihandles.expense.ExpenseListPanelHandle;
import seedu.moolah.model.alias.Alias;
import seedu.moolah.model.expense.Event;
import seedu.moolah.model.expense.Expense;

/**
 * A set of assertion methods useful for writing GUI tests.
 *
 * Adapted from:
 * https://github.com/se-edu/addressbook-level4/blob/master/src/test/java/seedu/address/ui/testutil/GuiTestAssert.java
 */
public class GuiTestAssert {

    /**
     * Asserts that {@code actualCard} displays the same values as {@code expectedCard} for an {@code Expense Card}.
     */
    public static void assertCardEqualsExpense(ExpenseCardHandle expectedCard, ExpenseCardHandle actualCard) {
        assertEquals(expectedCard.getIndex(), actualCard.getIndex());
        assertEquals(expectedCard.getCategory(), actualCard.getCategory());
        assertEquals(expectedCard.getDate(), actualCard.getDate());
        assertEquals(expectedCard.getTime(), actualCard.getTime());
        assertEquals(expectedCard.getPrice(), actualCard.getPrice());
        assertEquals(expectedCard.getUniqueId(), actualCard.getUniqueId());
    }

    /**
     * Asserts that {@code actualCard} displays the same values as {@code expectedCard} for an {@code Expense Card}.
     */
    public static void assertCardEqualsEvent(EventCardHandle expectedCard, EventCardHandle actualCard) {
        assertEquals(expectedCard.getIndex(), actualCard.getIndex());
        assertEquals(expectedCard.getCategory(), actualCard.getCategory());
        assertEquals(expectedCard.getDate(), actualCard.getDate());
        assertEquals(expectedCard.getTime(), actualCard.getTime());
        assertEquals(expectedCard.getPrice(), actualCard.getPrice());
    }

    /**
     * Asserts that {@code actualCard} displays the same values as {@code expectedCard} for an {@code Alias Card}.
     */
    public static void assertCardEqualsAlias(AliasCardHandle expectedCard, AliasCardHandle actualCard) {
        assertEquals(expectedCard.getAliasName(), actualCard.getAliasInput());
    }

    /**
     * Asserts that {@code actualCard} displays the details of {@code expectedExpense}.
     */
    public static void assertCardDisplaysExpense(Expense expectedExpense, ExpenseCardHandle actualCard) {
        assertEquals(actualCard.getUniqueId(), expectedExpense.getUniqueIdentifier().toString());
        assertEquals(actualCard.getDescription(), expectedExpense.getDescription().fullDescription);
        assertEquals(actualCard.getCategory(), expectedExpense.getCategory().getCategoryName());
        assertEquals(actualCard.getDate(),
                expectedExpense.getTimestamp().getFullTimestamp().format(ofPattern(DATE_PATTERN)));
        assertEquals(actualCard.getTime(),
                expectedExpense.getTimestamp().getFullTimestamp().format(ofPattern(TIME_PATTERN)));
        assertEquals(actualCard.getPrice(),
                String.format(PRICE_TEMPLATE, CURRENCY_SYMBOL, expectedExpense.getPrice().getAsDouble()));
    }

    /**
     * Asserts that {@code actualCard} displays the details of {@code expectedExpense}.
     */
    public static void assertCardDisplaysEvent(Event expectedExpense, EventCardHandle actualCard) {
        assertEquals(actualCard.getDescription(), expectedExpense.getDescription().fullDescription);
        assertEquals(actualCard.getCategory(), expectedExpense.getCategory().getCategoryName());
        assertEquals(actualCard.getPrice(),
                String.format(PRICE_TEMPLATE, CURRENCY_SYMBOL, expectedExpense.getPrice().getAsDouble()));
    }

    /**
     * Asserts that {@code actualCard} displays the details of {@code expectedAlias}.
     */
    public static void assertCardDisplaysAlias(Alias expectedAlias, AliasCardHandle actualCard) {
        assertEquals(actualCard.getAliasName(), expectedAlias.getAliasName());
        assertEquals(actualCard.getAliasInput(), expectedAlias.getInput());
    }

    /**
     * Asserts that the list in {@code ExpenseListPanelHandle} displays the details of {@code expenses} correctly and
     * in the correct order.
     */
    public static void assertListMatching(ExpenseListPanelHandle expenseListPanelHandle, Expense... expenses) {
        for (int i = 0; i < expenses.length; i++) {
            expenseListPanelHandle.navigateToCard(i);
            assertCardDisplaysExpense(expenses[i], expenseListPanelHandle.getCardHandle(i));
        }
    }

    /**
     * Asserts that the list in {@code ExpenseListPanelHandle} displays the details of {@code expense} correctly and
     * in the correct order.
     */
    public static void assertListMatching(ExpenseListPanelHandle expenseListPanelHandle, List<Expense> persons) {
        assertListMatching(expenseListPanelHandle, persons.toArray(new Expense[0]));
    }

    /**
     * Asserts the size of the list in {@code ExpenseListPanelHandle} equals to {@code size}.
     */
    public static void assertListSize(ExpenseListPanelHandle expenseListPanelHandle, int size) {
        int numberOfExpense = expenseListPanelHandle.getListSize();
        assertEquals(size, numberOfExpense);
    }

    /**
     * Asserts the message shown in {@code resultDisplayHandle} equals to {@code expected}.
     */
    public static void assertResultMessage(ResultDisplayHandle resultDisplayHandle, String expected) {
        assertEquals(expected, resultDisplayHandle.getText());
    }
}
