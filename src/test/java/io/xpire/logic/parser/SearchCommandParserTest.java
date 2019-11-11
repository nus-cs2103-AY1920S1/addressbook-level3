package io.xpire.logic.parser;

import static io.xpire.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static io.xpire.logic.parser.CommandParserTestUtil.assertEqualsParseSuccess;
import static io.xpire.logic.parser.CommandParserTestUtil.assertParseFailure;
import static io.xpire.model.ListType.XPIRE;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import io.xpire.logic.commands.SearchCommand;
import io.xpire.model.item.ContainsKeywordsPredicate;

//@@author JermyTan
public class SearchCommandParserTest {

    private SearchCommandParser parser = new SearchCommandParser(XPIRE);

    @Test
    public void parse_invalidArgs_throwsParseException() {
        // empty args
        assertParseFailure(parser, "     ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SearchCommand.MESSAGE_USAGE));

        // all empty keywords
        assertParseFailure(parser, " | || |  | ||  ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SearchCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsSearchCommand() {
        SearchCommand expectedSearchCommand =
                new SearchCommand(XPIRE,
                        new ContainsKeywordsPredicate(Arrays.asList("apple", "#Food", "#Fruit", "orange")));

        // no leading and trailing whitespaces
        assertEqualsParseSuccess(parser, "Apple|#Food|#Fruit|Orange", expectedSearchCommand);

        // multiple whitespaces between keywords
        assertEqualsParseSuccess(parser, "  Apple |  #Food |   #Fruit  |   Orange  ", expectedSearchCommand);

        // multiple empty keywords
        assertEqualsParseSuccess(parser, " Apple| | #Food | || | #Fruit | ||| Orange ", expectedSearchCommand);

        // order insensitive
        assertEqualsParseSuccess(parser, "#Fruit|Apple|Orange|#Food", expectedSearchCommand);

        // case insensitive for name
        assertEqualsParseSuccess(parser, "apple|#Food|#Fruit|oRaNGe", expectedSearchCommand);

        // case insensitive for tag
        assertEqualsParseSuccess(parser, "Apple|#food|#fruit|Orange", expectedSearchCommand);
    }
}
