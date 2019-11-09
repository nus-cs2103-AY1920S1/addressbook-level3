package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_TIE_BREAK;
import static seedu.address.commons.core.Messages.MISSING_TIEBREAK_METHODS;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_SUBJECT_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TIEBREAK_METHOD;
import static seedu.address.logic.commands.CommandTestUtil.MISSING_TIEBREAK_METHOD;
import static seedu.address.logic.commands.CommandTestUtil.SUBJECT_DESC_ALFRED;
import static seedu.address.logic.commands.CommandTestUtil.SUBJECT_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.TIE_BREAK_DESC_RANDOM;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.ArrayList;
import java.util.Comparator;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.leaderboardcommand.LeaderboardCommand;
import seedu.address.logic.commands.leaderboardcommand.LeaderboardWithRandomCommand;
import seedu.address.logic.commands.leaderboardcommand.SimpleLeaderboardCommand;
import seedu.address.model.entity.SubjectName;
import seedu.address.model.entity.Team;

class LeaderBoardCommandParserTest {

    private LeaderboardCommandParser parser = new LeaderboardCommandParser();

    @Test
    void parse_invalidUserInput_failure() {
        // Additional input which is not tiebreak methods or subject.
        assertParseFailure(parser, "hello there",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, LeaderboardCommand.MESSAGE_USAGE));

        // Tiebreak method specified does not exist
        assertParseFailure(parser, INVALID_TIEBREAK_METHOD, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                MESSAGE_INVALID_TIE_BREAK + "yes"));

        // Subject specified does not exist
        assertParseFailure(parser, INVALID_SUBJECT_DESC, SubjectName.MESSAGE_CONSTRAINTS);

        // Invalid subject before invalid tiebreak - invalid subject caught
        assertParseFailure(parser, INVALID_SUBJECT_DESC + " " + INVALID_TIEBREAK_METHOD,
                SubjectName.MESSAGE_CONSTRAINTS);

        // Invalid tiebreak before invalid subject - invalid subject caught
        assertParseFailure(parser, INVALID_TIEBREAK_METHOD + " " + INVALID_SUBJECT_DESC,
                SubjectName.MESSAGE_CONSTRAINTS);

        // Tiebreak method not mentioned after prefix
        assertParseFailure(parser, MISSING_TIEBREAK_METHOD, MISSING_TIEBREAK_METHODS);
    }

    @Test
    void parse_validfUserInput_success() {
        ArrayList<Comparator<Team>> comparators = new ArrayList<>();

        // Parse command with only subject provided
        assertParseSuccess(parser, SUBJECT_DESC_ALFRED, new SimpleLeaderboardCommand(comparators, SubjectName.HEALTH));

        // Parse command with tiebreak method provided
        assertParseSuccess(parser, TIE_BREAK_DESC_RANDOM, new LeaderboardWithRandomCommand(comparators, null));

        // Parse command with tiebreak method and subject provided
        assertParseSuccess(parser, TIE_BREAK_DESC_RANDOM + " " + SUBJECT_DESC_AMY,
                new LeaderboardWithRandomCommand(comparators, SubjectName.ENVIRONMENTAL));

    }
}
