package seedu.address.logic.parser.scorecommandparser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_TEAM_DISPLAYED_INDEX;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import static seedu.address.testutil.TypicalIds.ID_THIRD_TEAM;
import static seedu.address.testutil.TypicalScores.SCORE_TWENTY;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.scorecommand.SetScoreCommand;
import seedu.address.model.entity.Score;

class SetScoreCommandParserTest {

    private SetScoreCommandParser setScoreCommandParser = new SetScoreCommandParser();

    @Test
    void parse_incorrectUserInput_failure() {
        // Id not specified
        assertParseFailure(setScoreCommandParser, "45",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SetScoreCommand.MESSAGE_USAGE));

        // Empty user input
        assertParseFailure(setScoreCommandParser, "",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SetScoreCommand.MESSAGE_USAGE));

        // Score not specified
        assertParseFailure(setScoreCommandParser, "T-2",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SetScoreCommand.MESSAGE_USAGE));

        // Score out of bound
        assertParseFailure(setScoreCommandParser, "T-2 200", Score.MESSAGE_CONSTRAINTS);

        // Score out of bound
        assertParseFailure(setScoreCommandParser, "T-2 -200", Score.MESSAGE_CONSTRAINTS);

        // Invalid Id format - Belongs to a mentor
        assertParseFailure(setScoreCommandParser, "M-2 30", MESSAGE_INVALID_TEAM_DISPLAYED_INDEX);

        // Invalid Id format and invalid Score - Only invalid Id caught
        assertParseFailure(setScoreCommandParser, "M-2 -30", MESSAGE_INVALID_TEAM_DISPLAYED_INDEX);
    }

    @Test
    void parse_correctUserInput_success() {
        assertParseSuccess(setScoreCommandParser, "T-3 20", new SetScoreCommand(ID_THIRD_TEAM, SCORE_TWENTY));

        // With leading and trailing whitespaces
        assertParseSuccess(setScoreCommandParser, " T-3 20 ",
                new SetScoreCommand(ID_THIRD_TEAM, SCORE_TWENTY));
    }
}
