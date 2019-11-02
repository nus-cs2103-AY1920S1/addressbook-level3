package seedu.address.logic.parser.scorecommandparser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandAllocatorTestUtil.assertAllocatorFailure;
import static seedu.address.logic.parser.CommandAllocatorTestUtil.assertAllocatorSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.scorecommand.AddScoreCommand;
import seedu.address.logic.commands.scorecommand.ScoreCommand;
import seedu.address.logic.commands.scorecommand.SetScoreCommand;
import seedu.address.logic.commands.scorecommand.SubtractScoreCommand;
import seedu.address.model.entity.Id;
import seedu.address.model.entity.PrefixType;
import seedu.address.model.entity.Score;

class ScoreCommandAllocatorTest {

    private static final Id VALID_TEAM_ID = new Id(PrefixType.T, 2);
    private static final Score VALID_SCORE = new Score(45);
    private static final Score VALID_RESET_SCORE = new Score(0);
    private ScoreCommandAllocator scoreCommandAllocator = new ScoreCommandAllocator();

    @Test
    void allocate_correctUserInput_success() {
        String setCommand = "set T-2 45";
        String addCommand = "add T-2 45";
        String resetCommand = "reset T-2";
        String subCommand = "sub T-2 45";

        assertAllocatorSuccess(scoreCommandAllocator, setCommand, new SetScoreCommand(VALID_TEAM_ID, VALID_SCORE));
        assertAllocatorSuccess(scoreCommandAllocator, addCommand, new AddScoreCommand(VALID_TEAM_ID, VALID_SCORE));
        assertAllocatorSuccess(scoreCommandAllocator, resetCommand,
                new SetScoreCommand(VALID_TEAM_ID, VALID_RESET_SCORE));
        assertAllocatorSuccess(scoreCommandAllocator, subCommand, new SubtractScoreCommand(VALID_TEAM_ID, VALID_SCORE));
    }

    @Test
    void allocate_incorrectUserInput_failure() {
        // Score method not mentioned
        assertAllocatorFailure(scoreCommandAllocator, "T-2 45",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ScoreCommand.MESSAGE_USAGE));

        // Score method does not exist
        assertAllocatorFailure(scoreCommandAllocator, "delete T-2 45",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ScoreCommand.MESSAGE_USAGE));

        // User input is empty
        assertAllocatorFailure(scoreCommandAllocator, "",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ScoreCommand.MESSAGE_USAGE));
    }
}
