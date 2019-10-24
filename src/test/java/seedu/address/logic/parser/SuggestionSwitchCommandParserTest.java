package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.SuggestionSwitchCommand;

public class SuggestionSwitchCommandParserTest {

    private SuggestionSwitchCommandParser parser = new SuggestionSwitchCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SuggestionSwitchCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgsOn_returnsSuggestionCommand() {
        // no leading and trailing whitespaces
        SuggestionSwitchCommand expectedSuggestionSwitchCommand =
                new SuggestionSwitchCommand(true);

        assertParseSuccess(parser, " ON/", expectedSuggestionSwitchCommand);

        assertParseSuccess(parser, " ON/asdf", expectedSuggestionSwitchCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n  \n \t ON/  \t", expectedSuggestionSwitchCommand);

    }

    @Test
    public void parse_validArgsOff_returnsSuggestionCommand() {
        // no leading and trailing whitespaces
        SuggestionSwitchCommand expectedSuggestionSwitchCommand =
                new SuggestionSwitchCommand(false);

        assertParseSuccess(parser, " OFF/", expectedSuggestionSwitchCommand);

        assertParseSuccess(parser, " OFF/asdf", expectedSuggestionSwitchCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n  \n \t OFF/  \t", expectedSuggestionSwitchCommand);

    }

    @Test
    public void parse_invalidArgs_returnsSuggestionCommand() {
        assertParseFailure(parser, " ON/ OFF/", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                SuggestionSwitchCommand.MESSAGE_USAGE));

    }

}
