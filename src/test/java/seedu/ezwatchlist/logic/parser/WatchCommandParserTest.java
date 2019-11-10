package seedu.ezwatchlist.logic.parser;

import static seedu.ezwatchlist.commons.core.messages.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.ezwatchlist.logic.commands.CommandTestUtil.CURRENT_TAB_WATCHED_TAB;
import static seedu.ezwatchlist.logic.commands.CommandTestUtil.EPISODES_DESC_BOB_THE_BUILDER;
import static seedu.ezwatchlist.logic.parser.CliSyntax.PREFIX_NUM_OF_EPISODES;
import static seedu.ezwatchlist.logic.parser.CliSyntax.PREFIX_NUM_OF_SEASONS;
import static seedu.ezwatchlist.logic.parser.CommandParserTestUtil.assertParseFailure;

import org.junit.jupiter.api.Test;

import seedu.ezwatchlist.logic.commands.WatchCommand;

public class WatchCommandParserTest {

    private static final String SEASONS_EMPTY = " " + PREFIX_NUM_OF_SEASONS;
    private static final String EPISODES_EMPTY = " " + PREFIX_NUM_OF_EPISODES;

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, WatchCommand.MESSAGE_USAGE);

    private WatchCommandParser parser = new WatchCommandParser();

    @Test
    public void parse_missingIndex_failure() {
        // no index specified
        assertParseFailure(parser, EPISODES_DESC_BOB_THE_BUILDER, MESSAGE_INVALID_FORMAT, CURRENT_TAB_WATCHED_TAB);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + EPISODES_DESC_BOB_THE_BUILDER, MESSAGE_INVALID_FORMAT,
                CURRENT_TAB_WATCHED_TAB);

        // zero index
        assertParseFailure(parser, "0" + EPISODES_DESC_BOB_THE_BUILDER, MESSAGE_INVALID_FORMAT,
                CURRENT_TAB_WATCHED_TAB);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT, CURRENT_TAB_WATCHED_TAB);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT, CURRENT_TAB_WATCHED_TAB);
    }

    @Test
    public void parse_invalidEpisode_failure() {
        // negative episode number
        assertParseFailure(parser, "1 " + "e/-3", MESSAGE_INVALID_FORMAT, CURRENT_TAB_WATCHED_TAB);
    }

    @Test
    public void parse_invalidSeason_failure() {
        // negative season number
        assertParseFailure(parser, "1 " + "s/-3", MESSAGE_INVALID_FORMAT, CURRENT_TAB_WATCHED_TAB);
    }
}
