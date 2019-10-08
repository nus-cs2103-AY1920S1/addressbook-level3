/*
Step 14.

It has to override execute() from command interface.

Interacts with Game interface.
Extends to Step 15 in Game.java
 */
package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.game.Game;
import seedu.address.model.util.SampleDataUtil;
import seedu.address.model.wordbank.WordBank;

/**
 * Start the game.
 */
public class StartCommand extends GameCommand {

    public static final String COMMAND_WORD = "start";
    public static final String MESSAGE_GAME_START_SUCCESS = "Sample game session in progress, ";
    public static final String FIRST_QUESTION_MESSAGE = "guess the keyword! ";

    public StartCommand() {

    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        WordBank wordBank = SampleDataUtil.getSampleWordBank();
        Game newGame = new Game(wordBank);
        model.setGame(newGame);
        String currQuestion = model.getGame().showCurrQuestion();
        return new CommandResult(
                MESSAGE_GAME_START_SUCCESS + FIRST_QUESTION_MESSAGE
                        + "\n"
                        + currQuestion,
                true);
    }
}
