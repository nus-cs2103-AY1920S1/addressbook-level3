package seedu.address.logic.parser;

import org.junit.jupiter.api.Test;
import seedu.address.logic.commands.FindGroupCommand;
import seedu.address.logic.commands.FindPersonCommand;

import static org.junit.jupiter.api.Assertions.*;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GROUPNAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.grouputil.TypicalGroups.GROUP1;
import static seedu.address.testutil.grouputil.TypicalGroups.GROUPNAME1;
import static seedu.address.testutil.grouputil.TypicalGroups.GROUPNAME2;
import static seedu.address.testutil.personutil.TypicalPersonDescriptor.ALICE;
import static seedu.address.testutil.personutil.TypicalPersonDescriptor.BENSON;
import static seedu.address.testutil.personutil.TypicalPersonDescriptor.WHITESPACE;

class FindGroupCommandParserTest {

    private FindGroupCommandParser parser = new FindGroupCommandParser();

    @Test
    void parse_success() {
        assertParseSuccess(parser,
                WHITESPACE + PREFIX_GROUPNAME + WHITESPACE + GROUPNAME1.toString(),
                new FindGroupCommand(GROUPNAME1));

        assertParseSuccess(parser,
                WHITESPACE + PREFIX_GROUPNAME + GROUPNAME2.toString(),
                new FindGroupCommand(GROUPNAME2));
    }

    @Test
    void parse_failure() {
        assertParseFailure(parser,
                WHITESPACE,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindGroupCommand.MESSAGE_USAGE));
    }
}