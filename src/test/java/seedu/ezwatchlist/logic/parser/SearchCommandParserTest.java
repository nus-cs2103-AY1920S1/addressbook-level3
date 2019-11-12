package seedu.ezwatchlist.logic.parser;

import static seedu.ezwatchlist.logic.commands.CommandTestUtil.CURRENT_TAB_SEARCH_TAB;
import static seedu.ezwatchlist.logic.commands.CommandTestUtil.VALID_SHOW_NAME_ANNABELLE;
import static seedu.ezwatchlist.logic.commands.CommandTestUtil.VALID_TYPE_MOVIE;
import static seedu.ezwatchlist.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.ezwatchlist.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.ezwatchlist.commons.core.messages.Messages;
import seedu.ezwatchlist.commons.core.messages.SearchMessages;
import seedu.ezwatchlist.logic.commands.SearchCommand;

public class SearchCommandParserTest {
    private SearchCommandParser parser = new SearchCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "    ",
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, SearchMessages.MESSAGE_USAGE),
                CURRENT_TAB_SEARCH_TAB);
    }

    @Test
    public void parse_missingParts_failure() {
        // no prefix specified
        assertParseFailure(parser, VALID_SHOW_NAME_ANNABELLE,
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, SearchMessages.MESSAGE_USAGE),
                CURRENT_TAB_SEARCH_TAB);

        // no index and no field specified
        //assertParseFailure(parser, "", "Search", CURRENT_TAB_SEARCH_TAB);
    }

    @Test
    public void parse_validArgs_returnsSearchCommand() {
        HashMap<SearchKey, List<String>> searchHash = new HashMap<>();
        ArrayList<String> showNameList = new ArrayList<>();
        showNameList.add(VALID_SHOW_NAME_ANNABELLE);
        searchHash.put(SearchKey.KEY_NAME, showNameList);
        ArrayList<String> typeList = new ArrayList<>();
        typeList.add(VALID_TYPE_MOVIE);
        searchHash.put(SearchKey.KEY_TYPE, typeList);
        ArrayList<String> emptyList = new ArrayList<>();
        searchHash.put(SearchKey.KEY_GENRE, emptyList);
        searchHash.put(SearchKey.KEY_FROM_ONLINE, emptyList);
        searchHash.put(SearchKey.KEY_ACTOR, emptyList);
        searchHash.put(SearchKey.KEY_IS_WATCHED, emptyList);
        SearchCommand expectedSearchCommand = new SearchCommand(searchHash);

        // leading whitespaces
        assertParseSuccess(parser, "         n/Annabelle t/movie", expectedSearchCommand, CURRENT_TAB_SEARCH_TAB);
    }

}
