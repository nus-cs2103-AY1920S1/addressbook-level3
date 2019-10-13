package seedu.ezwatchlist.logic.parser;

import static seedu.ezwatchlist.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
<<<<<<< HEAD:src/test/java/seedu/address/logic/parser/FindCommandParserTest.java
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
=======
import static seedu.ezwatchlist.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.ezwatchlist.logic.parser.CommandParserTestUtil.assertParseSuccess;
>>>>>>> orgmain/branch-v1.2:src/test/java/seedu/ezwatchlist/logic/parser/FindCommandParserTest.java

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.ezwatchlist.logic.commands.FindCommand;
<<<<<<< HEAD:src/test/java/seedu/address/logic/parser/FindCommandParserTest.java
import seedu.ezwatchlist.logic.parser.FindCommandParser;
=======
>>>>>>> orgmain/branch-v1.2:src/test/java/seedu/ezwatchlist/logic/parser/FindCommandParserTest.java
import seedu.ezwatchlist.model.person.NameContainsKeywordsPredicate;

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
                new FindCommand(new NameContainsKeywordsPredicate(Arrays.asList("Alice", "Bob")));
        assertParseSuccess(parser, "Alice Bob", expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n Alice \n \t Bob  \t", expectedFindCommand);
    }

}
