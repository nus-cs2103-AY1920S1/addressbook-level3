package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FindRecipeCommand;
import seedu.address.model.recipe.NameContainsKeywordsPredicate;

public class FindRecipeCommandParserTest {

    private FindRecipeCommandParser parser = new FindRecipeCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                FindRecipeCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindCommand() {
        // no leading and trailing whitespaces
        FindRecipeCommand expectedFindRecipeCommand =
                new FindRecipeCommand(new NameContainsKeywordsPredicate(Arrays.asList("Fish", "Burger")));
        assertParseSuccess(parser, "Fish Burger", expectedFindRecipeCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n Fish \n \t Burger  \t", expectedFindRecipeCommand);
    }

}
