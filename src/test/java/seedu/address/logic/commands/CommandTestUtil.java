package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AMOUNT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CURRENCY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.ExpenseList;
import seedu.address.model.Model;
import seedu.address.model.expense.Expense;
import seedu.address.model.expense.NameContainsKeywordsPredicate;
import seedu.address.testutil.EditExpenseDescriptorBuilder;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    public static final String VALID_NAME_VODKA = "Belvedere Vodka";
    public static final String VALID_AMOUNT_VODKA = "30";
    public static final String VALID_CURRENCY_VODKA = "SGD";
    public static final String VALID_DATE_VODKA = "13/10/2019";
    public static final String VALID_NAME_RUM = "Ron Zacapa";
    public static final String VALID_AMOUNT_RUM = "200";
    public static final String VALID_CURRENCY_RUM = "USD";
    public static final String VALID_DATE_RUM = "09/05/2019";
    public static final String VALID_TAG_ALCOHOL = "alcohol";
    public static final String VALID_TAG_DRINKS = "drinks";

    public static final String VALID_BUDGET_NAME_EGYPT = "Egypt";
    public static final String VALID_BUDGET_AMOUNT_EGYPT = "9999";
    public static final String VALID_BUDGET_CURRENCY_EGYPT = "EUR";
    public static final String VALID_BUDGET_STARTDATE_EGYPT = "01/01/2019";
    public static final String VALID_BUDGET_ENDDATE_EGYPT = "31/12/2019";

    public static final String NAME_DESC_VODKA = " " + PREFIX_NAME + VALID_NAME_VODKA;
    public static final String NAME_DESC_RUM = " " + PREFIX_NAME + VALID_NAME_RUM;
    public static final String AMOUNT_DESC_VODKA = " " + PREFIX_AMOUNT + VALID_AMOUNT_VODKA;
    public static final String AMOUNT_DESC_RUM = " " + PREFIX_AMOUNT + VALID_AMOUNT_RUM;
    public static final String CURRENCY_DESC_VODKA = " " + PREFIX_CURRENCY + VALID_CURRENCY_VODKA;
    public static final String CURRENCY_DESC_RUM = " " + PREFIX_CURRENCY + VALID_CURRENCY_RUM;
    public static final String DATE_DESC_VODKA = " " + PREFIX_DATE + VALID_DATE_VODKA;
    public static final String DATE_DESC_RUM = " " + PREFIX_DATE + VALID_DATE_RUM;
    public static final String TAG_DESC_DRINKS = " " + PREFIX_TAG + VALID_TAG_DRINKS;
    public static final String TAG_DESC_ALCOHOL = " " + PREFIX_TAG + VALID_TAG_ALCOHOL;

    public static final String INVALID_NAME_DESC = " " + PREFIX_NAME + " "; // empty string not allowed in name
    public static final String INVALID_AMOUNT_DESC = " " + PREFIX_AMOUNT + "911a"; // character not allowed in amounts
    public static final String INVALID_CURRENCY_DESC = " " + PREFIX_CURRENCY + "X12"; // number not allowed in amounts
    public static final String INVALID_DATE_DESC = " " + PREFIX_DATE + "13/12/19"; // year must be of format yyyy
    public static final String INVALID_TAG_DESC = " " + PREFIX_TAG + "alcohol*"; // '*' not allowed in tags

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final EditCommand.EditExpenseDescriptor DESC_VODKA;
    public static final EditCommand.EditExpenseDescriptor DESC_RUM;

    static {
        DESC_VODKA = new EditExpenseDescriptorBuilder().withName(VALID_NAME_VODKA)
            .withCurrency(VALID_CURRENCY_VODKA).withAmount(VALID_AMOUNT_VODKA).withDate(VALID_DATE_VODKA)
            .withTags(VALID_TAG_DRINKS).build();
        DESC_RUM = new EditExpenseDescriptorBuilder().withName(VALID_NAME_RUM)
            .withCurrency(VALID_CURRENCY_RUM).withAmount(VALID_AMOUNT_RUM).withDate(VALID_DATE_RUM)
            .withTags(VALID_TAG_ALCOHOL, VALID_TAG_DRINKS).build();
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - the returned {@link CommandResult} matches {@code expectedCommandResult} <br>
     * - the {@code actualModel} matches {@code expectedModel}
     */
    public static void assertCommandSuccess(Command command, Model actualModel, CommandResult expectedCommandResult,
                                            Model expectedModel, CommandHistory actualCommandHistory) {
        CommandHistory expectedCommandHistory = new CommandHistory(actualCommandHistory);
        try {
            CommandResult result = command.execute(actualModel, actualCommandHistory);
            assertEquals(expectedCommandResult, result);
            assertEquals(expectedModel, actualModel);
            assertEquals(expectedCommandHistory, actualCommandHistory);
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

    /**
     * Convenience wrapper to {@link #assertCommandSuccess(Command, Model, CommandResult, Model, CommandHistory)}
     * that takes a string {@code expectedMessage}.
     */
    public static void assertCommandSuccess(Command command, Model actualModel, String expectedMessage,
                                            Model expectedModel, CommandHistory actualCommandHistory) {
        CommandResult expectedCommandResult = new CommandResult(expectedMessage);
        assertCommandSuccess(command, actualModel, expectedCommandResult, expectedModel, actualCommandHistory);
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - a {@code CommandException} is thrown <br>
     * - the CommandException message matches {@code expectedMessage} <br>
     * - the expenselist, filtered expense list and selected expense in {@code actualModel} remain unchanged
     */
    public static void assertCommandFailure(Command command, Model actualModel, String expectedMessage,
                                            CommandHistory actualCommandHistory) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        ExpenseList expectedExpenseList = new ExpenseList(actualModel.getExpenseList());
        List<Expense> expectedFilteredList = new ArrayList<>(actualModel.getFilteredExpenseList());

        CommandHistory expectedCommandHistory = new CommandHistory(actualCommandHistory);

        assertThrows(CommandException.class, expectedMessage, () -> command.execute(actualModel, actualCommandHistory));
        assertEquals(expectedExpenseList, actualModel.getExpenseList());
        assertEquals(expectedFilteredList, actualModel.getFilteredExpenseList());
        assertEquals(expectedCommandHistory, actualCommandHistory);
    }

    /**
     * Updates {@code model}'s filtered list to show only the expense at the given {@code targetIndex} in the
     * {@code model}'s expenselist.
     */
    public static void showExpenseAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredExpenseList().size());

        Expense expense = model.getFilteredExpenseList().get(targetIndex.getZeroBased());
        final String[] splitName = expense.getName().fullName.split("\\s+");
        model.updateFilteredExpenseList(new NameContainsKeywordsPredicate(Arrays.asList(splitName[0])));

        assertEquals(1, model.getFilteredExpenseList().size());
    }
}
