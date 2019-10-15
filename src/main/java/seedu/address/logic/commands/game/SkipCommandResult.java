package seedu.address.logic.commands.game;

import seedu.address.model.card.Card;
import seedu.address.statistics.GameDataPoint;

/**
 * Represents the command result returned by {@code GuessCommand}.
 * This class is needed to pass some info to the {@code GameManager} to populate the {@code GameStatistics}.
 */
public class SkipCommandResult extends GameCommandResult {

    private static final String MESSAGE_SKIPPED = "Word skipped!";

    public SkipCommandResult(Card card, String additionalMsg, boolean isFinished) {
        super(card,
                MESSAGE_SKIPPED
                + "\n"
                + additionalMsg,
                isFinished,
                false);
    }


    @Override
    public GameDataPoint getGameDataPoint(long millisElapsed) {
        return GameDataPoint.createSkipData(millisElapsed);
    }
}
