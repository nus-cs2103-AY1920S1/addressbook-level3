package seedu.ezwatchlist.logic.parser;

import static seedu.ezwatchlist.commons.core.messages.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.ezwatchlist.logic.commands.CommandTestUtil.NAME_DESC_ANNABELLE;
import static seedu.ezwatchlist.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.ezwatchlist.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.ezwatchlist.commons.core.messages.SearchMessages;
import seedu.ezwatchlist.logic.commands.EditCommand;
import seedu.ezwatchlist.logic.commands.SearchCommand;

public class SearchCommandParserTest {
    /*
    private SearchCommandParser parser = new SearchCommandParser();
    private static final String showName = "Once upon a time in Hollywood";
    private static

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "    ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, SearchMessages.MESSAGE_USAGE));
    }

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, NAME_DESC_ANNABELLE, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", EditCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_validArgs_returnsFindCommand() {
        // no leading and trailing whitespaces
        HashMap<SearchKey, List<String>> searchHash = new HashMap<>();
        ArrayList<String> nameList = new ArrayList<>();
        nameList.add("Alice");
        searchHash.put(SearchKey.KEY_NAME, nameList);
        SearchCommand expectedSearchCommand =
                new SearchCommand(searchHash);
        assertParseSuccess(parser, "Alice", expectedSearchCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n Alice \n", expectedSearchCommand);
    }*/

}
