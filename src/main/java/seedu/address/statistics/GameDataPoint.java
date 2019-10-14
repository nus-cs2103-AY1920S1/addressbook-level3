package seedu.address.statistics;

import java.util.Optional;

import seedu.address.model.game.Guess;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

/**
 * Represents a data point of a game, consisting of an action (GUESS or SKIP) and the time elapsed.
 * The collection of {@code GameDataPoint} is located in {@code GameStatistics}.
 */
public class GameDataPoint {

    private final GameDataType dataType;

    /** The millis taken after the current card is shown**/
    private final long millisTaken;

    /** Optional is empty for a SKIP data point, and contains the corresponding guess for GUESS data point **/
    private final Optional<Guess> guess;

    private GameDataPoint(GameDataType dataType, Optional<Guess> guess, long millisTaken) {
        requireAllNonNull(dataType, guess);
        if (millisTaken < 0) {
            throw new AssertionError("Millis taken cannot be negative");
        }
        this.dataType = dataType;
        this.guess = guess;
        this.millisTaken = millisTaken;
    }

    /**
     * Creates a skip data point.
     * @param millisTaken The time the skip command occurs after the card is displayed.
     */
    public static GameDataPoint createSkipData(long millisTaken) {
        return new GameDataPoint(GameDataType.SKIP,
                Optional.empty(),
                millisTaken);
    }

    /**
     * Creates a guess data point.
     * @param guess The corresponding guess.
     * @param millisTaken The time the guess command occurs after the card is displayed.
     * @return
     */
    public static GameDataPoint createGuessData(Guess guess, long millisTaken) {
        return new GameDataPoint(GameDataType.GUESS,
                Optional.of(guess),
                millisTaken);
    }

    public Optional<Guess> getGuess() {
        return guess;
    }

    public long getMillisTaken() {
        return millisTaken;
    }

    @Override
    public String toString() {
        return dataType + " - " + guess + ": " + millisTaken + "ms";
    }

}
