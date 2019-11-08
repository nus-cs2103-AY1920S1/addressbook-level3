package seedu.ichifund.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.ichifund.logic.parser.CliSyntax.PREFIX_AMOUNT;
import static seedu.ichifund.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static seedu.ichifund.logic.parser.CliSyntax.PREFIX_DAY;
import static seedu.ichifund.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.ichifund.logic.parser.CliSyntax.PREFIX_MONTH;
import static seedu.ichifund.logic.parser.CliSyntax.PREFIX_TRANSACTION_TYPE;
import static seedu.ichifund.logic.parser.CliSyntax.PREFIX_YEAR;
import static seedu.ichifund.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.ichifund.commons.core.index.Index;
import seedu.ichifund.logic.commands.exceptions.CommandException;
import seedu.ichifund.logic.commands.transaction.EditTransactionCommand;
import seedu.ichifund.model.FundBook;
import seedu.ichifund.model.Model;
import seedu.ichifund.model.analytics.Data;
import seedu.ichifund.model.budget.Budget;
import seedu.ichifund.model.budget.BudgetDescriptionPredicate;
import seedu.ichifund.model.repeater.Repeater;
import seedu.ichifund.model.transaction.Transaction;
import seedu.ichifund.testutil.EditTransactionDescriptorBuilder;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    public static final String VALID_NAME_AMY = "Amy Bee";
    public static final String VALID_NAME_BOB = "Bob Choo";
    public static final String VALID_PHONE_AMY = "11111111";
    public static final String VALID_PHONE_BOB = "22222222";
    public static final String VALID_EMAIL_AMY = "amy@example.com";
    public static final String VALID_EMAIL_BOB = "bob@example.com";
    public static final String VALID_ADDRESS_AMY = "Block 312, Amy Street 1";
    public static final String VALID_ADDRESS_BOB = "Block 123, Bobby Street 3";
    public static final String VALID_TAG_HUSBAND = "husband";
    public static final String VALID_TAG_FRIEND = "friend";

    public static final String VALID_DESCRIPTION_ALLOWANCE = "Allowance from Daddy";
    public static final String VALID_DESCRIPTION_BUS = "Bus to School";
    public static final String VALID_AMOUNT_ALLOWANCE = "500";
    public static final String VALID_AMOUNT_BUS = "2.10";
    public static final String VALID_CATEGORY_ALLOWANCE = "allowance";
    public static final String VALID_CATEGORY_BUS = "transportation";
    public static final String VALID_DAY_ALLOWANCE = "5";
    public static final String VALID_DAY_BUS = "20";
    public static final String VALID_MONTH_ALLOWANCE = "2";
    public static final String VALID_MONTH_BUS = "7";
    public static final String VALID_YEAR_ALLOWANCE = "2019";
    public static final String VALID_YEAR_BUS = "2018";
    public static final String VALID_TRANSACTION_TYPE_ALLOWANCE = "in";
    public static final String VALID_TRANSACTION_TYPE_BUS = "exp";

    public static final String DESCRIPTION_DESC_ALLOWANCE = " " + PREFIX_DESCRIPTION + VALID_DESCRIPTION_ALLOWANCE;
    public static final String DESCRIPTION_DESC_BUS = " " + PREFIX_DESCRIPTION + VALID_DESCRIPTION_BUS;
    public static final String AMOUNT_DESC_ALLOWANCE = " " + PREFIX_AMOUNT + VALID_AMOUNT_ALLOWANCE;
    public static final String AMOUNT_DESC_BUS = " " + PREFIX_AMOUNT + VALID_AMOUNT_BUS;
    public static final String CATEGORY_DESC_ALLOWANCE = " " + PREFIX_CATEGORY + VALID_CATEGORY_ALLOWANCE;
    public static final String CATEGORY_DESC_BUS = " " + PREFIX_CATEGORY + VALID_CATEGORY_BUS;
    public static final String DAY_DESC_ALLOWANCE = " " + PREFIX_DAY + VALID_DAY_ALLOWANCE;
    public static final String DAY_DESC_BUS = " " + PREFIX_DAY + VALID_DAY_BUS;
    public static final String MONTH_DESC_ALLOWANCE = " " + PREFIX_MONTH + VALID_MONTH_ALLOWANCE;
    public static final String MONTH_DESC_BUS = " " + PREFIX_MONTH + VALID_MONTH_BUS;
    public static final String YEAR_DESC_ALLOWANCE = " " + PREFIX_YEAR + VALID_YEAR_ALLOWANCE;
    public static final String YEAR_DESC_BUS = " " + PREFIX_YEAR + VALID_YEAR_BUS;
    public static final String TRANSACTION_TYPE_DESC_ALLOWANCE = " " + PREFIX_TRANSACTION_TYPE
            + VALID_TRANSACTION_TYPE_ALLOWANCE;
    public static final String TRANSACTION_TYPE_DESC_BUS = " " + PREFIX_TRANSACTION_TYPE
            + VALID_TRANSACTION_TYPE_BUS;

    public static final String INVALID_DESCRIPTION_DESC = " " + PREFIX_DESCRIPTION + "!?";
    public static final String INVALID_AMOUNT_DESC = " " + PREFIX_AMOUNT + "0.0";
    public static final String INVALID_CATEGORY_DESC = " " + PREFIX_CATEGORY + "?!";
    public static final String INVALID_DAY_DESC = " " + PREFIX_DAY + "32";
    public static final String INVALID_MONTH_DESC = " " + PREFIX_MONTH + "13";
    public static final String INVALID_YEAR_DESC = " " + PREFIX_YEAR + "999";
    public static final String INVALID_TRANSACTION_TYPE_DESC = " " + PREFIX_TRANSACTION_TYPE + "hi";

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final EditTransactionCommand.EditTransactionDescriptor DESC_ALLOWANCE;
    public static final EditTransactionCommand.EditTransactionDescriptor DESC_BUS;

    static {
        DESC_ALLOWANCE = new EditTransactionDescriptorBuilder().withDescription(VALID_DESCRIPTION_ALLOWANCE)
                .withAmount(VALID_AMOUNT_ALLOWANCE).withDay(VALID_DAY_ALLOWANCE).withMonth(VALID_MONTH_ALLOWANCE)
                .withYear(VALID_YEAR_ALLOWANCE).withCategory(VALID_CATEGORY_ALLOWANCE)
                .withTransactionType(VALID_TRANSACTION_TYPE_ALLOWANCE).build();
        DESC_BUS = new EditTransactionDescriptorBuilder().withDescription(VALID_DESCRIPTION_BUS)
                .withAmount(VALID_AMOUNT_BUS).withDay(VALID_DAY_BUS).withMonth(VALID_MONTH_BUS)
                .withYear(VALID_YEAR_BUS).withCategory(VALID_CATEGORY_BUS)
                .withTransactionType(VALID_TRANSACTION_TYPE_BUS).build();
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
     * - the address book, filtered person list and selected person in {@code actualModel} remain unchanged
     */
    public static void assertCommandFailure(Command command, Model actualModel, String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        FundBook expectedFundBook = new FundBook(actualModel.getFundBook());
        List<Transaction> expectedFilteredTransactionList = new ArrayList<>(actualModel.getFilteredTransactionList());
        List<Budget> expectedFilteredBudgetList = new ArrayList<>(actualModel.getFilteredBudgetList());
        List<Repeater> expectedFilteredRepeaterList = new ArrayList<>(actualModel.getFilteredRepeaterList());
        List<Data> expectedDataList = new ArrayList<>(actualModel.getDataList());

        assertThrows(CommandException.class, expectedMessage, () -> command.execute(actualModel));
        assertEquals(expectedFundBook, actualModel.getFundBook());
        assertEquals(expectedFilteredTransactionList, actualModel.getFilteredTransactionList());
        assertEquals(expectedFilteredRepeaterList, actualModel.getFilteredRepeaterList());
        assertEquals(expectedFilteredBudgetList, actualModel.getFilteredBudgetList());
        assertEquals(expectedDataList, actualModel.getDataList());
    }

    /**
     * Updates {@code model}'s filtered list to show only the budget at the given {@code targetIndex} in the
     * {@code model}'s fund book.
     */
    public static void showBudgetAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredBudgetList().size());

        Budget budget = model.getFilteredBudgetList().get(targetIndex.getZeroBased());
        final String[] splitDescription = budget.getDescription().description.split("\\s+");
        model.updateFilteredBudgetList(new BudgetDescriptionPredicate(Arrays.asList(splitDescription[0])));

        assertEquals(1, model.getFilteredBudgetList().size());
    }
}
