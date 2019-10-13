package seedu.address.commons.util;

import static java.util.Calendar.DAY_OF_WEEK;
import static java.util.Calendar.LONG;

import java.util.Calendar;
import java.util.Locale;

import seedu.address.logic.commands.StatisticType;

/**
 * Payload class to send user input to logic Manager via commandResult
 */
public class StatsPayload {

    private final Calendar startingDate;
    private final Calendar endingDate;
    private final StatisticType statisticType;
    /**
     * Constructs a {@code StatsPayload} with the specified {@code startingDate}, {@code statisticType}
     * and specified {@Code endingDate},
     * and set other fields to their default value.
     * @param startingDate starting date of query
     * @param endingDate ending date of query
     */
    public StatsPayload(Calendar startingDate, Calendar endingDate,
                        StatisticType statisticType) {
        this.startingDate = startingDate;
        this.endingDate = endingDate;
        this.statisticType = statisticType;
    }

    public Calendar getStartingDate() {
        return this.startingDate;
    }

    public Calendar getEndingDate() {
        return this.endingDate;
    }

    public StatisticType getStatisticType() {
        return this.statisticType;
    }

    public String displayStartingDate() {
        StringBuilder display = new StringBuilder();
        display.append(this.startingDate.get(Calendar.YEAR));
        display.append(this.startingDate.get(Calendar.DATE));
        return display.toString();
    }

    public  String displayEndingDate() {
        StringBuilder display = new StringBuilder();
        display.append(this.endingDate.get(Calendar.YEAR));
        display.append(this.endingDate.get(Calendar.DATE));
        return display.toString();
    }


}
