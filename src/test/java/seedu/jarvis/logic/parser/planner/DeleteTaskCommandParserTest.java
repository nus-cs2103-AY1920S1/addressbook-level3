package seedu.jarvis.logic.parser.planner;

import static seedu.jarvis.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.jarvis.logic.parser.CommandParserTestUtil.assertParseFailure;
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
        DeleteTaskCommandParser parser = new DeleteTaskCommandParser();
        String userInput = " not a number";
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteTaskCommand.MESSAGE_USAGE);

        assertParseFailure(parser, userInput, expectedMessage);
    }
}
