package seedu.guilttrip.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.guilttrip.logic.parser.CliSyntax.PREFIX_AMOUNT;
import static seedu.guilttrip.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static seedu.guilttrip.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.guilttrip.logic.parser.CliSyntax.PREFIX_DESC;
import static seedu.guilttrip.logic.parser.CliSyntax.PREFIX_TAG;

import seedu.guilttrip.logic.CommandHistory;
import seedu.guilttrip.logic.commands.editcommands.EditCategoryCommand;
import seedu.guilttrip.logic.commands.editcommands.EditExpenseCommand;
import seedu.guilttrip.logic.commands.exceptions.CommandException;
import seedu.guilttrip.model.Model;
import seedu.guilttrip.testutil.EditCategoryDescriptorBuilder;
import seedu.guilttrip.testutil.EditExpenseDescriptorBuilder;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    //Entry related testUtils.
    public static final String VALID_DESC_FOOD_EXPENSE = "pgp mala";
    public static final String VALID_DESC_CLOTHING_EXPENSE = "cotton on jeans on sale";
    public static final String VALID_AMOUNT_FOOD_EXPENSE = "5.50";
    public static final String VALID_AMOUNT_CLOTHING_EXPENSE = "300";
    public static final String VALID_CATEGORY_FOOD_EXPENSE = "Food";
    public static final String VALID_CATEGORY_CLOTHING_EXPENSE = "Shopping";
    public static final String VALID_DATE_CLOTHING_EXPENSE = "2019-11-09";
    public static final String VALID_DATE_FOOD_EXPENSE = "2019-09-09";
    public static final String VALID_TAG_FOOD = "food";
    public static final String VALID_TAG_CLOTHING_CLOTHES = "clothes";
    public static final String VALID_TAG_CLOTHING_WANT = "want";

    public static final String NAME_DESC_FOOD_EXPENSE = " " + PREFIX_DESC + VALID_DESC_FOOD_EXPENSE;
    public static final String NAME_DESC_CLOTHING_EXPENSE = " " + PREFIX_DESC + VALID_DESC_CLOTHING_EXPENSE;
    public static final String AMOUNT_FOOD_EXPENSE = " " + PREFIX_AMOUNT + VALID_AMOUNT_FOOD_EXPENSE;
    public static final String AMOUNT_CLOTHING_EXPENSE = " " + PREFIX_AMOUNT + VALID_AMOUNT_CLOTHING_EXPENSE;
    public static final String CATEGORY_FOOD_EXPENSE = " " + PREFIX_CATEGORY + VALID_CATEGORY_FOOD_EXPENSE;
    public static final String CATEGORY_CLOTHING_EXPENSE = " " + PREFIX_CATEGORY + VALID_CATEGORY_CLOTHING_EXPENSE;
    public static final String DATE_FOOD_EXPENSE = " " + PREFIX_DATE + VALID_DATE_FOOD_EXPENSE;
    public static final String DATE_CLOTHING_EXPENSE = " " + PREFIX_DATE + VALID_DATE_CLOTHING_EXPENSE;
    public static final String TAG_DESC_FOOD = " " + PREFIX_TAG + VALID_TAG_FOOD;
    public static final String TAG_DESC_CLOTHING = " " + PREFIX_TAG + VALID_TAG_CLOTHING_CLOTHES;
    public static final String TAG_DESC_WANT = " " + PREFIX_TAG + VALID_TAG_CLOTHING_WANT;


    public static final String INVALID_NAME_DESC = " " + PREFIX_DESC + ""; // 'empty' not allowed in names
    public static final String INVALID_TAG_DESC = " " + PREFIX_TAG + "food*"; // '*' not allowed in tags
    public static final String INVALID_AMOUNT = " " + PREFIX_AMOUNT + "0.001"; // '3dp' not allowed in amount
    public static final String INVALID_DATE = " " + PREFIX_DATE + "31-02-2019"; // 'non-existsnt date'

    //Category related Test Utils.
    public static final String VALID_CATEGORY_TYPE_EXPENSE = "Expense";
    public static final String VALID_CATEGORY_TYPE_INCOME = "Income";
    public static final String VALID_CATEGORY_NAME_EXPENSE_FOOD = "food";
    public static final String VALID_CATEGORY_NAME_EXPENSE_SHOPPING = "shopping";
    public static final String VALID_CATEGORY_NAME_INCOME = "Business";

    //Category related Test Utils.
    public static final String CATEGORY_TYPE_EXPENSE = " " + PREFIX_CATEGORY + "Expense";
    public static final String CATEGORY_TYPE_INCOME = " " + PREFIX_CATEGORY + "Income";
    public static final String CATEGORY_NAME_EXPENSE = " " + PREFIX_DESC + "food";
    public static final String CATEGORY_NAME_INCOME = " " + PREFIX_DESC + "business";

    public static final String INVALID_CATEGORY_TYPE_BUDGET = " " + PREFIX_CATEGORY + "Budget";
    public static final String INVALID_CATEGORY_NAME = " " + PREFIX_DESC + "Alcohol";

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final EditExpenseCommand.EditExpenseDescriptor DESC_FOOD_EXPENSE;
    public static final EditExpenseCommand.EditExpenseDescriptor DESC_CLOTHING_EXPENSE;
    public static final EditCategoryCommand.EditCategoryDescriptor DESC_CATEGORY_EXPENSE_FOOD;
    public static final EditCategoryCommand.EditCategoryDescriptor DESC_CATEGORY_INCOME_BUSINESS;

    static {
        DESC_FOOD_EXPENSE = new EditExpenseDescriptorBuilder().withDescription(VALID_DESC_FOOD_EXPENSE)
                .withAmount(VALID_AMOUNT_FOOD_EXPENSE).withCategory(VALID_CATEGORY_NAME_EXPENSE_FOOD)
                .withDate(VALID_DATE_FOOD_EXPENSE).withTags(VALID_TAG_FOOD).build();
        DESC_CLOTHING_EXPENSE = new EditExpenseDescriptorBuilder().withDescription(VALID_DESC_CLOTHING_EXPENSE)
                .withAmount(VALID_AMOUNT_CLOTHING_EXPENSE).withCategory(VALID_CATEGORY_NAME_EXPENSE_SHOPPING)
                .withTags(VALID_TAG_CLOTHING_CLOTHES, VALID_TAG_CLOTHING_WANT).build();
        DESC_CATEGORY_EXPENSE_FOOD = new EditCategoryDescriptorBuilder().withCategoryType(VALID_CATEGORY_TYPE_EXPENSE)
                .withCategoryName(VALID_CATEGORY_NAME_EXPENSE_FOOD).build();
        DESC_CATEGORY_INCOME_BUSINESS = new EditCategoryDescriptorBuilder().withCategoryType(VALID_CATEGORY_TYPE_INCOME)
                .withCategoryName(VALID_CATEGORY_NAME_INCOME).build();
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - the returned {@link CommandResult} matches {@code expectedCommandResult} <br>
     * - the {@code actualModel} matches {@code expectedModel}
     */
    public static void assertCommandSuccess(Command command, Model actualModel, CommandResult expectedCommandResult,
            Model expectedModel, CommandHistory commandHistory) {
        try {
            CommandResult result = command.execute(actualModel, commandHistory);
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
            Model expectedModel, CommandHistory commandHistory) {
        CommandResult expectedCommandResult = new CommandResult(expectedMessage);
        assertCommandSuccess(command, actualModel, expectedCommandResult, expectedModel, commandHistory);
    }

    //    /**
    //     * Executes the given {@code command}, confirms that <br>
    //     * - a {@code CommandException} is thrown <br>
    //     * - the CommandException message matches {@code expectedMessage} <br>
    //     * - the guilttrip book, filtered entry list and selected entry in {@code actualModel} remain unchanged
    //     */
    //    public static void assertCommandFailure(Command command, Model actualModel, String expectedMessage,
    //            CommandHistory commandHistory) {
    //        // we are unable to defensively copy the model for comparison later, so we can
    //        // only do so by copying its components.
    //        GuiltTrip expectedGuiltTrip = new GuiltTrip(actualModel.getAddressBook());
    //        List<Entry> expectedFilteredList = new ArrayList<>(actualModel.getFilteredEntryList());
    //
    //        assertThrows(CommandException.class, expectedMessage, () -> command.execute(actualModel, commandHistory));
    //        assertEquals(expectedGuiltTrip, actualModel.getAddressBook());
    //        assertEquals(expectedFilteredList, actualModel.getFilteredEntryList());
    //    }

}
