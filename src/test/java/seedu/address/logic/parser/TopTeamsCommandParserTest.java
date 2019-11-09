package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_TIE_BREAK;
import static seedu.address.commons.core.Messages.MISSING_TIEBREAK_METHODS;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_SUBJECT_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TIEBREAK_METHOD;
import static seedu.address.logic.commands.CommandTestUtil.MISSING_TIEBREAK_METHOD;
import static seedu.address.logic.commands.CommandTestUtil.SUBJECT_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.TIE_BREAK_DESC_RANDOM;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.ArrayList;
import java.util.Comparator;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.topteamscommand.SimpleTopTeamsCommand;
import seedu.address.logic.commands.topteamscommand.TopTeamsCommand;
import seedu.address.logic.commands.topteamscommand.TopTeamsRandomCommand;
import seedu.address.model.entity.SubjectName;
import seedu.address.model.entity.Team;

class TopTeamsCommandParserTest {

    private TopTeamsCommandParser parser = new TopTeamsCommandParser();

    @Test
    void parse_invalidUserInput_failure() {
        // Additional input which is not an integer.
        assertParseFailure(parser, "hello there",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, TopTeamsCommand.INVALID_VALUE_WARNING));

        // Additional input which is not a non-zero positive integer.
        assertParseFailure(parser, "-10",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, TopTeamsCommand.INVALID_VALUE_WARNING));

        // Additional input which is not a non-zero integer.
        assertParseFailure(parser, "0",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, TopTeamsCommand.INVALID_VALUE_WARNING));

        // Tiebreak method specified does not exist
        assertParseFailure(parser, "5 " + INVALID_TIEBREAK_METHOD, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                MESSAGE_INVALID_TIE_BREAK + "yes"));

        // Subject specified does not exist
        assertParseFailure(parser, "5 " + INVALID_SUBJECT_DESC, SubjectName.MESSAGE_CONSTRAINTS);

        // Tiebreak method not mentioned after prefix
        assertParseFailure(parser, "5 " + MISSING_TIEBREAK_METHOD, MISSING_TIEBREAK_METHODS);

        // Tiebreak method not mentioned after prefix with valid subject name
        assertParseFailure(parser, "5 " + SUBJECT_DESC_AMY + " "
                + MISSING_TIEBREAK_METHOD, MISSING_TIEBREAK_METHODS);

        // Invalid subject name after invalid number - invalid number caught
        assertParseFailure(parser, "-5 " + INVALID_SUBJECT_DESC,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, TopTeamsCommand.INVALID_VALUE_WARNING));

        // Invalid subject name after invalid tiebreak methods - invalid subject caught
        assertParseFailure(parser, "5 " + INVALID_TIEBREAK_METHOD + " " + INVALID_SUBJECT_DESC,
               SubjectName.MESSAGE_CONSTRAINTS);

        // Invalid subject name before invalid tiebreak methods - invalid subject caught
        assertParseFailure(parser, "5 " + INVALID_SUBJECT_DESC + " " + INVALID_TIEBREAK_METHOD,
                SubjectName.MESSAGE_CONSTRAINTS);
    }

    @Test
    void parse_validfUserInput_success() {
        ArrayList<Comparator<Team>> comparators = new ArrayList<>();

        // No subject or tiebreak
        assertParseSuccess(parser, "5", new SimpleTopTeamsCommand(5, comparators, null));

        // No subject but with tiebreak
        assertParseSuccess(parser, "5" + TIE_BREAK_DESC_RANDOM,
                new TopTeamsRandomCommand(5, comparators, null));

        // No tiebreak but with subject
        assertParseSuccess(parser, "5" + SUBJECT_DESC_AMY,
                new SimpleTopTeamsCommand(5, comparators, SubjectName.ENVIRONMENTAL));

        // With tiebreak and Subject
        assertParseSuccess(parser, "5" + SUBJECT_DESC_AMY + TIE_BREAK_DESC_RANDOM,
                new TopTeamsRandomCommand(5, comparators, SubjectName.ENVIRONMENTAL));
    }
}
