package seedu.address.commons.util;

import java.util.Calendar;

import seedu.address.logic.commands.statisticcommand.StatisticType;
import seedu.address.statistic.StatsPayload;

/**
 *
 *  A utility class containing a list of {@code StatsPayload} objects to be used in tests.
 *
 */
public class TypicalStatsPayload {

    public static final Calendar STARTING_DATE_1 = new Calendar.Builder()
            .setDate(2018, 1, 1)
            .build();
    public static final Calendar STARTING_DATE_2 = new Calendar.Builder()
            .setDate(2018, 2, 3)
            .build();
    public static final Calendar STARTING_DATE_3 = new Calendar.Builder()
            .setDate(2018, 3, 4)
            .build();
    public static final Calendar STARTING_DATE_4 = new Calendar.Builder()
            .setDate(2018, 5, 10)
            .build();
    public static final Calendar STARTING_DATE_5 = new Calendar.Builder()
            .setDate(2018, 10, 29)
            .build();
    public static final Calendar ENDING_DATE_1 = new Calendar.Builder()
            .setDate(2018, 1, 1)
            .build();
    public static final Calendar ENDING_DATE_2 = new Calendar.Builder()
            .setDate(2018, 3, 4)
            .build();
    public static final Calendar ENDING_DATE_3 = new Calendar.Builder()
            .setDate(2019, 4, 24)
            .build();
    public static final Calendar ENDING_DATE_4 = new Calendar.Builder()
            .setDate(2020, 4, 17)
            .build();
    public static final Calendar ENDING_DATE_5 = new Calendar.Builder()
            .setDate(2019, 11, 29)
            .build();


    public static final StatsPayload DEFAULT_STATS_PAYLOAD_REVENUE_1 =
            new StatsPayloadBuilder().withStatsType(StatisticType.REVENUE)
                    .withStartingDate(STARTING_DATE_1)
                    .withEndingDate(ENDING_DATE_1).build();

    public static final StatsPayload DEFAULT_STATS_PAYLOAD_REVENUE_2 =
            new StatsPayloadBuilder().withStatsType(StatisticType.REVENUE)
                    .withStartingDate(STARTING_DATE_2)
                    .withEndingDate(ENDING_DATE_2).build();

    public static final StatsPayload DEFAULT_STATS_PAYLOAD_PROFIT_1 =
            new StatsPayloadBuilder().withStatsType(StatisticType.PROFIT)
                    .withStartingDate(STARTING_DATE_1)
                    .withEndingDate(ENDING_DATE_1).build();

    public static final StatsPayload DEFAULT_STATS_PAYLOAD_PROFIT_2 =
            new StatsPayloadBuilder().withStatsType(StatisticType.PROFIT)
                    .withStartingDate(STARTING_DATE_2)
                    .withEndingDate(ENDING_DATE_2).build();

    public static final StatsPayload DEFAULT_STATS_PAYLOAD_COST_1 =
            new StatsPayloadBuilder().withStatsType(StatisticType.COST)
                    .withStartingDate(STARTING_DATE_1)
                    .withEndingDate(ENDING_DATE_1).build();

    public static final StatsPayload DEFAULT_STATS_PAYLOAD_COST_2 =
            new StatsPayloadBuilder().withStatsType(StatisticType.COST)
                    .withStartingDate(STARTING_DATE_2)
                    .withEndingDate(ENDING_DATE_2).build();

}
