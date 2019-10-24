package seedu.jarvis.logic.parser.planner;

import seedu.jarvis.logic.commands.planner.FindTaskCommand;
import seedu.jarvis.model.planner.TaskDesContainsKeywordsPredicate;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static seedu.jarvis.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.jarvis.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.jarvis.logic.parser.CommandParserTestUtil.assertParseSuccess;

class FindTaskCommandParserTest {

    @Test
    void parse_emptyArg_throwsParseException() {
        FindTaskCommandParser parser = new FindTaskCommandParser();
        assertParseFailure(parser, " ", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                                                                FindTaskCommand.MESSAGE_USAGE));
    }

    @Test
    void parse_validArgs_returnsFindCommand() {
        FindTaskCommandParser parser = new FindTaskCommandParser();

        //no leading and trailing whitespace
        FindTaskCommand expected = new FindTaskCommand(
                                        new TaskDesContainsKeywordsPredicate(Arrays.asList("book", "read")));
        assertParseSuccess(parser, "book read", expected);

        //multiple whitespaces between keywords
        assertParseSuccess(parser, "\n book \n \t read \t", expected);
    }
}