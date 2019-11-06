package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_TIE_BREAK;
import static seedu.address.commons.core.Messages.MISSING_TIEBREAK_METHODS;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TIEBREAK_METHOD;
import static seedu.address.logic.commands.CommandTestUtil.MISSING_TIEBREAK_METHOD;
import static seedu.address.logic.commands.CommandTestUtil.TIE_BREAK_DESC_RANDOM;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.ArrayList;
import java.util.Comparator;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.leaderboardcommand.LeaderboardCommand;
import seedu.address.logic.commands.leaderboardcommand.ShowLeaderboardWithRandomCommand;
import seedu.address.logic.commands.leaderboardcommand.ShowSimpleLeaderboardCommand;
import seedu.address.model.entity.Team;

class ShowLeaderBoardCommandParserTest {

    private ShowLeaderBoardCommandParser parser = new ShowLeaderBoardCommandParser();


    @Test
    void parse_invalidUserInput_failure() {
        // Additional input which is not tiebreak methods.
        assertParseFailure(parser, "hello there",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, LeaderboardCommand.MESSAGE_USAGE));

        // Tiebreak method specified does not exist
        assertParseFailure(parser, INVALID_TIEBREAK_METHOD, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                MESSAGE_INVALID_TIE_BREAK + "yes"));

        // Tiebreak method not mentioned after prefix
        assertParseFailure(parser, MISSING_TIEBREAK_METHOD, MISSING_TIEBREAK_METHODS);
    }

    @Test
    void parse_validfUserInput_success() {
        ArrayList<Comparator<Team>> comparators = new ArrayList<>();
        assertParseSuccess(parser, "", new ShowSimpleLeaderboardCommand(comparators));

        assertParseSuccess(parser, TIE_BREAK_DESC_RANDOM, new ShowLeaderboardWithRandomCommand(comparators));
    }
}
