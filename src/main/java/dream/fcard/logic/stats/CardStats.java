package dream.fcard.logic.stats;

import java.time.LocalDateTime;

public class CardStats extends Statistics {
    private int timesUsed;
    private LocalDateTime lastModified;

    public CardStats() {
        super();
        lastModified = LocalDateTime.now();
        this.timesUsed = 0;
    }

    /**
     * Resets the last modified date to current date.
     */
    public void setLastModified() {
        lastModified = LocalDateTime.now();
    }


}
