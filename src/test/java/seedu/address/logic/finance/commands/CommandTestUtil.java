package seedu.address.logic.finance.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.finance.parser.FinanceCliSyntax.PREFIX_AMOUNT;
import static seedu.address.logic.finance.parser.FinanceCliSyntax.PREFIX_CATEGORY;
import static seedu.address.logic.finance.parser.FinanceCliSyntax.PREFIX_DAY;
import static seedu.address.logic.finance.parser.FinanceCliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.finance.parser.FinanceCliSyntax.PREFIX_FROM;
import static seedu.address.logic.finance.parser.FinanceCliSyntax.PREFIX_PLACE;
import static seedu.address.logic.finance.parser.FinanceCliSyntax.PREFIX_TO;
import static seedu.address.logic.finance.parser.FinanceCliSyntax.PREFIX_TRANSACTION_METHOD;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.commons.core.index.Index;

import seedu.address.logic.finance.commands.exceptions.CommandException;
import seedu.address.model.finance.FinanceLog;
import seedu.address.model.finance.Model;
import seedu.address.model.finance.logentry.LogEntry;
import seedu.address.model.finance.logentry.LogEntryContainsKeywordsPredicate;
import seedu.address.testutil.EditLogEntryDescriptorBuilder;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    public static final String VALID_AMOUNT_LOG1 = "2.80";
    public static final String VALID_TDATE_LOG1 = "10-10-2019";
    public static final String VALID_DESC_LOG1 = "Yong Tau Foo";
    public static final String VALID_TMET_LOG1 = "cash";
    public static final String VALID_PLACE_LOG1 = "Frontier";
    public static final String VALID_FROM_LOG1 = "YTF Auntie";
    public static final String VALID_TO_LOG1 = "Me";
    public static final String VALID_CAT_FOOD = "food";
    public static final String VALID_CAT_PRESENT = "present";

    public static final String VALID_AMOUNT_LOG2 = "50";
    public static final String VALID_TDATE_LOG2 = "15-10-2019";
    public static final String VALID_DESC_LOG2 = "Dian Xiao Er";
    public static final String VALID_TMET_LOG2 = "NETS";
    public static final String VALID_PLACE_LOG2 = "Bishan";
    public static final String VALID_FROM_LOG2 = "Me";
    public static final String VALID_TO_LOG2 = "Mother";

    public static final String AMOUNT_DESC_LOG1 = " " + PREFIX_AMOUNT + VALID_AMOUNT_LOG1;
    public static final String TDATE_DESC_LOG1 = " " + PREFIX_DAY + VALID_TDATE_LOG1;
    public static final String DESC_DESC_LOG1 = " " + PREFIX_DESCRIPTION + VALID_DESC_LOG1;
    public static final String TMET_DESC_LOG1 = " " + PREFIX_TRANSACTION_METHOD + VALID_TMET_LOG1;
    public static final String PLACE_DESC_LOG1 = " " + PREFIX_PLACE + VALID_PLACE_LOG1;
    public static final String FROM_DESC_LOG1 = " " + PREFIX_FROM + VALID_FROM_LOG1;
    public static final String TO_DESC_LOG1 = " " + PREFIX_TO + VALID_TO_LOG1;
    public static final String CAT_DESC_FOOD = " " + PREFIX_CATEGORY + VALID_CAT_FOOD;
    public static final String CAT_DESC_PRESENT = " " + PREFIX_CATEGORY + VALID_CAT_PRESENT;

    public static final String AMOUNT_DESC_LOG2 = " " + PREFIX_AMOUNT + VALID_AMOUNT_LOG2;
    public static final String TDATE_DESC_LOG2 = " " + PREFIX_DAY + VALID_TDATE_LOG2;
    public static final String DESC_DESC_LOG2 = " " + PREFIX_DESCRIPTION + VALID_DESC_LOG2;
    public static final String TMET_DESC_LOG2 = " " + PREFIX_TRANSACTION_METHOD + VALID_TMET_LOG2;
    public static final String PLACE_DESC_LOG2 = " " + PREFIX_PLACE + VALID_PLACE_LOG2;
    public static final String FROM_DESC_LOG2 = " " + PREFIX_FROM + VALID_FROM_LOG2;
    public static final String TO_DESC_LOG2 = " " + PREFIX_TO + VALID_TO_LOG2;

    public static final String INVALID_AMOUNT_DESC = " " + PREFIX_AMOUNT + "James&";
    public static final String INVALID_TDATE_DESC = " " + PREFIX_DAY + "100";
    public static final String INVALID_DESC_DESC = " " + PREFIX_DESCRIPTION + " ";
    public static final String INVALID_TMET_DESC = " " + PREFIX_TRANSACTION_METHOD + "123";
    public static final String INVALID_CAT_DESC = " " + PREFIX_CATEGORY + "cat food";
    public static final String INVALID_PLACE_DESC = " " + PREFIX_PLACE + " ";
    public static final String INVALID_FROM_DESC = " " + PREFIX_FROM + "***";
    public static final String INVALID_TO_DESC = " " + PREFIX_TO + "-_-";

    public static final String PREAMBLE_WHITESPACE = "<amt>  <met>  <cat>";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final EditCommand.EditLogEntryDescriptor DESC_LOG1;
    public static final EditCommand.EditLogEntryDescriptor DESC_LOG2;

    static {
        DESC_LOG1 = new EditLogEntryDescriptorBuilder().withAmount(VALID_AMOUNT_LOG1)
                .withTDate(VALID_TDATE_LOG1)
                .withDescription(VALID_DESC_LOG1)
                .withTransactionMethod(VALID_TMET_LOG1)
                .withCats(VALID_CAT_FOOD)
                .withPlace(VALID_PLACE_LOG1)
                .withFrom(VALID_FROM_LOG1)
                .withTo(VALID_TO_LOG1).build();
        DESC_LOG2 = new EditLogEntryDescriptorBuilder().withAmount(VALID_AMOUNT_LOG2)
                .withTDate(VALID_TDATE_LOG2)
                .withDescription(VALID_DESC_LOG2)
                .withTransactionMethod(VALID_TMET_LOG2)
                .withCats(VALID_CAT_FOOD, VALID_CAT_PRESENT)
                .withPlace(VALID_PLACE_LOG2)
                .withFrom(VALID_FROM_LOG2)
                .withTo(VALID_TO_LOG2).build();
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - the returned {@link CommandResult} matches {@code expectedCommandResult} <br>
     * - the {@code actualModel} matches {@code expectedModel}
     */
    public static void assertCommandSuccess(Command command, Model actualModel,
                                            CommandResult expectedCommandResult, Model expectedModel) {
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
     * - the finance log, filtered log entry list and selected log entry in {@code actualModel} remain unchanged
     */
    public static void assertCommandFailure(Command command, Model actualModel, String expectedMessage) {
        // we are unable to defensively copy the calendarModel for comparison later, so we can
        // only do so by copying its components.
        FinanceLog expectedFinanceLog =
            new FinanceLog(actualModel.getFinanceLog());
        List<LogEntry> expectedFilteredList = new ArrayList<>(actualModel.getFilteredLogEntryList());

        assertThrows(CommandException.class, expectedMessage, () -> command.execute(actualModel));
        assertEquals(expectedFinanceLog, actualModel.getFinanceLog());
        assertEquals(expectedFilteredList, actualModel.getFilteredLogEntryList());
    }
    /**
     * Updates {@code Model}'s filtered list to show only the log entry at the given {@code targetIndex} in the
     * {@code Model}'s finance log.
     */
    public static void showLogEntryAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredLogEntryList().size());

        LogEntry log = model.getFilteredLogEntryList().get(targetIndex.getZeroBased());
        final String[] splitName = log.getDescription().value.split("\\s+");
        model.updateFilteredLogEntryList(new LogEntryContainsKeywordsPredicate(Arrays.asList(splitName[0])));

        assertEquals(1, model.getFilteredLogEntryList().size());
    }

}
