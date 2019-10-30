package dukecooks.logic.parser.mealplan;

import static dukecooks.logic.commands.CommandTestUtil.DAY2_DESC_FISH_MP;
import static dukecooks.logic.commands.CommandTestUtil.DAY3_DESC_FISH_MP;
import static dukecooks.logic.commands.CommandTestUtil.DAY4_DESC_FISH_MP;
import static dukecooks.logic.commands.CommandTestUtil.DAY6_DESC_FISH_MP;
import static dukecooks.logic.commands.CommandTestUtil.DAY7_DESC_FISH_MP;
import static dukecooks.logic.commands.CommandTestUtil.NAME_DESC_FISH_MP;
import static dukecooks.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static dukecooks.testutil.mealplan.TypicalMealPlans.BURGER_MP;
import static dukecooks.testutil.mealplan.TypicalMealPlans.FISH_MP;

import org.junit.jupiter.api.Test;

import dukecooks.commons.core.Messages;
import dukecooks.logic.commands.CommandTestUtil;
import dukecooks.logic.commands.mealplan.AddMealPlanCommand;
import dukecooks.logic.parser.CommandParserTestUtil;
import dukecooks.logic.parser.exceptions.ParseException;
import dukecooks.model.mealplan.components.MealPlan;
import dukecooks.model.mealplan.components.MealPlanName;
import dukecooks.model.recipe.components.RecipeName;
import dukecooks.testutil.mealplan.MealPlanBuilder;

public class AddMealPlanCommandParserTest {
    private AddMealPlanCommandParser parser = new AddMealPlanCommandParser();

