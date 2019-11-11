package seedu.address.logic.parser.expense;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.expense.DeleteExpenseCommand;

class DeleteExpenseParserTest {
    private DeleteExpenseParser parser = new DeleteExpenseParser();
    @Test
    public void parse_validIndex_success() {
        assertParseSuccess(parser, "1", new DeleteExpenseCommand(Index.fromOneBased(1)));
    }

    @Test
    public void parse_invalidIndex_throwsParseException() {
        assertParseFailure(parser, "0", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                DeleteExpenseCommand.MESSAGE_USAGE));

        assertParseFailure(parser, "-1", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                DeleteExpenseCommand.MESSAGE_USAGE));
    }
}
