package tagline.logic.parser.contact;

import static tagline.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static tagline.logic.parser.CommandParserTestUtil.assertPromptRequest;
import static tagline.logic.parser.contact.FindContactParser.FIND_CONTACT_EMPTY_ARGS_PROMPT_STRING;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import tagline.logic.commands.contact.FindContactCommand;
import tagline.logic.parser.Prompt;
import tagline.model.contact.NameContainsKeywordsPredicate;

public class FindContactParserTest {

    private FindContactParser parser = new FindContactParser();

    @Test
    public void parse_emptyArgs_throwsParseException() {
        assertPromptRequest(parser, "     ",
                Collections.singletonList(new Prompt("", FIND_CONTACT_EMPTY_ARGS_PROMPT_STRING)));
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