    @Test
    public void parse_allFieldsPresent_success() throws ParseException {
        MealPlan expectedMealPlan = new MealPlanBuilder(BURGER_MP).build();

        // whitespace only preamble
        assertParseSuccess(parser, CommandTestUtil.PREAMBLE_WHITESPACE
                + CommandTestUtil.NAME_DESC_BURGER_MP + CommandTestUtil.DAY1_DESC_BURGER_MP
                + CommandTestUtil.DAY2_DESC_BURGER_MP + CommandTestUtil.DAY3_DESC_BURGER_MP
                + CommandTestUtil.DAY4_DESC_BURGER_MP + CommandTestUtil.DAY5_DESC_BURGER_MP
                + CommandTestUtil.DAY6_DESC_BURGER_MP + CommandTestUtil.DAY7_DESC_BURGER_MP,
                new AddMealPlanCommand(expectedMealPlan));

        // multiple names - last name accepted
        assertParseSuccess(parser, CommandTestUtil.NAME_DESC_FISH
                + CommandTestUtil.NAME_DESC_BURGER_MP + CommandTestUtil.DAY1_DESC_BURGER_MP
                + CommandTestUtil.DAY2_DESC_BURGER_MP + CommandTestUtil.DAY3_DESC_BURGER_MP
                + CommandTestUtil.DAY4_DESC_BURGER_MP + CommandTestUtil.DAY5_DESC_BURGER_MP
                + CommandTestUtil.DAY6_DESC_BURGER_MP + CommandTestUtil.DAY7_DESC_BURGER_MP,
                new AddMealPlanCommand(expectedMealPlan));

        // multiple recipe names - all accepted
        MealPlan expectedMealPlanMultipleIngredients = new MealPlanBuilder(BURGER_MP)
                .withDay1(CommandTestUtil.VALID_NAME_BURGER, CommandTestUtil.VALID_NAME_FISH)
                .build();
        assertParseSuccess(parser, CommandTestUtil.NAME_DESC_BURGER_MP
                + CommandTestUtil.DAY1_DESC_BURGER_MP + CommandTestUtil.DAY2_DESC_BURGER_MP
                + CommandTestUtil.DAY3_DESC_BURGER_MP + CommandTestUtil.DAY4_DESC_BURGER_MP
                + CommandTestUtil.DAY5_DESC_BURGER_MP + CommandTestUtil.DAY6_DESC_BURGER_MP
                + CommandTestUtil.DAY7_DESC_BURGER_MP + CommandTestUtil.DAY1_DESC_FISH_MP,
                new AddMealPlanCommand(expectedMealPlanMultipleIngredients));

        expectedMealPlanMultipleIngredients = new MealPlanBuilder(BURGER_MP)
                .withDay2(CommandTestUtil.VALID_NAME_BURGER, CommandTestUtil.VALID_NAME_FISH).build();
        assertParseSuccess(parser, CommandTestUtil.NAME_DESC_BURGER_MP
                + CommandTestUtil.DAY1_DESC_BURGER_MP + CommandTestUtil.DAY2_DESC_BURGER_MP
                + CommandTestUtil.DAY3_DESC_BURGER_MP + CommandTestUtil.DAY4_DESC_BURGER_MP
                + CommandTestUtil.DAY5_DESC_BURGER_MP + CommandTestUtil.DAY6_DESC_BURGER_MP
                + CommandTestUtil.DAY7_DESC_BURGER_MP + CommandTestUtil.DAY2_DESC_FISH_MP,
                new AddMealPlanCommand(expectedMealPlanMultipleIngredients));

        expectedMealPlanMultipleIngredients = new MealPlanBuilder(BURGER_MP)
                .withDay3(CommandTestUtil.VALID_NAME_BURGER, CommandTestUtil.VALID_NAME_FISH)
                .build();
        assertParseSuccess(parser, CommandTestUtil.NAME_DESC_BURGER_MP
                + CommandTestUtil.DAY1_DESC_BURGER_MP + CommandTestUtil.DAY2_DESC_BURGER_MP
                + CommandTestUtil.DAY3_DESC_BURGER_MP + CommandTestUtil.DAY4_DESC_BURGER_MP
                + CommandTestUtil.DAY5_DESC_BURGER_MP + CommandTestUtil.DAY6_DESC_BURGER_MP
                + CommandTestUtil.DAY7_DESC_BURGER_MP + CommandTestUtil.DAY3_DESC_FISH_MP,
                new AddMealPlanCommand(expectedMealPlanMultipleIngredients));

        expectedMealPlanMultipleIngredients = new MealPlanBuilder(BURGER_MP)
                .withDay4(CommandTestUtil.VALID_NAME_BURGER, CommandTestUtil.VALID_NAME_FISH).build();
        assertParseSuccess(parser, CommandTestUtil.NAME_DESC_BURGER_MP
                + CommandTestUtil.DAY1_DESC_BURGER_MP + CommandTestUtil.DAY2_DESC_BURGER_MP
                + CommandTestUtil.DAY3_DESC_BURGER_MP + CommandTestUtil.DAY4_DESC_BURGER_MP
                + CommandTestUtil.DAY5_DESC_BURGER_MP + CommandTestUtil.DAY6_DESC_BURGER_MP
                + CommandTestUtil.DAY7_DESC_BURGER_MP + CommandTestUtil.DAY4_DESC_FISH_MP,
                new AddMealPlanCommand(expectedMealPlanMultipleIngredients));

        expectedMealPlanMultipleIngredients = new MealPlanBuilder(BURGER_MP)
                .withDay5(CommandTestUtil.VALID_NAME_BURGER, CommandTestUtil.VALID_NAME_FISH).build();
        assertParseSuccess(parser, CommandTestUtil.NAME_DESC_BURGER_MP
                + CommandTestUtil.DAY1_DESC_BURGER_MP + CommandTestUtil.DAY2_DESC_BURGER_MP
                + CommandTestUtil.DAY3_DESC_BURGER_MP + CommandTestUtil.DAY4_DESC_BURGER_MP
                + CommandTestUtil.DAY5_DESC_BURGER_MP + CommandTestUtil.DAY6_DESC_BURGER_MP
                + CommandTestUtil.DAY7_DESC_BURGER_MP + CommandTestUtil.DAY5_DESC_FISH_MP,
                new AddMealPlanCommand(expectedMealPlanMultipleIngredients));

        expectedMealPlanMultipleIngredients = new MealPlanBuilder(BURGER_MP)
                .withDay6(CommandTestUtil.VALID_NAME_BURGER, CommandTestUtil.VALID_NAME_FISH).build();
        assertParseSuccess(parser, CommandTestUtil.NAME_DESC_BURGER_MP
                + CommandTestUtil.DAY1_DESC_BURGER_MP + CommandTestUtil.DAY2_DESC_BURGER_MP
                + CommandTestUtil.DAY3_DESC_BURGER_MP + CommandTestUtil.DAY4_DESC_BURGER_MP
                + CommandTestUtil.DAY5_DESC_BURGER_MP + CommandTestUtil.DAY6_DESC_BURGER_MP
                + CommandTestUtil.DAY7_DESC_BURGER_MP + CommandTestUtil.DAY6_DESC_FISH_MP,
                new AddMealPlanCommand(expectedMealPlanMultipleIngredients));

        expectedMealPlanMultipleIngredients = new MealPlanBuilder(BURGER_MP)
                .withDay7(CommandTestUtil.VALID_NAME_BURGER, CommandTestUtil.VALID_NAME_FISH).build();
        assertParseSuccess(parser, CommandTestUtil.NAME_DESC_BURGER_MP
                + CommandTestUtil.DAY1_DESC_BURGER_MP + CommandTestUtil.DAY2_DESC_BURGER_MP
                + CommandTestUtil.DAY3_DESC_BURGER_MP + CommandTestUtil.DAY4_DESC_BURGER_MP
                + CommandTestUtil.DAY5_DESC_BURGER_MP + CommandTestUtil.DAY6_DESC_BURGER_MP
                + CommandTestUtil.DAY7_DESC_BURGER_MP + CommandTestUtil.DAY7_DESC_FISH_MP,
                new AddMealPlanCommand(expectedMealPlanMultipleIngredients));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero day 1s
        MealPlan expectedMealPlan = new MealPlanBuilder(FISH_MP).withDay1().build();
        assertParseSuccess(parser, NAME_DESC_FISH_MP + DAY2_DESC_FISH_MP + DAY3_DESC_FISH_MP
                        + DAY4_DESC_FISH_MP + DAY6_DESC_FISH_MP + DAY7_DESC_FISH_MP,
                new AddMealPlanCommand(expectedMealPlan));
        // zero day 2s
        expectedMealPlan = new MealPlanBuilder(FISH_MP).withDay2().build();
        assertParseSuccess(parser, NAME_DESC_FISH_MP,
                new AddMealPlanCommand(expectedMealPlan));
        // zero day 3s
        expectedMealPlan = new MealPlanBuilder(FISH_MP).withDay3().build();
        assertParseSuccess(parser, NAME_DESC_FISH_MP,
                new AddMealPlanCommand(expectedMealPlan));
        // zero day 4s
        expectedMealPlan = new MealPlanBuilder(FISH_MP).withDay4().build();
        assertParseSuccess(parser, NAME_DESC_FISH_MP,
                new AddMealPlanCommand(expectedMealPlan));
        // zero day 5s
        expectedMealPlan = new MealPlanBuilder(FISH_MP).withDay5().build();
        assertParseSuccess(parser, NAME_DESC_FISH_MP,
                new AddMealPlanCommand(expectedMealPlan));
        // zero day 6s
        expectedMealPlan = new MealPlanBuilder(FISH_MP).withDay6().build();
        assertParseSuccess(parser, NAME_DESC_FISH_MP,
                new AddMealPlanCommand(expectedMealPlan));
        // zero day 7s
        expectedMealPlan = new MealPlanBuilder(FISH_MP).withDay7().build();
        assertParseSuccess(parser, NAME_DESC_FISH_MP,
                new AddMealPlanCommand(expectedMealPlan));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                AddMealPlanCommand.MESSAGE_USAGE);

