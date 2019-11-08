package seedu.mark.logic.parser;

import static seedu.mark.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.mark.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.mark.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.mark.testutil.TypicalIndexes.INDEX_FIRST_REMINDER;

import org.junit.jupiter.api.Test;

import seedu.mark.logic.commands.GotoReminderCommand;

class GotoReminderCommandParserTest {
    private GotoReminderCommandParser parser = new GotoReminderCommandParser();

    @Test
    public void parse_validArgs_returnsGotoCommand() {
        assertParseSuccess(parser, "1", new GotoReminderCommand(INDEX_FIRST_REMINDER));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, GotoReminderCommand.MESSAGE_USAGE));
    }

}
