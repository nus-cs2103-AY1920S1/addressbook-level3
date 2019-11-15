package dukecooks.logic.parser.recipe;

import static dukecooks.testutil.recipe.TypicalRecipes.BURGER;

import org.junit.jupiter.api.Test;

import dukecooks.commons.core.Messages;
import dukecooks.logic.commands.CommandTestUtil;
import dukecooks.logic.commands.recipe.AddRecipeCommand;
import dukecooks.logic.parser.CommandParserTestUtil;
import dukecooks.model.recipe.components.Ingredient;
import dukecooks.model.recipe.components.Recipe;
import dukecooks.model.recipe.components.RecipeName;
import dukecooks.testutil.recipe.RecipeBuilder;

public class AddRecipeCommandParserTest {
    private AddRecipeCommandParser parser = new AddRecipeCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Recipe expectedRecipe = new RecipeBuilder(BURGER).build();

        // whitespace only preamble
        CommandParserTestUtil.assertParseSuccess(parser, CommandTestUtil.PREAMBLE_WHITESPACE
                + CommandTestUtil.NAME_DESC_BURGER
                 + CommandTestUtil.INGREDIENT_DESC_BURGER + CommandTestUtil.CALORIES_DESC_BURGER
                + CommandTestUtil.CARBS_DESC_BURGER + CommandTestUtil.FATS_DESC_BURGER
                 + CommandTestUtil.PROTEIN_DESC_BURGER, new AddRecipeCommand(expectedRecipe));

        // multiple names - last name accepted
        CommandParserTestUtil.assertParseSuccess(parser, CommandTestUtil.NAME_DESC_FISH
                + CommandTestUtil.NAME_DESC_BURGER
                + CommandTestUtil.INGREDIENT_DESC_BURGER + CommandTestUtil.CALORIES_DESC_BURGER
                + CommandTestUtil.CARBS_DESC_BURGER + CommandTestUtil.FATS_DESC_BURGER
                + CommandTestUtil.PROTEIN_DESC_BURGER, new AddRecipeCommand(expectedRecipe));

        // multiple ingredients - all accepted
        Recipe expectedRecipeMultipleIngredients = new RecipeBuilder(BURGER)
                .withIngredients(CommandTestUtil.VALID_INGREDIENT_BURGER, CommandTestUtil.VALID_INGREDIENT_FISH)
                .build();
        CommandParserTestUtil.assertParseSuccess(parser, CommandTestUtil.NAME_DESC_BURGER
                + CommandTestUtil.INGREDIENT_DESC_BURGER + CommandTestUtil.INGREDIENT_DESC_FISH
                + CommandTestUtil.CALORIES_DESC_BURGER + CommandTestUtil.CARBS_DESC_BURGER
                + CommandTestUtil.FATS_DESC_BURGER
                + CommandTestUtil.PROTEIN_DESC_BURGER, new AddRecipeCommand(expectedRecipeMultipleIngredients));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, AddRecipeCommand.MESSAGE_USAGE);

        // missing name prefix
        CommandParserTestUtil.assertParseFailure(parser, CommandTestUtil.VALID_NAME_BURGER,
                expectedMessage);

        // all prefixes missing
        CommandParserTestUtil.assertParseFailure(parser, CommandTestUtil.VALID_NAME_BURGER,
                expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        CommandParserTestUtil.assertParseFailure(parser, CommandTestUtil.INVALID_FOOD_NAME_DESC
                + CommandTestUtil.INGREDIENT_DESC_BURGER
                + CommandTestUtil.CALORIES_DESC_BURGER + CommandTestUtil.CARBS_DESC_BURGER
                + CommandTestUtil.FATS_DESC_BURGER
                + CommandTestUtil.PROTEIN_DESC_BURGER, RecipeName.MESSAGE_CONSTRAINTS);

        // invalid ingredient
        CommandParserTestUtil.assertParseFailure(parser, CommandTestUtil.NAME_DESC_BURGER
                + CommandTestUtil.INVALID_INGREDIENT_DESC
                + CommandTestUtil.CALORIES_DESC_BURGER + CommandTestUtil.CARBS_DESC_BURGER
                + CommandTestUtil.FATS_DESC_BURGER
                + CommandTestUtil.PROTEIN_DESC_BURGER, Ingredient.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        CommandParserTestUtil.assertParseFailure(parser, CommandTestUtil.INVALID_FOOD_NAME_DESC
                + CommandTestUtil.INVALID_INGREDIENT_DESC
                + CommandTestUtil.CALORIES_DESC_BURGER + CommandTestUtil.CARBS_DESC_BURGER
                + CommandTestUtil.FATS_DESC_BURGER
                + CommandTestUtil.PROTEIN_DESC_BURGER, RecipeName.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        CommandParserTestUtil.assertParseFailure(parser, CommandTestUtil.PREAMBLE_NON_EMPTY
                + CommandTestUtil.NAME_DESC_BURGER + CommandTestUtil.INVALID_INGREDIENT_DESC
                + CommandTestUtil.CALORIES_DESC_BURGER + CommandTestUtil.CARBS_DESC_BURGER
                + CommandTestUtil.FATS_DESC_BURGER + CommandTestUtil.PROTEIN_DESC_BURGER,
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, AddRecipeCommand.MESSAGE_USAGE));
    }
}
