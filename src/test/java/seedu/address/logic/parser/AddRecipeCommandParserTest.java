package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.CALORIES_DESC_BURGER;
import static seedu.address.logic.commands.CommandTestUtil.CARBS_DESC_BURGER;
import static seedu.address.logic.commands.CommandTestUtil.FATS_DESC_BURGER;
import static seedu.address.logic.commands.CommandTestUtil.INGREDIENT_DESC_BURGER;
import static seedu.address.logic.commands.CommandTestUtil.INGREDIENT_DESC_FISH;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_FOOD_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_INGREDIENT_DESC;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_BURGER;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_FISH;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.PROTEIN_DESC_BURGER;
import static seedu.address.logic.commands.CommandTestUtil.VALID_INGREDIENT_BURGER;
import static seedu.address.logic.commands.CommandTestUtil.VALID_INGREDIENT_FISH;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BURGER;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalRecipes.BURGER;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AddRecipeCommand;
import seedu.address.model.common.Name;
import seedu.address.model.recipe.Ingredient;
import seedu.address.model.recipe.Recipe;
import seedu.address.testutil.RecipeBuilder;

public class AddRecipeCommandParserTest {
    private AddRecipeCommandParser parser = new AddRecipeCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Recipe expectedRecipe = new RecipeBuilder(BURGER).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + NAME_DESC_BURGER
                 + INGREDIENT_DESC_BURGER + CALORIES_DESC_BURGER + CARBS_DESC_BURGER + FATS_DESC_BURGER
                 + PROTEIN_DESC_BURGER, new AddRecipeCommand(expectedRecipe));

        // multiple names - last name accepted
        assertParseSuccess(parser, NAME_DESC_FISH + NAME_DESC_BURGER
                + INGREDIENT_DESC_BURGER + CALORIES_DESC_BURGER + CARBS_DESC_BURGER + FATS_DESC_BURGER
                + PROTEIN_DESC_BURGER, new AddRecipeCommand(expectedRecipe));

        // multiple ingredients - all accepted
        Recipe expectedRecipeMultipleIngredients = new RecipeBuilder(BURGER)
                .withIngredients(VALID_INGREDIENT_BURGER, VALID_INGREDIENT_FISH)
                .build();
        assertParseSuccess(parser, NAME_DESC_BURGER + INGREDIENT_DESC_BURGER + INGREDIENT_DESC_FISH
                + CALORIES_DESC_BURGER + CARBS_DESC_BURGER + FATS_DESC_BURGER
                + PROTEIN_DESC_BURGER, new AddRecipeCommand(expectedRecipeMultipleIngredients));
    }

    //    Removed this test case as recipe has no optional fields.
    //    @Test
    //    public void parse_optionalFieldsMissing_success() {
    //        // zero ingredientss
    //        Recipe expectedRecipe = new RecipeBuilder(FISH).withIngredients().build();
    //        assertParseSuccess(parser, NAME_DESC_FISH,
    //                new AddRecipeCommand(expectedRecipe));
    //    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddRecipeCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, VALID_NAME_BURGER,
                expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_NAME_BURGER,
                expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, INVALID_FOOD_NAME_DESC + INGREDIENT_DESC_BURGER
                + CALORIES_DESC_BURGER + CARBS_DESC_BURGER + FATS_DESC_BURGER
                + PROTEIN_DESC_BURGER, Name.MESSAGE_CONSTRAINTS);

        // invalid ingredient
        assertParseFailure(parser, NAME_DESC_BURGER + INVALID_INGREDIENT_DESC
                + CALORIES_DESC_BURGER + CARBS_DESC_BURGER + FATS_DESC_BURGER
                + PROTEIN_DESC_BURGER, Ingredient.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_FOOD_NAME_DESC + INVALID_INGREDIENT_DESC
                        + CALORIES_DESC_BURGER + CARBS_DESC_BURGER + FATS_DESC_BURGER
                        + PROTEIN_DESC_BURGER, Name.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + NAME_DESC_BURGER + INVALID_INGREDIENT_DESC
                        + CALORIES_DESC_BURGER + CARBS_DESC_BURGER + FATS_DESC_BURGER + PROTEIN_DESC_BURGER,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddRecipeCommand.MESSAGE_USAGE));
    }
}
