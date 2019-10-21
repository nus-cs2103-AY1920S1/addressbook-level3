package seedu.address.cashier.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.ItemBuilder.DEFAULT_CATEGORY;
import static seedu.address.testutil.ItemBuilder.DEFAULT_COST;
import static seedu.address.testutil.ItemBuilder.DEFAULT_DESCRIPTION;
import static seedu.address.testutil.ItemBuilder.DEFAULT_PRICE;
import static seedu.address.testutil.ItemBuilder.DEFAULT_QUANTITY;
import static seedu.address.util.CliSyntax.PREFIX_CATEGORY;
import static seedu.address.util.CliSyntax.PREFIX_COST;
import static seedu.address.util.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.util.CliSyntax.PREFIX_PRICE;
import static seedu.address.util.CliSyntax.PREFIX_QUANTITY;

import java.util.ArrayList;

import seedu.address.cashier.model.Model;
import seedu.address.cashier.util.InventoryList;
import seedu.address.inventory.model.Item;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {
    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String VALID_DESCRIPTION_FISH_BURGER = "burger";
    public static final String VALID_DESCRIPTION_STORYBOOK= "the tale";
    public static final String VALID_CATEGORY = "food";
    public static final String VALID_QUANTITY = "20";
    public static final String VALID_COST = "3.25";
    public static final String VALID_PRICE = "5.23";

    public static final String DESC_DESCRIPTION_FISH_BURGER = " " + PREFIX_DESCRIPTION + VALID_DESCRIPTION_FISH_BURGER;
    public static final String DESC_DESCRIPTION_STORYBOOK = " " + PREFIX_DESCRIPTION + VALID_DESCRIPTION_STORYBOOK;
    //public static final String DESC_NAME_AMY = " " + PREFIX_PERSON + VALID_NAME_AMY;
    public static final String DESC_CATEGORY = " " + PREFIX_CATEGORY + VALID_CATEGORY;
    public static final String DESC_QUANTITY = " " + PREFIX_QUANTITY + VALID_QUANTITY;
    public static final String DESC_COST = " " + PREFIX_COST+ VALID_COST;
    public static final String DESC_PRICE = " " + PREFIX_PRICE + VALID_PRICE;

    public static final String DESC_BUILDER_QUANTITY = " " + PREFIX_QUANTITY + DEFAULT_QUANTITY;
    public static final String DESC_BUILDER_COST = " " + PREFIX_COST + DEFAULT_COST;
    public static final String DESC_BUILDER_PRICE = " " + PREFIX_PRICE + DEFAULT_PRICE;
    public static final String DESC_BUILDER_DESC = " " + PREFIX_DESCRIPTION + DEFAULT_DESCRIPTION;
    public static final String DESC_BUILDER_CATEGORY = " " + PREFIX_CATEGORY + DEFAULT_CATEGORY;

 /*   public static final String INVALID_DATE_1 = " "  + PREFIX_DATETIME + "03-sep-2019";
    public static final String INVALID_DATE_2 = " "  + PREFIX_DATETIME + "3-Sep-2019";
    public static final String INVALID_DATE_3 = " "  + PREFIX_DATETIME + "3/Sep/2019";   */
    public static final String INVALID_QUANTITY = " " + PREFIX_QUANTITY + "hi";

    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    /**
     * Executes the given {@code command}, confirms that <br>
     * - the returned {@link seedu.address.cashier.commands.CommandResult} matches {@code expectedCommandResult} <br>
     * - the {@code actualModel} matches {@code expectedModel}
     */
    public static void assertCommandSuccess(Command command, Model cashierModel,
                                            CommandResult expectedCommandResult,
                                            Model expectedModel, seedu.address.person.model.Model personModel,
                                            seedu.address.transaction.model.Model transactionModel,
                                            seedu.address.inventory.model.Model inventoryModel) {
        try {
            System.out.println("beforee");
            CommandResult result = command.execute(cashierModel, personModel, transactionModel, inventoryModel);
            System.out.println("inside test util:" + expectedCommandResult.getFeedbackToUser());
            System.out.println(result.getFeedbackToUser());
            assertEquals(expectedCommandResult, result);
            System.out.println("did first assert equals");
            assertEquals(expectedModel, cashierModel);
            assertEquals(expectedModel.getSalesList(), cashierModel.getSalesList());
        } catch (Exception ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

    /**
     * Convenience wrapper to
     * {@link #assertCommandSuccess(Command, Model, CommandResult, Model, seedu.address.person.model.Model,
     * seedu.address.transaction.model.Model transactionModel, seedu.address.inventory.model.Model inventoryModel)}
     * that takes a string {@code expectedMessage}.
     */
    public static void assertCommandSuccess(Command command, Model cashierModel,
                                            String expectedMessage,
                                            Model expectedModel, seedu.address.person.model.Model personModel,
                                            seedu.address.transaction.model.Model transactionModel,
                                            seedu.address.inventory.model.Model inventoryModel) {
        CommandResult expectedCommandResult = new CommandResult(expectedMessage);
        assertCommandSuccess(command, cashierModel, expectedCommandResult, expectedModel,
                personModel, transactionModel, inventoryModel);
    }


    /**
     * Executes the given {@code command}, confirms that <br>
     * - a {@code CommandException} is thrown <br>
     * - the CommandException message matches {@code expectedMessage} <br>
     * - the address book, filtered person list and selected person in {@code actualModel} remain unchanged
     */
    public static void assertCommandFailure(Command command, Model actualModel, String expectedMessage,
                                            seedu.address.person.model.Model personModel,
                                            seedu.address.transaction.model.Model transactionModel,
                                            seedu.address.inventory.model.Model inventoryModel) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        ArrayList<Item> salesList = actualModel.getSalesList();
        InventoryList inventoryList = actualModel.getInventoryList();

        assertThrows(Exception.class, expectedMessage, () -> command.execute(actualModel,
                personModel, transactionModel, inventoryModel));
        assertEquals(salesList, actualModel.getSalesList());
        assertEquals(inventoryList, actualModel.getInventoryList());
    }

    /**
     * Updates {@code model}'s filtered list to show only the person with the given name in the
     * {@code model}'s transaction list
     */
/*    public static void showTransactionsOfPerson(Model model, String name) {
        assertTrue(model.hasTransactionWithName(name));
        final String[] splitName = name.split("\\s+");
        model.updatePredicate(new TransactionContainsKeywordsPredicate(Arrays.asList(splitName[0])));
        //to apply the predicate on the filtered list
        model.getFilteredList();
    }   */
}
