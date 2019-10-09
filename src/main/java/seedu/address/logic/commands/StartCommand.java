/*
Step 14.

It has to override execute() from command interface.

Interacts with Game interface.
Extends to Step 15 in Game.java
 */
package seedu.address.logic.commands;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.game.Game;
import seedu.address.model.util.SampleDataUtil;
import seedu.address.model.wordbank.WordBank;

/**
 * Start the game.
 */
public class StartCommand extends AppCommand {

    public static final String COMMAND_WORD = "start";
    public static final String MESSAGE_GAME_START_SUCCESS = "Sample game session in progress, ";
    public static final String FIRST_QUESTION_MESSAGE = "guess the keyword! ";
    private final Index targetIndex;
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Starts the word bank identified by the index number used in the displayed person list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public StartCommand(Index index) {
        this.targetIndex = index;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        WordBank wordBank = SampleDataUtil.getSampleWordBank();
        Game newGame = new Game(wordBank);
        model.setGame(newGame);
        String currQuestion = model.getGame().showCurrQuestion();
        CommandResult result = new CommandResult(
                MESSAGE_GAME_START_SUCCESS + FIRST_QUESTION_MESSAGE
                        + "\n"
                        + currQuestion
                , true);
        result.setSwitchMode();
        return result;
    }
}
