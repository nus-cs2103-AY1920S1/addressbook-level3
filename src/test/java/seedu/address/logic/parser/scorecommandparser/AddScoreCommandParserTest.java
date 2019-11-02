package seedu.address.logic.parser.scorecommandparser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_INDEX;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIds.ID_THIRD_TEAM;
import static seedu.address.testutil.TypicalScores.SCORE_TWENTY;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.scorecommand.AddScoreCommand;
import seedu.address.model.entity.Score;

class AddScoreCommandParserTest {

    private AddScoreCommandParser addScoreCommandParser = new AddScoreCommandParser();

    @Test
    void parse_incorrectUserInput_failure() {
        // Id not specified
        assertParseFailure(addScoreCommandParser, "45", MESSAGE_INVALID_INDEX);

        // Empty user input
        assertParseFailure(addScoreCommandParser, "",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddScoreCommand.MESSAGE_USAGE));

        // Score not specified
        assertParseFailure(addScoreCommandParser, "T-2", Score.MESSAGE_CONSTRAINTS);

        // Score out of bound
        assertParseFailure(addScoreCommandParser, "T-2 200", Score.MESSAGE_CONSTRAINTS);

        // Score out of bound
        assertParseFailure(addScoreCommandParser, "T-2 -200", Score.MESSAGE_CONSTRAINTS);

        // Invalid Id format - Belongs to a mentor
        assertParseFailure(addScoreCommandParser, "M-2", MESSAGE_INVALID_INDEX);
    }

    @Test
    void parse_correctUserInput_success() {
        assertParseSuccess(addScoreCommandParser, "T-3 20", new AddScoreCommand(ID_THIRD_TEAM, SCORE_TWENTY));

        // With leading and trailing whitespaces
        assertParseSuccess(addScoreCommandParser, " T-3 20 ",
                new AddScoreCommand(ID_THIRD_TEAM, SCORE_TWENTY));

    }
}
