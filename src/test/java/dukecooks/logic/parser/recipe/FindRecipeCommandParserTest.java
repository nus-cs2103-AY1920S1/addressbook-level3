package dukecooks.logic.parser.recipe;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import dukecooks.commons.core.Messages;
import dukecooks.logic.commands.recipe.FindRecipeCommand;
import dukecooks.logic.parser.CommandParserTestUtil;
import dukecooks.model.recipe.components.RecipeNameContainsKeywordsPredicate;

public class FindRecipeCommandParserTest {

    private FindRecipeCommandParser parser = new FindRecipeCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        CommandParserTestUtil.assertParseFailure(parser, "     ", String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                FindRecipeCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindCommand() {
        // no leading and trailing whitespaces
        FindRecipeCommand expectedFindRecipeCommand =
                new FindRecipeCommand(new RecipeNameContainsKeywordsPredicate(Arrays.asList("Fish", "Burger")));
        CommandParserTestUtil.assertParseSuccess(parser, "Fish Burger", expectedFindRecipeCommand);

        // multiple whitespaces between keywords
        CommandParserTestUtil.assertParseSuccess(parser, " \n Fish \n \t Burger  \t", expectedFindRecipeCommand);
    }

}
