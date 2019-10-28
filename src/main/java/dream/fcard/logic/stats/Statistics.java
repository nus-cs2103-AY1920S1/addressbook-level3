package dream.fcard.logic.stats;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * A Statistics class which stores the creation date and time, and the
 */
public abstract class Statistics {
    protected final LocalDateTime createdDate;
    protected final DateTimeFormatter printFormat = DateTimeFormatter.RFC_1123_DATE_TIME;


    public Statistics() {
        this.createdDate = LocalDateTime.now();
    }

}
