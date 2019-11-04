package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.LoginCommand;
import seedu.address.model.person.LoginCredentialsPredicate;
import seedu.address.model.person.Password;
import seedu.address.model.person.Username;

//@@author madanalogy
class LoginCommandParserTest {

    private LoginCommandParser parser = new LoginCommandParser();

    @Test
    public void parse_invalidArgs_throwsParseException() {
        // Empty Args
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                LoginCommand.MESSAGE_USAGE));

        // Invalid Prefix
        assertParseFailure(parser, " u w/passw0rd", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                LoginCommand.MESSAGE_USAGE));

        // Invalid Username
        assertParseFailure(parser, " u/ w/password", LoginCommand.MESSAGE_FAILURE);

        // Invalid Password
        assertParseFailure(parser, " u/Agent w/", LoginCommand.MESSAGE_FAILURE);
    }

    @Test
    public void parse_validArgs_returnsLoginCommand() {
        LoginCommand expectedLoginCommand = new LoginCommand(
                new LoginCredentialsPredicate(new Username("Agent"), new Password("password")));

        assertParseSuccess(parser, " u/Agent w/password", expectedLoginCommand);
    }
}
