package seedu.address.statistic;

import java.util.Calendar;

import seedu.address.commons.util.StringUtil;
import seedu.address.logic.commands.statisticcommand.StatisticType;

/**
 * Payload class to send user input to logic Manager via commandResult
 */
public class StatsPayload {

    private final boolean isDefaultQuery;

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
        if (startingDate.equals(StatsParseUtil.MIN_DATE) && endingDate.equals(StatsParseUtil.MAX_DATE)) {
            this.isDefaultQuery = true;
        } else {
            this.isDefaultQuery = false;
        }
    }

    public boolean isDefaultQuery() {
        return this.isDefaultQuery;
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

    /**
     * extract out starting Date with year and date
     */
    public String displayStartingDate() {
        return StringUtil.convertCalendarDateToString(this.startingDate);
    }

    /**
     * extract out ending date with year and date
     */
    public String displayEndingDate() {
        return StringUtil.convertCalendarDateToString(this.endingDate);
    }

}
