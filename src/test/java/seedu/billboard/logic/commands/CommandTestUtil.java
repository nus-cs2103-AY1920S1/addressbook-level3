package seedu.billboard.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.billboard.logic.parser.CliSyntax.PREFIX_AMOUNT;
import static seedu.billboard.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.billboard.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.billboard.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.billboard.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import seedu.billboard.commons.core.index.Index;
import seedu.billboard.logic.commands.exceptions.CommandException;
import seedu.billboard.model.Billboard;
import seedu.billboard.model.Model;
import seedu.billboard.model.expense.Expense;
import seedu.billboard.model.expense.NameContainsKeywordsPredicate;
import seedu.billboard.testutil.EditExpenseDescriptorBuilder;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    public static final String VALID_NAME_DINNER = "got dinner";
    public static final String VALID_NAME_TAXES = "paid taxes";
    public static final String VALID_DESCRIPTION_DINNER = "bought mala from pgp";
    public static final String VALID_DESCRIPTION_TAXES = "paid income tax";
    public static final String VALID_AMOUNT_DINNER = "21.50";
    public static final String VALID_AMOUNT_TAXES = "320.50";
    public static final String VALID_TAG_DINNER = "food";
    public static final String VALID_TAG_TAXES = "bills";

    public static final String NAME_DESC_DINNER = " " + PREFIX_NAME + VALID_NAME_DINNER;
    public static final String NAME_DESC_TAXES = " " + PREFIX_NAME + VALID_NAME_TAXES;
    public static final String DESCRIPTION_DESC_DINNER = " " + PREFIX_DESCRIPTION + VALID_DESCRIPTION_DINNER;
    public static final String DESCRIPTION_DESC_TAXES = " " + PREFIX_DESCRIPTION + VALID_DESCRIPTION_TAXES;
    public static final String AMOUNT_DESC_DINNER = " " + PREFIX_AMOUNT + VALID_AMOUNT_DINNER;
    public static final String AMOUNT_DESC_TAXES = " " + PREFIX_AMOUNT + VALID_AMOUNT_TAXES;
    public static final String TAG_DESC_DINNER = " " + PREFIX_TAG + VALID_TAG_DINNER;
    public static final String TAG_DESC_TAXES = " " + PREFIX_TAG + VALID_TAG_TAXES;

    public static final String INVALID_NAME_DESC = " " + PREFIX_NAME + "James&"; // '&' not allowed in names
    public static final String INVALID_AMOUNT_DESC = " " + PREFIX_AMOUNT + "25.20abc"; // alphabet not allowed in amount
    public static final String INVALID_TAG_DESC = " " + PREFIX_TAG + "hubby*"; // '*' not allowed in tags

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final EditCommand.EditExpenseDescriptor DESC_DINNER;
    public static final EditCommand.EditExpenseDescriptor DESC_TAXES;

    static {
        DESC_DINNER = new EditExpenseDescriptorBuilder().withName(VALID_NAME_DINNER)
                .withDescription(VALID_DESCRIPTION_DINNER).withAmount(VALID_AMOUNT_DINNER)
                .withTags(VALID_TAG_TAXES).build();
        DESC_TAXES = new EditExpenseDescriptorBuilder().withName(VALID_NAME_TAXES)
                .withDescription(VALID_DESCRIPTION_TAXES).withAmount(VALID_AMOUNT_TAXES)
                .withTags(VALID_TAG_DINNER, VALID_TAG_TAXES).build();
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - the returned {@link CommandResult} matches {@code expectedCommandResult} <br>
     * - the {@code actualModel} matches {@code expectedModel}
     */
    public static void assertCommandSuccess(Command command, Model actualModel, CommandResult expectedCommandResult,
            Model expectedModel) {
        try {
            CommandResult result = command.execute(actualModel);
            assertEquals(expectedCommandResult, result);
            assertEquals(expectedModel, actualModel);
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

    /**
     * Convenience wrapper to {@link #assertCommandSuccess(Command, Model, CommandResult, Model)}
     * that takes a string {@code expectedMessage}.
     */
    public static void assertCommandSuccess(Command command, Model actualModel, String expectedMessage,
            Model expectedModel) {
        CommandResult expectedCommandResult = new CommandResult(expectedMessage);
        assertCommandSuccess(command, actualModel, expectedCommandResult, expectedModel);
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - a {@code CommandException} is thrown <br>
     * - the CommandException message matches {@code expectedMessage} <br>
     * - the address book, filtered expense list and selected expense in {@code actualModel} remain unchanged
     */
    public static void assertCommandFailure(Command command, Model actualModel, String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        Billboard expectedAddressBook = new Billboard(actualModel.getBillboardExpenses());
        List<Expense> expectedFilteredList = new ArrayList<>(actualModel.getFilteredExpenses());

        assertThrows(CommandException.class, expectedMessage, () -> command.execute(actualModel));
        assertEquals(expectedAddressBook, actualModel.getBillboardExpenses());
        assertEquals(expectedFilteredList, actualModel.getFilteredExpenses());
    }
    /**
     * Updates {@code model}'s filtered list to show only the expense at the given {@code targetIndex} in the
     * {@code model}'s address book.
     */
    public static void showExpenseAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredExpenses().size());

        Expense expense = model.getFilteredExpenses().get(targetIndex.getZeroBased());
        final String[] splitName = expense.getName().name.split("\\s+");
        model.updateFilteredExpenses(new NameContainsKeywordsPredicate(Collections.singletonList(splitName[0])));

        assertEquals(1, model.getFilteredExpenses().size());
    }

}
