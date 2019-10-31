package seedu.address.logic.parser.findcommandparser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Optional;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.findcommand.FindTeamCommand;

public class FindTeamCommandParserTest {
    private FindTeamCommandParser findTeamParser = new FindTeamCommandParser();

    @Test
    public void parse_validArgs_returnsFindCommand() {
        assertParseSuccess(findTeamParser, "team n/ifje",
                new FindTeamCommand(Optional.of("ifje"),
                        Optional.empty()));
    }

    @Test
    public void parse_inValidArgs_throwsParseException() {
        // No command provided
        assertParseFailure(findTeamParser, "", String.format(
                MESSAGE_INVALID_COMMAND_FORMAT, FindTeamCommand.MESSAGE_USAGE));
    }
}
