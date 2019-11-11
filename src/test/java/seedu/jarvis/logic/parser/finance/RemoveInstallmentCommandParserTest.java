package seedu.jarvis.logic.parser.finance;

import static seedu.jarvis.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.jarvis.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.jarvis.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.jarvis.testutil.TypicalIndexes.INDEX_FIRST_INSTALLMENT;

import org.junit.jupiter.api.Test;

import seedu.jarvis.logic.commands.finance.RemoveInstallmentCommand;

public class RemoveInstallmentCommandParserTest {

    private RemoveInstallmentCommandParser parser = new RemoveInstallmentCommandParser();

    @Test
    public void parse_validArgs_returnsDeleteCommand() {
        assertParseSuccess(parser, "1", new RemoveInstallmentCommand(INDEX_FIRST_INSTALLMENT));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                RemoveInstallmentCommand.MESSAGE_USAGE));
    }
}
