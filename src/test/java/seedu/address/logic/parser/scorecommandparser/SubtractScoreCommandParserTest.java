package seedu.address.logic.parser.scorecommandparser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_TEAM_DISPLAYED_INDEX;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIds.ID_THIRD_TEAM;
import static seedu.address.testutil.TypicalScores.SCORE_TWENTY;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.scorecommand.SubtractScoreCommand;
import seedu.address.model.entity.Score;

class SubtractScoreCommandParserTest {

    private SubtractScoreCommandParser subtractScoreCommandParser = new SubtractScoreCommandParser();

    @Test
    void parse_incorrectUserInput_failure() {
        // Id not specified
        assertParseFailure(subtractScoreCommandParser, "45",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SubtractScoreCommand.MESSAGE_USAGE));

        // Empty user input
        assertParseFailure(subtractScoreCommandParser, "",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SubtractScoreCommand.MESSAGE_USAGE));

        // Score not specified
        assertParseFailure(subtractScoreCommandParser, "T-2",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SubtractScoreCommand.MESSAGE_USAGE));

        // Score out of bound
        assertParseFailure(subtractScoreCommandParser, "T-2 200", Score.MESSAGE_CONSTRAINTS);

        // Score out of bound
        assertParseFailure(subtractScoreCommandParser, "T-2 -1", Score.MESSAGE_CONSTRAINTS);

        // Invalid Id format - Belongs to a mentor
        assertParseFailure(subtractScoreCommandParser, "M-2 45", MESSAGE_INVALID_TEAM_DISPLAYED_INDEX);

        // Invalid Id format and invalid Score - Only invalid id caught
        assertParseFailure(subtractScoreCommandParser, "M-2 -45", MESSAGE_INVALID_TEAM_DISPLAYED_INDEX);
    }

    @Test
    void parse_correctUserInput_success() {
        assertParseSuccess(subtractScoreCommandParser, "T-3 20",
                new SubtractScoreCommand(ID_THIRD_TEAM, SCORE_TWENTY));

        // With leading and trailing whitespaces
        assertParseSuccess(subtractScoreCommandParser, " T-3 20 ",
                new SubtractScoreCommand(ID_THIRD_TEAM, SCORE_TWENTY));

    }
}
