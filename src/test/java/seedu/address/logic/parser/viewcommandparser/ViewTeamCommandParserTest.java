package seedu.address.logic.parser.viewcommandparser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.viewcommand.ViewTeamCommand;
import seedu.address.model.entity.Id;
import seedu.address.model.entity.PrefixType;

class ViewTeamCommandParserTest {

    private ViewTeamCommandParser parser = new ViewTeamCommandParser();
    private Id teamId = new Id(PrefixType.T, 1);

    @Test
    public void parse_validArgs_returnsViewCommand() {
        assertParseSuccess(parser, "T-1", new ViewTeamCommand(teamId));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        // Invalid index provided
        assertParseFailure(parser, "a",
                String.format(ViewTeamCommand.MESSAGE_INVALID_TEAM_DISPLAYED_INDEX));

        // No argument provided
        assertParseFailure(parser, "",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewTeamCommand.MESSAGE_USAGE));
    }
}
