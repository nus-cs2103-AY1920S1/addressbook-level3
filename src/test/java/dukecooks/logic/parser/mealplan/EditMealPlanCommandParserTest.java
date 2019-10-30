package dukecooks.logic.parser.mealplan;

import static dukecooks.testutil.mealplan.TypicalMealPlans.FISH_MP;

import org.junit.jupiter.api.Test;

import dukecooks.commons.core.Messages;
import dukecooks.commons.core.index.Index;
import dukecooks.logic.commands.CommandTestUtil;
import dukecooks.logic.commands.mealplan.EditMealPlanCommand;
import dukecooks.logic.commands.recipe.EditRecipeCommand;
import dukecooks.logic.parser.CliSyntax;
import dukecooks.logic.parser.CommandParserTestUtil;
import dukecooks.model.mealplan.components.MealPlanName;
import dukecooks.model.recipe.components.RecipeName;
import dukecooks.testutil.TypicalIndexes;
import dukecooks.testutil.mealplan.EditMealPlanDescriptorBuilder;

public class EditMealPlanCommandParserTest {

    private static final String DAY1_EMPTY = " " + CliSyntax.PREFIX_DAY1;
    private static final String DAY2_EMPTY = " " + CliSyntax.PREFIX_DAY2;
    private static final String DAY3_EMPTY = " " + CliSyntax.PREFIX_DAY3;
    private static final String DAY4_EMPTY = " " + CliSyntax.PREFIX_DAY4;
    private static final String DAY5_EMPTY = " " + CliSyntax.PREFIX_DAY5;
    private static final String DAY6_EMPTY = " " + CliSyntax.PREFIX_DAY6;
    private static final String DAY7_EMPTY = " " + CliSyntax.PREFIX_DAY7;

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, EditMealPlanCommand.MESSAGE_USAGE);

    private EditMealPlanCommandParser parser = new EditMealPlanCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        CommandParserTestUtil.assertParseFailure(parser, CommandTestUtil.VALID_NAME_FISH_MP, MESSAGE_INVALID_FORMAT);

        // no field specified
        CommandParserTestUtil.assertParseFailure(parser, "1", EditRecipeCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        CommandParserTestUtil.assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        CommandParserTestUtil.assertParseFailure(parser, "-5" + CommandTestUtil.NAME_DESC_FISH_MP,
                MESSAGE_INVALID_FORMAT);

        // zero index
        CommandParserTestUtil.assertParseFailure(parser, "0" + CommandTestUtil.NAME_DESC_FISH_MP,
                MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        CommandParserTestUtil.assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        CommandParserTestUtil.assertParseFailure(parser, "1 zxc/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        CommandParserTestUtil.assertParseFailure(parser, "1" + CommandTestUtil.INVALID_MEALPLAN_NAME_DESC,
                MealPlanName.MESSAGE_CONSTRAINTS); // invalid name
        CommandParserTestUtil.assertParseFailure(parser, "1" + CommandTestUtil.INVALID_FOOD_NAME_DESC,
                RecipeName.MESSAGE_CONSTRAINTS); // invalid ingredient

        // while parsing {@code PREFIX_DAY1} alone will result in invalid recipe names of the {@code MealPlan}
        // being edited, parsing it together with a valid ingredient results in error
        CommandParserTestUtil.assertParseFailure(parser, "1" + CommandTestUtil.DAY1_DESC_BURGER_MP
                + CommandTestUtil.DAY1_DESC_FISH_MP + DAY1_EMPTY, RecipeName.MESSAGE_CONSTRAINTS);
        CommandParserTestUtil.assertParseFailure(parser, "1" + CommandTestUtil.DAY1_DESC_FISH_MP
                + DAY1_EMPTY + CommandTestUtil.DAY1_DESC_BURGER_MP, RecipeName.MESSAGE_CONSTRAINTS);
        CommandParserTestUtil.assertParseFailure(parser, "1" + DAY1_EMPTY
                + CommandTestUtil.DAY1_DESC_FISH_MP + CommandTestUtil.DAY1_DESC_BURGER_MP,
                RecipeName.MESSAGE_CONSTRAINTS);

        CommandParserTestUtil.assertParseFailure(parser, "2" + CommandTestUtil.DAY2_DESC_BURGER_MP
                + CommandTestUtil.DAY2_DESC_FISH_MP + DAY2_EMPTY, RecipeName.MESSAGE_CONSTRAINTS);
        CommandParserTestUtil.assertParseFailure(parser, "2" + CommandTestUtil.DAY2_DESC_FISH_MP
                + DAY2_EMPTY + CommandTestUtil.DAY2_DESC_BURGER_MP, RecipeName.MESSAGE_CONSTRAINTS);
        CommandParserTestUtil.assertParseFailure(parser, "2" + DAY2_EMPTY
                        + CommandTestUtil.DAY2_DESC_FISH_MP + CommandTestUtil.DAY2_DESC_BURGER_MP,
                RecipeName.MESSAGE_CONSTRAINTS);

        CommandParserTestUtil.assertParseFailure(parser, "3" + CommandTestUtil.DAY3_DESC_BURGER_MP
                + CommandTestUtil.DAY3_DESC_FISH_MP + DAY3_EMPTY, RecipeName.MESSAGE_CONSTRAINTS);
        CommandParserTestUtil.assertParseFailure(parser, "3" + CommandTestUtil.DAY3_DESC_FISH_MP
                + DAY3_EMPTY + CommandTestUtil.DAY3_DESC_BURGER_MP, RecipeName.MESSAGE_CONSTRAINTS);
        CommandParserTestUtil.assertParseFailure(parser, "3" + DAY3_EMPTY
                        + CommandTestUtil.DAY3_DESC_FISH_MP + CommandTestUtil.DAY3_DESC_BURGER_MP,
                RecipeName.MESSAGE_CONSTRAINTS);

        CommandParserTestUtil.assertParseFailure(parser, "4" + CommandTestUtil.DAY4_DESC_BURGER_MP
                + CommandTestUtil.DAY4_DESC_FISH_MP + DAY4_EMPTY, RecipeName.MESSAGE_CONSTRAINTS);
        CommandParserTestUtil.assertParseFailure(parser, "4" + CommandTestUtil.DAY4_DESC_FISH_MP
                + DAY4_EMPTY + CommandTestUtil.DAY4_DESC_BURGER_MP, RecipeName.MESSAGE_CONSTRAINTS);
        CommandParserTestUtil.assertParseFailure(parser, "4" + DAY4_EMPTY
                        + CommandTestUtil.DAY4_DESC_FISH_MP + CommandTestUtil.DAY4_DESC_BURGER_MP,
                RecipeName.MESSAGE_CONSTRAINTS);

        CommandParserTestUtil.assertParseFailure(parser, "5" + CommandTestUtil.DAY5_DESC_BURGER_MP
                + CommandTestUtil.DAY5_DESC_FISH_MP + DAY5_EMPTY, RecipeName.MESSAGE_CONSTRAINTS);
        CommandParserTestUtil.assertParseFailure(parser, "5" + CommandTestUtil.DAY5_DESC_FISH_MP
                + DAY5_EMPTY + CommandTestUtil.DAY5_DESC_BURGER_MP, RecipeName.MESSAGE_CONSTRAINTS);
        CommandParserTestUtil.assertParseFailure(parser, "5" + DAY5_EMPTY
                        + CommandTestUtil.DAY5_DESC_FISH_MP + CommandTestUtil.DAY5_DESC_BURGER_MP,
                RecipeName.MESSAGE_CONSTRAINTS);

        CommandParserTestUtil.assertParseFailure(parser, "6" + CommandTestUtil.DAY6_DESC_BURGER_MP
                + CommandTestUtil.DAY6_DESC_FISH_MP + DAY6_EMPTY, RecipeName.MESSAGE_CONSTRAINTS);
        CommandParserTestUtil.assertParseFailure(parser, "6" + CommandTestUtil.DAY6_DESC_FISH_MP
                + DAY6_EMPTY + CommandTestUtil.DAY6_DESC_BURGER_MP, RecipeName.MESSAGE_CONSTRAINTS);
        CommandParserTestUtil.assertParseFailure(parser, "6" + DAY6_EMPTY
                        + CommandTestUtil.DAY6_DESC_FISH_MP + CommandTestUtil.DAY6_DESC_BURGER_MP,
                RecipeName.MESSAGE_CONSTRAINTS);

        CommandParserTestUtil.assertParseFailure(parser, "7" + CommandTestUtil.DAY7_DESC_BURGER_MP
                + CommandTestUtil.DAY7_DESC_FISH_MP + DAY7_EMPTY, RecipeName.MESSAGE_CONSTRAINTS);
        CommandParserTestUtil.assertParseFailure(parser, "7" + CommandTestUtil.DAY7_DESC_FISH_MP
                + DAY7_EMPTY + CommandTestUtil.DAY7_DESC_BURGER_MP, RecipeName.MESSAGE_CONSTRAINTS);
        CommandParserTestUtil.assertParseFailure(parser, "7" + DAY7_EMPTY
                        + CommandTestUtil.DAY7_DESC_FISH_MP + CommandTestUtil.DAY7_DESC_BURGER_MP,
                RecipeName.MESSAGE_CONSTRAINTS);

        // multiple invalid values, but only the first invalid value is captured
        CommandParserTestUtil.assertParseFailure(parser, "1" + CommandTestUtil.INVALID_FOOD_NAME_DESC
                + CommandTestUtil.INVALID_MEALPLAN_NAME_DESC, RecipeName.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = TypicalIndexes.INDEX_SECOND_MEALPLAN;
        String userInput = targetIndex.getOneBased() + CommandTestUtil.DAY1_DESC_BURGER_MP
                + CommandTestUtil.NAME_DESC_BURGER_MP;
        EditMealPlanCommand.EditMealPlanDescriptor descriptor = new EditMealPlanDescriptorBuilder(FISH_MP)
                .withMealPlanName(CommandTestUtil.VALID_NAME_BURGER_MP)
                .withDay1ToAdd(CommandTestUtil.VALID_NAME_BURGER, CommandTestUtil.VALID_NAME_FISH)
                .build();
        EditMealPlanCommand expectedCommand = new EditMealPlanCommand(targetIndex, descriptor);
        CommandParserTestUtil.assertParseSuccess(parser, userInput, expectedCommand);

        userInput = targetIndex.getOneBased() + CommandTestUtil.DAY2_DESC_BURGER_MP
                + CommandTestUtil.NAME_DESC_BURGER_MP;
        descriptor = new EditMealPlanDescriptorBuilder(FISH_MP)
                .withMealPlanName(CommandTestUtil.VALID_NAME_BURGER_MP)
                .withDay2ToAdd(CommandTestUtil.VALID_NAME_BURGER)
                .build();
        expectedCommand = new EditMealPlanCommand(targetIndex, descriptor);
        CommandParserTestUtil.assertParseSuccess(parser, userInput, expectedCommand);

        userInput = targetIndex.getOneBased() + CommandTestUtil.DAY3_DESC_BURGER_MP
                + CommandTestUtil.NAME_DESC_BURGER_MP;
        descriptor = new EditMealPlanDescriptorBuilder(FISH_MP)
                .withMealPlanName(CommandTestUtil.VALID_NAME_BURGER_MP)
                .withDay3ToAdd(CommandTestUtil.VALID_NAME_BURGER)
                .build();
        expectedCommand = new EditMealPlanCommand(targetIndex, descriptor);
        CommandParserTestUtil.assertParseSuccess(parser, userInput, expectedCommand);

        userInput = targetIndex.getOneBased() + CommandTestUtil.DAY4_DESC_BURGER_MP
                + CommandTestUtil.NAME_DESC_BURGER_MP;
        descriptor = new EditMealPlanDescriptorBuilder(FISH_MP)
                .withMealPlanName(CommandTestUtil.VALID_NAME_BURGER_MP)
                .withDay4ToAdd(CommandTestUtil.VALID_NAME_BURGER)
                .build();
        expectedCommand = new EditMealPlanCommand(targetIndex, descriptor);
        CommandParserTestUtil.assertParseSuccess(parser, userInput, expectedCommand);

        userInput = targetIndex.getOneBased() + CommandTestUtil.DAY5_DESC_BURGER_MP
                + CommandTestUtil.NAME_DESC_BURGER_MP;
        descriptor = new EditMealPlanDescriptorBuilder(FISH_MP)
                .withMealPlanName(CommandTestUtil.VALID_NAME_BURGER_MP)
                .withDay5ToAdd(CommandTestUtil.VALID_NAME_BURGER)
                .build();
        expectedCommand = new EditMealPlanCommand(targetIndex, descriptor);
        CommandParserTestUtil.assertParseSuccess(parser, userInput, expectedCommand);

        userInput = targetIndex.getOneBased() + CommandTestUtil.DAY6_DESC_BURGER_MP
                + CommandTestUtil.NAME_DESC_BURGER_MP;
        descriptor = new EditMealPlanDescriptorBuilder(FISH_MP)
                .withMealPlanName(CommandTestUtil.VALID_NAME_BURGER_MP)
                .withDay6ToAdd(CommandTestUtil.VALID_NAME_BURGER)
                .build();
        expectedCommand = new EditMealPlanCommand(targetIndex, descriptor);
        CommandParserTestUtil.assertParseSuccess(parser, userInput, expectedCommand);

        userInput = targetIndex.getOneBased() + CommandTestUtil.DAY7_DESC_BURGER_MP
                + CommandTestUtil.NAME_DESC_BURGER_MP;
        descriptor = new EditMealPlanDescriptorBuilder(FISH_MP)
                .withMealPlanName(CommandTestUtil.VALID_NAME_BURGER_MP)
                .withDay7ToAdd(CommandTestUtil.VALID_NAME_BURGER)
                .build();
        expectedCommand = new EditMealPlanCommand(targetIndex, descriptor);
        CommandParserTestUtil.assertParseSuccess(parser, userInput, expectedCommand);
    }


    @Test
    public void parse_oneFieldSpecified_success() {
        // name
        Index targetIndex = TypicalIndexes.INDEX_THIRD_MEALPLAN;
        String userInput = targetIndex.getOneBased() + CommandTestUtil.NAME_DESC_FISH_MP;
        EditMealPlanCommand.EditMealPlanDescriptor descriptor = new EditMealPlanDescriptorBuilder()
                .withMealPlanName(CommandTestUtil.VALID_NAME_FISH_MP).build();
        EditMealPlanCommand expectedCommand = new EditMealPlanCommand(targetIndex, descriptor);
        CommandParserTestUtil.assertParseSuccess(parser, userInput, expectedCommand);

        // add day1
        userInput = targetIndex.getOneBased() + CommandTestUtil.DAY1_DESC_FISH_MP;
        descriptor = new EditMealPlanDescriptorBuilder().withDay1ToAdd(CommandTestUtil.VALID_INGREDIENT_FISH)
                .build();
        expectedCommand = new EditMealPlanCommand(targetIndex, descriptor);
        CommandParserTestUtil.assertParseSuccess(parser, userInput, expectedCommand);

        // remove day1
        userInput = targetIndex.getOneBased() + CommandTestUtil.REMOVEDAY1_DESC_FISH_MP;
        descriptor = new EditMealPlanDescriptorBuilder().withDay1ToRemove(CommandTestUtil.VALID_INGREDIENT_FISH)
                .build();
        expectedCommand = new EditMealPlanCommand(targetIndex, descriptor);
        CommandParserTestUtil.assertParseSuccess(parser, userInput, expectedCommand);

        // add day2
        userInput = targetIndex.getOneBased() + CommandTestUtil.DAY2_DESC_FISH_MP;
        descriptor = new EditMealPlanDescriptorBuilder().withDay2ToAdd(CommandTestUtil.VALID_INGREDIENT_FISH)
                .build();
        expectedCommand = new EditMealPlanCommand(targetIndex, descriptor);
        CommandParserTestUtil.assertParseSuccess(parser, userInput, expectedCommand);

        // remove day2
        userInput = targetIndex.getOneBased() + CommandTestUtil.REMOVEDAY2_DESC_FISH_MP;
        descriptor = new EditMealPlanDescriptorBuilder().withDay2ToRemove(CommandTestUtil.VALID_INGREDIENT_FISH)
                .build();
        expectedCommand = new EditMealPlanCommand(targetIndex, descriptor);
        CommandParserTestUtil.assertParseSuccess(parser, userInput, expectedCommand);

        // add day3
        userInput = targetIndex.getOneBased() + CommandTestUtil.DAY3_DESC_FISH_MP;
        descriptor = new EditMealPlanDescriptorBuilder().withDay3ToAdd(CommandTestUtil.VALID_INGREDIENT_FISH)
                .build();
        expectedCommand = new EditMealPlanCommand(targetIndex, descriptor);
        CommandParserTestUtil.assertParseSuccess(parser, userInput, expectedCommand);

        // remove day3
        userInput = targetIndex.getOneBased() + CommandTestUtil.REMOVEDAY3_DESC_FISH_MP;
        descriptor = new EditMealPlanDescriptorBuilder().withDay3ToRemove(CommandTestUtil.VALID_INGREDIENT_FISH)
                .build();
        expectedCommand = new EditMealPlanCommand(targetIndex, descriptor);
        CommandParserTestUtil.assertParseSuccess(parser, userInput, expectedCommand);

        // add day4
        userInput = targetIndex.getOneBased() + CommandTestUtil.DAY4_DESC_FISH_MP;
        descriptor = new EditMealPlanDescriptorBuilder().withDay4ToAdd(CommandTestUtil.VALID_INGREDIENT_FISH)
                .build();
        expectedCommand = new EditMealPlanCommand(targetIndex, descriptor);
        CommandParserTestUtil.assertParseSuccess(parser, userInput, expectedCommand);

        // remove day4
        userInput = targetIndex.getOneBased() + CommandTestUtil.REMOVEDAY4_DESC_FISH_MP;
        descriptor = new EditMealPlanDescriptorBuilder().withDay4ToRemove(CommandTestUtil.VALID_INGREDIENT_FISH)
                .build();
        expectedCommand = new EditMealPlanCommand(targetIndex, descriptor);
        CommandParserTestUtil.assertParseSuccess(parser, userInput, expectedCommand);

        // add day5
        userInput = targetIndex.getOneBased() + CommandTestUtil.DAY5_DESC_FISH_MP;
        descriptor = new EditMealPlanDescriptorBuilder().withDay5ToAdd(CommandTestUtil.VALID_INGREDIENT_FISH)
                .build();
        expectedCommand = new EditMealPlanCommand(targetIndex, descriptor);
        CommandParserTestUtil.assertParseSuccess(parser, userInput, expectedCommand);

        // remove day5
        userInput = targetIndex.getOneBased() + CommandTestUtil.REMOVEDAY5_DESC_FISH_MP;
        descriptor = new EditMealPlanDescriptorBuilder().withDay5ToRemove(CommandTestUtil.VALID_INGREDIENT_FISH)
                .build();
        expectedCommand = new EditMealPlanCommand(targetIndex, descriptor);
        CommandParserTestUtil.assertParseSuccess(parser, userInput, expectedCommand);

        // add day6
        userInput = targetIndex.getOneBased() + CommandTestUtil.DAY6_DESC_FISH_MP;
        descriptor = new EditMealPlanDescriptorBuilder().withDay6ToAdd(CommandTestUtil.VALID_INGREDIENT_FISH)
                .build();
        expectedCommand = new EditMealPlanCommand(targetIndex, descriptor);
        CommandParserTestUtil.assertParseSuccess(parser, userInput, expectedCommand);

        // remove day6
        userInput = targetIndex.getOneBased() + CommandTestUtil.REMOVEDAY6_DESC_FISH_MP;
        descriptor = new EditMealPlanDescriptorBuilder().withDay6ToRemove(CommandTestUtil.VALID_INGREDIENT_FISH)
                .build();
        expectedCommand = new EditMealPlanCommand(targetIndex, descriptor);
        CommandParserTestUtil.assertParseSuccess(parser, userInput, expectedCommand);

        // add day7
        userInput = targetIndex.getOneBased() + CommandTestUtil.DAY7_DESC_FISH_MP;
        descriptor = new EditMealPlanDescriptorBuilder().withDay7ToAdd(CommandTestUtil.VALID_INGREDIENT_FISH)
                .build();
        expectedCommand = new EditMealPlanCommand(targetIndex, descriptor);
        CommandParserTestUtil.assertParseSuccess(parser, userInput, expectedCommand);

        // remove day7
        userInput = targetIndex.getOneBased() + CommandTestUtil.REMOVEDAY7_DESC_FISH_MP;
        descriptor = new EditMealPlanDescriptorBuilder().withDay7ToRemove(CommandTestUtil.VALID_INGREDIENT_FISH)
                .build();
        expectedCommand = new EditMealPlanCommand(targetIndex, descriptor);
        CommandParserTestUtil.assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_acceptsLast() {
        Index targetIndex = TypicalIndexes.INDEX_FIRST_MEALPLAN;
        String userInput = targetIndex.getOneBased()
                + CommandTestUtil.DAY1_DESC_FISH_MP + CommandTestUtil.DAY1_DESC_BURGER_MP;

        EditMealPlanCommand.EditMealPlanDescriptor descriptor = new EditMealPlanDescriptorBuilder()
                .withDay1ToAdd(CommandTestUtil.VALID_NAME_FISH, CommandTestUtil.VALID_NAME_BURGER).build();
        EditMealPlanCommand expectedCommand = new EditMealPlanCommand(targetIndex, descriptor);
        CommandParserTestUtil.assertParseSuccess(parser, userInput, expectedCommand);

        userInput = targetIndex.getOneBased()
                + CommandTestUtil.DAY2_DESC_FISH_MP + CommandTestUtil.DAY2_DESC_BURGER_MP;
        descriptor = new EditMealPlanDescriptorBuilder()
                .withDay2ToAdd(CommandTestUtil.VALID_NAME_FISH, CommandTestUtil.VALID_NAME_BURGER).build();
        expectedCommand = new EditMealPlanCommand(targetIndex, descriptor);
        CommandParserTestUtil.assertParseSuccess(parser, userInput, expectedCommand);

        userInput = targetIndex.getOneBased()
                + CommandTestUtil.DAY3_DESC_FISH_MP + CommandTestUtil.DAY3_DESC_BURGER_MP;
        descriptor = new EditMealPlanDescriptorBuilder()
                .withDay3ToAdd(CommandTestUtil.VALID_NAME_FISH, CommandTestUtil.VALID_NAME_BURGER).build();
        expectedCommand = new EditMealPlanCommand(targetIndex, descriptor);
        CommandParserTestUtil.assertParseSuccess(parser, userInput, expectedCommand);

        userInput = targetIndex.getOneBased()
                + CommandTestUtil.DAY4_DESC_FISH_MP + CommandTestUtil.DAY4_DESC_BURGER_MP;
        descriptor = new EditMealPlanDescriptorBuilder()
                .withDay4ToAdd(CommandTestUtil.VALID_NAME_FISH, CommandTestUtil.VALID_NAME_BURGER).build();
        expectedCommand = new EditMealPlanCommand(targetIndex, descriptor);
        CommandParserTestUtil.assertParseSuccess(parser, userInput, expectedCommand);

        userInput = targetIndex.getOneBased()
                + CommandTestUtil.DAY5_DESC_FISH_MP + CommandTestUtil.DAY5_DESC_BURGER_MP;
        descriptor = new EditMealPlanDescriptorBuilder()
                .withDay5ToAdd(CommandTestUtil.VALID_NAME_FISH, CommandTestUtil.VALID_NAME_BURGER).build();
        expectedCommand = new EditMealPlanCommand(targetIndex, descriptor);
        CommandParserTestUtil.assertParseSuccess(parser, userInput, expectedCommand);

        userInput = targetIndex.getOneBased()
                + CommandTestUtil.DAY6_DESC_FISH_MP + CommandTestUtil.DAY6_DESC_BURGER_MP;
        descriptor = new EditMealPlanDescriptorBuilder()
                .withDay6ToAdd(CommandTestUtil.VALID_NAME_FISH, CommandTestUtil.VALID_NAME_BURGER).build();
        expectedCommand = new EditMealPlanCommand(targetIndex, descriptor);
        CommandParserTestUtil.assertParseSuccess(parser, userInput, expectedCommand);

        userInput = targetIndex.getOneBased()
                + CommandTestUtil.DAY7_DESC_FISH_MP + CommandTestUtil.DAY7_DESC_BURGER_MP;
        descriptor = new EditMealPlanDescriptorBuilder()
                .withDay7ToAdd(CommandTestUtil.VALID_NAME_FISH, CommandTestUtil.VALID_NAME_BURGER).build();
        expectedCommand = new EditMealPlanCommand(targetIndex, descriptor);
        CommandParserTestUtil.assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_resetDay1_success() {
        Index targetIndex = TypicalIndexes.INDEX_THIRD_MEALPLAN;
        String userInput = targetIndex.getOneBased() + DAY1_EMPTY;

        EditMealPlanCommand.EditMealPlanDescriptor descriptor = new EditMealPlanDescriptorBuilder().withDay1ToAdd()
                .build();
        EditMealPlanCommand expectedCommand = new EditMealPlanCommand(targetIndex, descriptor);

        CommandParserTestUtil.assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_resetDay2_success() {
        Index targetIndex = TypicalIndexes.INDEX_THIRD_MEALPLAN;
        String userInput = targetIndex.getOneBased() + DAY2_EMPTY;

        EditMealPlanCommand.EditMealPlanDescriptor descriptor = new EditMealPlanDescriptorBuilder().withDay2ToAdd()
                .build();
        EditMealPlanCommand expectedCommand = new EditMealPlanCommand(targetIndex, descriptor);

        CommandParserTestUtil.assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_resetDay3_success() {
        Index targetIndex = TypicalIndexes.INDEX_THIRD_MEALPLAN;
        String userInput = targetIndex.getOneBased() + DAY3_EMPTY;

        EditMealPlanCommand.EditMealPlanDescriptor descriptor = new EditMealPlanDescriptorBuilder().withDay3ToAdd()
                .build();
        EditMealPlanCommand expectedCommand = new EditMealPlanCommand(targetIndex, descriptor);

        CommandParserTestUtil.assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_resetDay4_success() {
        Index targetIndex = TypicalIndexes.INDEX_THIRD_MEALPLAN;
        String userInput = targetIndex.getOneBased() + DAY4_EMPTY;

        EditMealPlanCommand.EditMealPlanDescriptor descriptor = new EditMealPlanDescriptorBuilder().withDay4ToAdd()
                .build();
        EditMealPlanCommand expectedCommand = new EditMealPlanCommand(targetIndex, descriptor);

        CommandParserTestUtil.assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_resetDay5_success() {
        Index targetIndex = TypicalIndexes.INDEX_THIRD_MEALPLAN;
        String userInput = targetIndex.getOneBased() + DAY5_EMPTY;

        EditMealPlanCommand.EditMealPlanDescriptor descriptor = new EditMealPlanDescriptorBuilder().withDay5ToAdd()
                .build();
        EditMealPlanCommand expectedCommand = new EditMealPlanCommand(targetIndex, descriptor);

        CommandParserTestUtil.assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_resetDay6_success() {
        Index targetIndex = TypicalIndexes.INDEX_THIRD_MEALPLAN;
        String userInput = targetIndex.getOneBased() + DAY6_EMPTY;

        EditMealPlanCommand.EditMealPlanDescriptor descriptor = new EditMealPlanDescriptorBuilder().withDay6ToAdd()
                .build();
        EditMealPlanCommand expectedCommand = new EditMealPlanCommand(targetIndex, descriptor);

        CommandParserTestUtil.assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_resetDay7_success() {
        Index targetIndex = TypicalIndexes.INDEX_THIRD_MEALPLAN;
        String userInput = targetIndex.getOneBased() + DAY7_EMPTY;

        EditMealPlanCommand.EditMealPlanDescriptor descriptor = new EditMealPlanDescriptorBuilder().withDay7ToAdd()
                .build();
        EditMealPlanCommand expectedCommand = new EditMealPlanCommand(targetIndex, descriptor);

        CommandParserTestUtil.assertParseSuccess(parser, userInput, expectedCommand);
    }
}
