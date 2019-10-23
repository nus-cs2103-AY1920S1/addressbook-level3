package seedu.address.logic.parser.deletecommandparser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.deletecommand.DeleteMentorCommand;
import seedu.address.model.entity.Id;
import seedu.address.model.entity.PrefixType;

class DeleteMentorCommandParserTest {

    private DeleteMentorCommandParser parser = new DeleteMentorCommandParser();
    private Id mentorId = new Id(PrefixType.M, 1);

    @Test
    public void parse_validArgs_returnsDeleteCommand() {
        assertParseSuccess(parser, "M-1", new DeleteMentorCommand(mentorId));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        // Invalid index provided
        assertParseFailure(parser, "a",
                String.format(DeleteMentorCommand.MESSAGE_INVALID_MENTOR_DISPLAYED_INDEX));

        // No argument provided
        assertParseFailure(parser, "",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteMentorCommand.MESSAGE_USAGE));
    }
}
