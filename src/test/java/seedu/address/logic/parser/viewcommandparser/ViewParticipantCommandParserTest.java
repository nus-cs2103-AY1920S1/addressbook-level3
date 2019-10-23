package seedu.address.logic.parser.viewcommandparser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.viewcommand.ViewParticipantCommand;
import seedu.address.model.entity.Id;
import seedu.address.model.entity.PrefixType;

class ViewParticipantCommandParserTest {

    private ViewParticipantCommandParser parser = new ViewParticipantCommandParser();
    private Id participantId = new Id(PrefixType.P, 1);

    @Test
    public void parse_validArgs_returnsViewCommand() {
        assertParseSuccess(parser, "P-1", new ViewParticipantCommand(participantId));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        // Invalid index provided
        assertParseFailure(parser, "a",
                String.format(ViewParticipantCommand.MESSAGE_INVALID_PARTICIPANT_DISPLAYED_INDEX));

        // No argument provided
        assertParseFailure(parser, "",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewParticipantCommand.MESSAGE_USAGE));
    }
}
