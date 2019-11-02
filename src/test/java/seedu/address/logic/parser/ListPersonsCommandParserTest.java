package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.ListPersonsCommand;
import seedu.address.model.tag.TagContainsKeywordsPredicate;

public class ListPersonsCommandParserTest {

    private ListPersonsCommandParser parser = new ListPersonsCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ListPersonsCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindCommand() {
        // no leading and trailing whitespaces
        ListPersonsCommand expectedListPersonsCommand =
                new ListPersonsCommand(new TagContainsKeywordsPredicate(Arrays.asList("Team-1", "OC")));
        assertParseSuccess(parser, "Team-1 OC", expectedListPersonsCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n Team-1 \n \t OC  \t", expectedListPersonsCommand);
    }

}
