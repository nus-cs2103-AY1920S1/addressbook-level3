package seedu.jarvis.logic.parser.planner;

import static seedu.jarvis.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.jarvis.logic.commands.Command;
import seedu.jarvis.logic.commands.planner.DeleteTaskCommand;
import seedu.jarvis.logic.parser.ParserUtil;
import seedu.jarvis.logic.parser.exceptions.ParseException;

class DeleteTaskCommandParserTest {

    @Test
    void parse_validInput_success() throws ParseException {
        DeleteTaskCommandParser parser = new DeleteTaskCommandParser();
        String userInput = " 1";
        Command expectedCommand = new DeleteTaskCommand(ParserUtil.parseIndex("1"));

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    void parse_invalidIndex_exceptionThrown() {

    }
}
