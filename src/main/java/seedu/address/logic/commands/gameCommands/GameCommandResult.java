package seedu.address.logic.commands.gameCommands;

import java.util.Optional;

import seedu.address.logic.commands.CommandResult;
import seedu.address.model.card.Card;
import seedu.address.statistics.GameDataPoint;

/**
 * Represents the command result returned by a game command.
 * This class is needed to pass some info to the {@code GameManager} to populate the {@code GameStatistics}.
 */
public abstract class GameCommandResult extends CommandResult {

    /** Game should finish and open game result display **/
    private final boolean isFinishedGame;

    /** The card displayed when command is executed **/
    private final Optional<Card> card;

    public GameCommandResult(Card card, String feedback, boolean isFinishedGame) {
        super(feedback, !isFinishedGame);
        this.isFinishedGame = isFinishedGame;
        this.card = Optional.of(card);
    }

    public GameCommandResult(String feedback, boolean isFinishedGame) {
        super(feedback, !isFinishedGame);
        this.isFinishedGame = isFinishedGame;
        this.card = Optional.empty();
    }

    /**
     * Used to populate the {@code GameStatistics}.
     */
    public abstract GameDataPoint getGameDataPoint(long millisElapsed);

    public Optional<Card> getCard() {
        return card;
    }

    public boolean isFinishedGame() {
        return isFinishedGame;
    }
}
