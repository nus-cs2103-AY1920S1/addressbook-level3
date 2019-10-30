package organice.logic.parser;

import static organice.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static organice.logic.parser.CliSyntax.PREFIX_NAME;
import static organice.logic.parser.CommandParserTestUtil.assertParseFailure;
import static organice.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import organice.logic.commands.FindCommand;
import organice.model.person.PersonContainsPrefixesPredicate;

public class FindCommandParserTest {

    private FindCommandParser parser = new FindCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindCommand() {
        // no leading and trailing whitespaces
        FindCommand expectedFindCommand = new FindCommand(new PersonContainsPrefixesPredicate(
                ArgumentTokenizer.tokenize("   find n/Alice Bob      ", PREFIX_NAME)));
        assertParseSuccess(parser, FindCommand.COMMAND_WORD + " n/Alice Bob", expectedFindCommand);
    }

}
