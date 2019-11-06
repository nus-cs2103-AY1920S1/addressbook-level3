package thrift.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static thrift.logic.parser.CliSyntax.PREFIX_CURRENCY;
import static thrift.logic.parser.CliSyntax.PREFIX_DATE;
import static thrift.logic.parser.CliSyntax.PREFIX_INDEX;
import static thrift.logic.parser.CliSyntax.PREFIX_MONTH;
import static thrift.logic.parser.CliSyntax.PREFIX_NAME;
import static thrift.logic.parser.CliSyntax.PREFIX_OCCURRENCE;
import static thrift.logic.parser.CliSyntax.PREFIX_REMARK;
import static thrift.logic.parser.CliSyntax.PREFIX_TAG;
import static thrift.logic.parser.CliSyntax.PREFIX_VALUE;
import static thrift.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import thrift.commons.core.index.Index;
import thrift.logic.commands.exceptions.CommandException;
import thrift.model.Model;
import thrift.model.Thrift;
import thrift.model.transaction.DescriptionOrRemarkContainsKeywordsPredicate;
import thrift.model.transaction.Transaction;
import thrift.testutil.UpdateTransactionDescriptorBuilder;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    public static final String VALID_DESCRIPTION_BURSARY = "Bursary";
    public static final String VALID_DESCRIPTION_LAKSA = "Laksa";
    public static final String VALID_DESCRIPTION_PENANG_LAKSA = "Penang Laksa";
    public static final String VALID_DESCRIPTION_AIRPODS = "Airpods";
    public static final String VALID_VALUE_BURSARY = "500";
    public static final String VALID_VALUE_LAKSA = "3.50";
    public static final String VALID_VALUE_AIRPODS = "350";
    public static final String VALID_VALUE_BUDGET = "1000";
    public static final String VALID_REMARK_LAKSA = "Delicious!";
    public static final String VALID_REMARK_AIRPODS = "Is this really worth it?";
    public static final String VALID_DATE_BURSARY = "09/10/2019";
    public static final String VALID_DATE_LAKSA = "13/03/1937";
    public static final String VALID_DATE_AIRPODS = "14/03/1937";
    public static final String VALID_DATE_BUDGET = "10/2019";
    public static final String VALID_TAG_AWARD = "Award";
    public static final String VALID_TAG_LUNCH = "Lunch";
    public static final String VALID_TAG_BRUNCH = "Brunch";
    public static final String VALID_TAG_ACCESSORY = "Accessory";
    public static final String VALID_MONTH_JAN_19 = "01/2019";
    public static final String VALID_VALUE_CONVERT = "10000";
    public static final String VALID_CURRENCY_USD = "USD";
    public static final String VALID_CURRENCY_MYR = "MYR";

    public static final String DESC_BURSARY = " " + PREFIX_NAME + VALID_DESCRIPTION_BURSARY;
    public static final String DESC_LAKSA = " " + PREFIX_NAME + VALID_DESCRIPTION_LAKSA;
    public static final String DESC_AIRPODS = " " + PREFIX_NAME + VALID_DESCRIPTION_AIRPODS;
    public static final String VALUE_BURSARY = " " + PREFIX_VALUE + VALID_VALUE_BURSARY;
    public static final String VALUE_LAKSA = " " + PREFIX_VALUE + VALID_VALUE_LAKSA;
    public static final String REMARK_LAKSA = " " + PREFIX_REMARK + VALID_REMARK_LAKSA;
    public static final String VALUE_AIRPODS = " " + PREFIX_VALUE + VALID_VALUE_AIRPODS;
    public static final String VALUE_BUDGET = " " + PREFIX_VALUE + VALID_VALUE_BUDGET;
    public static final String DATE_BUDGET = " " + PREFIX_DATE + VALID_DATE_BUDGET;
    public static final String REMARK_AIRPODS = " " + PREFIX_REMARK + VALID_REMARK_AIRPODS;
    public static final String TAG_BURSARY = " " + PREFIX_TAG + VALID_TAG_AWARD;
    public static final String TAG_LAKSA = " " + PREFIX_TAG + VALID_TAG_LUNCH;
    public static final String TAG_BRUNCH = " " + PREFIX_TAG + VALID_TAG_BRUNCH;
    public static final String TAG_AIRPODS = " " + PREFIX_TAG + VALID_TAG_ACCESSORY;
    public static final String INDEX_TOKEN = " " + PREFIX_INDEX;
    public static final String MONTH_JAN_19 = " " + PREFIX_MONTH + VALID_MONTH_JAN_19;
    public static final String OCCURRENCE_TOKEN = " " + PREFIX_OCCURRENCE;
    public static final String VALUE_CONVERT = " " + PREFIX_VALUE + VALID_VALUE_CONVERT;
    public static final String CURRENCY_USD = " " + PREFIX_CURRENCY + VALID_CURRENCY_USD;
    public static final String CURRENCY_MYR = " " + PREFIX_CURRENCY + VALID_CURRENCY_MYR;

    public static final String INVALID_VALUE = " " + PREFIX_VALUE + ".00"; // missing whole number
    public static final String INVALID_DATE = " " + PREFIX_DATE + "13/aaaa"; // invalid month and non numeric year
    public static final String INVALID_TAG = " " + PREFIX_TAG + "A+"; //illegal character

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final UpdateCommand.UpdateTransactionDescriptor DESC_MEAL;
    public static final UpdateCommand.UpdateTransactionDescriptor DESC_PURCHASE;

    static {
        DESC_MEAL = new UpdateTransactionDescriptorBuilder().withDescription(VALID_DESCRIPTION_LAKSA)
                .withValue(VALID_VALUE_LAKSA).withDate(VALID_DATE_LAKSA).withTags(VALID_TAG_LUNCH).build();

        DESC_PURCHASE = new UpdateTransactionDescriptorBuilder().withDescription(VALID_DESCRIPTION_AIRPODS)
                .withValue(VALID_VALUE_AIRPODS).withDate(VALID_DATE_AIRPODS).withTags(VALID_TAG_ACCESSORY).build();
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - the returned {@link CommandResult} matches {@code expectedCommandResult} <br>
     * - the {@code actualModel} matches {@code expectedModel}
     */
    public static void assertCommandSuccess(Command command, Model actualModel, CommandResult expectedCommandResult,
            Model expectedModel) {
        try {
            CommandResult result;
            if (command instanceof ScrollingCommand) {
                result = ((ScrollingCommand) command).execute(actualModel, null);
            } else {
                result = ((NonScrollingCommand) command).execute(actualModel);
            }
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
     * - thrift, filtered transaction list and selected transaction in {@code actualModel} remain unchanged
     */
    public static void assertCommandFailure(Command command, Model actualModel, String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.

        Thrift expectedThrift = new Thrift(actualModel.getThrift());
        List<Transaction> expectedFilteredList = new ArrayList<>(actualModel.getFilteredTransactionList());

        if (command instanceof ScrollingCommand) {
            assertThrows(CommandException.class, expectedMessage, () -> (
                (ScrollingCommand) command).execute(actualModel, null));
        } else {
            assertThrows(CommandException.class, expectedMessage, (
                )-> ((NonScrollingCommand) command).execute(actualModel));
        }
        assertEquals(expectedThrift, actualModel.getThrift());
        assertEquals(expectedFilteredList, actualModel.getFilteredTransactionList());
    }
    /**
     * Updates {@code model}'s filtered list to show only the transaction at the given {@code targetIndex} in the
     * {@code model}'s thrift.
     */
    public static void showTransactionAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredTransactionList().size());

        Transaction transaction = model.getFilteredTransactionList().get(targetIndex.getZeroBased());
        final String[] splitDescription = transaction.getDescription().toString().split("\\s+");
        model.updateFilteredTransactionList(new DescriptionOrRemarkContainsKeywordsPredicate(
                Arrays.asList(splitDescription[0])));
        assertEquals(1, model.getFilteredTransactionList().size());
    }

    /**
     * Executes undo for the given {@code command} and confirms that the {@code model} matches {@code expectedModel}.
     *
     * @param command is the command that you want to perform undo
     * @param model is the actual model
     * @param expectedModel is the expected model to compare with
     */
    public static void assertUndoCommandSuccess(Undoable command, Model model, Model expectedModel) {
        command.undo(model);
        assertEquals(model, expectedModel);
    }

    /**
     * Executes redo for the given {@code command} and confirms that the {@code model} matches {@code expectedModel}.
     *
     * @param command is the command that you want to perform redo
     * @param model is the actual model
     * @param expectedModel is the expected model to compare with
     */
    public static void assertRedoCommandSuccess(Undoable command, Model model, Model expectedModel) {
        command.redo(model);
        assertEquals(model, expectedModel);
    }

}
