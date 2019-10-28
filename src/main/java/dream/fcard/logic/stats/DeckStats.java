package dream.fcard.logic.stats;

import java.time.LocalDateTime;

public class DeckStats extends Statistics {
    private int timesAccessed;
    private LocalDateTime lastModified;
    private int bestScore;

    public DeckStats() {
        super();
        lastModified = LocalDateTime.now();
        this.timesAccessed = 0;
    }

    /**
     * Resets the last modified date to current date.
     */
    public void setLastModified() {
        lastModified = LocalDateTime.now();
    }

    public void setBestScore(int bestScore) {
        this.bestScore = bestScore;
    }
}
