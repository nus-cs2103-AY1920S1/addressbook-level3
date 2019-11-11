package dukecooks.logic.parser.mealplan;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import dukecooks.commons.core.Messages;
import dukecooks.logic.commands.mealplan.FindMealPlanWithCommand;
import dukecooks.logic.parser.CommandParserTestUtil;
import dukecooks.model.mealplan.components.MealPlanRecipesContainsKeywordsPredicate;

public class FindMealPlanWithCommandParserTest {

    private FindMealPlanWithCommandParser parser = new FindMealPlanWithCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        CommandParserTestUtil.assertParseFailure(parser, "     ",
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, FindMealPlanWithCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindCommand() {
        // no leading and trailing whitespaces
        FindMealPlanWithCommand expectedFindMealPlanWithCommand =
                new FindMealPlanWithCommand(new MealPlanRecipesContainsKeywordsPredicate(Arrays
                        .asList("Cheese Burger")));
        CommandParserTestUtil.assertParseSuccess(parser, "Cheese Burger", expectedFindMealPlanWithCommand);

        // multiple whitespaces between keywords
        CommandParserTestUtil.assertParseSuccess(parser, " \n Cheese \n \t Burger  \t",
                expectedFindMealPlanWithCommand);
    }

}
