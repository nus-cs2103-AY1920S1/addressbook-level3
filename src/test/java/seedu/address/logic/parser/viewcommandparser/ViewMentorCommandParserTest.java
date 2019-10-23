package seedu.address.logic.parser.viewcommandparser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.viewcommand.ViewMentorCommand;
import seedu.address.model.entity.Id;
import seedu.address.model.entity.PrefixType;

class ViewMentorCommandParserTest {

    private ViewMentorCommandParser parser = new ViewMentorCommandParser();
    private Id mentorId = new Id(PrefixType.M, 1);

    @Test
    public void parse_validArgs_returnsViewCommand() {
        assertParseSuccess(parser, "M-1", new ViewMentorCommand(mentorId));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        // Invalid index provided
        assertParseFailure(parser, "a",
                String.format(ViewMentorCommand.MESSAGE_INVALID_MENTOR_DISPLAYED_INDEX));

        // No argument provided
        assertParseFailure(parser, "",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewMentorCommand.MESSAGE_USAGE));
    }
}
