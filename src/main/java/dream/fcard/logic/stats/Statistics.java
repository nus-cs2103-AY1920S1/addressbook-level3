package dream.fcard.logic.stats;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javafx.scene.Node;

/**
 * A Statistics class which stores the creation date and time, and the
 */
public class Statistics {
    private final LocalDateTime dateTime;
    private final DateTimeFormatter printFormat = DateTimeFormatter.RFC_1123_DATE_TIME;
    private int timesAccessed;
    private LocalDateTime lastModified;

    public Statistics() {
        this.dateTime = LocalDateTime.now();
        lastModified = dateTime;
        this.timesAccessed = 0;
    }

    /**
     * @return The Statistics of a card or deck in the GUI.
     */
    public Node showStats() {
        //Further implementation needed.

        return null;
    }

}
