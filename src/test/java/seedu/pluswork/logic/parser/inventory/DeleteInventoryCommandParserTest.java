package seedu.pluswork.logic.parser.inventory;

import static seedu.pluswork.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.pluswork.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.pluswork.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.pluswork.commons.core.index.Index;
import seedu.pluswork.logic.commands.exceptions.CommandException;
import seedu.pluswork.logic.commands.inventory.DeleteInventoryCommand;

public class DeleteInventoryCommandParserTest {
    private DeleteInventoryCommandParser parser = new DeleteInventoryCommandParser();

    @Test
    public void parse_validArgs_returnsDeleteMemberCommand() throws CommandException {
        assertParseSuccess(parser, "delete-inv ii/1", new DeleteInventoryCommand(new Index(0)));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() throws CommandException {
        assertParseFailure(parser, "delete-inv 3", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                DeleteInventoryCommand.MESSAGE_USAGE));
    }
}
