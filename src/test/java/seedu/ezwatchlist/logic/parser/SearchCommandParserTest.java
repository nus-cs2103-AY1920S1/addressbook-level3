package seedu.ezwatchlist.logic.parser;

import static seedu.ezwatchlist.commons.core.messages.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.ezwatchlist.logic.commands.CommandTestUtil.VALID_SHOW_NAME_ANNABELLE;
import static seedu.ezwatchlist.logic.commands.CommandTestUtil.CURRENT_TAB_SEARCH_TAB;
import static seedu.ezwatchlist.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.ezwatchlist.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.ezwatchlist.commons.core.messages.Messages;
import seedu.ezwatchlist.commons.core.messages.SearchMessages;
import seedu.ezwatchlist.logic.commands.EditCommand;
import seedu.ezwatchlist.logic.commands.SearchCommand;

public class SearchCommandParserTest {
    private  SearchCommandParser parser = new SearchCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "    ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SearchMessages.MESSAGE_USAGE), CURRENT_TAB_SEARCH_TAB);
    }

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_SHOW_NAME_ANNABELLE, MESSAGE_INVALID_COMMAND_FORMAT, CURRENT_TAB_SEARCH_TAB);

        // no field specified
        assertParseFailure(parser, "1", EditCommand.MESSAGE_NOT_EDITED, CURRENT_TAB_SEARCH_TAB);

        // no index and no field specified
        assertParseFailure(parser, "", Messages.MESSAGE_INVALID_COMMAND_FORMAT, CURRENT_TAB_SEARCH_TAB);
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
        assertParseSuccess(parser, "Alice", expectedSearchCommand, CURRENT_TAB_SEARCH_TAB);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n Alice \n", expectedSearchCommand, CURRENT_TAB_SEARCH_TAB);
    }

}
