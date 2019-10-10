package seedu.address.logic;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.GuessCommand;
import seedu.address.logic.commands.StartCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.game.Game;

/**
 * Represents the game logic. todo give a more descriptive comment
 */
public class GameLogic {

    private Model model;
    private Command gameCommand;
    private Game game;

    public GameLogic(Model model, Command gameCommand) {
        this.model = model;
        this.game = model.getGame();
        this.gameCommand = gameCommand;
    }

    /**
     * Executes {@code gameCommand} and returns the resulting {@code CommandResult}.
     * @throws CommandException If the executed command results in an exception.
     */
    public CommandResult process() throws CommandException {
        CommandResult commandResult;
        if (gameCommand instanceof StartCommand) {
            commandResult = gameCommand.execute(model);
            this.game = model.getGame();

            return commandResult;
        } else if (gameCommand instanceof GuessCommand) {
            if (game.isOver()) {
                commandResult = new CommandResult("The Game has ended."
                        + "\n"
                        + "Type 'start' to try again!");
                return commandResult;
            }

            commandResult = gameCommand.execute(model);

            game.moveToNextCard();

            if (game.isOver()) {
                commandResult = new CommandResult(commandResult.getFeedbackToUser()
                        + "\n"
                        + "GAME OVER!!!");
                return commandResult;
            }

            String nextQuestionToShow = game.showCurrQuestion();

            commandResult = new CommandResult(commandResult.getFeedbackToUser()
                    + "\n" + nextQuestionToShow, true);
            return commandResult;
        } else {
            return null;
        }
    }

}
