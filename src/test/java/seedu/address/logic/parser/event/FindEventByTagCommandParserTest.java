package seedu.address.logic.parser.event;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseNoIndexAndFieldFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.event.FindEventByTagCommand;
import seedu.address.model.event.EventTagContainsKeywordsPredicate;

public class FindEventByTagCommandParserTest {

    private FindEventByTagCommandParser parser = new FindEventByTagCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseNoIndexAndFieldFailure(parser,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindEventByTagCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindCommand() {
        // no leading and trailing whitespaces
        FindEventByTagCommand expectedFindEventByTagCommand =
                new FindEventByTagCommand(new EventTagContainsKeywordsPredicate(Arrays.asList("Music", "Above21")));
        assertParseSuccess(parser, "Music Above21", expectedFindEventByTagCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n Music \n \t Above21  \t", expectedFindEventByTagCommand);
    }

}
