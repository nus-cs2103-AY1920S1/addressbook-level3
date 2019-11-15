package seedu.guilttrip.logic.parser;

import static seedu.guilttrip.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.guilttrip.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.guilttrip.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.guilttrip.testutil.TypicalIndexes.INDEX_FIRST_ENTRY;

import org.junit.jupiter.api.Test;

import seedu.guilttrip.logic.commands.deletecommands.DeleteExpenseCommand;
import seedu.guilttrip.logic.parser.deletecommandparsers.DeleteExpenseCommandParser;

public class DeleteExpenseCommandParserTest {
    private DeleteExpenseCommandParser parser = new DeleteExpenseCommandParser();

    @Test
    public void parse_validArgs_returnsDeleteCommand() {
        assertParseSuccess(parser, "1", new DeleteExpenseCommand(INDEX_FIRST_ENTRY));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                DeleteExpenseCommand.MESSAGE_USAGE));
    }
}
