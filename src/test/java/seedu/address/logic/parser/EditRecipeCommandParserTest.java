package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.CALORIES_DESC_FISH;
import static seedu.address.logic.commands.CommandTestUtil.CARBS_DESC_FISH;
import static seedu.address.logic.commands.CommandTestUtil.FATS_DESC_FISH;
import static seedu.address.logic.commands.CommandTestUtil.INGREDIENT_DESC_BURGER;
import static seedu.address.logic.commands.CommandTestUtil.INGREDIENT_DESC_FISH;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_INGREDIENT_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_FISH;
import static seedu.address.logic.commands.CommandTestUtil.PROTEIN_DESC_FISH;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CALORIES_FISH;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CARBS_FISH;
import static seedu.address.logic.commands.CommandTestUtil.VALID_FATS_FISH;
import static seedu.address.logic.commands.CommandTestUtil.VALID_INGREDIENT_BURGER;
import static seedu.address.logic.commands.CommandTestUtil.VALID_INGREDIENT_FISH;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_FISH;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PROTEIN_FISH;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INGREDIENT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_RECIPE;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_RECIPE;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_RECIPE;
import static seedu.address.testutil.TypicalRecipes.FISH;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditRecipeCommand;
import seedu.address.logic.commands.EditRecipeCommand.EditRecipeDescriptor;
import seedu.address.model.common.Name;
import seedu.address.model.recipe.Ingredient;
import seedu.address.testutil.EditRecipeDescriptorBuilder;

public class EditRecipeCommandParserTest {

    private static final String INGREDIENT_EMPTY = " " + PREFIX_INGREDIENT;

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditRecipeCommand.MESSAGE_USAGE);

    private EditRecipeCommandParser parser = new EditRecipeCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_NAME_FISH, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", EditRecipeCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + NAME_DESC_FISH, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + NAME_DESC_FISH, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 zxc/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, "1" + INVALID_NAME_DESC, Name.MESSAGE_CONSTRAINTS); // invalid name
        assertParseFailure(parser, "1" + INVALID_INGREDIENT_DESC, Ingredient.MESSAGE_CONSTRAINTS); // invalid ingredient

        // while parsing {@code PREFIX_INGREDIENT} alone will reset the ingredients of the {@code Recipe} being edited,
        // parsing it together with a valid ingredient results in error
        assertParseFailure(parser, "1" + INGREDIENT_DESC_FISH + INGREDIENT_DESC_BURGER + INGREDIENT_EMPTY,
                Ingredient.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "1" + INGREDIENT_DESC_FISH + INGREDIENT_EMPTY + INGREDIENT_DESC_BURGER,
                Ingredient.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "1" + INGREDIENT_EMPTY + INGREDIENT_DESC_FISH + INGREDIENT_DESC_BURGER,
                Ingredient.MESSAGE_CONSTRAINTS);

        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser, "1" + INVALID_NAME_DESC,
                Name.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND_RECIPE;
        String userInput = targetIndex.getOneBased() + INGREDIENT_DESC_BURGER
                 + NAME_DESC_FISH + INGREDIENT_DESC_FISH;

        EditRecipeCommand.EditRecipeDescriptor descriptor = new EditRecipeDescriptorBuilder(FISH)
                .withIngredients(VALID_INGREDIENT_BURGER, VALID_INGREDIENT_FISH).build();
        EditRecipeCommand expectedCommand = new EditRecipeCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }


    @Test
    public void parse_oneFieldSpecified_success() {
        // name
        Index targetIndex = INDEX_THIRD_RECIPE;
        String userInput = targetIndex.getOneBased() + NAME_DESC_FISH;
        EditRecipeDescriptor descriptor = new EditRecipeDescriptorBuilder().withName(VALID_NAME_FISH).build();
        EditRecipeCommand expectedCommand = new EditRecipeCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // ingredients
        userInput = targetIndex.getOneBased() + INGREDIENT_DESC_FISH;
        descriptor = new EditRecipeDescriptorBuilder().withIngredients(VALID_INGREDIENT_FISH).build();
        expectedCommand = new EditRecipeCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // calories
        userInput = targetIndex.getOneBased() + CALORIES_DESC_FISH;
        descriptor = new EditRecipeDescriptorBuilder().withCalories(VALID_CALORIES_FISH).build();
        expectedCommand = new EditRecipeCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // carbs
        userInput = targetIndex.getOneBased() + CARBS_DESC_FISH;
        descriptor = new EditRecipeDescriptorBuilder().withCarbs(VALID_CARBS_FISH).build();
        expectedCommand = new EditRecipeCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // fats
        userInput = targetIndex.getOneBased() + FATS_DESC_FISH;
        descriptor = new EditRecipeDescriptorBuilder().withFats(VALID_FATS_FISH).build();
        expectedCommand = new EditRecipeCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        //protein
        userInput = targetIndex.getOneBased() + PROTEIN_DESC_FISH;
        descriptor = new EditRecipeDescriptorBuilder().withProtein(VALID_PROTEIN_FISH).build();
        expectedCommand = new EditRecipeCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_acceptsLast() {
        Index targetIndex = INDEX_FIRST_RECIPE;
        String userInput = targetIndex.getOneBased()
                + INGREDIENT_DESC_FISH + INGREDIENT_DESC_FISH
                + INGREDIENT_DESC_BURGER;

        EditRecipeDescriptor descriptor = new EditRecipeDescriptorBuilder()
                .withIngredients(VALID_INGREDIENT_FISH, VALID_INGREDIENT_BURGER)
                .build();
        EditRecipeCommand expectedCommand = new EditRecipeCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_resetIngredients_success() {
        Index targetIndex = INDEX_THIRD_RECIPE;
        String userInput = targetIndex.getOneBased() + INGREDIENT_EMPTY;

        EditRecipeCommand.EditRecipeDescriptor descriptor = new EditRecipeDescriptorBuilder().withIngredients().build();
        EditRecipeCommand expectedCommand = new EditRecipeCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
