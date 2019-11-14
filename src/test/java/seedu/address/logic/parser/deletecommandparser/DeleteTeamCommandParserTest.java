package seedu.address.logic.parser.deletecommandparser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.deletecommand.DeleteTeamCommand;
import seedu.address.model.entity.Id;
import seedu.address.model.entity.PrefixType;

class DeleteTeamCommandParserTest {

    private DeleteTeamCommandParser parser = new DeleteTeamCommandParser();
    private Id teamId = new Id(PrefixType.T, 1);

    @Test
    public void parse_validArgs_returnsDeleteCommand() {
        assertParseSuccess(parser, "T-1", new DeleteTeamCommand(teamId));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        // Invalid index provided
        assertParseFailure(parser, "a",
                String.format(DeleteTeamCommand.MESSAGE_INVALID_TEAM_DISPLAYED_INDEX));

        // No argument provided
        assertParseFailure(parser, "",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteTeamCommand.MESSAGE_USAGE));
    }
}
