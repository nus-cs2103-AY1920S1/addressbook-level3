package seedu.savenus.logic.parser;

import static seedu.savenus.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.savenus.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.savenus.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.savenus.logic.commands.FindCommand;
import seedu.savenus.model.food.NameContainsKeywordsPredicate;

public class FindCommandParserTest {

    private FindCommandParser parser = new FindCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindCommand() {
        // no leading and trailing whitespaces
        FindCommand expectedFindCommand =
                new FindCommand(new NameContainsKeywordsPredicate(Arrays.asList("Ayam", "Penyet")));
        assertParseSuccess(parser, "Ayam Penyet", expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n Ayam \n \t Penyet  \t", expectedFindCommand);
    }

}
