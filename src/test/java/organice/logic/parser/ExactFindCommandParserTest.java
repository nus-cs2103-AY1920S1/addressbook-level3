package organice.logic.parser;

import static organice.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static organice.logic.parser.CliSyntax.PREFIX_NAME;
import static organice.logic.parser.CommandParserTestUtil.assertParseFailure;
import static organice.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import organice.logic.commands.ExactFindCommand;
import organice.model.person.PersonContainsPrefixesPredicate;

public class ExactFindCommandParserTest {

    private ExactFindCommandParser parser = new ExactFindCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ExactFindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsExactFindCommand() {
        // no leading and trailing whitespaces
        ExactFindCommand expectedExactFindCommand = new ExactFindCommand(new PersonContainsPrefixesPredicate(
                ArgumentTokenizer.tokenize(" n/Alice Bob",
                        PREFIX_NAME)));
        assertParseSuccess(parser, " n/Alice Bob", expectedExactFindCommand);
    }

}
