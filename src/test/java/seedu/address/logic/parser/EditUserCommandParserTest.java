package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.personutil.TypicalPersonDescriptor.WHITESPACE;
import static seedu.address.testutil.personutil.TypicalPersonDescriptor.ZACK;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.EditUserCommand;

class EditUserCommandParserTest {

    private EditUserCommandParser parser = new EditUserCommandParser();

    @Test
    void parse_success() {
        assertParseSuccess(parser,
                WHITESPACE + PREFIX_NAME + ZACK.getName().toString() + WHITESPACE
                        + PREFIX_PHONE + ZACK.getPhone().toString() + WHITESPACE
                        + PREFIX_EMAIL + ZACK.getEmail().toString() + WHITESPACE
                        + PREFIX_ADDRESS + ZACK.getAddress().toString() + WHITESPACE
                        + PREFIX_REMARK + ZACK.getRemark().toString() + WHITESPACE
                        + PREFIX_TAG + WHITESPACE,
                new EditUserCommand(ZACK));
    }

    @Test
    void parse_failure() {
        assertParseFailure(parser,
                WHITESPACE,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditUserCommand.MESSAGE_USAGE));

    }
}
