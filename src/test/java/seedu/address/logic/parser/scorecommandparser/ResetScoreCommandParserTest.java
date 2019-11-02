package seedu.address.logic.parser.scorecommandparser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_TEAM_DISPLAYED_INDEX;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIds.ID_THIRD_TEAM;
import static seedu.address.testutil.TypicalScores.RESET_SCORE;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.scorecommand.ScoreCommand;
import seedu.address.logic.commands.scorecommand.SetScoreCommand;

class ResetScoreCommandParserTest {

    private ResetScoreCommandParser resetScoreCommandParser = new ResetScoreCommandParser();

    @Test
    void parse_incorrectUserInput_failure() {
        // Id not specified - Score mentioned instead
        assertParseFailure(resetScoreCommandParser, "45", MESSAGE_INVALID_TEAM_DISPLAYED_INDEX);

        // Empty user input
        assertParseFailure(resetScoreCommandParser, "",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ScoreCommand.RESET_MESSAGE_USAGE));

        // Invalid Id format - Belongs to a mentor
        assertParseFailure(resetScoreCommandParser, "M-2", MESSAGE_INVALID_TEAM_DISPLAYED_INDEX);
    }

    @Test
    void parse_correctUserInput_success() {
        assertParseSuccess(resetScoreCommandParser, "T-3",
                new SetScoreCommand(ID_THIRD_TEAM, RESET_SCORE));

        // With leading and trailing whitespaces
        assertParseSuccess(resetScoreCommandParser, " T-3 ",
                new SetScoreCommand(ID_THIRD_TEAM, RESET_SCORE));
    }
}
