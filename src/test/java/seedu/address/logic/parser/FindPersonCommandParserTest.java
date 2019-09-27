package seedu.address.logic.parser;

import org.junit.jupiter.api.Test;
import seedu.address.logic.commands.FindPersonCommand;

import static org.junit.jupiter.api.Assertions.*;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.personutil.TypicalPersonDescriptor.ALICE;
import static seedu.address.testutil.personutil.TypicalPersonDescriptor.BENSON;
import static seedu.address.testutil.personutil.TypicalPersonDescriptor.WHITESPACE;

class FindPersonCommandParserTest {

    private FindPersonCommandParser parser = new FindPersonCommandParser();

    @Test
    void parse_success() {
        assertParseSuccess(parser,
                 WHITESPACE + PREFIX_NAME + WHITESPACE + ALICE.getName().toString(),
                new FindPersonCommand(ALICE.getName()));

        assertParseSuccess(parser,
                WHITESPACE + PREFIX_NAME + BENSON.getName().toString(),
                new FindPersonCommand(BENSON.getName()));
    }

    @Test
    void parse_failure() {
        assertParseFailure(parser,
                WHITESPACE,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindPersonCommand.MESSAGE_USAGE));

    }
}