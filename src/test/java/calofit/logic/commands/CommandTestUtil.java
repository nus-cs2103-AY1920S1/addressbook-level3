package calofit.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import calofit.commons.core.index.Index;
import calofit.logic.commands.exceptions.CommandException;
import calofit.logic.parser.CliSyntax;
import calofit.model.Model;
import calofit.model.dish.Dish;
import calofit.model.dish.DishDatabase;
import calofit.model.dish.NameContainsKeywordsPredicate;
import calofit.testutil.Assert;
import calofit.testutil.EditDishDescriptorBuilder;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    public static final String VALID_NAME_DUCK_RICE = "Duck Rice";
    public static final String VALID_NAME_MACARONI = "Macaroni";
    public static final int VALID_CALORIE_1000 = 1000;
    public static final String VALID_TAG_SALTY = "salty";
    public static final String VALID_TAG_EXPENSIVE = "expensive";

    public static final String NAME_DESC_DUCK_RICE = " " + CliSyntax.PREFIX_NAME + VALID_NAME_DUCK_RICE;
    public static final String NAME_DESC_MACARONI = " " + CliSyntax.PREFIX_NAME + VALID_NAME_MACARONI;
    public static final String CALORIE_DESC_1000 = " " + CliSyntax.PREFIX_CALORIES + VALID_CALORIE_1000;
    public static final String TAG_DESC_EXPENSIVE = " " + CliSyntax.PREFIX_TAG + VALID_TAG_EXPENSIVE;
    public static final String TAG_DESC_SALTY = " " + CliSyntax.PREFIX_TAG + VALID_TAG_SALTY;

    public static final String INVALID_NAME_DESC = " "
            + CliSyntax.PREFIX_NAME + "Duck Noodles&"; // '&' not allowed in names
    public static final String INVAID_CALORIES_NEGATIVE = " " + CliSyntax.PREFIX_CALORIES + "-1000";
    public static final String INVALID_TAG_DESC = " " + CliSyntax.PREFIX_TAG + "bland*"; // '*' not allowed in tags

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final EditCommand.EditDishDescriptor DESC_DUCK_RICE;
    public static final EditCommand.EditDishDescriptor DESC_MACARONI;

    static {
        DESC_DUCK_RICE = new EditDishDescriptorBuilder().withName(VALID_NAME_DUCK_RICE)
                .withCalories(VALID_CALORIE_1000)
                .withTags(VALID_TAG_EXPENSIVE).build();
        DESC_MACARONI = new EditDishDescriptorBuilder().withName(VALID_NAME_MACARONI)
                .withCalories(VALID_CALORIE_1000)
                .withTags(VALID_TAG_SALTY, VALID_TAG_EXPENSIVE).build();
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
     * - the dish database, filtered dish list and selected dish in {@code actualModel} remain unchanged
     */
    public static void assertCommandFailure(Command command, Model actualModel, String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        DishDatabase expectedDishDatabase = new DishDatabase(actualModel.getDishDatabase());
        List<Dish> expectedFilteredList = new ArrayList<>(actualModel.getFilteredDishList());

        Assert.assertThrows(CommandException.class, expectedMessage, () -> command.execute(actualModel));
        assertEquals(expectedDishDatabase, actualModel.getDishDatabase());
        assertEquals(expectedFilteredList, actualModel.getFilteredDishList());
    }

    /**
     * Updates {@code model}'s filtered list to show only the dish at the given {@code targetIndex} in the
     * {@code model}'s dish database.
     */
    public static void showDishAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredDishList().size());

        Dish dish = model.getFilteredDishList().get(targetIndex.getZeroBased());
        final String[] splitName = dish.getName().fullName.split("\\s+");
        model.updateFilteredDishList(new NameContainsKeywordsPredicate(Arrays.asList(splitName[0])));

        assertEquals(1, model.getFilteredDishList().size());
    }

}
