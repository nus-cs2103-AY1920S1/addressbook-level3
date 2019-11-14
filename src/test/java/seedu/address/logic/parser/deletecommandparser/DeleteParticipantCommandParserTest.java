package seedu.address.logic.parser.deletecommandparser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.deletecommand.DeleteParticipantCommand;
import seedu.address.model.entity.Id;
import seedu.address.model.entity.PrefixType;

class DeleteParticipantCommandParserTest {

    private DeleteParticipantCommandParser parser = new DeleteParticipantCommandParser();
    private Id participantId = new Id(PrefixType.P, 1);

    @Test
    public void parse_validArgs_returnsDeleteCommand() {
        assertParseSuccess(parser, "P-1", new DeleteParticipantCommand(participantId));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        // Invalid index provided
        assertParseFailure(parser, "a",
                String.format(DeleteParticipantCommand.MESSAGE_INVALID_PARTICIPANT_DISPLAYED_INDEX));

        // No argument provided
        assertParseFailure(parser, "",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteParticipantCommand.MESSAGE_USAGE));
    }
}
