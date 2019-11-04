package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIds.ID_FIRST_MEMBER;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.DeleteMemberCommand;
import seedu.address.logic.commands.exceptions.CommandException;

public class DeleteMemberCommandParserTest {
    private DeleteMemberCommandParser parser = new DeleteMemberCommandParser();

    @Test
    public void parse_validArgs_returnsDeleteMemberCommand() throws CommandException {
        assertParseSuccess(parser, "delete-member mi/GS", new DeleteMemberCommand(ID_FIRST_MEMBER));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() throws CommandException {
        assertParseFailure(parser, "3", String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteMemberCommand.MESSAGE_USAGE));
    }
}
