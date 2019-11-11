package seedu.pluswork.logic.parser.member;

import static seedu.pluswork.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.pluswork.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.pluswork.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.pluswork.testutil.TypicalIds.ID_FIRST_MEMBER;

import org.junit.jupiter.api.Test;

import seedu.pluswork.logic.commands.member.DeleteMemberCommand;
import seedu.pluswork.logic.commands.exceptions.CommandException;
import seedu.pluswork.logic.parser.member.DeleteMemberCommandParser;

public class DeleteMemberCommandParserTest {
    private DeleteMemberCommandParser parser = new DeleteMemberCommandParser();

    @Test
    public void parse_validArgs_returnsDeleteMemberCommand() throws CommandException {
        assertParseSuccess(parser, "delete-member mi/GS", new DeleteMemberCommand(ID_FIRST_MEMBER));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() throws CommandException {
        assertParseFailure(parser, "3", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                DeleteMemberCommand.MESSAGE_USAGE));
    }
}