        // missing name prefix
        CommandParserTestUtil.assertParseFailure(parser, CommandTestUtil.VALID_NAME_BURGER_MP,
                expectedMessage);

        // all prefixes missing
        CommandParserTestUtil.assertParseFailure(parser, CommandTestUtil.VALID_NAME_BURGER_MP,
                expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        CommandParserTestUtil.assertParseFailure(parser, CommandTestUtil.INVALID_MEALPLAN_NAME_DESC
                + CommandTestUtil.DAY1_DESC_BURGER_MP + CommandTestUtil.DAY2_DESC_BURGER_MP
                + CommandTestUtil.DAY3_DESC_BURGER_MP + CommandTestUtil.DAY4_DESC_BURGER_MP
                + CommandTestUtil.DAY5_DESC_BURGER_MP + CommandTestUtil.DAY6_DESC_BURGER_MP
                + CommandTestUtil.DAY7_DESC_BURGER_MP, MealPlanName.MESSAGE_CONSTRAINTS);

        // invalid ingredient
        CommandParserTestUtil.assertParseFailure(parser, CommandTestUtil.NAME_DESC_BURGER_MP
                + CommandTestUtil.INVALID_FOOD_NAME_DESC + CommandTestUtil.DAY2_DESC_BURGER_MP
                + CommandTestUtil.DAY3_DESC_BURGER_MP + CommandTestUtil.DAY4_DESC_BURGER_MP
                + CommandTestUtil.DAY5_DESC_BURGER_MP + CommandTestUtil.DAY6_DESC_BURGER_MP
                + CommandTestUtil.DAY7_DESC_BURGER_MP , RecipeName.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        CommandParserTestUtil.assertParseFailure(parser, CommandTestUtil.INVALID_MEALPLAN_NAME_DESC
                + CommandTestUtil.INVALID_FOOD_NAME_DESC + CommandTestUtil.DAY2_DESC_BURGER_MP
                + CommandTestUtil.DAY3_DESC_BURGER_MP + CommandTestUtil.DAY4_DESC_BURGER_MP
                + CommandTestUtil.DAY5_DESC_BURGER_MP + CommandTestUtil.DAY6_DESC_BURGER_MP
                + CommandTestUtil.DAY7_DESC_BURGER_MP , MealPlanName.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        CommandParserTestUtil.assertParseFailure(parser, CommandTestUtil.PREAMBLE_NON_EMPTY
                + CommandTestUtil.DAY1_DESC_BURGER_MP + CommandTestUtil.DAY2_DESC_BURGER_MP
                + CommandTestUtil.DAY3_DESC_BURGER_MP + CommandTestUtil.DAY4_DESC_BURGER_MP
                + CommandTestUtil.DAY5_DESC_BURGER_MP + CommandTestUtil.DAY6_DESC_BURGER_MP
                + CommandTestUtil.DAY7_DESC_BURGER_MP,
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, AddMealPlanCommand.MESSAGE_USAGE));
    }
}
