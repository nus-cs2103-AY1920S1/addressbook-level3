package dukecooks.logic.parser.recipe;

import static dukecooks.testutil.recipe.TypicalRecipes.FISH;

import org.junit.jupiter.api.Test;

import dukecooks.commons.core.Messages;
import dukecooks.commons.core.index.Index;
import dukecooks.logic.commands.CommandTestUtil;
import dukecooks.logic.commands.recipe.EditRecipeCommand;
import dukecooks.logic.parser.CliSyntax;
import dukecooks.logic.parser.CommandParserTestUtil;
import dukecooks.model.recipe.components.Ingredient;
import dukecooks.model.recipe.components.RecipeName;
import dukecooks.testutil.TypicalIndexes;
import dukecooks.testutil.recipe.EditRecipeDescriptorBuilder;

public class EditRecipeCommandParserTest {

    private static final String INGREDIENT_EMPTY = " " + CliSyntax.PREFIX_INGREDIENT;

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, EditRecipeCommand.MESSAGE_USAGE);

    private EditRecipeCommandParser parser = new EditRecipeCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        CommandParserTestUtil.assertParseFailure(parser, CommandTestUtil.VALID_NAME_FISH, MESSAGE_INVALID_FORMAT);

        // no field specified
        CommandParserTestUtil.assertParseFailure(parser, "1", EditRecipeCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        CommandParserTestUtil.assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        CommandParserTestUtil.assertParseFailure(parser, "-5" + CommandTestUtil.NAME_DESC_FISH,
                MESSAGE_INVALID_FORMAT);

        // zero index
        CommandParserTestUtil.assertParseFailure(parser, "0" + CommandTestUtil.NAME_DESC_FISH,
                MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        CommandParserTestUtil.assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        CommandParserTestUtil.assertParseFailure(parser, "1 zxc/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        CommandParserTestUtil.assertParseFailure(parser, "1" + CommandTestUtil.INVALID_FOOD_NAME_DESC,
                RecipeName.MESSAGE_CONSTRAINTS); // invalid name
        CommandParserTestUtil.assertParseFailure(parser, "1" + CommandTestUtil.INVALID_INGREDIENT_DESC,
                Ingredient.MESSAGE_CONSTRAINTS); // invalid ingredient

        // while parsing {@code PREFIX_INGREDIENT} alone will reset the ingredients of the {@code Recipe} being edited,
        // parsing it together with a valid ingredient results in error
        CommandParserTestUtil.assertParseFailure(parser, "1" + CommandTestUtil.INGREDIENT_DESC_FISH
                + CommandTestUtil.INGREDIENT_DESC_BURGER + INGREDIENT_EMPTY,
                Ingredient.MESSAGE_CONSTRAINTS);
        CommandParserTestUtil.assertParseFailure(parser, "1" + CommandTestUtil.INGREDIENT_DESC_FISH
                + INGREDIENT_EMPTY + CommandTestUtil.INGREDIENT_DESC_BURGER,
                Ingredient.MESSAGE_CONSTRAINTS);
        CommandParserTestUtil.assertParseFailure(parser, "1" + INGREDIENT_EMPTY
                + CommandTestUtil.INGREDIENT_DESC_FISH + CommandTestUtil.INGREDIENT_DESC_BURGER,
                Ingredient.MESSAGE_CONSTRAINTS);

        // multiple invalid values, but only the first invalid value is captured
        CommandParserTestUtil.assertParseFailure(parser, "1" + CommandTestUtil.INVALID_FOOD_NAME_DESC
                + CommandTestUtil.INVALID_INGREDIENT_DESC, RecipeName.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = TypicalIndexes.INDEX_SECOND_RECIPE;
        String userInput = targetIndex.getOneBased() + CommandTestUtil.INGREDIENT_DESC_BURGER
                 + CommandTestUtil.NAME_DESC_FISH + CommandTestUtil.INGREDIENT_DESC_FISH;

        EditRecipeCommand.EditRecipeDescriptor descriptor = new EditRecipeDescriptorBuilder(FISH)
                .withIngredientsToAdd(CommandTestUtil.VALID_INGREDIENT_BURGER, CommandTestUtil.VALID_INGREDIENT_FISH)
                .build();
        EditRecipeCommand expectedCommand = new EditRecipeCommand(targetIndex, descriptor);

        CommandParserTestUtil.assertParseSuccess(parser, userInput, expectedCommand);
    }


    @Test
    public void parse_oneFieldSpecified_success() {
        // name
        Index targetIndex = TypicalIndexes.INDEX_THIRD_RECIPE;
        String userInput = targetIndex.getOneBased() + CommandTestUtil.NAME_DESC_FISH;
        EditRecipeCommand.EditRecipeDescriptor descriptor = new EditRecipeDescriptorBuilder()
                .withRecipeName(CommandTestUtil.VALID_NAME_FISH).build();
        EditRecipeCommand expectedCommand = new EditRecipeCommand(targetIndex, descriptor);
        CommandParserTestUtil.assertParseSuccess(parser, userInput, expectedCommand);

        // ingredients
        userInput = targetIndex.getOneBased() + CommandTestUtil.INGREDIENT_DESC_FISH;
        descriptor = new EditRecipeDescriptorBuilder().withIngredientsToAdd(CommandTestUtil.VALID_INGREDIENT_FISH)
                .build();
        expectedCommand = new EditRecipeCommand(targetIndex, descriptor);
        CommandParserTestUtil.assertParseSuccess(parser, userInput, expectedCommand);

        // remove ingredients
        userInput = targetIndex.getOneBased() + CommandTestUtil.REMOVEINGREDIENT_DESC_FISH;
        descriptor = new EditRecipeDescriptorBuilder().withIngredientsToRemove(CommandTestUtil.VALID_INGREDIENT_FISH)
                .build();
        expectedCommand = new EditRecipeCommand(targetIndex, descriptor);
        CommandParserTestUtil.assertParseSuccess(parser, userInput, expectedCommand);

        // calories
        userInput = targetIndex.getOneBased() + CommandTestUtil.CALORIES_DESC_FISH;
        descriptor = new EditRecipeDescriptorBuilder().withCalories(CommandTestUtil.VALID_CALORIES_FISH).build();
        expectedCommand = new EditRecipeCommand(targetIndex, descriptor);
        CommandParserTestUtil.assertParseSuccess(parser, userInput, expectedCommand);

        // carbs
        userInput = targetIndex.getOneBased() + CommandTestUtil.CARBS_DESC_FISH;
        descriptor = new EditRecipeDescriptorBuilder().withCarbs(CommandTestUtil.VALID_CARBS_FISH).build();
        expectedCommand = new EditRecipeCommand(targetIndex, descriptor);
        CommandParserTestUtil.assertParseSuccess(parser, userInput, expectedCommand);

        // fats
        userInput = targetIndex.getOneBased() + CommandTestUtil.FATS_DESC_FISH;
        descriptor = new EditRecipeDescriptorBuilder().withFats(CommandTestUtil.VALID_FATS_FISH).build();
        expectedCommand = new EditRecipeCommand(targetIndex, descriptor);
        CommandParserTestUtil.assertParseSuccess(parser, userInput, expectedCommand);

        //protein
        userInput = targetIndex.getOneBased() + CommandTestUtil.PROTEIN_DESC_FISH;
        descriptor = new EditRecipeDescriptorBuilder().withProtein(CommandTestUtil.VALID_PROTEIN_FISH).build();
        expectedCommand = new EditRecipeCommand(targetIndex, descriptor);
        CommandParserTestUtil.assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_acceptsLast() {
        Index targetIndex = TypicalIndexes.INDEX_FIRST_RECIPE;
        String userInput = targetIndex.getOneBased()
                + CommandTestUtil.INGREDIENT_DESC_FISH + CommandTestUtil.INGREDIENT_DESC_FISH
                + CommandTestUtil.INGREDIENT_DESC_BURGER;

        EditRecipeCommand.EditRecipeDescriptor descriptor = new EditRecipeDescriptorBuilder()
                .withIngredientsToAdd(CommandTestUtil.VALID_INGREDIENT_FISH, CommandTestUtil.VALID_INGREDIENT_BURGER)
                .build();
        EditRecipeCommand expectedCommand = new EditRecipeCommand(targetIndex, descriptor);

        CommandParserTestUtil.assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_resetIngredients_success() {
        Index targetIndex = TypicalIndexes.INDEX_THIRD_RECIPE;
        String userInput = targetIndex.getOneBased() + INGREDIENT_EMPTY;

        EditRecipeCommand.EditRecipeDescriptor descriptor = new EditRecipeDescriptorBuilder().withIngredientsToAdd()
                .build();
        EditRecipeCommand expectedCommand = new EditRecipeCommand(targetIndex, descriptor);

        CommandParserTestUtil.assertParseSuccess(parser, userInput, expectedCommand);
    }
}
