package seedu.jarvis.logic.parser.planner;

import static seedu.jarvis.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.jarvis.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.jarvis.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.jarvis.logic.commands.planner.DoneTaskCommand;
import seedu.jarvis.logic.parser.ParserUtil;
import seedu.jarvis.logic.parser.exceptions.ParseException;

class DoneTaskCommandParserTest {

    @Test
    void parse_validArgs_returnsDoneCommand() throws ParseException {
        DoneTaskCommandParser parser = new DoneTaskCommandParser();
        assertParseSuccess(parser, "1", new DoneTaskCommand(ParserUtil.parseIndex("1")));
    }

    @Test
    void parse_invalidArgs_throwsParseException() {
        DoneTaskCommandParser parser = new DoneTaskCommandParser();
        assertParseFailure(parser, "a",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DoneTaskCommand.MESSAGE_USAGE));
    }
}
