package seedu.address.logic.parser.scorecommandparser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_TEAM_DISPLAYED_INDEX;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIds.ID_THIRD_TEAM;
import static seedu.address.testutil.TypicalScores.RESET_SCORE;
import static seedu.address.testutil.TypicalScores.SCORE_THIRTY;
import static seedu.address.testutil.TypicalScores.SCORE_TWENTY;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.scorecommand.AddScoreCommand;
import seedu.address.logic.commands.scorecommand.ScoreCommand;
import seedu.address.logic.commands.scorecommand.SetScoreCommand;
import seedu.address.logic.commands.scorecommand.SubtractScoreCommand;
import seedu.address.model.entity.Score;

class ScoreCommandParserTest {

    private ScoreCommandParser scoreCommandParser = new ScoreCommandParser();

    @Test
    void parse_incorrectUserInput_failure() {
        // Empty user input
        assertParseFailure(scoreCommandParser, "",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ScoreCommand.MESSAGE_USAGE));

        // Method not specified
        assertParseFailure(scoreCommandParser, "T-2 45",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ScoreCommand.MESSAGE_USAGE));

        // Id not specified - Add Score Command
        assertParseFailure(scoreCommandParser, "add 45",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddScoreCommand.MESSAGE_USAGE));

        // Invalid id format
        assertParseFailure(scoreCommandParser, "add T5 45", MESSAGE_INVALID_TEAM_DISPLAYED_INDEX);

        // Invalid id - not team id
        assertParseFailure(scoreCommandParser, "add M-5 45", MESSAGE_INVALID_TEAM_DISPLAYED_INDEX);

        // Score not specified - Add Score Command
        assertParseFailure(scoreCommandParser, "add T-2",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddScoreCommand.MESSAGE_USAGE));

        // Score out of bound - too high
        assertParseFailure(scoreCommandParser, "add M-5 101", Score.MESSAGE_CONSTRAINTS);

        // Score out of bound - too low
        assertParseFailure(scoreCommandParser, "add M-5 -1", Score.MESSAGE_CONSTRAINTS);

        // Id not specified - Subtract Score Command
        assertParseFailure(scoreCommandParser, "sub 45",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SubtractScoreCommand.MESSAGE_USAGE));

        // Score not specified - Subtract Score Command
        assertParseFailure(scoreCommandParser, "sub T-3",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SubtractScoreCommand.MESSAGE_USAGE));

        // Id not specified - Set Score Command
        assertParseFailure(scoreCommandParser, "set 45",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SetScoreCommand.MESSAGE_USAGE));

        // Score not specified - Set Score Command
        assertParseFailure(scoreCommandParser, "set T-3",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SetScoreCommand.MESSAGE_USAGE));

        // Id not specified - Reset Score Command
        assertParseFailure(scoreCommandParser, "reset",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SetScoreCommand.RESET_MESSAGE_USAGE));
    }

    @Test
    void parse_correctUserInput_success() {
        // Add Score command test
        assertParseSuccess(scoreCommandParser, "add T-3 20",
                new AddScoreCommand(ID_THIRD_TEAM, SCORE_TWENTY));

        // Add Score - with leading and trailing whitespaces
        assertParseSuccess(scoreCommandParser, " add T-3 20 ",
                new AddScoreCommand(ID_THIRD_TEAM, SCORE_TWENTY));

        // Set Score command test
        assertParseSuccess(scoreCommandParser, "set T-3 20",
                new SetScoreCommand(ID_THIRD_TEAM, SCORE_TWENTY));

        // Set Score - with leading and trailing whitespaces
        assertParseSuccess(scoreCommandParser, " set T-3 20 ",
                new SetScoreCommand(ID_THIRD_TEAM, SCORE_TWENTY));

        // Reset Score command test
        assertParseSuccess(scoreCommandParser, "reset T-3",
                new SetScoreCommand(ID_THIRD_TEAM, RESET_SCORE));

        // Set Score - with leading and trailing whitespaces
        assertParseSuccess(scoreCommandParser, " reset T-3 ",
                new SetScoreCommand(ID_THIRD_TEAM, RESET_SCORE));

        // Subtract Score command test
        assertParseSuccess(scoreCommandParser, "sub T-3 30",
                new SubtractScoreCommand(ID_THIRD_TEAM, SCORE_THIRTY));

        // Subtract Score - with leading and trailing whitespaces
        assertParseSuccess(scoreCommandParser, " sub T-3 30 ",
                new SubtractScoreCommand(ID_THIRD_TEAM, SCORE_THIRTY));
    }
}
