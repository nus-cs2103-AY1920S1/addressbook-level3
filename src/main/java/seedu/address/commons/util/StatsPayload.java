package seedu.address.commons.util;

import java.util.Date;

import seedu.address.logic.commands.StatisticType;

/**
 * Payload class to send user input to logic Manager via commandResult
 */
public class StatsPayload {

    private final Date startingDate;
    private final Date endingDate;
    private final StatisticType statisticType;
    /**
     * Constructs a {@code StatsPayload} with the specified {@code startingDate}, {@code statisticType}
     * and specified {@Code endingDate},
     * and set other fields to their default value.
     * @param startingDate starting date of query
     * @param endingDate ending date of query
     */
    public StatsPayload(Date startingDate, Date endingDate,
                        StatisticType statisticType) {
        this.startingDate = startingDate;
        this.endingDate = endingDate;
        this.statisticType = statisticType;
    }

    public Date getStartingDate() {
        return this.startingDate;
    }

    public Date getEndingDate() {
        return this.endingDate;
    }

    public StatisticType getStatisticType() {
        return this.statisticType;
    }
}
