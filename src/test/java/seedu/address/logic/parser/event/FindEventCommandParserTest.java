package seedu.address.logic.parser.event;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.event.FindEventCommand;
import seedu.address.model.event.EventNameContainsKeywordsPredicate;

public class FindEventCommandParserTest {

    private FindEventCommandParser parser = new FindEventCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindEventCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindCommand() {
        // no leading and trailing whitespaces
        FindEventCommand expectedFindEventCommand =
                new FindEventCommand(new EventNameContainsKeywordsPredicate(Arrays.asList("Musical", "Concert")));
        assertParseSuccess(parser, "Musical Concert", expectedFindEventCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n Musical \n \t Concert  \t", expectedFindEventCommand);
    }

}
