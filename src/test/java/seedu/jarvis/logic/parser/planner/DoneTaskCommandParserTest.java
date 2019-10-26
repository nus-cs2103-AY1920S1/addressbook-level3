package seedu.jarvis.logic.parser.planner;

import seedu.jarvis.logic.commands.planner.DoneTaskCommand;
import seedu.jarvis.logic.parser.ParserUtil;
import seedu.jarvis.logic.parser.exceptions.ParseException;

import org.junit.jupiter.api.Test;

import static seedu.jarvis.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.jarvis.logic.parser.CommandParserTestUtil.assertParseSuccess;

class DoneTaskCommandParserTest {

    @Test
    void parse_validArgs_returnsDoneCommand() throws ParseException {
        DoneTaskCommandParser parser = new DoneTaskCommandParser();
        assertParseSuccess(parser, "1", new DoneTaskCommand(ParserUtil.parseIndex("1")));
    }

    @Test
    void parse_invalidArgs_throwsParseException() {
        DoneTaskCommandParser parser = new DoneTaskCommandParser();
        assertParseFailure(parser, "a", DoneTaskCommand.MESSAGE_USAGE);
    }
}