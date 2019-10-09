package seedu.savenus.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.savenus.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.savenus.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.savenus.logic.parser.CliSyntax.PREFIX_PRICE;
import static seedu.savenus.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.savenus.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.savenus.commons.core.index.Index;
import seedu.savenus.logic.commands.exceptions.CommandException;
import seedu.savenus.model.Menu;
import seedu.savenus.model.Model;
import seedu.savenus.model.food.Food;
import seedu.savenus.model.food.NameContainsKeywordsPredicate;
import seedu.savenus.testutil.EditFoodDescriptorBuilder;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    public static final String VALID_NAME_AMY = "Amy Bee";
    public static final String VALID_NAME_BOB = "Bob Choo";
    public static final String VALID_PRICE_AMY = "11111111";
    public static final String VALID_PRICE_BOB = "22222222";
    public static final String VALID_DESCRIPTION_AMY = "Amy's Description.";
    public static final String VALID_DESCRIPTION_BOB = "Bob's Description.";
    public static final String VALID_TAG_HUSBAND = "husband";
    public static final String VALID_TAG_FRIEND = "friend";

    public static final String NAME_DESC_AMY = " " + PREFIX_NAME + VALID_NAME_AMY;
    public static final String NAME_DESC_BOB = " " + PREFIX_NAME + VALID_NAME_BOB;
    public static final String PRICE_DESC_AMY = " " + PREFIX_PRICE + VALID_PRICE_AMY;
    public static final String PRICE_DESC_BOB = " " + PREFIX_PRICE + VALID_PRICE_BOB;
    public static final String DESCRIPTION_DESC_AMY = " " + PREFIX_DESCRIPTION + VALID_DESCRIPTION_AMY;
    public static final String DESCRIPTION_DESC_BOB = " " + PREFIX_DESCRIPTION + VALID_DESCRIPTION_BOB;
    public static final String TAG_DESC_FRIEND = " " + PREFIX_TAG + VALID_TAG_FRIEND;
    public static final String TAG_DESC_HUSBAND = " " + PREFIX_TAG + VALID_TAG_HUSBAND;

    public static final String INVALID_NAME_DESC = " " + PREFIX_NAME + "James&"; // '&' not allowed in names
    public static final String INVALID_PRICE_DESC = " " + PREFIX_PRICE + "911a"; // 'a' not allowed in prices
    public static final String INVALID_DESCRIPTION_DESC = " " + PREFIX_DESCRIPTION + "   "; // spaces only not allowed
    public static final String INVALID_TAG_DESC = " " + PREFIX_TAG + "hubby*"; // '*' not allowed in tags

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final EditCommand.EditFoodDescriptor DESC_AMY;
    public static final EditCommand.EditFoodDescriptor DESC_BOB;

    static {
        DESC_AMY = new EditFoodDescriptorBuilder().withName(VALID_NAME_AMY)
                .withPrice(VALID_PRICE_AMY).withDescription(VALID_DESCRIPTION_AMY)
                .withTags(VALID_TAG_FRIEND).build();
        DESC_BOB = new EditFoodDescriptorBuilder().withName(VALID_NAME_BOB)
                .withPrice(VALID_PRICE_BOB).withDescription(VALID_DESCRIPTION_BOB)
                .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND).build();
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
     * - the $aveNUS Menu, filtered food list and selected food in {@code actualModel} remain unchanged
     */
    public static void assertCommandFailure(Command command, Model actualModel, String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        Menu expectedMenu = new Menu(actualModel.getMenu());
        List<Food> expectedFilteredList = new ArrayList<>(actualModel.getFilteredFoodList());

        assertThrows(CommandException.class, expectedMessage, () -> command.execute(actualModel));
        assertEquals(expectedMenu, actualModel.getMenu());
        assertEquals(expectedFilteredList, actualModel.getFilteredFoodList());
    }
    /**
     * Updates {@code model}'s filtered list to show only the food at the given {@code targetIndex} in the
     * {@code model}'s menu.
     */
    public static void showFoodAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredFoodList().size());

        Food food = model.getFilteredFoodList().get(targetIndex.getZeroBased());
        final String[] splitName = food.getName().fullName.split("\\s+");
        model.updateFilteredFoodList(new NameContainsKeywordsPredicate(Arrays.asList(splitName[0])));

        assertEquals(1, model.getFilteredFoodList().size());
    }

}
