package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AMOUNT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.BankAccount;
import seedu.address.model.Model;
import seedu.address.model.category.Category;
import seedu.address.model.transaction.BankAccountOperation;
import seedu.address.model.transaction.TransactionContainsCategoriesPredicate;
//import seedu.address.testutil.UpdateTransactionDescriptorBuilder;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    public static final String VALID_NAME_AMY = "Amy Bee";
    public static final String VALID_NAME_BOB = "Bob Choo";

    public static final String NAME_DESC_AMY = " " + PREFIX_NAME + VALID_NAME_AMY;
    public static final String NAME_DESC_BOB = " " + PREFIX_NAME + VALID_NAME_BOB;

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final String VALID_AMOUNT_ALICE = "100";
    public static final String VALID_DATE_ALICE = "10112019";
    public static final String VALID_DESCRIPTION_ALICE = "milk";
    public static final String VALID_CATEGORY_ALICE = "food";

    public static final String AMOUNT_DESC_ALICE = " " + PREFIX_AMOUNT + VALID_AMOUNT_ALICE;
    public static final String DATE_DESC_ALICE = " " + PREFIX_DATE + VALID_DATE_ALICE;
    public static final String DESCRIPTION_DESC_ALICE = " " + PREFIX_NAME + VALID_DESCRIPTION_ALICE;
    public static final String CATEGORY_DESC_ALICE = " " + PREFIX_CATEGORY + VALID_CATEGORY_ALICE;

    public static final String INVALID_AMOUNT_ZERO_DESC = " " + PREFIX_AMOUNT + "0";
    public static final String INVALID_AMOUNT_RANGE_DESC = " " + PREFIX_AMOUNT + "10000000";
    public static final String INVALID_AMOUNT_TYPE_DESC = " " + PREFIX_AMOUNT + "10.002";
    public static final String INVALID_AMOUNT_OVERFLOW_DESC = " " + PREFIX_AMOUNT + "100000000";
    public static final String INVALID_DATE_DESC = " " + PREFIX_DATE + "10";
    public static final String INVALID_DATETYPE_DESC = " " + PREFIX_DATE + "40402019";
    public static final String INVALID_DATETYPE = "40402019";
    public static final String INVALID_DESCRIPTION_DESC = " " + PREFIX_NAME + "Milk@";
    public static final String INVALID_CATEGORY_DESC = " " + PREFIX_CATEGORY + "Work*";

    // TODO: FIX
    //
    //     public static final UpdateCommand.UpdateTransactionDescriptor DESC_AMY;
    //     public static final UpdateCommand.UpdateTransactionDescriptor DESC_BOB;
    //
    //     static {
    //         DESC_AMY = new UpdateTransactionDescriptorBuilder().withName(VALID_NAME_AMY)
    //             .withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY)
    //             .withCategories(VALID_TAG_FRIEND).build();
    //         DESC_BOB = new UpdateTransactionDescriptorBuilder().withName(VALID_NAME_BOB)
    //             .withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB)
    //             .withCategories(VALID_TAG_HUSBAND, VALID_TAG_FRIEND).build();
    //     }

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
     * - the bank account, filtered transaction list and selected transaction in {@code actualModel} remain unchanged
     */
    public static void assertCommandFailure(Command command, Model actualModel, String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        BankAccount expectedBankAccount = new BankAccount(actualModel.getBankAccount());
        List<BankAccountOperation> expectedFilteredList = new ArrayList<>(actualModel.getFilteredTransactionList());

        assertThrows(CommandException.class, expectedMessage, () -> command.execute(actualModel));
        assertEquals(expectedBankAccount, actualModel.getBankAccount());
        assertEquals(expectedFilteredList, actualModel.getFilteredTransactionList());
    }

    /**
     * Updates {@code model}'s filtered list to show only the transaction at the given {@code targetIndex} in the
     * {@code model}'s bank account.
     */
    public static void showTransactionAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredTransactionList().size());

        BankAccountOperation transaction = model.getFilteredTransactionList().get(targetIndex.getZeroBased());

        // final List<String> categories = transaction
        //     .getCategories()
        //     .stream()
        //     .map(category -> category.getCategoryName())
        //     .collect(Collectors.toList());

        final Optional<Set<Category>> categories = Optional.of(transaction
            .getCategories());
        model.updateFilteredTransactionList(new TransactionContainsCategoriesPredicate(categories,
            Optional.empty(), Optional.empty(), Optional.empty()));

        assertEquals(1, model.getFilteredTransactionList().size());
    }

    /**
     * Deletes the first transaction in {@code model}'s filtered list from {@code model}'s bank account.
     */
    public static void deleteFirstTransaction(Model model) {
        BankAccountOperation firstTransaction = model.getFilteredTransactionList().get(0);
        model.delete(firstTransaction);
        model.commitUserState();
    }

}
