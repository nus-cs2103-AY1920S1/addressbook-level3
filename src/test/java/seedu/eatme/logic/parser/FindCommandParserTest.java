package seedu.eatme.logic.parser;

import static seedu.eatme.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.eatme.logic.commands.CommandTestUtil.VALID_ADDRESS_NO_PREFIX_KFC;
import static seedu.eatme.logic.commands.CommandTestUtil.VALID_ADDRESS_NO_PREFIX_MAC;
import static seedu.eatme.logic.commands.CommandTestUtil.VALID_CATEGORY_NO_PREFIX;
import static seedu.eatme.logic.commands.CommandTestUtil.VALID_NAME_NO_PREFIX_KFC;
import static seedu.eatme.logic.commands.CommandTestUtil.VALID_NAME_NO_PREFIX_MAC;
import static seedu.eatme.logic.commands.CommandTestUtil.VALID_TAG_NO_PREFIX_CHEAP;
import static seedu.eatme.logic.commands.CommandTestUtil.VALID_TAG_NO_PREFIX_NICE;
import static seedu.eatme.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.eatme.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static seedu.eatme.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.eatme.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.eatme.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.eatme.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.eatme.logic.commands.FindCommand;
import seedu.eatme.model.eatery.EateryAttributesContainsKeywordsPredicate;

public class FindCommandParserTest {

    private FindCommandParser parser = new FindCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindCommand() {
        // no leading and trailing whitespaces
        FindCommand expectedFindCommand =
                new FindCommand(new EateryAttributesContainsKeywordsPredicate(Arrays.asList("Alice", "Bob")));
        assertParseSuccess(parser, " " + PREFIX_NAME + " Alice " + PREFIX_NAME + " Bob", expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser,
                " " + PREFIX_NAME + "\n \t Alice \t  " + PREFIX_NAME + " Bob  \t", expectedFindCommand);
    }

    @Test
    public void parse_validMultiArgs_returnFindCommand() {
        List<String> nameKeywords = Arrays.asList(VALID_NAME_NO_PREFIX_MAC, VALID_NAME_NO_PREFIX_KFC);
        List<String> addressKeywords = Arrays.asList(VALID_ADDRESS_NO_PREFIX_MAC, VALID_ADDRESS_NO_PREFIX_KFC);
        List<String> categoryKeywords = Arrays.asList(VALID_CATEGORY_NO_PREFIX);
        List<String> tagKeywords = Arrays.asList(VALID_TAG_NO_PREFIX_CHEAP, VALID_TAG_NO_PREFIX_NICE);

        FindCommand expectedFindCommand = new FindCommand(new EateryAttributesContainsKeywordsPredicate(
                nameKeywords, addressKeywords, categoryKeywords, tagKeywords));

        assertParseSuccess(parser,
                " " + PREFIX_NAME + " " + VALID_NAME_NO_PREFIX_MAC
                        + " " + PREFIX_NAME + " " + VALID_NAME_NO_PREFIX_KFC
                        + " " + PREFIX_ADDRESS + " " + VALID_ADDRESS_NO_PREFIX_MAC
                        + " " + PREFIX_ADDRESS + " " + VALID_ADDRESS_NO_PREFIX_KFC
                        + " " + PREFIX_CATEGORY + " " + VALID_CATEGORY_NO_PREFIX
                        + " " + PREFIX_TAG + " " + VALID_TAG_NO_PREFIX_CHEAP
                        + " " + PREFIX_TAG + " " + VALID_TAG_NO_PREFIX_NICE,
                expectedFindCommand);
    }

}
