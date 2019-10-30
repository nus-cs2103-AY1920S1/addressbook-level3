package dukecooks.logic.parser.mealplan;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import dukecooks.commons.core.Messages;
import dukecooks.logic.commands.mealplan.FindMealPlanCommand;
import dukecooks.logic.parser.CommandParserTestUtil;
import dukecooks.model.mealplan.components.MealPlanNameContainsKeywordsPredicate;

public class FindMealPlanCommandParserTest {

    private FindMealPlanCommandParser parser = new FindMealPlanCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        CommandParserTestUtil.assertParseFailure(parser, "     ",
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, FindMealPlanCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindCommand() {
        // no leading and trailing whitespaces
        FindMealPlanCommand expectedFindMealPlanCommand =
                new FindMealPlanCommand(new MealPlanNameContainsKeywordsPredicate(Arrays.asList("Fish", "Burger")));
        CommandParserTestUtil.assertParseSuccess(parser, "Fish Burger", expectedFindMealPlanCommand);

        // multiple whitespaces between keywords
        CommandParserTestUtil.assertParseSuccess(parser, " \n Fish \n \t Burger  \t", expectedFindMealPlanCommand);
    }

}
