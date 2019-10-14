package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CALORIES;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CARBS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FATS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INGREDIENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PROTEIN;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.DukeCooks;
import seedu.address.model.Model;
import seedu.address.model.recipe.NameContainsKeywordsPredicate;
import seedu.address.model.recipe.Recipe;
import seedu.address.testutil.EditRecipeDescriptorBuilder;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    public static final String VALID_NAME_FISH = "Fish and Chips";
    public static final String VALID_NAME_BURGER = "Cheese Burger";
    public static final String VALID_INGREDIENT_FISH = "Dory Fish";
    public static final String VALID_INGREDIENT_BURGER = "Beef Patty";
    public static final String VALID_CALORIES_FISH = "600";
    public static final String VALID_CALORIES_BURGER = "610";
    public static final String VALID_CARBS_FISH = "59";
    public static final String VALID_CARBS_BURGER = "57";
    public static final String VALID_FATS_FISH = "25";
    public static final String VALID_FATS_BURGER = "31";
    public static final String VALID_PROTEIN_FISH = "35";
    public static final String VALID_PROTEIN_BURGER = "28";

    public static final String NAME_DESC_FISH = " " + PREFIX_NAME + VALID_NAME_FISH;
    public static final String NAME_DESC_BURGER = " " + PREFIX_NAME + VALID_NAME_BURGER;
    public static final String INGREDIENT_DESC_FISH = " " + PREFIX_INGREDIENT + VALID_INGREDIENT_FISH;
    public static final String INGREDIENT_DESC_BURGER = " " + PREFIX_INGREDIENT + VALID_INGREDIENT_BURGER;
    public static final String CALORIES_DESC_FISH = " " + PREFIX_CALORIES + VALID_CALORIES_FISH;
    public static final String CALORIES_DESC_BURGER = " " + PREFIX_CALORIES + VALID_CALORIES_BURGER;
    public static final String CARBS_DESC_FISH = " " + PREFIX_CARBS + VALID_CARBS_FISH;
    public static final String CARBS_DESC_BURGER = " " + PREFIX_CARBS + VALID_CARBS_BURGER;
    public static final String FATS_DESC_FISH = " " + PREFIX_FATS + VALID_FATS_FISH;
    public static final String FATS_DESC_BURGER = " " + PREFIX_FATS + VALID_FATS_BURGER;
    public static final String PROTEIN_DESC_FISH = " " + PREFIX_PROTEIN + VALID_PROTEIN_FISH;
    public static final String PROTEIN_DESC_BURGER = " " + PREFIX_PROTEIN + VALID_PROTEIN_BURGER;

    public static final String INVALID_NAME_DESC = " " + PREFIX_NAME + "Fish & Chips"; // '&' not allowed in names
    public static final String INVALID_INGREDIENT_DESC = " " + PREFIX_INGREDIENT
            + "Cheese*Burger"; // '*' not allowed in ingredient names
    public static final String INVALID_CALORIES_DESC = " " + PREFIX_CALORIES + "1a"; // 'a' not allowed in calories
    public static final String INVALID_CARBS_DESC = " " + PREFIX_CARBS + "1a"; // 'a' not allowed in carbs
    public static final String INVALID_FATS_DESC = " " + PREFIX_FATS + "1a"; // 'a' not allowed in fats
    public static final String INVALID_PROTEIN_DESC = " " + PREFIX_PROTEIN + "1a"; // 'a' not allowed in protein

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final EditRecipeCommand.EditRecipeDescriptor DESC_FISH;
    public static final EditRecipeCommand.EditRecipeDescriptor DESC_BURGER;

    static {
        DESC_FISH = new EditRecipeDescriptorBuilder().withName(VALID_NAME_FISH)
                .withIngredients(VALID_INGREDIENT_FISH)
                .withCalories(VALID_CALORIES_FISH).withCarbs(VALID_CARBS_FISH)
                .withFats(VALID_FATS_FISH).withProtein(VALID_PROTEIN_FISH)
                .build();
        DESC_BURGER = new EditRecipeDescriptorBuilder().withName(VALID_NAME_BURGER)
                .withIngredients(VALID_INGREDIENT_BURGER, VALID_INGREDIENT_FISH)
                .withCalories(VALID_CALORIES_BURGER).withCarbs(VALID_CARBS_BURGER)
                .withFats(VALID_FATS_BURGER).withProtein(VALID_PROTEIN_BURGER)
                .build();
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
     * - Duke Cooks, filtered recipe list and selected recipe in {@code actualModel} remain unchanged
     */
    public static void assertCommandFailure(Command command, Model actualModel, String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        DukeCooks expectedDukeCooks = new DukeCooks(actualModel.getDukeCooks());
        List<Recipe> expectedFilteredList = new ArrayList<>(actualModel.getFilteredRecipeList());

        assertThrows(CommandException.class, expectedMessage, () -> command.execute(actualModel));
        assertEquals(expectedDukeCooks, actualModel.getDukeCooks());
        assertEquals(expectedFilteredList, actualModel.getFilteredRecipeList());
    }
    /**
     * Updates {@code model}'s filtered list to show only the recipe at the given {@code targetIndex} in the
     * {@code model}'s Duke Cooks.
     */
    public static void showRecipeAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredRecipeList().size());

        Recipe recipe = model.getFilteredRecipeList().get(targetIndex.getZeroBased());
        final String[] splitName = recipe.getName().fullName.split("\\s+");
        model.updateFilteredRecipeList(new NameContainsKeywordsPredicate(Arrays.asList(splitName[0])));

        assertEquals(1, model.getFilteredRecipeList().size());
    }

}
