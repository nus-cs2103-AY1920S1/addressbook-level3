package seedu.jarvis.logic.parser.planner;

import seedu.jarvis.logic.commands.Command;
import seedu.jarvis.logic.commands.address.DeleteAddressCommand;
import seedu.jarvis.logic.commands.planner.DeleteTaskCommand;
import seedu.jarvis.logic.parser.ParserUtil;
import seedu.jarvis.logic.parser.exceptions.ParseException;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static seedu.jarvis.logic.parser.CommandParserTestUtil.assertParseSuccess;

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