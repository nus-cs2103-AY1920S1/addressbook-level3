package seedu.address.transaction.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TransactionBuilder.DEFAULT_AMOUNT;
import static seedu.address.testutil.TransactionBuilder.DEFAULT_CATEGORY;
import static seedu.address.testutil.TransactionBuilder.DEFAULT_DATE;
import static seedu.address.testutil.TransactionBuilder.DEFAULT_DESCRIPTION;
import static seedu.address.util.CliSyntax.PREFIX_AMOUNT;
import static seedu.address.util.CliSyntax.PREFIX_CATEGORY;
import static seedu.address.util.CliSyntax.PREFIX_DATETIME;
import static seedu.address.util.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.util.CliSyntax.PREFIX_PERSON;

import java.util.Arrays;

import seedu.address.person.model.CheckAndGetPersonByNameModel;
import seedu.address.transaction.model.Model;
import seedu.address.transaction.model.TransactionList;
import seedu.address.transaction.model.transaction.TransactionContainsKeywordsPredicate;
import seedu.address.util.CommandResult;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {
    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String VALID_NAME_ALICE = "Alice Pauline";
    public static final String VALID_NAME_BENSEN = "Benson Meier";
    public static final String VALID_NAME_AMY = "Amy Bee";
    public static final String VALID_DATE = "03-Sep-2019";
    public static final String VALID_AMOUNT = "20";
    public static final String VALID_DESC = "DeScRiPtIoN @12345";
    public static final String VALID_CATEGORY = "cAtEgOrY @12345";

    public static final String DESC_NAME_ALICE = " " + PREFIX_PERSON + VALID_NAME_ALICE;
    public static final String DESC_NAME_BENSEN = " " + PREFIX_PERSON + VALID_NAME_BENSEN;
    public static final String DESC_NAME_AMY = " " + PREFIX_PERSON + VALID_NAME_AMY;
    public static final String DESC_DATE = " " + PREFIX_DATETIME + VALID_DATE;
    public static final String DESC_AMOUNT = " " + PREFIX_AMOUNT + VALID_AMOUNT;
    public static final String DESC_DESC = " " + PREFIX_DESCRIPTION + VALID_DESC;
    public static final String DESC_CATEGORY = " " + PREFIX_CATEGORY + VALID_CATEGORY;

    public static final String DESC_BUILDER_DATE = " " + PREFIX_DATETIME + DEFAULT_DATE;
    public static final String DESC_BUILDER_AMOUNT = " " + PREFIX_AMOUNT + DEFAULT_AMOUNT;
    public static final String DESC_BUILDER_DESC = " " + PREFIX_DESCRIPTION + DEFAULT_DESCRIPTION;
    public static final String DESC_BUILDER_CATEGORY = " " + PREFIX_CATEGORY + DEFAULT_CATEGORY;

    public static final String INVALID_DATE_1 = " " + PREFIX_DATETIME + "03-sep-2019";
    public static final String INVALID_DATE_2 = " " + PREFIX_DATETIME + "3-Sep-2019";
    public static final String INVALID_DATE_3 = " " + PREFIX_DATETIME + "3/Sep/2019";
    public static final String INVALID_AMOUNT = " " + PREFIX_AMOUNT + "hi";

    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";


    /**
     * Executes the given {@code command}, confirms that <br>
     * - the returned {@link seedu.address.util.CommandResult} matches {@code expectedCommandResult} <br>
     * - the {@code actualModel} matches {@code expectedModel}
     */
    public static void assertCommandSuccess(Command command, Model transactionModel,
                                            CommandResult expectedCommandResult,
                                            Model expectedModel,
                                            CheckAndGetPersonByNameModel personModel) {
        try {
            CommandResult result = command.execute(transactionModel, personModel);
            System.out.println("inside test util:" + expectedCommandResult.getFeedbackToUser());
            System.out.println(result.getFeedbackToUser());
            assertEquals(expectedCommandResult, result);
            System.out.println("did first assert equals");
            assertEquals(expectedModel, transactionModel);
            //consider checking the filtered list
        } catch (Exception ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

    /**
     * Convenience wrapper to
     * {@link #assertCommandSuccess(Command, Model, CommandResult, Model,
     * CheckAndGetPersonByNameModel)}
     * that takes a string {@code expectedMessage}.
     */
    public static void assertCommandSuccess(Command command, Model transactionModel,
                                            String expectedMessage,
                                            Model expectedModel,
                                            CheckAndGetPersonByNameModel personModel) {
        CommandResult expectedCommandResult = new CommandResult(expectedMessage);
        assertCommandSuccess(command, transactionModel, expectedCommandResult, expectedModel, personModel);
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - a {@code CommandException} is thrown <br>
     * - the CommandException message matches {@code expectedMessage} <br>
     * - the transaction list, filtered transaction list in {@code actualModel} remain unchanged
     */
    public static void assertCommandFailure(Command command, Model actualModel, String expectedMessage,
                                            CheckAndGetPersonByNameModel personModel) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        TransactionList transactionList = actualModel.getTransactionList();
        TransactionList expectedFilteredList = actualModel.getFilteredList();

        assertThrows(Exception.class, expectedMessage, () -> command.execute(actualModel, personModel));
        assertEquals(transactionList, actualModel.getTransactionList());
        assertEquals(expectedFilteredList, actualModel.getFilteredList());
    }

    /**
     * Updates {@code model}'s filtered list to show only the person with the given name in the
     * {@code model}'s transaction list
     */
    public static void showTransactionsOfPerson(Model model, String name) {
        assertTrue(model.hasTransactionWithName(name));
        final String[] splitName = name.split("\\s+");
        model.updatePredicate(new TransactionContainsKeywordsPredicate(Arrays.asList(splitName[0])));
        //to apply the predicate on the filtered list
        model.getFilteredList();
    }
}
