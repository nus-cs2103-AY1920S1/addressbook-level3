package tagline.logic.parser;

import static tagline.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static tagline.logic.parser.CommandParserTestUtil.assertParseFailure;
import static tagline.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import tagline.logic.commands.contact.FindContactCommand;
import tagline.logic.parser.contact.FindContactParser;
import tagline.model.contact.NameContainsKeywordsPredicate;

public class FindContactParserTest {

    private FindContactParser parser = new FindContactParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindContactCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindCommand() {
        // no leading and trailing whitespaces
        FindContactCommand expectedFindContactCommand =
                new FindContactCommand(new NameContainsKeywordsPredicate(Arrays.asList("Alice", "Bob")));
        assertParseSuccess(parser, "Alice Bob", expectedFindContactCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n Alice \n \t Bob  \t", expectedFindContactCommand);
    }

}
