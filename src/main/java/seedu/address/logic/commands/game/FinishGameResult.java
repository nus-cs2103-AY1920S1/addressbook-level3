package seedu.address.logic.commands.game;

import seedu.address.statistics.GameDataPoint;
import seedu.address.statistics.GameStatistics;

public class FinishGameResult extends GameCommandResult {

    private GameStatistics statistics;

    public FinishGameResult(GameStatistics statistics) {
        super("Game is finished!", true);
        this.statistics = statistics;
    }

    public GameStatistics getStatistics() {
        return statistics;
    }

    @Override
    public GameDataPoint getGameDataPoint(long millisElapsed) {
        return null;
    }
}
