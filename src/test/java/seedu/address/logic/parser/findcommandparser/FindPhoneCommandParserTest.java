package seedu.address.logic.parser.findcommandparser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.findcommand.FindPhoneCommand;
import seedu.address.model.phone.predicates.PhoneContainsKeywordsPredicate;

public class FindPhoneCommandParserTest {

    private FindPhoneCommandParser parser = new FindPhoneCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                FindPhoneCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindCommand() {

        // no leading and trailing whitespaces

        FindPhoneCommand expectedFindCommand =
                new FindPhoneCommand(new PhoneContainsKeywordsPredicate(Arrays.asList("iPhone", "Galaxy")));
        assertParseSuccess(parser, "iPhone Galaxy", expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n iPhone \n \t Galaxy  \t", expectedFindCommand);
    }

}

