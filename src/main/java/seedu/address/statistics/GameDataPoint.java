package seedu.address.statistics;

import seedu.address.model.game.Guess;

import java.util.Optional;

public class GameDataPoint {

    private final GameDataType dataType;
    private final long millisTaken;
    private final Optional<Guess> guess;

    public static GameDataPoint createSkipData(long millisTaken) {
        return new GameDataPoint(GameDataType.SKIP,
                Optional.empty(),
                millisTaken);
    }

    public static GameDataPoint createGuessData(Guess guess, long millisTaken) {
        return new GameDataPoint(GameDataType.GUESS,
                Optional.of(guess),
                millisTaken);
    }

    private GameDataPoint(GameDataType dataType, Optional<Guess> guess, long millisTaken) {
        this.dataType = dataType;
        this.guess = guess;
        this.millisTaken = millisTaken;
    }

    public GameDataType getDataType() {
        return dataType;
    }

    @Override
    public String toString() {
        return dataType + " - " + guess + ": " + millisTaken + "ms";
    }
}