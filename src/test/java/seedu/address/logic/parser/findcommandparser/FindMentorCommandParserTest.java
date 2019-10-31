package seedu.address.logic.parser.findcommandparser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Optional;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.findcommand.FindMentorCommand;

public class FindMentorCommandParserTest {
    private FindMentorCommandParser findMentorParser = new FindMentorCommandParser();

    @Test
    public void parse_validArgs_returnsFindCommand() {
        assertParseSuccess(findMentorParser, "participant n/ifje",
                new FindMentorCommand(Optional.of("ifje"),
                        Optional.empty(),
                        Optional.empty(),
                        Optional.empty()));
    }

    @Test
    public void parse_inValidArgs_throwsParseException() {
        // No command provided
        assertParseFailure(findMentorParser, "", String.format(
                MESSAGE_INVALID_COMMAND_FORMAT, FindMentorCommand.MESSAGE_USAGE));
    }
}
