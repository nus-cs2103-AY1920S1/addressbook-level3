package seedu.address.statistic;

import java.util.Calendar;

import seedu.address.logic.commands.statisticcommand.StatisticType;

/**
 * A utility class to help with building Stat Payloads objects.
 */
public class StatsPayloadBuilder {

    public static final StatisticType DEFAULT_STATS_TYPE = StatisticType.PROFIT;
    public static final Calendar DEFAULT_STARTING_TIME =
            new Calendar.Builder().setDate(2018, 1, 2).build();
    public static final Calendar DEFAULT_ENDING_TIME =
            new Calendar.Builder().setDate(2019, 2, 3).build();;


    private Calendar startingDate;
    private Calendar endingDate;
    private StatisticType statisticType;


    /**
     * Constructor that returns a default stats Payload object
     */
    StatsPayloadBuilder() {
        this.statisticType = this.DEFAULT_STATS_TYPE;
        this.startingDate = this.DEFAULT_STARTING_TIME;
        this.endingDate = this.DEFAULT_ENDING_TIME;
    }


    /**
     * Sets the {@code statisticType} of the {@code StatsPayload} that we are building.
     */
    public StatsPayloadBuilder withStatsType(StatisticType type) {
        this.statisticType = type;
        return this;
    }

    /**
     * Sets the {@code startingDate} of the {@code StatsPayload} that we are building.
     */
    public StatsPayloadBuilder withStartingDate(Calendar calendar) {
        this.startingDate = calendar;
        return this;
    }

    /**
     * Sets the {@code endingDate} of the {@code StatsPayload} that we are building.
     */
    public StatsPayloadBuilder withEndingDate(Calendar calendar) {
        this.endingDate = calendar;
        return this;
    }

    /**
     * return the {@code StatsPayload} object that we built
     */
    public StatsPayload build() {
        return new StatsPayload(this.startingDate, this.endingDate, this.statisticType);
    }

}
