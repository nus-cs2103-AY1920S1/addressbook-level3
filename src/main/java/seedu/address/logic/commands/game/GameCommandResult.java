package seedu.address.logic.commands.game;

import seedu.address.logic.commands.CommandResult;
import seedu.address.model.card.Card;
import seedu.address.statistics.GameDataPoint;

public abstract class GameCommandResult extends CommandResult {

    private final boolean isFinishedGame;
    private final boolean isExitGame; // field may be used to implement stopGame command
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
}
