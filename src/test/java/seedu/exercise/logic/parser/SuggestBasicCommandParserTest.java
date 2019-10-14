package seedu.exercise.logic.parser;

import static seedu.exercise.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.exercise.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.exercise.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.exercise.logic.commands.SuggestBasicCommand;


public class SuggestBasicCommandParserTest {
    private SuggestCommandParser parser = new SuggestCommandParser();

    @Test
    public void parse_validArgs_returnsSuggestBasicCommand() {
        assertParseSuccess(parser, " g/basic", new SuggestBasicCommand());
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                SuggestBasicCommand.MESSAGE_USAGE));
    }
}
