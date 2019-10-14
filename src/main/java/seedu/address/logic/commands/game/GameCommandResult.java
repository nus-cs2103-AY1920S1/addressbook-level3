package seedu.address.logic.commands.game;

import seedu.address.logic.commands.CommandResult;
import seedu.address.model.card.Card;
import seedu.address.statistics.GameDataPoint;

public abstract class GameCommandResult extends CommandResult {

    /** Game should finish and open game result display **/
    private final boolean isFinishedGame;

    /** Game should exit to home **/
    private final boolean isExitGame; // field may be used to implement stopGame command

    /** The card displayed when command is executed **/
    private final Card card;

    public GameCommandResult(Card card, String feedback, boolean isFinishedGame, boolean isExitGame) {
        super(feedback, !isFinishedGame && !isExitGame);
        this.isFinishedGame = isFinishedGame;
        this.isExitGame = isExitGame;
        this.card = card;
    }

    public abstract GameDataPoint getGameDataPoint(long millisElapsed);

    public Card getCard() {
        return card;
    }

    public boolean isFinishedGame() {
        return isFinishedGame;
    }

    public boolean isExitGame() {
        return isExitGame;
    }
}
